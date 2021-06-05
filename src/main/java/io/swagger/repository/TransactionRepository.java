package io.swagger.repository;

import io.swagger.model.Transaction;
import io.swagger.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Long> {

    public List<Transaction> findByUserPerforming_Id(Long id);
}
