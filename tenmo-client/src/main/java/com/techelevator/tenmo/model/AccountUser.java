package com.techelevator.tenmo.model;

public class AccountUser {
    // Has three private instance variables: user_id, username, and password.
    // The user_id variable is a long type that represents the user ID. The username variable is a String type
    // that represents the username associated with the user account.
    // The password variable is a String type that represents the password associated with the user account.
    private long user_id;
    private String username;
    private String password;

    public static long getId() {
        return getId();
    }

    // Has three accessor methods that can be used to get and set the values of the instance variables.
    // The getUser_id method returns the value of the user_id variable.
    // The getUsername method returns the value of the username variable. The setId method sets the value of the user_id variable to the specified long value.
    public long getUser_id() {
        return user_id;
    }
    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    //Has a toString method that returns a string representation of the AccountUser object.
    // This method concatenates the values of the user_id and username variables into a string.
    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return "UserID: " + getUser_id() + " Username: " + getUsername();
    }
}
