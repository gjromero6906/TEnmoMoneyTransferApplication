package com.techelevator.tenmo.model;

/* This class represents a financial transfer between two parties. It has several private instance variables, each representing a different aspect of the transfer, such as transfer ID,
   transfer type ID, transfer status ID, the ID of the sender, the ID of the recipient, and the amount being transferred. The class has a default constructor, and also provides public
   getter and setter methods for each of its private instance variables. This allows other parts of the program to access and modify the transfer's properties as needed. In addition,
   the 'Transfer' class overrides the 'toString' method, which returns a string representation of the transfer. The returned string includes information about the sender, the amount being
   transferred, and the recipient.*/

import com.techelevator.tenmo.services.AccountService;
import com.techelevator.tenmo.services.ConsoleService;
import com.techelevator.tenmo.services.TransferService;

public class Transfer {


    private long transfer_id;

    private long transfer_type_id;

    private long transfer_status_id;

    private long transfer_from;

    private long transfer_to;

    private double amount;


    private UserService userService;

    public Transfer() {
        this.userService = userService;
    }

    public void setTransferStatusId(long l) {
    }

    public void setTransferTypeId(long l) {
    }

    public void setAccountTo(int recipientId) {
    }

    public long getTransfer_id() {
        return transfer_id;
    }

    public void setTransfer_id(long transfer_id) {
        this.transfer_id = transfer_id;
    }

    public long getTransfer_type_id() {
        return transfer_type_id;
    }

    public void setTransfer_type_id(long transfer_type_id) {
        this.transfer_type_id = transfer_type_id;
    }

    public long getTransfer_status_id() {
        return transfer_status_id;
    }

    public void setTransfer_status_id(long transfer_status_id) {
        this.transfer_status_id = transfer_status_id;
    }

    public long getTransfer_from() {
        return transfer_from;
    }

    public void setTransfer_from(long transfer_from) {
        this.transfer_from = transfer_from;
    }

    public long getTransfer_to() {
        return transfer_to;
    }

    public void setTransfer_to(long transfer_to) {
        this.transfer_to = transfer_to;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return "Transfer From ID: " + getTransfer_from() + " in the amount of: $" + getAmount() + " Was sent to: " + getTransfer_to();
    }

    public void sendBucks() {
        // show list of users to select for sending money
        User[] users = userService.listUsers();
        System.out.println("-------------------------------------------");
        System.out.println("Users");
        System.out.println("ID          Name");
        System.out.println("-------------------------------------------");
        for (User user : users) {
            System.out.println(user.getId() + "          " + user.getUsername());
        }
        System.out.println("0          Exit");
        System.out.println("-------------------------------------------");

        // prompt user to select recipient
        int recipientId = ConsoleService.getUserInputInteger("Enter ID of user you are sending to (0 to cancel): ");
        if (recipientId == 0) {
            return;
        }

        // prompt user to enter amount to send
        double amount = ConsoleService.getUserInputDouble("Enter amount: ");

        // get current account
        Account currentAccount = AccountService.getCurrentAccount();

        // get account from which money will be sent
        Account accountFrom = AccountService.getAccountById(currentAccount.getAccountId());

        // check if accountFrom is null
        if (accountFrom == null) {
            System.out.println("Error: Account not found.");
            return;
        }

        // create transfer object
        Transfer transfer = new Transfer();
        transfer.setAccountFrom(accountFrom.getAccountId());
        transfer.setAccountTo(recipientId);
        transfer.setAmount(amount);
        transfer.setTransferTypeId(2L); // regular transfer
        transfer.setTransferStatusId(2L); // approved

        // send the transfer
        Transfer sentTransfer = TransferService.addTransfer(transfer);
        if (sentTransfer != null) {
            System.out.println("Transfer successful!");
        } else {
            System.out.println("Transfer failed.");
        }
    }

    public void setAccountFrom(Long accountId) {
    }

    private static class UserService {
        public User[] listUsers() {
            return new User[0];
        }
    }
}
