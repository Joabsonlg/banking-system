package model;

/**
 * Represents a customer of the bank.
 * A customer can have multiple accounts and a password for authentication.
 */
public final class Customer {

    public static final int MAX_ACCOUNTS = 10;

    /*@ spec_public @*/ String name;
    /*@ spec_public @*/ String cpf;
    /*@ spec_public @*/ String password;
    /*@ spec_public @*/ Account[] accounts;
    /*@ spec_public @*/ int accountCount;

    //@ public invariant name != null && name.length() > 0;
    //@ public invariant cpf != null && cpf.length() == 11;
    //@ public invariant password != null && password.length() > 0;
    //@ public invariant accounts != null;
    //@ public invariant accounts.length == MAX_ACCOUNTS;
    //@ public invariant 0 <= accountCount && accountCount <= MAX_ACCOUNTS;
    //@ public invariant \elemtype(\typeof(accounts)) == \type(Account);

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
    //@ ensures this.accounts != null && this.accountCount == 0;
    public Customer(String name, String cpf, String password) {
        this.name = name;
        this.cpf = cpf;
        this.password = password;
        this.accounts = new Account[MAX_ACCOUNTS];
        this.accountCount = 0;
    }

    /**
     * Adds an account to this customer.
     * 
     * @param account The account to add.
     */
    //@ requires account != null;
    //@ requires accountCount < MAX_ACCOUNTS;
    //@ assignable accounts[accountCount], accountCount;
    //@ ensures accountCount == \old(accountCount) + 1;
    //@ ensures accounts[\old(accountCount)] == account;
    public void addAccount(Account account) {
        this.accounts[this.accountCount] = account;
        this.accountCount++;
    }

    /**
     * Retrieves an account by its number from this customer.
     * Uses direct field access to the account number for formal verification compatibility.
     * 
     * @param accountNumber The account number.
     * @return The account if found, or null otherwise.
     */
    //@ requires accountNumber > 0;
    public /*@ pure nullable @*/ Account getAccountByNumber(int accountNumber) {
        /*@ loop_invariant 0 <= i && i <= accountCount;
          @ decreases accountCount - i;
          @*/
        for (int i = 0; i < accountCount; i++) {
            Account acc = accounts[i];
            if (acc != null && acc.accountNumber == accountNumber) {
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

    public /*@ pure @*/ Account[] getAccounts() {
        return this.accounts;
    }

    public /*@ pure @*/ int getAccountCount() {
        return this.accountCount;
    }
}
