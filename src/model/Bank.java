package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the Bank, which manages a list of customers.
 */
public class Bank {

    private /*@ spec_public @*/ List<Customer> customers;

    //@ public invariant customers != null;

    /**
     * Constructs a new Bank.
     */
    //@ ensures this.customers != null;
    //@ ensures this.customers.size() == 0;
    public Bank() {
        this.customers = new ArrayList<>();
    }

    /**
     * Adds a new customer to the bank.
     * 
     * @param customer The customer to add.
     */
    //@ requires customer != null;
    //@ assignable customers;
    //@ ensures customers.size() == \old(customers.size()) + 1;
    public void addCustomer(Customer customer) {
        this.customers.add(customer);
    }

    /**
     * Authenticates a customer by CPF and password.
     * 
     * @param cpf The customer's CPF.
     * @param password The customer's password.
     * @return The Customer if authentication is successful, null otherwise.
     */
    //@ requires cpf != null && password != null;
    public /*@ pure @*/ Customer authenticate(String cpf, String password) {
        Customer c = findCustomerByCpf(cpf);
        if (c != null && c.checkPassword(password)) {
            return c;
        }
        return null;
    }

    /**
     * Finds a customer by their CPF.
     * 
     * @param cpf The CPF to search for.
     * @return The customer if found, or null otherwise.
     */
    //@ requires cpf != null && cpf.length() == 11;
    //@ ensures \result == null || \result.getCpf().equals(cpf);
    public /*@ pure @*/ Customer findCustomerByCpf(String cpf) {
        for (Customer c : customers) {
            if (c.getCpf().equals(cpf)) {
                return c;
            }
        }
        return null;
    }

    /**
     * Finds an account by its number across all customers.
     * 
     * @param accountNumber The account number to search for.
     * @return The account if found, or null otherwise.
     */
    //@ requires accountNumber > 0;
    public /*@ pure @*/ Account findAccountByNumber(int accountNumber) {
        for (Customer c : customers) {
            Account acc = c.getAccountByNumber(accountNumber);
            if (acc != null) {
                return acc;
            }
        }
        return null;
    }
}
