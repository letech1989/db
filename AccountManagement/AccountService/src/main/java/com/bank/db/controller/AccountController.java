package com.bank.db.controller;

import com.bank.db.enums.ErrorCode;
import com.bank.db.exceptions.BusinessException;
import com.bank.db.models.Account;
import com.bank.db.services.AccountService;
import com.bank.db.utils.AccountInput;
import com.bank.db.utils.CreateAccountInput;
import com.bank.db.utils.InputValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;



@RestController
@RequestMapping("account/v1")
public class AccountController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    private AccountService accountService;

    @Autowired
    private InputValidator inputValidator;

    @GetMapping(value = "/checkbalance/{accountNumber}")
    public ResponseEntity<?> checkAccountBalance(@PathVariable String accountNumber) {

        if (inputValidator.isAccountNoValid(accountNumber)) {
            Account account = accountService.getAccount(accountNumber);
            if(account == null) {
                throw new BusinessException(ErrorCode.NOT_FOUND.getCode(), ErrorCode.NOT_FOUND.getMessage());
            }
            return new ResponseEntity<>(account, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(ErrorCode.INVALID_INPUT.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }


    @PutMapping(value = "/accounts", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createAccount( @RequestBody CreateAccountInput createAccountInput) {

        if (inputValidator.isCreateAccountCriteriaValid(createAccountInput)) {
            Account account = accountService.createAccount(createAccountInput.getCustomerName(), createAccountInput.getMobileNumber(), createAccountInput.getAddress(), createAccountInput.getEmail());
            return new ResponseEntity<>("Account created  successfully", HttpStatus.CREATED);

        } else {
            return new ResponseEntity<>(ErrorCode.INVALID_INPUT.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping(value = "/updateaccount", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateAccount( @RequestBody AccountInput accountinput) {

        if (inputValidator.isSearchCriteriaValid(accountinput)) {
            Account account = accountService.updateAccount(accountinput, accountinput.getType().name());
            if(account==null)
                throw new BusinessException(ErrorCode.NOT_FOUND.getCode(), ErrorCode.NOT_FOUND.getMessage() );
             return new ResponseEntity<>("Account updated succesfully", HttpStatus.OK);

        } else {
            return new ResponseEntity<>(ErrorCode.INVALID_INPUT.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}