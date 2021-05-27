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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.threeten.bp.OffsetDateTime;

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

    @Value("${server.bankAccount.iban}")
    private String bankIban;

    private static final Logger log = LoggerFactory.getLogger(TransactionsApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public TransactionsApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }


    public ResponseEntity<Void> createTransaction(@Parameter(in = ParameterIn.DEFAULT, description = "", required=true, schema=@Schema()) @Valid @RequestBody CreateTransactionDto body) {
        Transaction transaction = createTransactionFromDto(body);
        validateTransaction(transaction);
        transaction.execute();

        transactionService.addTransaction(transaction);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<Void> deleteTransaction(@Parameter(in = ParameterIn.PATH, description = "The transactionId of the transaction", required=true, schema=@Schema()) @PathVariable("transactionId") Integer transactionId) {
        transactionService.deleteTransactionById(transactionId);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<Transaction> getTransactionById(@Parameter(in = ParameterIn.PATH, description = "The transactionId of the transaction", required=true, schema=@Schema()) @PathVariable("transactionId") Integer transactionId) {
        Transaction transaction = transactionService.getTransactionById(transactionId);
        if (! CurrentUserInfo.isEmployee()) {
            if (! transaction.getAccountFrom().getUser().getId().equals(CurrentUserInfo.getCurrentUserId())) {
                throw new UnauthorizedRequestException("You are not allowed to access this transaction.");
            }
        }

        return new ResponseEntity<Transaction>(transaction,HttpStatus.OK);
    }

    public ResponseEntity<List<PublicTransactionDto>> getTransactions(@Parameter(in = ParameterIn.QUERY, description = "The number of items to skip before starting to collect the result set" ,schema=@Schema()) @Valid @RequestParam(value = "offset", required = false) Integer offset,@Parameter(in = ParameterIn.QUERY, description = "The numbers of items to return" ,schema=@Schema()) @Valid @RequestParam(value = "limit", required = false) Integer limit) {

        List<Transaction> transactions = transactionService.getTransactions();
        List<PublicTransactionDto> dtoList = new ArrayList<>();
        transactions.forEach(transaction -> dtoList.add(convertToDto(transaction)));

        Long currentUserId = CurrentUserInfo.getCurrentUserId();
        Iterator iterator = dtoList.iterator();

//        If current user is not an employee
        if (! CurrentUserInfo.isEmployee()) {
            while (iterator.hasNext()) {
                PublicTransactionDto transaction = (PublicTransactionDto) iterator.next();

//                Remove transaction from list if current user is not the performing user
                if (! transaction.getUserPerforming().getId().equals(currentUserId)) {
                    iterator.remove();
                }
            }
        }
        return new ResponseEntity<List<PublicTransactionDto>>(dtoList, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<Void> updateTransaction(@Parameter(in = ParameterIn.PATH, description = "The transactionId of the transaction", required=true, schema=@Schema()) @PathVariable("transactionId") Integer transactionId,@Parameter(in = ParameterIn.DEFAULT, description = "", required=true, schema=@Schema()) @Valid @RequestBody ModifyTransactionDto body) {
        Transaction transaction = transactionService.getTransactionById(transactionId);

        if (body.isEmpty()) {
            throw new InvalidRequestException("No values provided.");
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
        transaction.setAccountFrom(fromAccount);

        toAccount = accountService.getAccountByIban(createTransaction.getToIban());
        transaction.setAccountTo(toAccount);

//        This 'database lookup' could be avoided...
        User currentUser = userService.getUserById(CurrentUserInfo.getCurrentUserId());
        transaction.setUserPerforming(currentUser);

        return transaction;
    }

    private void validateTransaction(Transaction transaction) {
        validateDailyLimit(transaction);
        validateAbsoluteLimit(transaction);
        validateTransactionLimit(transaction);
        validateSavingsAccount(transaction);
        validateEmployeeAccess(transaction);
        validateSameAccount(transaction);
    }

    private void validateDailyLimit(Transaction transaction) {
        List<Transaction> userTransactions = transactionService.getUserTransactions(transaction.getUserPerforming());

        BigDecimal dailySum = getDailySum(userTransactions);
        dailySum = dailySum.add(transaction.getAmount());

        BigDecimal dailyLimit = transaction.getUserPerforming().getDailyLimit();

        if (dailySum.compareTo(dailyLimit) > 0) {
            throw new DailyLimitReachedException(dailyLimit);
        }
    }

    private void validateAbsoluteLimit(Transaction transaction) {
        if (transaction.getAccountFrom().getBalance().subtract(transaction.getAmount()).compareTo(transaction.getAccountFrom().getAbsoluteLimit()) < 0) {
            throw new AbsoluteLimitException(transaction.getAmount(),transaction.getAccountFrom().getBalance(),transaction.getAccountFrom().getAbsoluteLimit());
        }
    }

    private void validateTransactionLimit(Transaction transaction) {
        if (transaction.getAmount().compareTo(transaction.getUserPerforming().getTransactionLimit()) > 0) {
            throw new TransactionLimitException(transaction.getAmount(), transaction.getUserPerforming().getTransactionLimit());
        }
    }

    private void validateSavingsAccount(Transaction transaction) {

        //        Validate only owners can transfer to and from savings account
        if (transaction.getAccountFrom().getUser() != transaction.getAccountTo().getUser()) {
            if (transaction.getAccountFrom().getAccountType() == AccountType.SAVINGS) {
                throw new InvalidRequestException("You cannot transfer from a savings account to another account you don't own.");
            }
            if (transaction.getAccountTo().getAccountType() == AccountType.SAVINGS) {
                throw new InvalidRequestException("You cannot transfer to a savings account you don't own.");
            }
        }
    }

    private void validateEmployeeAccess(Transaction transaction) {

        //        Only employees have access to accounts they don't own
        if (transaction.getAccountFrom().getUser().getId() != transaction.getUserPerforming().getId()) {

//            Only the owner has access to the bank's account
            if (! CurrentUserInfo.isEmployee() || transaction.getAccountFrom().getIban().equals(bankIban) ) {
                throw new UnauthorizedRequestException(String.format("You don't have access to the account with iban %s.",transaction.getAccountFrom().getIban()));
            }
        }
    }

    private void validateSameAccount(Transaction transaction) {
        if (transaction.getAccountFrom().getIban() == transaction.getAccountTo().getIban()) {
            throw new InvalidRequestException("origin and target accounts cannot be the same.");
        }
    }

    private BigDecimal getDailySum(List<Transaction> transactions) {
        //    Sum up amount of all transactions within last day
        BigDecimal dailySum = transactions.stream()
                .map(transaction -> {
                    if (transaction.getTimestamp().isAfter(OffsetDateTime.now().minusDays(1))) {
                        return transaction.getAmount();
                    }
                    return BigDecimal.ZERO;
                }).reduce(BigDecimal.ZERO,BigDecimal::add);

        return dailySum;
    }

    private PublicTransactionDto convertToDto(Transaction transaction) {
        PublicTransactionDto publicTransactionDto = modelMapper.map(transaction, PublicTransactionDto.class);
        return publicTransactionDto;
    }
}
