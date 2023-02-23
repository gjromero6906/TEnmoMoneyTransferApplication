package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.TransferType;

public interface TransferTypeDao {
    TransferType[] getAllTranferTypes();

    TransferType getTransfereTypeById(Long id);

    TransferType[] getAllTransferTypes();

    TransferType getTransferTypeById(Long id);
}
