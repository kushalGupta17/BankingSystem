package com.bank.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.bank.exception.AccountNotFoundException;
import com.bank.exception.InsufficientFundsException;
import com.bank.model.Account;

public class BankService {
    // In-memory store: Key = Account Number, Value = Account Object
    private final Map<String, Account> accounts = new HashMap<>();

    public void openAccount(Account account) {
        if (accounts.containsKey(account.getAccountNumber())) {
            throw new IllegalArgumentException("Account number " + account.getAccountNumber() + " already exists.");
        }
        accounts.put(account.getAccountNumber(), account);
        System.out.println("Account opened successfully for " + account.getAccountHolderName());
    }

    public Account findAccount(String accountNumber) throws AccountNotFoundException {
        Account account = accounts.get(accountNumber);
        if (account == null) {
            throw new AccountNotFoundException("Account " + accountNumber + " not found.");
        }
        return account;
    }

    public void deposit(String accountNumber, double amount) throws AccountNotFoundException {
        Account account = findAccount(accountNumber);
        account.deposit(amount);
        System.out.println("Deposited ₹" + amount + " to " + accountNumber + ". New Balance: ₹" + account.getBalance());
    }

    public void withdraw(String accountNumber, double amount) throws AccountNotFoundException, InsufficientFundsException {
        Account account = findAccount(accountNumber);
        account.withdraw(amount);
        System.out.println("Withdrew ₹" + amount + " from " + accountNumber + ". Remaining Balance: ₹" + account.getBalance());
    }

    public void transfer(String fromAccountNumber, String toAccountNumber, double amount) 
            throws AccountNotFoundException, InsufficientFundsException {
        
        if (fromAccountNumber.equals(toAccountNumber)) {
            throw new IllegalArgumentException("Source and destination accounts cannot be the same.");
        }

        Account source = findAccount(fromAccountNumber);
        Account destination = findAccount(toAccountNumber);

        // Attempt withdrawal from source first; if this throws, destination is never credited
        source.withdraw(amount);
        destination.deposit(amount);

        System.out.println("Transfer of ₹" + amount + " from " + fromAccountNumber + " to " + toAccountNumber + " completed.");
    }

    public Collection<Account> getAllAccounts() {
        return accounts.values();
    }
}