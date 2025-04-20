package com.example.sda_combine;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

import java.util.List;

public class ControllerParticipantEventDetails {

    @FXML
    private ListView<String> eventListView; // To display event names

    @FXML
    private TextArea eventDetailsTextArea; // To display event details

    private String email; // The logged-in participant's email
    private String password; // The password (not used directly in this scenario)
    private String userType; // User type (not used directly in this scenario)

    // Method to set user details (passed from the previous controller)
    public void setUserDetails(String email, String password, String userType) {
        this.email = email;
        this.password = password;
        this.userType = userType;
    }

    private String formatEventDetails(List<String> eventDetails) {
        if (eventDetails != null && eventDetails.size() == 4) {
            return "Start Time: " + eventDetails.get(0) + "\n" +
                    "End Time: " + eventDetails.get(1) + "\n" +
                    "Venue: " + eventDetails.get(2) + "\n" +
                    "Description: " + eventDetails.get(3);
        }
        return "Event details are not available.";
    }


    @FXML
    public void onShowEventDetails() {
        // Get the selected event from the ListView
        String selectedEvent = eventListView.getSelectionModel().getSelectedItem();

        if (selectedEvent != null) {
            // Call the method from Controller to get event details
            Controller controller = Controller.getInstance(); // Get instance of Controller class
            List<String> eventDetails = controller.getEventDetailsByName(selectedEvent); // Fetch the event details as a list of strings

            // Format the event details for display in the TextArea
            String eventDetailsFormatted = formatEventDetails(eventDetails);

            // Display the event details in the TextArea
            eventDetailsTextArea.setText(eventDetailsFormatted);
        } else {
            // If no event is selected, show an alert
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Event Selected");
            alert.setHeaderText(null);
            alert.setContentText("Please select an event from the list.");
            alert.showAndWait();
        }
    }

    public void populateEventList() {
        Controller controller = Controller.getInstance();  // Get instance of Controller class
        List<String> eventNames = controller.getAllEventNames();  // Call the function to get event names

        // Create an ObservableList from the event names list and update the ListView
        ObservableList<String> observableEventNames = FXCollections.observableArrayList(eventNames);
        eventListView.setItems(observableEventNames);  // Set the items for the ListView
    }
}

