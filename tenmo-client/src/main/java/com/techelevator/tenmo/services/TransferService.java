package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import com.techelevator.util.BasicLogger;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

//TransferService(String baseUrl): constructor that takes in a base URL for the API
//setAuthToken(AuthenticatedUser currentUser): sets the auth token for the current user
//sendBucks(Transfer transfer): sends a transfer from the current user's account to another user's account
//requestBucks(Transfer transfer): sends a request for a transfer from another user's account to the current user's account
//userTransfers(User user): gets a list of all transfers for the current user's account
//updateTransfer(Transfer transfer): updates the status of a transfer (approve or reject)
//makeAuthEntity(): creates an HTTP entity with the current user's auth token
//makeTransferEntity(Transfer transfer): creates an HTTP entity with a transfer object and the current user's auth token

public class TransferService {
    private String baseUrl;
    private RestTemplate restTemplate = new RestTemplate();
    private String authToken;

    public TransferService(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public void setAuthToken(AuthenticatedUser currentUser) {
        this.authToken = currentUser.getToken();
    }

    public void sendBucks(Transfer transfer) {

        try {
            restTemplate.postForObject(baseUrl + "send/", makeTransferEntity(transfer), Void.class);

        } catch (ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        } catch (RestClientResponseException e) {
            BasicLogger.log(e.getStatusText());
        }
    }
    public void requestBucks(Transfer transfer) {
        try {

            restTemplate.postForObject(baseUrl + "request/", makeTransferEntity(transfer), Void.class);

        }catch (ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        } catch (RestClientResponseException e) {
            BasicLogger.log(e.getStatusText());
        }
    }

    public Transfer[] userTransfers(User user) {
        Transfer[] transfers = null;
        try {
            ResponseEntity<Transfer[]> response =
                    restTemplate.exchange(baseUrl + "transfer/history/", HttpMethod.GET, makeAuthEntity(), Transfer[].class);
            transfers = response.getBody();
        } catch (ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        } catch (RestClientResponseException e) {
            BasicLogger.log(e.getStatusText());
        }
        return transfers;
    }

    public void updateTransfer (Transfer transfer){
        try {
            restTemplate.put(baseUrl + "transfer/", makeTransferEntity(transfer));
        }catch (ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        } catch (RestClientResponseException e) {
            BasicLogger.log(e.getStatusText());
        }
    }

    private HttpEntity<Void> makeAuthEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(headers);
    }

    private HttpEntity<Transfer> makeTransferEntity(Transfer transfer){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(transfer, headers);
    }
}
