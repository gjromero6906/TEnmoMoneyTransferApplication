package com.techelevator.tenmo;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.AuthenticatedUser;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.UserCredentials;
import com.techelevator.tenmo.services.AccountService;
import com.techelevator.tenmo.services.AuthenticationService;

import com.techelevator.tenmo.services.ConsoleService;
import com.techelevator.tenmo.services.TransferService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class App {

    private static final String API_BASE_URL = "http://localhost:8080/";

    private final ConsoleService consoleService = new ConsoleService();
    private final AuthenticationService authenticationService = new AuthenticationService(API_BASE_URL);
    private AccountService accountService = new AccountService();
    private TransferService transferService = new TransferService();
    public final int STATUS_APPROVED = 2;
    public final int STATUS_PENDING = 1;
    public final int STATUS_REJECTED = 3;
    public final int TRANSFER_SEND = 2;
    public final int TRANSFER_REQUEST = 1;

    private AuthenticatedUser currentUser;
    public Account currentUserAccount;

    public static void main(String[] args) {
        App app = new App();
        app.run();
    }

    private void run() {
        consoleService.printGreeting();
        loginMenu();
        if (currentUser != null) {
            mainMenu();
        }
    }

    private void loginMenu() {
        int menuSelection = -1;
        while (menuSelection != 0 && currentUser == null) {
            consoleService.printLoginMenu();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
            if (menuSelection == 1) {
                handleRegister();
            } else if (menuSelection == 2) {
                handleLogin();
            } else if (menuSelection != 0) {
                System.out.println("Invalid Selection");
                consoleService.pause();
            }
        }
    }

    private void handleRegister() {
        System.out.println("Please register a new user account");
        UserCredentials credentials = consoleService.promptForCredentials();
        if (authenticationService.register(credentials)) {
            System.out.println("Registration successful. You can now login.");
        } else {
            consoleService.printErrorMessage();
        }
    }

    private void handleLogin() {
        UserCredentials credentials = consoleService.promptForCredentials();
        currentUser = authenticationService.login(credentials);
        if (currentUser == null) {
            consoleService.printErrorMessage();
        } else {
            accountService.setAuthToken(currentUser.getToken());
            transferService.setAuthToken(currentUser.getToken());
            currentUserAccount = accountService.get((long) (currentUser.getUser().getId() + 1000));
        }
    }

    private void mainMenu() {
        int menuSelection = -1;
        while (menuSelection != 0) {
            consoleService.printMainMenu();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
            if (menuSelection == 1) {
                viewCurrentBalance();
            } else if (menuSelection == 2) {
                viewTransferHistory();
            } else if (menuSelection == 3) {
                viewPendingRequests();
            } else if (menuSelection == 4) {
                sendBucks();
            } else if (menuSelection == 5) {
                requestBucks();
            } else if (menuSelection == 0) {
                continue;
            } else {
                System.out.println("Invalid Selection");
            }
            consoleService.pause();
        }
    }
    // displays the current user's balance by sending a GET request to retrieve the BankAccount object based on the user's ID.
    private void viewCurrentBalance() {
        System.out.println("Your current balance is: $" +
               currentUserAccount.getBalance());
    }

    private void viewTransferHistory() {
        List<Transfer> transferList = transferService.listTransfers();

        for (Transfer transfer : transferList) {
            if (transfer.getToAccountID().equals(currentUserAccount.getId()) ||
                    transfer.getFromAccountID().equals(currentUserAccount.getId())) {

                String fromToUser;
                if (currentUserAccount.getId().equals(transfer.getToAccountID())) {
                    fromToUser = "From User: " +
                            accountService.get(transfer.getFromAccountID()).getUsername();
                } else {
                    fromToUser = "To User: " +
                            accountService.get(transfer.getToAccountID()).getUsername();
                }

                System.out.println("Transfer ID: " + transfer.getId() +
                        " | Amount: $" + transfer.getAmount() +
                        " | " + fromToUser);

            }
        }

        long transferId = consoleService.promptForInt("Enter a transfer ID to see more details, or enter 0 to exit: ");
        Transfer currentTransfer = null;
        //we already have a list for the current user why not use it
        for (Transfer transfer: transferList){
            if(transfer.getId() == transferId){
                currentTransfer = transfer;
            }
        }
        if (currentTransfer != null) {
            String transferStatus = null;
            if (currentTransfer.getTransferStatus() == STATUS_PENDING) {
                transferStatus = "Pending";
            } else if (currentTransfer.getTransferStatus() == STATUS_APPROVED) {
                transferStatus = "Approved";
            } else if (currentTransfer.getTransferStatus() == STATUS_REJECTED) {
                transferStatus = "Rejected";
            }

            String transferType = null;
            if (currentTransfer.getTransferType() == TRANSFER_REQUEST) {
                transferType = "Request";
            } else if (currentTransfer.getTransferType() == TRANSFER_SEND) {
                transferType = "Transfer";
            }

            Account recipient = accountService.get(currentTransfer.getToAccountID());
            Account sender = accountService.get(currentTransfer.getFromAccountID());

            System.out.println("ID: " + currentTransfer.getId() + " | Amount: $" + currentTransfer.getAmount() + " | Recipient: " + recipient.getUsername() + " | Sender: " + sender.getUsername() + " | Status: " + transferStatus  + " | Type: " + transferType);

        } else {
            System.out.println("Invalid transfer ID.");
        }
    }

    private void viewPendingRequests() {
        // TODO Auto-generated method stub
        List<Transfer> transferList = transferService.listTransfers();

        for (Transfer transfer : transferList) {
            if (transfer.getTransferStatus() == STATUS_PENDING && transfer.getFromAccountID().equals(currentUserAccount.getId())) {
                Account requester = accountService.get(transfer.getToAccountID());
                System.out.println("Request ID: " + transfer.getId() + " | Requested Amount: $" + transfer.getAmount() + " | User: " + requester.getUsername());
            }
        }

        int selection = consoleService.promptForInt("Enter 1 to reject a request, 2 to approve a request, or 0 to exit: ");

        while(selection != 0) {
            if (selection == 1) {

                long transferId = consoleService.promptForInt("Enter the ID of the request to reject: ");
                Transfer currentTransfer = null;
                for (Transfer transfer: transferList){
                    if(transfer.getId() == transferId){
                        currentTransfer = transfer;
                    }
                }

                if (currentTransfer != null) {
                    currentTransfer.setTransferStatus(STATUS_REJECTED);
                    transferService.update(currentTransfer);
                    System.out.println("Request rejected.");
                } else {
                    System.out.println("Invalid request ID.");
                }
                selection=0;
                mainMenu();

            } else if (selection == 2) {

                long transferId = consoleService.promptForInt("Enter the ID of the request to reject: ");
                Transfer currentTransfer = null;
                for (Transfer transfer: transferList){
                    if(transfer.getId() == transferId){
                        currentTransfer = transfer;
                    }
                }

                if (currentTransfer != null) {
                    Account receiver = accountService.get(currentTransfer.getToAccountID());
                    Account sender = accountService.get(currentTransfer.getFromAccountID());

                    if (sender.getBalance().compareTo(currentTransfer.getAmount()) >= 0) {

                        sender.setBalance(sender.getBalance().subtract(currentTransfer.getAmount()));
                        accountService.update(sender);

                        receiver.setBalance(receiver.getBalance().add(currentTransfer.getAmount()));
                        accountService.update(receiver);
                        System.out.println("Request approved. Remaining balance: $" + sender.getBalance());

                        currentTransfer.setTransferStatus(STATUS_APPROVED);
                        transferService.update(currentTransfer);

                    } else {
                        System.out.println("Insufficient funds to approve the request.");
                    }

                } else {
                    System.out.println("Invalid request ID.");
                }
                selection=0;
                mainMenu();

            } else {
                selection = consoleService.promptForInt("Enter 1 to reject a request, 2 to approve a request, or 0 to exit: ");
            }
        }
        if (selection == 0) {
            mainMenu();
        }
    }

    private void sendBucks() {
        // TODO Auto-generated method stub
        List<String> accountList = accountService.listAccounts();

        for (String account : accountList) {
            if (!account.contains("Username: " + currentUser.getUser().getUsername())) {
                System.out.println(account);
            }
        }

        long recipientId = consoleService.promptForInt("Enter the ID of the account you want to transfer to, or 0 to exit: ");

        if (recipientId == currentUserAccount.getId()) {
            recipientId = consoleService.promptForInt("You cannot send money to yourself. Please enter the ID of the user you want to transfer to: ");
        } else if (recipientId != 0) {

            Account sender = accountService.get(currentUserAccount.getId());
            Account receiver = accountService.get(recipientId);

            if (receiver != null) {

                BigDecimal amount = consoleService.promptForBigDecimal("Enter the amount you would like to transfer: ");

                if (amount.signum() == 1 && sender.getBalance().compareTo(amount) >= 0) {

                    sender.setBalance(sender.getBalance().subtract(amount));
                    accountService.update(sender);

                    receiver.setBalance(receiver.getBalance().add(amount));
                    accountService.update(receiver);

                    System.out.println("$" + amount + " sent to user " + receiver.getUsername() + ". New balance: " + sender.getBalance());

                    Transfer transfer = new Transfer(amount, sender.getId(), receiver.getId(), STATUS_APPROVED, TRANSFER_SEND);
                    transferService.send(transfer);

                } else if (amount.signum() == 0 || amount.signum() == -1) {
                    System.out.println("You cannot $0 or a negative amount.");
                } else {
                    System.out.println("You cannot send more money than you have in your account.");
                }

            } else {
                System.out.println("Invalid Account ID.");
            }

        } else {
            mainMenu();
        }

    }

    private void requestBucks() {
        // TODO Auto-generated method stub
        List<String> accountList = accountService.listAccounts();

        for (String account : accountList) {
            if (!account.contains("Username: " + currentUser.getUser().getUsername())) {
                System.out.println(account);
            }
        }

        long requesteeId = (long)consoleService.promptForInt("Enter the ID of the user you want to request from, or 0 to exit: ");

        if (requesteeId == currentUserAccount.getId()) {
            requesteeId = consoleService.promptForInt("You cannot request money from yourself. Please enter the ID of the user you want to request money from: ");
        } else if (requesteeId != 0) {

            Account requester = accountService.get(currentUserAccount.getId());
            Account requestee = accountService.get(requesteeId);

            if (requestee != null) {

                BigDecimal amount = consoleService.promptForBigDecimal("Enter the amount you would like to request: ");
                if (amount.signum() == 1) {

                    System.out.println("$" + amount + " requested from user " + requestee.getUsername() + ". Approval pending.");

                    Transfer transfer = new Transfer(amount, requestee.getId(), requester.getId(), STATUS_PENDING, TRANSFER_REQUEST);
                    transferService.request(transfer);

                } else {
                    System.out.println("Please request an amount of money greater than $0.");
                }

            } else {
                System.out.println("Invalid Account ID.");
            }

        } else {
            mainMenu();
        }
    }
}