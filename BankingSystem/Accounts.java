import java.sql.*;
import java.util.Random;

public class Accounts {

    private String name;
    private double balance;
    private int accNo;

    public Accounts(String name, double balance) {
        this.name = name;
        this.balance = balance;
        this.accNo = generate_account_number();
    }

    public String getName() {
        return name;
    }

    public double getBalance() {
        return balance;
    }

    public int getAccNo() {
        return accNo;
    }

    // Opens a new account in the database
    public void open_account(String name, double initialBalance) {
        String query = "INSERT INTO accounts (name, balance) VALUES (?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, name);
            ps.setDouble(2, initialBalance);
            ps.executeUpdate();
            System.out.println("Account opened successfully for " + name);

        } catch (SQLException e) {
            System.out.println("Error while opening account:");
            e.printStackTrace();
        }
    }

    // Generate a random 6-digit account number
    public int generate_account_number() {
        Random rand = new Random();
        return 100000 + rand.nextInt(900000);
    }

    // Fetch the account number from the database for a given name
    public int get_account_number(String name) {
        String query = "SELECT acc_no FROM accounts WHERE name = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("acc_no");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // not found
    }

    // Check if an account already exists
    public boolean account_exists(String name) {
        String query = "SELECT COUNT(*) FROM accounts WHERE name = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}