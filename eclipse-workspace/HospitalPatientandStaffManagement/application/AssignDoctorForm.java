package application;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import java.time.LocalDate;

public class AssignDoctorForm {
    
    private VBox formContainer;
    private TextField patientIdField;
    private TextField patientNameField;
    private ComboBox<String> doctorComboBox;
    private ComboBox<String> assignmentTypeComboBox;
    private ComboBox<String> departmentComboBox;
    private DatePicker startDatePicker;
    private TextArea medicalHistoryArea;
    private TextArea notesArea;
    
    public AssignDoctorForm() {
        initializeForm();
    }
    
    private void initializeForm() {
        // Create form container
        formContainer = new VBox();
        formContainer.setSpacing(20);
        formContainer.setPadding(new Insets(20));
        formContainer.setStyle("-fx-background-color: #f8f9fa;");
        
        // Title
        Label title = new Label("Assign Doctor to Patient");
        title.setFont(new Font("Arial", 28));
        title.setStyle("-fx-font-weight: bold; -fx-text-fill: #2c3e50; -fx-padding: 0 0 10 0;");
        
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
        
        // Doctor ComboBox
        doctorComboBox = new ComboBox<>();
        doctorComboBox.getItems().addAll(
            "Dr. Smith - Cardiology (Available: Mon-Fri, 9 AM - 5 PM)",
            "Dr. Johnson - Neurology (Available: Mon-Wed, 10 AM - 4 PM)",
            "Dr. Williams - Pediatrics (Available: Tue-Thu, 8 AM - 6 PM)",
            "Dr. Brown - Orthopedics (Available: Mon-Fri, 9 AM - 3 PM)",
            "Dr. Davis - General Medicine (Available: Daily, 8 AM - 8 PM)",
            "Dr. Miller - Dermatology (Available: Wed-Fri, 11 AM - 7 PM)",
            "Dr. Wilson - Surgery (Available: Mon, Wed, Fri, 9 AM - 1 PM)",
            "Dr. Taylor - Gynecology (Available: Tue, Thu, 10 AM - 6 PM)",
            "Dr. Anderson - Psychiatry (Available: Mon-Fri, 9 AM - 5 PM)",
            "Dr. Thomas - Ophthalmology (Available: Wed-Sat, 9 AM - 4 PM)"
        );
        doctorComboBox.setPromptText("Select doctor");
        doctorComboBox.setPrefWidth(350);
        
        // Assignment Type ComboBox
        assignmentTypeComboBox = new ComboBox<>();
        assignmentTypeComboBox.getItems().addAll(
            "Primary Care Physician",
            "Consulting Specialist",
            "Emergency Care",
            "Post-Operative Care",
            "Chronic Disease Management",
            "Preventive Care",
            "Surgical Team",
            "Therapy Management"
        );
        assignmentTypeComboBox.setPromptText("Select assignment type");
        assignmentTypeComboBox.setPrefWidth(250);
        
        // Department ComboBox
        departmentComboBox = new ComboBox<>();
        departmentComboBox.getItems().addAll(
            "Cardiology Department",
            "Neurology Department",
            "Pediatrics Department",
            "Orthopedics Department",
            "General Medicine",
            "Surgery Department",
            "Emergency Department",
            "Intensive Care Unit (ICU)"
        );
        departmentComboBox.setPromptText("Select department");
        departmentComboBox.setPrefWidth(250);
        
        // Date Picker
        startDatePicker = new DatePicker();
        startDatePicker.setValue(LocalDate.now());
        startDatePicker.setPrefWidth(150);
        
        // Text Areas
        medicalHistoryArea = new TextArea();
        medicalHistoryArea.setPrefRowCount(3);
        medicalHistoryArea.setPrefWidth(300);
        medicalHistoryArea.setPromptText("Relevant medical history...");
        medicalHistoryArea.setWrapText(true);
        
        notesArea = new TextArea();
        notesArea.setPrefRowCount(3);
        notesArea.setPrefWidth(300);
        notesArea.setPromptText("Additional notes...");
        notesArea.setWrapText(true);
        
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
        
        // Row 0: Patient Information
        form.add(createLabel("Patient ID:*"), 0, row);
        form.add(patientIdField, 1, row);
        form.add(createLabel("Patient Name:"), 2, row);
        form.add(patientNameField, 3, row);
        row++;
        
        // Row 1: Doctor Selection
        form.add(createLabel("Doctor:*"), 0, row);
        form.add(doctorComboBox, 1, row, 3, 1);
        row++;
        
        // Row 2: Assignment Details
        form.add(createLabel("Assignment Type:*"), 0, row);
        form.add(assignmentTypeComboBox, 1, row);
        form.add(createLabel("Department:*"), 2, row);
        form.add(departmentComboBox, 3, row);
        row++;
        
        // Row 3: Start Date
        form.add(createLabel("Start Date:*"), 0, row);
        form.add(startDatePicker, 1, row);
        row++;
        
        // Row 4: Medical History
        form.add(createLabel("Medical History:"), 0, row);
        form.add(medicalHistoryArea, 1, row, 3, 1);
        row++;
        
        // Row 5: Notes
        form.add(createLabel("Notes:"), 0, row);
        form.add(notesArea, 1, row, 3, 1);
        row++;
        
        // Form validation message
        Label validationLabel = new Label();
        validationLabel.setStyle("-fx-text-fill: #e74c3c; -fx-font-size: 12px;");
        form.add(validationLabel, 1, row, 3, 1);
        
        // Button container
        HBox buttonContainer = new HBox(15);
        buttonContainer.setPadding(new Insets(20, 0, 0, 0));
        
        // Assign button
        Button assignButton = new Button("👨‍⚕️ Assign Doctor");
        assignButton.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 25; -fx-font-size: 14px; -fx-background-radius: 5;");
        assignButton.setOnAction(e -> {
            if (assignDoctor(validationLabel)) {
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
        
        buttonContainer.getChildren().addAll(assignButton, clearButton);
        
        // Add components to container
        formContainer.getChildren().addAll(title, form, buttonContainer);
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
    
    private boolean assignDoctor(Label validationLabel) {
        if (!validateForm()) {
            validationLabel.setText("Please fill in all required fields (*)");
            return false;
        }
        
        try {
            // Get form values
            String patientId = patientIdField.getText();
            String patientName = patientNameField.getText();
            String doctor = doctorComboBox.getValue();
            String assignmentType = assignmentTypeComboBox.getValue();
            String department = departmentComboBox.getValue();
            String startDate = startDatePicker.getValue().toString();
            String medicalHistory = medicalHistoryArea.getText();
            String notes = notesArea.getText();
            
            // Display assignment information
            System.out.println("\n=== DOCTOR ASSIGNED SUCCESSFULLY ===");
            System.out.println("Assignment Date: " + LocalDate.now());
            System.out.println("\n--- PATIENT INFORMATION ---");
            System.out.println("Patient ID: " + patientId);
            System.out.println("Patient Name: " + patientName);
            System.out.println("\n--- DOCTOR INFORMATION ---");
            System.out.println("Assigned Doctor: " + doctor);
            System.out.println("Assignment Type: " + assignmentType);
            System.out.println("Department: " + department);
            System.out.println("Start Date: " + startDate);
            
            if (!medicalHistory.isEmpty()) {
                System.out.println("\n--- MEDICAL HISTORY ---");
                System.out.println(medicalHistory);
            }
            
            if (!notes.isEmpty()) {
                System.out.println("\n--- NOTES ---");
                System.out.println(notes);
            }
            
            System.out.println("\nStatus: ASSIGNED ✓");
            System.out.println("Medical Record: UPDATED ✓");
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
               doctorComboBox.getValue() != null &&
               assignmentTypeComboBox.getValue() != null &&
               departmentComboBox.getValue() != null &&
               startDatePicker.getValue() != null;
    }
    
    private void clearForm() {
        patientIdField.clear();
        patientNameField.clear();
        doctorComboBox.setValue(null);
        assignmentTypeComboBox.setValue(null);
        departmentComboBox.setValue(null);
        startDatePicker.setValue(LocalDate.now());
        medicalHistoryArea.clear();
        notesArea.clear();
    }
    
    private void showSuccessAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Doctor Assigned");
        alert.setHeaderText("Doctor Assigned Successfully");
        alert.setContentText("The doctor has been assigned to the patient.\nMedical records have been updated.");
        alert.showAndWait();
    }
    
    public VBox getForm() {
        return formContainer;
    }
}