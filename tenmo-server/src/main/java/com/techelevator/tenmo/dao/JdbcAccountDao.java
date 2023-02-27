package com.techelevator.tenmo.dao;


import com.techelevator.tenmo.model.Account;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

// implements the AccountDao interface. It interacts with a database using JDBC (Java Database Connectivity) to handle the Account objects.
@Component
public class JdbcAccountDao implements AccountDao {

    private static final BigDecimal STARTING_BALANCE = new BigDecimal("1000.00");
    private JdbcTemplate jdbcTemplate;

    public JdbcAccountDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<String> listAccounts() {
        List<String> list = new ArrayList<>();
        String sql = "SELECT account_id, username, user_id FROM account JOIN tenmo_user USING (user_id);";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while(results.next()) {
            list.add("Account ID: " + results.getInt("account_id") + " " + "Username: " + results.getString("username"));
        }
        return list;
    }

    @Override
    public Account findByUserId(Long id) {
        String sql = "SELECT account_id, user_id, balance, username FROM account " +
                "JOIN tenmo_user USING (user_id) WHERE account_id = ?;";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, id);
        if (rowSet.next()){
            return mapRowToAccount(rowSet);
        }
        throw new UsernameNotFoundException("User " + id + " was not found.");
    }

    @Override
    public Account findByUserId(Account account) {
        String sql = "SELECT account_id, user_id, balance, username FROM account " +
                "JOIN tenmo_user USING (user_id) WHERE account_id = ?;";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, account.getId());
        if (rowSet.next()){
            return mapRowToAccount(rowSet);
        }
        if (rowSet.next()){
            return mapRowToAccount(rowSet);
        }
        throw new UsernameNotFoundException("User " + account.getId() + " was not found.");
    }

    @Override
    public boolean update(Account account) {
        String sql = "UPDATE account SET balance = ? WHERE account_id = ?;";
        try {
            jdbcTemplate.update(sql, account.getBalance(), account.getId());
        } catch (DataAccessException e) {
            return false;
        }
        return true;
    }

    private Account mapRowToAccount(SqlRowSet rs) {
        Account bankAccount = new Account();
        bankAccount.setId(rs.getLong("account_id"));
        bankAccount.setUserId(rs.getLong("user_id"));
        bankAccount.setBalance(rs.getBigDecimal("balance"));
        bankAccount.setUsername(rs.getString("username"));
        return bankAccount;
    }
}