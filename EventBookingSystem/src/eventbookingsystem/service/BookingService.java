package eventbookingsystem.service;


import eventbookingsystem.database.DatabaseConnection;
import eventbookingsystem.model.Booking;
import eventbookingsystem.model.Event;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookingService {
   //compstion
    private EventService eventService;

    public BookingService() {
        this.eventService = new EventService();
    }

    // Method to make a reservation========================
    public boolean makeReservation(int customerId, int eventId, int numberOfTickets) throws SQLException {

        // Check if event exists and has enough seats
        Event event = eventService.getEventById(eventId);

        if (event == null) {
            throw new SQLException("Event not found");
        }

        if (!event.checkAvailability(numberOfTickets)) {
            throw new SQLException("Not enough seats available");
        }

        // Create booking in database
        String query = "INSERT INTO BOOKINGS (BOOKING_ID, CUSTOMER_ID, EVENT_ID, " +
                      "BOOKING_DATE, NUMBER_OF_TICKETS) VALUES (BOOKING_SEQ.NEXTVAL, ?, ?, SYSDATE, ?)";

        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, customerId);
            pstmt.setInt(2, eventId);
            pstmt.setInt(3, numberOfTickets);

            int rowsAffected = pstmt.executeUpdate();
            conn.commit();
            pstmt.close();

            if (rowsAffected > 0) {
                // Update seats remaining
                int newSeatsRemaining = event.getSeatingInfo().getSeatsRemaining() - numberOfTickets;
                eventService.updateSeatsRemaining(eventId, newSeatsRemaining);

                return true;
            }
            return false;
        } catch (SQLException e) {
          e.printStackTrace();
        }
        return false;
    }

    // Method to cancel reservation---------------------------
    public boolean cancelReservation(int bookingId) throws SQLException
           {

        // Get booking details first
        Booking booking = null;
        try {
            booking = getBookingById(bookingId);
        } catch (SQLException e) {
            throw new SQLException("Failed to get booking", e);
        }

        if (booking == null) {
            throw new SQLException("Booking not found");
        }

        // Delete booking
        String query = "DELETE FROM BOOKINGS WHERE BOOKING_ID = ?";

        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, bookingId);

            int rowsAffected = pstmt.executeUpdate();
            conn.commit();
            pstmt.close();

            if (rowsAffected > 0) {
                // Return seats to event
                Event event = eventService.getEventById(booking.getEventId());
                int newSeatsRemaining = event.getSeatingInfo().getSeatsRemaining() +
                                       booking.getNumberOfTickets();
                eventService.updateSeatsRemaining(booking.getEventId(), newSeatsRemaining);
                return true;
            }

            return false;

        } catch (SQLException e) {
            throw new SQLException("Failed to cancel reservation", e);
        }
    }

    // Method to get customer bookings
    public List<Booking> getCustomerBookings(int customerId) throws SQLException {
        List<Booking> bookings = new ArrayList<>();
        String query = "SELECT B.*, E.EVENT_NAME, U.FULL_NAME " +
                      "FROM BOOKINGS B " +
                      "JOIN EVENTS E ON B.EVENT_ID = E.EVENT_ID " +
                      "JOIN USERS U ON B.CUSTOMER_ID = U.USER_ID " +
                      "WHERE B.CUSTOMER_ID = ? " +
                      "ORDER BY B.BOOKING_DATE DESC";


                ResultSet rs = null;
              PreparedStatement pstmt =null;
        try {
            Connection conn = DatabaseConnection.getConnection();
             pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, customerId);
             rs = pstmt.executeQuery();

            while (rs.next()) {
                Booking booking = new Booking();
                booking.setBookingId(rs.getInt("BOOKING_ID"));
                booking.setCustomerId(rs.getInt("CUSTOMER_ID"));

                Event event = eventService.getEventById(rs.getInt("EVENT_ID"));
                booking.setEventId(event);

                booking.setBookingDate(rs.getDate("BOOKING_DATE"));
                booking.setNumberOfTickets(rs.getInt("NUMBER_OF_TICKETS"));
                booking.setEventName(rs.getString("EVENT_NAME"));
                booking.setCustomerName(rs.getString("FULL_NAME"));
                bookings.add(booking);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        } finally{
           try{
           if(rs != null){
               rs.close();
           }
           if(pstmt!= null){
               pstmt.close();
           }
           }catch (SQLException e) {
            e.printStackTrace();  }

    }
        return bookings;
    }

    // Method to get event bookings
    public List<Booking> getEventBookings(int eventId) throws SQLException {
        List<Booking> bookings = new ArrayList<>();
        String query = "SELECT B.*, E.EVENT_NAME, U.FULL_NAME " +
                      "FROM BOOKINGS B " +
                      "JOIN EVENTS E ON B.EVENT_ID = E.EVENT_ID " +
                      "JOIN USERS U ON B.CUSTOMER_ID = U.USER_ID " +
                      "WHERE B.EVENT_ID = ? " +
                      "ORDER BY B.BOOKING_DATE DESC";
                ResultSet rs = null;
              PreparedStatement pstmt =null;
        try {
            Connection conn = DatabaseConnection.getConnection();
             pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, eventId);
             rs = pstmt.executeQuery();

            while (rs.next()) {
                Booking booking = new Booking();
                booking.setBookingId(rs.getInt("BOOKING_ID"));
                booking.setCustomerId(rs.getInt("CUSTOMER_ID"));

                Event event = eventService.getEventById(rs.getInt("EVENT_ID"));
                booking.setEventId(event);

                booking.setBookingDate(rs.getDate("BOOKING_DATE"));
                booking.setNumberOfTickets(rs.getInt("NUMBER_OF_TICKETS"));
                booking.setEventName(rs.getString("EVENT_NAME"));
                booking.setCustomerName(rs.getString("FULL_NAME"));
                bookings.add(booking);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally{
           try{
           if(rs != null){
               rs.close();
           }
           if(pstmt!= null){
               pstmt.close();
           }
           }catch (SQLException e) {
            e.printStackTrace();  }

    }
        return bookings;
    }

    // Method to get all bookings
    public List<Booking> getAllBookings() throws SQLException {
        List<Booking> bookings = new ArrayList<>();
        String query = "SELECT B.*, E.EVENT_NAME, U.FULL_NAME " +
                      "FROM BOOKINGS B " +
                      "JOIN EVENTS E ON B.EVENT_ID = E.EVENT_ID " +
                      "JOIN USERS U ON B.CUSTOMER_ID = U.USER_ID " +
                      "ORDER BY B.BOOKING_DATE DESC";

               ResultSet rs = null;
              PreparedStatement pstmt =null;
        try {
            Connection conn = DatabaseConnection.getConnection();
             pstmt = conn.prepareStatement(query);
             rs = pstmt.executeQuery();

            while (rs.next()) {
                Booking booking = new Booking();
                booking.setBookingId(rs.getInt("BOOKING_ID"));
                booking.setCustomerId(rs.getInt("CUSTOMER_ID"));

                Event event = eventService.getEventById(rs.getInt("EVENT_ID"));
                booking.setEventId(event);

                booking.setBookingDate(rs.getDate("BOOKING_DATE"));
                booking.setNumberOfTickets(rs.getInt("NUMBER_OF_TICKETS"));
                booking.setEventName(rs.getString("EVENT_NAME"));
                booking.setCustomerName(rs.getString("FULL_NAME"));
                bookings.add(booking);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally{
           try{
           if(rs != null){
               rs.close();
           }
           if(pstmt!= null){
               pstmt.close();
           }
           }catch (SQLException e) {
            e.printStackTrace();  }

    }return bookings;
    
    }

    // Helper method to get booking by ID
    private Booking getBookingById(int bookingId) throws SQLException  {
        
    
        String query = "SELECT B.*, E.EVENT_NAME, U.FULL_NAME " +
                      "FROM BOOKINGS B " +
                      "JOIN EVENTS E ON B.EVENT_ID = E.EVENT_ID " +
                      "JOIN USERS U ON B.CUSTOMER_ID = U.USER_ID " +
                      "WHERE B.BOOKING_ID = ?";
        
          Booking booking = new Booking();
             ResultSet rs = null;
              PreparedStatement pstmt =null;
             
        try{
            Connection conn = DatabaseConnection.getConnection();
             pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, bookingId);
             rs = pstmt.executeQuery();

            while (rs.next()) {

                booking.setBookingId(rs.getInt("BOOKING_ID"));
                booking.setCustomerId(rs.getInt("CUSTOMER_ID"));

                Event event = eventService.getEventById(rs.getInt("EVENT_ID"));
                booking.setEventId(event);

                booking.setBookingDate(rs.getDate("BOOKING_DATE"));
                booking.setNumberOfTickets(rs.getInt("NUMBER_OF_TICKETS"));
                booking.setEventName(rs.getString("EVENT_NAME"));
                booking.setCustomerName(rs.getString("FULL_NAME"));

            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally{ 
           try{ 
           if(rs != null){
               rs.close();
           }
           if(pstmt!= null){
               pstmt.close();
           }
           }catch (SQLException e) {
            e.printStackTrace();  }

    }return booking;

}
}
