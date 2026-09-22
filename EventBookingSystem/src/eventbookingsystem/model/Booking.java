package eventbookingsystem.model;

import java.util.Date;

public class Booking {

    private int bookingId;
    private int customerId;
    private Event eventId;
    private Date bookingDate;
    private int numberOfTickets;

    private String customerName;
    private String eventName;

    // Empty constructor
    public Booking() {
    }

    // Constructor
    public Booking(int bookingId, int customerId, Event eventId, Date bookingDate, int numberOfTickets) {
        this.bookingId = bookingId;
        this.customerId = customerId;
        this.eventId = eventId;
        this.bookingDate = bookingDate;
        this.numberOfTickets = numberOfTickets;
    }


    // Method to confirm booking
    public void confirmBooking() {
        System.out.println("Booking confirmed!");
        System.out.println("Booking ID: " + bookingId);
        System.out.println("Number of tickets: " + numberOfTickets);
        System.out.println("Booking Date: " + bookingDate);
    }

    // Method to display booking details
    public void displayBookingDetails() {
        System.out.println("=================================");
        System.out.println("Booking ID: " + bookingId);

        // Use Event object if available 
        if (eventId != null) {
            System.out.println("Event: " + eventId.getEventName());
            System.out.println("Location: " + eventId.getLocation());
            System.out.println("Event Date: " + eventId.getEventDate());
        } else {
            System.out.println("Event: " + eventName);
        }

        System.out.println("Customer: " + customerName);
        System.out.println("Number of Tickets: " + numberOfTickets);
        System.out.println("Booking Date: " + bookingDate);
        System.out.println("=================================");
    }

    // Getters and Setters
    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getEventId() {
        return eventId != null ? eventId.getEventId() : 0;
    }

    public void setEventId(Event eventId) {
        this.eventId = eventId;
    }

    public Event getEvent() {
        return eventId;
    }

    public void setEvent(Event event) {
        this.eventId = event;
    }

    public Date getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(Date bookingDate) {
        this.bookingDate = bookingDate;
    }

    public int getNumberOfTickets() {
        return numberOfTickets;
    }

    public void setNumberOfTickets(int numberOfTickets) {
        this.numberOfTickets = numberOfTickets;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    // toString method
    @Override
    public String toString() {
        return "Booking{" + "bookingId=" + bookingId + ", customerId=" + customerId +
               ", eventId=" + (eventId != null ? eventId.getEventName() : "null") + ", bookingDate=" + bookingDate +
               ", numberOfTickets=" + numberOfTickets + '}';
    }
}
