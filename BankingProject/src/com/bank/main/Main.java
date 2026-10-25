package com.bank.main;

import java.util.Collection;
import java.util.Scanner;

import com.bank.exception.AccountNotFoundException;
import com.bank.exception.InsufficientFundsException;
import com.bank.model.Account;
import com.bank.model.CheckingAccount;
import com.bank.model.SavingsAccount;
import com.bank.service.BankService;

public class Main {
    private static final BankService bankService = new BankService();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean running = true;

        System.out.println("============================================");
        System.out.println("   WELCOME TO CORE BANKING CONSOLE APP      ");
        System.out.println("============================================");

        while (running) {
            printMenu();
            System.out.print("Select an option (1-7): ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    handleCreateAccount();
                    break;
                case "2":
                    handleDeposit();
                    break;
                case "3":
                    handleWithdraw();
                    break;
                case "4":
                    handleTransfer();
                    break;
                case "5":
                    handleCheckBalance();
                    break;
                case "6":
                    handleListAllAccounts();
                    break;
                case "7":
                    System.out.println("Thank you for using our banking system. Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid selection. Please choose an option between 1 and 7.");
            }
            System.out.println();
        }
        scanner.close();
    }

    private static void printMenu() {
        System.out.println("1. Open New Account");
        System.out.println("2. Deposit Money");
        System.out.println("3. Withdraw Money");
        System.out.println("4. Transfer Funds");
        System.out.println("5. Check Account Summary");
        System.out.println("6. View All Accounts");
        System.out.println("7. Exit");
    }

    private static void handleCreateAccount() {
        System.out.print("Enter Account Type (1 for Savings, 2 for Checking): ");
        String type = scanner.nextLine().trim();

        System.out.print("Enter Account Number: ");
        String accNum = scanner.nextLine().trim();

        System.out.print("Enter Account Holder Name: ");
        String name = scanner.nextLine().trim();

        System.out.print("Enter Initial Deposit: ");
        double initialBalance = Double.parseDouble(scanner.nextLine().trim());

        try {
            if ("1".equals(type)) {
                System.out.print("Enter Minimum Balance requirement (e.g. 1000): ");
                double minBal = Double.parseDouble(scanner.nextLine().trim());
                System.out.print("Enter Annual Interest Rate (e.g. 0.04 for 4%): ");
                double rate = Double.parseDouble(scanner.nextLine().trim());
                bankService.openAccount(new SavingsAccount(accNum, name, initialBalance, minBal, rate));
            } else if ("2".equals(type)) {
                System.out.print("Enter Overdraft Limit (e.g. 5000): ");
                double overdraft = Double.parseDouble(scanner.nextLine().trim());
                bankService.openAccount(new CheckingAccount(accNum, name, initialBalance, overdraft));
            } else {
                System.out.println("Invalid account type selection.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error creating account: " + e.getMessage());
        }
    }

    private static void handleDeposit() {
        try {
            System.out.print("Enter Account Number: ");
            String accNum = scanner.nextLine().trim();

            System.out.print("Enter Amount to Deposit: ");
            double amount = Double.parseDouble(scanner.nextLine().trim());

            bankService.deposit(accNum, amount);
        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid numeric input.");
        } catch (AccountNotFoundException | IllegalArgumentException e) {
            System.out.println("Deposit failed: " + e.getMessage());
        }
    }

    private static void handleWithdraw() {
        try {
            System.out.print("Enter Account Number: ");
            String accNum = scanner.nextLine().trim();

            System.out.print("Enter Amount to Withdraw: ");
            double amount = Double.parseDouble(scanner.nextLine().trim());

            bankService.withdraw(accNum, amount);
        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid numeric input.");
        } catch (AccountNotFoundException | InsufficientFundsException | IllegalArgumentException e) {
            System.out.println("Withdrawal rejected: " + e.getMessage());
        }
    }

    private static void handleTransfer() {
        try {
            System.out.print("Enter Source Account Number: ");
            String fromAcc = scanner.nextLine().trim();

            System.out.print("Enter Destination Account Number: ");
            String toAcc = scanner.nextLine().trim();

            System.out.print("Enter Transfer Amount: ");
            double amount = Double.parseDouble(scanner.nextLine().trim());

            bankService.transfer(fromAcc, toAcc, amount);
        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid numeric input.");
        } catch (AccountNotFoundException | InsufficientFundsException | IllegalArgumentException e) {
            System.out.println("Transfer failed: " + e.getMessage());
        }
    }

    private static void handleCheckBalance() {
        try {
            System.out.print("Enter Account Number: ");
            String accNum = scanner.nextLine().trim();

            Account acc = bankService.findAccount(accNum);
            acc.displayAccountInfo();
        } catch (AccountNotFoundException e) {
            System.out.println("Lookup failed: " + e.getMessage());
        }
    }

    private static void handleListAllAccounts() {
        Collection<Account> all = bankService.getAllAccounts();
        if (all.isEmpty()) {
            System.out.println("No accounts currently registered in the system.");
            return;
        }
        System.out.println("Registered Accounts:");
        for (Account a : all) {
            a.displayAccountInfo();
        }
    }
}
