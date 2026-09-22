package eventbookingsystem.model;

import java.util.Date;

public class Event {
   
    private int eventId;
    private String eventName;
    private Date eventDate;
    private String location;
    private String description;
    private double ticketPrice;
    private int managerId;


    private SeatingArrangement seatingInfo;

    // Empty constructor
    public Event() {
        this.seatingInfo = new SeatingArrangement();
    }

    public Event(int eventId, String eventName, Date eventDate, String location,
                 String description, double ticketPrice, int managerId,
                 int totalCapacity, int seatsRemaining) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.location = location;
        this.description = description;
        this.ticketPrice = ticketPrice;
        this.managerId = managerId;

        // Create SeatingArrangement object (Composition)
        this.seatingInfo = new SeatingArrangement(totalCapacity, seatsRemaining);
    }


    // Method to book seats for this event
    public boolean bookSeats(int numberOfTickets) {
        return seatingInfo.reserveSeats(numberOfTickets);
    }

    // Method to cancel seats for this event
    public void cancelSeats(int numberOfTickets) {
        seatingInfo.releaseSeats(numberOfTickets);
    }

    // Method to check seat availability
    public boolean checkAvailability(int numberOfTickets) {
        return seatingInfo.hasAvailableSeats(numberOfTickets);
    }

    // Method to display event details
    public void displayEventDetails() {
        System.out.println("=================================");
        System.out.println("Event Name: " + eventName);
        System.out.println("Date: " + eventDate);
        System.out.println("Location: " + location);
        System.out.println("Description: " + description);
        System.out.println("Ticket Price: " + ticketPrice + " SAR");
        System.out.println("Seats Remaining: " + seatingInfo.getSeatsRemaining() +
                           " / " + seatingInfo.getTotalCapacity());
        System.out.println("=================================");
    }

    // Getters and Setters
    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(double ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public int getManagerId() {
        return managerId;
    }

    public void setManagerId(int managerId) {
        this.managerId = managerId;
    }

    // Getter for SeatingArrangement (Composition)
    public SeatingArrangement getSeatingInfo() {
        return seatingInfo;
    }

    public void setSeatingInfo(SeatingArrangement seatingInfo) {
        this.seatingInfo = seatingInfo;
    }

    // Simple getter for seats remaining (for TableView)
    public int getSeatsRemaining() {
        return seatingInfo.getSeatsRemaining();
    }

    // toString method
    @Override
    public String toString() {
        return "Event{" + "eventId=" + eventId + ", eventName=" + eventName +
               ", eventDate=" + eventDate + ", location=" + location +
               ", ticketPrice=" + ticketPrice + ", " + seatingInfo + '}';
    }
}
