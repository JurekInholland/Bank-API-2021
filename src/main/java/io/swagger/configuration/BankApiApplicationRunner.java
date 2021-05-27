package io.swagger.configuration;

import io.swagger.model.*;
import io.swagger.service.AccountService;
import io.swagger.service.IbanService;
import io.swagger.service.TransactionService;
import io.swagger.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.threeten.bp.OffsetDateTime;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Random;

@Component
public class BankApiApplicationRunner implements ApplicationRunner
{
    @Autowired
    private UserService userService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private IbanService ibanService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    PasswordEncoder encoder;


//    Get bank iban from application.properties
    @Value("${server.bankAccount.iban}")
    private String bankIban;

    @Override
    public void run(ApplicationArguments args) throws Exception
    {
        // Seed database users

//        UserRoles customer = new UserRoles(Role.CUSTOMER);
//        UserRoles bothRoles = new UserRoles(Role.EMPLOYEE);
//        bothRoles.addRole(Role.CUSTOMER);

        Collection<Role> roles = new ArrayList<>();

//        roles.add(Role.CUSTOMER);
        roles.add(Role.EMPLOYEE);


        List<User> users = Arrays.asList
        (
            new User("Brad","Gibson","0119627516","brad.gibson@example.com",encoder.encode("firewall"),roles),
            new User("Bobbie","Grant","0112627416","bobbie.grant@example.com",encoder.encode("bobbiegrant123"),roles),
            new User("Alex","Smith","0549687523","alex.smith@example.com",encoder.encode("alexsmith132"),roles),
            new User("Ray","Manzarek","0295482332","ray.manzarek@example.com",encoder.encode("ray475"),roles),
            new User("Benjamin","Franklin","0234765412","benjamin.franklin@example.com",encoder.encode("benjfrank091"),roles),
            new User("George","Washington","0259847659","george.washington@example.com",encoder.encode("georgie829"),roles),
            new User("Felix","Brown","0928957462","felix.brown@example.com",encoder.encode("felixbrown  430"),roles),
            new User("Hans","Zimmer","0939857193","hans.zimmer@example.com",encoder.encode("hans4849"),roles),
            new User("Neil","Gardner","0294891839","neil.garnder@example.com",encoder.encode("Neil1093"),roles),
            new User("Virgil","Manson","0981049285","virgil.manson@example.com",encoder.encode("virgil98"),roles),
            new User("Scrooge","McDuck","0123456789","scrooge@mcduck.com",encoder.encode("swimmingingold"), roles)
        );
        users.forEach(user -> userService.addUser(user));

        // Seed accounts
        List<Account> accountList = new ArrayList<Account>();

//        Create dedicated bank account
        Account bankAccount = new Account(bankIban,users.get(10),BigDecimal.valueOf(9999999), AccountType.CURRENT);

        // Add fixed accounts for debugging
        Account fixedAccount1 = new Account("TESTIBAN01", users.get(0), BigDecimal.valueOf(5000), AccountType.SAVINGS);
        Account fixedAccount2 = new Account("TESTIBAN02", users.get(1), BigDecimal.valueOf(500.50), AccountType.CURRENT);
        accountList.add(fixedAccount1);
        accountList.add(fixedAccount2);
        accountList.add(bankAccount);

        // Create bank accounts for users
        for (User user : users) {

            // Generate between 1-4 accounts per User
            Random rand = new Random();
            Integer low = 1;
            Integer high = 5;
            Integer numOfAccounts = rand.nextInt(high-low) + low;

            for (Integer i = 0; i < numOfAccounts; i++) {
                // Random amount between 10-3000
                low = 10;
                high = 3000;
                BigDecimal accountBalance = BigDecimal.valueOf(rand.nextInt(high-low) + low);

                // Add IBAN and User to account
                accountService.addAccount(
                    new Account(ibanService.generateUniqueIban(), user, accountBalance, AccountType.CURRENT)
                );
            }
        }
        accountService.addAccount(accountList);

        Transaction testTransaction = new Transaction();
        testTransaction.setAmount(BigDecimal.valueOf(25.25));
        testTransaction.setTimestamp(OffsetDateTime.now().minusDays(1).minusHours(1));
        testTransaction.setAccountFrom(accountList.get(0));
        testTransaction.setAccountTo((accountList.get(1)));
        testTransaction.setUserPerforming(users.get(0));
        transactionService.addTransaction(testTransaction);
    }
}
