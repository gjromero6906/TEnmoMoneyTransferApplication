package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;

import com.techelevator.tenmo.model.User;

import java.math.BigDecimal;
import java.util.List;

public interface AccountDao {
//The findByUserId method is used to retrieve an account based on the user id.
// There are two different versions of this method: one that takes a Long user id parameter and one that takes an Account object as a parameter.
    Account findByUserId(Long id);

    Account findByUserId(Account account);
//The update method is used to update an account's balance.
// It takes an Account object as a parameter and returns a boolean to indicate whether the update was successful.
    boolean update(Account account);
//The listAccounts method returns a list of account information as strings.
    List<String> listAccounts();
}