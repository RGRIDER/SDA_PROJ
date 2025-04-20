package com.example.sda_combine;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.util.List;

public class ControllerModifyEvent {

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
        List<String> eventNames = controller.getEventsByOrganizerEmail(email);  // Call the function to get event names

        // Create an ObservableList from the event names list and update the ListView
        ObservableList<String> observableEventNames = FXCollections.observableArrayList(eventNames);
        eventListView.setItems(observableEventNames);  // Set the items for the ListView
    }

    // You can also add this method to handle when the proceed button is clicked

    public void onProceedClicked() {
        // Get the selected event name from the ListView
        String selectedEvent = eventListView.getSelectionModel().getSelectedItem();

        // Check if an event is selected
        if (selectedEvent != null) {
            try {
                // Load the FXML file for the next screen (modify-event2.fxml)
                FXMLLoader loader = new FXMLLoader(getClass().getResource("modify-event2.fxml"));
                Parent root = loader.load();

                // Get the controller for the modify-event2.fxml
                ControllerModifyEvent2 modifyEvent2Controller = loader.getController();

                // Pass the selected event name to the next controller
                modifyEvent2Controller.populateEventDetails(selectedEvent);

                // Set up the new stage (window)
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle("Modify Event - Step 2");
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // Handle case where no event is selected
            System.out.println("Please select an event to proceed.");
        }
    }

}
