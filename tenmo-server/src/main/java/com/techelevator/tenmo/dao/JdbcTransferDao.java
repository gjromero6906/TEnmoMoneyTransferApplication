package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

//implements the TransferDao interface.
// The JdbcTransferDao interacts with the database to perform CRUD (Create, Read, Update, Delete) operations on transfer records.
@Component
public class JdbcTransferDao implements TransferDao {

    private JdbcTemplate jdbcTemplate;

    public JdbcTransferDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public boolean create(Transfer transfer, int transferType, int transferStatus) {
        String sql = "INSERT INTO transfer (transfer_type_id, transfer_status_id, account_from, account_to, amount) " +
                "values(?, ?, ?, ?, ?);";
        try {
            jdbcTemplate.update(sql, transferType, transferStatus, transfer.getFromAccountID(),
                    transfer.getToAccountID(), transfer.getAmount());
            return true;
        } catch (DataAccessException e) {
            return false;
        }
    }

    public List<Transfer> listBySender(Long senderId) {
        List<Transfer> transfers = new ArrayList<>();
        String sql = "SELECT transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount " +
                "FROM transfer WHERE account_from = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, senderId);
        while(results.next()) {
            Transfer transfer = mapRowToTransfer(results);
            transfers.add(transfer);
        }
        return transfers;
    }

    public Transfer getById(Long transferId) {
        String sql = "SELECT * FROM transfer WHERE transfer_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, transferId);
        return mapRowToTransfer(results);
    }

    public boolean update(Transfer transfer) {
        String sql = "UPDATE transfer SET transfer_status_id = ? WHERE transfer_id = ?;";
        try {
            jdbcTemplate.update(sql, transfer.getTransferStatus(), transfer.getId());
        } catch (DataAccessException e) {
            return false;
        }
        return true;
    }

    public List<Transfer> listAllTransfers() {
        List<Transfer> transferList = new ArrayList<>();
        String sql = "SELECT * FROM transfer";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while(results.next()) {
            transferList.add(mapRowToTransfer(results));
        }
        return transferList;
    }

    private Transfer mapRowToTransfer(SqlRowSet rowSet) {
        Transfer transfer = new Transfer();
        transfer.setId(rowSet.getLong("transfer_id"));
        transfer.setFromAccountID(rowSet.getLong("account_from"));
        transfer.setToAccountID(rowSet.getLong("account_to"));
        transfer.setAmount(rowSet.getBigDecimal("amount"));
        transfer.setTransferType(rowSet.getInt("transfer_type_id"));
        transfer.setTransferStatus(rowSet.getInt("transfer_status_id"));
        return transfer;
    }
}