package com.example.sda_combine;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ControllerCreateEvent {
    private String email;
    private String password;
    private String userType;

    @FXML
    private TextField eventNameField;
    @FXML
    private TextField dateField;
    @FXML
    private TextField startTimeField;
    @FXML
    private TextField endTimeField;
    @FXML
    private TextField venueNameField;
    @FXML
    private TextArea descriptionArea;
    @FXML
    private TextField teamSizeField;
    @FXML
    private TextField teamLimitField;
    @FXML
    private TextField registrationFeeField;
    @FXML
    private TextField accountNumberField;
    @FXML
    private Label errorLabel;

    public void setUserDetails(String email, String password, String userType) {
        this.email = email;
        this.password = password;
        this.userType = userType;
    }

    @FXML
    private void onCreateEvent() {
        try {

            // Fetch inputs
            String eventName = eventNameField.getText().trim();
            String date = dateField.getText().trim();
            String startTime = startTimeField.getText().trim();
            String endTime = endTimeField.getText().trim();
            String venueName = venueNameField.getText().trim();
            String description = descriptionArea.getText().trim();
            String accountNumber = accountNumberField.getText().trim();

            // Parse numeric fields with proper validation
            int teamSize;
            int teamLimit;
            int registrationFee;

            try {
                teamSize = Integer.parseInt(teamSizeField.getText().trim());
                teamLimit = Integer.parseInt(teamLimitField.getText().trim());
                registrationFee = Integer.parseInt(registrationFeeField.getText().trim());
            } catch (NumberFormatException e) {
                errorLabel.setText("Team size, team limit, and registration fee must be valid numbers.");
                return;
            }

            // Validate that all mandatory fields are filled
            if (eventName.isEmpty() || date.isEmpty() || startTime.isEmpty() || endTime.isEmpty()
                    || venueName.isEmpty() || description.isEmpty() || accountNumber.isEmpty()) {
                errorLabel.setText("Please fill in all fields.");
                return;
            }

            // Check if event already exists
            Controller controller = Controller.getInstance();
            if (controller.eventExistsByName(eventName)) {
                errorLabel.setText("An event with this name already exists.");
                return;
            }

            // Validate date format
            if (!date.matches("\\d{4}-\\d{2}-\\d{2}")) {
                errorLabel.setText("Invalid date format. Use YYYY-MM-DD.");
                return;
            }

            // Validate time format (HH:MM:SS) and constraints
            if (!isValidTimeFormat(startTime) || !isValidTimeFormat(endTime)) {
                errorLabel.setText("Invalid time format. Use HH:MM:SS.");
                return;
            }

            LocalTime start = LocalTime.parse(startTime);
            LocalTime end = LocalTime.parse(endTime);

            // Check university policy for event timings
            LocalTime minTime = LocalTime.of(8, 0, 0);
            LocalTime maxTime = LocalTime.of(22, 0, 0);

            if (start.isBefore(minTime) || end.isAfter(maxTime)) {
                errorLabel.setText("University policy requires events to be scheduled between 08:00:00 and 22:00:00.");
                return;
            }

            // Ensure start time is before end time
            if (!start.isBefore(end)) {
                errorLabel.setText("Start time must be earlier than end time.");
                return;
            }

            // Validate numeric constraints
            if (teamSize <= 0) {
                errorLabel.setText("Team size must be greater than 0.");
                return;
            }

            if (teamLimit < teamSize) {
                errorLabel.setText("Team limit cannot be smaller than team size.");
                return;
            }

            if (registrationFee < 0) {
                errorLabel.setText("Registration fee cannot be negative.");
                return;
            }

            // Perform further logical validations (if any)

            // Create the event
            controller.createEvent(email, eventName, date, startTime, endTime, venueName, description,
                    teamSize, teamLimit, registrationFee, accountNumber);
            errorLabel.setText("Event created successfully!");
        } catch (Exception e) {
            errorLabel.setText("Error creating event: " + e.getMessage());
            e.printStackTrace();
        }
    }



    // Helper method to validate time format (HH:MM:SS)
    private boolean isValidTimeFormat(String time) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            LocalTime.parse(time, formatter);  // This will throw an exception if the time is invalid
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}
