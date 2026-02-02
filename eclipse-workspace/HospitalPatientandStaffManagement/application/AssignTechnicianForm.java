package application;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import java.util.List;

public class AssignTechnicianForm {
    private VBox formContainer;
    private TextField patientIdField;
    private TextField patientNameField;
    private TextField doctorIdField;
    private TextField doctorNameField;
    private ComboBox<String> doctorComboBox;
    private TextArea assignmentDetailsArea;
    private DoctorDatabase doctorDatabase;
   
    public AssignTechnicianForm() {
        doctorDatabase = DoctorDatabase.getInstance();
        initializeForm();
    }
    
    private void initializeForm() {
        formContainer = new VBox();
        formContainer.setSpacing(20);
        formContainer.setPadding(new Insets(20));
        formContainer.setStyle("-fx-background-color: #f8f9fa;");
        
        // Title
        Label title = new Label("Assign Doctor to Patient");
        title.setFont(new Font("Arial", 28));
        title.setStyle("-fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        
        // Create form grid
        GridPane form = new GridPane();
        form.setVgap(15);
        form.setHgap(20);
        form.setPadding(new Insets(25));
        form.setStyle("-fx-background-color: white; -fx-border-color: #dee2e6; -fx-border-radius: 8; -fx-border-width: 1;");
        
        // Patient Information section
        Label patientSection = new Label("PATIENT INFORMATION");
        patientSection.setStyle("-fx-font-weight: bold; -fx-font-size: 16px; -fx-text-fill: #2c3e50; -fx-padding: 0 0 10 0;");
        form.add(patientSection, 0, 0, 4, 1);
        
        int row = 1;
        
        // Patient ID field
        form.add(createLabel("Patient ID:*"), 0, row);
        patientIdField = createTextField(150, "Enter patient ID");
        form.add(patientIdField, 1, row);
        
        Button searchPatientButton = new Button("🔍 Search Patient");
        searchPatientButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 5 10;");
        searchPatientButton.setOnAction(e -> searchPatient());
        
        HBox patientIdBox = new HBox(5);
        patientIdBox.getChildren().addAll(patientIdField, searchPatientButton);
        form.add(patientIdBox, 1, row, 3, 1);
        row++;
        
        form.add(createLabel("Patient Name:"), 0, row);
        patientNameField = createTextField(250, "Patient name (auto-filled)");
        patientNameField.setEditable(false);
        patientNameField.setStyle("-fx-background-color: #ecf0f1;");
        form.add(patientNameField, 1, row, 3, 1);
        row++;
        
        // Add some spacing
        form.add(new Label(""), 0, row);
        row++;
        
        // Doctor Information section
        Label doctorSection = new Label("DOCTOR INFORMATION");
        doctorSection.setStyle("-fx-font-weight: bold; -fx-font-size: 16px; -fx-text-fill: #2c3e50; -fx-padding: 10 0 10 0;");
        form.add(doctorSection, 0, row, 4, 1);
        row++;
        
        // Doctor ID and Name fields
        form.add(createLabel("Doctor ID:*"), 0, row);
        doctorIdField = createTextField(150, "Doctor ID (auto-filled)");
        doctorIdField.setEditable(false);
        doctorIdField.setStyle("-fx-background-color: #ecf0f1;");
        form.add(doctorIdField, 1, row);
        
        form.add(createLabel("Doctor Name:"), 2, row);
        doctorNameField = createTextField(250, "Doctor name (auto-filled)");
        doctorNameField.setEditable(false);
        doctorNameField.setStyle("-fx-background-color: #ecf0f1;");
        form.add(doctorNameField, 3, row);
        row++;
        
        // Doctor selection ComboBox
        form.add(createLabel("Select Doctor:*"), 0, row);
        doctorComboBox = new ComboBox<>();
        updateDoctorComboBox();
        doctorComboBox.setPromptText("Select a doctor");
        doctorComboBox.setPrefWidth(400);
        doctorComboBox.setOnAction(e -> {
            String selected = doctorComboBox.getValue();
            if (selected != null && !selected.isEmpty() && !selected.contains("No available")) {
                // Extract doctor ID from selection
                String[] parts = selected.split(" - ");
                if (parts.length >= 1) {
                    String doctorId = parts[0].trim();
                    doctorIdField.setText(doctorId);
                    
                    // Get doctor from database to fill name
                    Staff doctor = doctorDatabase.getDoctorById(doctorId);
                    if (doctor != null) {
                        doctorNameField.setText(doctor.getName());
                    }
                    
                    // Set background color to indicate selection
                    doctorIdField.setStyle("-fx-background-color: #d4edda;");
                    doctorNameField.setStyle("-fx-background-color: #d4edda;");
                }
            }
        });
        
        HBox doctorSelectionBox = new HBox(10);
        doctorSelectionBox.getChildren().addAll(doctorComboBox);
        form.add(doctorSelectionBox, 1, row, 3, 1);
        row++;
        
        // Show available doctors button
        Button showAvailableDoctorsButton = new Button("📋 View Available Doctors List");
        showAvailableDoctorsButton.setStyle("-fx-background-color: #2ecc71; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 8 15;");
        showAvailableDoctorsButton.setOnAction(e -> showAvailableDoctors());
        form.add(showAvailableDoctorsButton, 1, row, 3, 1);
        row++;
        
        // Add some spacing
        form.add(new Label(""), 0, row);
        row++;
        
        // Assignment Details section
        Label assignmentSection = new Label("ASSIGNMENT DETAILS");
        assignmentSection.setStyle("-fx-font-weight: bold; -fx-font-size: 16px; -fx-text-fill: #2c3e50; -fx-padding: 10 0 10 0;");
        form.add(assignmentSection, 0, row, 4, 1);
        row++;
        
        // Assignment details area
        form.add(createLabel("Assignment Details:*"), 0, row);
        assignmentDetailsArea = new TextArea();
        assignmentDetailsArea.setPrefRowCount(4);
        assignmentDetailsArea.setPrefWidth(400);
        assignmentDetailsArea.setPromptText("Enter assignment details (reason, duration, special instructions)...");
        assignmentDetailsArea.setWrapText(true);
        form.add(assignmentDetailsArea, 1, row, 3, 1);
        row++;
        
        // Duration and Priority
        form.add(createLabel("Duration:*"), 0, row);
        ComboBox<String> durationComboBox = new ComboBox<>();
        durationComboBox.getItems().addAll(
            "1 day",
            "3 days", 
            "1 week",
            "2 weeks",
            "1 month",
            "Until discharge",
            "Continuous care"
        );
        durationComboBox.setValue("1 week");
        durationComboBox.setPrefWidth(150);
        form.add(durationComboBox, 1, row);
        
        form.add(createLabel("Priority:*"), 2, row);
        ComboBox<String> priorityComboBox = new ComboBox<>();
        priorityComboBox.getItems().addAll("Normal", "High", "Emergency", "Critical");
        priorityComboBox.setValue("Normal");
        priorityComboBox.setPrefWidth(150);
        form.add(priorityComboBox, 3, row);
        row++;
        
        // Assignment date (auto-generated)
        form.add(createLabel("Assignment Date:"), 0, row);
        TextField dateField = createTextField(150, java.time.LocalDate.now().toString());
        dateField.setEditable(false);
        dateField.setStyle("-fx-background-color: #ecf0f1;");
        form.add(dateField, 1, row);
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
        assignButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 25; -fx-font-size: 14px; -fx-background-radius: 5;");
        assignButton.setOnAction(e -> {
            if (assignDoctorToPatient(durationComboBox, priorityComboBox, dateField, validationLabel)) {
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
        
        // View all assignments button
        Button viewAssignmentsButton = new Button("View All Assignments");
        viewAssignmentsButton.setStyle("-fx-background-color: #9b59b6; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 25; -fx-font-size: 14px; -fx-background-radius: 5;");
        viewAssignmentsButton.setOnAction(e -> showAllAssignments());
        
        buttonContainer.getChildren().addAll(assignButton, clearButton, viewAssignmentsButton);
        formContainer.getChildren().addAll(title, form, buttonContainer);
    }
    
    private void updateDoctorComboBox() {
        doctorComboBox.getItems().clear();
        List<Staff> availableDoctors = doctorDatabase.getAvailableDoctors();
        
        for (Staff doctor : availableDoctors) {
            String displayText = String.format("%s - %s | Specialization: %s | Experience: %d years | Status: %s",
                doctor.getStaffId(),
                doctor.getName(),
                doctor.getSpecialization(),
                doctor.getExperience(),
                doctor.getStatus()
            );
            doctorComboBox.getItems().add(displayText);
        }
        
        if (availableDoctors.isEmpty()) {
            doctorComboBox.getItems().add("No available doctors");
            doctorComboBox.setDisable(true);
        } else {
            doctorComboBox.setDisable(false);
        }
        
        // Clear ID and name fields
        doctorIdField.clear();
        doctorNameField.clear();
        doctorIdField.setStyle("-fx-background-color: #ecf0f1;");
        doctorNameField.setStyle("-fx-background-color: #ecf0f1;");
    }
    
    private void searchPatient() {
        String patientId = patientIdField.getText().trim();
        if (!patientId.isEmpty()) {
            // Search patient in database
            PatientDatabase patientDb = PatientDatabase.getInstance();
            List<Patients> patients = patientDb.searchById(patientId);
            
            if (!patients.isEmpty()) {
                Patients patient = patients.get(0);
                patientNameField.setText(patient.getName());
                patientNameField.setStyle("-fx-background-color: #d4edda;"); // Green background
                
                // Show patient info
                System.out.println("Found patient: " + patient.getName() + " (ID: " + patient.getId() + ")");
            } else {
                patientNameField.setText("Patient not found");
                patientNameField.setStyle("-fx-background-color: #ffcccc;"); // Red background
            }
        }
    }
    
    private void showAvailableDoctors() {
        List<Staff> availableDoctors = doctorDatabase.getAvailableDoctors();
        
        StringBuilder doctorsInfo = new StringBuilder();
        doctorsInfo.append("AVAILABLE DOCTORS FOR ASSIGNMENT\n");
        doctorsInfo.append("=".repeat(80)).append("\n\n");
        
        if (availableDoctors.isEmpty()) {
            doctorsInfo.append("No doctors currently available.\n");
        } else {
            doctorsInfo.append(String.format("%-8s | %-20s | %-20s | %-6s | %-15s\n",
                "ID", "Name", "Specialization", "Exp", "Status"));
            doctorsInfo.append("-".repeat(80)).append("\n");
            
            for (Staff doctor : availableDoctors) {
                doctorsInfo.append(String.format("%-8s | %-20s | %-20s | %4d yrs | %-15s\n",
                    doctor.getStaffId(),
                    doctor.getName(),
                    doctor.getSpecialization(),
                    doctor.getExperience(),
                    doctor.getStatus()
                ));
            }
        }
        
        doctorsInfo.append("\nTotal available doctors: ").append(availableDoctors.size());
        doctorsInfo.append("\nTotal doctors in system: ").append(doctorDatabase.getDoctorCount());
        
        // Show in a dialog with text area for better formatting
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Available Doctors");
        alert.setHeaderText("Doctors Available for Assignment");
        
        TextArea textArea = new TextArea(doctorsInfo.toString());
        textArea.setEditable(false);
        textArea.setWrapText(true);
        textArea.setPrefRowCount(15);
        textArea.setPrefColumnCount(80);
        textArea.setStyle("-fx-font-family: 'Monospaced'; -fx-font-size: 12px;");
        
        alert.getDialogPane().setContent(textArea);
        alert.getDialogPane().setPrefSize(800, 500);
        alert.showAndWait();
    }
    
    private boolean assignDoctorToPatient(ComboBox<String> durationComboBox, 
                                        ComboBox<String> priorityComboBox,
                                        TextField dateField,
                                        Label validationLabel) {
        // Validate patient
        String patientId = patientIdField.getText().trim();
        if (patientId.isEmpty() || patientNameField.getText().equals("Patient not found")) {
            validationLabel.setText("Please enter a valid patient ID");
            return false;
        }
        
        // Validate doctor selection
        String selectedDoctor = doctorComboBox.getValue();
        if (selectedDoctor == null || selectedDoctor.isEmpty() || 
            selectedDoctor.contains("No available doctors")) {
            validationLabel.setText("Please select an available doctor");
            return false;
        }
        
        // Validate doctor ID
        String doctorId = doctorIdField.getText().trim();
        if (doctorId.isEmpty()) {
            validationLabel.setText("Please select a doctor from the list");
            return false;
        }
        
        try {
            String assignmentDetails = assignmentDetailsArea.getText().trim();
            if (assignmentDetails.isEmpty()) {
                validationLabel.setText("Please enter assignment details");
                return false;
            }
            
            String duration = durationComboBox.getValue();
            String priority = priorityComboBox.getValue();
            String assignmentDate = dateField.getText();
            
            // Get the doctor object
            Staff doctor = doctorDatabase.getDoctorById(doctorId);
            
            // Assign doctor to patient
            if (doctorDatabase.assignDoctorToPatient(doctorId, patientId)) {
                // Log assignment
                System.out.println("\n" + "=".repeat(80));
                System.out.println("DOCTOR ASSIGNMENT RECORD");
                System.out.println("=".repeat(80));
                System.out.println("ASSIGNMENT DETAILS:");
                System.out.println("  Patient ID: " + patientId);
                System.out.println("  Patient Name: " + patientNameField.getText());
                System.out.println("  Doctor ID: " + doctorId);
                System.out.println("  Doctor Name: " + doctorNameField.getText());
                System.out.println("  Duration: " + duration);
                System.out.println("  Priority: " + priority);
                System.out.println("  Assignment Date: " + assignmentDate);
                System.out.println("  Assignment Details: " + assignmentDetails);
                
                // Get doctor details for logging
                if (doctor != null) {
                    System.out.println("\nDOCTOR DETAILS:");
                    System.out.println("  Specialization: " + doctor.getSpecialization());
                    System.out.println("  Experience: " + doctor.getExperience() + " years");
                    System.out.println("  Status: " + doctor.getStatus());
                    System.out.println("  Department: " + doctor.getDepartment());
                }
                
                System.out.println("\nAssignment successfully recorded in system.");
                System.out.println("=".repeat(80));
                
                // Update doctor combo box
                updateDoctorComboBox();
                
                return true;
            } else {
                validationLabel.setText("Failed to assign doctor.");
                return false;
            }
            
        } catch (Exception e) {
            validationLabel.setText("Error: " + e.getMessage());
            return false;
        }
    }
    
    private void showAllAssignments() {
        // This would need to be implemented based on how you track doctor-patient assignments
        List<Staff> allDoctors = doctorDatabase.getAllDoctors();
        
        StringBuilder assignmentsInfo = new StringBuilder();
        assignmentsInfo.append("CURRENT DOCTOR ASSIGNMENTS\n");
        assignmentsInfo.append("=".repeat(100)).append("\n\n");
        
        int totalDoctors = 0;
        
        for (Staff doctor : allDoctors) {
            totalDoctors++;
            assignmentsInfo.append("Doctor: ").append(doctor.getName())
                          .append(" (ID: ").append(doctor.getStaffId()).append(")\n");
            assignmentsInfo.append("Specialization: ").append(doctor.getSpecialization())
                          .append(" | Experience: ").append(doctor.getExperience()).append(" years\n");
            assignmentsInfo.append("Status: ").append(doctor.getStatus())
                          .append(" | Department: ").append(doctor.getDepartment()).append("\n");
            assignmentsInfo.append("-".repeat(100)).append("\n");
        }
        
        if (totalDoctors == 0) {
            assignmentsInfo.append("No doctors found in database.\n");
        } else {
            assignmentsInfo.append("\nSUMMARY:\n");
            assignmentsInfo.append("  Total doctors in system: ").append(totalDoctors).append("\n");
            assignmentsInfo.append("  Available doctors: ").append(doctorDatabase.getAvailableDoctorCount()).append("\n");
        }
        
        // Show in a dialog
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Current Assignments");
        alert.setHeaderText("Doctor Information");
        
        TextArea textArea = new TextArea(assignmentsInfo.toString());
        textArea.setEditable(false);
        textArea.setWrapText(true);
        textArea.setPrefRowCount(20);
        textArea.setPrefColumnCount(100);
        textArea.setStyle("-fx-font-family: 'Monospaced'; -fx-font-size: 12px;");
        
        alert.getDialogPane().setContent(textArea);
        alert.getDialogPane().setPrefSize(800, 500);
        alert.showAndWait();
    }
    
    private void clearForm() {
        patientIdField.clear();
        patientNameField.clear();
        patientNameField.setStyle("-fx-background-color: #ecf0f1;");
        doctorIdField.clear();
        doctorNameField.clear();
        doctorIdField.setStyle("-fx-background-color: #ecf0f1;");
        doctorNameField.setStyle("-fx-background-color: #ecf0f1;");
        assignmentDetailsArea.clear();
        updateDoctorComboBox();
    }
    
    private void showSuccessAlert() {
        String doctorId = doctorIdField.getText();
        String doctorName = doctorNameField.getText();
        String patientId = patientIdField.getText();
        String patientName = patientNameField.getText();
        
        // Get doctor details
        Staff doctor = doctorDatabase.getDoctorById(doctorId);
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Assignment Successful");
        alert.setHeaderText("✅ Doctor Assigned to Patient");
        
        String content = "Doctor has been successfully assigned to the patient.\n\n" +
                        "PATIENT INFORMATION:\n" +
                        "  ID: " + patientId + "\n" +
                        "  Name: " + patientName + "\n\n" +
                        "DOCTOR INFORMATION:\n" +
                        "  ID: " + doctorId + "\n" +
                        "  Name: " + doctorName + "\n";
        
        if (doctor != null) {
            content += "  Specialization: " + doctor.getSpecialization() + "\n" +
                      "  Experience: " + doctor.getExperience() + " years\n" +
                      "  Status: " + doctor.getStatus() + "\n";
        }
        
        content += "\nAssignment has been recorded in the system.";
        
        alert.setContentText(content);
        alert.showAndWait();
        clearForm();
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
    
    public VBox getForm() {
        return formContainer;
    }
}
