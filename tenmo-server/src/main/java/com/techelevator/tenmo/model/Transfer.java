package com.techelevator.tenmo.model;

/* The Transfer class has six private instance variables: id, amount, fromAccountID, toAccountID, transferStatus, and transferType.
The id variable is a Long type that represents the transfer ID. The amount variable is a BigDecimal type that represents the amount of money transferred.
The fromAccountID variable is a Long type that represents the account ID of the account that initiated the transfer.
The toAccountID variable is a Long type that represents the account ID of the account receiving the transfer.
The transferStatus variable is an int type that represents the status of the transfer (i.e. pending, approved, rejected).
The transferType variable is an int type that represents the type of transfer (i.e. request, send).*/

import java.math.BigDecimal;

public class Transfer {

    private int transferId;
    private int transferTypeId;
    private String transferType;
    private int statusId;
    private String transferStatus;
    private int fromUserId;
    private int toUserId;
    private BigDecimal amount;
    private String accountFromUsername;
    private String accountToUsername;

    public Transfer() {
    }

    public String getAccountFromUsername() {
        return accountFromUsername;
    }

    public void setAccountFromUsername(String accountFromUsername) {
        this.accountFromUsername = accountFromUsername;
    }

    public String getAccountToUsername() {
        return accountToUsername;
    }

    public void setAccountToUsername(String accountToUsername) {
        this.accountToUsername = accountToUsername;
    }

    public int getTransferId() {
        return transferId;
    }

    public void setTransferId(int transferId) {
        this.transferId = transferId;
    }

    public int getTransferTypeId() {
        return transferTypeId;
    }

    public void setTransferTypeId(int transferTypeId) {
        this.transferTypeId = transferTypeId;
    }

    public String getTransferType() {
        return transferType;
    }

    public void setTransferType(String transferType) {
        this.transferType = transferType;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public String getTransferStatus() {
        return transferStatus;
    }

    public void setTransferStatus(String transferStatus) {
        this.transferStatus = transferStatus;
    }

    public int getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(int fromUserId) {
        this.fromUserId = fromUserId;
    }

    public int getToUserId() {
        return toUserId;
    }

    public void setToUserId(int toUserId) {
        this.toUserId = toUserId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}



