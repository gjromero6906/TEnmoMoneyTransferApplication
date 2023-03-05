package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

//This class is the TransferController, which contains methods for handling HTTP requests related to transfers, such as sending and requesting money, updating transfers, and returning transfer history. Some of the main features of this class include:
//Use of the TransferDao and UserDao interfaces to interact with the database and retrieve information about transfers and users.
//Several constants are defined, including the transfer status codes and transfer types.
//Annotations such as @RestController and @PreAuthorize are used to specify the behavior of this class.
//Methods such as sendBucks(), userTransfers(), requestBucks(), and updateTransfer() handle different types of transfer-related HTTP requests. These methods make use of the Transfer and User models to manipulate the database and retrieve information.
@RestController
@PreAuthorize("isAuthenticated()")
public class TransferController {
    private TransferDao transferDao;
    private UserDao userDao;
    private final static int PENDING_STATUS = 1;
    private final static int APPROVED_STATUS = 2;
    private final static int REJECTED_STATUS = 3;
    private final static int TYPE_SEND = 2;
    private final static int TYPE_REQUEST = 1;

    public TransferController(UserDao userDao, TransferDao transferDao) {
        this.userDao = userDao;
        this.transferDao = transferDao;
    }

    @RequestMapping(path = "/send/", method = RequestMethod.POST)
    public void sendBucks(@RequestBody Transfer transfer) {
        int transferId = transferDao.sendRequestBucks(transfer);
        transferDao.updateSenderAccountBalance(transferId, transfer);
        transferDao.updateReceiverAccountBalance(transferId, transfer);
    }

    @RequestMapping(path = "/transfer/history/", method = RequestMethod.GET)
    public List<Transfer> userTransfers(Principal principal) {
        String username = principal.getName();
        User user = userDao.findAccountByUsername(username);
        return transferDao.userTransfers(user);
    }

    @RequestMapping(path = "/request/", method = RequestMethod.POST)
    public void requestBucks(@RequestBody Transfer transfer) {
        transferDao.sendRequestBucks(transfer);
    }

    @RequestMapping(path = "/transfer/", method = RequestMethod.PUT)
    public void updateTransfer(@RequestBody Transfer transfer) {
        transferDao.updateTransfer(transfer);
        User payingUser = userDao.findAccountByUsername(transfer.getAccountFromUsername());
        if (transfer.getStatusId() == APPROVED_STATUS && transfer.getAmount().compareTo(payingUser.getBalance()) <= 0) {
            transferDao.updateSenderAccountBalance(transfer.getTransferId(), transfer);
            transferDao.updateReceiverAccountBalance(transfer.getTransferId(), transfer);
        } else {
            transfer.setStatusId(PENDING_STATUS);
        }
    }
}
