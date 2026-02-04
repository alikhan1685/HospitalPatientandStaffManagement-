package application;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import java.time.LocalDate;
import java.util.List;

public class AssignDoctorForm {
    private VBox formContainer;
    private TextField patientIdField;
    private TextField patientNameField;
    private ComboBox<String> doctorComboBox;
    private DoctorDatabase doctorDatabase;
    private PatientDatabase patientDatabase;
    private Button refreshButton;
    
    public AssignDoctorForm() {
        doctorDatabase = DoctorDatabase.getInstance();
        patientDatabase = PatientDatabase.getInstance();
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
        
        // Doctor ComboBox with refresh button
        doctorComboBox = new ComboBox<>();
        doctorComboBox.setPrefWidth(300);
        
        refreshButton = new Button("🔄");
        refreshButton.setStyle("-fx-background-color: #2ecc71; -fx-text-fill: white; -fx-font-weight: bold;");
        refreshButton.setTooltip(new Tooltip("Refresh doctors list"));
        refreshButton.setOnAction(e -> updateDoctorComboBox());
        
        HBox doctorContainer = new HBox(10);
        doctorContainer.getChildren().addAll(doctorComboBox, refreshButton);
        
        // Add event listener for patient ID field
        patientIdField.setOnKeyReleased(e -> {
            String patientId = patientIdField.getText().trim();
            if (!patientId.isEmpty()) {
                String patientName = getPatientName(patientId);
                patientNameField.setText(patientName);
                
                // Change background color
                if (patientName.equals("Patient not found")) {
                    patientNameField.setStyle("-fx-background-color: #ffcccc;");
                } else {
                    patientNameField.setStyle("-fx-background-color: #d4edda;");
                }
            } else {
                patientNameField.clear();
                patientNameField.setStyle("-fx-background-color: #ecf0f1;");
            }
        });
        
        // Add search button next to patient ID
        Button searchPatientButton = new Button("🔍");
        searchPatientButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-weight: bold;");
        searchPatientButton.setOnAction(e -> {
            String patientId = patientIdField.getText().trim();
            if (!patientId.isEmpty()) {
                String patientName = getPatientName(patientId);
                patientNameField.setText(patientName);
                
                if (patientName.equals("Patient not found")) {
                    showAlert("Patient Not Found", "No patient found with ID: " + patientId);
                }
            }
        });
        
        HBox patientIdContainer = new HBox(5);
        patientIdContainer.getChildren().addAll(patientIdField, searchPatientButton);
        
        // Add labels and fields to grid
        int row = 0;
        
        // Patient Information
        form.add(createLabel("Patient ID:*"), 0, row);
        form.add(patientIdContainer, 1, row);
        row++;
        
        form.add(createLabel("Patient Name:"), 0, row);
        form.add(patientNameField, 1, row);
        row++;
        
        form.add(createLabel("Assign Doctor:*"), 0, row);
        form.add(doctorContainer, 1, row);
        row++;
        
        // Assignment Details
        form.add(createLabel("Assignment Date:"), 0, row);
        TextField dateField = createTextField(150, LocalDate.now().toString());
        dateField.setEditable(false);
        dateField.setStyle("-fx-background-color: #ecf0f1;");
        form.add(dateField, 1, row);
        row++;
        
        // Form validation message
        Label validationLabel = new Label();
        validationLabel.setStyle("-fx-text-fill: #e74c3c; -fx-font-size: 12px;");
        form.add(validationLabel, 1, row);
        row++;
        
        // Button container
        HBox buttonContainer = new HBox(15);
        buttonContainer.setPadding(new Insets(20, 0, 0, 0));
        
        // Assign button - MAIN BUTTON
        Button assignButton = new Button("Assign Doctor");
        assignButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 25; -fx-font-size: 14px; -fx-background-radius: 5;");
        assignButton.setOnAction(e -> {
            if (assignDoctorToPatient(validationLabel)) {
                showSuccessAlert();
                updateDoctorComboBox(); // Refresh the list after assignment
            }
        });
        
        // Clear button
        Button clearButton = new Button("Clear Form");
        clearButton.setStyle("-fx-background-color: #95a5a6; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 25; -fx-font-size: 14px; -fx-background-radius: 5;");
        clearButton.setOnAction(e -> {
            clearForm();
            validationLabel.setText("");
        });
        
        // View assignments button
        Button viewAssignmentsButton = new Button("View Assignments");
        viewAssignmentsButton.setStyle("-fx-background-color: #9b59b6; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 25; -fx-font-size: 14px; -fx-background-radius: 5;");
        viewAssignmentsButton.setOnAction(e -> showAllAssignments());
        
        buttonContainer.getChildren().addAll(assignButton, clearButton, viewAssignmentsButton);
        form.add(buttonContainer, 0, row, 2, 1);
        
        // Add components to container
        formContainer.getChildren().addAll(title, form);
        
        // Initialize the doctor combobox
        updateDoctorComboBox();
    }
    
