package com.example.sda_combine;
import java.util.ArrayList;
import java.util.List;

public class Venue {
    private List<String> venues;

    // Constructor to initialize the venues list
    public Venue() {
        this.venues = new ArrayList<>();
        this.venues.add("Cricket Ground");
        this.venues.add("Futsal Ground");
        this.venues.add("Cafeteria");
        this.venues.add("Rawal-1");
        this.venues.add("Rawal-2");
        this.venues.add("Margalla-1");
        this.venues.add("Margalla-2");
        this.venues.add("Karakoram-1");
        this.venues.add("Karakoram-2");
        this.venues.add("Khyber-1");
        this.venues.add("Khyber-2");
        this.venues.add("Auditorium");
    }

    // Getter for venues list
    public List<String> getVenues() {
        return venues;
    }

    // Setter for venues list
    public void setVenues(List<String> venues) {
        this.venues = venues;
    }

    // Method to add a new venue
    public void addVenue(String venue) {
        if (venue != null && !venue.isEmpty() && !venues.contains(venue)) {
            venues.add(venue);
        } else {
            System.out.println("Venue is either null, empty, or already exists.");
        }
    }

    // Method to remove a venue
    public void removeVenue(String venue) {
        if (venues.contains(venue)) {
            venues.remove(venue);
        } else {
            System.out.println("Venue not found.");
        }
    }
}
