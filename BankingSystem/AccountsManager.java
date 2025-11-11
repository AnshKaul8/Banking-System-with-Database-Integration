import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountsManager {

    // Deposit (credit) money into account
    public void credit_money(int acc_no, double amount) {
        String query = "UPDATE accounts SET balance = balance + ? WHERE acc_no = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setDouble(1, amount);
            ps.setInt(2, acc_no);
            int rows = ps.executeUpdate();
            if (rows > 0)
                System.out.println("Amount credited successfully!");
            else
                System.out.println("Account not found.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Withdraw (debit) money from account
    public void debit_money(int acc_no, double amount) {
        String query = "UPDATE accounts SET balance = balance - ? WHERE acc_no = ? AND balance >= ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setDouble(1, amount);
            ps.setInt(2, acc_no);
            ps.setDouble(3, amount);
            int rows = ps.executeUpdate();
            if (rows > 0)
                System.out.println("Amount debited successfully!");
            else
                System.out.println("Insufficient balance or account not found.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Transfer money between accounts
    public void transfer_money(int fromAcc, int toAcc, double amount) {
        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);

            debit_money(fromAcc, amount);
            credit_money(toAcc, amount);

            conn.commit();
            System.out.println("Transfer successful!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Check account balance
    public void check_balance(int acc_no) {
        String query = "SELECT balance FROM accounts WHERE acc_no = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, acc_no);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                System.out.println("Current Balance: ₹" + rs.getDouble("balance"));
            } else {
                System.out.println("Account not found.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Insert a new account into the database
    public void createAccount(Accounts account) {
        String query = "INSERT INTO accounts (acc_no, name, balance) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, account.getAccNo());
            ps.setString(2, account.getName());
            ps.setDouble(3, account.getBalance());
            int rows = ps.executeUpdate();
            if (rows > 0)
                System.out.println("Account created successfully!");
            else
                System.out.println("Failed to create account.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Public wrapper for deposit
    public void deposit(int acc_no, double amount) {
        credit_money(acc_no, amount);
    }

    // Public wrapper for withdraw
    public void withdraw(int acc_no, double amount) {
        debit_money(acc_no, amount);
    }

    // Public wrapper for transfer
    public void transfer(int fromAcc, int toAcc, double amount) {
        transfer_money(fromAcc, toAcc, amount);
    }

    // Public method to fetch and return the account balance
    public double getBalance(int acc_no) {
        String query = "SELECT balance FROM accounts WHERE acc_no = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, acc_no);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getDouble("balance");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
}