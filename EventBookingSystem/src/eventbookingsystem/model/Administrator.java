package eventbookingsystem.model;


public class Administrator extends User {

    // Empty constructor
    public Administrator() {
        super();
    }

    // Constructor
    public Administrator(int userId, String username, String password, String fullName, String email) {
        super(userId, username, password, fullName, email);
    }


    // Implementation of abstract method from User class
    @Override
    public String getRole() {
        return "Admin";
    }

    // Admin-specific method to display welcome message
    public void displayWelcomeMessage() {
        System.out.println("Welcome, " + getFullName() + "!");
        System.out.println("You have full system access.");
    }
}
