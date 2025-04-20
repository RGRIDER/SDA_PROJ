package com.example.sda_combine;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ControllerOrganizerHomePage {
    private String email = "i221196@nu.edu.pk";
    private String password = "123";
    private String userType = "Organizer";

    public void setUserDetails(String email, String password, String userType) {
        this.email = email;
        this.password = password;
        this.userType = userType;
    }

    // Existing method to create an event
    public void onCreateEvent() {
        try {
            // Load the FXML file for creating event
            FXMLLoader loader = new FXMLLoader(getClass().getResource("create-event.fxml"));
            Parent root = loader.load();

            // Get the controller for the create-event.fxml
            ControllerCreateEvent createEventController = loader.getController();

            // Pass data to the create-event controller, including the main controller
            createEventController.setUserDetails(email, password, userType);

            // Set up the new stage (window)
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Create Event");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // New method to modify an event

    public void onModifyEvent() {
        try {
            // Load the FXML file for modifying event
            FXMLLoader loader = new FXMLLoader(getClass().getResource("modify-event.fxml"));
            Parent root = loader.load();

            // Get the controller for the modify-event.fxml
            ControllerModifyEvent modifyEventController = loader.getController();

            // Pass data to the modify-event controller
            modifyEventController.setUserDetails(email, password, userType);

            // Call populateEventList() after setting user details
            modifyEventController.populateEventList();

            // Set up the new stage (window) for modifying event
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Modify Event");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onEventDetails() {
        try {
            // Load the FXML file for event details
            FXMLLoader loader = new FXMLLoader(getClass().getResource("event-details.fxml"));
            Parent root = loader.load();

            // Get the controller for the event-details.fxml
            ControllerEventDetails eventDetailsController = loader.getController();

            // Pass the organizer's email to the event details controller
            eventDetailsController.setOrganizerEmail(email);
            eventDetailsController.populateEventList();

            // Set up the new stage (window) for viewing event details
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Event Details");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onAddAnnouncement() {
        try {
            // Load the "add-announcement.fxml" file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("add-announcement.fxml"));
            Scene scene = new Scene(loader.load());

            // Get the controller for the "add-announcement.fxml" file
            ControllerAddAnnouncement addAnnouncementController = loader.getController();

            // Pass the organizer's email to the add-announcement controller
            addAnnouncementController.setOrganizerEmail(email);

            // Create a new Stage for the "Add Announcement" screen
            Stage stage = new Stage();
            stage.setTitle("Add Announcement");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception (e.g., show an error dialog)
        }
    }
    public void onViewFeedback(){
        try {
            // Load the "add-announcement.fxml" file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("view-feedback.fxml"));
            Scene scene = new Scene(loader.load());

            // Get the controller for the "add-announcement.fxml" file
            ControllerViewFeedback controller = loader.getController();

            // Pass the organizer's email to the add-announcement controller
            controller.setUserDetails(email,password,userType);
            controller.populateEventList();

            // Create a new Stage for the "Add Announcement" screen
            Stage stage = new Stage();
            stage.setTitle("View Feedback");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();

        }


    }

}
