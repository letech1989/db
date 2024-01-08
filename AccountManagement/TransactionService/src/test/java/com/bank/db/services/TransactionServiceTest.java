package com.bank.db.services;
import com.bank.db.enums.ACTION;
import com.bank.db.exceptions.BusinessException;
import com.bank.db.models.Account;
import com.bank.db.models.Transaction;
import com.bank.db.repositories.TransactionRepository;
import com.bank.db.utils.AccountInput;
import com.bank.db.utils.InputValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;


import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@TestPropertySource(locations = "classpath:application.yaml")
public class TransactionServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionService transactionService;

    @Mock
    InputValidator inputValidator;

    AccountInput accountInput;

    Account account;

    private String posturl;

    @Captor
    private ArgumentCaptor<HttpEntity<AccountInput>> httpEntityCaptor;



    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        accountInput = new AccountInput();
        accountInput.setAccountNumber("12345678");
        accountInput.setCustomerNumber("123456753456782");
        accountInput.setType(ACTION.DEPOSIT);
        accountInput.setAmount(2000);

        account = new Account("shamsher", 12345678,"kastrup 3300", "123456753456782","12345678",1000.0);
        ReflectionTestUtils.setField(transactionService, "url", "http://localhost:8080/account/v1/updateaccount");
        posturl ="http://localhost:8080/account/v1/updateaccount";

    }



    @Test
    public void testFetchData() {

        String customerNumber = "123456789098765";
        String accountNumber = "12345678";
        double amount = 100.0;
        AccountInput input = new AccountInput();
        input.setAccountNumber(accountNumber);
        input.setCustomerNumber(customerNumber);
        input.setAmount(amount);


        HttpHeaders headers11 = new HttpHeaders();
        headers11.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<AccountInput> expectedRequestEntity =new HttpEntity<>(input, headers11);
        when(restTemplate.postForObject(eq(posturl), httpEntityCaptor.capture(), eq(Account.class))).thenReturn(account);

        Account resultAccount = transactionService.fetchData(accountInput.getCustomerNumber(), accountInput.getAccountNumber(), accountInput.getAmount());
        assertNotNull(resultAccount);
        assertEquals(account, resultAccount);
        verify(restTemplate, times(1)).postForObject(eq(posturl), any(), eq(Account.class));
        HttpEntity<AccountInput> capturedHttpEntity = httpEntityCaptor.getValue();
        assertNotNull(capturedHttpEntity);

    }


    @Test
    public void testUpdateAccount() {
          when(restTemplate.postForObject(eq(posturl), any(HttpEntity.class), eq(Account.class))).thenReturn(account);
          Account result = transactionService.updateAccount(accountInput);
          verify(restTemplate, times(1)).postForObject(eq(posturl), any(HttpEntity.class), eq(Account.class));
          assertEquals(account, result);
    }

    @Test
    public void testUpdateAccountException() {
        when(restTemplate.postForObject(any(String.class), any(HttpEntity.class), eq(Account.class)))
                .thenThrow(new BusinessException("B00001", "Exception occured"));

        assertThrows(BusinessException.class, () -> transactionService.updateAccount(null));
    }

    @Test
    public void testUpdateTransaction() {

        accountInput.setType(ACTION.WITHDRAW);
        double amount = 100.0;
        String action = "withdraw";
        String expectedCustomerNumber = account.getCustomerNumber();
        String expectedAccountNumber = account.getAccountNumber();
        String expectedTargetOwnerName = account.getCustomerNumber();
        String expectedReference = action;
        Transaction result = transactionService.updateTransaction(account, amount, action);
         verify(transactionRepository, times(1)).save(argThat(savedTransaction -> {
            assertEquals(amount, savedTransaction.getAmount());
            assertEquals(expectedCustomerNumber, savedTransaction.getCustomerNumber());
            assertEquals(expectedAccountNumber, savedTransaction.getAccountNumber());
            assertEquals(expectedTargetOwnerName, savedTransaction.getTargetOwnerName());
            assertEquals(expectedReference, savedTransaction.getReference());
            assertNotNull(savedTransaction.getInitiationDate());
            assertNotNull(savedTransaction.getCompletionDate());


            return true;
        }));


        assertNotNull(result);
        assertEquals(amount, result.getAmount());
        assertEquals(expectedCustomerNumber, result.getCustomerNumber());
        assertEquals(expectedAccountNumber, result.getAccountNumber());
        assertEquals(expectedTargetOwnerName, result.getTargetOwnerName());
        assertEquals(expectedReference, result.getReference());
        assertNotNull(result.getInitiationDate());
        assertNotNull(result.getCompletionDate());
    }



    @Test
    public void testGetTransactionDetailsByAccNumber() {

        Transaction mockTrans = new Transaction();
        mockTrans.setAccountNumber(accountInput.getAccountNumber());
        mockTrans.setcustomerNumber(accountInput.getCustomerNumber());
        mockTrans.setCompletionDate(LocalDateTime.now());
        mockTrans.setReference("DEPOSIT");
        mockTrans.setAmount(5000.9);

        List<Transaction> mockTransactions = Collections.singletonList(mockTrans);

        when(transactionRepository.findByAccountNumber(accountInput.getAccountNumber())).thenReturn(mockTransactions);

        List<Transaction> resultTransactions = transactionService.getTransactionDetailsByAccNumber(accountInput.getAccountNumber());

        verify(transactionRepository, times(1)).findByAccountNumber(accountInput.getAccountNumber());
        assertEquals(mockTransactions, resultTransactions);
    }

    @Test
    public void testGetTransactionDetailsByCustomer() {
        Transaction mockTrans = new Transaction();
        mockTrans.setAccountNumber(accountInput.getAccountNumber());
        mockTrans.setcustomerNumber(accountInput.getCustomerNumber());
        mockTrans.setCompletionDate(LocalDateTime.now());
        mockTrans.setReference("DEPOSIT");
        mockTrans.setAmount(5000.9);
        List<Transaction> mockTransactions = Collections.singletonList(mockTrans);
        when(transactionRepository.findByCustomerNumber(accountInput.getCustomerNumber())).thenReturn(mockTransactions);
        List<Transaction> resultTransactions = transactionService.getTransactionDetailsByCustomer(accountInput.getCustomerNumber());
        assertEquals(mockTransactions, resultTransactions);
    }

    @Test
    public void testIsAmountAvailable() {
        double amount = 50.00;
        double accountBalance = 100.00;
        assertTrue(transactionService.isAmountAvailable(amount, accountBalance));
        amount = 150.00;
        assertFalse(transactionService.isAmountAvailable(amount, accountBalance));
    }


}
