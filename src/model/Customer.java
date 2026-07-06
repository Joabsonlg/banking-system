package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a customer of the bank.
 * A customer can have multiple accounts and a password for authentication.
 */
public class Customer {

    private /*@ spec_public @*/ String name;
    private /*@ spec_public @*/ String cpf;
    private /*@ spec_public @*/ String password;
    private /*@ spec_public @*/ List<Account> accounts;

    //@ public invariant name != null && name.length() > 0;
    //@ public invariant cpf != null && cpf.length() == 11;
    //@ public invariant password != null && password.length() > 0;
    //@ public invariant accounts != null;

    /**
     * Constructs a new customer.
     * 
     * @param name The name of the customer.
     * @param cpf The CPF of the customer (11 digits).
     * @param password The password for authentication.
     */
    //@ requires name != null && name.length() > 0;
    //@ requires cpf != null && cpf.length() == 11;
    //@ requires password != null && password.length() > 0;
    //@ ensures this.name == name;
    //@ ensures this.cpf == cpf;
    //@ ensures this.password == password;
    //@ ensures this.accounts != null && this.accounts.size() == 0;
    public Customer(String name, String cpf, String password) {
        this.name = name;
        this.cpf = cpf;
        this.password = password;
        this.accounts = new ArrayList<>();
    }

    /**
     * Adds an account to this customer.
     * 
     * @param account The account to add.
     */
    //@ requires account != null;
    //@ assignable accounts;
    //@ ensures accounts.size() == \old(accounts.size()) + 1;
    public void addAccount(Account account) {
        this.accounts.add(account);
    }

    /**
     * Retrieves an account by its number from this customer.
     * 
     * @param accountNumber The account number.
     * @return The account if found, or null otherwise.
     */
    //@ requires accountNumber > 0;
    public /*@ pure @*/ Account getAccountByNumber(int accountNumber) {
        /*@ loop_invariant 0 <= i && i <= accounts.size();
          @ loop_invariant (\forall int j; 0 <= j && j < i; accounts.get(j).getAccountNumber() != accountNumber);
          @ decreases accounts.size() - i;
          @*/
        for (int i = 0; i < accounts.size(); i++) {
            Account acc = accounts.get(i);
            if (acc.getAccountNumber() == accountNumber) {
                return acc;
            }
        }
        return null;
    }

    /**
     * Validates the customer's password.
     * 
     * @param inputPassword The password to check.
     * @return true if the password matches, false otherwise.
     */
    //@ requires inputPassword != null;
    //@ ensures \result == this.password.equals(inputPassword);
    public /*@ pure @*/ boolean checkPassword(String inputPassword) {
        return this.password.equals(inputPassword);
    }

    public /*@ pure @*/ String getName() {
        return this.name;
    }

    public /*@ pure @*/ String getCpf() {
        return this.cpf;
    }

    public /*@ pure @*/ List<Account> getAccounts() {
        return this.accounts;
    }
}
