package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class Transfer {
// Has six private instance variables: id, amount, fromAccountID, toAccountID, transferStatus, and transferType.
// The id variable is a Long type that represents the transfer ID. The amount variable is a BigDecimal type that represents the amount of money transferred.
// The fromAccountID variable is a Long type that represents the account ID of the account that initiated the transfer.
// The toAccountID variable is a Long type that represents the account ID of the account receiving the transfer.
// The transferStatus variable is an int type that represents the status of the transfer (i.e. pending, approved, rejected).
// The transferType variable is an int type that represents the type of transfer (i.e. request, send).
    private Long id;
    private BigDecimal amount;
    private Long fromAccountID;
    private Long toAccountID;
    private int transferStatus;
    private int transferType;

//The first constructor takes five parameters: amount, fromAccountID, toAccountID, transferStatus, and transferType.
//These parameters are used to initialize the instance variables of the Transfer object.
    public Transfer(BigDecimal amount, Long fromAccountID, Long toAccountID, int transferStatus, int transferType) {
        this.amount = amount;
        this.fromAccountID = fromAccountID;
        this.toAccountID = toAccountID;
        this.transferStatus = transferStatus;
        this.transferType = transferType;
    }
//The second constructor is a default constructor with no parameters.
    public Transfer() {
    }

    //Has six accessor methods that can be used to get and set the values of the instance variables.
    // The getId method returns the value of the id variable. The getAmount method returns the value of the amount variable.
    // The getFromAccountID method returns the value of the fromAccountID variable. The getToAccountID method returns the value of the toAccountID variable.
    // The getTransferStatus method returns the value of the transferStatus variable. The getTransferType method returns the value of the transferType variable.
    //Each of these accessor methods has a corresponding mutator method that can be used to set the value of the associated instance variable.
    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFromAccountID() {
        return fromAccountID;
    }

    public void setFromAccountID(Long fromAccountID) {
        this.fromAccountID = fromAccountID;
    }

    public Long getToAccountID() {
        return toAccountID;
    }

    public void setToAccountID(Long toAccountID) {
        this.toAccountID = toAccountID;
    }

    public int getTransferStatus() {
        return transferStatus;
    }

    public void setTransferStatus(int transferStatus) {
        this.transferStatus = transferStatus;
    }

    public int getTransferType() {
        return transferType;
    }

    public void setTransferType(int transferType) {
        this.transferType = transferType;
    }

    // Has a toString method that returns a string representation of the Transfer object.
    // This method concatenates the values of the id, amount, fromAccountID, and toAccountID variables into a string.
    @Override
    public String toString() {
        return "Transfer{" +
                "id=" + id +
                ", amount=" + amount +
                ", fromAccountID=" + fromAccountID +
                ", toAccountID=" + toAccountID +
                '}';
    }
}
