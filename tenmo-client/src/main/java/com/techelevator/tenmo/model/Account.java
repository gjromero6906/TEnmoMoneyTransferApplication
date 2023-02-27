package com.techelevator.tenmo.model;

// This class provides a basic model for a bank account that can be used in a larger application or system.

import java.math.BigDecimal;

public class Account {

    //It has four private instance variables:
    //id - a Long value that represents the unique identifier of the account
    //userId - a Long value that represents the unique identifier of the user that owns the account
    //balance - a BigDecimal value that represents the current balance of the account
    //username - a String value that represents the username of the user that owns the account
    private Long id;
    private Long userId;
    private BigDecimal balance;
    private String username;

    //The class has getter and setter methods for each instance variable, allowing other parts of the code to access or modify the values of these variables.
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    //The toString() method has also been overridden to provide a string representation of an Account object, which includes the values of its instance variables.
    @Override
    public String toString() {
        return "BankAccount{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", balance=" + balance +
                '}';
    }
}