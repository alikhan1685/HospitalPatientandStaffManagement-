package application;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import java.util.Arrays;
import java.util.List;

public class AddDoctorForm {
    
    private VBox formContainer;
    private TextField idField;
    private TextField nameField;
    private TextField ageField;
    private ComboBox<String> genderComboBox;
    private ComboBox<String> specializationComboBox;
    private ComboBox<String> qualificationComboBox;
    private ComboBox<String> departmentComboBox;
    private ComboBox<String> statusComboBox;
    private TextField experienceField;
    private TextField phoneField;
    private TextField emailField;
    private TextField licenseField;
    private TextField consultationHoursField;
    private TextField consultationFeeField;
    private TextArea expertiseArea;
    private TextArea notesArea;
    
    public AddDoctorForm() {
        initializeForm();
    }
    
    private void initializeForm() {
        // Create form container
        formContainer = new VBox();
        formContainer.setSpacing(20);
        formContainer.setPadding(new Insets(20));
        formContainer.setStyle("-fx-background-color: #f8f9fa;");
        
        // Title
        Label title = new Label("Add New Doctor");
        title.setFont(new Font("Arial", 28));
        title.setStyle("-fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        
        // Create form grid
        GridPane form = new GridPane();
        form.setVgap(15);
        form.setHgap(20);
        form.setPadding(new Insets(25));
        form.setStyle("-fx-background-color: white; -fx-border-color: #dee2e6; -fx-border-radius: 8; -fx-border-width: 1;");
        
        // Initialize form fields
        idField = createTextField(200, "e.g., DOC-001");
        nameField = createTextField(300, "Enter full name (Dr. First Last)");
        ageField = createTextField(80, "Age");
        experienceField = createTextField(100, "Years");
        phoneField = createTextField(200, "Phone number");
        emailField = createTextField(250, "Email address");
        licenseField = createTextField(200, "Medical license number");
        consultationHoursField = createTextField(200, "e.g., 9 AM - 5 PM");
        consultationFeeField = createTextField(150, "Fee amount");
        
        // Gender ComboBox
        genderComboBox = new ComboBox<>();
        genderComboBox.getItems().addAll("Male", "Female", "Other");
        genderComboBox.setPromptText("Select gender");
        genderComboBox.setPrefWidth(150);
        
        // Specialization ComboBox
        specializationComboBox = new ComboBox<>();
        specializationComboBox.getItems().addAll(
            "Cardiology",
            "Neurology",
            "Orthopedics",
            "Pediatrics",
            "General Medicine",
            "General Surgery",
            "Dermatology",
            "Ophthalmology",
            "ENT (Otolaryngology)",
            "Gynecology",
            "Psychiatry",
            "Radiology",
            "Anesthesiology",
            "Oncology",
            "Urology",
            "Nephrology",
            "Endocrinology",
            "Gastroenterology",
            "Hematology",
            "Pulmonology",
            "Rheumatology",
            "Plastic Surgery",
            "Neuro Surgery",
            "Cardiac Surgery",
            "Pediatric Surgery"
        );
        specializationComboBox.setPromptText("Select specialization");
        specializationComboBox.setPrefWidth(250);
        
        // Qualification ComboBox
        qualificationComboBox = new ComboBox<>();
        qualificationComboBox.getItems().addAll(
            "MBBS (Bachelor of Medicine, Bachelor of Surgery)",
            "MD (Doctor of Medicine)",
            "MS (Master of Surgery)",
            "DM (Doctorate of Medicine)",
            "MCh (Master of Chirurgiae)",
            "DNB (Diplomate of National Board)",
            "PhD (Doctor of Philosophy)",
            "FRCS (Fellow of Royal College of Surgeons)",
            "MRCP (Member of Royal College of Physicians)",
            "Board Certified",
            "Diploma in Medical Specialization"
        );
        qualificationComboBox.setPromptText("Select qualification");
        qualificationComboBox.setPrefWidth(300);
        
        // Department ComboBox
        departmentComboBox = new ComboBox<>();
        departmentComboBox.getItems().addAll(
            "Cardiology Department",
            "Neurology Department",
            "Orthopedics Department",
            "Pediatrics Department",
            "General Medicine",
            "Surgery Department",
            "Dermatology Department",
            "Ophthalmology Department",
            "ENT Department",
            "Gynecology Department",
            "Psychiatry Department",
            "Radiology Department",
            "Anesthesiology Department",
            "Oncology Department",
            "Emergency Medicine",
            "Intensive Care Unit (ICU)",
            "Outpatient Department (OPD)",
            "Inpatient Department"
        );
        departmentComboBox.setPromptText("Select department");
        departmentComboBox.setPrefWidth(250);
        
        // Status ComboBox
        statusComboBox = new ComboBox<>();
        statusComboBox.getItems().addAll(
            "Active - Available",
            "Active - On Duty",
            "Active - Off Duty",
            "On Leave",
            "On Vacation",
            "Available for Consultation",
            "Not Available",
            "In Surgery",
            "In Emergency",
            "Teaching/Research",
            "Sabbatical"
        );
        statusComboBox.setValue("Active - Available");
        statusComboBox.setPrefWidth(200);
        
        // Expertise TextArea
        expertiseArea = new TextArea();
        expertiseArea.setPrefRowCount(3);
        expertiseArea.setPrefWidth(300);
        expertiseArea.setPromptText("Special expertise, procedures, certifications...");
        expertiseArea.setWrapText(true);
        
        // Notes TextArea
        notesArea = new TextArea();
        notesArea.setPrefRowCount(3);
        notesArea.setPrefWidth(300);
        notesArea.setPromptText("Additional notes (optional)...");
        notesArea.setWrapText(true);
        
        // Add labels and fields to grid
        int row = 0;
        
        // Row 0: ID and Name
        form.add(createLabel("Doctor ID:*"), 0, row);
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
        
        // Row 2: Specialization and Qualification
        form.add(createLabel("Specialization:*"), 0, row);
        form.add(specializationComboBox, 1, row);
        form.add(createLabel("Qualification:*"), 2, row);
        form.add(qualificationComboBox, 3, row);
        row++;
        
        // Row 3: Department and Experience
        form.add(createLabel("Department:*"), 0, row);
        form.add(departmentComboBox, 1, row);
        form.add(createLabel("Experience (Years):*"), 2, row);
        form.add(experienceField, 3, row);
        row++;
        
        // Row 4: Contact Information
        form.add(createLabel("Phone:*"), 0, row);
        form.add(phoneField, 1, row);
        form.add(createLabel("Email:*"), 2, row);
        form.add(emailField, 3, row);
        row++;
        
        // Row 5: Professional Details
        form.add(createLabel("License Number:*"), 0, row);
        form.add(licenseField, 1, row);
        form.add(createLabel("Status:*"), 2, row);
        form.add(statusComboBox, 3, row);
        row++;
        
        // Row 6: Consultation Details
        form.add(createLabel("Consultation Hours:"), 0, row);
        form.add(consultationHoursField, 1, row);
        form.add(createLabel("Consultation Fee:"), 2, row);
        form.add(consultationFeeField, 3, row);
        row++;
        
        // Row 7: Address field (takes 2 columns)
        form.add(createLabel("Address:"), 0, row);
        TextField addressField = new TextField();
        addressField.setPromptText("Clinic/Hospital address");
        addressField.setPrefWidth(300);
        form.add(addressField, 1, row, 3, 1);
        row++;
        
        // Row 8: Expertise
        form.add(createLabel("Special Expertise:"), 0, row);
        form.add(expertiseArea, 1, row, 3, 1);
        row++;
        
        // Row 9: Notes
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
        
        // Save button
        Button saveButton = new Button("Save Doctor");
        saveButton.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 25; -fx-font-size: 14px; -fx-background-radius: 5;");
        saveButton.setOnAction(e -> {
            if (saveDoctor(addressField, validationLabel)) {
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
    
    private boolean saveDoctor(TextField addressField, Label validationLabel) {
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
            String specialization = specializationComboBox.getValue();
            String qualification = qualificationComboBox.getValue();
            String department = departmentComboBox.getValue();
            String status = statusComboBox.getValue();
            String experience = experienceField.getText();
            String license = licenseField.getText();
            String phone = phoneField.getText();
            String email = emailField.getText();
            String address = addressField.getText();
            String consultationHours = consultationHoursField.getText();
            String consultationFee = consultationFeeField.getText();
            String expertise = expertiseArea.getText();
            String notes = notesArea.getText();
            
            // Validate data
            if (age < 25 || age > 75) {
                validationLabel.setText("Age must be between 25 and 75");
                return false;
            }
            
            if (!phone.matches("\\d{10,}")) {
                validationLabel.setText("Phone number must contain at least 10 digits");
                return false;
            }
            
            if (!email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
                validationLabel.setText("Please enter a valid email address");
                return false;
            }
            
            // Create Person object
            Person person = new Person(name, age, gender, address, phone, email, phone); // Using phone as emergency contact
            
            // Display saved information
            System.out.println("\n=== DOCTOR SAVED SUCCESSFULLY ===");
            System.out.println("Doctor ID: " + id);
            System.out.println("Person ID: " + person.getId());
            System.out.println("Name: " + person.getName());
            System.out.println("Age: " + person.getAge());
            System.out.println("Gender: " + person.getGender());
            System.out.println("Specialization: " + specialization);
            System.out.println("Qualification: " + qualification);
            System.out.println("Department: " + department);
            System.out.println("Status: " + status);
            System.out.println("Experience: " + experience + " years");
            System.out.println("Medical License: " + license);
            System.out.println("Phone: " + person.getPhone());
            System.out.println("Email: " + person.getEmail());
            System.out.println("Address: " + person.getAddress());
            System.out.println("Emergency Contact: " + person.getEmergencyContact());
            System.out.println("Consultation Hours: " + consultationHours);
            System.out.println("Consultation Fee: " + consultationFee);
            
            if (!expertise.isEmpty()) {
                System.out.println("\nSpecial Expertise:");
                String[] expertises = expertise.split("\n");
                for (String exp : expertises) {
                    System.out.println("  • " + exp.trim());
                }
            }
            
            if (!notes.isEmpty()) {
                System.out.println("\nAdditional Notes: " + notes);
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
               specializationComboBox.getValue() != null &&
               qualificationComboBox.getValue() != null &&
               departmentComboBox.getValue() != null &&
               statusComboBox.getValue() != null &&
               !experienceField.getText().isEmpty() &&
               !licenseField.getText().isEmpty() &&
               !phoneField.getText().isEmpty() &&
               !emailField.getText().isEmpty();
    }
    
    private void clearForm(TextField addressField) {
        idField.clear();
        nameField.clear();
        ageField.clear();
        genderComboBox.setValue(null);
        specializationComboBox.setValue(null);
        qualificationComboBox.setValue(null);
        departmentComboBox.setValue(null);
        statusComboBox.setValue("Active - Available");
        experienceField.clear();
        licenseField.clear();
        phoneField.clear();
        emailField.clear();
        addressField.clear();
        consultationHoursField.clear();
        consultationFeeField.clear();
        expertiseArea.clear();
        notesArea.clear();
    }
    
    private void showSuccessAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Doctor Saved Successfully");
        alert.setContentText("Doctor information has been saved to the system.");
        alert.showAndWait();
    }
    
    public VBox getForm() {
        return formContainer;
    }
}