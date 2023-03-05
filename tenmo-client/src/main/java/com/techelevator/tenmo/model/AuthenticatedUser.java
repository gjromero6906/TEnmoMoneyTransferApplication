package com.techelevator.tenmo.model;

//Represents an authenticated user with a token and associated user object
//Contains private instance variables for the user's authentication token and user object
//Provides getter and setter methods for the token and user object
public class AuthenticatedUser {
	
	private String token;
	private User user;
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
}
