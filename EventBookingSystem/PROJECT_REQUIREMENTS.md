# Event Booking System - Requirements Specification

## Project Overview
A simple Event Ticket Booking System built with JavaFX and Oracle Database for CS313 Advanced Programming Language course. The system provides role-based access for customers, event managers, and administrators.

---

## 1. Functional Requirements

### 1.1 Common Features (All Users)
- **User Login**: Users can log in with username and password
- **User Registration**: New customers can register accounts
- **User Logout**: Users can safely log out of the system

### 1.2 Customer Features
- **Browse Events**: View list of all available events
- **View Event Details**: See complete information about an event (name, date, location, seats remaining, ticket price)
- **Book Tickets**: Reserve tickets for an event
- **Cancel Booking**: Cancel previously made reservations
- **View Booking History**: See personal list of all bookings made

### 1.3 Event Manager Features
- **Create Events**: Add new events with details (name, date, location, description, capacity, price)
- **Update Events**: Modify existing event information
- **Delete Events**: Remove events from the system
- **View Managed Events**: See all events they created
- **View Event Statistics**: Check booking information for their events

### 1.4 Administrator Features
- **View All Users**: See complete list of system users
- **Manage Users**: Add or remove users from the system
- **View All Events**: See every event in the system
- **View All Bookings**: Access complete booking records
- **Generate Reports**: Create simple reports showing total bookings and revenue

---

## 2. User Interface Screens

### 2.1 Login Screen (`Login.fxml`)
- **Layout**: VBox
- **Components**:
  - Username text field
  - Password field
  - Login button
  - "New User? Register" link button
- **Controller**: `LoginController.java`

### 2.2 Registration Screen (`Registration.fxml`)
- **Layout**: GridPane
- **Components**:
  - Full name text field
  - Username text field
  - Password field
  - Email text field
  - Role selection (Customer/Manager)
  - Register button
  - Back to Login button
- **Controller**: `RegistrationController.java`

### 2.3 Customer Dashboard (`CustomerDashboard.fxml`)
- **Layout**: BorderPane
- **Components**:
  - TableView showing available events
  - View Details button
  - Book Ticket button
  - My Bookings button
  - Logout button
- **Controller**: `CustomerDashboardController.java`

### 2.4 Event Details Screen (`EventDetails.fxml`)
- **Layout**: VBox
- **Components**:
  - Event name label
  - Date label
  - Location label
  - Description label
  - Seats remaining label
  - Price label
  - Number of tickets spinner/field
  - Book Now button
  - Back button
- **Controller**: `EventDetailsController.java`

### 2.5 Customer Bookings Screen (`CustomerBookings.fxml`)
- **Layout**: BorderPane
- **Components**:
  - TableView showing user bookings
  - Cancel Booking button
  - Export to File button
  - Back button
- **Controller**: `CustomerBookingsController.java`

### 2.6 Event Manager Dashboard (`ManagerDashboard.fxml`)
- **Layout**: BorderPane
- **Components**:
  - TableView showing manager's events
  - Add Event button
  - Edit Event button
  - Delete Event button
  - View Bookings button
  - Logout button
- **Controller**: `ManagerDashboardController.java`

### 2.7 Admin Dashboard (`AdminDashboard.fxml`)
- **Layout**: BorderPane
- **Components**:
  - TableView with data display
  - Tab selection (Users/Events/Bookings)
  - Add User button
  - Remove User button
  - View Reports button
  - Logout button
- **Controller**: `AdminDashboardController.java`

---

## 3. Object-Oriented Design

### 3.1 Class Hierarchy

#### Abstract Base Class
**User** (Abstract)
- Attributes: userId, username, password, fullName, email
- Abstract method: `getRole()`
- Methods: `login()`, `logout()`

#### Concrete User Classes (Inheritance)
**Customer** extends User
- Additional attributes: None
- Methods: `bookTicket()`, `cancelBooking()`, `viewBookingHistory()`

**EventManager** extends User
- Additional attributes: None
- Methods: `createEvent()`, `updateEvent()`, `deleteEvent()`

**Administrator** extends User
- Additional attributes: None
- Methods: `manageUsers()`, `generateReports()`

#### Event-Related Classes
**Event**
- Attributes: eventId, eventName, eventDate, location, description, ticketPrice, managerId
- **Composition**: SeatingArrangement seatingInfo (Event HAS-A SeatingArrangement)
- Methods: `getEventDetails()`, `updateEventInfo()`

**SeatingArrangement** (Composition - owned by Event)
- Attributes: totalCapacity, seatsRemaining
- Methods: `reserveSeats(int numberOfTickets)`, `releaseSeats(int numberOfTickets)`, `getSeatsRemaining()`
- Note: Cannot exist without parent Event

**Booking**
- Attributes: bookingId, customerId, eventId, bookingDate, numberOfTickets
- Methods: `confirmBooking()`, `cancelReservation()`

### 3.2 Interface Design
**BookingOperations** (Interface)
- Methods:
  - `boolean makeReservation(int customerId, int eventId, int tickets)`
  - `boolean cancelReservation(int bookingId)`
  - `List<Booking> getCustomerBookings(int customerId)`

