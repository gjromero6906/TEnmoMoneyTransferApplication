package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

public interface TransferDao {
    //For the type of transfer

    Transfer getTransferTypeById(Long id);

    Transfer[] getAllTransferTypes();

    // all related to Status
    Transfer[] getAllTransferStatus();

    Transfer getTransferStatus(Long id);
    //for a regular transfer
    Transfer[] getAllTransfers();

    Transfer[] getTransfersByUserId(Long id);

    void updateTransfer(Transfer transfer, Long typeId, Long statusId, Long transferId);

    Transfer getTransferByTransferId(Long id);

    Transfer[] getTransferDetails(Long id);

    Transfer addTransfer(Transfer transfer, Long statusId, Long statusTypeId, Long idFrom, Long idTo, Double amount);

}
