package com.example.sda_combine;

import java.util.ArrayList;
import java.util.List;

public class Event {
    private int eventId;
    private String name;
    private String organizerEmail;  // Renamed to organizerEmail from organizerId for consistency
    private String description;
    private String venue;
    private DateTime dateTime;  // Using the DateTime class
    private List<Feedback> feedbackList;
    private List<Team> verifiedTeams; // List of verified teams (only verified teams are stored here)
    private int teamSize;   // Size of a team
    private int teamLimit;  // Maximum number of teams
    private int registrationFee; // Registration fee for the event
    private String accountNumber; // New attribute for account number
    private List<String> announcements; // List for storing announcements

    // Constructor with all parameters including new announcements attribute
    public Event(int eventId, String name, String organizerEmail, String description,
                 String venue, DateTime dateTime,
                 int teamSize, int teamLimit, int registrationFee, String accountNumber) {
        this.eventId = eventId;
        this.name = name;
        this.organizerEmail = organizerEmail;
        this.description = description;
        this.venue = venue;
        this.dateTime = dateTime;
        this.teamSize = teamSize;
        this.teamLimit = teamLimit;
        this.registrationFee = registrationFee;
        this.accountNumber = accountNumber;  // Initialize account number
        this.announcements = new ArrayList<>();
        this.feedbackList = new ArrayList<>();
        this.verifiedTeams = new ArrayList<>();
    }

    // Overloaded constructor without team attributes
    public Event(int eventId, String name, String organizerEmail, String description,
                 String venue, DateTime dateTime, String accountNumber) {
        this(eventId, name, organizerEmail, description, venue, dateTime, 0, 0, 0, accountNumber);
    }
    public Event(int eventId, String name, String organizerEmail, String description, String venue,
                 int teamSize, int teamLimit, int registrationFee, String accountNumber,
                 String date, String startTime, String endTime) {
        this.eventId = eventId;
        this.name = name;
        this.organizerEmail = organizerEmail;
        this.description = description;
        this.venue = venue;
        this.teamSize = teamSize;
        this.teamLimit = teamLimit;
        this.registrationFee = registrationFee;
        this.accountNumber = accountNumber;
        this.dateTime=new DateTime();
        this.dateTime.setDate(date);
        this.dateTime.setStartTime(startTime);
        this.dateTime.setEndTime(endTime);
        this.announcements = new ArrayList<>();
        this.feedbackList = new ArrayList<>();
        this.verifiedTeams = new ArrayList<>();
    }

    // Getters and Setters
    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrganizerEmail() {
        return organizerEmail;
    }

    public void setOrganizerEmail(String organizerEmail) {
        this.organizerEmail = organizerEmail;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public DateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(DateTime dateTime) {
        this.dateTime = dateTime;
    }

    public List<Feedback> getFeedbackList() {
        return feedbackList;
    }

    public void setFeedbackList(List<Feedback> feedbackList) {
        this.feedbackList = feedbackList;
    }

    public int getTeamSize() {
        return teamSize;
    }

    public void setTeamSize(int teamSize) {
        this.teamSize = teamSize;
    }

    public int getTeamLimit() {
        return teamLimit;
    }

    public void setTeamLimit(int teamLimit) {
        this.teamLimit = teamLimit;
    }

    public int getRegistrationFee() {
        return registrationFee;
    }

    public void setRegistrationFee(int registrationFee) {
        this.registrationFee = registrationFee;
    }

    public String getAccountNumber() {
        return accountNumber; // Getter for account number
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber; // Setter for account number
    }

    public List<Team> getVerifiedTeams() {
        return verifiedTeams;
    }

    public int getVerifiedTeamsize() {
        if(verifiedTeams == null)
        {
            return 0;
        }
        else return verifiedTeams.size();
    }

    public void addTeam(Team team) {
        this.verifiedTeams.add(team);
    }

    public List<String> getAnnouncements() {
        return announcements; // Getter for announcements list
    }

    public void setAnnouncements(List<String> announcements) {
        this.announcements = announcements; // Setter for announcements list
    }

    public void addAnnouncement(String announcement) {
        this.announcements.add(announcement); // Add an announcement to the list
    }

    // toString method for displaying event information
    @Override
    public String toString() {
        return "Event{" +
                "eventId='" + eventId + '\'' +
                ", name='" + name + '\'' +
                ", organizerEmail='" + organizerEmail + '\'' +
                ", description='" + description + '\'' +
                ", venue='" + venue + '\'' +
                ", dateTime=" + dateTime +
                ", feedbackList=" + feedbackList +
                ", verifiedTeams=" + verifiedTeams +
                ", teamSize=" + teamSize +
                ", teamLimit=" + teamLimit +
                ", registrationFee=" + registrationFee +
                ", accountNumber='" + accountNumber + '\'' +
                ", announcements=" + announcements +
                '}';
    }

    // Method to add feedback to the list
    public void addFeedback(Feedback feedback) {
        feedbackList.add(feedback);
    }
}
