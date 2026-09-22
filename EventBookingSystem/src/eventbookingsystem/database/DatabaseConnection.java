package eventbookingsystem.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DatabaseConnection {
    // Database credentials
    private static final String DB_URL = "jdbc:oracle:thin:@dan-laptop:1521:XE";
    private static final String DB_USER = "system";
    private static final String DB_PASSWORD = "0509667971";

    // Static connection instance
    private static Connection connection;
    private static DatabaseConnection instance;
    // Private constructor to prevent instantiation
     
    private DatabaseConnection() {
        try{
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            connection.setAutoCommit(false); // Disable auto-commit for manual transaction control
        } catch(SQLException e){
            e.printStackTrace();
            throw new RuntimeException("Failed to connect to database");
        }
    }

    public static DatabaseConnection getinstance(){
        if(instance==null){
            instance=new DatabaseConnection();
        }
        
        return instance;
    }
    
    
    // Method to get database connection
    public static Connection getConnection()  {
        if(instance == null) {
            getinstance();
        }
        return connection;
    }

    // Method to close database connection
    public static void closeConnection() {
        try {
            if(connection != null) {
                connection.close();
                connection = null;
                instance = null;
            }
        } catch (SQLException e) {
            System.out.println("Error closing connection: " + e.getMessage());
        }
    }  
}
