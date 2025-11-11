import java.sql.*;
import java.util.*;

public class BankingApp {
    public static void main(String[] args) {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DBConnection.getConnection();
            if (conn != null) {
                System.out.println("Connected to the database successfully.");
            } else {
                System.out.println("Failed to connect to the database.");
                return;
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Error loading driver or connecting to database: " + e.getMessage());
            return;
        }

        Scanner scanner = new Scanner(System.in);
        User user = new User(conn, scanner);
        boolean loggedIn = false;
        while (!loggedIn) {
            System.out.println("\nWelcome! Please choose an option:");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline
            switch (choice) {
                case 1:
                    user.register();
                    break;
                case 2:
                    loggedIn = user.login();
                    if (!loggedIn) {
                        System.out.println("Login failed. Please try again.");
                    }
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

        AccountsManager manager = new AccountsManager();
        boolean exit = false;

        while (!exit) {
            System.out.println("\nBanking System Menu:");
            System.out.println("1. Open Account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Transfer");
            System.out.println("5. Check Balance");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter account holder name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter initial deposit amount: ");
                    double initialDeposit = scanner.nextDouble();
                    scanner.nextLine();
                    Accounts newAccount = new Accounts(name, initialDeposit);
                    manager.createAccount(newAccount);
                    System.out.println("Account created successfully.");
                    break;
                case 2:
                    System.out.print("Enter account ID: ");
                    int depositId = scanner.nextInt();
                    System.out.print("Enter deposit amount: ");
                    double depositAmount = scanner.nextDouble();
                    scanner.nextLine();
                    manager.deposit(depositId, depositAmount);
                    break;
                case 3:
                    System.out.print("Enter account ID: ");
                    int withdrawId = scanner.nextInt();
                    System.out.print("Enter withdrawal amount: ");
                    double withdrawAmount = scanner.nextDouble();
                    scanner.nextLine();
                    manager.withdraw(withdrawId, withdrawAmount);
                    break;
                case 4:
                    System.out.print("Enter source account ID: ");
                    int fromId = scanner.nextInt();
                    System.out.print("Enter destination account ID: ");
                    int toId = scanner.nextInt();
                    System.out.print("Enter transfer amount: ");
                    double transferAmount = scanner.nextDouble();
                    scanner.nextLine();
                    manager.transfer(fromId, toId, transferAmount);
                    break;
                case 5:
                    System.out.print("Enter account ID: ");
                    int checkId = scanner.nextInt();
                    scanner.nextLine();
                    double balance = manager.getBalance(checkId);
                    System.out.println("Current balance: " + balance);
                    break;
                case 6:
                    exit = true;
                    System.out.println("Exiting the application.");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        scanner.close();
    }
}
