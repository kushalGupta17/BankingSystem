package com.bank.model;

public class CheckingAccount extends Account {
    private final double overdraftLimit;

    public CheckingAccount(String accountNumber, String accountHolderName, double initialBalance, double overdraftLimit) {
        super(accountNumber, accountHolderName, initialBalance);
        this.overdraftLimit = overdraftLimit;
    }

    @Override
    public void withdraw(double amount) throws com.bank.exception.InsufficientFundsException {
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be greater than zero.");
        }
        if (balance - amount < -overdraftLimit) {
            throw new com.bank.exception.InsufficientFundsException(
                "Transaction rejected: Exceeds overdraft limit of ₹" + overdraftLimit
            );
        }
        balance -= amount;
    }
}