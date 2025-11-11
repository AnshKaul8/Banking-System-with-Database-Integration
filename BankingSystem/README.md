# Banking-System-with-Database-Integration
 
Banking Management System (Java + JDBC + MySQL)

Overview
The Banking Management System is a Java-based project that simulates real-world banking operations,
such as account creation, deposits, withdrawals, and balance inquiries. It uses JDBC (Java Database Connectivity) to interact with a MySQL database, 
ensuring reliable data storage and retrieval. Designed with modularity and clarity in mind, the project demonstrates practical implementation of OOP principles and database integration in Java.

Key Features

Account Management: Create, update, and delete customer accounts with unique IDs.
Transactions: Deposit and withdraw securely with balance validation.
Data Persistence: All data stored in a connected MySQL database using JDBC.
User Authentication: Simple login system with credentials stored securely.
Error Handling: Graceful handling of invalid inputs and SQL exceptions.

Project Structure

BankingApp.java → Main driver class (handles menu, user interaction)
Accounts.java → Represents account details and core operations
AccountsManager.java → Handles database connectivity and queries (via JDBC)
User.java → Manages user credentials and authentication

Setup Instructions
Install MySQL and create a database named bankdb.
Import the SQL schema from /database/bankdb.sql.
Update database credentials in AccountsManager.java.
Compile and run BankingApp.java.

Purpose
This project is built for academic demonstration and learning — showcasing Java’s integration with SQL databases and real-world application of OOP design.
