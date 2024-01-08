package com.bank.db.services;

import com.bank.db.enums.ErrorCode;
import com.bank.db.exceptions.BusinessException;
import com.bank.db.models.Account;
import com.bank.db.repositories.AccountRepository;
import com.bank.db.utils.AccountInput;
import com.bank.db.utils.CodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    private  AccountRepository accountRepository;

    public Account getAccount(String customerNumber, String accountNumber) {
        return (customerNumber != null && accountNumber != null) ?
                accountRepository.findByCustomerNumberAndAccountNumber(customerNumber, accountNumber).orElse(null) :
                (accountNumber != null) ?
                        accountRepository.findByAccountNumber(accountNumber).orElse(null) :
                        null;
    }



    public Account getAccount(String accountNumber) {
        Optional<Account> account = accountRepository.findByAccountNumber(accountNumber);
        return account.orElse(null);
    }

    public Account createAccount(String customerName, String mobileNumber, String address, String email) {
        CodeGenerator codeGenerator = new CodeGenerator();
        Account newAccount = new Account(customerName, mobileNumber, address, email, codeGenerator.generateCustomerNumber(), codeGenerator.generateAccountNumber(), 0.00);
        try {
          return  accountRepository.save(newAccount);
        }catch(Exception nn){
            throw new BusinessException("B00000", "Account creation failed");
        }

    }

    public Account updateAccount(AccountInput input, String action) {
        Account acc = getAccount(input.getCustomerNumber(), input.getAccountNumber());
        if("DEPOSIT".equalsIgnoreCase(action)){
            acc.setCurrentBalance(acc.getCurrentBalance()+input.getAmount());
        }else if("WITHDRAW".equalsIgnoreCase(action)){
            if(input.getAmount() > acc.getCurrentBalance()){
                throw new BusinessException(ErrorCode.INSUFFICIENT_FUNDS.getCode(), ErrorCode.INSUFFICIENT_FUNDS.getMessage());
            }
            acc.setCurrentBalance(acc.getCurrentBalance()-input.getAmount());
        }
        try {
            Account account =  accountRepository.save(acc);
            return account;
        }catch (Exception ex){
            throw  new BusinessException("B00000","Error while updating  balance");
        }
    }
}
