package application;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import java.util.Arrays;
import java.util.List;

public class AddNurseForm {
    
    private VBox formContainer;
    private TextField idField;
    private TextField nameField;
    private TextField ageField;
    private ComboBox<String> genderComboBox;
    private ComboBox<String> qualificationComboBox;
    private ComboBox<String> specializationComboBox;
    private ComboBox<String> departmentComboBox;
    private ComboBox<String> shiftComboBox;
    private ComboBox<String> statusComboBox;
    private TextField experienceField;
    private TextField phoneField;
    private TextField emailField;
    private TextField licenseField;
    private TextArea certificationArea;
    private TextArea notesArea;
    
    public AddNurseForm() {
        initializeForm();
    }
    
    private void initializeForm() {
        // Create form container
        formContainer = new VBox();
        formContainer.setSpacing(20);
        formContainer.setPadding(new Insets(20));
        formContainer.setStyle("-fx-background-color: #f8f9fa;");
        
        // Title
        Label title = new Label("Add New Nurse");
        title.setFont(new Font("Arial", 28));
        title.setStyle("-fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        
        // Create form grid
        GridPane form = new GridPane();
        form.setVgap(15);
        form.setHgap(20);
        form.setPadding(new Insets(25));
        form.setStyle("-fx-background-color: white; -fx-border-color: #dee2e6; -fx-border-radius: 8; -fx-border-width: 1;");
        
        // Initialize form fields
        idField = createTextField(200, "e.g., NUR-001");
        nameField = createTextField(300, "Enter full name");
        ageField = createTextField(80, "Age");
        experienceField = createTextField(100, "Years");
        phoneField = createTextField(200, "Phone number");
        emailField = createTextField(250, "Email address");
        licenseField = createTextField(200, "License number");
        
        // Gender ComboBox
        genderComboBox = new ComboBox<>();
        genderComboBox.getItems().addAll("Male", "Female", "Other");
        genderComboBox.setPromptText("Select gender");
        genderComboBox.setPrefWidth(150);
        
        // Qualification ComboBox
        qualificationComboBox = new ComboBox<>();
        qualificationComboBox.getItems().addAll(
            "B.Sc Nursing",
            "M.Sc Nursing",
            "GNM (General Nursing & Midwifery)",
            "ANM (Auxiliary Nurse Midwife)",
            "Post Basic B.Sc Nursing",
            "Diploma in Nursing",
            "Nurse Practitioner",
            "Registered Nurse (RN)",
            "Licensed Practical Nurse (LPN)",
            "Certified Nursing Assistant (CNA)"
        );
        qualificationComboBox.setPromptText("Select qualification");
        qualificationComboBox.setPrefWidth(250);
        
        // Specialization ComboBox
        specializationComboBox = new ComboBox<>();
        specializationComboBox.getItems().addAll(
            "General Ward Nurse",
            "ICU Nurse",
            "Emergency Room Nurse",
            "Pediatric Nurse",
            "Maternity Nurse",
            "Operation Theater Nurse",
            "Psychiatric Nurse",
            "Cardiac Care Nurse",
            "Oncology Nurse",
            "Geriatric Nurse",
            "Neonatal Nurse",
            "Trauma Nurse",
            "Community Health Nurse",
            "Hospice Nurse",
            "Dialysis Nurse"
        );
        specializationComboBox.setPromptText("Select specialization");
        specializationComboBox.setPrefWidth(250);
        
        // Department ComboBox
        departmentComboBox = new ComboBox<>();
        departmentComboBox.getItems().addAll(
            "Intensive Care Unit (ICU)",
            "Emergency Department",
            "Pediatrics",
            "Maternity Ward",
            "Operating Theater",
            "Cardiology",
            "Oncology",
            "Orthopedics",
            "Neurology",
            "General Ward",
            "Geriatrics",
            "Psychiatry",
            "Outpatient Department"
        );
        departmentComboBox.setPromptText("Select department");
        departmentComboBox.setPrefWidth(250);
        
        // Shift ComboBox
        shiftComboBox = new ComboBox<>();
        shiftComboBox.getItems().addAll(
            "Morning Shift (6 AM - 2 PM)",
            "Evening Shift (2 PM - 10 PM)",
            "Night Shift (10 PM - 6 AM)",
            "Day Shift (8 AM - 4 PM)",
            "Rotating Shifts",
            "Flexible Hours",
            "On-Call",
            "24-Hour Duty"
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
            "Training",
            "Sick Leave"
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
        form.add(createLabel("Nurse ID:*"), 0, row);
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
        
        // Row 2: Qualification and Specialization
        form.add(createLabel("Qualification:*"), 0, row);
        form.add(qualificationComboBox, 1, row);
        form.add(createLabel("Specialization:*"), 2, row);
        form.add(specializationComboBox, 3, row);
        row++;
        
        // Row 3: Department and Shift
        form.add(createLabel("Department:*"), 0, row);
        form.add(departmentComboBox, 1, row);
        form.add(createLabel("Shift:*"), 2, row);
        form.add(shiftComboBox, 3, row);
        row++;
        
        // Row 4: Experience and License
        form.add(createLabel("Experience (Years):*"), 0, row);
        form.add(experienceField, 1, row);
        form.add(createLabel("License Number:*"), 2, row);
        form.add(licenseField, 3, row);
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
        
        // Row 7: Address field (takes 2 columns)
        form.add(createLabel("Address:"), 0, row);
        TextField addressField = new TextField();
        addressField.setPromptText("Residential address");
        addressField.setPrefWidth(300);
        form.add(addressField, 1, row, 3, 1);
        row++;
        
        // Row 8: Certifications
        form.add(createLabel("Certifications:"), 0, row);
        form.add(certificationArea, 1, row, 3, 1);
        row++;
        
        // Row 9: Notes
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
        Button saveButton = new Button("Save Nurse");
        saveButton.setStyle("-fx-background-color: #9b59b6; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 25; -fx-font-size: 14px; -fx-background-radius: 5;");
        saveButton.setOnAction(e -> {
            if (saveNurse(addressField, validationLabel)) {
                showSuccessAlert();
            }
        });
        
        // Clear button
        Button clearButton = new Button("Clear Form");
        clearButton.setStyle("-fx-background-color: #95a5a6; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 25; -fx-font-size: 14px; -fx-background-radius: 5;");
        clearButton.setOnAction(e -> {
            clearForm(addressField);
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
    
    private boolean saveNurse(TextField addressField, Label validationLabel) {
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
            String qualification = qualificationComboBox.getValue();
            String specialization = specializationComboBox.getValue();
            String department = departmentComboBox.getValue();
            String shift = shiftComboBox.getValue();
            String status = statusComboBox.getValue();
            String experience = experienceField.getText();
            String license = licenseField.getText();
            String phone = phoneField.getText();
            String email = emailField.getText();
            String address = addressField.getText();
            String certifications = certificationArea.getText();
            String notes = notesArea.getText();
            
            // Validate data
            if (age < 21 || age > 65) {
                validationLabel.setText("Age must be between 21 and 65");
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
            
            // Create Person object
            Person person = new Person(name, age, gender, address, phone, email, phone); // Using phone as emergency contact
            
            // Display saved information
            System.out.println("\n=== NURSE SAVED SUCCESSFULLY ===");
            System.out.println("Nurse ID: " + id);
            System.out.println("Person ID: " + person.getId());
            System.out.println("Name: " + person.getName());
            System.out.println("Age: " + person.getAge());
            System.out.println("Gender: " + person.getGender());
            System.out.println("Qualification: " + qualification);
            System.out.println("Specialization: " + specialization);
            System.out.println("Department: " + department);
            System.out.println("Shift: " + shift);
            System.out.println("Status: " + status);
            System.out.println("Experience: " + experience + " years");
            System.out.println("License Number: " + license);
            System.out.println("Phone: " + person.getPhone());
            System.out.println("Email: " + person.getEmail());
            System.out.println("Address: " + person.getAddress());
            System.out.println("Emergency Contact: " + person.getEmergencyContact());
            
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
            clearForm(addressField);
            
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
               qualificationComboBox.getValue() != null &&
               specializationComboBox.getValue() != null &&
               departmentComboBox.getValue() != null &&
               shiftComboBox.getValue() != null &&
               statusComboBox.getValue() != null &&
               !experienceField.getText().isEmpty() &&
               !licenseField.getText().isEmpty() &&
               !phoneField.getText().isEmpty();
    }
    
    private void clearForm(TextField addressField) {
        idField.clear();
        nameField.clear();
        ageField.clear();
        genderComboBox.setValue(null);
        qualificationComboBox.setValue(null);
        specializationComboBox.setValue(null);
        departmentComboBox.setValue(null);
        shiftComboBox.setValue(null);
        statusComboBox.setValue("Active");
        experienceField.clear();
        licenseField.clear();
        phoneField.clear();
        emailField.clear();
        addressField.clear();
        certificationArea.clear();
        notesArea.clear();
    }
    
    private void showSuccessAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Nurse Saved Successfully");
        alert.setContentText("Nurse information has been saved to the system.");
        alert.showAndWait();
    }
    
    public VBox getForm() {
        return formContainer;
    }
}