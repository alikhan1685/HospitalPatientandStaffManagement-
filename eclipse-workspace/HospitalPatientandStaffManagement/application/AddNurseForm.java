package application;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
public class AddNurseForm {
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
   
    public AddNurseForm() {
        initializeForm();
    }
    
    private void initializeForm() {
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
        nameField = createTextField(300, "Enter full name");
        ageField = createTextField(100, "Age");
        experienceField = createTextField(100, "Years of experience");
        phoneField = createTextField(200, "Phone number");
        licenseField = createTextField(200, "Nursing license number");
        qualificationField = createTextField(250, "e.g., BSN, RN, LPN");
        
        // Gender ComboBox
        genderComboBox = new ComboBox<>();
        genderComboBox.getItems().addAll("Male", "Female", "Other");
        genderComboBox.setPromptText("Select gender");
        genderComboBox.setPrefWidth(150);
        
        // Specialization ComboBox
        specializationComboBox = new ComboBox<>();
        specializationComboBox.getItems().addAll(
            "General Nursing",
            "Pediatric Nursing", 
            "Critical Care Nursing",
            "Surgical Nursing",
            "Emergency Nursing",
            "Cardiac Nursing",
            "Neurology Nursing",
            "Oncology Nursing",
            "Geriatric Nursing",
            "Psychiatric Nursing",
            "Neonatal Nursing"
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
        
        // Add Nurse button
        Button addButton = new Button("Add Nurse");
        addButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 25; -fx-font-size: 14px; -fx-background-radius: 5;");
        addButton.setOnAction(e -> {
            if (saveNurse(addressField, certificationsField, validationLabel)) {
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
    
    private boolean saveNurse(TextField addressField, TextField certificationsField, Label validationLabel) {
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
            String department = departmentComboBox.getValue();
            String shift = shiftComboBox.getValue();
            int experience = Integer.parseInt(experienceField.getText());
            String phone = phoneField.getText();
            String license = licenseField.getText();
            String qualification = qualificationField.getText();
            String address = addressField.getText();
            String certifications = certificationsField.getText();
            
            // Validate data
            if (age < 21 || age > 65) {
                validationLabel.setText("Nurse age must be between 21 and 65");
                return false;
            }
            
            if (experience < 0 || experience > 40) {
                validationLabel.setText("Experience must be between 0 and 40 years");
                return false;
            }
            
            if (!phone.matches("\\d{10,}")) {
                validationLabel.setText("Phone number must contain at least 10 digits");
                return false;
            }
            
            // Generate nurse ID
            String nurseId = "NUR-" + (name.hashCode() % 10000);
            
            // Create Nurse object
            Nurses nurse = new Nurses(
                nurseId,
                name,
                age,
                specialization,
                qualification,
                experience,
                license,
                gender,
                address,
                phone,
                department
            );
            
            // Set additional properties
            nurse.setShift(shift);
            nurse.setCertifications(certifications);
            nurse.setDepartment(department);
            nurse.setStatus("Available");
            
            // Save to database
            NurseDatabase.getInstance().addNurse(nurse);
            
            // Display success message
            System.out.println("\n" + "=".repeat(50));
            System.out.println("NURSE ADDED TO DATABASE");
            System.out.println("=".repeat(50));
         //   nurse.displayInfo();
            System.out.println("=".repeat(50));
            
            // Show database status
            NurseDatabase.getInstance().printAllNurses();
            
            // Clear form
            validationLabel.setText("");
            clearForm(addressField, certificationsField);
            
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
               departmentComboBox.getValue() != null &&
               shiftComboBox.getValue() != null &&
               !experienceField.getText().isEmpty() &&
               !phoneField.getText().isEmpty() &&
               !licenseField.getText().isEmpty() &&
               !qualificationField.getText().isEmpty();
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
        int totalNurses = NurseDatabase.getInstance().getNurseCount();
        int availableNurses = NurseDatabase.getInstance().getAvailableNurseCount();
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Nurse Added Successfully");
        alert.setContentText("Nurse has been added to the nursing database.\n\n" +
                           "Total nurses: " + totalNurses + "\n" +
                           "Available nurses: " + availableNurses);
        alert.showAndWait();
    }
    
    public VBox getForm() {
        return formContainer;
    }
}