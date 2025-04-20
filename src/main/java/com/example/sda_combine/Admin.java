package com.example.sda_combine;

public class Admin extends User {

    public Admin(String userId, String firstName, String lastName, String email, String password) {
        super(userId, firstName, lastName, email, password); // Pass userId as a super argument

    }

    // Admin-specific methods
    public void manageVenues() {
        System.out.println("Managing venues...");
    }

    public void sendNotifications(String message) {
        System.out.println("Sending notification: " + message);
    }
}
