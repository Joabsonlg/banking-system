package controller;

import model.Account;
import model.Bank;
import model.Customer;
import view.ConsoleView;

import java.util.List;

/**
 * The controller that orchestrates the banking system flow.
 */
public class BankController {

    private Bank bank;
    private ConsoleView view;
    private int nextAccountNumber;
    private Customer currentSession;

    public BankController(Bank bank, ConsoleView view) {
        this.bank = bank;
        this.view = view;
        this.nextAccountNumber = 1;
        this.currentSession = null;
    }

    public void start() {
        boolean running = true;
        while (running) {
            if (currentSession == null) {
                int option = view.showMainMenu();
                switch (option) {
                    case 1:
                        login();
                        break;
                    case 2:
                        registerCustomer();
                        break;
                    case 3:
                        running = false;
                        view.displayMessage("Thank you for using the Banking System.");
                        break;
                    default:
                        view.displayMessage("Invalid option. Please try again.");
                }
            } else {
                int option = view.showSessionMenu(currentSession.getName());
                switch (option) {
                    case 1:
                        openAccount();
                        break;
                    case 2:
                        deposit();
                        break;
                    case 3:
                        withdraw();
                        break;
                    case 4:
                        transfer();
                        break;
                    case 5:
                        listAccounts();
                        break;
                    case 6:
                        logout();
                        break;
                    default:
                        view.displayMessage("Invalid option. Please try again.");
                }
            }
        }
    }

    private void login() {
        String cpf = view.getStringInput("Enter your CPF (11 digits): ");
        String password = view.getStringInput("Enter your password: ");
        
        Customer customer = bank.authenticate(cpf, password);
        if (customer != null) {
            this.currentSession = customer;
            view.displayMessage("Login successful!");
        } else {
            view.displayMessage("Error: Invalid CPF or Password.");
        }
    }

    private void logout() {
        this.currentSession = null;
        view.displayMessage("Logged out successfully.");
    }

    private void registerCustomer() {
        String name = view.getStringInput("Enter customer name: ");
        String cpf = view.getStringInput("Enter customer CPF (11 digits): ");
        String password = view.getStringInput("Enter a password: ");

        if (cpf == null || cpf.length() != 11) {
            view.displayMessage("Error: CPF must have exactly 11 digits.");
            return;
        }

        if (name == null || name.isEmpty() || password == null || password.isEmpty()) {
            view.displayMessage("Error: Name and password cannot be empty.");
            return;
        }

        if (bank.findCustomerByCpf(cpf) != null) {
            view.displayMessage("Error: Customer with this CPF already exists.");
            return;
        }

        Customer customer = new Customer(name, cpf, password);
        bank.addCustomer(customer);
        view.displayMessage("Customer registered successfully! You can now login.");
    }

    private void openAccount() {
        Account account = new Account(nextAccountNumber++);
        currentSession.addAccount(account);
        view.displayMessage("Account created successfully! Your Account Number is: " + account.getAccountNumber());
    }

    private void deposit() {
        int accNumber = view.getIntInput("Enter your account number: ");
        Account account = currentSession.getAccountByNumber(accNumber);

        if (account == null) {
            view.displayMessage("Error: Account not found in your session.");
            return;
        }

        double amount = view.getDoubleInput("Enter amount to deposit: ");
        if (amount <= 0) {
            view.displayMessage("Error: Deposit amount must be positive.");
            return;
        }

        account.deposit(amount);
        view.displayMessage("Deposit successful. New balance: $" + account.getBalance());
    }

    private void withdraw() {
        int accNumber = view.getIntInput("Enter your account number: ");
        Account account = currentSession.getAccountByNumber(accNumber);

        if (account == null) {
            view.displayMessage("Error: Account not found in your session.");
            return;
        }

        double amount = view.getDoubleInput("Enter amount to withdraw: ");
        if (amount <= 0) {
            view.displayMessage("Error: Withdrawal amount must be positive.");
            return;
        }

        if (account.getBalance() < amount) {
            view.displayMessage("Error: Insufficient funds.");
            return;
        }

        account.withdraw(amount);
        view.displayMessage("Withdrawal successful. New balance: $" + account.getBalance());
    }

    private void transfer() {
        int fromAccNumber = view.getIntInput("Enter your source account number: ");
        Account sourceAccount = currentSession.getAccountByNumber(fromAccNumber);

        if (sourceAccount == null) {
            view.displayMessage("Error: Source account not found in your session.");
            return;
        }

        int toAccNumber = view.getIntInput("Enter the destination account number: ");
        Account destAccount = bank.findAccountByNumber(toAccNumber);

        if (destAccount == null) {
            view.displayMessage("Error: Destination account not found.");
            return;
        }

        if (sourceAccount == destAccount) {
            view.displayMessage("Error: Cannot transfer to the same account.");
            return;
        }

        double amount = view.getDoubleInput("Enter amount to transfer: ");
        if (amount <= 0) {
            view.displayMessage("Error: Transfer amount must be positive.");
            return;
        }

        if (sourceAccount.getBalance() < amount) {
            view.displayMessage("Error: Insufficient funds.");
            return;
        }

        sourceAccount.transfer(destAccount, amount);
        view.displayMessage("Transfer successful!");
        view.displayMessage("Your new balance: $" + sourceAccount.getBalance());
    }

    private void listAccounts() {
        List<Account> accounts = currentSession.getAccounts();
        if (accounts.isEmpty()) {
            view.displayMessage("You don't have any accounts yet.");
            return;
        }
        
        view.displayMessage("\n--- Your Accounts ---");
        for (Account acc : accounts) {
            view.displayMessage("Account Number: " + acc.getAccountNumber() + " | Balance: $" + acc.getBalance());
        }
        view.displayMessage("---------------------");
    }
}
