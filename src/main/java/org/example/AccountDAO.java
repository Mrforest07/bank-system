
package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;

public class AccountDAO {



    public int createAccount(String holderName, BigDecimal balance)
            throws SQLException {

        String sql = "INSERT INTO accounts (holder_name, balance) VALUES (?, ?)";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(
                     sql,
                     java.sql.Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, holderName);
            ps.setBigDecimal(2, balance);

            int rows = ps.executeUpdate();

            if (rows != 1) {
                return -1;
            }

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    return keys.getInt(1);
                }
            }

            return -1;
        }
    }



    public int[] createAccountsBatch(List<Account> accounts)
            throws SQLException {

        String sql = "INSERT INTO accounts (holder_name, balance) VALUES (?, ?)";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            con.setAutoCommit(false);

            try {
                for (Account account : accounts) {
                    ps.setString(1, account.getHolderName());
                    ps.setBigDecimal(2, account.getBalance());
                    ps.addBatch();
                }

                int[] results = ps.executeBatch();

                con.commit();
                return results;

            } catch (SQLException e) {
                try {
                    con.rollback();
                } catch (SQLException rollbackError) {
                    e.addSuppressed(rollbackError);
                }

                throw e;
            }
        }
    }





    public boolean depositMoney(int accountId, BigDecimal amount)
            throws SQLException {
        String sql = "UPDATE accounts SET balance = balance + ? WHERE account_id = ?";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setBigDecimal(1, amount);
            ps.setInt(2, accountId);

            return ps.executeUpdate() == 1;

        }
    }



    public List<Account> getAllAccounts() throws SQLException {
        List<Account> accounts = new ArrayList<>();

        String sql = "SELECT account_id, holder_name, balance FROM accounts";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Account account = new Account(
                        rs.getInt("account_id"),
                        rs.getString("holder_name"),
                        rs.getBigDecimal("balance")
                );

                accounts.add(account);
            }

        }

        return accounts;
    }



    public boolean deleteAccount(int accountId) throws SQLException {
        String sql = "DELETE FROM accounts WHERE account_id = ?";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, accountId);

            return ps.executeUpdate() == 1;
        }
    }




    public boolean transferMoney(int senderId, int receiverId, BigDecimal amount)
            throws SQLException {

        if (senderId <= 0 || receiverId <= 0
                || senderId == receiverId
                || amount == null
                || amount.compareTo(BigDecimal.ZERO) <= 0) {
            return false;
        }

        String deductSql = "UPDATE accounts "
                + "SET balance = balance - ? "
                + "WHERE account_id = ? AND balance >= ?";

        String addSql = "UPDATE accounts "
                + "SET balance = balance + ? "
                + "WHERE account_id = ?";

        try (Connection con = getConnection()) {
            con.setAutoCommit(false);

            try {
                try (PreparedStatement deduct = con.prepareStatement(deductSql);
                     PreparedStatement add = con.prepareStatement(addSql)) {

                    deduct.setBigDecimal(1, amount);
                    deduct.setInt(2, senderId);
                    deduct.setBigDecimal(3, amount);

                    if (deduct.executeUpdate() != 1) {
                        con.rollback();
                        return false;
                    }

                    add.setBigDecimal(1, amount);
                    add.setInt(2, receiverId);

                    if (add.executeUpdate() != 1) {
                        con.rollback();
                        return false;
                    }
                }

                con.commit();
                return true;

            } catch (SQLException e) {
                try {
                    con.rollback();
                } catch (SQLException rollbackError) {
                    e.addSuppressed(rollbackError);
                }
                throw e;
            }
        }
    }



    private Connection getConnection() throws SQLException {
        String url = System.getenv("DB_URL");
        String user = System.getenv("DB_USER");
        String password = System.getenv("DB_PASSWORD");

        if (url == null || user == null || password == null) {
            throw new SQLException(
                    "Database environment variables are not configured."
            );
        }

        return DriverManager.getConnection(url, user, password);
    }

}
