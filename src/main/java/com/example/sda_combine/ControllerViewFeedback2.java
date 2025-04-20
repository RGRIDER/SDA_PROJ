package com.example.sda_combine;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

import java.util.List;

public class ControllerViewFeedback2 {

    @FXML
    private TextArea feedbackTextArea;

    private String eventName;

    // Set the event name to display the feedbacks
    public void setEventName(String eventName) {
        this.eventName = eventName;
        loadFeedbacks();  // Load feedbacks for the selected event
    }

    // This method will load the feedbacks for the selected event and display them
    private void loadFeedbacks() {
        // Use the controller to get feedbacks for the event
        Controller controller = Controller.getInstance();
        List<String> feedbacks = controller.getFeedbacksForEventByName(eventName);

        // Display the feedbacks in a more structured and visually appealing way
        StringBuilder feedbackDisplay = new StringBuilder();

        if (feedbacks.isEmpty()) {
            feedbackDisplay.append("No feedback available for this event.");
        } else {
            for (String feedback : feedbacks) {
                // Assuming each feedback is formatted as "Rating: X, Comments: Y"
                String[] feedbackParts = feedback.split(", ");  // Split by comma for formatting

                // Assuming feedback is structured like "Rating: X" and "Comments: Y"
                String rating = "Rating: Not available";
                String comments = "No comments provided";

                for (String part : feedbackParts) {
                    if (part.startsWith("Rating:")) {
                        rating = part;  // Extract rating
                    } else if (part.startsWith("Comment:")) {
                        comments = part.substring(9);  // Remove "Comments:" part
                    }
                }

                // Add a separator and formatted feedback
                feedbackDisplay.append("-------------------------------------------------------------------------------\n")
                        .append("Feedback:\n")
                        .append("  ").append(rating).append("\n")
                        .append("  ").append("Comment: ").append(comments).append("\n")
                        .append("-------------------------------------------------------------------------------------\n\n");
            }
        }

        feedbackTextArea.setText(feedbackDisplay.toString());
    }
}
