package com.techelevator.tenmo.model;


// Represents the login credentials of a user
// Contains private instance variables for the username and password
// Provides a constructor to set the username and password
// Provides getter and setter methods for the username and password.
public class UserCredentials {

    private String username;
    private String password;

    public UserCredentials(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

