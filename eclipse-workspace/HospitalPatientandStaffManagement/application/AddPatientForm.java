package application;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.util.Arrays;
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
        genderComboBox.getItems().addAll("Male", "Female", "Other", "Prefer not to say");
        genderComboBox.setPromptText("Select gender");
        genderComboBox.setPrefWidth(200);
        
        // Assign Doctor ComboBox
        assignDoctorComboBox = new ComboBox<>();
        initializeDoctorComboBox();
        assignDoctorComboBox.setPromptText("Select a doctor");
        assignDoctorComboBox.setPrefWidth(250);
        
        // Medical History TextArea
        medicalHistoryArea = new TextArea();
        medicalHistoryArea.setPrefRowCount(4);
        medicalHistoryArea.setPrefWidth(300);
        medicalHistoryArea.setPromptText("Enter medical history (optional)");
        medicalHistoryArea.setWrapText(true);
        
        // Add labels and fields to grid
        int row = 0;
        form.add(createLabel("Full Name:*"), 0, row);
        form.add(nameField, 1, row++);
        
        form.add(createLabel("Age:*"), 0, row);
        form.add(ageField, 1, row++);
        
        form.add(createLabel("Gender:*"), 0, row);
        form.add(genderComboBox, 1, row++);
        
        form.add(createLabel("Address:*"), 0, row);
        form.add(addressField, 1, row++);
        
        form.add(createLabel("Phone:*"), 0, row);
        form.add(phoneField, 1, row++);
        
        form.add(createLabel("Email:"), 0, row);
        form.add(emailField, 1, row++);
        
        form.add(createLabel("Emergency Contact:*"), 0, row);
        form.add(emergencyContactField, 1, row++);
        
        form.add(createLabel("Blood Group:"), 0, row);
        form.add(bloodGroupField, 1, row++);
        
        form.add(createLabel("Assign Doctor:*"), 0, row);
        form.add(assignDoctorComboBox, 1, row++);
        
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
    }
    
    private void initializeDoctorComboBox() {
        List<String> doctors = Arrays.asList(
            "Dr. Smith - Cardiology",
            "Dr. Johnson - Neurology", 
            "Dr. Williams - Pediatrics",
            "Dr. Davis - Orthopedics",
            "Dr. Miller - Dermatology"
        );
        assignDoctorComboBox.getItems().addAll(doctors);
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
            String doctor = assignDoctorComboBox.getValue();
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
            
            // Create Person object
            Person person = new Person(name, age, gender, address, phone, email, emergencyContact);
            
            // Create Patient object
            Patients patient = new Patients(
                person.getId(),
                person.getName(),
                person.getAge(),
                person.getGender(),
                person.getAddress(),
                person.getPhone(),
                person.getEmail(),
                person.getEmergencyContact(),
                bloodGroup,
                medicalHistory,
                doctor
            );
            
            // Display saved information
            System.out.println("\n=== PATIENT SAVED SUCCESSFULLY ===");
            System.out.println("Patient ID: " + patient.getId());
            System.out.println("Name: " + patient.getName());
            System.out.println("Age: " + patient.getAge());
            System.out.println("Gender: " + patient.getGender());
            System.out.println("Phone: " + patient.getPhone());
            System.out.println("Assigned Doctor: " + patient.getAssignedDoctorName());
            System.out.println("Blood Group: " + patient.getBloodGroup());
            System.out.println("=== END ===");
            
            // Clear validation message
            validationLabel.setText("");
            clearForm();
            
            return true;
            
        } catch (NumberFormatException e) {
            validationLabel.setText("Age must be a valid number");
            return false;
        }
    }
    
    private boolean validateForm() {
        return !nameField.getText().isEmpty() &&
               !ageField.getText().isEmpty() &&
               genderComboBox.getValue() != null &&
               !addressField.getText().isEmpty() &&
               !phoneField.getText().isEmpty() &&
               !emergencyContactField.getText().isEmpty() &&
               assignDoctorComboBox.getValue() != null;
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
    }
    
    private void showSuccessAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Patient Saved Successfully");
        alert.setContentText("Patient information has been saved to the system.");
        alert.showAndWait();
    }
    
    public VBox getForm() {
        return formContainer;
    }
}