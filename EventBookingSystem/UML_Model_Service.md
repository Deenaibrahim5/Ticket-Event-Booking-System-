# Event Booking System - UML Class Diagram
## Model & Service Packages Only

---

```
╔═══════════════════════════════════════════════════════════════════════════════╗
║                              MODEL PACKAGE                                     ║
╚═══════════════════════════════════════════════════════════════════════════════╝

                            ╔════════════════════╗
                            ║   <<abstract>>     ║
                            ║       User         ║
                            ╠════════════════════╣
                            ║ - userId: int      ║
                            ║ - username: String ║
                            ║ - password: String ║
                            ║ - fullName: String ║
                            ║ - email: String    ║
                            ╠════════════════════╣
                            ║ + getRole(): String║
                            ║ + login(): boolean ║
                            ║ + getters/setters  ║
                            ╚════════════════════╝
                                       △
                                       │ Inheritance
                    ┌──────────────────┼──────────────────┐
                    │                  │                  │
        ╔═══════════════════╗ ╔═══════════════════╗ ╔═══════════════════╗
        ║    Customer       ║ ║  EventManager     ║ ║  Administrator    ║
        ╠═══════════════════╣ ╠═══════════════════╣ ╠═══════════════════╣
        ║                   ║ ║                   ║ ║                   ║
        ╠═══════════════════╣ ╠═══════════════════╣ ╠═══════════════════╣
        ║+ getRole()        ║ ║+ getRole()        ║ ║+ getRole()        ║
        ╚═══════════════════╝ ╚═══════════════════╝ ╚═══════════════════╝



╔═══════════════════════════╗                      ╔══════════════════════════╗
║         Event             ║ ◆────────────────── ║   SeatingArrangement     ║
╠═══════════════════════════╣    Composition      ╠══════════════════════════╣
║ - eventId: int            ║    (1..1)           ║ - totalCapacity: int     ║
║ - eventName: String       ║                     ║ - seatsRemaining: int    ║
║ - eventDate: Date         ║                     ╠══════════════════════════╣
║ - location: String        ║                     ║ + bookSeats(int): boolean║
║ - description: String     ║                     ║ + releaseSeats(int): void║
║ - ticketPrice: double     ║                     ║ + getSeatsRemaining(): int║
║ - managerId: int          ║                     ║ + getTotalCapacity(): int║
║ - seatingInfo: SeatingArrangement               ╚══════════════════════════╝
╠═══════════════════════════╣
║ + bookSeats(int): boolean ║
║ + getSeatsRemaining(): int║
║ + getters/setters         ║
╚═══════════════════════════╝
            △
            │
            │ Aggregation (0..*)
            ◇
            │
╔═══════════════════════════╗
║        Booking            ║
╠═══════════════════════════╣
║ - bookingId: int          ║
║ - customerId: int         ║
║ - eventId: Event          ║
║ - bookingDate: Date       ║
║ - numberOfTickets: int    ║
║ - customerName: String    ║
║ - eventName: String       ║
╠═══════════════════════════╣
║ + confirmBooking(): void  ║
║ + displayBookingDetails() ║
║ + getters/setters         ║
╚═══════════════════════════╝


╔═══════════════════════════════════════════════════════════════════════════════╗
║                             SERVICE PACKAGE                                    ║
╚═══════════════════════════════════════════════════════════════════════════════╝

╔═════════════════════════════╗
║       UserService           ║
╠═════════════════════════════╣
║ (no attributes - stateless) ║
╠═════════════════════════════╣
║ + login(): User             ║
║ + registerUser(): boolean   ║
║ + getAllUsers(): List<User> ║
║ + deleteUser(): boolean     ║
║ + getUserById(): User       ║
║ + createUserByRole(): User  ║
╚═════════════════════════════╝
            │
            │ Dependency (uses/creates)
            │
            ├────────────────────────────┐
            │                            │
            ▼                            ▼
    ┌───────────────┐          ┌─────────────────┐
    │     User      │          │   Customer      │
    │  (abstract)   │          │   EventManager  │
    └───────────────┘          │   Administrator │
                               └─────────────────┘


╔═════════════════════════════╗
║       EventService          ║
╠═════════════════════════════╣
║ (no attributes - stateless) ║
╠═════════════════════════════╣
║ + getAllEvents(): List<Event>║
║ + getEventById(): Event     ║
║ + getEventsByManager(): List║
║ + createEvent(): boolean    ║
║ + updateEvent(): boolean    ║
║ + deleteEvent(): boolean    ║
║ + updateSeatsRemaining()    ║
╚═════════════════════════════╝
            │
            │ Dependency (uses/creates)
            ▼
    ┌───────────────┐
    │     Event     │
    └───────────────┘


╔═════════════════════════════╗
║      BookingService         ║
╠═════════════════════════════╣
║ (no attributes - stateless) ║
╠═════════════════════════════╣
║ + makeReservation(): boolean║
║ + cancelReservation(): bool ║
║ + getAllBookings(): List    ║
║ + getBookingsByCustomer()   ║
╚═════════════════════════════╝
            │
            │ Dependency (uses/creates)
            ▼
    ┌───────────────┐
    │    Booking    │
    └───────────────┘


╔═══════════════════════════════════════════════════════════════════════════════╗
║                           RELATIONSHIPS SUMMARY                                ║
╚═══════════════════════════════════════════════════════════════════════════════╝

┌─────────────────────────────────────────────────────────────────────────────────┐
│  RELATIONSHIP       │  FROM              │  TO                  │  TYPE         │
├─────────────────────────────────────────────────────────────────────────────────┤
│  Inheritance        │  Customer          │  User (abstract)     │  IS-A         │
│  Inheritance        │  EventManager      │  User (abstract)     │  IS-A         │
│  Inheritance        │  Administrator     │  User (abstract)     │  IS-A         │
│  Composition        │  Event             │  SeatingArrangement  │  OWNS (1..1)  │
│  Aggregation        │  Booking           │  Event               │  HAS-A (0..*) │
│  Dependency         │  UserService       │  User                │  USES         │
│  Dependency         │  UserService       │  Customer            │  CREATES      │
│  Dependency         │  UserService       │  EventManager        │  CREATES      │
│  Dependency         │  UserService       │  Administrator       │  CREATES      │
│  Dependency         │  EventService      │  Event               │  USES/CREATES │
│  Dependency         │  BookingService    │  Booking             │  USES/CREATES │
└─────────────────────────────────────────────────────────────────────────────────┘


╔═══════════════════════════════════════════════════════════════════════════════╗
║                                  LEGEND                                        ║
╚═══════════════════════════════════════════════════════════════════════════════╝

    △       Inheritance (IS-A) - Child class extends parent class

    ◆────   Composition (OWNS) - Strong relationship, child dies with parent

    ◇────   Aggregation (HAS-A) - Weak relationship, child exists independently

    ────▶   Dependency (USES) - One class uses another

    (1..1)  One-to-one cardinality

    (0..*)  Zero-to-many cardinality
```

