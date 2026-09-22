package eventbookingsystem.service;

import eventbookingsystem.database.DatabaseConnection;
import eventbookingsystem.model.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class UserService {

    public UserService() {
    }

    // Method to validate user login
    public User login(String username, String password) throws SQLException {
        String query = "SELECT * FROM USERS WHERE USERNAME = ? AND PASSWORD = ?";

        try {
            Connection conn = DatabaseConnection.getinstance().getConnection();
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, username);
            pstmt.setString(2, password);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                // User found, create appropriate user object based on role
                String role = rs.getString("ROLE");
                User user = createUserByRole(role);

                user.setUserId(rs.getInt("USER_ID"));
                user.setUsername(rs.getString("USERNAME"));
                user.setPassword(rs.getString("PASSWORD"));
                user.setFullName(rs.getString("FULL_NAME"));
                user.setEmail(rs.getString("EMAIL"));

                rs.close();
                pstmt.close();

                return user;
            }

            rs.close();
            pstmt.close();
            return null; // Login failed

        } catch (SQLException e) {
            throw new SQLException("Login failed", e);
        }
    }

    // Helper method to create user object based on role (Polymorphism)
    public User createUserByRole(String role) {
        if (role.equals("Customer")) {
            return new Customer();
        } else if (role.equals("Manager")) {
            return new EventManager();
        } else if (role.equals("Admin")) {
            return new Administrator();
        }
        return null;
    }

    // Method to register a new user
    public boolean registerUser(String username, String password, String fullName,
                               String email, String role) throws SQLException {
        String query = "INSERT INTO USERS (USER_ID, USERNAME, PASSWORD, FULL_NAME, EMAIL, ROLE) " +
                      "VALUES (USER_SEQ.NEXTVAL, ?, ?, ?, ?, ?)";

        try {
            Connection conn = DatabaseConnection.getinstance().getConnection();
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setString(3, fullName);
            pstmt.setString(4, email);
            pstmt.setString(5, role);

            int rowsAffected = pstmt.executeUpdate();
            conn.commit();
            pstmt.close();

            return rowsAffected > 0;

        } catch (SQLException e) {
            throw new SQLException("User registration failed", e);
        }
    }

    // Method to get all users (for admin)
    public List<User> getAllUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM USERS ORDER BY USER_ID";

        try {
            Connection conn = DatabaseConnection.getinstance().getConnection();
            PreparedStatement pstmt = conn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String role = rs.getString("ROLE");
                User user = createUserByRole(role);

                if (user != null) {
                    user.setUserId(rs.getInt("USER_ID"));
                    user.setUsername(rs.getString("USERNAME"));
                    user.setPassword(rs.getString("PASSWORD"));
                    user.setFullName(rs.getString("FULL_NAME"));
                    user.setEmail(rs.getString("EMAIL"));

                    users.add(user);
                }
            }

            rs.close();
            pstmt.close();

        } catch (SQLException e) {
            throw new SQLException("Failed to retrieve users", e);
        }

        return users;
    }

    // Method to delete a user
    public boolean deleteUser(int userId) throws SQLException {
        String query = "DELETE FROM USERS WHERE USER_ID = ?";

        try {
            Connection conn = DatabaseConnection.getinstance().getConnection();
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, userId);

            int rowsAffected = pstmt.executeUpdate();
            conn.commit();
            pstmt.close();

            return rowsAffected > 0;

        } catch (SQLException e) {
            throw new SQLException("Failed to delete user", e);
        }
    }

    // Method to get user by ID
    public User getUserById(int userId) throws SQLException {
        String query = "SELECT * FROM USERS WHERE USER_ID = ?";

            ResultSet rs = null;
              PreparedStatement pstmt =null;
               User user = null ;


        try {
            Connection conn = DatabaseConnection.getinstance().getConnection();
             pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, userId);
             rs = pstmt.executeQuery();

            if (rs.next()) {
                String role = rs.getString("ROLE");
                 user = createUserByRole(role);

                user.setUserId(rs.getInt("USER_ID"));
                user.setUsername(rs.getString("USERNAME"));
                user.setPassword(rs.getString("PASSWORD"));
                user.setFullName(rs.getString("FULL_NAME"));
                user.setEmail(rs.getString("EMAIL"));


            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally{
           try{
           if(rs != null){
               rs.close();
           }
           if(pstmt!= null){
               pstmt.close();
           }
           }catch (SQLException e) {
            e.printStackTrace();  }

    } return user;
    }
}
