package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.dao.TransferDetail;
import com.techelevator.tenmo.dao.TransferStatusDao;
import com.techelevator.tenmo.dao.TransferTypeDao;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.TransferStatus;
import com.techelevator.tenmo.model.TransferType;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@PreAuthorize("isAuthenticated()")
public class  TransferController {

    private TransferStatusDao transferStatusDao;
    private TransferDao transferDao;
    private TransferTypeDao transferTypeDao;


    public TransferController(TransferStatusDao transferStatusDAO, TransferDao transferDao, TransferTypeDao transferTypeDAO) {
        this.transferStatusDao = transferStatusDAO;
        this.transferDao = transferDao;
        this.transferTypeDao = transferTypeDAO;
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
    public TransferDetail[] listTransferDetails(@PathVariable Long id) {
        return transferDao.getTransferDetails(id);
    }

    //TRANSFER STATUS'
    @PreAuthorize("permitAll")
    @GetMapping(path = "transferstatus/")
    public TransferStatus[] getAllTransferStatus() {
        return transferStatusDao.getAllTransferStatus();
    }

    @PreAuthorize("permitAll")
    @GetMapping(path = "transferstatus/{id}")
    public TransferStatus getTansferStatusWithId(@PathVariable Long id) {
        return transferStatusDao.getTransferStatus(id);
    }

    //TRANSFER TYPES
    @PreAuthorize("permitAll")
    @GetMapping(path = "transfertype/")
    public TransferType[] getAllTransferType() {
        return transferTypeDao.getAllTranferTypes();
    }

    @PreAuthorize("permitAll")
    @GetMapping(path = "transfertype/{id}")
    public TransferType getTansferTypeWithId(@PathVariable Long id) {
        return transferTypeDao.getTransfereTypeById(id);
    }

}
