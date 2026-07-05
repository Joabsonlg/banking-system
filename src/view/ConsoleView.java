package view;

import java.util.Scanner;

/**
 * Handles the interaction with the user via console.
 */
public class ConsoleView {

    private Scanner scanner;

    public ConsoleView() {
        this.scanner = new Scanner(System.in);
    }

    public int showMainMenu() {
        System.out.println("\n=== Banking System - Main Menu ===");
        System.out.println("1. Login");
        System.out.println("2. Register New Customer");
        System.out.println("3. Exit");
        System.out.print("Select an option: ");
        return getValidInt();
    }

    public int showSessionMenu(String customerName) {
        System.out.println("\n=== Welcome, " + customerName + " ===");
        System.out.println("1. Open New Account");
        System.out.println("2. Deposit");
        System.out.println("3. Withdraw");
        System.out.println("4. Transfer to another account");
        System.out.println("5. List my accounts and balances");
        System.out.println("6. Logout");
        System.out.print("Select an option: ");
        return getValidInt();
    }

    private int getValidInt() {
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter a number.");
            scanner.next(); 
            System.out.print("Select an option: ");
        }
        return scanner.nextInt();
    }

    public String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.next();
    }

    public double getDoubleInput(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextDouble()) {
            System.out.println("Invalid input. Please enter a valid number.");
            scanner.next();
            System.out.print(prompt);
        }
        return scanner.nextDouble();
    }

    public int getIntInput(String prompt) {
        System.out.print(prompt);
        return getValidInt();
    }

    public void displayMessage(String message) {
        System.out.println(message);
    }
}
