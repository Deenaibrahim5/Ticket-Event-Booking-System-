# Event Booking System - Progress Tracking

## Project Information
- **Project Name**: Event Booking System
- **Course**: CS313 Advanced Programming Language
- **Start Date**: 2025-11-14
- **Status**: Planning Phase

---

## Progress Overview

| Phase | Status | Progress |
|-------|--------|----------|
| Planning & Documentation | ✅ COMPLETED | 100% |
| Database Setup | ⏳ NOT STARTED | 0% |
| Model Classes | ⏳ NOT STARTED | 0% |
| Database Layer | ⏳ NOT STARTED | 0% |
| Service Layer | ⏳ NOT STARTED | 0% |
| FXML Views | ⏳ NOT STARTED | 0% |
| Controllers | ⏳ NOT STARTED | 0% |
| Testing & Integration | ⏳ NOT STARTED | 0% |
| Documentation | ⏳ NOT STARTED | 0% |

---

## Detailed Task List

### Phase 1: Planning & Documentation ✅
- [x] Review project requirements
- [x] Design class hierarchy
- [x] Plan database schema
- [x] Create Requirements Specification document
- [x] Create Progress Tracking document
- [ ] **Waiting for approval to proceed**

---

### Phase 2: Database Setup ⏳
- [ ] Create database schema SQL script
- [ ] Create USERS table
- [ ] Create EVENTS table
- [ ] Create BOOKINGS table
- [ ] Create sequences (USER_SEQ, EVENT_SEQ, BOOKING_SEQ)
- [ ] Create sample test data SQL script
- [ ] Insert sample users (admin, manager, customers)
- [ ] Insert sample events
- [ ] Test database connection from Java

**Status**: Not Started

---

### Phase 3: Model Classes ⏳

#### 3.1 Base & Core Models
- [ ] Create User.java (abstract class)
- [ ] Create Customer.java (extends User)
- [ ] Create EventManager.java (extends User)
- [ ] Create Administrator.java (extends User)
- [ ] Create SeatingArrangement.java (composition class)
- [ ] Create Event.java (with SeatingArrangement composition)
- [ ] Create Booking.java

**Status**: Not Started

---

### Phase 4: Database Layer ⏳

#### 4.1 Connection & Exceptions
- [ ] Create DatabaseConnection.java
- [ ] Create DatabaseException.java
- [ ] Create BookingException.java

#### 4.2 Interface & Service
- [ ] Create BookingOperations.java (interface)
- [ ] Create UserService.java (CRUD for users)
- [ ] Create EventService.java (CRUD for events)
- [ ] Create BookingService.java (implements BookingOperations)

**Status**: Not Started

---

### Phase 5: FXML Views ⏳

#### 5.1 Create FXML Files
- [ ] Create Login.fxml (VBox layout)
- [ ] Create Registration.fxml (GridPane layout)
- [ ] Create CustomerDashboard.fxml (BorderPane layout)
- [ ] Create EventDetails.fxml (VBox layout)
- [ ] Create CustomerBookings.fxml (BorderPane layout)
- [ ] Create ManagerDashboard.fxml (BorderPane layout)
- [ ] Create AdminDashboard.fxml (BorderPane layout)

**Status**: Not Started

---

### Phase 6: Controllers ⏳

#### 6.1 Create Controller Classes
- [ ] Create LoginController.java
- [ ] Create RegistrationController.java
- [ ] Create CustomerDashboardController.java
- [ ] Create EventDetailsController.java
- [ ] Create CustomerBookingsController.java
- [ ] Create ManagerDashboardController.java
- [ ] Create AdminDashboardController.java

#### 6.2 Connect Controllers to Views
- [ ] Link Login.fxml to LoginController
- [ ] Link Registration.fxml to RegistrationController
- [ ] Link CustomerDashboard.fxml to CustomerDashboardController
- [ ] Link EventDetails.fxml to EventDetailsController
- [ ] Link CustomerBookings.fxml to CustomerBookingsController
- [ ] Link ManagerDashboard.fxml to ManagerDashboardController
- [ ] Link AdminDashboard.fxml to AdminDashboardController

**Status**: Not Started

---

### Phase 7: Main Application ⏳
- [ ] Update EventBookingSystem.java (main class)
- [ ] Implement application startup logic
- [ ] Load initial Login screen
- [ ] Test application launch

**Status**: Not Started

---

### Phase 8: Feature Implementation ⏳

#### 8.1 Login & Registration
- [ ] Implement login validation
- [ ] Implement user registration
- [ ] Implement role-based navigation

#### 8.2 Customer Features
- [ ] Implement browse events functionality
- [ ] Implement view event details
- [ ] Implement book tickets
- [ ] Implement cancel booking
- [ ] Implement view booking history
- [ ] Implement export bookings to file (ObjectOutputStream)

#### 8.3 Event Manager Features
- [ ] Implement create event
- [ ] Implement update event
- [ ] Implement delete event
- [ ] Implement view managed events
- [ ] Implement view event statistics

#### 8.4 Administrator Features
- [ ] Implement view all users
- [ ] Implement add/remove users
- [ ] Implement view all events
- [ ] Implement view all bookings
- [ ] Implement generate reports

**Status**: Not Started

---

### Phase 9: Testing & Bug Fixes ⏳
- [ ] Test login/registration flow
- [ ] Test customer booking flow
- [ ] Test event manager operations
- [ ] Test administrator operations
- [ ] Test database persistence
- [ ] Test exception handling
- [ ] Fix any bugs found
- [ ] Test file I/O operations

**Status**: Not Started

---

### Phase 10: Documentation ⏳
- [ ] Write project introduction
- [ ] Create system hierarchy diagram
- [ ] Create UML class diagram
- [ ] Document database schema
- [ ] Take execution screenshots
- [ ] Take code screenshots
- [ ] Write final report

**Status**: Not Started

---

## Known Issues
*No issues yet - project not started*

---

## Notes
- Using Oracle Database XE (localhost:1521/XE)
- Database credentials: system/0509667971
- All UI built with FXML files
- Following CS313 course concepts only
- Humanized naming conventions used throughout

---

## Next Steps
1. ✅ Complete requirements specification
2. ✅ Create progress tracking document
3. ⏳ **WAITING FOR APPROVAL TO START IMPLEMENTATION**
4. Start with database setup
5. Create model classes
6. Build database layer
7. Create FXML views
8. Implement controllers
9. Test and debug

---

**Last Updated**: 2025-11-14
**Total Completion**: 10%
