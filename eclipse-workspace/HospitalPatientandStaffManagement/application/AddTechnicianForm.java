package application;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class AddTechnicianForm {
    
    private VBox formContainer;
    private TextField idField;
    private TextField nameField;
    private TextField ageField;
    private ComboBox<String> genderComboBox;
    private ComboBox<String> roleComboBox;
    private ComboBox<String> departmentComboBox;
    private ComboBox<String> shiftComboBox;
    private ComboBox<String> statusComboBox;
    private TextField experienceField;
    private TextField qualificationField;
    private TextField specializationField;
    private TextField phoneField;
    private TextField emailField;
    private TextArea certificationArea;
    private TextArea notesArea;
    
    public AddTechnicianForm() {
        initializeForm();
    }
    
    private void initializeForm() {
        // Create form container
        formContainer = new VBox();
        formContainer.setSpacing(20);
        formContainer.setPadding(new Insets(20));
        formContainer.setStyle("-fx-background-color: #f8f9fa;");
        
        // Title
        Label title = new Label("Add New Technician");
        title.setFont(new Font("Arial", 28));
        title.setStyle("-fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        
        // Create form grid
        GridPane form = new GridPane();
        form.setVgap(15);
        form.setHgap(20);
        form.setPadding(new Insets(25));
        form.setStyle("-fx-background-color: white; -fx-border-color: #dee2e6; -fx-border-radius: 8; -fx-border-width: 1;");
        
        // Initialize form fields
        idField = createTextField(200, "e.g., TECH-001");
        nameField = createTextField(300, "Enter full name");
        ageField = createTextField(80, "Age");
        experienceField = createTextField(100, "Years");
        qualificationField = createTextField(250, "e.g., BSc, Diploma");
        specializationField = createTextField(250, "Special skills");
        phoneField = createTextField(200, "Phone number");
        emailField = createTextField(250, "Email address");
        
        // Gender ComboBox
        genderComboBox = new ComboBox<>();
        genderComboBox.getItems().addAll("Male", "Female", "Other");
        genderComboBox.setPromptText("Select gender");
        genderComboBox.setPrefWidth(150);
        
        // Role ComboBox
        roleComboBox = new ComboBox<>();
        roleComboBox.getItems().addAll(
            "Laboratory Technician",
            "Radiology Technician",
            "Surgical Technician",
            "Cardiac Technician",
            "Respiratory Technician",
            "Emergency Room Technician",
            "Phlebotomy Technician",
            "Medical Laboratory Technician",
            "X-Ray Technician",
            "MRI Technician",
            "Ultrasound Technician",
            "ECG Technician",
            "Dialysis Technician",
            "Anesthesia Technician"
        );
        roleComboBox.setPromptText("Select role");
        roleComboBox.setPrefWidth(250);
        
        // Department ComboBox
        departmentComboBox = new ComboBox<>();
        departmentComboBox.getItems().addAll(
            "Radiology",
            "Laboratory",
            "Surgery",
            "Cardiology",
            "Emergency Room",
            "Intensive Care Unit",
            "Operating Room",
            "Pathology",
            "Pharmacy",
            "Physical Therapy",
            "Diagnostic Imaging"
        );
        departmentComboBox.setPromptText("Select department");
        departmentComboBox.setPrefWidth(200);
        
        // Shift ComboBox
        shiftComboBox = new ComboBox<>();
        shiftComboBox.getItems().addAll(
            "Morning Shift (6 AM - 2 PM)",
            "Evening Shift (2 PM - 10 PM)",
            "Night Shift (10 PM - 6 AM)",
            "Rotating Shifts",
            "Flexible Hours",
            "On-Call"
        );
        shiftComboBox.setPromptText("Select shift");
        shiftComboBox.setPrefWidth(200);
        
        // Status ComboBox
        statusComboBox = new ComboBox<>();
        statusComboBox.getItems().addAll(
            "Active",
            "On Duty",
            "Off Duty",
            "On Leave",
            "On Vacation",
            "Available",
            "Unavailable",
            "Training"
        );
        statusComboBox.setValue("Active");
        statusComboBox.setPrefWidth(150);
        
        // Certification TextArea
        certificationArea = new TextArea();
        certificationArea.setPrefRowCount(3);
        certificationArea.setPrefWidth(300);
        certificationArea.setPromptText("Enter certifications (one per line)");
        certificationArea.setWrapText(true);
        
        // Notes TextArea
        notesArea = new TextArea();
        notesArea.setPrefRowCount(3);
        notesArea.setPrefWidth(300);
        notesArea.setPromptText("Additional notes (optional)");
        notesArea.setWrapText(true);
        
        // Add labels and fields to grid
        int row = 0;
        
        // Row 0: ID and Name
        form.add(createLabel("Technician ID:*"), 0, row);
        form.add(idField, 1, row);
        form.add(createLabel("Full Name:*"), 2, row);
        form.add(nameField, 3, row);
        row++;
        
        // Row 1: Age and Gender
        form.add(createLabel("Age:*"), 0, row);
        form.add(ageField, 1, row);
        form.add(createLabel("Gender:*"), 2, row);
        form.add(genderComboBox, 3, row);
        row++;
        
        // Row 2: Role and Department
        form.add(createLabel("Role:*"), 0, row);
        form.add(roleComboBox, 1, row);
        form.add(createLabel("Department:*"), 2, row);
        form.add(departmentComboBox, 3, row);
        row++;
        
        // Row 3: Experience and Qualification
        form.add(createLabel("Experience (Years):*"), 0, row);
        form.add(experienceField, 1, row);
        form.add(createLabel("Qualification:*"), 2, row);
        form.add(qualificationField, 3, row);
        row++;
        
        // Row 4: Specialization and Shift
        form.add(createLabel("Specialization:"), 0, row);
        form.add(specializationField, 1, row);
        form.add(createLabel("Shift:*"), 2, row);
        form.add(shiftComboBox, 3, row);
        row++;
        
        // Row 5: Contact Information
        form.add(createLabel("Phone:*"), 0, row);
        form.add(phoneField, 1, row);
        form.add(createLabel("Email:"), 2, row);
        form.add(emailField, 3, row);
        row++;
        
        // Row 6: Status
        form.add(createLabel("Status:*"), 0, row);
        form.add(statusComboBox, 1, row);
        row++;
        
        // Row 7: Certifications
        form.add(createLabel("Certifications:"), 0, row);
        form.add(certificationArea, 1, row, 3, 1);
        row++;
        
        // Row 8: Notes
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
        
        // Save button
        Button saveButton = new Button("Save Technician");
        saveButton.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 25; -fx-font-size: 14px; -fx-background-radius: 5;");
        saveButton.setOnAction(e -> {
            if (saveTechnician(validationLabel)) {
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
    
    private boolean saveTechnician(Label validationLabel) {
        if (!validateForm()) {
            validationLabel.setText("Please fill in all required fields (*)");
            return false;
        }
        
        try {
            // Get form values
            String id = idField.getText();
            String name = nameField.getText();
            int age = Integer.parseInt(ageField.getText());
            String gender = genderComboBox.getValue();
            String role = roleComboBox.getValue();
            String department = departmentComboBox.getValue();
            String shift = shiftComboBox.getValue();
            String status = statusComboBox.getValue();
            String experience = experienceField.getText();
            String qualification = qualificationField.getText();
            String specialization = specializationField.getText();
            String phone = phoneField.getText();
            String email = emailField.getText();
            String certifications = certificationArea.getText();
            String notes = notesArea.getText();
            
            // Validate data
            if (age < 18 || age > 70) {
                validationLabel.setText("Age must be between 18 and 70");
                return false;
            }
            
            if (!phone.matches("\\d{10,}")) {
                validationLabel.setText("Phone number must contain at least 10 digits");
                return false;
            }
            
            if (email != null && !email.isEmpty() && !email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
                validationLabel.setText("Please enter a valid email address");
                return false;
            }
            
            // Display saved information
            System.out.println("\n=== TECHNICIAN SAVED SUCCESSFULLY ===");
            System.out.println("Technician ID: " + id);
            System.out.println("Name: " + name);
            System.out.println("Age: " + age);
            System.out.println("Gender: " + gender);
            System.out.println("Role: " + role);
            System.out.println("Department: " + department);
            System.out.println("Shift: " + shift);
            System.out.println("Status: " + status);
            System.out.println("Experience: " + experience + " years");
            System.out.println("Qualification: " + qualification);
            System.out.println("Specialization: " + specialization);
            System.out.println("Phone: " + phone);
            System.out.println("Email: " + email);
            
            if (!certifications.isEmpty()) {
                System.out.println("Certifications:");
                String[] certs = certifications.split("\n");
                for (String cert : certs) {
                    System.out.println("  - " + cert.trim());
                }
            }
            
            if (!notes.isEmpty()) {
                System.out.println("Notes: " + notes);
            }
            
            System.out.println("=== END ===");
            
            // Clear validation message
            validationLabel.setText("");
            clearForm();
            
            return true;
            
        } catch (NumberFormatException e) {
            validationLabel.setText("Age and Experience must be valid numbers");
            return false;
        } catch (Exception e) {
            validationLabel.setText("An error occurred: " + e.getMessage());
            return false;
        }
    }
    
    private boolean validateForm() {
        return !idField.getText().isEmpty() &&
               !nameField.getText().isEmpty() &&
               !ageField.getText().isEmpty() &&
               genderComboBox.getValue() != null &&
               roleComboBox.getValue() != null &&
               departmentComboBox.getValue() != null &&
               shiftComboBox.getValue() != null &&
               statusComboBox.getValue() != null &&
               !experienceField.getText().isEmpty() &&
               !qualificationField.getText().isEmpty() &&
               !phoneField.getText().isEmpty();
    }
    
    private void clearForm() {
        idField.clear();
        nameField.clear();
        ageField.clear();
        genderComboBox.setValue(null);
        roleComboBox.setValue(null);
        departmentComboBox.setValue(null);
        shiftComboBox.setValue(null);
        statusComboBox.setValue("Active");
        experienceField.clear();
        qualificationField.clear();
        specializationField.clear();
        phoneField.clear();
        emailField.clear();
        certificationArea.clear();
        notesArea.clear();
    }
    
    private void showSuccessAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Technician Saved Successfully");
        alert.setContentText("Technician information has been saved to the system.");
        alert.showAndWait();
    }
    
    public VBox getForm() {
        return formContainer;
    }
}