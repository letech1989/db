package com.bank.db.controller;
import com.bank.db.enums.ACTION;
import com.bank.db.enums.ErrorCode;
import com.bank.db.models.Account;
import com.bank.db.models.Transaction;
import com.bank.db.services.TransactionService;
import com.bank.db.utils.AccountInput;
import com.bank.db.utils.InputValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TransactionControllerTest {

    @Mock
    private TransactionService transactionService;

    @Mock
    InputValidator inputValidator;

    @InjectMocks
    private TransactionController transactionController;

    AccountInput accountInput;
    Account account;
    Transaction transaction;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);


        accountInput = new AccountInput();
        accountInput.setAccountNumber("12345678");
        accountInput.setCustomerNumber("123456753456782");
        accountInput.setType(ACTION.DEPOSIT);
        accountInput.setAmount(2000);

        transaction = new Transaction();
        transaction.setId(12221222);
        transaction.setReference("");
        transaction.setAmount(2000);
        transaction.setCompletionDate(LocalDateTime.now());
        transaction.setcustomerNumber("123456789098765");
        transaction.setAccountNumber("12345678");

        account = new Account("shamsher", 12345678,"kastrup 3300", "123456753456782","12345678",1000.0);

    }

    @Test
    public void testDepositValidInput() {


        when(inputValidator.isSearchCriteriaValid(accountInput)).thenReturn(true);
        when(transactionService.depositAmount(accountInput)).thenReturn(transaction);

        ResponseEntity<?> responseEntity = transactionController.deposit(accountInput);

        verify(transactionService, times(1)).depositAmount(accountInput);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(transaction, responseEntity.getBody());
    }

    @Test
    public void testDepositInvalidInput() {
        AccountInput invalidDepositInput = new AccountInput();

        when(inputValidator.isSearchCriteriaValid(invalidDepositInput)).thenReturn(false);

        ResponseEntity<?> responseEntity = transactionController.deposit(invalidDepositInput);

        verify(transactionService, never()).depositAmount(any(AccountInput.class));
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(ErrorCode.INVALID_INPUT.getMessage(), responseEntity.getBody());
    }

    @Test
    public void testWithdrawValidInput() {
        AccountInput withdrawInput = new AccountInput(/* Set necessary properties */);
        Transaction mockTransaction = new Transaction(/* Set necessary properties */);

        when(inputValidator.isSearchCriteriaValid(withdrawInput)).thenReturn(true);
        when(transactionService.withdrawAmount(withdrawInput)).thenReturn(mockTransaction);

        ResponseEntity<?> responseEntity = transactionController.withdraw(withdrawInput);

        verify(transactionService, times(1)).withdrawAmount(withdrawInput);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Amount withdraw successfully", responseEntity.getBody());
    }

    @Test
    public void testWithdrawInvalidInput() {
        AccountInput invalidWithdrawInput = new AccountInput(/* Set invalid properties */);

        when(inputValidator.isSearchCriteriaValid(invalidWithdrawInput)).thenReturn(false);

        ResponseEntity<?> responseEntity = transactionController.withdraw(invalidWithdrawInput);

        verify(transactionService, never()).withdrawAmount(any(AccountInput.class));
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(ErrorCode.INVALID_INPUT.getMessage(), responseEntity.getBody());
    }

    @Test
    public void testGetTransactionDetailsValidInput() {
        String validAccountNumber = "123456789";
        List<Transaction> mockTransactions = Collections.singletonList(new Transaction(/* Set necessary properties */));

        when(inputValidator.isAccountNoValid(validAccountNumber)).thenReturn(true);
        when(transactionService.getTransactionDetailsByAccNumber(validAccountNumber)).thenReturn(mockTransactions);

        ResponseEntity<?> responseEntity = transactionController.getTransactionDetails(validAccountNumber);

        verify(transactionService, times(1)).getTransactionDetailsByAccNumber(validAccountNumber);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mockTransactions, responseEntity.getBody());
    }

    @Test
    public void testGetTransactionDetailsInvalidInput() {
        String invalidAccountNumber = "invalidAccount";

        when(inputValidator.isAccountNoValid(invalidAccountNumber)).thenReturn(false);

        ResponseEntity<?> responseEntity = transactionController.getTransactionDetails(invalidAccountNumber);

        verify(transactionService, never()).getTransactionDetailsByAccNumber(any());
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(ErrorCode.INVALID_ACCOUNT_NUMBER, responseEntity.getBody());
    }

    @Test
    public void testGetTransactionDetailsNoDataFound() {
        String validAccountNumber = "123456789";

        when(inputValidator.isAccountNoValid(validAccountNumber)).thenReturn(true);
        when(transactionService.getTransactionDetailsByAccNumber(validAccountNumber)).thenReturn(null);

        ResponseEntity<?> responseEntity = transactionController.getTransactionDetails(validAccountNumber);

        verify(transactionService, times(1)).getTransactionDetailsByAccNumber(validAccountNumber);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("No Data found ", responseEntity.getBody());
    }


}
