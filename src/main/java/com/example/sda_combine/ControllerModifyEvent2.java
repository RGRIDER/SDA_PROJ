package com.example.sda_combine;
import java.time.LocalTime;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.util.List;

public class ControllerModifyEvent2 {

    private String eventName;  // Variable to store the selected event name

    @FXML
    private TextField startTimeField; // Text field for start time
    @FXML
    private TextField endTimeField;   // Text field for end time
    @FXML
    private TextField venueField;    // Text field for venue
    @FXML
    private TextArea descriptionField; // Text area for description
    @FXML
    private Label errorMessageLabel; // Label for error messages

    // Populate the event details into the input fields
    public void populateEventDetails(String eventName) {
        this.eventName = eventName; // Store the event name
        try {
            // Get event details from the Controller
            Controller controller = Controller.getInstance();
            List<String> eventDetails = controller.getEventDetailsByName(eventName);

            // Populate fields with the event details
            startTimeField.setText(eventDetails.get(0)); // Start Time
            endTimeField.setText(eventDetails.get(1));   // End Time
            venueField.setText(eventDetails.get(2));     // Venue
            descriptionField.setText(eventDetails.get(3)); // Description
            errorMessageLabel.setVisible(false);         // Hide error message
        } catch (IllegalArgumentException e) {
            errorMessageLabel.setText(e.getMessage());   // Display error message
            errorMessageLabel.setVisible(true);
        }
    }

    // Method to handle submit button click
    public void onSubmitClicked() {
        try {
            // Get updated values from input fields
            String updatedStartTime = startTimeField.getText().trim();
            String updatedEndTime = endTimeField.getText().trim();
            String updatedVenue = venueField.getText().trim();
            String updatedDescription = descriptionField.getText().trim();

            // Validation: Ensure all fields are non-empty
            if (updatedStartTime.isEmpty() || updatedEndTime.isEmpty() || updatedVenue.isEmpty() || updatedDescription.isEmpty()) {
                errorMessageLabel.setText("All fields must be filled.");
                errorMessageLabel.setVisible(true);
                return;
            }

            // Validation: Start and end times must follow the format "HH:mm:ss"
            if (!updatedStartTime.matches("\\d{2}:\\d{2}:\\d{2}") || !updatedEndTime.matches("\\d{2}:\\d{2}:\\d{2}")) {
                errorMessageLabel.setText("Start and End times must be in the format HH:mm:ss.");
                errorMessageLabel.setVisible(true);
                return;
            }

            // Parse start and end times to validate their order
            LocalTime startTime = LocalTime.parse(updatedStartTime);
            LocalTime endTime = LocalTime.parse(updatedEndTime);

            // Validation: Start time must be before end time
            if (!startTime.isBefore(endTime)) {
                errorMessageLabel.setText("Start time must be earlier than end time.");
                errorMessageLabel.setVisible(true);
                return;
            }

            // Validation: Times must be between 07:00:00 and 22:00:00
            LocalTime earliestAllowedTime = LocalTime.of(7, 0, 0);
            LocalTime latestAllowedTime = LocalTime.of(22, 0, 0);

            if (startTime.isBefore(earliestAllowedTime) || endTime.isAfter(latestAllowedTime)) {
                errorMessageLabel.setText("Times must be between 07:00:00 and 22:00:00.");
                errorMessageLabel.setVisible(true);
                return;
            }

            // If all validations pass, update the event object in the Controller
            Controller controller = Controller.getInstance();
            controller.updateEventDetails(eventName, updatedStartTime, updatedEndTime, updatedVenue, updatedDescription);

            errorMessageLabel.setText("Event details updated successfully!");
            errorMessageLabel.setVisible(true);
        } catch (Exception e) {
            errorMessageLabel.setText("Failed to update event details: " + e.getMessage());
            errorMessageLabel.setVisible(true);
        }
    }

}
