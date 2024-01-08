package com.bank.db.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "account", schema = "db_bank")
public class Account {

    @Id @GeneratedValue
    private long id;

    private String customerNumber;

    private String accountNumber;

    private double currentBalance;

    private String address;

    private int mobile;



    private String customerName;



    private String email;

   // private transient List<com.bank.db.models.Transaction> transactions;

    protected Account() {}

    public Account(String customerName, int  mobile, String address, String generatecustomerNumber, String generateAccountNumber, double currentBalance) {
        this.customerNumber = generatecustomerNumber;
        this.accountNumber = generateAccountNumber;
        this.currentBalance = currentBalance;
        this.address = address;
        this.mobile = mobile;
        this.customerName = customerName;
    }

    public Account(String address, int  mobile, String generatecustomerNumber, String generateAccountNumber, double currentBalance) {
        this.customerNumber = generatecustomerNumber;
        this.accountNumber = generateAccountNumber;
        this.currentBalance = currentBalance;
        this.address = address;
        this.mobile = mobile;
    }
    public Account(long id, String customerNumber, String accountNumber, double currentBalance, String address, int mobile) {
        this.id = id;
        this.customerNumber = customerNumber;
        this.accountNumber = accountNumber;
        this.currentBalance = currentBalance;
        this.address = address;
        this.mobile = mobile;
    }


    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getCustomerNumber() {
        return customerNumber;
    }
    public void setCustomerNumber(String customerNumber) {
        this.customerNumber = customerNumber;
    }
    public String getAccountNumber() {
        return accountNumber;
    }
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
    public double getCurrentBalance() {
        return currentBalance;
    }
    public void setCurrentBalance(double currentBalance) {
        this.currentBalance = currentBalance;
    }
    public int getmobile() {
        return mobile;
    }
    public void setmobile(int mobile) {
        this.mobile = mobile;
    }
    public String getaddress() {
        return address;
    }
    public void setaddress(String address) {
        this.address = address;
    }
//    public List<com.bank.db.models.Transaction> getTransactions() {
//        return transactions;
//    }
//    public void setTransactions(List<com.bank.db.models.Transaction> transactions) {
//        this.transactions = transactions;
//    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", customerNumber='" + customerNumber + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                ", currentBalance=" + currentBalance +
                ", address='" + address + '\'' +
                ", mobile='" + mobile + '\'' +
                '}';
    }
}
