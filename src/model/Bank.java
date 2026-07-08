package model;

/**
 * Represents the Bank, which manages a list of customers.
 */
public class Bank {

    public static final int MAX_CUSTOMERS = 100;
    /*@ spec_public @*/ Customer[] customers;
    /*@ spec_public @*/ int customerCount;

    //@ public invariant customers != null;
    //@ public invariant customers.length == MAX_CUSTOMERS;
    //@ public invariant 0 <= customerCount && customerCount <= MAX_CUSTOMERS;
    //@ public invariant \elemtype(\typeof(customers)) == \type(Customer);

    /**
     * Constructs a new Bank.
     */
    //@ ensures this.customers != null;
    //@ ensures this.customers.length == MAX_CUSTOMERS;
    //@ ensures this.customerCount == 0;
    public Bank() {
        this.customers = new Customer[MAX_CUSTOMERS];
        this.customerCount = 0;
    }

    /**
     * Adds a new customer to the bank.
     * 
     * @param customer The customer to add.
     */
    //@ requires customer != null;
    //@ requires customerCount < MAX_CUSTOMERS;
    //@ assignable customers[customerCount], customerCount;
    //@ ensures customerCount == \old(customerCount) + 1;
    //@ ensures customers[\old(customerCount)] == customer;
    public void addCustomer(Customer customer) {
        this.customers[this.customerCount] = customer;
        this.customerCount++;
    }

    /**
     * Authenticates a customer by CPF and password.
     * Uses direct field access for formal verification compatibility.
     * 
     * @param cpf The customer's CPF.
     * @param password The customer's password.
     * @return The Customer if authentication is successful, null otherwise.
     */
    //@ requires cpf != null && cpf.length() == 11 && password != null;
    public /*@ pure nullable @*/ Customer authenticate(String cpf, String password) {
        /*@ nullable @*/ Customer c = findCustomerByCpf(cpf);
        if (c != null && c.password.equals(password)) {
            return c;
        }
        return null;
    }

    /**
     * Finds a customer by their CPF.
     * Uses direct field access for formal verification compatibility.
     * 
     * @param cpf The CPF to search for.
     * @return The customer if found, or null otherwise.
     */
    //@ requires cpf != null && cpf.length() == 11;
    public /*@ pure nullable @*/ Customer findCustomerByCpf(String cpf) {
        /*@ loop_invariant 0 <= i && i <= customerCount;
          @ decreases customerCount - i;
          @*/
        for (int i = 0; i < customerCount; i++) {
            Customer c = customers[i];
            if (c != null && c.cpf.equals(cpf)) {
                return c;
            }
        }
        return null;
    }

    /**
     * Finds an account by its number across all customers.
     * Uses direct field access for formal verification compatibility.
     * 
     * @param accountNumber The account number to search for.
     * @return The account if found, or null otherwise.
     */
    //@ requires accountNumber > 0;
    public /*@ pure nullable @*/ Account findAccountByNumber(int accountNumber) {
        /*@ loop_invariant 0 <= i && i <= customerCount;
          @ decreases customerCount - i;
          @*/
        for (int i = 0; i < customerCount; i++) {
            Customer c = customers[i];
            if (c != null) {
                //@ assume c.accounts != null && c.accounts.length == Customer.MAX_ACCOUNTS;
                //@ assume 0 <= c.accountCount && c.accountCount <= Customer.MAX_ACCOUNTS;
                /*@ loop_invariant 0 <= j && j <= c.accountCount;
                  @ decreases c.accountCount - j;
                  @*/
                for (int j = 0; j < c.accountCount; j++) {
                    Account acc = c.accounts[j];
                    if (acc != null && acc.accountNumber == accountNumber) {
                        return acc;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Gets the current number of customers in the bank.
     * 
     * @return The customer count.
     */
    //@ ensures \result == customerCount;
    public /*@ pure @*/ int getCustomerCount() {
        return this.customerCount;
    }
}
