#Event Ticket Booking System
A Java-based desktop application (JavaFX) for browsing, booking, and managing event reservations, connected to an Oracle Database. Developed as a course project for Advanced Programming Language at Princess Nourah bint Abdulrahman University.

##Overview
The system supports three user roles — Administrator, Event Manager, and Customer — each with a dedicated dashboard and permissions. It handles user authentication, event creation and management, ticket booking, seat tracking, and booking history.

##Features
-User Authentication & Registration — Login with role-based access (Admin, Manager, Customer), with input validation and error handling.
-Customer Dashboard
 -Browse available events with details (date, location, price, seats remaining)
 -Book tickets with real-time seat availability check
 -View and cancel personal bookings
-Event Manager Dashboard
 -Create, edit, and delete events
 -View and manage events created by the manager
-Administrator Dashboard
 -View and manage all users (delete users)
 -View all events (read-only — cannot delete/edit events)
 -View and delete bookings across the system
-Seat Management — Automatic seat reservation and release tied to bookings via the SeatingArrangement class
-Confirmation & Validation Dialogs — Confirmation prompts before deletions, and clear error/success messages throughout

##System Architecture
-Model classes: User (abstract), Administrator, Customer, EventManager, Event, Booking, SeatingArrangement
-Service layer: UserService, EventService, BookingService — handle all CRUD operations and business logic
-Controllers (JavaFX/FXML): LoginController,RegistrationController,CustomerDashboardController
,ManagerDashboardController, AdminDashboardController, EventDetailsController, CustomerBookingsController
-Database: DatabaseConnection (Singleton pattern) manages a single Oracle DB connection
-Demonstrates OOP principles: Inheritance, Polymorphism, Composition

##Technologies Used 
-Java
-JavaFX (FXML)
-Oracle Database (JDBC)

#Database Schema
-Users (User_ID, Username, Password, Full_Name, Email, Role)
-Events (Event_ID, Manager_ID, Event_Name, Date, Description, Location, Total_Capacity, Seats_Remaining, Ticket_Price)
-Booking (Booking_ID, Customer_ID, Event_ID, Booking_Date, Number_of_Tickets)

#How to Run
1.Clone the repository
2.Set up an Oracle Database with the schema above
3.Update database connection details in DatabaseConnection.java (URL, username, password)
4.Run the application starting from LoginController / main application entry point

#Author
Deena Aljeraiwi
