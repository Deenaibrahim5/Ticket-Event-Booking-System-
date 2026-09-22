package eventbookingsystem.controller;

import eventbookingsystem.database.DatabaseConnection;
import eventbookingsystem.model.User;
import eventbookingsystem.service.UserService;
import java.io.IOException;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorLabel;

    private UserService userService;

    // Initialize method - called after FXML is loaded
    @FXML
    public void initialize() {
        userService = new UserService();
    }

    // Handle login button action
    @FXML
    private void handleLogin(ActionEvent event) {
        String username = usernameField.getText().trim();
        String password = passwordField.getText();

        // Validate input
        if (username.isEmpty() || password.isEmpty()) {
            errorLabel.setText("Please enter username and password");
            return;
        }

        try {
            // Attempt login
            User user = userService.login(username, password);

            if (user != null) {
                // Login successful - navigate based on role
                errorLabel.setText("");
                navigateToDashboard(event, user);
            } else {
                // Login failed
                errorLabel.setText("Invalid username or password");
            }

        } catch (SQLException e) {
            errorLabel.setText("Database error: " + e.getMessage());
        }
    }

    // Navigate to appropriate dashboard based on user role (Polymorphism)
    private void navigateToDashboard(ActionEvent event, User user) {
        try {
            String fxmlFile = "";
            String role = user.getRole();

            // Determine which dashboard to load
            if (role.equals("Customer")) {
                fxmlFile = "/eventbookingsystem/UI/CustomerDashboard.fxml";
            } else if (role.equals("Manager")) {
                fxmlFile = "/eventbookingsystem/UI/ManagerDashboard.fxml";
            } else if (role.equals("Admin")) {
                fxmlFile = "/eventbookingsystem/UI/AdminDashboard.fxml";
            }

            // Load FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = loader.load();

            // Pass user data to controller
            if (role.equals("Customer")) {
                CustomerDashboardController controller = loader.getController();
                controller.setUser(user);
            } else if (role.equals("Manager")) {
                ManagerDashboardController controller = loader.getController();
                controller.setUser(user);
            } else if (role.equals("Admin")) {
                AdminDashboardController controller = loader.getController();
                controller.setUser(user);
            }

            // Switch scene
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            errorLabel.setText("Error loading dashboard: " + e.getMessage());
        }
    }

    // Handle register button action
    @FXML
    private void handleRegister(ActionEvent event) {
        try {
            // Load registration screen
            Parent root = FXMLLoader.load(getClass().getResource("/eventbookingsystem/UI/Registration.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            errorLabel.setText("Error loading registration: " + e.getMessage());
        }
    }
}
