package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import com.techelevator.util.BasicLogger;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

public class TransferService {
    private static String API_BASE_URL;
    private static RestTemplate restTemplate = new RestTemplate();
    private static AuthenticatedUser user;

    public TransferService(String URL, AuthenticatedUser authenticatedUser) {
        user = authenticatedUser;
        API_BASE_URL = URL;
    }

    private HttpEntity<Void> makeAuthEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(user.getToken());
        return new HttpEntity(headers);
    }

    private static HttpEntity<Transfer> makeTransferEntity(Transfer transfer) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(user.getToken());
        return new HttpEntity<>(transfer, headers);
    }

    public Transfer[] getAllTransfersToFrom(Long id) {
        Transfer[] transfers = null;
        try {
            ResponseEntity<Transfer[]> response =
                    restTemplate.exchange(API_BASE_URL + "transfers/" + id, HttpMethod.GET,
                            makeAuthEntity(), Transfer[].class);
            transfers = response.getBody();

        } catch (RestClientResponseException e) {
            BasicLogger.log(e.getRawStatusCode() + " : " + e.getStatusText());
        } catch (ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return transfers;
    }


    public Transfer[] getAllTransferFrom(Long id) {
        Transfer[] transfers = null;
        try {
            ResponseEntity<Transfer[]> response =
                    restTemplate.exchange(API_BASE_URL + "transferdetails/" + id, HttpMethod.GET,
                            makeAuthEntity(), Transfer[].class);
            transfers = response.getBody();

        } catch (RestClientResponseException e) {
            BasicLogger.log(e.getRawStatusCode() + " : " + e.getStatusText());
        } catch (ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return transfers;
    }

    public Transfer[] listTransfer() {
        Transfer[] transfers = null;
        try {
            ResponseEntity<Transfer[]> response =
                    restTemplate.exchange(API_BASE_URL + "transfers/",
                            HttpMethod.GET,
                            makeAuthEntity(),
                            Transfer[].class);
            transfers = response.getBody();
        } catch (RestClientResponseException e) {
            BasicLogger.log(e.getRawStatusCode() + " : " + e.getStatusText());
        } catch (ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return transfers;
    }

    public static Transfer addTransfer(Transfer transfer) {
        Transfer t = transfer;
        try {
            //this whole mess gets reduced
//            t = restTemplate.exchange(API_BASE_URL + "transfer/"+transfer.getTransferStatusId()+"/"+transfer.getTransferTypeId()+"/"+transfer.getAccountFrom()+"/"+transfer.getAccountTo()+"/"+ transfer
//                            .getAmount(),
            t= restTemplate.exchange(API_BASE_URL +"add/transfer",
                    HttpMethod.POST,
                    makeTransferEntity(t),
                    Transfer.class).getBody();
        } catch (RestClientResponseException e) {
            BasicLogger.log(e.getRawStatusCode() + " : " + e.getStatusText());
        } catch (ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return t;
    }


    public void updateTransfer(Transfer transfer, Long typeId, Long statusId, Long transferId){
        HttpEntity<Transfer> entity = makeTransferEntity(transfer);
        try{
            restTemplate.put(API_BASE_URL+"transfer/update/"+typeId+"/"+statusId+"/"+transferId,
                    entity);
        }catch (RestClientResponseException e) {
            BasicLogger.log(e.getRawStatusCode() + " : " + e.getStatusText());
        } catch (ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
    }

    public Transfer[] listTransferStatus() {
        Transfer[] transferStatuses = null;
        try {
            ResponseEntity<Transfer[]> response =
                    restTemplate.exchange(API_BASE_URL + "transfers/",
                            HttpMethod.GET,
                            makeAuthEntity(),
                            Transfer[].class);
            transferStatuses = response.getBody();
        } catch (RestClientResponseException e) {
            BasicLogger.log(e.getRawStatusCode() + " : " + e.getStatusText());
        } catch (ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return transferStatuses;
    }

    public Transfer[] listTransferTypes() {
        Transfer[] transferTypes = null;
        try {
            ResponseEntity<Transfer[]> response =
                    restTemplate.exchange(API_BASE_URL + "transfers/",
                            HttpMethod.GET,
                            makeAuthEntity(),
                            Transfer[].class);
            transferTypes = response.getBody();
        } catch (RestClientResponseException e) {
            BasicLogger.log(e.getRawStatusCode() + " : " + e.getStatusText());
        } catch (ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return transferTypes;
    }

    public Transfer getTransferTypeById(Long id) {
        Transfer transferType = null;
        try {
            ResponseEntity<Transfer> response =
                    restTemplate.exchange(API_BASE_URL + "transfertype/" + id, HttpMethod.GET,
                            makeAuthEntity(), Transfer.class);
            transferType = response.getBody();

        } catch (RestClientResponseException e) {
            BasicLogger.log(e.getRawStatusCode() + " : " + e.getStatusText());
        } catch (ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return transferType;
    }

    public Transfer getTransferStatusById(Long id) {
        Transfer transferStatus = null;
        try {
            ResponseEntity<Transfer> response =
                    restTemplate.exchange(API_BASE_URL + "transferstatus/" + id, HttpMethod.GET,
                            makeAuthEntity(), Transfer.class);
            transferStatus = response.getBody();

        } catch (RestClientResponseException e) {
            BasicLogger.log(e.getRawStatusCode() + " : " + e.getStatusText());
        } catch (ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return transferStatus;
    }

    public User[] listUsers() {
        User[] users = null;
        try {
            ResponseEntity<User[]> response =
                    restTemplate.exchange(API_BASE_URL + "users",
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
}
