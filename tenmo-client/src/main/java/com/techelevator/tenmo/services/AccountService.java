package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Account;
import com.techelevator.util.BasicLogger;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// Provides methods to interact with the RESTful API for accounts provided by a Tenmo application.
// This class uses the Spring Framework's RestTemplate to make HTTP requests to the API.
//Also logs exceptions using the BasicLogger utility class if there are any exceptions thrown while making the HTTP requests.
public class AccountService {
//Has a static final variable named API_BASE_URL that holds the base URL for the Tenmo API's account endpoints.
// The class also has a static variable named restTemplate that is an instance of the RestTemplate class.
    public static final String API_BASE_URL = "http://localhost:8080/account/";
    private static RestTemplate restTemplate = new RestTemplate();

    private static String authToken = null;

//Has a method named setAuthToken that takes a String as an argument and sets the authToken instance variable to that value.
// The authToken is used to authenticate requests to the API.
    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    //  has three methods that make HTTP requests to the API: listAccounts, get, and update.
    //  The listAccounts method sends an HTTP GET request to the API's account endpoint and returns a List of strings representing the account information.
    //  The get method sends an HTTP GET request to the API's account endpoint with the specified ID and returns an Account object representing the account.
    //  The update method sends an HTTP PUT request to the API's account endpoint with the updated Account object and returns a boolean indicating whether
    //  the update was successful.
    public static List<String> listAccounts() {
        List<String> accountList = new ArrayList<>();
        try {
            ResponseEntity<String[]> response = restTemplate.exchange(API_BASE_URL, HttpMethod.GET, makeAuthEntity(), String[].class);
            accountList = Arrays.asList(response.getBody());
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return accountList;
    }

    public static Account get(Long id) {
        Account account = null;
        try {
            ResponseEntity<Account> response =
                    restTemplate.exchange(API_BASE_URL + id, HttpMethod.GET, makeAuthEntity(), Account.class);
            account = response.getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return account;
    }

    public boolean update(Account account) {
        boolean success = false;
        try {
            restTemplate.put(API_BASE_URL + account.getUserId(), makeAccountEntity(account));
            success = true;
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return success;
    }

//Has two private methods named makeAccountEntity and makeAuthEntity.
// The makeAccountEntity method takes an Account object as an argument and creates an HTTP request entity with the specified account and authentication headers.
// The makeAuthEntity method creates an HTTP request entity with only the authentication headers.
// These methods are used to create the HTTP request entities required for the HTTP requests sent by the listAccounts, get, and update methods.
    private HttpEntity<Account> makeAccountEntity(Account account) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(account, headers);
    }


    private static HttpEntity<Void> makeAuthEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(headers);
    }
}
