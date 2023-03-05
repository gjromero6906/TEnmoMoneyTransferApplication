package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.User;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

//This class contains a Data Access Object (DAO) implementation for User-related database operations, such as finding user IDs, account balances, creating new users, and retrieving information about users. The class includes methods for:
//Finding a user's ID by their username
//Retrieving a list of all users with accounts, excluding the user with the provided username
//Retrieving a User object with account information (ID, username, balance) by their username
//Retrieving a User object by their username, including their password hash
//Creating a new user with a given username and password hash, and creating an account with a starting balance
//Retrieving an account balance for a given user ID
@Component
public class JdbcUserDao implements UserDao {

    private static final BigDecimal STARTING_BALANCE = new BigDecimal("1000.00");
    private JdbcTemplate jdbcTemplate;

    public JdbcUserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int findIdByUsername(String username) {
        String sql = "SELECT user_id FROM tenmo_user WHERE username ILIKE ?;";
        Integer id = jdbcTemplate.queryForObject(sql, Integer.class, username);
        if (id != null) {
            return id;
        } else {
            return -1;
        }
    }


    @Override
    public List<User> findAllUsersWithAccount(String username) {
        List<User> users = new ArrayList<>();
        String sql = "SELECT tenmo_user.user_id, username, balance FROM tenmo_user JOIN account ON account.user_id = tenmo_user.user_id WHERE username != ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, username);
        while(results.next()) {
            User user = mapRowToUserAccount(results);
            users.add(user);
        }
        return users;
    }

    @Override
    public User findAccountByUsername(String username) throws UsernameNotFoundException {
        String sql = "SELECT account.user_id, username, balance FROM tenmo_user JOIN account ON account.user_id = tenmo_user.user_id WHERE username ILIKE ?;";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, username);
        if (rowSet.next()){
            return mapRowToUserAccount(rowSet);
        }
        throw new UsernameNotFoundException("User " + username + " was not found.");
    }

    @Override
    public User findByUsername(String username) throws UsernameNotFoundException {
        String sql = "SELECT user_id, username, password_hash FROM tenmo_user WHERE username ILIKE ?;";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, username);
        if (rowSet.next()){
            return mapRowToUser(rowSet);
        }
        throw new UsernameNotFoundException("User " + username + " was not found.");
    }

    @Override
    public boolean create(String username, String password) {

        // create user
        String sql = "INSERT INTO tenmo_user (username, password_hash) VALUES (?, ?) RETURNING user_id";
        String password_hash = new BCryptPasswordEncoder().encode(password);
        Integer newUserId;
        try {
            newUserId = jdbcTemplate.queryForObject(sql, Integer.class, username, password_hash);
        } catch (DataAccessException e) {
            return false;
        }

        // create account
        sql = "INSERT INTO account (user_id, balance) values(?, ?)";
        try {
            jdbcTemplate.update(sql, newUserId, STARTING_BALANCE);
        } catch (DataAccessException e) {
            return false;
        }

        return true;
    }

    @Override
    public BigDecimal getAccountBalance(Integer userId) {
        BigDecimal accountBalance = null;
        String sql = "SELECT balance FROM account JOIN tenmo_user ON tenmo_user.user_id = account.user_id WHERE account.user_id = ?";

        accountBalance = jdbcTemplate.queryForObject(sql, BigDecimal.class, userId);

        return accountBalance;
    }

    private User mapRowToUser(SqlRowSet rs) {
        User user = new User();
        user.setUserId(rs.getInt("user_id"));
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password_hash"));
        user.setActivated(true);
        user.setAuthorities("USER");
        return user;
    }

    private User mapRowToUserAccount(SqlRowSet rs) {
        User user = new User();
        user.setUserId(rs.getInt("user_id"));
        user.setUsername(rs.getString("username"));
        user.setBalance(rs.getBigDecimal("balance"));
        return user;
    }
}
