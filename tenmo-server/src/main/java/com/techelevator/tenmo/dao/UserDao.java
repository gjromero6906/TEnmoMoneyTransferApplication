package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.User;

import java.math.BigDecimal;
import java.util.List;

public interface UserDao {

//    List<User> findAll(String username);

    User findByUsername(String username);

    List<User> findAllUsersWithAccount(String username);

    User findAccountByUsername(String username);

    int findIdByUsername(String username);

    boolean create(String username, String password);

    BigDecimal getAccountBalance (Integer userId);
}
