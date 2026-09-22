package eventbookingsystem.model;

public class SeatingArrangement {
   
    private int totalCapacity;
    private int seatsRemaining;

   
    public SeatingArrangement() {
    }

    public SeatingArrangement(int totalCapacity, int seatsRemaining) {
        this.totalCapacity = totalCapacity;
        this.seatsRemaining = seatsRemaining;
    }

    // Method  reserve seats
    public boolean reserveSeats(int numberOfTickets) {
        if (numberOfTickets <= 0) {
            System.out.println("Error: Invalid number of tickets.");
            return false;
        }

        if (numberOfTickets > seatsRemaining) {
            System.out.println("Error: Not enough seats available.");
            return false;
        }

        seatsRemaining = seatsRemaining - numberOfTickets;
        System.out.println(numberOfTickets + " seats reserved successfully.");
        return true;
    }

    // Method to release seats (when booking is cancelled)
    public void releaseSeats(int numberOfTickets) {
        if (numberOfTickets <= 0) {
            System.out.println("Error: Invalid number of tickets.");
            return;
        }

        seatsRemaining = seatsRemaining + numberOfTickets;

        // seats remaining doesn't exceed total capacity
        if (seatsRemaining > totalCapacity) {
            seatsRemaining = totalCapacity;
        }

        System.out.println(numberOfTickets + " seats released.");
    }

    // Method to check if seats are available
    public boolean hasAvailableSeats(int numberOfTickets) {
        return numberOfTickets <= seatsRemaining;
    }

    // Getters and Setters
    public int getTotalCapacity() {
        return totalCapacity;
    }

    public void setTotalCapacity(int totalCapacity) {
        this.totalCapacity = totalCapacity;
    }

    public int getSeatsRemaining() {
        return seatsRemaining;
    }

    public void setSeatsRemaining(int seatsRemaining) {
        this.seatsRemaining = seatsRemaining;
    }

   
    @Override
    public String toString() {
        return "SeatingArrangement{" + "totalCapacity=" + totalCapacity +
               ", seatsRemaining=" + seatsRemaining + '}';
    }
}
