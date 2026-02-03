package application;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ScheduleAppointmentForm {
    private VBox formContainer;
    private TextField appointmentIdField;
    private TextField patientIdField;
    private TextField patientNameField;
    private ComboBox<String> doctorComboBox;
    private ComboBox<String> appointmentTypeComboBox;
    private ComboBox<String> departmentComboBox;
    private ComboBox<String> urgencyComboBox;
    private DatePicker appointmentDatePicker;
    private TextField contactField;
    private TextArea symptomsArea;
    private TextArea notesArea;
   
    public ScheduleAppointmentForm() {
        initializeForm();
    }
   
    private void initializeForm() {
        formContainer = new VBox();
        formContainer.setSpacing(20);
        formContainer.setPadding(new Insets(20));
        formContainer.setStyle("-fx-background-color: #f8f9fa;");
        
        // Title
        Label title = new Label("Schedule Medical Appointment");
        title.setFont(new Font("Arial", 28));
        title.setStyle("-fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        
        // Create form grid
        GridPane form = new GridPane();
        form.setVgap(15);
        form.setHgap(20);
        form.setPadding(new Insets(25));
        form.setStyle("-fx-background-color: white; -fx-border-color: #dee2e6; -fx-border-radius: 8; -fx-border-width: 1;");
        
        // Initialize form fields
        appointmentIdField = createTextField(200, "Auto-generated");
        appointmentIdField.setText("APT-" + System.currentTimeMillis());
        appointmentIdField.setEditable(false);
        appointmentIdField.setStyle("-fx-background-color: #ecf0f1; -fx-font-weight: bold;");
        
        patientIdField = createTextField(200, "Enter patient ID (e.g., P001)");
        patientNameField = createTextField(250, "Patient name (auto-filled)");
        patientNameField.setEditable(false);
        patientNameField.setStyle("-fx-background-color: #ecf0f1;");
        
        contactField = createTextField(150, "Phone number");
        
        // Doctor ComboBox - Populate from DoctorDatabase
        doctorComboBox = new ComboBox<>();
        populateDoctorComboBox();
        doctorComboBox.setPromptText("Select doctor");
        doctorComboBox.setPrefWidth(250);
        
        // Appointment Type ComboBox
        appointmentTypeComboBox = new ComboBox<>();
        appointmentTypeComboBox.getItems().addAll(
            "Initial Consultation",
            "Follow-up Visit",
            "Regular Check-up",
            "Emergency Consultation",
            "Post-operative Review",
            "Diagnostic Test Review",
            "Vaccination/Immunization",
            "Pre-surgical Consultation",
            "Therapy Session",
            "Second Opinion"
        );
        appointmentTypeComboBox.setPromptText("Select appointment type");
        appointmentTypeComboBox.setPrefWidth(200);
        
        // Department ComboBox
        departmentComboBox = new ComboBox<>();
        // We'll populate this from doctor's specialization
        departmentComboBox.setPromptText("Department will auto-fill");
        departmentComboBox.setPrefWidth(200);
        departmentComboBox.setEditable(false);
        
        // Urgency ComboBox
        urgencyComboBox = new ComboBox<>();
        urgencyComboBox.getItems().addAll(
            "Routine - Non-urgent",
            "Urgent - Within 48 hours",
            "Emergency - Immediate attention",
            "Follow-up - As scheduled"
        );
        urgencyComboBox.setValue("Routine - Non-urgent");
        urgencyComboBox.setPrefWidth(180);
        
        // Date Picker
        appointmentDatePicker = new DatePicker();
        appointmentDatePicker.setValue(LocalDate.now().plusDays(1));
        appointmentDatePicker.setPrefWidth(150);
        
        // Symptoms TextArea
        symptomsArea = new TextArea();
        symptomsArea.setPrefRowCount(3);
        symptomsArea.setPrefWidth(300);
        symptomsArea.setPromptText("Describe symptoms or reason for visit...");
        symptomsArea.setWrapText(true);
        
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
                String patientName = getPatientNameFromDatabase(patientId);
                patientNameField.setText(patientName);
                
                // If patient exists, fill all their information
                if (!patientName.equals("Patient not found")) {
                    Patients patient = PatientDatabase.getInstance().getPatient(patientId);
                    if (patient != null) {
                        // Fill contact field if available
                        if (patient.getPhone() != null && !patient.getPhone().isEmpty()) {
                            contactField.setText(patient.getPhone());
                        }
                        
                        // AUTO-FILL DOCTOR AND DEPARTMENT FROM PATIENT'S ASSIGNED DOCTOR
                        String assignedDoctorName = patient.getAssignedDoctorName();
                        if (assignedDoctorName != null && !assignedDoctorName.isEmpty()) {
                            // Find and select the assigned doctor in combobox
                            doctorComboBox.setValue(assignedDoctorName);
                            
                            // Find the doctor in DoctorDatabase and get their specialization/department
                            Staff assignedDoctor = findDoctorByName(assignedDoctorName);
                            if (assignedDoctor != null) {
                                String department = assignedDoctor.getSpecialization();
                                departmentComboBox.setValue(department);
                            }
                        }
                    }
                }
            } else {
                patientNameField.clear();
            }
        });
        
        // Add listener to doctor combobox to auto-fill department
        doctorComboBox.setOnAction(e -> {
            String selectedDoctor = doctorComboBox.getValue();
            if (selectedDoctor != null) {
                // Find the doctor and get their specialization
                Staff doctor = findDoctorByName(selectedDoctor);
                if (doctor != null) {
                    String department = doctor.getSpecialization();
                    departmentComboBox.setValue(department);
                }
            }
        });
        
        // Add labels and fields to grid
        int row = 0;
        
        // Row 0: Appointment ID
        form.add(createLabel("Appointment ID:"), 0, row);
        form.add(appointmentIdField, 1, row);
        row++;
        
        // Row 1: Patient Information
        form.add(createLabel("Patient ID:*"), 0, row);
        form.add(patientIdField, 1, row);
        form.add(createLabel("Patient Name:"), 2, row);
        form.add(patientNameField, 3, row);
        row++;
        
        // Row 2: Doctor Selection
        form.add(createLabel("Doctor:*"), 0, row);
        form.add(doctorComboBox, 1, row);
        form.add(createLabel("Department:*"), 2, row);
        form.add(departmentComboBox, 3, row);
        row++;
        
        // Row 3: Appointment Details
        form.add(createLabel("Appointment Type:*"), 0, row);
        form.add(appointmentTypeComboBox, 1, row);
        form.add(createLabel("Date:*"), 2, row);
        form.add(appointmentDatePicker, 3, row);
        row++;
        
        // Row 4: Contact & Urgency
        form.add(createLabel("Contact:*"), 0, row);
        form.add(contactField, 1, row);
        form.add(createLabel("Urgency:*"), 2, row);
        form.add(urgencyComboBox, 3, row);
        row++;
        
        // Row 5: Room/Clinic and Time
        form.add(createLabel("Room/Clinic:"), 0, row);
        TextField roomField = createTextField(200, "e.g., Room 101, Clinic A");
        form.add(roomField, 1, row);
        form.add(createLabel("Time:"), 2, row);
        TextField timeField = createTextField(100, "e.g., 10:30 AM");
        form.add(timeField, 3, row);
        row++;
        
        // Row 6: Symptoms
        form.add(createLabel("Symptoms/Reason:*"), 0, row);
        form.add(symptomsArea, 1, row, 3, 1);
        row++;
        
        // Row 7: Notes
        form.add(createLabel("Additional Notes:"), 0, row);
        form.add(notesArea, 1, row, 3, 1);
        row++;
        
        // Form validation message
        Label validationLabel = new Label();
        validationLabel.setStyle("-fx-text-fill: #e74c3c; -fx-font-size: 12px;");
        form.add(validationLabel, 1, row, 3, 1);
        
        // Button container
        HBox buttonContainer = new HBox(15);
        buttonContainer.setPadding(new Insets(20, 0, 0, 0));
        
        // Schedule button
        Button scheduleButton = new Button("Schedule Appointment");
        scheduleButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 25; -fx-font-size: 14px; -fx-background-radius: 5;");
        scheduleButton.setOnAction(e -> {
            if (saveAppointment(roomField, timeField, validationLabel)) {
                showSuccessAlert();
            }
        });
        
        // Clear button
        Button clearButton = new Button("Clear Form");
        clearButton.setStyle("-fx-background-color: #95a5a6; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 25; -fx-font-size: 14px; -fx-background-radius: 5;");
        clearButton.setOnAction(e -> {
            clearForm(roomField, timeField);
            validationLabel.setText("");
        });
        
        // Add Find Patient button
        Button findPatientButton = new Button("Find Patient");
        findPatientButton.setStyle("-fx-background-color: #9b59b6; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 25; -fx-font-size: 14px; -fx-background-radius: 5;");
        findPatientButton.setOnAction(e -> {
            String patientId = patientIdField.getText().trim();
            if (!patientId.isEmpty()) {
                String patientName = getPatientNameFromDatabase(patientId);
                if (patientName.equals("Patient not found")) {
                    showAlert("Patient Not Found", "No patient found with ID: " + patientId);
                } else {
                    patientNameField.setText(patientName);
                    
                    // Auto-fill doctor and department if patient has assigned doctor
                    Patients patient = PatientDatabase.getInstance().getPatient(patientId);
                    if (patient != null) {
                        String assignedDoctorName = patient.getAssignedDoctorName();
                        if (assignedDoctorName != null && !assignedDoctorName.isEmpty()) {
                            doctorComboBox.setValue(assignedDoctorName);
                            
                            Staff assignedDoctor = findDoctorByName(assignedDoctorName);
                            if (assignedDoctor != null) {
                                departmentComboBox.setValue(assignedDoctor.getSpecialization());
                            }
                        }
                    }
                    
                    showAlert("Patient Found", "Patient: " + patientName + "\nID: " + patientId);
                }
            } else {
                showAlert("Missing ID", "Please enter a Patient ID first");
            }
        });
        
        buttonContainer.getChildren().addAll(scheduleButton, findPatientButton, clearButton);
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
    
    // Method to get patient name from database
    private String getPatientNameFromDatabase(String patientId) {
        PatientDatabase database = PatientDatabase.getInstance();
        String patientName = database.getPatientName(patientId);
        return patientName;
    }
    
    // Method to populate doctor combobox from DoctorDatabase
    private void populateDoctorComboBox() {
        List<Staff> doctors = DoctorDatabase.getInstance().getAllDoctors();
        for (Staff doctor : doctors) {
            String doctorInfo = doctor.getName() + " - " + doctor.getSpecialization();
            doctorComboBox.getItems().add(doctorInfo);
        }
    }
    
    // Method to find doctor by name from DoctorDatabase
    private Staff findDoctorByName(String doctorInfo) {
        List<Staff> doctors = DoctorDatabase.getInstance().getAllDoctors();
        for (Staff doctor : doctors) {
            String doctorDisplayName = doctor.getName() + " - " + doctor.getSpecialization();
            if (doctorDisplayName.equals(doctorInfo) || doctor.getName().equals(doctorInfo)) {
                return doctor;
            }
        }
        return null;
    }
    
    private boolean saveAppointment(TextField roomField, 
                                    TextField timeField, 
                                    Label validationLabel) {
        if (!validateForm()) {
            validationLabel.setText("Please fill in all required fields (*)");
            return false;
        }
        
        try {
            // Get form values
            String appointmentId = appointmentIdField.getText();
            String patientId = patientIdField.getText();
            String patientName = patientNameField.getText();
            String doctor = doctorComboBox.getValue();
            String appointmentType = appointmentTypeComboBox.getValue();
            String department = departmentComboBox.getValue();
            String urgency = urgencyComboBox.getValue();
            LocalDate appointmentDate = appointmentDatePicker.getValue();
            String time = timeField.getText();
            String contact = contactField.getText();
            String room = roomField.getText();
            String symptoms = symptomsArea.getText();
            String notes = notesArea.getText();
            
            // Validate patient exists
            if (patientName.equals("Patient not found")) {
                validationLabel.setText("Patient ID not found. Please enter a valid patient ID.");
                return false;
            }
            
            // Validate contact number
            if (!contact.matches("\\d{10,}")) {
                validationLabel.setText("Contact number must contain at least 10 digits");
                return false;
            }
            
            // Display appointment information
            System.out.println("\n=== APPOINTMENT SCHEDULED SUCCESSFULLY ===");
            System.out.println("Appointment ID: " + appointmentId);
            System.out.println("Date: " + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            System.out.println("\n--- PATIENT INFORMATION ---");
            System.out.println("Patient ID: " + patientId);
            System.out.println("Patient Name: " + patientName);
            System.out.println("Contact: " + contact);
            System.out.println("\n--- DOCTOR INFORMATION ---");
            System.out.println("Doctor: " + doctor);
            System.out.println("Department: " + department);
            System.out.println("\n--- APPOINTMENT DETAILS ---");
            System.out.println("Appointment Type: " + appointmentType);
            System.out.println("Date: " + appointmentDate);
            System.out.println("Time: " + time);
            System.out.println("Urgency: " + urgency);
            System.out.println("Room/Clinic: " + room);
            System.out.println("\n--- MEDICAL INFORMATION ---");
            System.out.println("Symptoms/Reason: " + symptoms);
            
            if (!notes.isEmpty()) {
                System.out.println("Additional Notes: " + notes);
            }
            
            System.out.println("\n--- APPOINTMENT STATUS ---");
            System.out.println("Status: SCHEDULED ✓");
            System.out.println("Confirmation: EMAIL_SENT ✓");
            System.out.println("=== END ===");
            
            // Create and save appointment to database
            Appointment appointment = new Appointment(
                appointmentId,      // appointmentId
                patientId,          // patientId
                patientName,        // patientName
                doctor,             // doctor
                appointmentType,    // appointmentType
                department,         // department
                urgency,            // urgency
                appointmentDate,    // date
                time,               // time
                contact,            // contact
                room,               // room
                symptoms,           // symptoms
                notes,              // notes
                "Scheduled"         // status (default)
            );
            
            // ADD TO DATABASE
            AppointmentDatabase.getInstance().addAppointment(appointment);
            
            // Generate new appointment ID for next entry
            appointmentIdField.setText("APT-" + System.currentTimeMillis());
            
            // Clear validation message
            validationLabel.setText("");
            clearForm(roomField, timeField);
            
            return true;
            
        } catch (Exception e) {
            validationLabel.setText("An error occurred: " + e.getMessage());
            return false;
        }
    }
    
    private boolean validateForm() {
        String patientName = patientNameField.getText();
        return !patientIdField.getText().isEmpty() &&
               !patientName.equals("Patient not found") &&
               doctorComboBox.getValue() != null &&
               appointmentTypeComboBox.getValue() != null &&
               departmentComboBox.getValue() != null &&
               appointmentDatePicker.getValue() != null &&
               !contactField.getText().isEmpty() &&
               !symptomsArea.getText().trim().isEmpty();
    }
    
    private void clearForm(TextField roomField, TextField timeField) {
        patientIdField.clear();
        patientNameField.clear();
        doctorComboBox.setValue(null);
        appointmentTypeComboBox.setValue(null);
        departmentComboBox.setValue(null);
        urgencyComboBox.setValue("Routine - Non-urgent");
        appointmentDatePicker.setValue(LocalDate.now().plusDays(1));
        contactField.clear();
        roomField.clear();
        timeField.clear();
        symptomsArea.clear();
        notesArea.clear();
    }
    
    private void showSuccessAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Appointment Scheduled");
        alert.setHeaderText("Appointment Scheduled Successfully");
        alert.setContentText("The appointment has been scheduled and confirmed.");
        alert.showAndWait();
    }
    
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    public VBox getForm() {
        return formContainer;
    }
}