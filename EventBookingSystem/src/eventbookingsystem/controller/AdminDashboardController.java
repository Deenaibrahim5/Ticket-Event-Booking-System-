package eventbookingsystem.controller;

import eventbookingsystem.model.Booking;
import eventbookingsystem.model.Event;
import eventbookingsystem.model.User;
import eventbookingsystem.service.BookingService;
import eventbookingsystem.service.EventService;
import eventbookingsystem.service.UserService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;

public class AdminDashboardController {

    @FXML private Label welcomeLabel;
    @FXML private TabPane tabPane;
    @FXML private TableView<User> usersTable;
    @FXML private TableView<Event> eventsTable;
    @FXML private TableView<Booking> bookingsTable;

    @FXML private TableColumn<User, Integer> userIdColumn;
    @FXML private TableColumn<User, String> usernameColumn;
    @FXML private TableColumn<User, String> fullNameColumn;
    @FXML private TableColumn<User, String> emailColumn;
    @FXML private TableColumn<User, String> roleColumn;

    @FXML private TableColumn<Event, Integer> eventIdColumn;
    @FXML private TableColumn<Event, String> eventNameColumn;
    @FXML private TableColumn<Event, String> eventDateColumn;
    @FXML private TableColumn<Event, String> locationColumn;
    @FXML private TableColumn<Event, Integer> seatsColumn;
    @FXML private TableColumn<Event, Double> priceColumn;

    @FXML private TableColumn<Booking, Integer> bookingIdColumn;
    @FXML private TableColumn<Booking, String> customerColumn;
    @FXML private TableColumn<Booking, String> eventColumn;
    @FXML private TableColumn<Booking, String> dateColumn;
    @FXML private TableColumn<Booking, Integer> ticketsColumn;

    private User currentUser;
    private UserService userService;
    private EventService eventService;
    private BookingService bookingService;

    public AdminDashboardController() {
        userService = new UserService();
        eventService = new EventService();
        bookingService = new BookingService();
    }

    public void setUser(User user) {
        this.currentUser = user;
        welcomeLabel.setText("Welcome, " + user.getFullName() + "!");
    }

    @FXML
    public void initialize() {
        userIdColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        fullNameColumn.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));

        eventIdColumn.setCellValueFactory(new PropertyValueFactory<>("eventId"));
        eventNameColumn.setCellValueFactory(new PropertyValueFactory<>("eventName"));
        eventDateColumn.setCellValueFactory(new PropertyValueFactory<>("eventDate"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("ticketPrice"));
        seatsColumn.setCellValueFactory(new PropertyValueFactory<>("seatsRemaining"));

        bookingIdColumn.setCellValueFactory(new PropertyValueFactory<>("bookingId"));
        customerColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        eventColumn.setCellValueFactory(new PropertyValueFactory<>("eventName"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("bookingDate"));
        ticketsColumn.setCellValueFactory(new PropertyValueFactory<>("numberOfTickets"));

        loadAllData();
    }

    private void loadAllData() {
        try {
            usersTable.setItems(FXCollections.observableArrayList(userService.getAllUsers()));
            eventsTable.setItems(FXCollections.observableArrayList(eventService.getAllEvents()));
            bookingsTable.setItems(FXCollections.observableArrayList(bookingService.getAllBookings()));
        } catch (Exception e) {
            System.out.println("Error loading data: " + e.getMessage());
        }
    }

    @FXML
    private void handleDelete(ActionEvent event) {
        int selectedTab = tabPane.getSelectionModel().getSelectedIndex();

        switch (selectedTab) {
            case 0: // Users tab
                deleteUser();
                break;
            case 1: // Events tab
                showAlert("Access Denied", "Administrators can only view events. Only Event Managers can delete events.");
                break;
            case 2: // Bookings tab
                deleteBooking();
                break;
            default:
                showAlert("Error", "No tab selected");
        }
    }

    private void deleteUser() {
        User selected = usersTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("No Selection", "Please select a user to delete.");
            return;
        }

        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Confirm Delete");
        confirmAlert.setHeaderText("Delete User");
        confirmAlert.setContentText("Are you sure you want to delete user: " + selected.getUsername() + "?");

        if (confirmAlert.showAndWait().get() == ButtonType.OK) {
            try {
                userService.deleteUser(selected.getUserId());
                loadAllData();
                showAlert("Success", "User deleted successfully!");
            } catch (Exception e) {
                showAlert("Error", "Failed to delete user: " + e.getMessage());
            }
        }
    }

    private void deleteBooking() {
        Booking selected = bookingsTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("No Selection", "Please select a booking to delete.");
            return;
        }

        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Confirm Delete");
        confirmAlert.setHeaderText("Delete Booking");
        confirmAlert.setContentText("Are you sure you want to delete booking ID: " + selected.getBookingId() + "?");

        if (confirmAlert.showAndWait().get() == ButtonType.OK) {
            try {
                bookingService.cancelReservation(selected.getBookingId());
                loadAllData();
                showAlert("Success", "Booking deleted successfully!");
            } catch (Exception e) {
                showAlert("Error", "Failed to delete booking: " + e.getMessage());
            }
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
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}


