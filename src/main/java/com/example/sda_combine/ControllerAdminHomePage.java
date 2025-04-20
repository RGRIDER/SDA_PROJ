package com.example.sda_combine;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ControllerAdminHomePage {

    private String email;
    private String password;
    private String userType;

    @FXML
    private Button fullReportButton; // Button to view the full report


    // Setter for user details
    public void setUserDetails(String email, String password, String userType) {
        this.email = email;
        this.password = password;
        this.userType = userType;
    }

    @FXML
    public void onFullReport() {
        try {
            // Load the FXML file for the full report
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/sda_combine/full-report.fxml"));
            Parent root = loader.load();

            // Retrieve the controller and pass data
            ControllerFullReport controller = loader.getController();
            controller.fetchAndDisplayReport();

            // Set up the scene and stage for the full report
            Stage stage = new Stage();
            stage.setTitle("Full Event Report");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to load full-report.fxml");
        }
    }

    @FXML
    public void onRemoveEvent() {
        // Logic for removing an event
        System.out.println("Remove Event button clicked");
    }
}
