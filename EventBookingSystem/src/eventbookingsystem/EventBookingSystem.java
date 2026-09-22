/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */
package eventbookingsystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author deena
 */
public class EventBookingSystem extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // Load the Login screen
            Parent root = FXMLLoader.load(getClass().getResource("/eventbookingsystem/UI/Login.fxml"));

            // Create scene
            Scene scene = new Scene(root);

            // Setup stage
            primaryStage.setTitle("Event Ticket Booking System");
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();

        } catch (Exception e) {
            System.out.println("Error loading application: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Main method - entry point of the application
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
