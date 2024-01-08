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

    private String mobile;

    private String customerName;

    private String email;

    protected Account() {}

    public Account(String customerName, String  mobile, String address, String email, String generatecustomerNumber, String generateAccountNumber, double currentBalance) {
        this.customerNumber = generatecustomerNumber;
        this.accountNumber = generateAccountNumber;
        this.currentBalance = currentBalance;
        this.address = address;
        this.email = email;
        this.mobile = mobile;
        this.customerName = customerName;
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
    public String getmobile() {
        return mobile;
    }
    public void setmobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