---

## Relationship Explanations

### 1. Inheritance (User Hierarchy)
```
User (abstract)
    △
    ├── Customer
    ├── EventManager
    └── Administrator
```
- **Type:** IS-A relationship
- **Meaning:** Customer IS-A User, EventManager IS-A User, Administrator IS-A User
- **Purpose:** Polymorphism - all user types share common attributes but have role-specific behavior

### 2. Composition (Event → SeatingArrangement)
```
Event ◆──── SeatingArrangement
```
- **Type:** Strong ownership (OWNS)
- **Cardinality:** 1..1 (one Event has exactly one SeatingArrangement)
- **Meaning:** SeatingArrangement cannot exist without Event
- **Lifecycle:** When Event is deleted, SeatingArrangement is also deleted

### 3. Aggregation (Booking → Event)
```
Booking ◇──── Event
```
- **Type:** Weak relationship (HAS-A)
- **Cardinality:** 0..* (an Event can have zero or many Bookings)
- **Meaning:** Booking references Event, but Event exists independently
- **Lifecycle:** When Booking is deleted, Event remains in the system

### 4. Service Dependencies
```
UserService ────▶ User, Customer, EventManager, Administrator
EventService ────▶ Event
BookingService ────▶ Booking
```
- **Type:** Dependency (USES/CREATES)
- **Meaning:** Services use and create model objects
- **Note:** Services are stateless (no attributes)

---

## Design Patterns Used

### Factory Pattern (UserService)
```java
public User createUserByRole(String role) {
    if (role.equals("Customer")) return new Customer();
    else if (role.equals("Manager")) return new EventManager();
    else if (role.equals("Admin")) return new Administrator();
    return null;
}
```
- UserService creates concrete user types based on role string
- Returns abstract User type (polymorphism)

---

## Package Structure

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
│
└── service/
    ├── UserService.java
    ├── EventService.java
    └── BookingService.java
```
