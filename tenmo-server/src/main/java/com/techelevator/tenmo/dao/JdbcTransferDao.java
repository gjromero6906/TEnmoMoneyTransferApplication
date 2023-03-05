package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

//This class is a Data Access Object (DAO) that handles interactions with the database related to transfers:
//It uses a JdbcTemplate object to interact with the database.
//It implements the TransferDao interface.
//It has methods to send, request, and update transfers.
//It has methods to update the account balances of the sender and receiver for a given transfer.
//It has a method to retrieve a list of transfers for a given user.
@Component
public class JdbcTransferDao implements TransferDao {

    private JdbcTemplate jdbcTemplate;
    public JdbcTransferDao(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public int sendRequestBucks(Transfer transfer) {
        if(transfer.getFromUserId() == transfer.getToUserId()) {
            System.out.println("Error: You can not send money to yourself");
            return 0;
        }
        int newId = 0;
        String sql = "INSERT INTO transfer (transfer_type_id, transfer_status_id, account_from, account_to, amount) " +
                "VALUES (?, ?, (SELECT account_id FROM account WHERE user_id = ?), (SELECT account_id FROM account WHERE user_id = ?), ?) RETURNING transfer_id";
        try{
            newId = jdbcTemplate.queryForObject(sql, Integer.class, transfer.getTransferTypeId(), transfer.getStatusId(), transfer.getFromUserId(), transfer.getToUserId(), transfer.getAmount());

        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return newId;
    }
    @Override
    public void updateSenderAccountBalance(int transferId, Transfer transfer){

        String sql = "UPDATE account SET balance = (SELECT SUM (balance - transfer.amount) " +
                "FROM account join Transfer on account.account_id = transfer.account_from Where transfer_id = ?) " +
                "WHERE user_id = ?";
        try{
            jdbcTemplate.update(sql, transferId, transfer.getFromUserId());

        }catch (DataAccessException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void updateReceiverAccountBalance(int transferId, Transfer transfer){
        String sql = "UPDATE account SET balance = (SELECT SUM (balance + transfer.amount) " +
                "FROM account join Transfer on account.account_id = transfer.account_to Where transfer_id = ?) " +
                "WHERE user_id = ?";
        try {
            jdbcTemplate.update(sql, transferId, transfer.getToUserId());

        }catch (DataAccessException e) {
            e.printStackTrace();
        }
    }
    @Override
    public List<Transfer> userTransfers(User user){

        List<Transfer> transfers = new ArrayList<>();
        String sql = "SELECT transfer_id, transfer.transfer_type_id,  transfer_type_desc, transfer.transfer_status_id," +
                " z.username AS to_username, y.username AS from_username," +
                "f.user_id AS from_user_id, transfer_status_desc, x.user_id AS to_user_id, amount " +
                "FROM transfer JOIN transfer_type ON transfer_type.transfer_type_id = transfer.transfer_type_id " +
                "JOIN transfer_status ON transfer_status.transfer_status_id = transfer.transfer_status_id " +
                "JOIN account x ON x.account_id = transfer.account_to " +
                "JOIN account f ON f.account_id = transfer.account_from " +
                "JOIN tenmo_user z ON z.user_id = x.user_id " +
                "JOIN tenmo_user y ON y.user_id = f.user_id " +
                "WHERE f.user_id = ? OR x.user_id = ?";
        try {
            SqlRowSet rowSet =  jdbcTemplate.queryForRowSet(sql, user.getUserId(), user.getUserId());

            while(rowSet.next()){
                transfers.add(mapRowToTransferWithStatusAndType(rowSet));
            }

        }catch (DataAccessException e) {
            e.printStackTrace();
        }

        return transfers;
    }

    @Override
    public void updateTransfer (Transfer transfer) {
        String sql = "UPDATE transfer SET transfer_status_id = ? WHERE transfer_id = ?";
        jdbcTemplate.update(sql, transfer.getStatusId(), transfer.getTransferId());
    }

    private Transfer mapRowToTransferWithStatusAndType(SqlRowSet rs) {
        Transfer transfer = new Transfer();
        transfer.setTransferId(rs.getInt("transfer_id"));
        transfer.setTransferTypeId(rs.getInt("transfer_type_id"));
        transfer.setTransferType(rs.getString("transfer_type_desc"));
        transfer.setStatusId(rs.getInt("transfer_status_id"));
        transfer.setTransferStatus(rs.getString("transfer_status_desc"));
        transfer.setFromUserId(rs.getInt("from_user_id"));
        transfer.setToUserId(rs.getInt("to_user_id"));
        transfer.setAccountFromUsername(rs.getString("from_username"));
        transfer.setAccountToUsername(rs.getString("to_username"));
        transfer.setAmount(rs.getBigDecimal("amount"));
        return transfer;
    }
}