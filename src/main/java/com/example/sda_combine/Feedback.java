package com.example.sda_combine;

public class Feedback {
    private int eventId;    // ID of the event for which feedback is given
    private String comments;   // Feedback comments
    private int rating;        // Feedback rating (e.g., 1 to 5)

    // Constructor
    public Feedback(int eventId, String comments, int rating) {
        this.eventId = eventId;
        this.comments = comments;
        this.rating = rating;
    }

    // Getters
    public int getEventId() {
        return eventId;
    }

    public String getComments() {
        return comments;
    }

    public int getRating() {
        return rating;
    }

    // Setters
    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    // Overridden toString method
    @Override
    public String toString() {
        return "Event ID: " + eventId + ", Rating: " + rating + ", Comments: " + comments;
    }
}
