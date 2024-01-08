package com.bank.db.repositories;
import com.bank.db.models.Account;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class AccountRepositoryTest {

    @Autowired
    private AccountRepository accountRepository;

    @Test
    void testFindByCustomerNumberAndAccountNumber() {
        // Create a sample Account
        Account account = new Account("John Doe", "1234567890", "123 Main St", "C123@gmail.com", "A456", "636363", 1000.0);

        // Save the Account to the database
        accountRepository.save(account);

        // Query the repository using findByCustomerNumberAndAccountNumber
        Optional<Account> optionalAccount = accountRepository.findByCustomerNumberAndAccountNumber("C123", "A456");

        // Assertions
        assertTrue(optionalAccount.isPresent());
        assertEquals("John Doe", optionalAccount.get().getCustomerName());
        assertEquals("1234567890", optionalAccount.get().getmobile());
        assertEquals("123 Main St", optionalAccount.get().getAddress());
        // Add more assertions based on your data and requirements
    }

    @Test
    void testFindByAccountNumber() {
        // Create a sample Account
        Account account = new Account("Jane Doe", "9876543210", "456 Main St", "C456", "A789", "12346789",1500.0);

        // Save the Account to the database
        accountRepository.save(account);

        // Query the repository using findByAccountNumber
        Optional<Account> optionalAccount = accountRepository.findByAccountNumber("A789");

        // Assertions
        assertTrue(optionalAccount.isPresent());
        assertEquals("Jane Doe", optionalAccount.get().getCustomerName());
        assertEquals("9876543210", optionalAccount.get().getmobile());
        assertEquals("456 Main St", optionalAccount.get().getAddress());
        // Add more assertions based on your data and requirements
    }
}
