package io.swagger.service;

import java.util.Optional;
import java.util.Random;

import io.swagger.model.Account;
import io.swagger.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service

public class IbanServiceImpl implements IbanService{

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public Integer randNumber() {
        Random rand = new Random();

        return rand.nextInt(10);
    }

    @Override
    public String generateIban() {
        StringBuilder ibanBuilder = new StringBuilder("NL");
        ibanBuilder.append(this.randNumber());
        ibanBuilder.append(this.randNumber());
        ibanBuilder.append("INHO0");
        ibanBuilder.append(this.randNumber());
        ibanBuilder.append(this.randNumber());
        ibanBuilder.append(this.randNumber());
        ibanBuilder.append(this.randNumber());
        ibanBuilder.append(this.randNumber());
        ibanBuilder.append(this.randNumber());
        ibanBuilder.append(this.randNumber());
        ibanBuilder.append(this.randNumber());
        ibanBuilder.append(this.randNumber());

        return ibanBuilder.toString();
    }

    @Override
    public String generateUniqueIban() {
        String iban = this.generateIban();

        while (!this.isUnique(iban))
        {
            iban = this.generateIban();
        }

        return iban;
    }

    @Override
    public boolean isUnique(String iban) {
        Optional<Account> account = accountRepository.findById(iban);
        boolean isUnique = !account.isPresent();

        return isUnique;
    }
}
