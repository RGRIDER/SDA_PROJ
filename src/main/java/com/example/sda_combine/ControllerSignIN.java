package com.example.sda_combine;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class ControllerSignIN {

    @FXML
    private TextField IT_4; // University Email

    @FXML
    private PasswordField PT_2; // Password

    @FXML
    private ToggleGroup TG_2; // User Type (Admin, Participant, Organizer)

    @FXML
    private Button B_2; // Sign-In Button

    @FXML
    private Label LB_2; // Label to display errors or success messages

    @FXML
    private void handleSignIn() {
        // Get input values
        String email = IT_4.getText();
        String password = PT_2.getText();
        RadioButton selectedRadioButton = (RadioButton) TG_2.getSelectedToggle();
        String userType = (selectedRadioButton != null) ? selectedRadioButton.getText() : null;

        // Validate inputs
        if (email.isEmpty() || password.isEmpty()) {
            LB_2.setText("Email and Password are required!");
            LB_2.setStyle("-fx-text-fill: red;");
        } else if (!email.contains("@nu.edu.pk")) {
            LB_2.setText("Invalid email format (Use University emails)!");
            LB_2.setStyle("-fx-text-fill: red;");
        } else if (userType == null) {
            LB_2.setText("Please select a user type!");
            LB_2.setStyle("-fx-text-fill: red;");
        } else {
            LB_2.setText(""); // Clear error messages

            // Call Login function from the centralized Controller
            Controller controller = Controller.getInstance();
            boolean loginSuccessful = controller.Login(email, password, userType);

            // Display appropriate message based on login result
            if (loginSuccessful) {
                LB_2.setText("Login Successful!");
                LB_2.setStyle("-fx-text-fill: green;");

                // Navigate to the appropriate home page
                navigateToHomePage(userType, email, password);
            } else {
                LB_2.setText("User Not Found!");
                LB_2.setStyle("-fx-text-fill: red;");
            }
        }
    }

    /**
     * Navigate to the appropriate home page based on user type.
     */
    private void navigateToHomePage(String userType, String email, String password) {
        String fxmlFile = "";

        // Determine the FXML file based on user type
        switch (userType) {
            case "Admin":
                fxmlFile = "admin-home-page.fxml";
                break;
            case "Organizer":
                fxmlFile = "organizer-home-page.fxml";
                break;
            case "Participant":
                fxmlFile = "participant-home-page.fxml";
                break;
            default:
                LB_2.setText("Unknown user type!");
                LB_2.setStyle("-fx-text-fill: red;");
                return; // Exit if no valid user type
        }

        try {
            // Load the FXML file for the respective home page
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent homePageRoot = fxmlLoader.load();

            // Dynamically determine the controller type
            Object controller = fxmlLoader.getController();
            if (controller instanceof ControllerParticipantHomePage) {
                ((ControllerParticipantHomePage) controller).setUserDetails(email, password, userType);
            } else if (controller instanceof ControllerOrganizerHomePage) {
                ((ControllerOrganizerHomePage) controller).setUserDetails(email, password, userType);
            } else if (controller instanceof ControllerAdminHomePage) {
                ((ControllerAdminHomePage) controller).setUserDetails(email, password, userType);
            } else {
                throw new IllegalStateException("Unknown controller type: " + controller.getClass().getName());
            }

            // Get the current stage and set the new scene
            Stage stage = (Stage) B_2.getScene().getWindow();
            stage.setScene(new Scene(homePageRoot));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            LB_2.setText("Error loading the home page!");
            LB_2.setStyle("-fx-text-fill: red;");
        } catch (IllegalStateException e) {
            e.printStackTrace();
            LB_2.setText("Controller setup failed: " + e.getMessage());
            LB_2.setStyle("-fx-text-fill: red;");
        }
    }

    @FXML
    public void switchToSignUp(MouseEvent event) {
        try {
            // Load the Sign Up FXML file
            Parent signUpRoot = FXMLLoader.load(getClass().getResource("/com/example/sda_combine/sign-up.fxml"));

            // Get the current stage
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Set the scene for the stage
            stage.setScene(new Scene(signUpRoot));

            // Show the updated stage
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
