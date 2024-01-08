package com.bank.db.repositories;

import com.bank.db.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByAccountNumber(String accountNumber);
    List<Transaction> findByCustomerNumber(String customerNumber);
    List<Transaction> findByAccountNumberOrCustomerNumber(String accountNumber, String customerNumber);

}
