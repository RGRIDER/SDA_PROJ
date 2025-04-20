package com.example.sda_combine;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;

import java.util.List;

public class ControllerSubmitFeedback {

    private String email;
    private String password;
    private String userType;

    // FXML components
    @FXML
    private ComboBox<String> eventComboBox;

    @FXML
    private TextArea feedbackTextArea;

    @FXML
    private TextField ratingTextField;

    private Controller controller = Controller.getInstance();

    // Setter method for user details (passed from previous controller)
    public void setUserDetails(String email, String password, String userType) {
        this.email = email;
        this.password = password;
        this.userType = userType;
    }

    // Method to populate the ComboBox with event names
    public void populateEventNames() {
        List<String> eventNames = controller.getAllEventNames(); // Fetch event names using the controller's method
        eventComboBox.getItems().addAll(eventNames);
    }

    // Method to handle submit feedback action
    @FXML
    public void handleSubmitFeedback(ActionEvent event) {
        // Get selected event, feedback, and rating
        String selectedEvent = eventComboBox.getValue();
        String feedbackText = feedbackTextArea.getText();
        String ratingText = ratingTextField.getText();

        // Validate the inputs
        if (selectedEvent == null || selectedEvent.isEmpty()) {
            showAlert("Validation Error", "Please select an event.");
            return;
        }

        if (feedbackText == null || feedbackText.trim().isEmpty()) {
            showAlert("Validation Error", "Feedback cannot be empty.");
            return;
        }

        // Validate rating
        int rating = -1;
        try {
            rating = Integer.parseInt(ratingText);
        } catch (NumberFormatException e) {
            showAlert("Invalid Rating", "Please enter a valid rating between 1 and 5.");
            return;
        }

        if (rating < 1 || rating > 5) {
            showAlert("Invalid Rating", "Please enter a rating between 1 and 5.");
            return;
        }

        // Submit the feedback via the controller
        boolean feedbackSubmitted = controller.submitFeedback(selectedEvent, feedbackText, rating, email);

        // Show success or error message
        if (feedbackSubmitted) {
            showAlert("Feedback Submitted", "Thank you for your feedback!");
        } else {
            showAlert("Error", "There was an issue submitting your feedback. Please try again.");
        }
    }

    // Helper method to show alerts
    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
