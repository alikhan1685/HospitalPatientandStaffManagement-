package application;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.util.List;
public class AddPatientForm {
    private VBox formContainer;
    private TextField nameField;
    private TextField ageField;
    private ComboBox<String> genderComboBox;
    private TextField addressField;
    private TextField phoneField;
    private TextField emailField;
    private ComboBox<String> assignDoctorComboBox;
    private TextArea medicalHistoryArea;
    private TextField emergencyContactField;
    private TextField bloodGroupField;
    private Button refreshDoctorsButton;
    public AddPatientForm() {
        initializeForm();
    }
    private void initializeForm() {
        // Create form container 
        formContainer = new VBox();
        formContainer.setSpacing(20);
        formContainer.setPadding(new Insets(20));
        formContainer.setStyle("-fx-background-color: #f8f9fa;");
        
        // Title
        Label title = new Label("Add New Patient");
        title.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        
        // Create form grid
        GridPane form = new GridPane();
        form.setVgap(15);
        form.setHgap(20);
        form.setPadding(new Insets(25));
        form.setStyle("-fx-background-color: white; -fx-border-color: #dee2e6; -fx-border-radius: 8; -fx-border-width: 1;");
        
        // Initialize form fields
        nameField = createTextField(300, "Enter full name");
        ageField = createTextField(100, "Enter age");
        addressField = createTextField(300, "Enter address");
        phoneField = createTextField(200, "Enter phone number");
        emailField = createTextField(300, "Enter email");
        emergencyContactField = createTextField(200, "Enter emergency contact");
        bloodGroupField = createTextField(100, "e.g., O+, A-, etc.");
        
        // Gender ComboBox
        genderComboBox = new ComboBox<>();
        genderComboBox.getItems().addAll("Male", "Female");
        genderComboBox.setPromptText("Select gender");
        genderComboBox.setPrefWidth(200);
        
        // Assign Doctor ComboBox with refresh button
        assignDoctorComboBox = new ComboBox<>();
        assignDoctorComboBox.setPrefWidth(250);
        
        // Refresh button for doctors combobox
        refreshDoctorsButton = new Button("🔄");
        refreshDoctorsButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-weight: bold;");
        refreshDoctorsButton.setTooltip(new Tooltip("Refresh available doctors list"));
        refreshDoctorsButton.setOnAction(e -> updateDoctorComboBox());
        
        // Create HBox for doctor selection with refresh button
        HBox doctorSelectionBox = new HBox(10);
        doctorSelectionBox.getChildren().addAll(assignDoctorComboBox, refreshDoctorsButton);
        
        // Medical History TextArea
        medicalHistoryArea = new TextArea();
        medicalHistoryArea.setPrefRowCount(4);
        medicalHistoryArea.setPrefWidth(300);
        medicalHistoryArea.setPromptText("Enter medical history (optional)");
        medicalHistoryArea.setWrapText(true);
        
        // Add labels and fields to grid
        int row = 0;
        form.add(createLabel("Full Name:"), 0, row);
        form.add(nameField, 1, row++);
        
        form.add(createLabel("Age:"), 0, row);
        form.add(ageField, 1, row++);
        
        form.add(createLabel("Gender:"), 0, row);
        form.add(genderComboBox, 1, row++);
        
        form.add(createLabel("Address:"), 0, row);
        form.add(addressField, 1, row++);
        
        form.add(createLabel("Phone:"), 0, row);
        form.add(phoneField, 1, row++);
        
        form.add(createLabel("Blood Group:"), 0, row);
        form.add(bloodGroupField, 1, row++);
        
        form.add(createLabel("Assign Doctor:"), 0, row);
        form.add(doctorSelectionBox, 1, row++);
        
        form.add(createLabel("Medical History:"), 0, row);
        form.add(medicalHistoryArea, 1, row++);
        
        // Form validation message
        Label validationLabel = new Label();
        validationLabel.setStyle("-fx-text-fill: #e74c3c; -fx-font-size: 12px;");
        form.add(validationLabel, 1, row);
        
        // Button container
        HBox buttonContainer = new HBox(15);
        buttonContainer.setPadding(new Insets(20, 0, 0, 0));
        
        // Save button
        Button saveButton = new Button("Save Patient");
        saveButton.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 25; -fx-font-size: 14px; -fx-background-radius: 5;");
        saveButton.setOnAction(e -> {
            if (savePatient(validationLabel)) {
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
        
        buttonContainer.getChildren().addAll(saveButton, clearButton);
        
        // Add components to container
        formContainer.getChildren().addAll(title, form, buttonContainer);
        
        // Initialize the doctor combobox with available doctors
        updateDoctorComboBox();
    }
    
    // Method to update the doctor combobox with available doctors from DoctorDatabase
    private void updateDoctorComboBox() {
        assignDoctorComboBox.getItems().clear();
        
        DoctorDatabase db = DoctorDatabase.getInstance();
        List<Staff> availableDoctors = db.getAvailableDoctors();
        
        if (availableDoctors.isEmpty()) {
            assignDoctorComboBox.getItems().add("No available doctors");
            assignDoctorComboBox.setValue("No available doctors");
            assignDoctorComboBox.setPromptText("No doctors available");
        } else {
            for (Staff doctor : availableDoctors) {
                // Format: "Dr. Name (Specialization) - Available Patients: X"
                String displayText = String.format("%s (%s) - Available", 
                    doctor.getName(),
                    doctor.getSpecialization()
                );
                assignDoctorComboBox.getItems().add(displayText);
            }
            assignDoctorComboBox.setPromptText("Select a doctor");
            
            // Optional: Sort the items alphabetically
            assignDoctorComboBox.getItems().sort(String::compareTo);
        }
    }
    
    // Helper method to extract doctor name from combobox selection
    private String extractDoctorName(String displayText) {
        if (displayText == null || displayText.equals("No available doctors")) {
            return null;
        }
        
        try {
            // Format: "Dr. Name (Specialization) - Available"
            String[] parts = displayText.split(" \\(");
            if (parts.length > 0) {
                return parts[0].trim();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return displayText;
    }
    
    // Helper method to extract doctor specialization from combobox selection
    private String extractDoctorSpecialization(String displayText) {
        if (displayText == null || displayText.equals("No available doctors")) {
            return null;
        }
        
        try {
            // Format: "Dr. Name (Specialization) - Available"
            String[] parts = displayText.split(" \\(");
            if (parts.length > 1) {
                // Remove the ")" and " - Available" part
                String specializationPart = parts[1];
                if (specializationPart.contains(") - Available")) {
                    return specializationPart.replace(") - Available", "").trim();
                }
                return specializationPart.replace(")", "").trim();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    // Method to find doctor ID by name and specialization
    private String findDoctorId(String doctorName, String specialization) {
        DoctorDatabase db = DoctorDatabase.getInstance();
        List<Staff> allDoctors = db.getAllDoctors();
        
        for (Staff doctor : allDoctors) {
            if (doctor.getName().equals(doctorName) && 
                doctor.getSpecialization().equals(specialization)) {
                return doctor.getStaffId();
            }
        }
        return null;
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
  
    private boolean savePatient(Label validationLabel) {
        if (!validateForm()) {
            validationLabel.setText("Please fill in all required fields (*)");
            return false;
        }
        
        try {
            // Get form values
            String name = nameField.getText();
            int age = Integer.parseInt(ageField.getText());
            String gender = genderComboBox.getValue();
            String address = addressField.getText();
            String phone = phoneField.getText();
            String email = emailField.getText();
            String emergencyContact = emergencyContactField.getText();
            String selectedDoctor = assignDoctorComboBox.getValue();
            String medicalHistory = medicalHistoryArea.getText();
            String bloodGroup = bloodGroupField.getText();
            
            // Validate data
            if (age < 0 || age > 150) {
                validationLabel.setText("Please enter a valid age (0-150)");
                return false;
            }
            
            if (!phone.matches("\\d{10,}")) {
                validationLabel.setText("Phone number must contain at least 10 digits");
                return false;
            }
            
            if (!email.isEmpty() && !email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
                validationLabel.setText("Please enter a valid email address (or leave empty)");
                return false;
            }
            
            // Check if a doctor is selected and available
            if (selectedDoctor == null || selectedDoctor.equals("No available doctors")) {
                validationLabel.setText("Please select an available doctor");
                return false;
            }
            
            // Extract doctor information
            String doctorName = extractDoctorName(selectedDoctor);
            String specialization = extractDoctorSpecialization(selectedDoctor);
            
            if (doctorName == null || specialization == null) {
                validationLabel.setText("Invalid doctor selection");
                return false;
            }
            
            // Find doctor ID
            String doctorId = findDoctorId(doctorName, specialization);
            
            if (doctorId == null) {
                validationLabel.setText("Selected doctor not found in database");
                return false;
            }
            
            // Create patient
            Patients patient = new Patients(name, age, gender);
            patient.setAddress(address);
            patient.setPhone(phone);
            patient.setEmail(email);
            patient.setEmergencyContact(emergencyContact);
            patient.setAssignedDoctorName(doctorName);
            patient.setBloodGroup(bloodGroup);
            
            // Add medical history
            if (medicalHistory != null && !medicalHistory.trim().isEmpty()) {
                patient.addMedicalHistory(medicalHistory);
            }
            
            // Add patient to database
            PatientDatabase patientDb = PatientDatabase.getInstance();
            patientDb.addPatient(patient);
            
            // Get patient ID after it's generated by the database
            String patientId = patient.getId();
            
            // Assign doctor to patient in DoctorDatabase
            DoctorDatabase doctorDb = DoctorDatabase.getInstance();
            boolean assignmentSuccess = doctorDb.assignDoctorToPatient(doctorId, patientId, name);
            
            if (assignmentSuccess) {
                // Update doctor status or perform any additional logic
                doctorDb.updateDoctorStatus(doctorId, "Active");
            }
            
            // Display saved information in console
            System.out.println("\n=== PATIENT SAVED SUCCESSFULLY ===");
            System.out.println("Patient ID: " + patientId);
            System.out.println("Name: " + name);
            System.out.println("Age: " + age);
            System.out.println("Gender: " + gender);
            System.out.println("Phone: " + phone);
            System.out.println("Address: " + address);
            System.out.println("Assigned Doctor: " + doctorName + " (" + specialization + ")");
            System.out.println("Doctor ID: " + doctorId);
            System.out.println("Blood Group: " + bloodGroup);
            
            if (medicalHistory != null && !medicalHistory.trim().isEmpty()) {
                System.out.println("Medical History: " + medicalHistory);
            }
            
            System.out.println("Doctor Assignment: " + (assignmentSuccess ? "SUCCESS" : "FAILED"));
            
            // Display database status
            System.out.println("Total patients in database: " + patientDb.getAllPatients().size());
            System.out.println("Total doctors in database: " + doctorDb.getDoctorCount());
            System.out.println("Available doctors: " + doctorDb.getAvailableDoctorCount());
            System.out.println("=== END ===");
            
            // Print all assignments
            doctorDb.printAllAssignments();
            
            // Clear validation message
            validationLabel.setText("");
            
            return true;
            
        } catch (NumberFormatException e) {
            validationLabel.setText("Age must be a valid number");
            return false;
        } catch (Exception e) {
            validationLabel.setText("An error occurred: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    private boolean validateForm() {
        // Check basic required fields
        boolean basicFieldsValid = !nameField.getText().isEmpty() &&
                                  !ageField.getText().isEmpty() &&
                                  genderComboBox.getValue() != null &&
                                  !addressField.getText().isEmpty() &&
                                  !phoneField.getText().isEmpty() &&
                                  assignDoctorComboBox.getValue() != null;
        
        if (!basicFieldsValid) {
            return false;
        }
        
        // Additional check for doctor selection
        String selectedDoctor = assignDoctorComboBox.getValue();
        return selectedDoctor != null && !selectedDoctor.equals("No available doctors");
    }
    
    private void clearForm() {
        nameField.clear();
        ageField.clear();
        genderComboBox.setValue(null);
        addressField.clear();
        phoneField.clear();
        emailField.clear();
        emergencyContactField.clear();
        bloodGroupField.clear();
        assignDoctorComboBox.setValue(null);
        medicalHistoryArea.clear();
        
        // Refresh the doctor list
        updateDoctorComboBox();
    }
    
    private void showSuccessAlert() {
        PatientDatabase patientDb = PatientDatabase.getInstance();
        DoctorDatabase doctorDb = DoctorDatabase.getInstance();
        
        // Get the last added patient
        List<Patients> allPatients = patientDb.getAllPatients();
        String patientId = "";
        if (!allPatients.isEmpty()) {
            patientId = allPatients.get(allPatients.size() - 1).getId();
        }
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Patient Saved Successfully");
        
        String alertMessage = String.format(
            "Patient information has been saved to the system.\n" +
            "Patient ID: %s\n\n" +
            "Database Status:\n" +
            "• Total Patients: %d\n" +
            "• Total Doctors: %d\n" +
            "• Available Doctors: %d",
            patientId,
            patientDb.getPatientCount(),
            doctorDb.getDoctorCount(),
            doctorDb.getAvailableDoctorCount()
        );
        
        alert.setContentText(alertMessage);
        alert.showAndWait();
    }
    
    public VBox getForm() {
        return formContainer;
    }
}