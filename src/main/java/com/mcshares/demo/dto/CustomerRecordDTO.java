package com.mcshares.demo.dto;

import java.util.Date;

public class CustomerRecordDTO {

    private String id;
    private String customerName;
    private Date dateBirthOrIncorporated;
    private String customerType;
    private int numberOfShares;
    private double sharePrice;
    private double balance;

    public CustomerRecordDTO() {
    }

    public CustomerRecordDTO(String id, String customerName, Date dateBirthOrIncorporated, String customerType, int numberOfShares, double sharePrice, double balance) {
        this.id = id;
        this.customerName = customerName;
        this.dateBirthOrIncorporated = dateBirthOrIncorporated;
        this.customerType = customerType;
        this.numberOfShares = numberOfShares;
        this.sharePrice = sharePrice;
        this.balance = balance;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Date getDateBirthOrIncorporated() {
        return dateBirthOrIncorporated;
    }

    public void setDateBirthOrIncorporated(Date dateBirthOrIncorporated) {
        this.dateBirthOrIncorporated = dateBirthOrIncorporated;
    }

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    public int getNumberOfShares() {
        return numberOfShares;
    }

    public void setNumberOfShares(int numberOfShares) {
        this.numberOfShares = numberOfShares;
    }

    public double getSharePrice() {
        return sharePrice;
    }

    public void setSharePrice(double sharePrice) {
        this.sharePrice = sharePrice;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
