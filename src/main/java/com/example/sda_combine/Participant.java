package com.example.sda_combine;

public class Participant extends User {
    private String[] registeredEvents; // List of registered events (simplified)

    public Participant(String userId, String firstName, String lastName, String email, String password) {
        super(userId, firstName, lastName, email, password); // Pass userId as a super argument
        this.registeredEvents = new String[10]; // Example size

    }

    // Method to register for an event
    public void registerForEvent(String eventName) {
        System.out.println(firstName + " has registered for the event: " + eventName);
        // Logic to add event to registeredEvents array
    }

    // View registered events
    public void viewRegisteredEvents() {
        System.out.println("Registered events for " + getFirstName() + ":");
        for (String event : registeredEvents) {
            if (event != null) {
                System.out.println("- " + event);
            }
        }
    }
}
