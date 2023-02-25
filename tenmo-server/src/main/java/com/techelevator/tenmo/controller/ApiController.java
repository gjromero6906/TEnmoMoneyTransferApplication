package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")
public class ApiController {
    private TransferDao transferDAO;
    private AccountDao accountDAO;
    private UserDao userDAO;

    public ApiController(AccountDao accountDAO, UserDao userDAO, TransferDao transferDAO) {
        this.accountDAO = accountDAO;
        this.userDAO = userDAO;
        this.transferDAO =transferDAO;

    }
    //TransferController
    @PreAuthorize("hasRole('USER')")
    @GetMapping(path = "transfers/{id}")
    public Transfer[] listTransferById(@PathVariable Long id) {
        return transferDAO.getTransfersByUserId(id);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping(path = "trans/{id}")
    public Transfer listTransferByTransferId(@PathVariable Long id) {
        return transferDAO.getTransferByTransferId(id);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping(path = "transfers/")
    public Transfer[] listAllTransfers() {
        return transferDAO.getAllTransfers();
    }


    @PreAuthorize("hasRole('USER')")
    @PutMapping(path = "transfer/update/{typeId}/{statusId}/{transferId}")
    public void update(@Valid @RequestBody Transfer transfer,
                       @PathVariable Long typeId,
                       @PathVariable Long statusId,
                       @PathVariable Long transferId) {
        transferDAO.updateTransfer(transfer, typeId, statusId, transferId);
    }

    @PreAuthorize("permitAll")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "add/transfer")
    public Transfer addToTransfer(@Valid @RequestBody Transfer transfer) {
        return transferDAO.addTransfer(transfer, transfer.getTransferStatusId(), transfer.getTransferStatusId(), transfer.getAccountFrom(), transfer.getAccountTo(), transfer.getAmount());
    }

    //TRANSFER DETAILS
    @PreAuthorize("hasRole('USER')")
    @GetMapping(path = "transferdetails/{id}")
    public Transfer[] listTransferDetails(@PathVariable Long id) {
        return transferDAO.getTransferDetails(id);
    }

    //TRANSFER STATUS'
    @PreAuthorize("permitAll")
    @GetMapping(path = "transferstatus/")
    public Transfer[] getAllTransferStatus() {
        return transferDAO.getAllTransferStatus();
    }

    @PreAuthorize("permitAll")
    @GetMapping(path = "transferstatus/{id}")
    public Transfer getTansferStatusWithId(@PathVariable Long id) {
        return transferDAO.getTransferStatus(id);
    }

    //TRANSFER TYPES
    @PreAuthorize("permitAll")
    @GetMapping(path = "transfertype/")
    public Transfer[] getAllTransferType() {
        return transferDAO.getAllTransferTypes();
    }

    @PreAuthorize("permitAll")
    @GetMapping(path = "transfertype/{id}")
    public Transfer getTransferTypeWithId(@PathVariable Long id) {
        return transferDAO.getTransferTypeById(id);
    }

    //userController
    @PreAuthorize("permitAll")
    @RequestMapping(path = "user/{id}", method = RequestMethod.GET)
    public User listUserById(@PathVariable Long id) {
        return userDAO.findById(id);
    }

    @PreAuthorize("permitAll")
    @RequestMapping(path = "user/", method = RequestMethod.GET)
    public List<User> listAllUsers() {
        return userDAO.findAll();
    }
    //AccountController
    @PreAuthorize("permitAll")
    @GetMapping(path = "accounts/")
    public Account[] findAllAccounts() {
        return accountDAO.findAllAccounts();
    }

    @PreAuthorize("permitAll")
    @GetMapping(path = "account/user/{id}")
    public Account getByUserId(@PathVariable Long id) {
        return accountDAO.findByUserId(id);
    }

    @PreAuthorize("permitAll")
    @GetMapping(path = "account/{id}")
    public Account getByAccountId(@PathVariable Long id) {
        return accountDAO.findByAccountId(id);
    }

    @PreAuthorize("permitAll")
    @PutMapping(path = "withdraw/{id}/{amount}")
    public Account withdraw(@Valid @RequestBody Account account, @PathVariable Long id, @PathVariable Double amount) {

        return accountDAO.withdrawAcct(account, id, amount);
    }

    @PreAuthorize("permitAll")
    @PutMapping(path = "deposit/{id}/{amount}")
    public Account deposit(@Valid @RequestBody Account account, @PathVariable Long id, @PathVariable Double amount) {
        return accountDAO.depositAcct(account, id, amount);
    }

    @PreAuthorize("permitAll")
    @RequestMapping(path = "balance/{id}", method = RequestMethod.GET)
    public Account getBalance(@PathVariable Long id) {
        return accountDAO.getBalance(id);
    }
}
