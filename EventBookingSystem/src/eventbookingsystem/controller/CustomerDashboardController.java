package eventbookingsystem.controller;

import eventbookingsystem.model.Event;
import eventbookingsystem.model.User;
import eventbookingsystem.service.EventService;
import java.io.IOException;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class CustomerDashboardController {

    @FXML
    private Label welcomeLabel;

    @FXML
    private TableView<Event> eventsTable;

    @FXML
    private TableColumn<Event, Integer> eventIdColumn;

    @FXML
    private TableColumn<Event, String> eventNameColumn;

    @FXML
    private TableColumn<Event, String> eventDateColumn;

    @FXML
    private TableColumn<Event, String> locationColumn;

    @FXML
    private TableColumn<Event, Integer> seatsColumn;

    @FXML
    private TableColumn<Event, Double> priceColumn;

    private User currentUser;
    private EventService eventService;

    public CustomerDashboardController() {
        eventService = new EventService();
    }

    public void setUser(User user) {
        this.currentUser = user;
        if (welcomeLabel != null) {
            welcomeLabel.setText("Welcome, " + user.getFullName() + "!");
            loadEvents();
        }
    }

    @FXML
    public void initialize() {
        eventIdColumn.setCellValueFactory(new PropertyValueFactory<>("eventId"));
        eventNameColumn.setCellValueFactory(new PropertyValueFactory<>("eventName"));
        eventDateColumn.setCellValueFactory(new PropertyValueFactory<>("eventDate"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("ticketPrice"));
        seatsColumn.setCellValueFactory(new PropertyValueFactory<>("seatsRemaining"));

        // Load events if user is already set
        if (currentUser != null && welcomeLabel != null) {
            welcomeLabel.setText("Welcome, " + currentUser.getFullName() + "!");
            loadEvents();
        }
    }

    private void loadEvents() {
        try {
            eventsTable.setItems(FXCollections.observableArrayList(eventService.getAllEvents()));
        } catch (SQLException e) {
            System.out.println("Error loading events: " + e.getMessage());
            showAlert("Database Error", "Failed to load events: " + e.getMessage());
        }
    }

    private void showAlert(String title, String message) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void handleViewDetails(ActionEvent event) {
        Event selected = eventsTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/eventbookingsystem/UI/EventDetails.fxml"));
                Parent root = loader.load();
                EventDetailsController controller = loader.getController();
                controller.setEventAndUser(selected, currentUser);

                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
                showAlert("Error", "Failed to load Event Details: " + e.getMessage());
            }
        } else {
            showAlert("No Selection", "Please select an event to view details.");
        }
    }

    @FXML
    private void handleBookTicket(ActionEvent event) {
        handleViewDetails(event);
    }

    @FXML
    private void handleMyBookings(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/eventbookingsystem/UI/CustomerBookings.fxml"));
            Parent root = loader.load();
            CustomerBookingsController controller = loader.getController();
            controller.setUser(currentUser);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load Customer Bookings: " + e.getMessage());
        } catch (NullPointerException e) {
            e.printStackTrace();
            showAlert("Error", "FXML file not found or controller initialization failed.");
        }
    }

    @FXML
    private void handleLogout(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/eventbookingsystem/UI/Login.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load Login screen: " + e.getMessage());
        }
    }
}
