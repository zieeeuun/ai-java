package com.example.diary.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {

    // Database connection details - these should ideally be loaded from a config file or environment variables
    private static final String DB_URL = "jdbc:mysql://localhost:3306/diary_db"; // Replace with your DB name
    private static final String DB_USER = "root"; // Replace with your DB username
    private static final String DB_PASSWORD = "password"; // Replace with your DB password

    private static Connection connection = null;
    private static Boolean driverAvailable = null;
    private static boolean databaseReachable = true;

    public static boolean isDatabaseAvailable() {
        if (!databaseReachable) {
            return false;
        }
        if (driverAvailable != null) {
            return driverAvailable;
        }
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            driverAvailable = true;
        } catch (ClassNotFoundException e) {
            driverAvailable = false;
            System.err.println("[INFO] MySQL JDBC 드라이버가 없어 메모리 모드로 동작합니다.");
        }
        return driverAvailable;
    }

    // Method to get a database connection
    public static Connection getConnection() throws SQLException {
        if (!isDatabaseAvailable()) {
            throw new SQLException("Database unavailable");
        }
        if (connection == null || connection.isClosed()) {
            try {
                // Load the JDBC driver (optional for modern JDBC versions, but good practice)
                connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                System.out.println("Database connection established successfully.");
            } catch (SQLException e) {
                databaseReachable = false;
                System.err.println("[INFO] DB 연결 실패로 메모리 모드로 전환합니다: " + e.getMessage());
                throw new SQLException("Database connection failed", e);
            }
        }
        return connection;
    }

    // Method to close the database connection
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Database connection closed.");
            } catch (SQLException e) {
                System.err.println("Error closing database connection.");
                e.printStackTrace();
            }
        }
    }

    // --- For initial setup: Method to create the table if it doesn't exist ---
    // This might be called once when the application starts or manually
    public static void createTableIfNotExists() {
        if (!isDatabaseAvailable()) {
            return;
        }
        try (Connection conn = getConnection();
             java.sql.Statement stmt = conn.createStatement()) {

            String createTableSQL = "CREATE TABLE IF NOT EXISTS diary_entries (" +
                                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                                    "title VARCHAR(255) NOT NULL," +
                                    "content TEXT NOT NULL," +
                                    "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                                    "updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP" +
                                    ") ENGINE=InnoDB";
            stmt.executeUpdate(createTableSQL);
            System.out.println("Table 'diary_entries' checked/created successfully.");

        } catch (SQLException e) {
            System.err.println("[INFO] 테이블 초기화를 건너뜁니다(메모리 모드): " + e.getMessage());
        }
    }
}
