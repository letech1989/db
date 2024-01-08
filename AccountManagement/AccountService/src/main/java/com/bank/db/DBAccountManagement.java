package com.bank.db;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.bank.db","com.bank.db.services","com.bank.db.controller","com.bank.db.exception","com.bank.db.config","com.bank.db.models","com.bank.db.repositories","com.bank.db.utils"})
public class DBAccountManagement {
    public static void main(String[] args) {
        SpringApplication.run(DBAccountManagement.class, args);
    }

}
