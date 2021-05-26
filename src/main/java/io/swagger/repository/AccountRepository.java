package io.swagger.repository;

import io.swagger.model.Account;
import io.swagger.model.AccountType;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Repository
public interface AccountRepository extends CrudRepository<Account, String> {
    @Transactional
    @Modifying
    @Query(value = "update Account account " +
            "set" +
            "account.balance=?2," +
            " account.accountType=?3, " +
            "account.user=?4 " +
            "where account.iban = ?1 ",
            nativeQuery = true)
    void updateAccountByIban(String iban, BigDecimal balance, AccountType accountType, Integer userId);
}