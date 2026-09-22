package eventbookingsystem.model;





public class EventManager extends User {

    // Empty constructor
    public EventManager() {
        super();
    }

    // Constructor
    public EventManager(int userId, String username, String password, String fullName, String email) {
        super(userId, username, password, fullName, email);
    }

    // Implementation of abstract method from User class
    @Override
    public String getRole() {
        return "Manager";
    }

    // Manager-specific method to display welcome message
    public void displayWelcomeMessage() {
        System.out.println("Welcome, " + getFullName() + "!");
        System.out.println("You can create and manage events.");
    }
}
