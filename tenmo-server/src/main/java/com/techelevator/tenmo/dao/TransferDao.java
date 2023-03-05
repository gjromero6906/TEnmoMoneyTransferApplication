package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;

import java.util.List;

//This is an interface defining methods for transferring funds between two users.
//The interface includes methods for sending a request for funds, updating the sender and receiver account balances, updating transfer status, and retrieving a list of transfers for a user.
public interface TransferDao {

    int sendRequestBucks(Transfer transfer);

    void updateSenderAccountBalance(int transferId, Transfer transfer);

    void updateReceiverAccountBalance(int transferId, Transfer transfer);

    void updateTransfer (Transfer transfer);

    List<Transfer> userTransfers(User user);

}