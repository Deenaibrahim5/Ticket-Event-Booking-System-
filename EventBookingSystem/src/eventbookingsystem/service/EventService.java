package eventbookingsystem.service;

import eventbookingsystem.database.DatabaseConnection;
import eventbookingsystem.model.Event;
import eventbookingsystem.model.SeatingArrangement;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
  

public class EventService {

    public EventService() {
    }

    // Method to get all events
    public List<Event> getAllEvents() throws SQLException {
        List<Event> events = new ArrayList<>();
        String query = "SELECT * FROM EVENTS ORDER BY EVENT_DATE";

        try {
            Connection conn = DatabaseConnection.getinstance().getConnection();
            PreparedStatement pstmt = conn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Event event = new Event();
                event.setEventId(rs.getInt("EVENT_ID"));
                event.setEventName(rs.getString("EVENT_NAME"));
                event.setEventDate(rs.getDate("EVENT_DATE"));
                event.setLocation(rs.getString("LOCATION"));
                event.setDescription(rs.getString("DESCRIPTION"));
                event.setTicketPrice(rs.getDouble("TICKET_PRICE"));
                event.setManagerId(rs.getInt("MANAGER_ID"));

                // Set seating information (Composition)
                SeatingArrangement seating = new SeatingArrangement(
                    rs.getInt("TOTAL_CAPACITY"),
                    rs.getInt("SEATS_REMAINING")
                );
                event.setSeatingInfo(seating);

                events.add(event);
            }

            rs.close();
            pstmt.close();

        } catch (SQLException e) {
            throw new SQLException("Failed to retrieve events", e);
        }

        return events;
    }

    // Method to get events by manager ID
    public List<Event> getEventsByManager(int managerId) throws SQLException {
        List<Event> events = new ArrayList<>();
        String query = "SELECT * FROM EVENTS WHERE MANAGER_ID = ? ORDER BY EVENT_DATE";

        try {
            Connection conn = DatabaseConnection.getinstance().getConnection();
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, managerId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Event event = new Event();
                event.setEventId(rs.getInt("EVENT_ID"));
                event.setEventName(rs.getString("EVENT_NAME"));
                event.setEventDate(rs.getDate("EVENT_DATE"));
                event.setLocation(rs.getString("LOCATION"));
                event.setDescription(rs.getString("DESCRIPTION"));
                event.setTicketPrice(rs.getDouble("TICKET_PRICE"));
                event.setManagerId(rs.getInt("MANAGER_ID"));

                // Set seating information (Composition)
                SeatingArrangement seating = new SeatingArrangement(
                    rs.getInt("TOTAL_CAPACITY"),
                    rs.getInt("SEATS_REMAINING")
                );
                event.setSeatingInfo(seating);

                events.add(event);
            }

            rs.close();
            pstmt.close();

        } catch (SQLException e) {
            throw new SQLException("Failed to retrieve manager events", e);
        }

        return events;
    }

    // Method to get event by ID
    public Event getEventById(int eventId) throws SQLException {
        String query = "SELECT * FROM EVENTS WHERE EVENT_ID = ?";
        Event event = null;

        try {
            Connection conn = DatabaseConnection.getinstance().getConnection();
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, eventId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                event = new Event();
                event.setEventId(rs.getInt("EVENT_ID"));
                event.setEventName(rs.getString("EVENT_NAME"));
                event.setEventDate(rs.getDate("EVENT_DATE"));
                event.setLocation(rs.getString("LOCATION"));
                event.setDescription(rs.getString("DESCRIPTION"));
                event.setTicketPrice(rs.getDouble("TICKET_PRICE"));
                event.setManagerId(rs.getInt("MANAGER_ID"));

                // Set seating information (Composition)
                SeatingArrangement seating = new SeatingArrangement(
                    rs.getInt("TOTAL_CAPACITY"),
                    rs.getInt("SEATS_REMAINING")
                );
                event.setSeatingInfo(seating);
            }

            rs.close();
            pstmt.close();

        } catch (SQLException e) {
            throw new SQLException("Failed to retrieve event", e);
        }

        return event;
    }

    // Method to create a new event
    public boolean createEvent(String eventName, Date eventDate, String location,
                              String description, int totalCapacity, double ticketPrice,
                              int managerId) throws SQLException {
        String query = "INSERT INTO EVENTS (EVENT_ID, EVENT_NAME, EVENT_DATE, LOCATION, " +
                      "DESCRIPTION, TOTAL_CAPACITY, SEATS_REMAINING, TICKET_PRICE, MANAGER_ID) " +
                      "VALUES (EVENT_SEQ.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            Connection conn = DatabaseConnection.getinstance().getConnection();
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, eventName);
            pstmt.setDate(2, eventDate);
            pstmt.setString(3, location);
            pstmt.setString(4, description);
            pstmt.setInt(5, totalCapacity);
            pstmt.setInt(6, totalCapacity); // Initially, seats remaining = total capacity
            pstmt.setDouble(7, ticketPrice);
            pstmt.setInt(8, managerId);

            int rowsAffected = pstmt.executeUpdate();
            conn.commit();
            pstmt.close();

            return rowsAffected > 0;

        } catch (SQLException e) {
            throw new SQLException("Failed to create event", e);
        }
    }

    // Method to update event
    public boolean updateEvent(int eventId, String eventName, Date eventDate,
                              String location, String description, double ticketPrice)
                              throws SQLException {
        String query = "UPDATE EVENTS SET EVENT_NAME = ?, EVENT_DATE = ?, LOCATION = ?, " +
                      "DESCRIPTION = ?, TICKET_PRICE = ? WHERE EVENT_ID = ?";

        try {
            Connection conn = DatabaseConnection.getinstance().getConnection();
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, eventName);
            pstmt.setDate(2, eventDate);
            pstmt.setString(3, location);
            pstmt.setString(4, description);
            pstmt.setDouble(5, ticketPrice);
            pstmt.setInt(6, eventId);

            int rowsAffected = pstmt.executeUpdate();
            conn.commit();
            pstmt.close();

            return rowsAffected > 0;

        } catch (SQLException e) {
            throw new SQLException("Failed to update event", e);
        }
    }

    // Method to delete event
    public boolean deleteEvent(int eventId) throws SQLException {
        String query = "DELETE FROM EVENTS WHERE EVENT_ID = ?";

        try {
            Connection conn = DatabaseConnection.getinstance().getConnection();
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, eventId);

            int rowsAffected = pstmt.executeUpdate();
            conn.commit();
            pstmt.close();

            return rowsAffected > 0;

        } catch (SQLException e) {
            throw new SQLException("Failed to delete event", e);
        }
    }

    // Method to update seats remaining
    public boolean updateSeatsRemaining(int eventId, int seatsRemaining) throws SQLException {
        String query = "UPDATE EVENTS SET SEATS_REMAINING = ? WHERE EVENT_ID = ?";

        try {
            Connection conn = DatabaseConnection.getinstance().getConnection();
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, seatsRemaining);
            pstmt.setInt(2, eventId);

            int rowsAffected = pstmt.executeUpdate();
            conn.commit();
            pstmt.close();

            return rowsAffected > 0;

        } catch (SQLException e) {
            throw new SQLException("Failed to update seats", e);
        }
    }
}
