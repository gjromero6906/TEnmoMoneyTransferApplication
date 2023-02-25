package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@PreAuthorize("isAuthenticated()")
public class  TransferController {

    private TransferDao transferDao;


    public TransferController(TransferDao transferDao) {
        this.transferDao = transferDao;
    }


    @PreAuthorize("hasRole('USER')")
    @GetMapping(path = "transfers/{id}")
    public Transfer[] listTransferById(@PathVariable Long id) {
        return transferDao.getTransfersByUserId(id);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping(path = "trans/{id}")
    public Transfer listTransferByTransferId(@PathVariable Long id) {
        return transferDao.getTransferByTransferId(id);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping(path = "transfers/")
    public Transfer[] listAllTransfers() {
        return transferDao.getAllTransfers();
    }


    @PreAuthorize("hasRole('USER')")
    @PutMapping(path = "transfer/update/{typeId}/{statusId}/{transferId}")
    public void update(@Valid @RequestBody Transfer transfer,
                       @PathVariable Long typeId,
                       @PathVariable Long statusId,
                       @PathVariable Long transferId) {
        transferDao.updateTransfer(transfer, typeId, statusId, transferId);
    }

    @PreAuthorize("permitAll")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "add/transfer")
    public Transfer addToTransfer(@Valid @RequestBody Transfer transfer) {
        return transferDao.addTransfer(transfer, transfer.getTransferStatusId(), transfer.getTransferStatusId(), transfer.getAccountFrom(), transfer.getAccountTo(), transfer.getAmount());
    }

    //TRANSFER DETAILS
    @PreAuthorize("hasRole('USER')")
    @GetMapping(path = "transferdetails/{id}")
    public Transfer[] listTransferDetails(@PathVariable Long id) {
        return transferDao.getTransferDetails(id);
    }

    //TRANSFER STATUS'
    @PreAuthorize("permitAll")
    @GetMapping(path = "transferstatus/")
    public Transfer[] getAllTransferStatus() {
        return transferDao.getAllTransferStatus();
    }

    @PreAuthorize("permitAll")
    @GetMapping(path = "transferstatus/{id}")
    public Transfer getTansferStatusWithId(@PathVariable Long id) {
        return transferDao.getTransferStatus(id);
    }

    //TRANSFER TYPES
    @PreAuthorize("permitAll")
    @GetMapping(path = "transfertype/")
    public Transfer[] getAllTransferType() {
        return transferDao.getAllTransferTypes();
    }

    @PreAuthorize("permitAll")
    @GetMapping(path = "transfertype/{id}")
    public Transfer getTransferTypeWithId(@PathVariable Long id) {
        return transferDao.getTransferTypeById(id);
    }

}
