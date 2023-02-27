package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class Account {

// contains fields and getters/setters for id, userId, balance, and username. The id field uniquely identifies the account,
// the userId field is the foreign key referencing the user table, the balance field represents the account's current balance,
// and the username field represents the name of the user associated with the account.
// The class also overrides the toString() method to return a string representation of the account.
    private Long id;
    private Long userId;
    private BigDecimal balance;
    private String username;

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

    @Override
    public String toString() {
        return "BankAccount{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", balance=" + balance +
                '}';
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
}
