package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcTransferDao implements TransferDao{

    @Autowired
    JdbcTemplate jdbcTemplate;

    public JdbcTransferDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    //Regular Transfer
    @Override
    public Transfer[] getTransfersByUserId(Long id) {
        List<Transfer> transfers = new ArrayList<>();

        String SQL = "SELECT * FROM transfer t " +
                "JOIN transfer_type tt ON tt.transfer_type_id=t.transfer_type_id " +
                "JOIN transfer_status ts ON ts.transfer_status_id=t.transfer_status_id " +
                "WHERE (account_from IN (SELECT account_id FROM account WHERE user_id = ?)) " +
                "OR (account_to IN (SELECT account_id FROM account WHERE user_id = ?));";
        SqlRowSet results = jdbcTemplate.queryForRowSet(SQL, id, id);
        while(results.next()){
            Transfer transfer = mapToRowTransfer(results);
            transfers.add(transfer);
        }
        return transfers.toArray(new Transfer[0]);
    }

    @Override
    public Transfer[] getTransferDetails(Long id){
        List<Transfer> transfers = new ArrayList<>();

        String SQL = "SELECT  t.transfer_id, tu.username as account_from , tu2.username as account_to, " +
                "tt.transfer_type_desc as transfer_type, ts.transfer_status_desc as transfer_status, t.amount as amount FROM transfer t " +
                "INNER JOIN account ac ON account_from =ac.account_id " +
                "INNER JOIN account ac2 on account_to = ac2.account_id " +
                "INNER JOIN tenmo_user tu ON tu.user_id = ac.user_id " +
                "INNER JOIN tenmo_user tu2 ON tu2.user_id = ac2.user_id " +
                "INNER JOIN transfer_type tt ON tt.transfer_type_id = t.transfer_type_id " +
                "INNER JOIN transfer_status ts on ts.transfer_status_id = t.transfer_status_id " +
                "WHERE (account_from IN (SELECT account_id FROM account WHERE user_id = ?)) " +
                "OR (account_to IN (SELECT account_id FROM account WHERE user_id = ?));";

        SqlRowSet results = jdbcTemplate.queryForRowSet(SQL, id, id);
        while(results.next()){
            Transfer transfer = mapToRowTransferDetails(results);
            transfers.add(transfer);
        }
        return transfers.toArray(new Transfer[0]);
    }

    @Override
    public Transfer addTransfer(Transfer transfer, Long statusId, Long statusTypeId, Long idFrom, Long idTo, Double amount) {
        String SQL = "INSERT INTO transfer" +
                " (transfer_type_id, " +
                "transfer_status_id, " +
                "account_from, " +
                "account_to, " +
                "amount) " +
                "VALUES (?, ?, ?, ?, ?);";
        jdbcTemplate.update(SQL, statusTypeId, statusId, idFrom, idTo, amount);
        return null;
    }

    @Override
    public void updateTransfer(Transfer transfer, Long typeId, Long statusId, Long transferId) {
        Transfer transfer1 = getTransferByTransferId(transferId);
        transfer1.setTransferId(transfer.getTransferId());
        transfer1.setAccountFrom(transfer.getAccountFrom());
        transfer1.setAccountTo(transfer.getAccountTo());
        transfer1.setTransferStatusDesc(transfer.getTransferStatusDesc());
        transfer1.setTransferTypeDesc(transfer.getTransferTypeDesc());
        transfer1.setAmount(transfer.getAmount());
        transfer1.setTransferTypeId(typeId);
        transfer1.setTransferStatusId(statusId);
        String SQL = "UPDATE transfer t SET transfer_type_id = ?, transfer_status_id= ?  WHERE transfer_id = ?;";
        jdbcTemplate.update(SQL, typeId, statusId, transferId);
    }

    @Override
    public Transfer getTransferByTransferId(Long id) {
        Transfer transfer = new Transfer();
        String SQL ="SELECT * FROM transfer t " +
                "JOIN transfer_type tt ON tt.transfer_type_id=t.transfer_type_id " +
                "JOIN transfer_status ts ON ts.transfer_status_id=t.transfer_status_id " +
                "WHERE t.transfer_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(SQL, id);
        while(results.next()){
            transfer = mapToRowTransfer(results);
        }
        return transfer;
    }

    @Override
    public Transfer[] getAllTransfers() {
        List<Transfer> transfers = new ArrayList<>();

        String SQL = "SELECT * FROM transfer t " +
                "JOIN transfer_type tt ON tt.transfer_type_id=t.transfer_type_id " +
                "JOIN transfer_status ts ON ts.transfer_status_id=t.transfer_status_id;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(SQL);
        while(results.next()){
            Transfer transfer = mapToRowTransfer(results);
            transfers.add(transfer);
        }
        return transfers.toArray(new Transfer[0]);
    }
    //Status Transfer
    @Override
    public Transfer[] getAllTransferStatus(){
        List<Transfer> t = new ArrayList<>();
        String SQL = "SELECT * from transfer_status;";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(SQL);
            while (results.next()) {
                Transfer transferStatus = mapRowToTransferStatus(results);
                t.add(transferStatus);
            }
        } catch(DataAccessException e) {
            System.out.print("Error accessing data");
        }
        return t.toArray(new Transfer[0]);

    }

    @Override
    public Transfer getTransferStatus(Long id){
        Transfer transferStatus = null;
        String SQL = "SELECT * FROM transfer_status WHERE transfer_status_id =  ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(SQL, id);
        while(results.next()){
            transferStatus = mapRowToTransferStatus(results);
        }
        return transferStatus;
    }
    //transfer type
    @Override
    public Transfer[] getAllTransferTypes(){
        List<Transfer> t = new ArrayList<>();
        String SQL = "SELECT * FROM transfer_type;";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(SQL);
            while (results.next()) {
                Transfer transferType = mapRowToTransferType(results);
                t.add(transferType);
            }
        } catch(DataAccessException e) {
            System.out.print("Error accessing data");
        }
        return t.toArray(new Transfer[0]);

    }

    @Override
    public Transfer getTransferTypeById(Long id) {
        Transfer transferType = null;
        String SQL = "SELECT * FROM transfer_type WHERE transfer_type_id =  ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(SQL, id);
        while(results.next()){
            transferType = mapRowToTransferType(results);
        }
        return transferType;
    }

    //Regular transfer
    private Transfer mapToRowTransferDetails(SqlRowSet results){
        Transfer transferDetail = new Transfer();
        transferDetail.setTransferId(results.getLong("transfer_id"));
        transferDetail.setAccountFrom(results.getLong("account_from"));
        transferDetail.setAccountTo(results.getLong("account_to"));
        transferDetail.setTransferTypeDesc(results.getString("transfer_type"));
        transferDetail.setTransferStatusDesc(results.getString("transfer_status"));
        transferDetail.setAmount(results.getDouble("amount"));
        return transferDetail;
    }

    private Transfer mapToRowTransfer(SqlRowSet results){
        Transfer transfer = new Transfer();
        transfer.setTransferId(results.getLong("transfer_id"));
        transfer.setTransferTypeId(results.getLong("transfer_type_id"));
        transfer.setTransferTypeDesc(results.getString("transfer_type_desc"));
        transfer.setTransferStatusId(results.getLong("transfer_status_id"));
        transfer.setTransferStatusDesc(results.getString("transfer_status_desc"));
        transfer.setAccountFrom(results.getLong("account_from"));
        transfer.setAccountTo(results.getLong("account_to"));
        transfer.setAmount(results.getDouble("amount"));
        return transfer;
    }
    //Status transfer
    private Transfer mapRowToTransferStatus(SqlRowSet results){
        Transfer transferStatus = new Transfer();
        transferStatus.setTransferStatusId(results.getLong("transfer_status_id"));
        transferStatus.setTransferStatusDesc(results.getString("transfer_status_desc"));
        return transferStatus;
    }
    //Transfer Type
    private Transfer mapRowToTransferType(SqlRowSet results){
        Transfer transferType = new Transfer();
        transferType.setTransferTypeId(results.getLong("transfer_type_id"));
        transferType.setTransferTypeDesc(results.getString("transfer_type_desc"));
        return transferType;
    }
}
