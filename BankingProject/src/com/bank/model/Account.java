package com.bank.model;

public abstract class Account 
{
	private final String accountNumber;
	private final String accountHolderName;
	protected double balance;
	
	public Account(String accountNumber, String accountHolderName, double balance) {
		super();
		this.accountNumber = accountNumber;
		this.accountHolderName = accountHolderName;
		this.balance = balance;
	}
	public double getBalance() {
		return balance;
	
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public String getAccountHolderName() {
		return accountHolderName;
	}
	public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive.");
        }
        balance += amount;
    }

	public abstract void withdraw(double amount) throws com.bank.exception.InsufficientFundsException;;

    public void displayAccountInfo() {
        System.out.println("Account No: " + accountNumber + 
                           " | Holder: " + accountHolderName + 
                           " | Balance: ₹" + balance);
    }

	
	
	
}
