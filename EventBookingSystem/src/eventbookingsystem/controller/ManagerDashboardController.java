package eventbookingsystem.controller;

import eventbookingsystem.model.Event;
import eventbookingsystem.model.User;
import eventbookingsystem.service.EventService;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.io.IOException;

public class ManagerDashboardController {

    @FXML private Label welcomeLabel;
    @FXML private TableView<Event> eventsTable;
    @FXML private TableColumn<Event, Integer> eventIdColumn;
    @FXML private TableColumn<Event, String> eventNameColumn;
    @FXML private TableColumn<Event, String> eventDateColumn;
    @FXML private TableColumn<Event, String> locationColumn;
    @FXML private TableColumn<Event, Integer> seatsColumn;
    @FXML private TableColumn<Event, Double> priceColumn;

    private User currentUser;
    private EventService eventService;

    public ManagerDashboardController() {
        eventService = new EventService();
    }

    public void setUser(User user) {
        this.currentUser = user;
        welcomeLabel.setText("Welcome, " + user.getFullName() + "!");
        loadEvents();
    }

    @FXML
    public void initialize() {
        eventIdColumn.setCellValueFactory(new PropertyValueFactory<>("eventId"));
        eventNameColumn.setCellValueFactory(new PropertyValueFactory<>("eventName"));
        eventDateColumn.setCellValueFactory(new PropertyValueFactory<>("eventDate"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("ticketPrice"));
        seatsColumn.setCellValueFactory(new PropertyValueFactory<>("seatsRemaining"));
    }

    private void loadEvents() {
        try {
            eventsTable.setItems(FXCollections.observableArrayList(
                eventService.getEventsByManager(currentUser.getUserId())));
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            showAlert("Error", "Failed to load events: " + e.getMessage());
        }
    }

    @FXML
    private void handleAddEvent(ActionEvent event) {
        // Create dialog for adding new event
        Dialog<Event> dialog = new Dialog<>();
        dialog.setTitle("Add New Event");
        dialog.setHeaderText("Create a new event");

        // Set the button types
        ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        // Create the form fields
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new javafx.geometry.Insets(20, 150, 10, 10));

        TextField eventName = new TextField();
        eventName.setPromptText("Event Name");
        TextField location = new TextField();
        location.setPromptText("Location");
        TextField description = new TextField();
        description.setPromptText("Description");
        TextField capacity = new TextField();
        capacity.setPromptText("Capacity");
        TextField price = new TextField();
        price.setPromptText("Ticket Price");
        DatePicker datePicker = new DatePicker();
        datePicker.setPromptText("Event Date");

        grid.add(new Label("Event Name:"), 0, 0);
        grid.add(eventName, 1, 0);
        grid.add(new Label("Location:"), 0, 1);
        grid.add(location, 1, 1);
        grid.add(new Label("Description:"), 0, 2);
        grid.add(description, 1, 2);
        grid.add(new Label("Date:"), 0, 3);
        grid.add(datePicker, 1, 3);
        grid.add(new Label("Capacity:"), 0, 4);
        grid.add(capacity, 1, 4);
        grid.add(new Label("Ticket Price:"), 0, 5);
        grid.add(price, 1, 5);

        dialog.getDialogPane().setContent(grid);

        // Request focus on the event name field
        javafx.application.Platform.runLater(() -> eventName.requestFocus());

        // Prevent dialog from closing on validation errors
        final Button saveButton = (Button) dialog.getDialogPane().lookupButton(saveButtonType);
        saveButton.addEventFilter(javafx.event.ActionEvent.ACTION, e -> {
            // Commit DatePicker editor value
            datePicker.getEditor().commitValue();

            if (eventName.getText().isEmpty() || location.getText().isEmpty() ||
                description.getText().isEmpty() || capacity.getText().isEmpty() ||
                price.getText().isEmpty()) {
                showAlert("Error", "Please fill all fields");
                e.consume();
                return;
            }

            if (datePicker.getValue() == null) {
                showAlert("Error", "Please select a date");
                e.consume();
                return;
            }

            try {
                Integer.parseInt(capacity.getText());
                Double.parseDouble(price.getText());
            } catch (NumberFormatException ex) {
                showAlert("Error", "Invalid number format for capacity or price");
                e.consume();
            }
        });

        // Convert the result when save button is clicked
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                try {
                    String name = eventName.getText();
                    String loc = location.getText();
                    String desc = description.getText();
                    int cap = Integer.parseInt(capacity.getText());
                    double pr = Double.parseDouble(price.getText());
                    java.time.LocalDate localDate = datePicker.getValue();
                    java.sql.Date sqlDate = java.sql.Date.valueOf(localDate);

                    eventService.createEvent(name, sqlDate, loc, desc, cap, pr, currentUser.getUserId());
                    loadEvents();
                    showAlert("Success", "Event created successfully!");
                } catch (Exception ex) {
                    showAlert("Error", "Failed to create event: " + ex.getMessage());
                }
            }
            return null;
        });

        dialog.showAndWait();
    }

    @FXML
    private void handleEditEvent(ActionEvent event) {
        Event selected = eventsTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("No Selection", "Please select an event to edit.");
            return;
        }

        // Create dialog for editing event
        Dialog<Event> dialog = new Dialog<>();
        dialog.setTitle("Edit Event");
        dialog.setHeaderText("Edit event details");

        ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new javafx.geometry.Insets(20, 150, 10, 10));

        TextField eventName = new TextField(selected.getEventName());
        TextField location = new TextField(selected.getLocation());
        TextField description = new TextField(selected.getDescription());
        TextField price = new TextField(String.valueOf(selected.getTicketPrice()));
        DatePicker datePicker = new DatePicker();
        if (selected.getEventDate() != null) {
            java.sql.Date sqlDate = (java.sql.Date) selected.getEventDate();
            datePicker.setValue(sqlDate.toLocalDate());
        }

        grid.add(new Label("Event Name:"), 0, 0);
        grid.add(eventName, 1, 0);
        grid.add(new Label("Location:"), 0, 1);
        grid.add(location, 1, 1);
        grid.add(new Label("Description:"), 0, 2);
        grid.add(description, 1, 2);
        grid.add(new Label("Date:"), 0, 3);
        grid.add(datePicker, 1, 3);
        grid.add(new Label("Ticket Price:"), 0, 4);
        grid.add(price, 1, 4);

        dialog.getDialogPane().setContent(grid);

        // Prevent dialog from closing on validation errors
        final Button saveButton = (Button) dialog.getDialogPane().lookupButton(saveButtonType);
        saveButton.addEventFilter(javafx.event.ActionEvent.ACTION, e -> {
            // Commit DatePicker editor value
            datePicker.getEditor().commitValue();

            if (eventName.getText().isEmpty() || location.getText().isEmpty() ||
                description.getText().isEmpty() || price.getText().isEmpty()) {
                showAlert("Error", "Please fill all fields");
                e.consume();
                return;
            }

            if (datePicker.getValue() == null) {
                showAlert("Error", "Please select a date");
                e.consume();
                return;
            }

            try {
                Double.parseDouble(price.getText());
            } catch (NumberFormatException ex) {
                showAlert("Error", "Invalid number format for price");
                e.consume();
            }
        });

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                try {
                    String name = eventName.getText();
                    String loc = location.getText();
                    String desc = description.getText();
                    double pr = Double.parseDouble(price.getText());
                    java.time.LocalDate localDate = datePicker.getValue();
                    java.sql.Date sqlDate = java.sql.Date.valueOf(localDate);

                    eventService.updateEvent(selected.getEventId(), name, sqlDate, loc, desc, pr);
                    loadEvents();
                    showAlert("Success", "Event updated successfully!");
                } catch (Exception ex) {
                    showAlert("Error", "Failed to update event: " + ex.getMessage());
                }
            }
            return null;
        });

        dialog.showAndWait();
    }

    @FXML
    private void handleDeleteEvent(ActionEvent event) {
        Event selected = eventsTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("No Selection", "Please select an event to delete.");
            return;
        }

        // Confirm deletion
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Confirm Delete");
        confirmAlert.setHeaderText("Delete Event");
        confirmAlert.setContentText("Are you sure you want to delete: " + selected.getEventName() + "?");

        if (confirmAlert.showAndWait().get() == ButtonType.OK) {
            try {
                eventService.deleteEvent(selected.getEventId());
                loadEvents();
                showAlert("Success", "Event deleted successfully!");
            } catch (Exception e) {
                showAlert("Error", "Failed to delete event: " + e.getMessage());
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
