package com.example.sda_combine;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.util.List;

public class ControllerFullReport {

    @FXML
    private TextArea reportTextArea; // Connected to the TextField in the FXML



    public void fetchAndDisplayReport() {
        // Access the main controller to fetch the reports
        Controller controller = Controller.getInstance(); // Assuming Controller is a Singleton

        // Generate the full report
        List<String> reports = controller.generateFullReport();

        // Combine the reports into a single string
        StringBuilder fullReport = new StringBuilder();
        for (String report : reports) {
            fullReport.append(report).append("\n\n"); // Separate each report by a blank line
        }

        // Ensure the TextArea is read-only and update it with the full report
        reportTextArea.setEditable(false); // Set TextArea to read-only
        reportTextArea.setText(fullReport.toString());
    }

}
