package com.example.sda_combine;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ControllerHomePage {

    public Button signUpButton;
    public Button signInButton;

    // Method to handle Sign Up button click
    public void onSignUpButtonClick(ActionEvent event) {
        try {
            // Load the sign-up scene
            AnchorPane signUpPage = FXMLLoader.load(getClass().getResource("sign-up.fxml"));
            Scene signUpScene = new Scene(signUpPage);

            // Get the current stage and set the new scene
            Stage currentStage = (Stage) signUpButton.getScene().getWindow();
            currentStage.setScene(signUpScene);
            currentStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method to handle Sign In button click
    public void onSignInButtonClick(ActionEvent event) {
        try {
            // Load the sign-in scene
            AnchorPane signInPage = FXMLLoader.load(getClass().getResource("sign-in.fxml"));
            Scene signInScene = new Scene(signInPage);

            // Get the current stage and set the new scene
            Stage currentStage = (Stage) signInButton.getScene().getWindow();
            currentStage.setScene(signInScene);
            currentStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
