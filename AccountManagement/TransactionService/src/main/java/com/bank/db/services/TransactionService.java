package com.bank.db.services;

import com.bank.db.enums.ACTION;
import com.bank.db.exceptions.BusinessException;
import com.bank.db.models.Account;
import com.bank.db.models.Transaction;

import com.bank.db.repositories.TransactionRepository;
import com.bank.db.utils.AccountInput;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private RestTemplate restTemplate;


    @Value("${app.transactionurl}")
    private String url;

    public Account fetchData(String customerNumber, String accountNumber, double amount) {
        AccountInput input = new AccountInput();
        input.setAccountNumber(accountNumber);
        input.setCustomerNumber(customerNumber);
        input.setAmount(amount);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<AccountInput> requestEntity = new HttpEntity<>(input, headers);
        Account account = restTemplate.postForObject(url, requestEntity, Account.class);

        return account;
   }

    public Account updateAccount(AccountInput input) {
       try {
           HttpHeaders headers = new HttpHeaders();
           headers.setContentType(MediaType.APPLICATION_JSON);
           HttpEntity<AccountInput> requestEntity = new HttpEntity<>(input, headers);
           Account account = restTemplate.postForObject(url, requestEntity, Account.class);

           return account;
       }catch (BusinessException bx){
           throw new BusinessException(bx.getErrorCode()  ,bx.getMessage());
       }
    }

    public Transaction updateTransaction(Account account, double amount, String action){
        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setcustomerNumber(account.getCustomerNumber());
        transaction.setAccountNumber(account.getAccountNumber());
        transaction.setTargetOwnerName(account.getCustomerNumber());
        transaction.setReference(action);
        transaction.setInitiationDate(LocalDateTime.now());
        transaction.setCompletionDate(LocalDateTime.now());
        transactionRepository.save(transaction);
        return transaction;

    }

    public Transaction getAccountDetails(String customerNumber, String accountNumber, double amount, String action){

        Account account =fetchData(customerNumber, accountNumber, amount);
        return updateTransaction(account,amount,action);

    }

    public Transaction depositAmount(AccountInput input){

        Account account =updateAccount(input);
        return updateTransaction(account, input.getAmount(), input.getType().name());

    }

    public Transaction withdrawAmount(AccountInput input){
        Account account =updateAccount(input);
        return updateTransaction(account, input.getAmount(), input.getType().name());

    }

    public List<Transaction> getTransactionDetailsByAccNumber(String  accountNumber) {

        List<Transaction>  details = transactionRepository.findByAccountNumber(accountNumber);
        return details ;

    }

    public List<Transaction> getTransactionDetailsByCustomer(String  customerNumber) {

        List<Transaction>  details = transactionRepository.findByCustomerNumber(customerNumber);
        return details ;

    }

    public boolean isAmountAvailable(double amount, double accountBalance) {
        return (accountBalance - amount) > 0;
    }
}
