package com.example.sda_combine;

public class Organizer extends User {
    private String[] managedEvents; // List of managed events (simplified)

    public Organizer(String userId,String firstName, String lastName, String email, String password) {
        super(userId,firstName, lastName, email, password); // Assign incremented id as string
        this.managedEvents = new String[10]; // Example size


    }

    // Method to create an event
    public void createEvent(String eventName) {
        System.out.println(firstName + " has created the event: " + eventName);
        // Logic to add event to managedEvents array
    }

    // View managed events
    public void viewManagedEvents() {
        System.out.println("Managed events for " + getFirstName() + ":");
        for (String event : managedEvents) {
            if (event != null) {
                System.out.println("- " + event);
            }
        }
    }
}
