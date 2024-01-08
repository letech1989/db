package com.bank.db.models;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
class AccountTest {

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void testSaveAndRetrieveAccount() {
        // Create a sample Account
        Account account = new Account("John Doe", "1234567890", "123 Main St", "shamsherdemo@gmail.com", "C123", "A456", 1000.0);

        // Save the Account to the database
        entityManager.persist(account);
        entityManager.flush();

        // Retrieve the Account from the database
        Account retrievedAccount = entityManager.find(Account.class, account.getId());

        // Assertions
        assertNotNull(retrievedAccount);
        assertEquals("John Doe", retrievedAccount.getCustomerName());
        assertEquals("1234567890", retrievedAccount.getmobile());
        assertEquals("123 Main St", retrievedAccount.getAddress());
        // Add more assertions based on your data and requirements
    }
}
