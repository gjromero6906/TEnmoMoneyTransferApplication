package com.techelevator.tenmo.services;


import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import com.techelevator.tenmo.model.UserCredentials;

import java.math.BigDecimal;
import java.util.Scanner;

// Provides methods to interact with the Tenmo application via the console interface
// Contains a private instance variable for a Scanner object to read user input
// Provides methods to prompt the user for menu selections, credentials, strings, integers, and decimal numbers, and to print greetings, menus, and various types of information
// Contains methods to display user details and a list of transfers, and to prompt the user to approve or reject a pending transfer.
public class ConsoleService {

    private final Scanner scanner = new Scanner(System.in);

    public int promptForMenuSelection(String prompt) {
        int menuSelection;
        System.out.print(prompt);
        try {
            menuSelection = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            menuSelection = -1;
        }
        return menuSelection;
    }

    public void printGreeting() {
        System.out.println("*********************");
        System.out.println("* Welcome to TEnmo! *");
        System.out.println("*********************");
    }

    public void printLoginMenu() {
        System.out.println();
        System.out.println("1: Register");
        System.out.println("2: Login");
        System.out.println("0: Exit");
        System.out.println();
    }

    public void printMainMenu() {
        System.out.println();
        System.out.println("1: View your current balance");
        System.out.println("2: View your past transfers");
        System.out.println("3: View your pending requests");
        System.out.println("4: Send TE bucks");
        System.out.println("5: Request TE bucks");
        System.out.println("0: Exit");
        System.out.println();
    }

    public UserCredentials promptForCredentials() {
        String username = promptForString("Username: ");
        String password = promptForString("Password: ");
        return new UserCredentials(username, password);
    }

    public String promptForString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    public int promptForInt(String prompt) {
        System.out.print(prompt);
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number.");
            }
        }
    }

    public BigDecimal promptForBigDecimal(String prompt) {
        System.out.print(prompt);
        while (true) {
            try {
                return new BigDecimal(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a decimal number.");
            }
        }
    }

    public void pause() {
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }

    public void printErrorMessage() {
        System.out.println("An error occurred. Check the log for details.");
    }

    public int userIdForSendRequestMoney(User[] users) {
        System.out.println("---------------------------------------------");
        System.out.println("Users");
        System.out.println("ID      Name");
        System.out.println("----------------------------------------------");

        for (int i = 0; i < users.length; i++) {
            System.out.println(users[i].selectionPrint());
        }
        return promptForInt("Please enter User ID: ");

    }

    public void printTransferDetails(Transfer chosenTransfer) {
        System.out.println("---------------------------------------------------------------");
        System.out.println("Transfer Details");
        System.out.println("---------------------------------------------------------------");
        System.out.println("Id: " + chosenTransfer.getTransferId());
        System.out.println("From: " + chosenTransfer.getAccountFromUsername());
        System.out.println("To: " + chosenTransfer.getAccountToUsername());
        System.out.println("Type: " + chosenTransfer.getTransferType());
        System.out.println("Status: " + chosenTransfer.getTransferStatus());
        System.out.println("Amount: $" + chosenTransfer.getAmount());
    }

    public void printTransferListHeader (){
        System.out.println("---------------------------------------------------------------");
        System.out.println("Transfer List");
        System.out.println("ID          From/To         Amount");
        System.out.println("---------------------------------------------------------------");
    }

    public void approveRejectOrPending (){
        System.out.println("---------------------------------------------------------------");
        System.out.println("1. Approve");
        System.out.println("2. Reject");
        System.out.println("0. Don't approve or reject (Do nothing)");
        System.out.println("---------------------------------------------------------------");
    }
}
