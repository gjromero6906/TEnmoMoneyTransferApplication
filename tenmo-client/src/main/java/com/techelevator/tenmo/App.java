package com.techelevator.tenmo;

import com.techelevator.tenmo.model.*;
import com.techelevator.tenmo.services.AccountService;
import com.techelevator.tenmo.services.AuthenticationService;
import com.techelevator.tenmo.services.ConsoleService;
import com.techelevator.tenmo.services.TransferService;
import io.cucumber.java.cy_gb.A;

import java.math.BigDecimal;

public class App {

    private static final String API_BASE_URL = "http://localhost:8080/";

    private final ConsoleService consoleService = new ConsoleService();
    private final AuthenticationService authenticationService = new AuthenticationService(API_BASE_URL);

    private AuthenticatedUser currentUser;

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

	private void viewCurrentBalance() {
		// TODO Auto-generated method stub
        AccountService accountService= new AccountService(API_BASE_URL,currentUser);
        Account currentAccount =accountService.getBalance((long)currentUser.getUser().getId());
        consoleService.printGetBalance(currentAccount);

	}

	private void viewTransferHistory() {
		// TODO Auto-generated method stub
	}

	private void viewPendingRequests() {
		// TODO Auto-generated method stub
		
	}

	private void sendBucks() {
		// TODO Auto-generated method stub
        AccountService accountService = new AccountService(API_BASE_URL,currentUser);
        Account current = accountService.getAccountById((long)currentUser.getUser().getId());
        Account[] accounts = accountService.listAccounts();
        User[] users = accountService.getUsers();
        for(int i = 0;i<accounts.length;i++){
            System.out.println(accounts[i].getAccountId()+" "+ users[i].getUsername());
        }
        long accountToSend = Long.parseLong(consoleService.promptForString("please enter id to send"));
        while (!accountService.userExists(accountToSend) && accountToSend== current.getAccountId() ){
            System.out.println("user is invalid");
            for(int i = 0;i<accounts.length;i++){
                System.out.println(accounts[i].getAccountId()+" "+ users[i].getUsername());
            }
            accountToSend =Long.parseLong(consoleService.promptForString("please enter id to send"));
        }
        double amount = consoleService.promptForInt("enter amount to send");
        if (amount<0){
            System.out.println("Can't send negative");
            consoleService.printMainMenu();
        }else if(amount>current.getBalance()){
            System.out.println("Sending this amount will over draw,transaction not approve");
            consoleService.printMainMenu();
        }else {
            accountService.withdraw(current.getAccountId(),amount);
            accountService.deposit(accountToSend,amount);
            consoleService.transactionApproved(amount);
            consoleService.printMainMenu();
        }


    }

	private void requestBucks() {
		// TODO Auto-generated method stub
		
	}

}
