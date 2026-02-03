package application;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class StatusTreatmentForm {
   
    private VBox formContainer;
    private TextField treatmentIdField;
    private TextField patientIdField;
    private TextField patientNameField;
    private ComboBox<String> treatmentTypeComboBox;
    private ComboBox<String> statusComboBox;
    private DatePicker startDatePicker;
    private DatePicker endDatePicker;
    private TextArea treatmentDetailsArea;
    private TextArea progressNotesArea;
    private TextArea medicationsArea;
    
    public StatusTreatmentForm() {
        initializeForm();
    }
    private void initializeForm() {
        // Create form container
        formContainer = new VBox();
        formContainer.setSpacing(20);
        formContainer.setPadding(new Insets(20));
        formContainer.setStyle("-fx-background-color: #f8f9fa;");
      
        // Title
        Label title = new Label("Update Medical Treatment");
        title.setFont(new Font("Arial", 28));
        title.setStyle("-fx-font-weight: bold; -fx-text-fill: #2c3e50; -fx-padding: 0 0 10 0;");
        
        // Subtitle
        Label subtitle = new Label("Update treatment plans and track patient progress");
        subtitle.setStyle("-fx-text-fill: #7f8c8d; -fx-font-size: 14px;");
        
        // Create form grid
        GridPane form = new GridPane();
        form.setVgap(15);
        form.setHgap(20);
        form.setPadding(new Insets(25));
        form.setStyle("-fx-background-color: white; -fx-border-color: #dee2e6; -fx-border-radius: 8; -fx-border-width: 1;");
        
        // Initialize form fields
        treatmentIdField = createTextField(200, "Enter Treatment ID");
        patientIdField = createTextField(200, "Enter Patient ID");
        patientNameField = createTextField(250, "Patient name (auto-filled)");
        patientNameField.setEditable(false);
        patientNameField.setStyle("-fx-background-color: #ecf0f1;");
        
        // Treatment Type ComboBox
        treatmentTypeComboBox = new ComboBox<>();
        treatmentTypeComboBox.getItems().addAll(
            "Medication Therapy",
            "Physical Therapy",
            "Surgical Treatment",
            "Chemotherapy",
            "Radiation Therapy",
            "Psychotherapy",
            "Rehabilitation",
            "Wound Care",
            "Pain Management",
            "Respiratory Therapy",
            "Dialysis",
            "Intravenous Therapy",
            "Occupational Therapy",
            "Speech Therapy",
            "Nutrition Therapy"
        );
        treatmentTypeComboBox.setPromptText("Select treatment type");
        treatmentTypeComboBox.setPrefWidth(250);
        
        // Status ComboBox
        statusComboBox = new ComboBox<>();
        statusComboBox.getItems().addAll(
            "Ongoing - Active Treatment",
            "Completed - Treatment Finished",
            "Discontinued - Treatment Stopped",
            "On Hold - Paused Temporarily",
            "Modified - Adjusted Plan",
            "Successful - Positive Outcome",
            "Unsuccessful - Negative Outcome"
        );
        statusComboBox.setValue("Ongoing - Active Treatment");
        statusComboBox.setPrefWidth(250);
        
        // Date Pickers
        startDatePicker = new DatePicker();
        startDatePicker.setValue(LocalDate.now().minusDays(7));
        startDatePicker.setPrefWidth(150);
        
        endDatePicker = new DatePicker();
        endDatePicker.setPrefWidth(150);
        
        // Text Areas
        treatmentDetailsArea = new TextArea();
        treatmentDetailsArea.setPrefRowCount(4);
        treatmentDetailsArea.setPrefWidth(300);
        treatmentDetailsArea.setPromptText("Enter treatment details and procedures...");
        treatmentDetailsArea.setWrapText(true);
        
        progressNotesArea = new TextArea();
        progressNotesArea.setPrefRowCount(3);
        progressNotesArea.setPrefWidth(300);
        progressNotesArea.setPromptText("Progress notes and observations...");
        progressNotesArea.setWrapText(true);
        
        medicationsArea = new TextArea();
        medicationsArea.setPrefRowCount(3);
        medicationsArea.setPrefWidth(300);
        medicationsArea.setPromptText("Medications prescribed (one per line)...");
        medicationsArea.setWrapText(true);
        
        // Add event listener for patient ID field
        patientIdField.setOnKeyReleased(e -> {
            String patientId = patientIdField.getText().trim();
            if (!patientId.isEmpty()) {
                patientNameField.setText(getPatientName(patientId));
            } else {
                patientNameField.clear();
            }
        });
        
        // Add labels and fields to grid
        int row = 0;
        
        // Row 0: Treatment ID
        form.add(createLabel("Treatment ID:*"), 0, row);
        form.add(treatmentIdField, 1, row);
        row++;
        
        // Row 1: Patient Information
        form.add(createLabel("Patient ID:*"), 0, row);
        form.add(patientIdField, 1, row);
        form.add(createLabel("Patient Name:"), 2, row);
        form.add(patientNameField, 3, row);
        row++;
        
        // Row 2: Treatment Details
        form.add(createLabel("Treatment Type:*"), 0, row);
        form.add(treatmentTypeComboBox, 1, row);
        form.add(createLabel("Status:*"), 2, row);
        form.add(statusComboBox, 3, row);
        row++;
        
        // Row 3: Dates
        form.add(createLabel("Start Date:*"), 0, row);
        form.add(startDatePicker, 1, row);
        form.add(createLabel("End Date:"), 2, row);
        form.add(endDatePicker, 3, row);
        row++;
        
        // Row 4: Doctor Information
        form.add(createLabel("Attending Doctor:"), 0, row);
        ComboBox<String> doctorComboBox = new ComboBox<>();
        doctorComboBox.getItems().addAll(
            "Dr. Smith - Cardiology",
            "Dr. Johnson - Neurology",
            "Dr. Williams - Pediatrics"
        );
        doctorComboBox.setPromptText("Select doctor");
        doctorComboBox.setPrefWidth(250);
        form.add(doctorComboBox, 1, row);
        row++;
        
        // Row 5: Treatment Details
        form.add(createLabel("Treatment Details:*"), 0, row);
        form.add(treatmentDetailsArea, 1, row, 3, 1);
        row++;
        
        // Row 6: Progress Notes
        form.add(createLabel("Progress Notes:*"), 0, row);
        form.add(progressNotesArea, 1, row, 3, 1);
        row++;
        
        // Row 7: Medications
        form.add(createLabel("Medications:"), 0, row);
        form.add(medicationsArea, 1, row, 3, 1);
        row++;
        
        // Row 8: Vital Signs
        form.add(createLabel("Vital Signs:"), 0, row);
        GridPane vitalSignsGrid = new GridPane();
        vitalSignsGrid.setHgap(10);
        vitalSignsGrid.setVgap(5);
        
        vitalSignsGrid.add(new Label("BP:"), 0, 0);
        TextField bpField = createTextField(80, "120/80");
        vitalSignsGrid.add(bpField, 1, 0);
        
        vitalSignsGrid.add(new Label("Pulse:"), 2, 0);
        TextField pulseField = createTextField(60, "72");
        vitalSignsGrid.add(pulseField, 3, 0);
        
        vitalSignsGrid.add(new Label("Temp:"), 0, 1);
        TextField tempField = createTextField(60, "98.6");
        vitalSignsGrid.add(tempField, 1, 1);
        
        vitalSignsGrid.add(new Label("Resp:"), 2, 1);
        TextField respField = createTextField(60, "16");
        vitalSignsGrid.add(respField, 3, 1);
        
        form.add(vitalSignsGrid, 1, row, 3, 1);
        row++;
        
        // Form validation message
        Label validationLabel = new Label();
        validationLabel.setStyle("-fx-text-fill: #e74c3c; -fx-font-size: 12px;");
        form.add(validationLabel, 1, row, 3, 1);
        
        // Button container
        HBox buttonContainer = new HBox(15);
        buttonContainer.setPadding(new Insets(20, 0, 0, 0));
        
        // Update button
        Button updateButton = new Button("🔄 Update Treatment");
        updateButton.setStyle("-fx-background-color: #e67e22; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 25; -fx-font-size: 14px; -fx-background-radius: 5;");
        updateButton.setOnAction(e -> {
            if (updateTreatment(doctorComboBox, bpField, pulseField, tempField, respField, validationLabel)) {
                showSuccessAlert();
            }
        });
        
        // Clear button
        Button clearButton = new Button("Clear Form");
        clearButton.setStyle("-fx-background-color: #95a5a6; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 25; -fx-font-size: 14px; -fx-background-radius: 5;");
        clearButton.setOnAction(e -> {
            clearForm(doctorComboBox, bpField, pulseField, tempField, respField);
            validationLabel.setText("");
        });
        
        // View History button
        Button historyButton = new Button("View Treatment History");
        historyButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 25; -fx-font-size: 14px; -fx-background-radius: 5;");
        historyButton.setOnAction(e -> viewTreatmentHistory());
        
        buttonContainer.getChildren().addAll(updateButton, clearButton, historyButton);
        
        // Add components to container
        formContainer.getChildren().addAll(title, subtitle, form, buttonContainer);
    }
    
    private TextField createTextField(double width, String prompt) {
        TextField field = new TextField();
        field.setPrefWidth(width);
        field.setPromptText(prompt);
        return field;
    }
    
    private Label createLabel(String text) {
        Label label = new Label(text);
        label.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        return label;
    }
    
    private String getPatientName(String patientId) {
        switch (patientId.toUpperCase()) {
            case "P001":
            case "P1002024":
                return "John Smith";
            case "P002":
            case "P2002024":
                return "Emma Johnson";
            case "P003":
            case "P3002024":
                return "Robert Brown";
            case "P004":
            case "P4002024":
                return "Sarah Miller";
            default:
                return "Patient not found";
        }
    }
    
    private boolean updateTreatment(ComboBox<String> doctorComboBox,
                                   TextField bpField, TextField pulseField,
                                   TextField tempField, TextField respField,
                                   Label validationLabel) {
        if (!validateForm()) {
            validationLabel.setText("Please fill in all required fields (*)");
            return false;
        }
        
        try {
            // Get form values
            String treatmentId = treatmentIdField.getText();
            String patientId = patientIdField.getText();
            String patientName = patientNameField.getText();
            String treatmentType = treatmentTypeComboBox.getValue();
            String status = statusComboBox.getValue();
            String startDate = startDatePicker.getValue().toString();
            String endDate = endDatePicker.getValue() != null ? endDatePicker.getValue().toString() : "Ongoing";
            String doctor = doctorComboBox.getValue();
            String treatmentDetails = treatmentDetailsArea.getText();
            String progressNotes = progressNotesArea.getText();
            String medications = medicationsArea.getText();
            String bp = bpField.getText();
            String pulse = pulseField.getText();
            String temp = tempField.getText();
            String resp = respField.getText();
            
            // Display update information
            System.out.println("\n=== TREATMENT UPDATED SUCCESSFULLY ===");
            System.out.println("Update Date: " + LocalDate.now());
            System.out.println("Update Time: " + LocalDate.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
            System.out.println("\n--- TREATMENT INFORMATION ---");
            System.out.println("Treatment ID: " + treatmentId);
            System.out.println("Patient ID: " + patientId);
            System.out.println("Patient Name: " + patientName);
            System.out.println("Treatment Type: " + treatmentType);
            System.out.println("Status: " + status);
            System.out.println("Start Date: " + startDate);
            System.out.println("End Date: " + endDate);
            System.out.println("Attending Doctor: " + doctor);
            
            System.out.println("\n--- VITAL SIGNS ---");
            System.out.println("Blood Pressure: " + bp);
            System.out.println("Pulse Rate: " + pulse + " bpm");
            System.out.println("Temperature: " + temp + "°F");
            System.out.println("Respiration: " + resp + " breaths/min");
            
            System.out.println("\n--- TREATMENT DETAILS ---");
            System.out.println(treatmentDetails);
            
            System.out.println("\n--- PROGRESS NOTES ---");
            System.out.println(progressNotes);
            
            if (!medications.isEmpty()) {
                System.out.println("\n--- MEDICATIONS ---");
                String[] meds = medications.split("\n");
                for (String med : meds) {
                    System.out.println("• " + med.trim());
                }
            }
            
            System.out.println("\nStatus: UPDATED ✓");
            System.out.println("Medical Record: UPDATED ✓");
            System.out.println("=== END ===");
            
            // Clear validation message
            validationLabel.setText("");
            clearForm(doctorComboBox, bpField, pulseField, tempField, respField);
            
            return true;
            
        } catch (Exception e) {
            validationLabel.setText("An error occurred: " + e.getMessage());
            return false;
        }
    }
    
    private boolean validateForm() {
        return !treatmentIdField.getText().isEmpty() &&
               !patientIdField.getText().isEmpty() &&
               treatmentTypeComboBox.getValue() != null &&
               statusComboBox.getValue() != null &&
               startDatePicker.getValue() != null &&
               !treatmentDetailsArea.getText().trim().isEmpty() &&
               !progressNotesArea.getText().trim().isEmpty();
    }
    
    private void clearForm(ComboBox<String> doctorComboBox,
                          TextField bpField, TextField pulseField,
                          TextField tempField, TextField respField) {
        treatmentIdField.clear();
        patientIdField.clear();
        patientNameField.clear();
        treatmentTypeComboBox.setValue(null);
        statusComboBox.setValue("Ongoing - Active Treatment");
        startDatePicker.setValue(LocalDate.now().minusDays(7));
        endDatePicker.setValue(null);
        doctorComboBox.setValue(null);
        treatmentDetailsArea.clear();
        progressNotesArea.clear();
        medicationsArea.clear();
        bpField.clear();
        pulseField.clear();
        tempField.clear();
        respField.clear();
    }
    
    private void showSuccessAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Treatment Updated");
        alert.setHeaderText("Treatment Updated Successfully");
        alert.setContentText("The treatment plan has been updated.\nMedical records have been updated accordingly.");
        alert.showAndWait();
    }
    
    private void viewTreatmentHistory() {
        System.out.println("\n=== TREATMENT HISTORY VIEW ===");
        System.out.println("Feature: View Treatment History");
        System.out.println("This feature would display all treatment records for the patient.");
        System.out.println("=== END ===");
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Treatment History");
        alert.setHeaderText("History View Feature");
        alert.setContentText("Treatment history viewing feature will be implemented in the next update.");
        alert.showAndWait();
    }
    
    public VBox getForm() {
        return formContainer;
    }
}