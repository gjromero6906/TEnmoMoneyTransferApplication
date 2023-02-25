package com.techelevator.tenmo.model;

/* This class represents a financial transfer between two parties. It has several private instance variables, each representing a different aspect of the transfer, such as transfer ID,
   transfer type ID, transfer status ID, the ID of the sender, the ID of the recipient, and the amount being transferred. The class has a default constructor, and also provides public
   getter and setter methods for each of its private instance variables. This allows other parts of the program to access and modify the transfer's properties as needed. In addition,
   the 'Transfer' class overrides the 'toString' method, which returns a string representation of the transfer. The returned string includes information about the sender, the amount being
   transferred, and the recipient.*/

public class Transfer {

    private long transfer_id;

    private long transfer_type_id;

    private long transfer_status_id;

    private long transfer_from;

    private long transfer_to;

    private double amount;

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
}
