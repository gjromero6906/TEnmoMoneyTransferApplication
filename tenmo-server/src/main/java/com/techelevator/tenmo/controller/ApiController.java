package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;

import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/account/")
@PreAuthorize("isAuthenticated()")
public class ApiController {

    RestTemplate restTemplate = new RestTemplate();
    private AccountDao accountDao;

    public ApiController(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    @GetMapping("")
    public List<String> listAccounts() {
        return accountDao.listAccounts();
    }

    @GetMapping("{id}")
    public Account get(@PathVariable Long id) {
        return accountDao.findByUserId(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("{id}")
    public Account update(@RequestBody Account account, @PathVariable Long id) {
        accountDao.update(account);
        return account;
    }


    @RestController
    @PreAuthorize("isAuthenticated()")
    public class TransferController {

        RestTemplate restTemplate = new RestTemplate();
        private TransferDao transferDao;

        public TransferController(TransferDao transferDao) {
            this.transferDao = transferDao;
        }

        @GetMapping("/transfer/{id}")
        public Transfer get(@PathVariable Long id) {
            return transferDao.getById(id);
        }

        @ResponseStatus(HttpStatus.OK)
        @PutMapping("/transfer/{id}")
        public boolean update(@RequestBody Transfer transfer) {
            return transferDao.update(transfer);
        }

        @GetMapping("/transfers/{id}")
        public List<Transfer> getTransfersBySender(@PathVariable Long id) {
            return transferDao.listBySender(id);
        }

        @GetMapping("/transfers")
        public List<Transfer> getAllTransfers() {
            return transferDao.listAllTransfers();
        }

        @ResponseStatus(HttpStatus.CREATED)
        @PutMapping("/transfer")
        public void create(@RequestBody Transfer transfer,
                           @RequestParam(name = "type", required = false, defaultValue = "2") int transferType,
                           @RequestParam(name = "status", required = false, defaultValue = "2") int transferStatus) {
            transferDao.create(transfer, transferType, transferStatus);
        }
    }
}