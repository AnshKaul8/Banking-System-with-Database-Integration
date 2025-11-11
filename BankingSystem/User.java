import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

//Class User declaration
public class User {
    private Connection connection;
    private Scanner scanner;

//Constructor Initialization
    public User(Connection connection, Scanner scanner){
        this.connection = connection;
        this.scanner = scanner;
    }

