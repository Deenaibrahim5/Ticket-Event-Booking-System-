package eventbookingsystem.controller;

import eventbookingsystem.database.DatabaseConnection;
import eventbookingsystem.service.UserService;
import java.io.IOException;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RegistrationController {

    @FXML
    private TextField fullNameField;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField emailField;

    @FXML
    private ComboBox<String> roleComboBox;

    @FXML
    private Label messageLabel;

    private UserService userService;

    // Constructor
    public RegistrationController() {
        userService = new UserService();
    }

    // Initialize method - called after FXML loading
    @FXML
    public void initialize() {
        // Populate role combo box
        roleComboBox.setItems(FXCollections.observableArrayList("Customer", "Manager", "Admin"));
        roleComboBox.getSelectionModel().selectFirst();
    }

    // Handle register button action
    @FXML
    private void handleRegister(ActionEvent event) {
        String fullName = fullNameField.getText().trim();
        String username = usernameField.getText().trim();
        String password = passwordField.getText();
        String email = emailField.getText().trim();
        String role = roleComboBox.getValue();

        // Validate input
        if (fullName.isEmpty() || username.isEmpty() || password.isEmpty()) {
            messageLabel.setText("Please fill all required fields");
            return;
        }

        try {
            // Attempt registration
            boolean success = userService.registerUser(username, password, fullName, email, role);

            if (success) {
                messageLabel.setStyle("-fx-text-fill: green;");
                messageLabel.setText("Registration successful!");

                // Clear fields after successful registration
                fullNameField.clear();
                usernameField.clear();
                passwordField.clear();
                emailField.clear();

            } else {
                messageLabel.setStyle("-fx-text-fill: red;");
                messageLabel.setText("Registration failed. Username may already exist.");
            }

        } catch (SQLException e) {
            messageLabel.setStyle("-fx-text-fill: red;");
            messageLabel.setText("Database error: " + e.getMessage());
        }
    }

    // Handle back button action
    @FXML
    private void handleBack(ActionEvent event) {
        try {
            // Load login screen
            Parent root = FXMLLoader.load(getClass().getResource("/eventbookingsystem/UI/Login.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            messageLabel.setText("Error loading login: " + e.getMessage());
        }
    }
}
