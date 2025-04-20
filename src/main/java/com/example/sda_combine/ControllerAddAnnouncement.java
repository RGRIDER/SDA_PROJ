package com.example.sda_combine;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import java.util.List;

public class ControllerAddAnnouncement {

    private String organizerEmail;

    @FXML
    private ListView<String> eventListView; // Displays event names
    @FXML
    private TextField announcementTextField; // Input for the announcement message
    @FXML
    private Label errorLabel; // Label for error/success messages

    // Method to set the organizer's email and populate the event list
    public void setOrganizerEmail(String email) {
        this.organizerEmail = email;
        populateEventList();
    }

    // Populate the event list for the organizer
    public void populateEventList() {
        Controller controller = Controller.getInstance(); // Get the singleton instance of the Controller
        List<String> eventNames = controller.getEventsByOrganizerEmail(organizerEmail); // Fetch events for the organizer

        // Convert the list of event names to an ObservableList and set it in the ListView
        ObservableList<String> observableEventNames = FXCollections.observableArrayList(eventNames);
        eventListView.setItems(observableEventNames);
    }

    // Method called when the "Add Announcement" button is pressed
    @FXML
    public void onAddAnnouncement() {
        // Get the selected event and announcement text
        String selectedEvent = eventListView.getSelectionModel().getSelectedItem();
        String announcement = announcementTextField.getText().trim();

        // Validation: Check if an event is selected
        if (selectedEvent == null || selectedEvent.isEmpty()) {
            errorLabel.setTextFill(Color.RED);
            errorLabel.setText("Please select an event.");
            return;
        }

        // Validation: Check if the announcement field is empty
        if (announcement.isEmpty()) {
            errorLabel.setTextFill(Color.RED);
            errorLabel.setText("Announcement cannot be empty.");
            return;
        }

        // Call the addAnnouncement method in the Controller
        Controller controller = Controller.getInstance();
        boolean isSuccess = controller.addAnnouncement(selectedEvent, organizerEmail, announcement);

        // Display success or error message based on the result
        if (isSuccess) {
            errorLabel.setTextFill(Color.GREEN);
            errorLabel.setText("Announcement added successfully!");
            announcementTextField.clear(); // Clear the text field
        } else {
            errorLabel.setTextFill(Color.RED);
            errorLabel.setText("Failed to add announcement. Please try again.");
        }
    }
}
