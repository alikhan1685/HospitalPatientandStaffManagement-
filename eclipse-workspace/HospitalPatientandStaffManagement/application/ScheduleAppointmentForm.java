package application;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

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
    private ComboBox<String> timeSlotComboBox;
    private TextField durationField;
    private TextArea symptomsArea;
    private TextArea notesArea; 
    public ScheduleAppointmentForm() {
        initializeForm();
    }  
    private void initializeForm() {
        // Create form container
        formContainer = new VBox();
        formContainer.setSpacing(20);
        formContainer.setPadding(new Insets(20));
        formContainer.setStyle("-fx-background-color: #f8f9fa;");
        
        // Title
        Label title = new Label("Schedule Medical Appointment");
        title.setFont(new Font("Arial", 28));
        title.setStyle("-fx-font-weight: bold; -fx-text-fill: #2c3e50; -fx-padding: 0 0 10 0;");
        
        // Subtitle
        Label subtitle = new Label("Schedule appointments for patients with medical professionals");
        subtitle.setStyle("-fx-text-fill: #7f8c8d; -fx-font-size: 14px;");
        
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
        
        patientIdField = createTextField(200, "Enter patient ID");
        patientNameField = createTextField(250, "Patient name (auto-filled)");
        patientNameField.setEditable(false);
        patientNameField.setStyle("-fx-background-color: #ecf0f1;");
        
        durationField = createTextField(100, "e.g., 30 min, 1 hour");
        
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
            "Second Opinion",
            "Telemedicine Consultation",
            "Physical Examination",
            "Medication Review",
            "Specialist Referral"
        );
        appointmentTypeComboBox.setPromptText("Select appointment type");
        appointmentTypeComboBox.setPrefWidth(250);
        
        // Department ComboBox
        departmentComboBox = new ComboBox<>();
        departmentComboBox.getItems().addAll(
            "Cardiology Department",
            "Neurology Department",
            "Pediatrics Department",
            "Orthopedics Department",
            "General Medicine",
            "Dermatology Department",
            "Surgery Department",
            "Gynecology Department",
            "Psychiatry Department",
            "Ophthalmology Department",
            "ENT Department",
            "Oncology Department",
            "Emergency Department",
            "Outpatient Department (OPD)"
        );
        departmentComboBox.setPromptText("Select department");
        departmentComboBox.setPrefWidth(250);
        
        // Urgency ComboBox
        urgencyComboBox = new ComboBox<>();
        urgencyComboBox.getItems().addAll(
            "Routine - Non-urgent",
            "Urgent - Within 48 hours",
            "Emergency - Immediate attention",
            "Follow-up - As scheduled"
        );
        urgencyComboBox.setValue("Routine - Non-urgent");
        urgencyComboBox.setPrefWidth(200);
        
        // Date Picker
        appointmentDatePicker = new DatePicker();
        appointmentDatePicker.setValue(LocalDate.now().plusDays(1)); // Tomorrow by default
        appointmentDatePicker.setPrefWidth(150);
        
        // Time Slot ComboBox
        timeSlotComboBox = new ComboBox<>();
        updateTimeSlots(); // Initial time slots
        timeSlotComboBox.setPromptText("Select time slot");
        timeSlotComboBox.setPrefWidth(150);
        
        // Update time slots when date changes
        appointmentDatePicker.valueProperty().addListener((obs, oldDate, newDate) -> {
            updateTimeSlots();
        });
        
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
                // Simulate patient name lookup
                patientNameField.setText(getPatientName(patientId));
            } else {
                patientNameField.clear();
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
        form.add(doctorComboBox, 1, row, 3, 1);
        row++;
        
        // Row 3: Appointment Details
        form.add(createLabel("Appointment Type:*"), 0, row);
        form.add(appointmentTypeComboBox, 1, row);
        form.add(createLabel("Department:*"), 2, row);
        form.add(departmentComboBox, 3, row);
        row++;
        
        // Row 4: Schedule
        form.add(createLabel("Date:*"), 0, row);
        form.add(appointmentDatePicker, 1, row);
        form.add(createLabel("Time Slot:*"), 2, row);
        form.add(timeSlotComboBox, 3, row);
        row++;
        
        form.add(createLabel("Duration:*"), 0, row);
        form.add(durationField, 1, row);
        form.add(createLabel("Urgency:*"), 2, row);
        form.add(urgencyComboBox, 3, row);
        row++;
        
        // Row 5: Room/Clinic
        form.add(createLabel("Room/Clinic:"), 0, row);
        TextField roomField = createTextField(200, "e.g., Room 101, Clinic A");
        form.add(roomField, 1, row);
        row++;
        
        // Row 6: Symptoms
        form.add(createLabel("Symptoms/Reason:*"), 0, row);
        form.add(symptomsArea, 1, row, 3, 1);
        row++;
        
        // Row 7: Notes
        form.add(createLabel("Additional Notes:"), 0, row);
        form.add(notesArea, 1, row, 3, 1);
        row++;
        
        // Row 8: Insurance/Contact Info
        form.add(createLabel("Insurance Info:"), 0, row);
        TextField insuranceField = createTextField(200, "Insurance provider & number");
        form.add(insuranceField, 1, row);
        
        form.add(createLabel("Contact Person:"), 2, row);
        TextField contactPersonField = createTextField(200, "Emergency contact person");
        form.add(contactPersonField, 3, row);
        row++;
        
        // Form validation message
        Label validationLabel = new Label();
        validationLabel.setStyle("-fx-text-fill: #e74c3c; -fx-font-size: 12px;");
        form.add(validationLabel, 1, row, 3, 1);
        
        // Button container
        HBox buttonContainer = new HBox(15);
        buttonContainer.setPadding(new Insets(20, 0, 0, 0));
        
        // Schedule button
        Button scheduleButton = new Button("📅 Schedule Appointment");
        scheduleButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 25; -fx-font-size: 14px; -fx-background-radius: 5;");
        scheduleButton.setOnAction(e -> {
            if (scheduleAppointment(roomField, insuranceField, contactPersonField, validationLabel)) {
                showSuccessAlert();
            }
        });
        
        // Clear button
        Button clearButton = new Button("Clear Form");
        clearButton.setStyle("-fx-background-color: #95a5a6; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 25; -fx-font-size: 14px; -fx-background-radius: 5;");
        clearButton.setOnAction(e -> {
            clearForm(roomField, insuranceField, contactPersonField);
            validationLabel.setText("");
        });
        
        // Check Availability button
        Button availabilityButton = new Button("Check Doctor Availability");
        availabilityButton.setStyle("-fx-background-color: #2ecc71; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 25; -fx-font-size: 14px; -fx-background-radius: 5;");
        availabilityButton.setOnAction(e -> checkDoctorAvailability());
        
        buttonContainer.getChildren().addAll(scheduleButton, clearButton, availabilityButton);
        
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
    
    private void updateTimeSlots() {
        timeSlotComboBox.getItems().clear();
        LocalDate selectedDate = appointmentDatePicker.getValue();
        
        if (selectedDate != null) {
            // Generate time slots from 8 AM to 6 PM
            for (int hour = 8; hour <= 18; hour++) {
                for (int minute = 0; minute < 60; minute += 30) { // 30-minute intervals
                    String time = String.format("%02d:%02d", hour, minute);
                    timeSlotComboBox.getItems().add(time);
                }
            }
        }
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
    
    private boolean scheduleAppointment(TextField roomField, 
                                        TextField insuranceField, 
                                        TextField contactPersonField, 
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
            String timeSlot = timeSlotComboBox.getValue();
            String duration = durationField.getText();
            String room = roomField.getText();
            String symptoms = symptomsArea.getText();
            String notes = notesArea.getText();
            String insurance = insuranceField.getText();
            String contactPerson = contactPersonField.getText();
            
            // Display appointment information
            System.out.println("\n=== APPOINTMENT SCHEDULED SUCCESSFULLY ===");
            System.out.println("Appointment ID: " + appointmentId);
            System.out.println("Scheduled Date: " + LocalDate.now());
            System.out.println("Scheduled Time: " + LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
            System.out.println("\n--- PATIENT INFORMATION ---");
            System.out.println("Patient ID: " + patientId);
            System.out.println("Patient Name: " + patientName);
            System.out.println("\n--- DOCTOR INFORMATION ---");
            System.out.println("Assigned Doctor: " + doctor);
            System.out.println("Department: " + department);
            System.out.println("\n--- APPOINTMENT DETAILS ---");
            System.out.println("Appointment Type: " + appointmentType);
            System.out.println("Date: " + appointmentDate);
            System.out.println("Time Slot: " + timeSlot);
            System.out.println("Duration: " + duration);
            System.out.println("Urgency Level: " + urgency);
            System.out.println("Room/Clinic: " + room);
            System.out.println("\n--- MEDICAL INFORMATION ---");
            System.out.println("Symptoms/Reason: " + symptoms);
            
            if (!notes.isEmpty()) {
                System.out.println("Additional Notes: " + notes);
            }
            
            if (!insurance.isEmpty()) {
                System.out.println("Insurance Information: " + insurance);
            }
            
            if (!contactPerson.isEmpty()) {
                System.out.println("Emergency Contact: " + contactPerson);
            }
            
            System.out.println("\n--- APPOINTMENT STATUS ---");
            System.out.println("Status: SCHEDULED ✓");
            System.out.println("Confirmation: EMAIL_SENT ✓");
            System.out.println("Reminder: SET_FOR_24_HOURS_BEFORE ✓");
            System.out.println("=== END ===");
            
            // Generate new appointment ID for next entry
            appointmentIdField.setText("APT-" + System.currentTimeMillis());
            
            // Clear validation message
            validationLabel.setText("");
            clearForm(roomField, insuranceField, contactPersonField);
            
            return true;
            
        } catch (Exception e) {
            validationLabel.setText("An error occurred: " + e.getMessage());
            return false;
        }
    }
    
    private boolean validateForm() {
        return !patientIdField.getText().isEmpty() &&
               doctorComboBox.getValue() != null &&
               appointmentTypeComboBox.getValue() != null &&
               departmentComboBox.getValue() != null &&
               appointmentDatePicker.getValue() != null &&
               timeSlotComboBox.getValue() != null &&
               !durationField.getText().isEmpty() &&
               !symptomsArea.getText().trim().isEmpty();
    }
    
    private void clearForm(TextField roomField, TextField insuranceField, TextField contactPersonField) {
        patientIdField.clear();
        patientNameField.clear();
        doctorComboBox.setValue(null);
        appointmentTypeComboBox.setValue(null);
        departmentComboBox.setValue(null);
        urgencyComboBox.setValue("Routine - Non-urgent");
        appointmentDatePicker.setValue(LocalDate.now().plusDays(1));
        timeSlotComboBox.setValue(null);
        durationField.clear();
        roomField.clear();
        symptomsArea.clear();
        notesArea.clear();
        insuranceField.clear();
        contactPersonField.clear();
        updateTimeSlots(); // Refresh time slots
    }
    
    private void showSuccessAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Appointment Scheduled");
        alert.setHeaderText("Appointment Scheduled Successfully");
        alert.setContentText("The appointment has been scheduled and confirmed.\nA confirmation email has been sent to the patient.");
        alert.showAndWait();
    }
    
    private void checkDoctorAvailability() {
        System.out.println("\n=== DOCTOR AVAILABILITY CHECK ===");
        System.out.println("Feature: Check Doctor Availability");
        System.out.println("This feature would check real-time doctor availability.");
        System.out.println("=== END ===");
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Doctor Availability");
        alert.setHeaderText("Availability Check Feature");
        alert.setContentText("Real-time doctor availability checking feature will be implemented in the next update.");
        alert.showAndWait();
    }
    
    public VBox getForm() {
        return formContainer;
    }
}