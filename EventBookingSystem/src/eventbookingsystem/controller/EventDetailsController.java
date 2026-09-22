package eventbookingsystem.controller;

import eventbookingsystem.database.DatabaseConnection;
import eventbookingsystem.model.Event;
import eventbookingsystem.model.User;
import eventbookingsystem.service.BookingService;
import eventbookingsystem.service.EventService;
import java.io.IOException;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.stage.Stage;

public class EventDetailsController {

    @FXML private Label eventNameLabel;
    @FXML private Label eventDateLabel;
    @FXML private Label locationLabel;
    @FXML private Label descriptionLabel;
    @FXML private Label seatsLabel;
    @FXML private Label priceLabel;
    @FXML private Label messageLabel;
    @FXML private Spinner<Integer> ticketSpinner;

    private Event currentEvent;
    private User currentUser;
    private BookingService bookingService;
    private EventService eventService;

    public EventDetailsController() {
        bookingService = new BookingService();
        eventService = new EventService();
    }

    public void setEventAndUser(Event event, User user) {
        this.currentEvent = event;
        this.currentUser = user;
        displayEventDetails();
    }

    private void displayEventDetails() {
        eventNameLabel.setText(currentEvent.getEventName());
        eventDateLabel.setText(currentEvent.getEventDate().toString());
        locationLabel.setText(currentEvent.getLocation());
        descriptionLabel.setText(currentEvent.getDescription());
        seatsLabel.setText(String.valueOf(currentEvent.getSeatingInfo().getSeatsRemaining()));
        priceLabel.setText(currentEvent.getTicketPrice() + " SAR");

        int maxTickets = Math.min(10, currentEvent.getSeatingInfo().getSeatsRemaining());
        ticketSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, maxTickets, 1));
    }

    @FXML
    private void handleBook(ActionEvent event) throws SQLException {
        try {
            int tickets = ticketSpinner.getValue();

            if (currentEvent.bookSeats(tickets)) {
                eventService.updateSeatsRemaining(currentEvent.getEventId(), currentEvent.getSeatsRemaining());

                boolean success = bookingService.makeReservation(
                    currentUser.getUserId(), currentEvent.getEventId(), tickets);

                if (success) {
                    messageLabel.setStyle("-fx-text-fill: green;");
                    messageLabel.setText("Booking successful!");
                    Thread.sleep(1000);
                    handleBack(event);
                }
            } else {
                messageLabel.setStyle("-fx-text-fill: red;");
                messageLabel.setText("Not enough seats available");
            }
        } catch ( InterruptedException e) {
            messageLabel.setStyle("-fx-text-fill: red;");
            messageLabel.setText("Error: " + e.getMessage());
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
        }
    }
}
