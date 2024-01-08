package com.bank.db.controller;


import com.bank.db.enums.ErrorCode;
import com.bank.db.exceptions.InputValidationException;
import com.bank.db.models.Transaction;
import com.bank.db.utils.*;
import com.bank.db.services.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("transaction/v1")
public class TransactionController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionController.class);

    @Autowired
    private TransactionService transactionService;

    @Autowired
    InputValidator inputValidator;


    @PostMapping(value = "/deposit",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deposit(@RequestBody AccountInput depositInput) {

        if (inputValidator.isSearchCriteriaValid(depositInput)) {
            Transaction transaction = transactionService.depositAmount(depositInput);
            return new ResponseEntity<>(transaction, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(ErrorCode.INVALID_INPUT.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/withdraw",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> withdraw( @RequestBody AccountInput withdrawInput)  {
         if (inputValidator.isSearchCriteriaValid(withdrawInput)) {
             Transaction transaction = transactionService.withdrawAmount(withdrawInput);
             return new ResponseEntity<>("Amount withdraw successfully", HttpStatus.OK);
         } else {
             return new ResponseEntity<>(ErrorCode.INVALID_INPUT.getMessage(), HttpStatus.BAD_REQUEST);
         }
    }



    @GetMapping(value = "/transactiondetails/{accountNumber}",   produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getTransactionDetails(@PathVariable String accountNumber) {
        LOGGER.debug("Get transaction details for account number: {}", accountNumber);

        if (!inputValidator.isAccountNoValid(accountNumber)) {
            return new ResponseEntity<>(ErrorCode.INVALID_ACCOUNT_NUMBER, HttpStatus.BAD_REQUEST);
        }

        List<Transaction> transList = transactionService.getTransactionDetailsByAccNumber(accountNumber);


        if (transList != null) {
            return new ResponseEntity<>(transList, HttpStatus.OK);

        } else {
            return  new ResponseEntity<>("No Data found ", HttpStatus.NOT_FOUND);
        }
    }

 }

