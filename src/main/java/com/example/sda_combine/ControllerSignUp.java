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

public class ControllerSignUp {

    private Controller controller = Controller.getInstance();

    @FXML
    private TextField IT_1; // First Name
    @FXML
    private TextField IT_4; // First Name

    @FXML
    private TextField IT_2; // Last Name
    @FXML
    private TextField IT_3; // Email
    @FXML
    private PasswordField PT_1; // Password
    @FXML
    private Label LB_1; // Error Label
    @FXML
    private Button B_1; // Sign-Up Button
    @FXML
    private ToggleGroup TG_1; // Group for Radio Buttons


    @FXML
    private void handleSignUp() {
        // Get input values
        String firstName = IT_1.getText();
        String lastName = IT_2.getText();
        String email = IT_3.getText();
        String password = PT_1.getText();
        RadioButton selectedRadioButton = (RadioButton) TG_1.getSelectedToggle();
        String userType = (selectedRadioButton != null) ? selectedRadioButton.getText() : null;
        String adminKey = IT_4.getText();

        // Validate inputs
        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            LB_1.setText("All fields are required!");
            LB_1.setStyle("-fx-text-fill: red;");
        } else if (!email.contains("@nu.edu.pk")) {
            LB_1.setText("Invalid email format (Use University emails)!");
            LB_1.setStyle("-fx-text-fill: red;");
        } else if (userType == null) {
            LB_1.setText("Please select a user type!");
            LB_1.setStyle("-fx-text-fill: red;");
        } else if ("Admin".equals(userType)) {
            // Check if admin key is correct
            if (adminKey.isEmpty()) {
                LB_1.setText("Admin key is required for Admin registration!");
                LB_1.setStyle("-fx-text-fill: red;");
            } else if (!"root123".equals(adminKey)) {
                LB_1.setText("Invalid Admin key!");
                LB_1.setStyle("-fx-text-fill: red;");
            } else {
                // Register Admin user
                registerUser(firstName, lastName, email, password, userType);
            }
        } else {
            // Register non-admin user
            registerUser(firstName, lastName, email, password, userType);
        }
    }

    private void registerUser(String firstName, String lastName, String email, String password, String userType) {
        // Call the Controller's registerUser method
        boolean result = controller.registerUser(firstName, lastName, email, password, userType);

        if (!result) {
            // Display error message if user already exists
            LB_1.setText("Error: User already exists with entered email!");
            LB_1.setStyle("-fx-text-fill: red;");
        } else {
            // Display success message
            LB_1.setText("Sign-Up Successful!");
            LB_1.setStyle("-fx-text-fill: green;"); // Set label color to green
        }
    }



    @FXML
    public void switchToSignIn(MouseEvent event) {
        try {
            // Load the Sign In FXML file
            Parent signInRoot = FXMLLoader.load(getClass().getResource("sign-in.fxml"));

            // Get the current stage
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Set the scene for the stage
            stage.setScene(new Scene(signInRoot));

            // Show the updated stage
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}