package model;

/**
 * Represents a bank account for a customer.
 * It manages the balance and operations like deposit, withdraw, and transfer.
 */
public class Account {

    private /*@ spec_public @*/ int accountNumber;
    private /*@ spec_public @*/ double balance;

    //@ public invariant balance >= 0.0;
    //@ public invariant accountNumber > 0;

    /**
     * Constructs a new account with the given account number.
     * 
     * @param accountNumber The unique identifier for the account.
     */
    //@ requires accountNumber > 0;
    //@ ensures this.accountNumber == accountNumber;
    //@ ensures this.balance == 0.0;
    public Account(int accountNumber) {
        this.accountNumber = accountNumber;
        this.balance = 0.0;
    }

    /**
     * Deposits a positive amount into the account.
     * 
     * @param amount The amount to be deposited.
     */
    //@ requires amount > 0.0;
    //@ assignable balance;
    //@ ensures balance == \old(balance) + amount;
    public void deposit(double amount) {
        this.balance += amount;
    }

    /**
     * Withdraws a positive amount from the account, ensuring the balance does not become negative.
     * 
     * @param amount The amount to be withdrawn.
     */
    //@ requires amount > 0.0;
    //@ requires balance >= amount;
    //@ assignable balance;
    //@ ensures balance == \old(balance) - amount;
    public void withdraw(double amount) {
        this.balance -= amount;
    }

    /**
     * Transfers a positive amount from this account to a destination account.
     * 
     * @param destination The account to receive the money.
     * @param amount The amount to transfer.
     */
    //@ requires amount > 0.0;
    //@ requires balance >= amount;
    //@ requires destination != null;
    //@ requires destination != this;
    //@ assignable balance, destination.balance;
    //@ ensures balance == \old(balance) - amount;
    //@ ensures destination.balance == \old(destination.balance) + amount;
    public void transfer(Account destination, double amount) {
        this.withdraw(amount);
        destination.deposit(amount);
    }

    /**
     * Gets the current balance of the account.
     * 
     * @return The current balance.
     */
    //@ ensures \result == balance;
    public /*@ pure @*/ double getBalance() {
        return this.balance;
    }

    /**
     * Gets the account number.
     * 
     * @return The account number.
     */
    //@ ensures \result == accountNumber;
    public /*@ pure @*/ int getAccountNumber() {
        return this.accountNumber;
    }
}
