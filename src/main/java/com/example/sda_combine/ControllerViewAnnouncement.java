package com.example.sda_combine;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.util.List;

public class ControllerViewAnnouncement {

    // The logged-in participant's email, set from the previous controller
    private String email;

    // FXML components
    @FXML
    private TextArea announcementTextArea;

    @FXML
    private Label instructionsLabel;

    // Singleton instance of the Controller class
    private Controller controller = Controller.getInstance();

    // Method to set user details (passed from the previous controller)
    public void setUserDetails(String email, String password, String userType) {
        this.email = email;
    }

    // Method to initialize announcements for the participant
    public void initializeAnnouncements() {
        // Get announcements for the participant using their email
        List<String> announcements = controller.getParticipantAnnouncements(email);

        // Convert the list of announcements to a single string with line breaks
        StringBuilder announcementText = new StringBuilder();
        for (String announcement : announcements) {
            announcementText.append(announcement).append("\n");
        }

        // Set the formatted announcements text in the TextArea
        announcementTextArea.setText(announcementText.toString());
    }
}
