package io.swagger.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.api.exception.InvalidRequestException;
import io.swagger.model.Account;
import io.swagger.model.CreateAccountDto;
import io.swagger.model.ModifyAccountDto;
import io.swagger.model.User;
import io.swagger.service.AccountService;
import io.swagger.service.IbanService;
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
import java.util.List;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-05-21T13:43:31.154Z[GMT]")
@RestController
public class AccountsApiController implements AccountsApi {

    @Autowired
    private AccountService accountService;

    @Autowired
    private UserService userService;

    @Autowired
    private IbanService ibanService;

    @Autowired
    private ModelMapper modelMapper;

    private static final Logger log = LoggerFactory.getLogger(AccountsApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public AccountsApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<Void> createAccount(@Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema()) @Valid @RequestBody CreateAccountDto body) {
        Account account = this.convertToEntity(body);
        accountService.addAccount(account);

        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<Void> deleteAccount(@Parameter(in = ParameterIn.PATH, description = "The iban account to delete", required = true, schema = @Schema()) @PathVariable("iban") String iban) {
        accountService.deleteAccountByIban(iban);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<Account> getAccount(@Parameter(in = ParameterIn.PATH, description = "The the iban of the account", required = true, schema = @Schema()) @PathVariable("iban") String iban) {

        Account account = accountService.getAccountByIban(iban);

        if (! CurrentUserInfo.isEmployee()) {
            if (! account.getUser().getId().equals(CurrentUserInfo.getCurrentUserId())) {
                throw new InvalidRequestException("You are not allowed to acccess this account.");
            }
        }

        return new ResponseEntity<Account>(account, HttpStatus.OK);
    }

    public ResponseEntity<List<Account>> getAccounts(@Parameter(in = ParameterIn.QUERY, description = "The number of items to skip before starting to collect the result set", schema = @Schema()) @Valid @RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset, @Parameter(in = ParameterIn.QUERY, description = "The numbers of items to return", schema = @Schema()) @Valid @RequestParam(value = "limit", required = false, defaultValue = "100") Integer limit) {
        List<Account> accounts = accountService.getAccounts();

        List<Account> outputAccounts = new ArrayList<>();

            accounts.forEach(account -> {

                if (!CurrentUserInfo.isEmployee()) {
                    if (account.getUser().getId().equals(CurrentUserInfo.getCurrentUserId())) {
                        outputAccounts.add(account);
                    }
                } else {
                    outputAccounts.add(account);
                }
            });
        return new ResponseEntity<List<Account>>(outputAccounts.subList(Math.min(outputAccounts.size(), offset), Math.min(outputAccounts.size(), offset + limit)), HttpStatus.OK);
    }

    public ResponseEntity<Void> updateAccount(@Parameter(in = ParameterIn.PATH, description = "The the iban of the account", required = true, schema = @Schema()) @PathVariable("iban") String iban, @Parameter(in = ParameterIn.DEFAULT, description = "", schema = @Schema()) @Valid @RequestBody ModifyAccountDto body) {
        Account account = this.convertToUpdateAccountEntity(body);
        accountService.updateAccountByIban(account, iban);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    private Account convertToEntity(CreateAccountDto createAccountDto) {
        Account account = modelMapper.map(createAccountDto, Account.class);
        User user = userService.getUserById(CurrentUserInfo.getCurrentUserId());
        account.setUser(user);

        account.setBalance(BigDecimal.ZERO);
        account.setIban(ibanService.generateUniqueIban());

        return account;
    }

    private Account convertToUpdateAccountEntity(ModifyAccountDto modifyAccountDto) {
        Account account = modelMapper.map(modifyAccountDto, Account.class);
        return account;
    }
}
