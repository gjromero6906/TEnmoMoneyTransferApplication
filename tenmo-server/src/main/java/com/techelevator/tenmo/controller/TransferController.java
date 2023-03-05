package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;


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

    //@ApiOperation("Creates an approved transfer and then updates account balances of the sender and receiver of TE Bucks.")
    @RequestMapping(path = "/send/", method = RequestMethod.POST)
    public void sendBucks(@RequestBody Transfer transfer) {
        int transferId = transferDao.sendRequestBucks(transfer);
        transferDao.updateSenderAccountBalance(transferId, transfer);
        transferDao.updateReceiverAccountBalance(transferId, transfer);
    }

  //  @ApiOperation("Returns a list of transfers the signed in user has on record")
    @RequestMapping(path = "/transfer/history/", method = RequestMethod.GET)
    public List<Transfer> userTransfers(Principal principal) {
        String username = principal.getName();
        User user = userDao.findAccountByUsername(username);
        return transferDao.userTransfers(user);
    }

   // @ApiOperation("Creates a transfer Request with signed in user as the recipient ")
    @RequestMapping(path = "/request/", method = RequestMethod.POST)
    public void requestBucks(@RequestBody Transfer transfer) {
        transferDao.sendRequestBucks(transfer);
    }

  //  @ApiOperation("Updates the transfers for a request transfer that has been approved.")
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
