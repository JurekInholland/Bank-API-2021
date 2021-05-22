package io.swagger.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.api.exception.*;
import io.swagger.model.*;
import io.swagger.service.AccountService;
import io.swagger.service.TransactionService;
import io.swagger.service.UserService;
import io.swagger.util.CurrentUserInfo;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-05-21T13:43:31.154Z[GMT]")
@RestController
public class TransactionsApiController implements TransactionsApi {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private UserService userService;

    private static final Logger log = LoggerFactory.getLogger(TransactionsApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public TransactionsApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<Void> createTransaction(@Parameter(in = ParameterIn.DEFAULT, description = "", required=true, schema=@Schema()) @Valid @RequestBody CreateTransactionDto body) {
//    TODO: check daily limit
        Transaction transaction = createTransactionFromDto(body);
        transactionService.addTransaction(transaction);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<Void> deleteTransaction(@Parameter(in = ParameterIn.PATH, description = "The transactionId of the transaction", required=true, schema=@Schema()) @PathVariable("transactionId") Integer transactionId) {
        transactionService.deleteTransactionById(transactionId);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<PublicTransactionDto> getTransactionById(@Parameter(in = ParameterIn.PATH, description = "The transactionId of the transaction", required=true, schema=@Schema()) @PathVariable("transactionId") Integer transactionId) {
        return new ResponseEntity<PublicTransactionDto>(convertToDto(transactionService.getTransactionById(transactionId)),HttpStatus.OK);
    }

    public ResponseEntity<List<PublicTransactionDto>> getTransactions(@Parameter(in = ParameterIn.QUERY, description = "The number of items to skip before starting to collect the result set" ,schema=@Schema()) @Valid @RequestParam(value = "offset", required = false) Integer offset,@Parameter(in = ParameterIn.QUERY, description = "The numbers of items to return" ,schema=@Schema()) @Valid @RequestParam(value = "limit", required = false) Integer limit) {

        UserRoles currentRoles = CurrentUserInfo.getCurrentUserRoles();

        List<PublicTransactionDto> dtoList = new ArrayList<>();
        List<Transaction> transactions = transactionService.getTransactions();
        transactions.forEach(trans -> dtoList.add(convertToDto(trans)));

        Long currentUserId = CurrentUserInfo.getCurrentUserId();
        Iterator iterator = dtoList.iterator();

        if (currentRoles.stream().noneMatch(Role.EMPLOYEE::equals)) {
            while (iterator.hasNext()) {
                PublicTransactionDto transaction = (PublicTransactionDto) iterator.next();
                System.out.println("customer ids:" + transaction.getUserPerforming().getId() + " current: " + currentUserId);

                if (! transaction.getUserPerforming().getId().equals(currentUserId)) {
                    System.out.println("remove");
                    iterator.remove();
                }
            }
        }

        Transaction last = transactions.get(transactions.size()-1);
        BigDecimal daily = last.getDailySum(transactions);
        System.out.println("DAILY SUM:" + daily);

        return new ResponseEntity<List<PublicTransactionDto>>(dtoList, HttpStatus.OK);
    }
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<Void> updateTransaction(@Parameter(in = ParameterIn.PATH, description = "The transactionId of the transaction", required=true, schema=@Schema()) @PathVariable("transactionId") Integer transactionId,@Parameter(in = ParameterIn.DEFAULT, description = "", required=true, schema=@Schema()) @Valid @RequestBody ModifyTransactionDto body) {
        Transaction transaction = transactionService.getTransactionById(transactionId);

        if (body.isEmpty()) {
            throw new EmptyBodyException();
        }

        if (body.getFromIban() != null) {
            Account fromAccount = accountService.getAccountByIban(body.getFromIban());
            transaction.setAccountFrom(fromAccount);
        }
        if (body.getToIban() != null) {
            Account toAccount = accountService.getAccountByIban(body.getToIban());
            transaction.setAccountTo(toAccount);
        }
        if (body.getAmount() != null) {
            transaction.setAmount(body.getAmount());
        }
        if (body.getTimestamp() != null) {
            transaction.setTimestamp(body.getTimestamp());
        }

        transactionService.addTransaction(transaction);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    private Transaction createTransactionFromDto(CreateTransactionDto createTransaction) {
        Transaction transaction = null;
        Account fromAccount = null;
        Account toAccount = null;

        transaction = modelMapper.map(createTransaction, Transaction.class);
        fromAccount = accountService.getAccountByIban(createTransaction.getFromIban());
        toAccount = accountService.getAccountByIban(createTransaction.getToIban());


        User currentUser = userService.getUserById(CurrentUserInfo.getCurrentUserId());

        transaction.setAccountFrom(fromAccount);
        transaction.setAccountTo(toAccount);
        transaction.setUserPerforming(currentUser);

        if (fromAccount.getUser().getId() != currentUser.getId()) {
            if (! CurrentUserInfo.isEmployee()) {
                throw new NoAccessToAccountException();
            }
        }

        if (fromAccount == null || toAccount == null) {
            throw new AccountNotFoundException();
        }
        if (fromAccount.getIban() == toAccount.getIban()) {
            throw new InvalidAccountException();
        }

        if (transaction.hasSufficientBalance()) {
            transaction.execute();
        } else {
            throw new InsufficientBalanceException();
        }

        return transaction;

    }

    private PublicTransactionDto convertToDto(Transaction transaction) {
        PublicTransactionDto publicTransactionDto = modelMapper.map(transaction, PublicTransactionDto.class);
        return publicTransactionDto;
    }

}
