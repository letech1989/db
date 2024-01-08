package com.bank.db.repositories;

import com.bank.db.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByCustomerNumberAndAccountNumber(String customerNumber, String accountNumber);
    Optional<Account> findByAccountNumber(String accountNumber);
}
