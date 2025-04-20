package com.example.sda_combine;

public class User {
    protected String userId;
    protected String firstName;
    protected String lastName;
    protected String email;
    protected String password;

    // Constructor
    public User(String userId, String firstName, String lastName, String email, String password) {
        this.userId = userId; // User ID is passed as an argument
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public void registerUser(String userId, String firstName, String lastName, String email, String password, String userType) {
        Database db = new Database();
        db.registerUser(userId, firstName, lastName, email, password, userType);
    }

    // Getters and Setters
    public String getFirstName() {
        return firstName;
    }

    public String getUserId() {
        return userId;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Method for login validation
    public boolean validateLogin(String email, String password) {
        return this.email.equals(email) && this.password.equals(password);
    }
}
