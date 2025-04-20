package com.example.sda_combine;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class ControllerViewFeedback {

    private String email;
    private String password;
    private String userType;

    @FXML
    private ListView<String> eventListView;  // ListView to display event names

    // Method to set the user details (email, password, userType)
    public void setUserDetails(String email, String password, String userType) {
        this.email = email;
        this.password = password;
        this.userType = userType;
    }

    // This method will be called to populate the event list based on the organizer's email
    public void populateEventList() {
        Controller controller = Controller.getInstance();  // Get instance of Controller class
        List<String> eventNames = controller.getEventsByOrganizerEmail(email);

        ObservableList<String> observableEventNames = FXCollections.observableArrayList(eventNames);
        eventListView.setItems(observableEventNames);  // Set the items for the ListView
    }

    // This method is called when the user clicks the "Proceed" button
    @FXML
    public void onProceedClicked() throws IOException {
        // Get the selected event name from the ListView
        String selectedEventName = eventListView.getSelectionModel().getSelectedItem();

        // Check if an event is selected
        if (selectedEventName != null) {
            // Load the view-feedback2.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("view-feedback2.fxml"));
            Parent root = loader.load();

            // Get the controller of the view-feedback2.fxml
            ControllerViewFeedback2 controllerFeedback2 = loader.getController();

            // Pass the selected event name to the ControllerFeedback2
            controllerFeedback2.setEventName(selectedEventName);

            // Create a new stage and set the scene to display the feedbacks
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("View Feedbacks for " + selectedEventName);
            stage.show();
        } else {
            // If no event is selected, show a message or handle accordingly
            System.out.println("Please select an event.");
        }
    }
}
