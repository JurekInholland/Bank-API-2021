package io.swagger.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.api.exception.AccountNotFoundException;
import io.swagger.model.*;
import io.swagger.service.AccountService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-05-21T13:43:31.154Z[GMT]")
@RestController
public class AccountsApiController implements AccountsApi {

    @Autowired
    private AccountService accountService;

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

    public ResponseEntity<Void> createAccount(@Parameter(in = ParameterIn.DEFAULT, description = "", required=true, schema=@Schema()) @Valid @RequestBody CreateAccountDto body) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                Account account = modelMapper.map(body, Account.class);
                accountService.addAccount(account);
            } catch (Exception e) {
                log.error(e.getMessage());
                return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<Void>(HttpStatus.OK);
        }

        return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<Void> deleteAccount(@Parameter(in = ParameterIn.PATH, description = "The iban account to delete", required=true, schema=@Schema()) @PathVariable("iban") String iban) {
        accountService.deleteAccountByIban(iban);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<Account> getAccount(@Parameter(in = ParameterIn.PATH, description = "The the iban of the account", required=true, schema=@Schema()) @PathVariable("iban") String iban) {
        String token = request.getHeader("Authorization");

        try {
            boolean auth = accountService.checkUserRole(token, iban);
            if(auth)
            {
                Account account = accountService.getAccountByIban(iban);
                return new ResponseEntity<Account>(account, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }

        } catch (AccountNotFoundException e) {
            return new ResponseEntity<Account>(HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<List<Account>> getAccounts(@Parameter(in = ParameterIn.QUERY, description = "The number of items to skip before starting to collect the result set" ,schema=@Schema()) @Valid @RequestParam(value = "offset", required = false, defaultValue="0") Integer offset,@Parameter(in = ParameterIn.QUERY, description = "The numbers of items to return" ,schema=@Schema()) @Valid @RequestParam(value = "limit", required = false, defaultValue="100") Integer limit) {
        List<Account> accounts = accountService.getAccounts();
        return new ResponseEntity<List<Account>>(accounts.subList(Math.min(accounts.size(), offset),Math.min(accounts.size(), offset + limit)),HttpStatus.OK);
    }
    public ResponseEntity<Void> updateAccount(@Parameter(in = ParameterIn.PATH, description = "The the iban of the account", required=true, schema=@Schema()) @PathVariable("iban") String iban,@Parameter(in = ParameterIn.DEFAULT, description = "", schema=@Schema()) @Valid @RequestBody ModifyAccountDto body) {
        String accept = request.getHeader("Accept");

        if (accept != null && accept.contains("application/json")) {
            try {
                Account account = modelMapper.map(body, Account.class);
                boolean update = accountService.updateAccountByIban(account, iban);
                if (update){
                  return new ResponseEntity<Void>(HttpStatus.OK);
                }
                else {
                    return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
                }
            } catch (Exception e) {
                log.error(e.getMessage());
                return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
    }

    private Account convertToEntity(CreateAccountDto createAccountDto) {

        Account account = modelMapper.map(createAccountDto, Account.class);

        return account;
    }
    private Account convertToUpdateUserEntity(ModifyAccountDto modifyAccountDto)
    {
        Account account = modelMapper.map(modifyAccountDto, Account.class);
        return account;
    }

    private PublicAccountDto convertToPublicDto(Account account) {
        PublicAccountDto publicAccount = modelMapper.map(account, PublicAccountDto.class);
        return publicAccount;
    }
}
