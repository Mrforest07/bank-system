package org.example;

import java.util.Arrays;
import java.util.List;
import java.math.BigDecimal;
import java.util.Scanner;


public class Main {

    private static void createAccount(Scanner sc) {
        System.out.print("Enter account holder name: ");
        sc.nextLine(); // Consume leftover newline
        String name = sc.nextLine();

        System.out.print("Enter initial balance: ");
        BigDecimal balance = sc.nextBigDecimal();


        try {
            BankService bankService = new BankService();

            int accountId = bankService.createAccount(name, balance);

            if (accountId > 0) {
                System.out.println("Account created successfully!");
                System.out.println("Your Account ID: " + accountId);
            } else {
                System.out.println("Account creation failed.");
            }

        } catch (java.sql.SQLException e) {
            System.out.println("Database error: Could not create account.");
        }

    }


    private static void viewAllAccounts() {
        BankService bankService = new BankService();

        try {
            java.util.List<Account> accounts = bankService.getAllAccounts();

            System.out.println("\n===== ALL ACCOUNTS =====");

            if (accounts.isEmpty()) {
                System.out.println("No accounts found.");
                return;
            }

            for (Account account : accounts) {
                System.out.println(
                        "ID: " + account.getAccountId()
                                + " | Name: " + account.getHolderName()
                                + " | Balance: ₹" + account.getBalance()
                );
            }

        } catch (java.sql.SQLException e) {
            System.out.println("Could not retrieve accounts due to a database error.");
        }
    }


    private static void depositMoney(Scanner sc) {
        System.out.print("Enter account ID: ");
        int accountId = sc.nextInt();

        System.out.print("Enter deposit amount: ");
        BigDecimal amount = sc.nextBigDecimal();

        try {
            BankService bankService = new BankService();

            boolean deposited = bankService.depositMoney(accountId, amount);

            if (deposited) {
                System.out.println("Money deposited successfully!");
            } else {
                System.out.println("Deposit failed. Check the account ID and amount.");
            }

        } catch (java.sql.SQLException e) {
            System.out.println("Database error: Could not deposit money.");
        }
    }



    private static void transferMoney(Scanner sc) {
        System.out.print("Enter sender account ID: ");
        int senderId = sc.nextInt();

        System.out.print("Enter receiver account ID: ");
        int receiverId = sc.nextInt();

        System.out.print("Enter transfer amount: ");
        BigDecimal amount = sc.nextBigDecimal();

        try {
            BankService bankService = new BankService();

            boolean transferred = bankService.transferMoney(
                    senderId, receiverId, amount
            );

            if (transferred) {
                System.out.println("Transfer completed successfully!");
            } else {
                System.out.println(
                        "Transfer failed. Check account IDs, balance, and amount."
                );
            }

        } catch (java.sql.SQLException e) {
            System.out.println("Database error: Could not complete the transfer.");
        }
    }

    private static void deleteAccount(Scanner sc) {
        System.out.print("Enter account ID to delete: ");
        int accountId = sc.nextInt();

        try {
            BankService bankService = new BankService();

            boolean deleted = bankService.deleteAccount(accountId);

            if (deleted) {
                System.out.println("Account deleted successfully!");
            } else {
                System.out.println("Deletion failed. Account ID not found or invalid.");
            }

        } catch (java.sql.SQLException e) {
            System.out.println("Database error: Could not delete account.");
        }
    }


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);



        while (true) {
            System.out.println("\n===== FOREST BANK =====");
            System.out.println("1. Create Account");
            System.out.println("2. View All Accounts");
            System.out.println("3. Deposit Money");
            System.out.println("4. Delete Account");
            System.out.println("5. Transfer Money");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");

            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    createAccount(sc);

                    break;
                case 2:
                    viewAllAccounts();
                    break;
                case 3:
                    depositMoney(sc);
                    break;
                case 4:
                    deleteAccount(sc);
                    break;

                case 5:
                    transferMoney(sc);
                    break;
                case 6:
                    System.out.println("Thank you for using Forest Bank!");
                    sc.close();
                    return;
            }
        }
    }
}
