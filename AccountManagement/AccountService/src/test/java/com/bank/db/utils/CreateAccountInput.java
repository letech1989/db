package com.bank.db.utils;


import java.util.Objects;

public class CreateAccountInput {



    private String customerName;

    private String address;

    private String mobileNumber;

    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public CreateAccountInput() {}

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    @Override
    public String toString() {
        return "CreateAccountInput{" +
                "customerName='" + customerName + '\'' +
                ", address='" + address + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreateAccountInput that = (CreateAccountInput) o;
        return Objects.equals(customerName, that.customerName) &&
                Objects.equals(address, that.address) &&
                Objects.equals(mobileNumber, that.mobileNumber) &&
                Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerName, address, mobileNumber, email);
    }
}
