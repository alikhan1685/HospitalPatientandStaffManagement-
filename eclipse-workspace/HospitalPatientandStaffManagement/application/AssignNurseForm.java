package application;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class AssignNurseForm {
    
    private VBox formContainer;
    private TextField patientIdField;
    private TextField patientNameField;
    private ComboBox<String> nurseComboBox;
    private ComboBox<String> assignmentTypeComboBox;
    private ComboBox<String> shiftTypeComboBox;
    private ComboBox<String> wardComboBox;
    private ComboBox<String> priorityComboBox;
    private DatePicker startDatePicker;
    private DatePicker endDatePicker;
    private TextField durationField;
    private TextArea instructionsArea;
    private TextArea notesArea;
    private CheckBox medicationCheckBox;
    private CheckBox monitoringCheckBox;
    private CheckBox woundCareCheckBox;
    private CheckBox postOpCheckBox;
    private CheckBox emergencyCheckBox;
    
    public AssignNurseForm() {
        initializeForm();
    }
    
    private void initializeForm() {
        // Create form container
        formContainer = new VBox();
        formContainer.setSpacing(20);
        formContainer.setPadding(new Insets(20));
        formContainer.setStyle("-fx-background-color: #f8f9fa;");
        
        // Title
        Label title = new Label("Assign Nurse to Patient");
        title.setFont(new Font("Arial", 28));
        title.setStyle("-fx-font-weight: bold; -fx-text-fill: #2c3e50; -fx-padding: 0 0 10 0;");
        
        // Subtitle
        Label subtitle = new Label("Assign nursing care to patients based on their medical needs");
        subtitle.setStyle("-fx-text-fill: #7f8c8d; -fx-font-size: 14px;");
        
        // Create form grid
        GridPane form = new GridPane();
        form.setVgap(15);
        form.setHgap(20);
        form.setPadding(new Insets(25));
        form.setStyle("-fx-background-color: white; -fx-border-color: #dee2e6; -fx-border-radius: 8; -fx-border-width: 1;");
        
        // Initialize form fields
        patientIdField = createTextField(200, "Enter patient ID");
        patientNameField = createTextField(250, "Patient name (auto-filled)");
        patientNameField.setEditable(false);
        patientNameField.setStyle("-fx-background-color: #ecf0f1;");
        
        // Nurse ComboBox with specialty
        nurseComboBox = new ComboBox<>();
        nurseComboBox.getItems().addAll(
            "N001 - Sarah Johnson (ICU Specialist)",
            "N002 - Michael Brown (Emergency Care)",
            "N003 - Emily Davis (Pediatrics)",
            "N004 - Robert Wilson (Surgical Care)",
            "N005 - Lisa Miller (Maternity)",
            "N006 - David Taylor (Cardiac Care)",
            "N007 - Jennifer Lee (Oncology)",
            "N008 - James Anderson (Geriatrics)",
            "N009 - Maria Garcia (Trauma)",
            "N010 - Thomas Clark (Neonatal)"
        );
        nurseComboBox.setPromptText("Select nurse");
        nurseComboBox.setPrefWidth(300);
        
        // Assignment Type ComboBox
        assignmentTypeComboBox = new ComboBox<>();
        assignmentTypeComboBox.getItems().addAll(
            "Primary Care Nurse",
            "Medication Administration",
            "Vital Signs Monitoring",
            "Post-Operative Care",
            "Wound Care & Dressing",
            "Patient Education",
            "Emergency Response",
            "Shift Duty",
            "One-on-One Care",
            "Critical Care",
            "Palliative Care",
            "Rehabilitation Care"
        );
        assignmentTypeComboBox.setPromptText("Select assignment type");
        assignmentTypeComboBox.setPrefWidth(250);
        
        // Shift Type ComboBox
        shiftTypeComboBox = new ComboBox<>();
        shiftTypeComboBox.getItems().addAll(
            "Morning Shift (6:00 AM - 2:00 PM)",
            "Evening Shift (2:00 PM - 10:00 PM)",
            "Night Shift (10:00 PM - 6:00 AM)",
            "12-Hour Day Shift",
            "12-Hour Night Shift",
            "Rotating Shifts",
            "On-Call Duty",
            "24-Hour Continuous Care"
        );
        shiftTypeComboBox.setPromptText("Select shift type");
        shiftTypeComboBox.setPrefWidth(250);
        
        // Ward ComboBox
        wardComboBox = new ComboBox<>();
        wardComboBox.getItems().addAll(
            "ICU - Intensive Care Unit",
            "Emergency Department",
            "Pediatrics Ward",
            "Maternity Ward",
            "Surgical Ward",
            "Cardiology Ward",
            "Oncology Ward",
            "General Medicine Ward",
            "Orthopedics Ward",
            "Neurology Ward",
            "Isolation Ward",
            "Recovery Room"
        );
        wardComboBox.setPromptText("Select ward");
        wardComboBox.setPrefWidth(250);
        
        // Priority ComboBox
        priorityComboBox = new ComboBox<>();
        priorityComboBox.getItems().addAll(
            "Low - Routine Care",
            "Medium - Standard Care",
            "High - Close Monitoring",
            "Critical - Intensive Care",
            "Emergency - Immediate Attention"
        );
        priorityComboBox.setValue("Medium - Standard Care");
        priorityComboBox.setPrefWidth(200);
        
        // Date Pickers
        startDatePicker = new DatePicker();
        startDatePicker.setValue(LocalDate.now());
        startDatePicker.setPrefWidth(150);
        
        endDatePicker = new DatePicker();
        endDatePicker.setValue(LocalDate.now().plusDays(1));
        endDatePicker.setPrefWidth(150);
        
        durationField = createTextField(100, "e.g., 3 days");
        
        // Text Areas
        instructionsArea = new TextArea();
        instructionsArea.setPrefRowCount(3);
        instructionsArea.setPrefWidth(300);
        instructionsArea.setPromptText("Enter specific care instructions...");
        instructionsArea.setWrapText(true);
        
        notesArea = new TextArea();
        notesArea.setPrefRowCount(3);
        notesArea.setPrefWidth(300);
        notesArea.setPromptText("Additional notes (optional)...");
        notesArea.setWrapText(true);
        
        // Care Responsibilities Checkboxes
        medicationCheckBox = new CheckBox("Medication Administration");
        monitoringCheckBox = new CheckBox("Vital Signs Monitoring");
        woundCareCheckBox = new CheckBox("Wound Care & Dressing");
        postOpCheckBox = new CheckBox("Post-Operative Care");
        emergencyCheckBox = new CheckBox("Emergency Response");
        
        // Add event listener for patient ID field
        patientIdField.setOnKeyReleased(e -> {
            String patientId = patientIdField.getText().trim();
            if (!patientId.isEmpty()) {
                // Simulate patient name lookup
                patientNameField.setText(getPatientName(patientId));
            } else {
                patientNameField.clear();
            }
        });
        
        // Add labels and fields to grid
        int row = 0;
        
        // Row 0: Patient Information
        form.add(createLabel("Patient ID:*"), 0, row);
        form.add(patientIdField, 1, row);
        form.add(createLabel("Patient Name:"), 2, row);
        form.add(patientNameField, 3, row);
        row++;
        
        // Row 1: Nurse Selection
        form.add(createLabel("Nurse:*"), 0, row);
        form.add(nurseComboBox, 1, row, 3, 1);
        row++;
        
        // Row 2: Assignment Details
        form.add(createLabel("Assignment Type:*"), 0, row);
        form.add(assignmentTypeComboBox, 1, row);
        form.add(createLabel("Priority Level:*"), 2, row);
        form.add(priorityComboBox, 3, row);
        row++;
        
        // Row 3: Shift and Ward
        form.add(createLabel("Shift Type:*"), 0, row);
        form.add(shiftTypeComboBox, 1, row);
        form.add(createLabel("Ward:*"), 2, row);
        form.add(wardComboBox, 3, row);
        row++;
        
        // Row 4: Duration
        form.add(createLabel("Start Date:*"), 0, row);
        form.add(startDatePicker, 1, row);
        form.add(createLabel("End Date:"), 2, row);
        form.add(endDatePicker, 3, row);
        row++;
        
        form.add(createLabel("Duration:"), 0, row);
        form.add(durationField, 1, row);
        row++;
        
        // Row 5: Care Responsibilities
        Label responsibilitiesLabel = new Label("Care Responsibilities:");
        responsibilitiesLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px; -fx-text-fill: #2c3e50;");
        form.add(responsibilitiesLabel, 0, row);
        
        HBox checkboxes1 = new HBox(15);
        checkboxes1.getChildren().addAll(medicationCheckBox, monitoringCheckBox);
        form.add(checkboxes1, 1, row);
        
        HBox checkboxes2 = new HBox(15);
        checkboxes2.getChildren().addAll(woundCareCheckBox, postOpCheckBox, emergencyCheckBox);
        form.add(checkboxes2, 2, row, 2, 1);
        row++;
        
        // Row 6: Instructions
        form.add(createLabel("Care Instructions:*"), 0, row);
        form.add(instructionsArea, 1, row, 3, 1);
        row++;
        
        // Row 7: Notes
        form.add(createLabel("Additional Notes:"), 0, row);
        form.add(notesArea, 1, row, 3, 1);
        row++;
        
        // Form validation message
        Label validationLabel = new Label();
        validationLabel.setStyle("-fx-text-fill: #e74c3c; -fx-font-size: 12px;");
        validationLabel.setTextAlignment(TextAlignment.CENTER);
        form.add(validationLabel, 1, row, 3, 1);
        
        // Button container
        HBox buttonContainer = new HBox(15);
        buttonContainer.setPadding(new Insets(20, 0, 0, 0));
        
        // Assign button
        Button assignButton = new Button("✅ Assign Nurse");
        assignButton.setStyle("-fx-background-color: #9b59b6; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 25; -fx-font-size: 14px; -fx-background-radius: 5;");
        assignButton.setOnAction(e -> {
            if (assignNurse(validationLabel)) {
                showSuccessAlert();
            }
        });
        
        // Clear button
        Button clearButton = new Button("Clear Form");
        clearButton.setStyle("-fx-background-color: #95a5a6; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 25; -fx-font-size: 14px; -fx-background-radius: 5;");
        clearButton.setOnAction(e -> {
            clearForm();
            validationLabel.setText("");
        });
        
        // View Schedule button
        Button scheduleButton = new Button("View Nurse Schedule");
        scheduleButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 25; -fx-font-size: 14px; -fx-background-radius: 5;");
        scheduleButton.setOnAction(e -> viewNurseSchedule());
        
        buttonContainer.getChildren().addAll(assignButton, clearButton, scheduleButton);
        
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
        // Simulate patient lookup - in real app, this would query database
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
    
    private boolean assignNurse(Label validationLabel) {
        if (!validateForm()) {
            validationLabel.setText("Please fill in all required fields (*)");
            return false;
        }
        
        try {
            // Get form values
            String patientId = patientIdField.getText();
            String patientName = patientNameField.getText();
            String nurse = nurseComboBox.getValue();
            String assignmentType = assignmentTypeComboBox.getValue();
            String shiftType = shiftTypeComboBox.getValue();
            String ward = wardComboBox.getValue();
            String priority = priorityComboBox.getValue();
            LocalDate startDate = startDatePicker.getValue();
            LocalDate endDate = endDatePicker.getValue();
            String duration = durationField.getText();
            String instructions = instructionsArea.getText();
            String notes = notesArea.getText();
            
            // Get selected responsibilities
            StringBuilder responsibilities = new StringBuilder();
            if (medicationCheckBox.isSelected()) responsibilities.append("Medication Administration, ");
            if (monitoringCheckBox.isSelected()) responsibilities.append("Vital Signs Monitoring, ");
            if (woundCareCheckBox.isSelected()) responsibilities.append("Wound Care, ");
            if (postOpCheckBox.isSelected()) responsibilities.append("Post-Operative Care, ");
            if (emergencyCheckBox.isSelected()) responsibilities.append("Emergency Response, ");
            
            if (responsibilities.length() > 0) {
                responsibilities.setLength(responsibilities.length() - 2); // Remove last comma
            }
            
            // Display assignment information
            System.out.println("\n=== NURSE ASSIGNMENT CREATED ===");
            System.out.println("Assignment ID: ASG-" + System.currentTimeMillis());
            System.out.println("Timestamp: " + LocalDate.now() + " " + LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
            System.out.println("\n--- PATIENT INFORMATION ---");
            System.out.println("Patient ID: " + patientId);
            System.out.println("Patient Name: " + patientName);
            System.out.println("\n--- NURSE INFORMATION ---");
            System.out.println("Assigned Nurse: " + nurse);
            System.out.println("Assignment Type: " + assignmentType);
            System.out.println("Shift Type: " + shiftType);
            System.out.println("Ward: " + ward);
            System.out.println("Priority: " + priority);
            System.out.println("\n--- SCHEDULE ---");
            System.out.println("Start Date: " + startDate);
            System.out.println("End Date: " + (endDate != null ? endDate : "Not specified"));
            System.out.println("Duration: " + duration);
            System.out.println("\n--- CARE RESPONSIBILITIES ---");
            System.out.println("Responsibilities: " + (responsibilities.length() > 0 ? responsibilities.toString() : "None specified"));
            System.out.println("\n--- INSTRUCTIONS ---");
            System.out.println(instructions);
            
            if (!notes.isEmpty()) {
                System.out.println("\n--- ADDITIONAL NOTES ---");
                System.out.println(notes);
            }
            
            System.out.println("\nStatus: ASSIGNED ✓");
            System.out.println("=== END ===");
            
            // Clear validation message
            validationLabel.setText("");
            clearForm();
            
            return true;
            
        } catch (Exception e) {
            validationLabel.setText("An error occurred: " + e.getMessage());
            return false;
        }
    }
    
    private boolean validateForm() {
        return !patientIdField.getText().isEmpty() &&
               nurseComboBox.getValue() != null &&
               assignmentTypeComboBox.getValue() != null &&
               shiftTypeComboBox.getValue() != null &&
               wardComboBox.getValue() != null &&
               !instructionsArea.getText().trim().isEmpty();
    }
    
    private void clearForm() {
        patientIdField.clear();
        patientNameField.clear();
        nurseComboBox.setValue(null);
        assignmentTypeComboBox.setValue(null);
        shiftTypeComboBox.setValue(null);
        wardComboBox.setValue(null);
        priorityComboBox.setValue("Medium - Standard Care");
        startDatePicker.setValue(LocalDate.now());
        endDatePicker.setValue(LocalDate.now().plusDays(1));
        durationField.clear();
        instructionsArea.clear();
        notesArea.clear();
        medicationCheckBox.setSelected(false);
        monitoringCheckBox.setSelected(false);
        woundCareCheckBox.setSelected(false);
        postOpCheckBox.setSelected(false);
        emergencyCheckBox.setSelected(false);
    }
    
    private void showSuccessAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Assignment Successful");
        alert.setHeaderText("Nurse Assigned Successfully");
        alert.setContentText("The nurse has been assigned to the patient.\nAssignment details have been recorded in the system.");
        alert.showAndWait();
    }
    
    private void viewNurseSchedule() {
        System.out.println("\n=== NURSE SCHEDULE VIEW ===");
        System.out.println("Feature: View Nurse Schedule");
        System.out.println("This feature would display all nurse assignments and schedules.");
        System.out.println("=== END ===");
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Nurse Schedule");
        alert.setHeaderText("Schedule View Feature");
        alert.setContentText("Nurse schedule viewing feature will be implemented in the next update.");
        alert.showAndWait();
    }
    
    public VBox getForm() {
        return formContainer;
    }