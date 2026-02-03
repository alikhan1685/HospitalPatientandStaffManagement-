package application;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class AddDoctorForm {
    private VBox formContainer;
    private TextField nameField;
    private TextField ageField;
    private ComboBox<String> genderComboBox;
    private ComboBox<String> specializationComboBox;
    private ComboBox<String> departmentComboBox;
    private ComboBox<String> shiftComboBox;
    private TextField experienceField;
    private TextField phoneField;
    private TextField licenseField;
    private TextField qualificationField; 
   
    public AddDoctorForm() {
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
        licenseField = createTextField(200, "Doctor license number");
        qualificationField = createTextField(250, "e.g., MBBS, MD, MS");
        
        // Gender ComboBox
        genderComboBox = new ComboBox<>();
        genderComboBox.getItems().addAll("Male", "Female", "Other");
        genderComboBox.setPromptText("Select gender");
        genderComboBox.setPrefWidth(150);
        
        // Specialization ComboBox
        specializationComboBox = new ComboBox<>();
        specializationComboBox.getItems().addAll(
            "General Doctor",
            "Pediatric Doctor", 
            "Critical Care Doctor",
            "Surgical Doctor",
            "Emergency Doctor",
            "Cardiac Doctor",
            "Neurology Doctor",
            "Oncology Doctor",
            "Geriatric Doctor",
            "Psychiatric Doctor"
        );
        specializationComboBox.setPromptText("Select specialization");
        specializationComboBox.setPrefWidth(250);
        
        // Department ComboBox
        departmentComboBox = new ComboBox<>();
        departmentComboBox.getItems().addAll(
            "General Ward",
            "Pediatrics Department",
            "Intensive Care Unit (ICU)",
            "Surgery Department",
            "Emergency Department",
            "Cardiology Department",
            "Maternity Ward",
            "Orthopedics Department",
            "Oncology Department",
            "Psychiatry Department"
        );
        departmentComboBox.setPromptText("Select department");
        departmentComboBox.setPrefWidth(250);
        
        // Shift ComboBox
        shiftComboBox = new ComboBox<>();
        shiftComboBox.getItems().addAll(
            "Day Shift (7 AM - 3 PM)",
            "Evening Shift (3 PM - 11 PM)",
            "Night Shift (11 PM - 7 AM)",
            "Morning Shift (6 AM - 2 PM)",
            "Afternoon Shift (2 PM - 10 PM)",
            "Rotational Shift"
        );
        shiftComboBox.setValue("Day Shift (7 AM - 3 PM)");
        shiftComboBox.setPrefWidth(200);
        
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
        form.add(createLabel("Department:*"), 2, row);
        form.add(departmentComboBox, 3, row);
        row++;
        
        form.add(createLabel("Experience (Years):*"), 0, row);
        form.add(experienceField, 1, row);
        form.add(createLabel("Shift:*"), 2, row);
        form.add(shiftComboBox, 3, row);
        row++;
        
        form.add(createLabel("Phone:*"), 0, row);
        form.add(phoneField, 1, row);
        form.add(createLabel("License Number:*"), 2, row);
        form.add(licenseField, 3, row);
        row++;
        
        form.add(createLabel("Qualification:*"), 0, row);
        form.add(qualificationField, 1, row);
        row++;
        
        form.add(createLabel("Address:"), 0, row);
        TextField addressField = createTextField(250, "Hospital/residential address");
        form.add(addressField, 1, row, 3, 1);
        row++;
        
        form.add(createLabel("Certifications:"), 0, row);
        TextField certificationsField = createTextField(300, "e.g., CPR, BLS, ACLS (comma separated)");
        form.add(certificationsField, 1, row, 3, 1);
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
        addButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 25; -fx-font-size: 14px; -fx-background-radius: 5;");
        addButton.setOnAction(e -> {
            if (saveDoctor(addressField, certificationsField, validationLabel)) {
                showSuccessAlert();
            }
        });
        
        // Clear button
        Button clearButton = new Button("Clear Form");
        clearButton.setStyle("-fx-background-color: #95a5a6; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 25; -fx-font-size: 14px; -fx-background-radius: 5;");
        clearButton.setOnAction(e -> {
            clearForm(addressField, certificationsField);
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
    
    private boolean saveDoctor(TextField addressField, TextField certificationsField, Label validationLabel) {
        if (!validateForm()) {
            validationLabel.setText("Please fill in all required fields (*)");
            return false;
        }
        
        try {
            // Get form values
            String name = nameField.getText().trim();
            int age = Integer.parseInt(ageField.getText().trim());
            String gender = genderComboBox.getValue();
            String specialization = specializationComboBox.getValue();
            String department = departmentComboBox.getValue();
            String shift = shiftComboBox.getValue();
            int experience = Integer.parseInt(experienceField.getText().trim());
            String phone = phoneField.getText().trim();
            String license = licenseField.getText().trim();
            String qualification = qualificationField.getText().trim();
            String address = addressField.getText().trim();
            String certifications = certificationsField.getText().trim();
            
            // Validate data
            if (age < 25 || age > 70) {
                validationLabel.setText("Doctor age must be between 25 and 70");
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
            
            if (license.isEmpty()) {
                validationLabel.setText("License number is required");
                return false;
            }
            
            // Generate sequential doctor ID starting from DOC-001
            String doctorId = generateDoctorId();
            
            // Create the full name with Dr. prefix
            String fullName = name;
            if (!name.startsWith("Dr.") && !name.startsWith("Dr ")) {
                fullName = "Dr. " + name;
            }
            
            // Create Staff object for doctor using the full constructor
            Staff doctor = new Staff(doctorId, fullName, age, gender, specialization, 
                                    experience, license, qualification, department,
                                    phone, address, "Available");
            
            // Display success message
            System.out.println("\n" + "=".repeat(50));
            System.out.println("DOCTOR ADDED TO DATABASE");
            System.out.println("=".repeat(50));
            System.out.println("Doctor ID: " + doctorId);
            System.out.println("Name: " + fullName);
            System.out.println("Age: " + age);
            System.out.println("Gender: " + gender);
            System.out.println("Specialization: " + specialization);
            System.out.println("Experience: " + experience + " years");
            System.out.println("Department: " + department);
            System.out.println("Qualification: " + qualification);
            System.out.println("License: " + license);
            System.out.println("Phone: " + phone);
            System.out.println("Address: " + address);
            System.out.println("Shift: " + shift);
            System.out.println("Certifications: " + certifications);
            System.out.println("=".repeat(50));
            
            // Save to DoctorDatabase
            DoctorDatabase db = DoctorDatabase.getInstance();
            db.addDoctor(doctor);
            
            // Show database status
            db.printAllDoctors();
            
            // Clear form
            validationLabel.setText("");
            clearForm(addressField, certificationsField);
            
            return true;
            
        } catch (NumberFormatException e) {
            validationLabel.setText("Age and Experience must be valid numbers");
            return false;
        } catch (Exception e) {
            validationLabel.setText("An error occurred: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    // Helper method to generate sequential doctor IDs
    private String generateDoctorId() {
        // Get all current doctors
        java.util.List<Staff> allDoctors = DoctorDatabase.getInstance().getAllDoctors();
        
        // Find the highest existing doctor number
        int maxNumber = 0;
        for (Staff doctor : allDoctors) {
            String id = doctor.getStaffId();
            if (id != null && id.startsWith("DOC-")) {
                try {
                    // Extract the number part after "DOC-"
                    String numberPart = id.substring(4); // Remove "DOC-"
                    int number = Integer.parseInt(numberPart);
                    if (number > maxNumber) {
                        maxNumber = number;
                    }
                } catch (NumberFormatException e) {
                    // Skip if not a valid number format
                }
            }
        }
        
        // Generate next sequential ID
        int nextNumber = maxNumber + 1;
        return String.format("DOC-%03d", nextNumber); // DOC-001, DOC-002, etc.
    }
    
    private boolean validateForm() {
        return !nameField.getText().trim().isEmpty() &&
               !ageField.getText().trim().isEmpty() &&
               genderComboBox.getValue() != null &&
               specializationComboBox.getValue() != null &&
               departmentComboBox.getValue() != null &&
               shiftComboBox.getValue() != null &&
               !experienceField.getText().trim().isEmpty() &&
               !phoneField.getText().trim().isEmpty() &&
               !licenseField.getText().trim().isEmpty() &&
               !qualificationField.getText().trim().isEmpty();
    }
    
    private void clearForm(TextField addressField, TextField certificationsField) {
        nameField.clear();
        ageField.clear();
        genderComboBox.setValue(null);
        specializationComboBox.setValue(null);
        departmentComboBox.setValue(null);
        shiftComboBox.setValue("Day Shift (7 AM - 3 PM)");
        experienceField.clear();
        phoneField.clear();
        licenseField.clear();
        qualificationField.clear();
        addressField.clear();
        certificationsField.clear();
    }
    
    private void showSuccessAlert() {
        int totalDoctors = DoctorDatabase.getInstance().getDoctorCount();
        int availableDoctors = DoctorDatabase.getInstance().getAvailableDoctorCount();
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Doctor Added Successfully");
        alert.setContentText("Doctor has been added to the doctor database.\n\n" +
                           "Total Doctors: " + totalDoctors + "\n" +
                           "Available Doctors: " + availableDoctors);
        alert.showAndWait();
    }
    
    public VBox getForm() {
        return formContainer;
    }
}