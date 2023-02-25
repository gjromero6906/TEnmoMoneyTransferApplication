package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;

public interface AccountDao {
    public Account getBalance(Long userId);

    public Account[] findAllAccounts();

    public Account withdrawAcct(Account account, Long id, Double amount);
    public Account depositAcct(Account account, Long id, Double amount);

    public Account findByUserId(Long id);

    public Account findByAccountId(Long id);
}
