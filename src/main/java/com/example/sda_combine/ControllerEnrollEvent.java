package com.example.sda_combine;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class ControllerEnrollEvent {

    @FXML
    private ListView<String> eventListView; // List view to display events
    @FXML
    private VBox teamEmailInputs; // VBox to hold dynamic email input fields
    @FXML
    private Label errorLabel; // Label to display error messages

    @FXML
    private Button b1;

    private String email; // The logged-in participant's email
    private String password; // The password (not used directly in this scenario)
    private String userType; // User type (not used directly in this scenario)

    // Method to set user details (passed from the previous controller)
    public void setUserDetails(String email, String password, String userType) {
        this.email = email;
        this.password = password;
        this.userType = userType;
    }

    // Method to initialize the event list and populate the events
    public void initialize() {
        // Get the list of event names from Controller
        Controller controller = Controller.getInstance();
        List<String> events = controller.getAllEventNames(); // Get event names
        eventListView.getItems().setAll(events); // Display event names in the ListView
    }

    // Method called when the "Enroll in Event" button is pressed
    // Method called when the "Enroll in Event" button is pressed
    @FXML
    public void onEnrollEvent() {
        errorLabel.setText("");

        String selectedEvent = eventListView.getSelectionModel().getSelectedItem();
        if (selectedEvent == null) {
            errorLabel.setText("No event selected. Please select an event to enroll in.");
            return;
        }

        Controller controller = Controller.getInstance();
        int teamSize = controller.getTeamSizeByEventName(selectedEvent);

        // Clear existing inputs
        teamEmailInputs.getChildren().clear();

        eventListView.setVisible(false);
        b1.setVisible(false);

        // Set alignment for the VBox to ensure left alignment
        teamEmailInputs.setAlignment(Pos.TOP_LEFT); // Align all inputs to the top-left
        teamEmailInputs.setSpacing(10); // Add spacing between elements
        teamEmailInputs.setPadding(new Insets(20, 20, 20, 0)); // Add padding around the inputs

        // Team name input
        TextField teamNameField = new TextField();
        teamNameField.setPromptText("Enter team name");
        teamNameField.setMinWidth(300); // Adjust width for consistency
        teamEmailInputs.getChildren().add(teamNameField);

        // Participant email input (readonly)
        TextField participantEmailField = new TextField(email);
        participantEmailField.setDisable(true);
        participantEmailField.setPromptText("Your email (participant)");
        participantEmailField.setMinWidth(300);
        teamEmailInputs.getChildren().add(participantEmailField);

        // Additional team member emails
        for (int i = 1; i < teamSize; i++) {
            TextField emailInput = new TextField();
            emailInput.setPromptText("Enter email of team member " + i);
            emailInput.setMinWidth(300);
            teamEmailInputs.getChildren().add(emailInput);
        }

        // Submit button
        Button submitButton = new Button("Submit Team");
        submitButton.setOnAction(e -> submitTeam(selectedEvent, teamNameField, teamSize));
        teamEmailInputs.getChildren().add(submitButton);
    }




    // Method to submit the team
    private void submitTeam(String eventName, TextField teamNameField, int teamSize) {
        errorLabel.setText(""); // Clear previous errors

        String teamName = teamNameField.getText().trim();
        if (teamName.isEmpty()) {
            errorLabel.setText("Team name cannot be empty.");
            return;
        }

        List<String> memberEmails = new ArrayList<>();

        // Add the logged-in participant's email
        memberEmails.add(email);

        // Validate the emails of other team members
        Controller controller = Controller.getInstance();
        for (int i = 1; i < teamSize; i++) {
            TextField emailField = (TextField) teamEmailInputs.getChildren().get(i + 1); // Skip team name and participant email
            String memberEmail = emailField.getText().trim();

            // Validate email format
            if (!memberEmail.endsWith("@nu.edu.pk")) {
                errorLabel.setText("Invalid email format for " + memberEmail + ". Use your university email (@nu.edu.pk).");
                return;
            }

            // Validate participant existence
            if (!controller.doesParticipantExist(memberEmail)) {
                errorLabel.setText("Participant with email " + memberEmail + " does not exist.");
                return;
            }

            memberEmails.add(memberEmail);
        }

        // Validate the number of emails
        if (memberEmails.size() != teamSize) {
            errorLabel.setText("The number of team members does not match the required team size.");
            return;
        }

        // Call Controller method to add the team to the event
        controller.addTeamToEvent(eventName, teamName, memberEmails);

        // Display success message
        errorLabel.setText("Team successfully enrolled in the event!");
    }
}
