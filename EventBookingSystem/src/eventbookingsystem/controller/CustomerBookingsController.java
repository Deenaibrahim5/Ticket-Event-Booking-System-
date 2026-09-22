package eventbookingsystem.controller;

import eventbookingsystem.database.DatabaseConnection;
import eventbookingsystem.model.Booking;
import eventbookingsystem.model.User;
import eventbookingsystem.service.BookingService;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class CustomerBookingsController {

    @FXML private TableView<Booking> bookingsTable;
    @FXML private TableColumn<Booking, Integer> bookingIdColumn;
    @FXML private TableColumn<Booking, String> eventNameColumn;
    @FXML private TableColumn<Booking, String> bookingDateColumn;
    @FXML private TableColumn<Booking, Integer> ticketsColumn;

    private User currentUser;
    private BookingService bookingService;

    public CustomerBookingsController() {
        bookingService = new BookingService();
    }

    public void setUser(User user) {
        this.currentUser = user;
        loadBookings();
    }

    @FXML
    public void initialize() {
        bookingIdColumn.setCellValueFactory(new PropertyValueFactory<>("bookingId"));
        eventNameColumn.setCellValueFactory(new PropertyValueFactory<>("eventName"));
        bookingDateColumn.setCellValueFactory(new PropertyValueFactory<>("bookingDate"));
        ticketsColumn.setCellValueFactory(new PropertyValueFactory<>("numberOfTickets"));
    }

    private void loadBookings() {
        try {
            List<Booking> bookings = bookingService.getCustomerBookings(currentUser.getUserId());
            bookingsTable.setItems(FXCollections.observableArrayList(bookings));
        } catch (SQLException e) {
            showAlert("Error", "Failed to load bookings: " + e.getMessage());
        }
    }

    @FXML
    private void handleCancelBooking(ActionEvent event) {
        Booking selected = bookingsTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            try {
                bookingService.cancelReservation(selected.getBookingId());
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setContentText("Booking cancelled successfully!");
                alert.showAndWait();
                loadBookings();
            } catch (SQLException e) {
                showAlert("Error", "Failed to cancel booking: " + e.getMessage());
            }
        } else {
            showAlert("No Selection", "Please select a booking to cancel.");
        }
    }

    @FXML
    private void handleBack(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/eventbookingsystem/UI/CustomerDashboard.fxml"));
            Parent root = loader.load();
            CustomerDashboardController controller = loader.getController();
            controller.setUser(currentUser);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load Customer Dashboard: " + e.getMessage());
        } catch (NullPointerException e) {
            e.printStackTrace();
            showAlert("Error", "FXML file not found or controller initialization failed.");
        }
    }

    private void showAlert(String title, String message) {
        Alert.AlertType type = title.equalsIgnoreCase("Error") ? Alert.AlertType.ERROR : Alert.AlertType.INFORMATION;
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
