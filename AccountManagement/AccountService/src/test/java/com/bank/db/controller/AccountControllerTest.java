package com.bank.db.controller;
import com.bank.db.enums.ACTION;
import com.bank.db.enums.ErrorCode;
import com.bank.db.exceptions.BusinessException;
import com.bank.db.exceptions.InputValidationException;
import com.bank.db.models.Account;
import com.bank.db.repositories.AccountRepository;
import com.bank.db.services.AccountService;
import com.bank.db.utils.AccountInput;
import com.bank.db.utils.CodeGenerator;
import com.bank.db.utils.CreateAccountInput;
import com.bank.db.utils.InputValidator;

import javafx.beans.binding.When;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;



@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class AccountControllerTest {


    @Mock
    private AccountService accountService;


    @Mock
    AccountRepository accountRepository;

    @Mock
    InputValidator inputValidator;


    CreateAccountInput validInput;

    AccountInput accountInput;

    @InjectMocks
    private AccountController accountController;

    Account account;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks

        validInput =new CreateAccountInput();

        validInput.setCustomerName("Shamsher yadav");
        validInput.setAddress("2770 kastrup");
        validInput.setEmail("shamsherdemo@example.com");
        validInput.setMobileNumber("12345678");

        accountInput = new AccountInput();
        accountInput.setAccountNumber("12345678");
        accountInput.setCustomerNumber("123456753456782");
        accountInput.setType(ACTION.DEPOSIT);
        accountInput.setAmount(2000);

        account = new Account(validInput.getCustomerName(), "12345678","kastrup 3300", validInput.getEmail(),"123456753456782","12345678",1000.0);

    }

    @Test
    void createAccountTest() {
        when(inputValidator.isCreateAccountCriteriaValid(validInput)).thenReturn(true);
        ResponseEntity<String> dd = accountController.createAccount(validInput);
        assertEquals(HttpStatus.CREATED, dd.getStatusCode());
        assertEquals("Account created  successfully", dd.getBody().toString());

    }

    @Test
    void creationFailedAccountTest() {
        when(inputValidator.isCreateAccountCriteriaValid(validInput)).thenReturn(false);
        ResponseEntity<String> dd = accountController.createAccount(validInput);
        assertEquals(HttpStatus.BAD_REQUEST, dd.getStatusCode());
    }



    @Test
    void updateAccount_DepositAction_Successful() {
        when(accountService.updateAccount(accountInput,  accountInput.getType().name())).thenReturn(account);
        Account updatedAccount = accountService.updateAccount(accountInput, "DEPOSIT");
        assertNotNull(updatedAccount);
    }



    @Test
    void depositAmount() {
        when(inputValidator.isSearchCriteriaValid(accountInput)).thenReturn(true);
        when(accountService.updateAccount(accountInput,  accountInput.getType().name())).thenReturn(account);
        ResponseEntity<?> dd  = accountController.updateAccount(accountInput);
        assertEquals(HttpStatus.OK, dd.getStatusCode());
        assertEquals("Account updated succesfully", dd.getBody().toString());

    }

    @Test
    void withdrawAmount() {
        accountInput.setType(ACTION.WITHDRAW);
        when(inputValidator.isSearchCriteriaValid(accountInput)).thenReturn(true);
        when(accountService.updateAccount(accountInput,  accountInput.getType().name())).thenReturn(account);
        ResponseEntity<?> dd  = accountController.updateAccount(accountInput);
        assertEquals(HttpStatus.OK, dd.getStatusCode());
        assertEquals("Account updated succesfully", dd.getBody().toString());

    }

    @Test
    void failedwithdrawAmount() {
        accountInput.setType(ACTION.WITHDRAW);
        accountInput.setAmount(10000);
        when(inputValidator.isSearchCriteriaValid(accountInput)).thenReturn(true);
        when(accountService.updateAccount(accountInput,  accountInput.getType().name())).thenReturn(account);
        ResponseEntity<?> dd  = accountController.updateAccount(accountInput);
        assertEquals(HttpStatus.OK, dd.getStatusCode());
        assertEquals("Account updated succesfully", dd.getBody().toString());

    }

    @Test
    void checkAccountBalanceTest() {
        when(inputValidator.isAccountNoValid(account.getAccountNumber())).thenReturn(true);
        when(accountService.getAccount(any())).thenReturn(account);
        ResponseEntity<?> dd = accountController.checkAccountBalance(account.getAccountNumber());
        assertEquals(HttpStatus.OK, dd.getStatusCode());
        Account acc= (Account) dd.getBody();
        assertEquals(acc.getCurrentBalance(), account.getCurrentBalance());

    }








}
