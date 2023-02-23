package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.TransferStatus;
import com.techelevator.tenmo.model.TransferType;
import com.techelevator.util.BasicLogger;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

public class TransferService {
    private String API_BASE_URL;
    private RestTemplate restTemplate = new RestTemplate();
    private AuthenticatedUser user;

    public TransferService(String URL, AuthenticatedUser authenticatedUser) {
        this.user = authenticatedUser;
        this.API_BASE_URL = URL;
    }

    private HttpEntity<Void> makeAuthEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(user.getToken());
        return new HttpEntity(headers);
    }

    private HttpEntity<Transfer> makeTransferEntity(Transfer transfer) {
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


    public TransferDetail[] getAllTransferFrom(Long id) {
        TransferDetail[] transfers = null;
        try {
            ResponseEntity<TransferDetail[]> response =
                    restTemplate.exchange(API_BASE_URL + "transferdetails/" + id, HttpMethod.GET,
                            makeAuthEntity(), TransferDetail[].class);
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

    public Transfer addTransfer(Transfer transfer) {
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

    public TransferStatus[] listTransferStatus() {
        TransferStatus[] transferStatuses = null;
        try {
            ResponseEntity<TransferStatus[]> response =
                    restTemplate.exchange(API_BASE_URL + "transfers/",
                            HttpMethod.GET,
                            makeAuthEntity(),
                            TransferStatus[].class);
            transferStatuses = response.getBody();
        } catch (RestClientResponseException e) {
            BasicLogger.log(e.getRawStatusCode() + " : " + e.getStatusText());
        } catch (ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return transferStatuses;
    }

    public TransferType[] listTransferTypes() {
        TransferType[] transferTypes = null;
        try {
            ResponseEntity<TransferType[]> response =
                    restTemplate.exchange(API_BASE_URL + "transfers/",
                            HttpMethod.GET,
                            makeAuthEntity(),
                            TransferType[].class);
            transferTypes = response.getBody();
        } catch (RestClientResponseException e) {
            BasicLogger.log(e.getRawStatusCode() + " : " + e.getStatusText());
        } catch (ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return transferTypes;
    }

    public TransferType getTransferTypeById(Long id) {
        TransferType transferType = null;
        try {
            ResponseEntity<TransferType> response =
                    restTemplate.exchange(API_BASE_URL + "transfertype/" + id, HttpMethod.GET,
                            makeAuthEntity(), TransferType.class);
            transferType = response.getBody();

        } catch (RestClientResponseException e) {
            BasicLogger.log(e.getRawStatusCode() + " : " + e.getStatusText());
        } catch (ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return transferType;
    }

    public TransferStatus getTransferStatusById(Long id) {
        TransferStatus transferStatus = null;
        try {
            ResponseEntity<TransferStatus> response =
                    restTemplate.exchange(API_BASE_URL + "transferstatus/" + id, HttpMethod.GET,
                            makeAuthEntity(), TransferStatus.class);
            transferStatus = response.getBody();

        } catch (RestClientResponseException e) {
            BasicLogger.log(e.getRawStatusCode() + " : " + e.getStatusText());
        } catch (ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return transferStatus;
    }
}
