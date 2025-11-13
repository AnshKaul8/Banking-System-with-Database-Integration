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

    // Transfer money between accounts (minimal logic)
    public void transfer_money(int fromAcc, int toAcc, double amount) {
        double currentBalance = getBalance(fromAcc);

        if (currentBalance < 0) {
            System.out.println("Source account not found.");
            return;
        }

        if (currentBalance < amount) {
            System.out.println("Insufficient balance.");
            return;
        }

        // Perform debit and credit using existing methods
        debit_money(fromAcc, amount);
        credit_money(toAcc, amount);

        System.out.println("Transfer successful!");
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
        double minBalance;
        switch (account.getAccountType().toLowerCase()) {
            case "savings":
                minBalance = 1000;
                break;
            case "current":
                minBalance = 5000;
                break;
            case "fixed deposit":
                minBalance = 10000;
                break;
            default:
                minBalance = 0;
                break;
        }

        if (account.getBalance() < minBalance) {
            System.out.println("Minimum balance for " + account.getAccountType() + " is " + minBalance);
            return;
        }

        String query = "INSERT INTO accounts (acc_no, name, balance, accountType) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, account.getAccNo());
            ps.setString(2, account.getName());
            ps.setDouble(3, account.getBalance());
            ps.setString(4, account.getAccountType());
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