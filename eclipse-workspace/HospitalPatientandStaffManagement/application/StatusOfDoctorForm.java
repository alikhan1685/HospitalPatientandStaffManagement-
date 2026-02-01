package application;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class StatusOfDoctorForm  extends Doctors{
    private VBox formContainer;
    private TextField nameField;
    private TextField ageField;
    private ComboBox<String> genderComboBox;
    private ComboBox<String> specializationComboBox;
    private TextField experienceField;
    private TextField phoneField;
    private TextField emailField;
    private TextField licenseField;
    private ComboBox<String> statusComboBox;
    
    public StatusOfDoctorForm() {
        initializeForm();
    }
    
    private void initializeForm() {
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
        nameField = createTextField(300, "Enter full name");
        ageField = createTextField(100, "Age");
        experienceField = createTextField(100, "Years of experience");
        phoneField = createTextField(200, "Phone number");
        emailField = createTextField(250, "Email address");
        licenseField = createTextField(200, "Medical license number");
        
        // Gender ComboBox
        genderComboBox = new ComboBox<>();
        genderComboBox.getItems().addAll("Male", "Female", "Other");
        genderComboBox.setPromptText("Select gender");
        genderComboBox.setPrefWidth(150);
        
        // Specialization ComboBox
        specializationComboBox = new ComboBox<>();
        specializationComboBox.getItems().addAll(
            "Cardiology", "Neurology", "Pediatrics", "General Medicine",
            "General Surgery", "Dermatology", "Gynecology", "Psychiatry",
            "Orthopedics", "Radiology", "Anesthesiology", "Emergency Medicine"
        );
        specializationComboBox.setPromptText("Select specialization");
        specializationComboBox.setPrefWidth(250);
        
        // Status ComboBox
        statusComboBox = new ComboBox<>();
        statusComboBox.getItems().addAll(
            "Active",
            "Available",
            "Available for Consultation",
            "On Duty",
            "On Leave",
            "Not Available",
            "In Surgery"
        );
        statusComboBox.setValue("Active");
        statusComboBox.setPrefWidth(200);
        
        // Add labels and fields to grid
        int row = 0;
        
        form.add(createLabel("Full Name:*"), 0, row);
        form.add(nameField, 1, row);
        row++;
        
        form.add(createLabel("Age:*"), 0, row);
        form.add(ageField, 1, row);
        form.add(createLabel("Gender:*"), 2, row);
        form.add(genderComboBox, 3, row);
        row++;
        
        form.add(createLabel("Specialization:*"), 0, row);
        form.add(specializationComboBox, 1, row);
        form.add(createLabel("Experience (Years):*"), 2, row);
        form.add(experienceField, 3, row);
        row++;
        
        form.add(createLabel("Phone:*"), 0, row);
        form.add(phoneField, 1, row);
        form.add(createLabel("Email:*"), 2, row);
        form.add(emailField, 3, row);
        row++;
        
        form.add(createLabel("License Number:*"), 0, row);
        form.add(licenseField, 1, row);
        form.add(createLabel("Status:*"), 2, row);
        form.add(statusComboBox, 3, row);
        row++;
        
        form.add(createLabel("Address:"), 0, row);
        TextField addressField = createTextField(250, "Clinic/Hospital address");
        form.add(addressField, 1, row);
        row++;
        
        // Qualification field
        form.add(createLabel("Qualification:"), 0, row);
        TextField qualificationField = createTextField(250, "e.g., MBBS, MD, PhD");
        qualificationField.setText("MBBS");
        form.add(qualificationField, 1, row);
        row++;
        
        // Form validation message
        Label validationLabel = new Label();
        validationLabel.setStyle("-fx-text-fill: #e74c3c; -fx-font-size: 12px;");
        form.add(validationLabel, 1, row, 3, 1);
        
        // Button container
        HBox buttonContainer = new HBox(15);
        buttonContainer.setPadding(new Insets(20, 0, 0, 0));
        
        // Add Doctor button
        Button addButton = new Button("Add Doctor");
        addButton.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 25; -fx-font-size: 14px; -fx-background-radius: 5;");
        addButton.setOnAction(e -> {
            if (saveDoctor(addressField, qualificationField, validationLabel)) {
                showSuccessAlert();
            }
        });
        
        // Clear button
        Button clearButton = new Button("Clear Form");
        clearButton.setStyle("-fx-background-color: #95a5a6; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 25; -fx-font-size: 14px; -fx-background-radius: 5;");
        clearButton.setOnAction(e -> {
            clearForm(addressField, qualificationField);
            validationLabel.setText("");
        });
        
        buttonContainer.getChildren().addAll(addButton, clearButton);
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
    
    private boolean saveDoctor(TextField addressField, TextField qualificationField, Label validationLabel) {
        if (!validateForm()) {
            validationLabel.setText("Please fill in all required fields (*)");
            return false;
        }
        
        try {
            // Get form values
            String name = nameField.getText();
            int age = Integer.parseInt(ageField.getText());
            String gender = genderComboBox.getValue();
            String specialization = specializationComboBox.getValue();
            int experience = Integer.parseInt(experienceField.getText());
            String phone = phoneField.getText();
            String email = emailField.getText();
            String address = addressField.getText();
            String license = licenseField.getText();
            String status = statusComboBox.getValue();
            String qualification = qualificationField.getText();
            
            // Validate data
            if (age < 25 || age > 75) {
                validationLabel.setText("Age must be between 25 and 75");
                return false;
            }
            
            if (experience < 0 || experience > 50) {
                validationLabel.setText("Experience must be between 0 and 50 years");
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
            
            // Create Staff object for doctor
            Staff doctor = new Staff();
            
            // Generate doctor ID
            String doctorId = "DOC-" + (name.hashCode() % 10000);
            
            // Set doctor properties
            doctor.setStaffId(doctorId);
            doctor.setName(name);
            doctor.setAge(age);
            doctor.setGender(gender);
            doctor.setAddress(address);
            doctor.setPhone(phone);
            doctor.setEmail(email);
            doctor.setEmergencyContact(phone);
            doctor.setSpecialization(specialization);
            doctor.setExperience(experience);
            doctor.setLicenseNumber(license);
            doctor.setQualification(qualification);
            doctor.setDepartment(specialization + " Department");
            doctor.setStatus(status);
            
            // Save to database
            DoctorDatabase.getInstance().addDoctor(doctor);
            
            // Display success message
            System.out.println("\n" + "=".repeat(50));
            System.out.println("DOCTOR ADDED TO DATABASE");
            System.out.println("=".repeat(50));
            System.out.println("Doctor ID: " + doctorId);
            System.out.println("Name: " + name);
            System.out.println("Specialization: " + specialization);
            System.out.println("Status: " + status);
            System.out.println("Total doctors in database: " + DoctorDatabase.getInstance().getDoctorCount());
            System.out.println("=".repeat(50));
            
            // Show database status
            DoctorDatabase.getInstance().printAllDoctors();
            
            // Clear form
            validationLabel.setText("");
            clearForm(addressField, qualificationField);
            
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
        return !nameField.getText().isEmpty() &&
               !ageField.getText().isEmpty() &&
               genderComboBox.getValue() != null &&
               specializationComboBox.getValue() != null &&
               !experienceField.getText().isEmpty() &&
               !phoneField.getText().isEmpty() &&
               !emailField.getText().isEmpty() &&
               !licenseField.getText().isEmpty() &&
               statusComboBox.getValue() != null;
    }
    
    private void clearForm(TextField addressField, TextField qualificationField) {
        nameField.clear();
        ageField.clear();
        genderComboBox.setValue(null);
        specializationComboBox.setValue(null);
        experienceField.clear();
        phoneField.clear();
        emailField.clear();
        licenseField.clear();
        addressField.clear();
        qualificationField.clear();
        qualificationField.setText("MBBS");
        statusComboBox.setValue("Active");
    }
    
    private void showSuccessAlert() {
        int totalDoctors = DoctorDatabase.getInstance().getDoctorCount();
        int availableDoctors = DoctorDatabase.getInstance().getAvailableDoctorCount();
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Doctor Added Successfully");
        alert.setContentText("Doctor has been added to the database.\n\n" +
                           "Total doctors: " + totalDoctors + "\n" +
                           "Available doctors: " + availableDoctors);
        alert.showAndWait();
    }
    
    public VBox getForm() {
        return formContainer;
    }
}