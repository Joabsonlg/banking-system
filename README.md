# Banking System (Java SE + JML)

This is a pure Java SE application that implements an advanced Banking System using the **Model-View-Controller (MVC)** architectural pattern. It was developed as the final project for the "Applied Logic to Software Engineering" (LAES) course.

A key feature of this project is the integration of the **Java Modeling Language (JML)**. The domain models are formally specified using Design by Contract principles (Pre-conditions, Post-conditions, Invariants, etc.) to mathematically guarantee the correctness of operations like deposits, withdrawals, and transfers between multiple accounts.

## Features

- **Pure Java SE**: No external frameworks like Spring or Hibernate were used, keeping the codebase clean and focused on formal logic.
- **MVC Architecture**: Clear separation of concerns between business rules (`Model`), console interactions (`View`), and user input orchestration (`Controller`).
- **Authentication & Sessions**: Customers must register with a CPF and password, and login to manage their accounts.
- **Multiple Accounts**: A single customer can open and manage multiple bank accounts.
- **Transfers**: Customers can transfer money between their own accounts or to accounts owned by other customers.
- **JML Specifications**: Extensive use of `//@ requires`, `//@ ensures`, `//@ assignable`, and `//@ invariant` to enforce business rules statically, including complex state changes across multiple objects.

## Project Structure

```text
BankingSystem/
│
├── src/
│   ├── model/
│   │   ├── Account.java     # Contains core business rules and JML contracts
│   │   ├── Customer.java    # Manages customer sessions and lists of accounts
│   │   └── Bank.java        # Manages global state and authentication
│   │
│   ├── view/
│   │   └── ConsoleView.java # Handles all terminal input/output
│   │
│   ├── controller/
│   │   └── BankController.java # Connects View to Model, manages sessions
│   │
│   └── Main.java            # Application entry point
│
├── run.bat                  # Build and execution script for Windows
└── README.md
```

## Prerequisites

To compile and run this project, you need:
- **Java Development Kit (JDK)** installed and configured in your system's `PATH`.

*(Note: If you intend to run the OpenJML static analyzer on this code, ensure you are using a JDK version compatible with your OpenJML installation, typically JDK 8 or 11).*

## How to Run (Windows)

The project includes an automated batch script to compile the source code and start the application.

1. Open your terminal (Command Prompt or PowerShell) or File Explorer.
2. Navigate to the root directory of the project (`BankingSystem`).
3. Run the script:
   - Double-click the `run.bat` file in File Explorer.
   - OR type `.\run.bat` in your terminal.

The script will compile all `.java` files into a `bin/` folder and immediately launch the interactive Console menu.

## JML Example

Here is a glimpse of how JML guarantees state correctness during a transfer operation in `Account.java`, ensuring no money is created or lost:

```java
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
```
