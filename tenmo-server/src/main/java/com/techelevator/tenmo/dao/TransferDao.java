package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;


import java.util.List;

// lays out the methods that any class implementing this interface must implement.
// These methods provide functionality for interacting with transfer data stored in a database.
public interface TransferDao {

    boolean create(Transfer transfer, int transferType, int transferStatus);

    List<Transfer> listBySender(Long senderId);

    Transfer getById(Long transferId);

    List<Transfer> listAllTransfers();

    boolean update(Transfer transfer);
}