package com.example.sda_combine;

public class DateTime {
    private String date;
    private String startTime;
    private String endTime;

    // Default constructor
    public DateTime() {
        this.date = "";
        this.startTime = "";
        this.endTime = "";
    }

    // Parameterized constructor
    public DateTime(String date, String startTime, String endTime) {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    // Getters
    public String getDate() {
        return date;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    // Setters
    public void setDate(String date) {
        this.date = date;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    // toString method for displaying object information
    @Override
    public String toString() {
        return "DateTime{" +
                "date='" + date + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                '}';
    }
}