Implemented by: `BookingService` class

### 3.3 Polymorphism Usage
- User reference can point to Customer, EventManager, or Administrator objects
- Different role-based behavior through method overriding

---

## 4. Database Schema

### 4.1 Database Information
- **Database Type**: Oracle Database XE
- **Username**: system
- **Password**: 0509667971
- **Connection**: localhost:1521/XE

### 4.2 Tables

#### USERS Table
```sql
CREATE TABLE USERS (
    USER_ID NUMBER PRIMARY KEY,
    USERNAME VARCHAR2(50) UNIQUE NOT NULL,
    PASSWORD VARCHAR2(50) NOT NULL,
    FULL_NAME VARCHAR2(100) NOT NULL,
    EMAIL VARCHAR2(100),
    ROLE VARCHAR2(20) NOT NULL
);
```

#### EVENTS Table
```sql
CREATE TABLE EVENTS (
    EVENT_ID NUMBER PRIMARY KEY,
    EVENT_NAME VARCHAR2(100) NOT NULL,
    EVENT_DATE DATE NOT NULL,
    LOCATION VARCHAR2(200) NOT NULL,
    DESCRIPTION VARCHAR2(500),
    TOTAL_CAPACITY NUMBER NOT NULL,
    SEATS_REMAINING NUMBER NOT NULL,
    TICKET_PRICE NUMBER(10,2) NOT NULL,
    MANAGER_ID NUMBER,
    FOREIGN KEY (MANAGER_ID) REFERENCES USERS(USER_ID)
);
```

#### BOOKINGS Table
```sql
CREATE TABLE BOOKINGS (
    BOOKING_ID NUMBER PRIMARY KEY,
    CUSTOMER_ID NUMBER NOT NULL,
    EVENT_ID NUMBER NOT NULL,
    BOOKING_DATE DATE NOT NULL,
    NUMBER_OF_TICKETS NUMBER NOT NULL,
    FOREIGN KEY (CUSTOMER_ID) REFERENCES USERS(USER_ID),
    FOREIGN KEY (EVENT_ID) REFERENCES EVENTS(EVENT_ID)
);
```

#### Sequences
```sql
CREATE SEQUENCE USER_SEQ START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE EVENT_SEQ START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE BOOKING_SEQ START WITH 1 INCREMENT BY 1;
```

---

## 5. Technical Requirements

### 5.1 Course Concepts Usage

| Concept | Implementation |
|---------|----------------|
| Classes & Objects | User, Customer, EventManager, Admin, Event, Booking, SeatingArrangement |
| Inheritance | Customer, EventManager, Administrator extend User |
| Abstract Classes | User abstract class with abstract getRole() method |
| Interface | BookingOperations interface |
| Polymorphism | User references to different user types |
| Exception Handling | DatabaseException, BookingException custom exceptions |
| GUI Programming | JavaFX with FXML files |
| Event-Driven Programming | Button actions, table selections |
| Collections | ArrayList<Event>, ArrayList<Booking>, HashMap<Integer, User> |
| Generics | Generic List and Map usage |
| JDBC | DatabaseConnection with PreparedStatement |
| File I/O | Export bookings using ObjectOutputStream |

### 5.2 Package Structure
```
eventbookingsystem/
├── model/
│   ├── User.java (abstract)
│   ├── Customer.java
│   ├── EventManager.java
│   ├── Administrator.java
│   ├── Event.java
│   ├── SeatingArrangement.java
│   └── Booking.java
├── database/
│   ├── DatabaseConnection.java
│   ├── DatabaseException.java
│   └── BookingOperations.java (interface)
├── service/
│   ├── BookingService.java
│   ├── UserService.java
│   └── EventService.java
├── controller/
│   ├── LoginController.java
│   ├── RegistrationController.java
│   ├── CustomerDashboardController.java
│   ├── EventDetailsController.java
│   ├── CustomerBookingsController.java
│   ├── ManagerDashboardController.java
│   └── AdminDashboardController.java
├── view/
│   ├── Login.fxml
│   ├── Registration.fxml
│   ├── CustomerDashboard.fxml
│   ├── EventDetails.fxml
│   ├── CustomerBookings.fxml
│   ├── ManagerDashboard.fxml
│   └── AdminDashboard.fxml
├── exception/
│   └── BookingException.java
└── EventBookingSystem.java (main)
```

---

## 6. Non-Functional Requirements
- Simple, student-level code (no advanced features)
- Clean, readable code with comments
- Follow course material concepts only
- Basic error handling with user-friendly messages
- Simple, functional UI design (not professional)

---

## 7. Deliverables
1. Source code with all packages
2. FXML files for all UI screens
3. Database schema SQL script
4. Test data SQL script
5. Project report including:
   - Introduction
   - System hierarchy
   - UML class diagram
   - Database schema
   - Execution snapshots
   - Code snapshots

---

**Document Version**: 1.0
**Date**: 2025-11-14
**Course**: CS313 Advanced Programming Language
