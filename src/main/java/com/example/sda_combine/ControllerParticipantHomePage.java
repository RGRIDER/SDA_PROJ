package com.example.sda_combine;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ControllerParticipantHomePage {
    private String email="i221196@nu.edu.pk";
    private String password="123";
    private String userType="Participant";

    // Setter method for user details
    public void setUserDetails(String email, String password, String userType) {
        this.email = email;
        this.password = password;
        this.userType = userType;
    }

    // Method to handle event enrollment
    public void onEnrollEvent() {
        try {
            // Load the FXML file for the enroll event page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/sda_combine/enroll-event.fxml"));
            Parent root = loader.load();

            // Retrieve the controller and pass the user details
            ControllerEnrollEvent controller = loader.getController();
            controller.setUserDetails(email, password, userType); // Passing user details to the next controller

            // Set up the scene and stage for the enroll event page
            Stage stage = new Stage();
            stage.setTitle("Enroll for Event");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to load enroll-event.fxml");
        }
    }

    public void onEventDetails()
    {
        try {
            // Load the FXML file for the enroll event page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/sda_combine/participant-event-details.fxml"));
            Parent root = loader.load();

            // Retrieve the controller and pass the user details
            ControllerParticipantEventDetails controller = loader.getController();
            controller.setUserDetails(email, password, userType); // Passing user details to the next controller
            controller.populateEventList();


            Stage stage = new Stage();
            stage.setTitle("View Announcements");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to load participant-event-details.fxml");
        }
    }

    public void onAnnouncements(){
        try {
            // Load the FXML file for the enroll event page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/sda_combine/view-announcement.fxml"));
            Parent root = loader.load();

            // Retrieve the controller and pass the user details
            ControllerViewAnnouncement controller = loader.getController();
            controller.setUserDetails(email, password, userType); // Passing user details to the next controller
            controller.initializeAnnouncements();


            Stage stage = new Stage();
            stage.setTitle("Event Details");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to load participant-event-details.fxml");
        }
    }

    public void onFeedback(){
        try {
            // Load the FXML file for the enroll event page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/sda_combine/submit-feedback.fxml"));
            Parent root = loader.load();

            // Retrieve the controller and pass the user details
            ControllerSubmitFeedback controller = loader.getController();
            controller.setUserDetails(email, password, userType); // Passing user details to the next controller
            controller.populateEventNames();


            Stage stage = new Stage();
            stage.setTitle("Add Feedback");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to load submit-feedback.fxml");
        }
    }


}
