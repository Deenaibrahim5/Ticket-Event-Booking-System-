package eventbookingsystem.model;

public class Customer extends User {

    // Empty constructor
    public Customer() {
        super();
    }

    public Customer(int userId, String username, String password, String fullName, String email) {
        super(userId, username, password, fullName, email);
    }


    // Implementation of abstract method from User class
    @Override
    public String getRole() {
        return "Customer";
    }

   
    public void displayWelcomeMessage() {
        System.out.println("Welcome, " + getFullName() + "!");
        System.out.println("You can browse events and book tickets.");
    }
}