    // Method to update doctor combobox with available doctors
    private void updateDoctorComboBox() {
        doctorComboBox.getItems().clear();
        
        List<Staff> availableDoctors = doctorDatabase.getAvailableDoctors();
        
        if (availableDoctors.isEmpty()) {
            doctorComboBox.getItems().add("No available doctors");
            doctorComboBox.setValue("No available doctors");
            doctorComboBox.setPromptText("No doctors available");
        } else {
            for (Staff doctor : availableDoctors) {
                // Format: "Dr. Name (Specialization) - ID: DOC-001"
                String displayText = String.format("%s (%s) - ID: %s", 
                    doctor.getName(),
                    doctor.getSpecialization(),
                    doctor.getStaffId()
                );
                doctorComboBox.getItems().add(displayText);
            }
            doctorComboBox.setPromptText("Select doctor to assign");
            
            // Sort alphabetically
            doctorComboBox.getItems().sort(String::compareTo);
        }
    }
    
    // Method to get patient name
    private String getPatientName(String patientId) {
        List<Patients> patients = patientDatabase.searchById(patientId);
        
        if (!patients.isEmpty()) {
            Patients patient = patients.get(0);
            System.out.println("Patient found: " + patient.getName() + " (ID: " + patient.getId() + ")");
            return patient.getName();
        } else {
            return "Patient not found";
        }
    }
    
