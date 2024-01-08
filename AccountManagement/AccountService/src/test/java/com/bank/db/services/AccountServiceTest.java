package com.bank.db.services;
import com.bank.db.enums.ACTION;
import com.bank.db.enums.ErrorCode;
import com.bank.db.exceptions.BusinessException;
import com.bank.db.models.Account;
import com.bank.db.repositories.AccountRepository;
import com.bank.db.utils.AccountInput;
import com.bank.db.utils.CodeGenerator;
import javafx.beans.binding.When;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountService accountService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAccountByCustomerAndAccountNumber() {
        String customerNumber = "123456789123456";
        String accountNumber = "12345678";
        Account mockAccount = new Account("shamsher","88448033","kastrup 2770", "shamsherdemo@gmail.com","123456789123456","12345678",200);
        when(accountRepository.findByCustomerNumberAndAccountNumber(customerNumber, accountNumber)).thenReturn(Optional.of(mockAccount));
        Account resultAccount = accountService.getAccount(customerNumber, accountNumber);
        verify(accountRepository, times(1)).findByCustomerNumberAndAccountNumber(customerNumber, accountNumber);
        assertEquals(mockAccount, resultAccount);
    }

    @Test
    public void testGetAccountByAccountNumber() {
        String accountNumber = "12345789";
        Account mockAccount = new Account("shamsher","88448033","kastrup 2770", "shamsherdemo@gmail.com","123456789123456","12345678",200);
        when(accountRepository.findByAccountNumber(accountNumber)).thenReturn(Optional.of(mockAccount));
        Account resultAccount = accountService.getAccount(accountNumber);
        verify(accountRepository, times(1)).findByAccountNumber(accountNumber);
        assertEquals(mockAccount, resultAccount);
    }

    @Test
    public void testCreateAccount() {
        CodeGenerator codeGenerator = mock(CodeGenerator.class);
        when(codeGenerator.generateCustomerNumber()).thenReturn("C123");
        when(codeGenerator.generateAccountNumber()).thenReturn("A456");

        String customerName = "Shamsher Yadav";
        String mobileNumber = "1234567890";
        String address = "2770 kastrup";
        String email = "shamsheryadav@db.com";

        Account mockAccount = new Account(customerName, mobileNumber, address, email, "C123", "A456", 0.00);

        when(accountRepository.save(any(Account.class))).thenReturn(mockAccount);

        Account resultAccount = accountService.createAccount(customerName, mobileNumber, address, email);

        verify(accountRepository, times(1)).save(any(Account.class));
        assertEquals(mockAccount, resultAccount);
    }

    @Test
    public void testCreateAccountException() {
        when(accountRepository.save(any(Account.class))).thenThrow(new RuntimeException("Simulating a database exception"));

        assertThrows(BusinessException.class, () -> accountService.createAccount("shamsher yadav", "1234567890", "2770 kastrup", "shamsherdemo@gmail.com"));
    }

    @Test
    public void testUpdateAccountDeposit() {
        AccountInput input = new AccountInput();
        input.setCustomerNumber("123456789123456");
        input.setAccountNumber("12345678");
        input.setAmount(50);
        input.setType(ACTION.DEPOSIT);
        Account mockAccount = new Account("shamsher","88448033","kastrup 2770", "shamsherdemo@gmail.com","123456789123456","12345678",200);
        Account updMockAccount = new Account("shamsher","88448033","kastrup 2770", "shamsherdemo@gmail.com","123456789123456","12345678",250);;

        when(accountRepository.findByCustomerNumberAndAccountNumber(anyString(), anyString())).thenReturn(java.util.Optional.of(mockAccount));
        when(accountRepository.save(any(Account.class))).thenReturn(updMockAccount);
        Account resultAccount = accountService.updateAccount(input, "DEPOSIT");
        assertEquals(updMockAccount.getCurrentBalance(), resultAccount.getCurrentBalance());
    }

    @Test
    public void testUpdateAccountWithdraw() {
        AccountInput input = new AccountInput();
        input.setCustomerNumber("123456789123456");
        input.setAccountNumber("12345678");
        input.setAmount(30);
        input.setType(ACTION.WITHDRAW);
        Account mockAccount = new Account("shamsher","88448033","kastrup 2770", "shamsherdemo@gmail.com","123456789123456","12345678",200);

        mockAccount.setCurrentBalance(100.00);
        when(accountRepository.findByCustomerNumberAndAccountNumber(anyString(), anyString())).thenReturn(java.util.Optional.of(mockAccount));
        when(accountRepository.save(any(Account.class))).thenReturn(mockAccount);

        Account resultAccount = accountService.updateAccount(input, "WITHDRAW");

        verify(accountRepository, times(1)).save(any(Account.class));
        assertEquals(mockAccount, resultAccount);
        assertEquals(70.00, resultAccount.getCurrentBalance());
    }

    @Test
    public void testUpdateAccountWithdrawInsufficientFunds() {
        AccountInput input = new AccountInput();
        input.setCustomerNumber("123456789123456");
        input.setAccountNumber("12345678");
        input.setAmount(30.4);
        input.setType(ACTION.WITHDRAW);
        Account mockAccount = new Account("shamsher","88448033","kastrup 2770", "shamsherdemo@gmail.com","123456789123456","12345678",0);
        when(accountRepository.findByCustomerNumberAndAccountNumber(anyString(), anyString())).thenReturn(java.util.Optional.of(mockAccount));
        assertThrows(BusinessException.class, () -> accountService.updateAccount(input, "WITHDRAW"));
        verify(accountRepository, never()).save(any(Account.class));
    }



    @Test
    void updateAccount_shouldThrowBusinessException_whenWithdrawalExceedsBalance() {

        AccountInput input = new AccountInput();
        input.setCustomerNumber("123456789123456");
        input.setAccountNumber("12345678");
        input.setAmount(30.4);
        input.setType(ACTION.WITHDRAW);
        Account mockAccount = new Account("shamsher yadav", "1234567890", "2770 kastrup", "shamsherdemo@gmail.com", "122765783627845", "12345678", 50.00);
        when(accountRepository.findByCustomerNumberAndAccountNumber(anyString(), anyString())).thenReturn(java.util.Optional.of(mockAccount));
        accountService.updateAccount(input,input.getType().name());
        BusinessException exception = assertThrows(BusinessException.class, () -> accountService.updateAccount(input, "WITHDRAW"));

        assertEquals(ErrorCode.INSUFFICIENT_FUNDS.getCode(), exception.getErrorCode());
        assertEquals(ErrorCode.INSUFFICIENT_FUNDS.getMessage(), exception.getMessage());
    }


    @Test
    void getAccount_withCustomerAndAccountNumbers_shouldReturnAccount_whenAccountExists() {

        String customerNumber = "123456789012345";
        String accountNumber = "12345678";
        Account mockAccount = new Account("Shamsher", "1234567890", "kastrup", "shamsherdemo@gmail.com", customerNumber, accountNumber, 100.00);
        when(accountRepository.findByCustomerNumberAndAccountNumber(anyString(), anyString())).thenReturn(Optional.of(mockAccount));

        Account result = accountService.getAccount(customerNumber, accountNumber);

        assertNotNull(result);
        assertEquals(customerNumber, result.getCustomerNumber());
        assertEquals(accountNumber, result.getAccountNumber());
        assertEquals(100.00, result.getCurrentBalance());
    }

    @Test
    void getAccount_withCustomerAndAccountNumbers_shouldReturnNull_whenAccountDoesNotExist() {

        String customerNumber = "989878765413245";
        String accountNumber = "12345678";
        when(accountRepository.findByCustomerNumberAndAccountNumber(anyString(), anyString())).thenReturn(Optional.empty());

        Account result = accountService.getAccount(customerNumber, accountNumber);

        assertNull(result);
    }

    @Test
    void getAccount_withAccountNumber_shouldReturnAccount_whenAccountExists() {

        String accountNumber = "12345678";
        Account mockAccount = new Account("shamsher yadav", "1234567890", "Address", "shamsherdemo@gmail.com", "123546789876345", accountNumber, 50.00);
        when(accountRepository.findByAccountNumber(anyString())).thenReturn(Optional.of(mockAccount));

        Account result = accountService.getAccount(null, accountNumber);

        assertNotNull(result);
        assertEquals("123546789876345", result.getCustomerNumber());
        assertEquals(accountNumber, result.getAccountNumber());
        assertEquals(50.00, result.getCurrentBalance());
    }

    @Test
    void getAccount_withAccountNumber_shouldReturnNull_whenAccountDoesNotExist() {

        String accountNumber = "12345678";
        when(accountRepository.findByAccountNumber(anyString())).thenReturn(Optional.empty());

        Account result = accountService.getAccount(null, accountNumber);

        assertNull(result);
    }

    @Test
    void getAccount_withNullArguments_shouldReturnNull() {

        Account result = accountService.getAccount(null, null);

        assertNull(result);
    }

}
