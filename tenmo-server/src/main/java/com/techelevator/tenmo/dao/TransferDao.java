package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;


import java.util.List;

// lays out the methods that any class implementing this interface must implement.
// These methods provide functionality for interacting with transfer data stored in a database.
public interface TransferDao {

    int sendRequestBucks(Transfer transfer);

    void updateSenderAccountBalance(int transferId, Transfer transfer);

    void updateReceiverAccountBalance(int transferId, Transfer transfer);

    void updateTransfer (Transfer transfer);

    List<Transfer> userTransfers(User user);

}