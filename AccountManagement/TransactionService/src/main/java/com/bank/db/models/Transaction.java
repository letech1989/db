package com.bank.db.models;

import javax.persistence.*;
import java.time.LocalDateTime;

// TODO Add support for Bank charges, currency conversion, setup repeat payment/ standing order
@Entity
@Table(name = "transaction", schema = "db_bank")

@SequenceGenerator(name = "transaction_seq", sequenceName = "transaction_sequence", schema = "db_bank", initialValue = 5)
public class Transaction {

    @Id

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transaction_seq")
    private long id;

    private String customerNumber;

    private String accountNumber;

    private String targetOwnerName;

    private double amount;

    private LocalDateTime initiationDate;

    private LocalDateTime completionDate;

    private String reference;

    private Double latitude;

    private Double longitude;

    public Transaction() {}

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public String getCustomerNumber() {
        return customerNumber;
    }
    public void setcustomerNumber(String customerNumber) {
        this.customerNumber = customerNumber;
    }
    public String getAccountNumber() {
        return accountNumber;
    }
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
    public String getTargetOwnerName() {
        return targetOwnerName;
    }
    public void setTargetOwnerName(String targetOwnerName) {
        this.targetOwnerName = targetOwnerName;
    }
    public double getAmount() {
        return amount;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }
    public LocalDateTime getInitiationDate() {
        return initiationDate;
    }
    public void setInitiationDate(LocalDateTime initiationDate) {
        this.initiationDate = initiationDate;
    }
    public LocalDateTime getCompletionDate() {
        return completionDate;
    }
    public void setCompletionDate(LocalDateTime completionDate) {
        this.completionDate = completionDate;
    }
    public String getReference() {
        return reference;
    }
    public void setReference(String reference) {
        this.reference = reference;
    }


    @Override
    public String toString() {
        return "Transaction{" +
                "customerNumber=" + customerNumber +
                ", accountNumber=" + accountNumber +
                ", targetOwnerName='" + targetOwnerName + '\'' +
                ", amount=" + amount +
                ", initiationDate=" + initiationDate +
                ", completionDate=" + completionDate +
                ", reference='" + reference + '\'' +
                '}';
    }
}
