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

public class AssignTechnicianForm {
    
    private VBox formContainer;
    private TextField patientIdField;
    private TextField patientNameField;
    private ComboBox<String> technicianComboBox;
    private ComboBox<String> testTypeComboBox;
    private ComboBox<String> equipmentComboBox;
    private ComboBox<String> departmentComboBox;
    private ComboBox<String> urgencyComboBox;
    private DatePicker testDatePicker;
    private TextField locationField;
    private TextArea instructionsArea;
    private TextArea notesArea;
    
    public AssignTechnicianForm() {
        initializeForm();
    }
    
    private void initializeForm() {
        // Create form container
        formContainer = new VBox();
        formContainer.setSpacing(20);
        formContainer.setPadding(new Insets(20));
        formContainer.setStyle("-fx-background-color: #f8f9fa;");
        
        // Title
        Label title = new Label("Assign Technician to Patient");
        title.setFont(new Font("Arial", 28));
        title.setStyle("-fx-font-weight: bold; -fx-text-fill: #2c3e50; -fx-padding: 0 0 10 0;");
        
        // Subtitle
        Label subtitle = new Label("Assign diagnostic tests and procedures to technicians");
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
        
        locationField = createTextField(250, "e.g., Lab Room 3, Radiology Dept");
        
        // Technician ComboBox
        technicianComboBox = new ComboBox<>();
        technicianComboBox.getItems().addAll(
            "TECH-001 - John Smith (Lab Technician)",
            "TECH-002 - Sarah Johnson (Radiology)",
            "TECH-003 - Michael Brown (Cardiac)",
            "TECH-004 - Emily Davis (Surgical)",
            "TECH-005 - Robert Wilson (Respiratory)",
            "TECH-006 - Lisa Miller (Emergency)",
            "TECH-007 - David Taylor (Phlebotomy)",
            "TECH-008 - Jennifer Lee (Medical Lab)",
            "TECH-009 - James Anderson (X-Ray)",
            "TECH-010 - Maria Garcia (MRI)"
        );
        technicianComboBox.setPromptText("Select technician");
        technicianComboBox.setPrefWidth(300);
        
        // Test Type ComboBox
        testTypeComboBox = new ComboBox<>();
        testTypeComboBox.getItems().addAll(
            "Blood Test - Complete Blood Count (CBC)",
            "Blood Test - Lipid Profile",
            "Blood Test - Glucose Test",
            "Blood Test - Liver Function Test",
            "Blood Test - Kidney Function Test",
            "Blood Test - Thyroid Function Test",
            "Urine Analysis",
            "X-Ray - Chest",
            "X-Ray - Bone/Joint",
            "X-Ray - Abdomen",
            "MRI Scan",
            "CT Scan",
            "Ultrasound",
            "ECG (Electrocardiogram)",
            "EEG (Electroencephalogram)",
            "Endoscopy",
            "Colonoscopy",
            "Biopsy",
            "Culture and Sensitivity",
            "Pregnancy Test",
            "COVID-19 Test"
        );
        testTypeComboBox.setPromptText("Select test/procedure");
        testTypeComboBox.setPrefWidth(300);
        
        // Equipment ComboBox
        equipmentComboBox = new ComboBox<>();
        equipmentComboBox.getItems().addAll(
            "X-Ray Machine - Model XR-5000",
            "MRI Scanner - Model MRI-3T",
            "CT Scanner - Model CT-64",
            "Ultrasound Machine - Model US-2000",
            "ECG Machine - Model ECG-Pro",
            "EEG Machine - Model EEG-32",
            "Blood Analyzer - Model BA-4000",
            "Microscope - Digital Lab",
            "Endoscope - Flexible HD",
            "Colonoscope - HD Video",
            "Centrifuge - High Speed",
            "Incubator - Laboratory",
            "Autoclave - Sterilization",
            "Phlebotomy Kit - Standard",
            "Vital Signs Monitor"
        );
        equipmentComboBox.setPromptText("Select equipment");
        equipmentComboBox.setPrefWidth(250);
        
        // Department ComboBox
        departmentComboBox = new ComboBox<>();
        departmentComboBox.getItems().addAll(
            "Laboratory",
            "Radiology",
            "Cardiology Lab",
            "Pathology",
            "Emergency Lab",
            "Surgical Suite",
            "ICU Lab",
            "Outpatient Lab",
            "Research Lab",
            "Phlebotomy Room"
        );
        departmentComboBox.setPromptText("Select department");
        departmentComboBox.setPrefWidth(200);
        
        // Urgency ComboBox
        urgencyComboBox = new ComboBox<>();
        urgencyComboBox.getItems().addAll(
            "Routine - Within 24 hours",
            "Urgent - Within 4 hours",
            "Emergency - Immediate",
            "Stat - Within 1 hour",
            "Scheduled - Pre-booked"
        );
        urgencyComboBox.setValue("Routine - Within 24 hours");
        urgencyComboBox.setPrefWidth(200);
        
        // Test Date Picker
        testDatePicker = new DatePicker();
        testDatePicker.setValue(LocalDate.now());
        testDatePicker.setPrefWidth(150);
        
        // Instructions TextArea
        instructionsArea = new TextArea();
        instructionsArea.setPrefRowCount(3);
        instructionsArea.setPrefWidth(300);
        instructionsArea.setPromptText("Enter specific test instructions...");
        instructionsArea.setWrapText(true);
        
        // Notes TextArea
        notesArea = new TextArea();
        notesArea.setPrefRowCount(3);
        notesArea.setPrefWidth(300);
        notesArea.setPromptText("Additional notes (optional)...");
        notesArea.setWrapText(true);
        
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
        
        // Row 1: Technician Selection
        form.add(createLabel("Technician:*"), 0, row);
        form.add(technicianComboBox, 1, row, 3, 1);
        row++;
        
        // Row 2: Test Details
        form.add(createLabel("Test/Procedure:*"), 0, row);
        form.add(testTypeComboBox, 1, row);
        form.add(createLabel("Urgency:*"), 2, row);
        form.add(urgencyComboBox, 3, row);
        row++;
        
        // Row 3: Equipment and Department
        form.add(createLabel("Equipment:*"), 0, row);
        form.add(equipmentComboBox, 1, row);
        form.add(createLabel("Department:*"), 2, row);
        form.add(departmentComboBox, 3, row);
        row++;
        
        // Row 4: Schedule and Location
        form.add(createLabel("Test Date:*"), 0, row);
        form.add(testDatePicker, 1, row);
        form.add(createLabel("Location:*"), 2, row);
        form.add(locationField, 3, row);
        row++;
        
        // Row 5: Instructions
        form.add(createLabel("Test Instructions:*"), 0, row);
        form.add(instructionsArea, 1, row, 3, 1);
        row++;
        
        // Row 6: Notes
        form.add(createLabel("Additional Notes:"), 0, row);
        form.add(notesArea, 1, row, 3, 1);
        row++;
        
        // Row 7: Additional Test Preparation
        Label preparationLabel = new Label("Test Preparation Requirements:");
        preparationLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px; -fx-text-fill: #2c3e50;");
        form.add(preparationLabel, 0, row);
        
        CheckBox fastingCheckBox = new CheckBox("Fasting Required");
        CheckBox waterCheckBox = new CheckBox("Water Allowed");
        CheckBox medicationCheckBox = new CheckBox("Hold Medication");
        CheckBox consentCheckBox = new CheckBox("Consent Form Needed");
        
        HBox checkboxes1 = new HBox(15);
        checkboxes1.getChildren().addAll(fastingCheckBox, waterCheckBox);
        form.add(checkboxes1, 1, row);
        
        HBox checkboxes2 = new HBox(15);
        checkboxes2.getChildren().addAll(medicationCheckBox, consentCheckBox);
        form.add(checkboxes2, 2, row, 2, 1);
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
        Button assignButton = new Button("🔧 Assign Technician");
        assignButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 25; -fx-font-size: 14px; -fx-background-radius: 5;");
        assignButton.setOnAction(e -> {
            if (assignTechnician(validationLabel, fastingCheckBox, waterCheckBox, medicationCheckBox, consentCheckBox)) {
                showSuccessAlert();
            }
        });
        
        // Clear button
        Button clearButton = new Button("Clear Form");
        clearButton.setStyle("-fx-background-color: #95a5a6; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 25; -fx-font-size: 14px; -fx-background-radius: 5;");
        clearButton.setOnAction(e -> {
            clearForm(fastingCheckBox, waterCheckBox, medicationCheckBox, consentCheckBox);
            validationLabel.setText("");
        });
        
        // View Schedule button
        Button scheduleButton = new Button("View Technician Schedule");
        scheduleButton.setStyle("-fx-background-color: #2ecc71; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 25; -fx-font-size: 14px; -fx-background-radius: 5;");
        scheduleButton.setOnAction(e -> viewTechnicianSchedule());
        
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
    
    private boolean assignTechnician(Label validationLabel, 
                                     CheckBox fastingCheckBox, 
                                     CheckBox waterCheckBox, 
                                     CheckBox medicationCheckBox, 
                                     CheckBox consentCheckBox) {
        if (!validateForm()) {
            validationLabel.setText("Please fill in all required fields (*)");
            return false;
        }
        
        try {
            // Get form values
            String patientId = patientIdField.getText();
            String patientName = patientNameField.getText();
            String technician = technicianComboBox.getValue();
            String testType = testTypeComboBox.getValue();
            String equipment = equipmentComboBox.getValue();
            String department = departmentComboBox.getValue();
            String urgency = urgencyComboBox.getValue();
            LocalDate testDate = testDatePicker.getValue();
            String location = locationField.getText();
            String instructions = instructionsArea.getText();
            String notes = notesArea.getText();
            
            // Get test preparation requirements
            StringBuilder preparation = new StringBuilder();
            if (fastingCheckBox.isSelected()) preparation.append("Fasting Required, ");
            if (waterCheckBox.isSelected()) preparation.append("Water Allowed, ");
            if (medicationCheckBox.isSelected()) preparation.append("Hold Medication, ");
            if (consentCheckBox.isSelected()) preparation.append("Consent Form Needed, ");
            
            if (preparation.length() > 0) {
                preparation.setLength(preparation.length() - 2); // Remove last comma
            }
            
            // Display assignment information
            System.out.println("\n=== TECHNICIAN ASSIGNMENT CREATED ===");
            System.out.println("Assignment ID: TEC-ASG-" + System.currentTimeMillis());
            System.out.println("Timestamp: " + LocalDate.now() + " " + LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
            System.out.println("\n--- PATIENT INFORMATION ---");
            System.out.println("Patient ID: " + patientId);
            System.out.println("Patient Name: " + patientName);
            System.out.println("\n--- TECHNICIAN INFORMATION ---");
            System.out.println("Assigned Technician: " + technician);
            System.out.println("Test/Procedure: " + testType);
            System.out.println("Equipment: " + equipment);
            System.out.println("Department: " + department);
            System.out.println("Urgency Level: " + urgency);
            System.out.println("\n--- SCHEDULE & LOCATION ---");
            System.out.println("Test Date: " + testDate);
            System.out.println("Location: " + location);
            System.out.println("\n--- TEST PREPARATION ---");
            System.out.println("Preparation Requirements: " + (preparation.length() > 0 ? preparation.toString() : "None specified"));
            System.out.println("\n--- INSTRUCTIONS ---");
            System.out.println(instructions);
            
            if (!notes.isEmpty()) {
                System.out.println("\n--- ADDITIONAL NOTES ---");
                System.out.println(notes);
            }
            
            System.out.println("\nStatus: ASSIGNED ✓");
            System.out.println("Test Order: GENERATED ✓");
            System.out.println("=== END ===");
            
            // Clear validation message
            validationLabel.setText("");
            clearForm(fastingCheckBox, waterCheckBox, medicationCheckBox, consentCheckBox);
            
            return true;
            
        } catch (Exception e) {
            validationLabel.setText("An error occurred: " + e.getMessage());
            return false;
        }
    }
    
    private boolean validateForm() {
        return !patientIdField.getText().isEmpty() &&
               technicianComboBox.getValue() != null &&
               testTypeComboBox.getValue() != null &&
               equipmentComboBox.getValue() != null &&
               departmentComboBox.getValue() != null &&
               testDatePicker.getValue() != null &&
               !locationField.getText().trim().isEmpty() &&
               !instructionsArea.getText().trim().isEmpty();
    }
    
    private void clearForm(CheckBox fastingCheckBox, 
                           CheckBox waterCheckBox, 
                           CheckBox medicationCheckBox, 
                           CheckBox consentCheckBox) {
        patientIdField.clear();
        patientNameField.clear();
        technicianComboBox.setValue(null);
        testTypeComboBox.setValue(null);
        equipmentComboBox.setValue(null);
        departmentComboBox.setValue(null);
        urgencyComboBox.setValue("Routine - Within 24 hours");
        testDatePicker.setValue(LocalDate.now());
        locationField.clear();
        instructionsArea.clear();
        notesArea.clear();
        fastingCheckBox.setSelected(false);
        waterCheckBox.setSelected(false);
        medicationCheckBox.setSelected(false);
        consentCheckBox.setSelected(false);
    }
    
    private void showSuccessAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Assignment Successful");
        alert.setHeaderText("Technician Assigned Successfully");
        alert.setContentText("The technician has been assigned to perform the test/procedure.\nTest order has been generated and sent to the laboratory.");
        alert.showAndWait();
    }
    
    private void viewTechnicianSchedule() {
        System.out.println("\n=== TECHNICIAN SCHEDULE VIEW ===");
        System.out.println("Feature: View Technician Schedule");
        System.out.println("This feature would display all technician assignments and test schedules.");
        System.out.println("=== END ===");
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Technician Schedule");
        alert.setHeaderText("Schedule View Feature");
        alert.setContentText("Technician schedule viewing feature will be implemented in the next update.");
        alert.showAndWait();
    }
    
    public VBox getForm() {
        return formContainer;
    }
}