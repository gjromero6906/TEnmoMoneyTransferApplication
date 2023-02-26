package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import com.techelevator.util.BasicLogger;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

public class AccountService {
    private static String API_BASE_URL;
    private static RestTemplate restTemplate = new RestTemplate();
    private static AuthenticatedUser authenticatedUser;
    private TransferService transferService;

    public AccountService(String URL, AuthenticatedUser user) {
        this.authenticatedUser = user;
        this.API_BASE_URL = URL;
        this.transferService = new TransferService(URL, user);
    }

    public static Account getCurrentAccount() {
        Long userId = Long.valueOf(authenticatedUser.getUser().getId());
        return getAccountById(userId);
    }

    public static Account getAccountById(Long userId) {
        Account account = null;
        try {
            account = restTemplate.exchange(API_BASE_URL + "account/user/" + userId,
                    HttpMethod.GET,
                    makeAuthEntity(),
                    Account.class).getBody();
        } catch (RestClientResponseException rcre) {
            BasicLogger.log(rcre.getRawStatusCode() + " : " + rcre.getStatusText());
        } catch (ResourceAccessException rae) {
            BasicLogger.log(rae.getMessage());
        }
        return account;
    }


    private static HttpEntity<Void> makeAuthEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authenticatedUser.getToken());
        return new HttpEntity<>(headers);
    }

    private HttpEntity<Account> makeTransferEntity(Account account) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authenticatedUser.getToken());
        return new HttpEntity<>(account, headers);
    }

    public Account getBalance(Long id) {
        Account account = null;
        try {
            account = restTemplate.exchange(API_BASE_URL + "balance/" + id,
                    HttpMethod.GET,
                    makeAuthEntity(),
                    Account.class).getBody();

        } catch (RestClientResponseException rcre) {
            BasicLogger.log(rcre.getRawStatusCode() + " : " + rcre.getStatusText());
        } catch (ResourceAccessException rae) {
            BasicLogger.log(rae.getMessage());
        }
        return account;
    }

    public Account[] listAccounts() {
        Account[] accounts = null;
        try {
            ResponseEntity<Account[]> response =
                    restTemplate.exchange(API_BASE_URL + "accounts/",
                            HttpMethod.GET,
                            makeAuthEntity(),
                            Account[].class);
            accounts = response.getBody();
        } catch (RestClientResponseException e) {
            BasicLogger.log(e.getRawStatusCode() + " : " + e.getStatusText());
        } catch (ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return accounts;
    }

    public User[] getUsers() {
        User[] users = null;
        try {
            ResponseEntity<User[]> response =
                    restTemplate.exchange(API_BASE_URL + "user/",
                            HttpMethod.GET,
                            makeAuthEntity(),
                            User[].class);
            users = response.getBody();
        } catch (RestClientResponseException e) {
            BasicLogger.log(e.getRawStatusCode() + " : " + e.getStatusText());
        } catch (ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return users;
    }

    public void withdraw(Long accountId, double amount) {
    }

    public void deposit(long accountToSend, double amount) {
    }

    public boolean userExists(long accountToSend) {
        return false;
    }
}
