import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;



//Register Function

public void register(){
        scanner.nextLine();
    //Taking User Details
        System.out.print("Full Name: ");
        String full_name = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        
        //Error Handling
        // Checking if User with same Email already exists
    
        if(User_exist(email)) {
            System.out.println("User Already Exists for this Email Address!!");
            return;
        }
        
        //SQL QUERY
        // Here we are inserting a new user into the table
        String register_query = "INSERT INTO users(full_name, email, password) VALUES(?, ?, ?)";

        //Try-With-Resources Block
        try (PreparedStatement preparedStatement = connection.prepareStatement(register_query)) {
            preparedStatement.setString(1, full_name);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, password);
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Registration Successfull!");
            } else {
                System.out.println("Registration Failed!");
            }
        } catch (SQLException e) {
            // Prints if there is any Error
            e.printStackTrace();
        }

    }
    public boolean User_exist(String email) {
        String query = "SELECT COUNT(*) FROM users WHERE email = ?";
        try (
            PreparedStatement preparedStatement = connection.prepareStatement(query)
        ) {
            preparedStatement.setString(1, email);
            try (java.sql.ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean login() {
        scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        String query = "SELECT COUNT(*) FROM users WHERE email = ? AND password = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            try (java.sql.ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    if (count > 0) {
                        System.out.println("Login Successful!");
                        return true;
                    } else {
                        System.out.println("Invalid email or password.");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
