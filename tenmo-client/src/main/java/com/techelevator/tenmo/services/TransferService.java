package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.util.BasicLogger;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// Provides methods to interact with the RESTful API for transfers provided by a Tenmo application.
// This class uses the Spring Framework's RestTemplate to make HTTP requests to the API.
public class TransferService {
    //Has a static final variable named BASE_URL that holds the base URL for the Tenmo API's transfer endpoints.
    // The class also has a static variable named restTemplate that is an instance of the RestTemplate class.
    private static final String BASE_URL = "http://localhost:8080/";
    private static RestTemplate restTemplate = new RestTemplate();
    private static String authToken= null;

// Has a method named setAuthToken that takes a String as an argument and sets the authToken instance variable to that value.
// The authToken is used to authenticate requests to the API.
    public void setAuthToken(String authToken){
        this.authToken=authToken;
    }

// Has six methods that make HTTP requests to the API: send, request, listTransfers, getById, update, and makeTransferEntity.

    // Has a private method named makeTransferEntity that takes a Transfer object as an argument and creates an HTTP request entity with the specified
    // transfer and authentication headers.
    // This method is used to create the HTTP request entities required for the HTTP requests sent by the send, request, update, and getById methods.
    private static HttpEntity<Transfer> makeTransferEntity(Transfer transfer) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setBearerAuth(authToken);
        return new HttpEntity<>(transfer, httpHeaders);
    }

    //The send method sends an HTTP PUT request to the API's transfer endpoint with the provided Transfer object and returns a boolean indicating whether
    // the transfer was successful. The request method sends an HTTP PUT request to the API's transfer endpoint with the provided Transfer object and a type
    // and status indicating that it is a request, and returns a boolean indicating whether the request was successful. The listTransfers method sends an HTTP
    // GET request to the API's transfer endpoint for a specific account and returns a list of Transfer objects representing the transfers for that account.
    // The listTransfers method without an argument sends an HTTP GET request to the API's transfer endpoint and returns a list of Transfer objects representing
    // all transfers. The getById method sends an HTTP GET request to the API's transfer endpoint with the specified ID and returns a Transfer object representing
    // that transfer. The update method sends an HTTP PUT request to the API's transfer endpoint with the updated Transfer object and returns a boolean indicating
    // whether the update was successful.
    public static boolean send(Transfer transfer){
        boolean success = false;

        try {
            restTemplate.put(BASE_URL + "transfer", makeTransferEntity(transfer));
            success = true;
        } catch (RestClientResponseException | ResourceAccessException e){
            System.out.println(e.getMessage());
        }
        return success;
    }

    public boolean request(Transfer transfer) {
        boolean success = false;

        try {
            restTemplate.put(BASE_URL + "transfer?type=" + 1 + "&status=" + 1, makeTransferEntity(transfer));
            success = true;
        } catch (RestClientResponseException | ResourceAccessException e){
            System.out.println(e.getMessage());
        }
        return success;
    }

    public List<Transfer> listTransfers(long account) {
        List<Transfer> transferList = new ArrayList<>();
        try {
            ResponseEntity<Transfer[]> response = restTemplate.exchange(BASE_URL + "transfers/" + account, HttpMethod.GET, makeAuthEntity(), Transfer[].class);
            transferList = Arrays.asList(response.getBody());
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return transferList;
    }

    public List<Transfer> listTransfers() {
        List<Transfer> transfers = new ArrayList<>();
        try {
            ResponseEntity<Transfer[]> response = restTemplate.exchange(BASE_URL + "transfers", HttpMethod.GET, makeAuthEntity(), Transfer[].class);
            transfers = Arrays.asList(response.getBody());
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return transfers;
    }

    public Transfer getById(long id) {
        Transfer transfer = null;
        try {
            ResponseEntity<Transfer> response = restTemplate.exchange(BASE_URL + "transfer/" + id, HttpMethod.GET, makeAuthEntity(), Transfer.class);
            transfer = response.getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return transfer;
    }

    //
    public boolean update(Transfer transfer) {
        boolean success = false;
        try {
            restTemplate.put(BASE_URL + "transfer/" + transfer.getId(), makeTransferEntity(transfer));
            success = true;
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return success;
    }

    private HttpEntity<Void> makeAuthEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(headers);
    }
}
