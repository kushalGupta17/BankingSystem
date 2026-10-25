package com.bank.model;

public class SavingsAccount extends Account {
    private final double minimumBalance;
    private final double interestRate; // e.g., 0.04 for 4%

    public SavingsAccount(String accountNumber, String accountHolderName, double initialBalance, double minimumBalance, double interestRate) {
        super(accountNumber, accountHolderName, initialBalance);
        this.minimumBalance = minimumBalance;
        this.interestRate = interestRate;
    }
    @Override
    public void withdraw(double amount) throws com.bank.exception.InsufficientFundsException {
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be greater than zero.");
        }
        if (balance - amount < minimumBalance) {
            throw new com.bank.exception.InsufficientFundsException(
                "Transaction rejected: Balance cannot drop below minimum limit of ₹" + minimumBalance
            );
        }
        balance -= amount;
  
    }

    public void applyInterest() {
        double interest = balance * interestRate;
        balance += interest;
        System.out.println("Interest of ₹" + interest + " applied to account " + getAccountNumber());
    }
}