    // Helper method to extract doctor ID from combobox selection
    private String extractDoctorId(String displayText) {
        if (displayText == null || displayText.equals("No available doctors")) {
            return null;
        }
        
        try {
            // Format: "Dr. Name (Specialization) - ID: DOC-001"
            if (displayText.contains("ID: ")) {
                String[] parts = displayText.split("ID: ");
                if (parts.length > 1) {
                    return parts[1].trim();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    // Helper method to extract doctor name from combobox selection
    private String extractDoctorName(String displayText) {
        if (displayText == null || displayText.equals("No available doctors")) {
            return null;
        }
        
        try {
            // Format: "Dr. Name (Specialization) - ID: DOC-001"
            String[] parts = displayText.split(" \\(");
            if (parts.length > 0) {
                return parts[0].trim();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return displayText;
    }
    
    // MAIN METHOD: Assign doctor to patient
    private boolean assignDoctorToPatient(Label validationLabel) {
        // Validate patient
        String patientId = patientIdField.getText().trim();
        String patientName = patientNameField.getText();
        
        if (patientName.equals("Patient not found") || patientName.isEmpty()) {
            validationLabel.setText("Error: Patient not found. Please enter a valid Patient ID.");
            patientIdField.setStyle("-fx-border-color: #ff0000; -fx-border-width: 2px;");
            return false;
        }
        
        // Validate doctor selection
        String selectedDoctor = doctorComboBox.getValue();
        if (selectedDoctor == null || selectedDoctor.equals("No available doctors")) {
            validationLabel.setText("Please select an available doctor.");
            doctorComboBox.setStyle("-fx-border-color: #ff0000; -fx-border-width: 2px;");
            return false;
        }
        
        try {
            // Extract doctor information
            String doctorId = extractDoctorId(selectedDoctor);
            String doctorName = extractDoctorName(selectedDoctor);
            
            if (doctorId == null || doctorName == null) {
                validationLabel.setText("Invalid doctor selection.");
                return false;
            }
            
            // Get the doctor object
            Staff doctor = doctorDatabase.getDoctorById(doctorId);
            if (doctor == null) {
                validationLabel.setText("Doctor not found in database.");
                return false;
            }
            
            // Assign doctor to patient
            boolean assignmentSuccess = doctorDatabase.assignDoctorToPatient(doctorId, patientId, patientName);
            
            if (assignmentSuccess) {
                // Update patient's assigned doctor in PatientDatabase
                List<Patients> patients = patientDatabase.searchById(patientId);
                if (!patients.isEmpty()) {
                    Patients patient = patients.get(0);
                    patient.setAssignedDoctorName(doctorName);
                }
                
                // Update doctor status to active
                doctorDatabase.updateDoctorStatus(doctorId, "Active");
                
                // Display success message
                System.out.println("\n" + "=".repeat(60));
                System.out.println("DOCTOR ASSIGNED SUCCESSFULLY");
                System.out.println("=".repeat(60));
                System.out.println("Patient ID: " + patientId);
                System.out.println("Patient Name: " + patientName);
                System.out.println("Doctor ID: " + doctorId);
                System.out.println("Doctor Name: " + doctorName);
                System.out.println("Specialization: " + doctor.getSpecialization());
                System.out.println("Department: " + doctor.getDepartment());
                System.out.println("Assignment Date: " + LocalDate.now());
                System.out.println("Status: Active");
                System.out.println("=".repeat(60));
                
                // Print database status
                doctorDatabase.printAllAssignments();
                
                return true;
            } else {
                validationLabel.setText("Failed to assign doctor. The doctor might not be available.");
                return false;
            }
            
        } catch (Exception e) {
            validationLabel.setText("An error occurred: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    private void showAllAssignments() {
        List<Assignment> allAssignments = doctorDatabase.getAllAssignments();
        
        if (allAssignments.isEmpty()) {
            showAlert("No Assignments", "There are no doctor-patient assignments yet.");
            return;
        }
        
        StringBuilder assignmentsInfo = new StringBuilder();
        assignmentsInfo.append("ALL DOCTOR-PATIENT ASSIGNMENTS\n");
        assignmentsInfo.append("=".repeat(80)).append("\n\n");
        
        for (Assignment assignment : allAssignments) {
            assignmentsInfo.append(String.format("Doctor: %s (ID: %s)\n", 
                assignment.getDoctorName(), assignment.getDoctorId()));
            assignmentsInfo.append(String.format("Patient: %s (ID: %s)\n", 
                assignment.getPatientName(), assignment.getPatientId()));
            assignmentsInfo.append(String.format("Date: %s | Status: %s\n", 
                assignment.getAssignmentDate(), assignment.getStatus()));
            assignmentsInfo.append("-".repeat(40)).append("\n");
        }
        
        assignmentsInfo.append("\n📊 Total Assignments: ").append(allAssignments.size());
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("All Assignments");
        alert.setHeaderText("Doctor-Patient Assignments");
        alert.setContentText(assignmentsInfo.toString());
        alert.showAndWait();
    }
    
    private void clearForm() {
        patientIdField.clear();
        patientNameField.clear();
        patientNameField.setStyle("-fx-background-color: #ecf0f1;");
        doctorComboBox.setValue(null);
        patientIdField.setStyle("");
        doctorComboBox.setStyle("");
    }
    
    private void showSuccessAlert() {
        int totalAssignments = doctorDatabase.getAllAssignments().size();
        int totalDoctors = doctorDatabase.getDoctorCount();
        int availableDoctors = doctorDatabase.getAvailableDoctorCount();
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("✅ Doctor Assigned Successfully");
        
        String content = String.format(
            "Doctor has been successfully assigned to the patient.\n\n" +
            "Database Status:\n" +
            "• Total Assignments: %d\n" +
            "• Total Doctors: %d\n" +
            "• Available Doctors: %d\n\n" +
            "You can view all assignments by clicking 'View Assignments'.",
            totalAssignments,
            totalDoctors,
            availableDoctors
        );
        
        alert.setContentText(content);
        alert.showAndWait();
    }
    
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
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