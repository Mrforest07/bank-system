
package org.example;

import java.math.BigDecimal;

public class BankService {

    public int createAccount(String holderName, BigDecimal balance)
            throws java.sql.SQLException {

        if (holderName == null || holderName.trim().isEmpty()) {
            System.out.println("Account holder name cannot be empty.");
            return -1;
        }

        if (balance == null || balance.compareTo(BigDecimal.ZERO) < 0) {
            System.out.println("Balance cannot be negative.");
            return -1;
        }

        return accountDAO.createAccount(holderName.trim(), balance);
    }

    private final AccountDAO accountDAO = new AccountDAO();

    public boolean depositMoney(int accountId, BigDecimal amount)
            throws java.sql.SQLException {

        if (accountId <= 0) {
            System.out.println("Invalid account ID.");
            return false;
        }

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            System.out.println("Deposit amount must be positive.");
            return false;
        }

        return accountDAO.depositMoney(accountId, amount);
    }



    public boolean transferMoney(int senderId, int receiverId, BigDecimal amount)
            throws java.sql.SQLException {

        if (senderId <= 0 || receiverId <= 0) {
            System.out.println("Invalid account ID.");
            return false;
        }

        if (senderId == receiverId) {
            System.out.println("Sender and receiver must be different.");
            return false;
        }

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            System.out.println("Transfer amount must be positive.");
            return false;
        }

        return accountDAO.transferMoney(senderId, receiverId, amount);
    }



    public java.util.List<Account> getAllAccounts() throws java.sql.SQLException {
        return accountDAO.getAllAccounts();
    }

    public boolean deleteAccount(int accountId) throws java.sql.SQLException {
        if (accountId <= 0) {
            System.out.println("Invalid account ID.");
            return false;
        }

        return accountDAO.deleteAccount(accountId);
    }
}

