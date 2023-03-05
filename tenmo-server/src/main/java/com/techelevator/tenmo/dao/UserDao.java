package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.User;

import java.math.BigDecimal;
import java.util.List;

//This is an interface for the User DAO (Data Access Object) which provides methods to interact with the user data in the database. Some of the methods are:
//findByUsername(String username) - returns a User object based on the username
//findAllUsersWithAccount(String username) - returns a list of all users that have an account, except the one with the given username
//findAccountByUsername(String username) - returns a User object with the account information (balance) based on the username
//findIdByUsername(String username) - returns the ID of the user based on the username
//create(String username, String password) - creates a new user with the given username and password
//getAccountBalance(Integer userId) - returns the account balance of the user with the given ID.
public interface UserDao {

//    List<User> findAll(String username);

    User findByUsername(String username);

    List<User> findAllUsersWithAccount(String username);

    User findAccountByUsername(String username);

    int findIdByUsername(String username);

    boolean create(String username, String password);

    BigDecimal getAccountBalance (Integer userId);
}
