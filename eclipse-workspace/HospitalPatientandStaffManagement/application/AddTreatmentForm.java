package application;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import java.time.LocalDate;
import java.util.List;
public class AddTreatmentForm {
    private VBox formContainer;
    private TextField treatmentIdField;
    private TextField patientIdField;
    private TextField patientNameField;
    private ComboBox<String> treatmentTypeComboBox;
    private ComboBox<String> doctorComboBox;
    private DatePicker startDatePicker;
    private TextArea treatmentPlanArea;
    private TextArea medicationsArea;
    private PatientDatabase patientDatabase;
    private TreatmentDatabase treatmentDatabase;
    public AddTreatmentForm() {
        patientDatabase = PatientDatabase.getInstance();
        treatmentDatabase = TreatmentDatabase.getInstance(); 
        initializeForm();
    }
    private void initializeForm() {
        // Create form container
        formContainer = new VBox();
        formContainer.setSpacing(20);
        formContainer.setPadding(new Insets(20));
        formContainer.setStyle("-fx-background-color: #f8f9fa;");
        
        // Title
        Label title = new Label("Add New Treatment");
        title.setFont(new Font("Arial", 28));
        title.setStyle("-fx-font-weight: bold; -fx-text-fill: #2c3e50; -fx-padding: 0 0 10 0;");
        
        // Create form grid
        GridPane form = new GridPane();
        form.setVgap(15);
        form.setHgap(20);
        form.setPadding(new Insets(25));
        form.setStyle("-fx-background-color: white; -fx-border-color: #dee2e6; -fx-border-radius: 8; -fx-border-width: 1;");
        
        // Initialize form fields
        treatmentIdField = createTextField(200, "Auto-generated");
        treatmentIdField.setText("TREAT-" + System.currentTimeMillis());
        treatmentIdField.setEditable(false);
        treatmentIdField.setStyle("-fx-background-color: #ecf0f1; -fx-font-weight: bold;");
        
        patientIdField = createTextField(200, "Enter patient ID (e.g., P1002024)");
        patientNameField = createTextField(250, "Patient name (auto-filled)");
        patientNameField.setEditable(false);
        patientNameField.setStyle("-fx-background-color: #ecf0f1;");
        
        // Treatment Type ComboBox
        treatmentTypeComboBox = new ComboBox<>();
        treatmentTypeComboBox.getItems().addAll(
            "Physical Therapy",
            "Surgical Treatment", 
            "Chemotherapy",
            "Radiation Therapy",
            "Psychotherapy",       
            "Pain Management",
            "Dialysis",
            "Intravenous Therapy",
            "Occupational Therapy",
            "Nutrition Therapy"
        );
        treatmentTypeComboBox.setPromptText("Select treatment type");
        treatmentTypeComboBox.setPrefWidth(250);
        
        // Doctor ComboBox
        doctorComboBox = new ComboBox<>();
        doctorComboBox.getItems().addAll(
            "Dr. Ahmed Khan - Cardiology",
            "Dr. Farooq - Neurology", 
            "Dr. Aleem - Pediatrics",
            "Dr. Mahmood - Orthopedics",
            "Dr. Alizay - Dermatology"
        );
        doctorComboBox.setPromptText("Select attending doctor");
        doctorComboBox.setPrefWidth(250);
        
        // Date Picker
        startDatePicker = new DatePicker();
        startDatePicker.setValue(LocalDate.now());
        startDatePicker.setPrefWidth(150);
        
        // Text Areas
        treatmentPlanArea = new TextArea();
        treatmentPlanArea.setPrefRowCount(4);
        treatmentPlanArea.setPrefWidth(300);
        treatmentPlanArea.setPromptText("Enter treatment plan details...");
        treatmentPlanArea.setWrapText(true);
        
        medicationsArea = new TextArea();
        medicationsArea.setPrefRowCount(3);
        medicationsArea.setPrefWidth(300);
        medicationsArea.setPromptText("Enter medications (one per line)...");
        medicationsArea.setWrapText(true);
        
        // Add event listener for patient ID field
        patientIdField.setOnKeyReleased(e -> {
            String patientId = patientIdField.getText().trim();
            if (!patientId.isEmpty()) {
                String patientName = getPatientName(patientId);
                patientNameField.setText(patientName);
                
                // Change background color based on whether patient is found
                if (patientName.equals("Patient not found")) {
                    patientNameField.setStyle("-fx-background-color: #ffcccc;");
                    patientIdField.setStyle("-fx-border-color: #ff0000; -fx-border-width: 1px;");
                } else {
                    patientNameField.setStyle("-fx-background-color: #d4edda;");
                    patientIdField.setStyle("-fx-border-color: #28a745; -fx-border-width: 1px;");
                }
            } else {
                patientNameField.clear();
                patientNameField.setStyle("-fx-background-color: #ecf0f1;");
                patientIdField.setStyle("");
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
        
        // Row 0: Treatment ID
        form.add(createLabel("Treatment ID:"), 0, row);
        form.add(treatmentIdField, 1, row);
        row++;
        
        // Row 1: Patient Information
        form.add(createLabel("Patient ID:*"), 0, row);
        form.add(patientIdContainer, 1, row);
        form.add(createLabel("Patient Name:"), 2, row);
        form.add(patientNameField, 3, row);
        row++;
        
        // Row 2: Treatment Details
        form.add(createLabel("Treatment Type:*"), 0, row);
        form.add(treatmentTypeComboBox, 1, row);
        form.add(createLabel("Start Date:*"), 2, row);
        form.add(startDatePicker, 3, row);
        row++;
        
        // Row 3: Doctor
        form.add(createLabel("Attending Doctor:*"), 0, row);
        form.add(doctorComboBox, 1, row);
        row++;
        
        // Row 4: Treatment Plan
        form.add(createLabel("Treatment Plan:*"), 0, row);
        form.add(treatmentPlanArea, 1, row, 3, 1);
        row++;
        
        // Row 5: Medications
        form.add(createLabel("Medications:"), 0, row);
        form.add(medicationsArea, 1, row, 3, 1);
        row++;
        
        // Row 6: Duration and Frequency
        form.add(createLabel("Duration:"), 0, row);
        TextField durationField = createTextField(100, "e.g., 2 weeks, 1 month");
        form.add(durationField, 1, row);
        
        form.add(createLabel("Frequency:"), 2, row);
        TextField frequencyField = createTextField(100, "e.g., Daily, Weekly");
        form.add(frequencyField, 3, row);
        row++;
        
        // Form validation message
        Label validationLabel = new Label();
        validationLabel.setStyle("-fx-text-fill: #e74c3c; -fx-font-size: 12px;");
        form.add(validationLabel, 1, row, 3, 1);
        
        // Button container
        HBox buttonContainer = new HBox(15);
        buttonContainer.setPadding(new Insets(20, 0, 0, 0));
        
        // Save button
        Button saveButton = new Button("💊 Save Treatment");
        saveButton.setStyle("-fx-background-color: #e67e22; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 25; -fx-font-size: 14px; -fx-background-radius: 5;");
        saveButton.setOnAction(e -> {
            if (saveTreatment(durationField, frequencyField, validationLabel)) {
                showSuccessAlert();
            }
        });
        
        // Clear button
        Button clearButton = new Button("Clear Form");
        clearButton.setStyle("-fx-background-color: #95a5a6; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 25; -fx-font-size: 14px; -fx-background-radius: 5;");
        clearButton.setOnAction(e -> {
            clearForm(durationField, frequencyField);
            validationLabel.setText("");
            patientNameField.setStyle("-fx-background-color: #ecf0f1;");
            patientIdField.setStyle("");
        });
        
        buttonContainer.getChildren().addAll(saveButton, clearButton);
        
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
        // Search patient in the database
        List<Patients> patients = patientDatabase.searchById(patientId);
        
        if (!patients.isEmpty()) {
            // Patient found in database
            Patients patient = patients.get(0);
            System.out.println("Patient found: " + patient.getName() + " (ID: " + patient.getId() + ")");
            return patient.getName();
        } else {
            // Patient not found in database, check hardcoded values
            String hardcodedName = getHardcodedPatientName(patientId);
            if (!hardcodedName.equals("Patient not found")) {
                System.out.println("Patient found in hardcoded list: " + hardcodedName);
                return hardcodedName;
            }
            
            System.out.println("Patient not found with ID: " + patientId);
            return "Patient not found";
        }
    }
    
    private String getHardcodedPatientName(String patientId) {
        // Hardcoded mapping for demo/testing (optional)
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
            case "P005":
            case "P5002024":
                return "Michael Davis";
            default:
                return "Patient not found";
        }
    }
    
    private boolean saveTreatment(TextField durationField, TextField frequencyField, Label validationLabel) {
    	
        // First validate the patient exists
        String patientId = patientIdField.getText().trim();
        String patientName = patientNameField.getText();
        
        if (patientName.equals("Patient not found") || patientName.isEmpty()) {
            validationLabel.setText("Error: Patient not found. Please enter a valid Patient ID.");
            patientIdField.setStyle("-fx-border-color: #ff0000; -fx-border-width: 2px;");
            return false;
        }
        
        if (!validateForm()) {
            validationLabel.setText("Please fill in all required fields (*)");
            return false;
        }
        
        try {
            // Get form values
            String treatmentId = treatmentIdField.getText();
            String treatmentType = treatmentTypeComboBox.getValue();
            String doctor = doctorComboBox.getValue();
            String startDate = startDatePicker.getValue().toString();
            String treatmentPlan = treatmentPlanArea.getText();
            String medications = medicationsArea.getText();
            String duration = durationField.getText();
            String frequency = frequencyField.getText();
            
            // Display saved information
            System.out.println("\n" + "=".repeat(60));
            System.out.println("TREATMENT SAVED SUCCESSFULLY");
            System.out.println("=".repeat(60));
            System.out.println("Treatment ID: " + treatmentId);
            System.out.println("Patient ID: " + patientId);
            System.out.println("Patient Name: " + patientName);
            System.out.println("Treatment Type: " + treatmentType);
            System.out.println("Attending Doctor: " + doctor);
            System.out.println("Start Date: " + startDate);
            System.out.println("Duration: " + duration);
            System.out.println("Frequency: " + frequency);
            
            Treatments treatment = new Treatments(
            	    treatmentId,
            	    patientId,
            	    patientName,
            	    doctor,
            	    treatmentType,
            	    duration,
            	    "Active"
            	);

            	// ✅ SAVE INTO DATABASE
            	treatmentDatabase.addTreatment(treatment);

            
            System.out.println("\n--- TREATMENT PLAN ---");
            System.out.println(treatmentPlan);
            
            if (!medications.isEmpty()) {
                System.out.println("\n--- MEDICATIONS ---");
                String[] meds = medications.split("\n");
                for (String med : meds) {
                    if (!med.trim().isEmpty()) {
                        System.out.println("• " + med.trim());
                    }
                }
            }
            
            System.out.println("\nStatus: ACTIVE ✓");
            System.out.println("Medical Record: UPDATED ✓");
            System.out.println("=".repeat(60));
            
            // Generate new treatment ID for next entry
            treatmentIdField.setText("TREAT-" + System.currentTimeMillis());
            
            // Clear validation message
            validationLabel.setText("");
            clearForm(durationField, frequencyField);
            
            return true;
            
        } catch (Exception e) {
            validationLabel.setText("An error occurred: " + e.getMessage());
            return false;
        }
    }
    
    private boolean validateForm() {
        return !patientIdField.getText().isEmpty() &&
               treatmentTypeComboBox.getValue() != null &&
               doctorComboBox.getValue() != null &&
               startDatePicker.getValue() != null &&
               !treatmentPlanArea.getText().trim().isEmpty();
    }
    
    private void clearForm(TextField durationField, TextField frequencyField) {
        patientIdField.clear();
        patientNameField.clear();
        treatmentTypeComboBox.setValue(null);
        doctorComboBox.setValue(null);
        startDatePicker.setValue(LocalDate.now());
        treatmentPlanArea.clear();
        medicationsArea.clear();
        durationField.clear();
        frequencyField.clear();
        patientNameField.setStyle("-fx-background-color: #ecf0f1;");
        patientIdField.setStyle("");
    }
    
    private void showSuccessAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Treatment Saved");
        alert.setHeaderText("Treatment Plan Saved Successfully");
        
        String patientName = patientNameField.getText();
        String treatmentType = treatmentTypeComboBox.getValue();
        
        String content = "Treatment for patient '" + patientName + "' has been saved.\n\n" +
                       "Treatment Type: " + treatmentType + "\n" +
                       "Patient ID: " + patientIdField.getText() + "\n" +
                       "Treatment ID: " + treatmentIdField.getText();
        
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
    
    public VBox getForm() {
        return formContainer;
    }
}