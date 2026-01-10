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
    private TextField phoneField;
    private TextField licenseField;
    private ComboBox<String> specializationComboBox;
    private ComboBox<String> departmentComboBox;
    private ComboBox<String> doctorComboBox;
    
    public AddNurseForm() {
        initializeForm();
    }
    
    private void initializeForm() {
        // Create form container
        formContainer = new VBox();
        formContainer.setSpacing(15);
        formContainer.setPadding(new Insets(20));
        formContainer.setStyle("-fx-background-color: #f8f9fa;");
        
        // Title
        Label title = new Label("Add New Nurse");
        title.setFont(new Font("Arial", 24));
        title.setStyle("-fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        
        // Create form grid
        GridPane form = new GridPane();
        form.setVgap(12);
        form.setHgap(15);
        form.setPadding(new Insets(20));
        form.setStyle("-fx-background-color: white; -fx-border-color: #dee2e6; -fx-border-radius: 8; -fx-border-width: 1;");
        
        // Initialize form fields
        nameField = new TextField();
        nameField.setPromptText("Enter full name");
        nameField.setPrefWidth(250);
        
        ageField = new TextField();
        ageField.setPromptText("Age");
        ageField.setPrefWidth(80);
        
        phoneField = new TextField();
        phoneField.setPromptText("Phone number");
        phoneField.setPrefWidth(200);
        
        licenseField = new TextField();
        licenseField.setPromptText("License number");
        licenseField.setPrefWidth(200);
        
        // Gender ComboBox
        genderComboBox = new ComboBox<>();
        genderComboBox.getItems().addAll("Male", "Female", "Other");
        genderComboBox.setPromptText("Select gender");
        genderComboBox.setPrefWidth(150);
        
        // Specialization ComboBox
        specializationComboBox = new ComboBox<>();
        specializationComboBox.getItems().addAll(
            "General Ward Nurse",
            "ICU Nurse",
            "Emergency Room Nurse",
            "Pediatric Nurse",
            "Maternity Nurse",
            "Operation Theater Nurse"
        );
        specializationComboBox.setPromptText("Select specialization");
        specializationComboBox.setPrefWidth(200);
        
        // Department ComboBox
        departmentComboBox = new ComboBox<>();
        departmentComboBox.getItems().addAll(
            "Intensive Care Unit (ICU)",
            "Emergency Department",
            "Pediatrics",
            "Maternity Ward",
            "Operating Theater",
            "General Ward"
        );
        departmentComboBox.setPromptText("Select department");
        departmentComboBox.setPrefWidth(200);
        
        // Doctor ComboBox for "Assist with Doctor"
        doctorComboBox = new ComboBox<>();
        doctorComboBox.getItems().addAll(
            "Select a doctor",
            "Dr. Smith (Cardiology)",
            "Dr. Johnson (Pediatrics)",
            "Dr. Williams (Surgery)",
            "Dr. Brown (Neurology)",
            "Dr. Davis (Orthopedics)",
            "Dr. Miller (Emergency)"
        );
        doctorComboBox.setValue("Select a doctor");
        doctorComboBox.setPrefWidth(200);
        
        // Add labels and fields to grid
        int row = 0;
        
        // Row 0: Full Name
        Label nameLabel = new Label("Full Name:*");
        nameLabel.setStyle("-fx-font-weight: bold;");
        form.add(nameLabel, 0, row);
        form.add(nameField, 1, row);
        row++;
        
        // Row 1: Age
        Label ageLabel = new Label("Age:*");
        ageLabel.setStyle("-fx-font-weight: bold;");
        form.add(ageLabel, 0, row);
        form.add(ageField, 1, row);
        row++;
        
        // Row 2: Gender
        Label genderLabel = new Label("Gender:*");
        genderLabel.setStyle("-fx-font-weight: bold;");
        form.add(genderLabel, 0, row);
        form.add(genderComboBox, 1, row);
        row++;
        
        // Row 3: Phone
        Label phoneLabel = new Label("Phone:*");
        phoneLabel.setStyle("-fx-font-weight: bold;");
        form.add(phoneLabel, 0, row);
        form.add(phoneField, 1, row);
        row++;
        
        // Row 4: License Number
        Label licenseLabel = new Label("License Number:*");
        licenseLabel.setStyle("-fx-font-weight: bold;");
        form.add(licenseLabel, 0, row);
        form.add(licenseField, 1, row);
        row++;
        
        // Row 5: Specialization
        Label specializationLabel = new Label("Specialization:*");
        specializationLabel.setStyle("-fx-font-weight: bold;");
        form.add(specializationLabel, 0, row);
        form.add(specializationComboBox, 1, row);
        row++;
        
        // Row 6: Department
        Label departmentLabel = new Label("Department:*");
        departmentLabel.setStyle("-fx-font-weight: bold;");
        form.add(departmentLabel, 0, row);
        form.add(departmentComboBox, 1, row);
        row++;
        
        // Row 7: Assist with Doctor
        Label doctorLabel = new Label("Assist with Doctor:");
        doctorLabel.setStyle("-fx-font-weight: bold;");
        form.add(doctorLabel, 0, row);
        form.add(doctorComboBox, 1, row);
        row++;
        
        // Form validation message
        Label validationLabel = new Label();
        validationLabel.setStyle("-fx-text-fill: #e74c3c; -fx-font-size: 12px;");
        form.add(validationLabel, 0, row, 2, 1);
        
        // Button container
        HBox buttonContainer = new HBox(15);
        buttonContainer.setPadding(new Insets(20, 0, 0, 0));
        
        // Add Nurse Button
        Button addButton = new Button("Add Nurse");
        addButton.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 25; -fx-font-size: 14px; -fx-background-radius: 5;");
        addButton.setOnAction(e -> {
            if (addNurse(validationLabel)) {
                showSuccessAlert();
            }
        });
        
        buttonContainer.getChildren().add(addButton);
        
        // Add components to container
        formContainer.getChildren().addAll(title, form, buttonContainer);
    }
    
    private boolean addNurse(Label validationLabel) {
        if (!validateForm()) {
            validationLabel.setText("Please fill in all required fields (*)");
            return false;
        }
        
        try {
            // Get form values
            String name = nameField.getText();
            int age = Integer.parseInt(ageField.getText());
            String gender = genderComboBox.getValue();
            String phone = phoneField.getText();
            String license = licenseField.getText();
            String specialization = specializationComboBox.getValue();
            String department = departmentComboBox.getValue();
            String assignedDoctor = doctorComboBox.getValue();
            
            // Validate data
            if (age < 21 || age > 65) {
                validationLabel.setText("Age must be between 21 and 65");
                return false;
            }
            
            if (!phone.matches("\\d{10,}")) {
                validationLabel.setText("Phone number must contain at least 10 digits");
                return false;
            }
            
            // Create nurse and add to AvailableStaffForm
            String nurseId = "NUR-" + String.format("%03d", (AvailableStaffForm.getNurseCount() + 1));
            Nurses newNurse = new Nurses(nurseId, name, "Available", phone, department);
            
            // Add to available staff system
            AvailableStaffForm.addNurse(newNurse);
            
            // Display saved information
            System.out.println("\n=== NURSE ADDED SUCCESSFULLY ===");
            System.out.println("Nurse ID: " + nurseId);
            System.out.println("Name: " + name);
            System.out.println("Age: " + age);
            System.out.println("Gender: " + gender);
            System.out.println("Phone: " + phone);
            System.out.println("License Number: " + license);
            System.out.println("Specialization: " + specialization);
            System.out.println("Department: " + department);
            System.out.println("Assisting Doctor: " + assignedDoctor);
            System.out.println("=== END ===");
            
            // Clear form
            clearForm();
            validationLabel.setText("");
            
            return true;
            
        } catch (NumberFormatException e) {
            validationLabel.setText("Age must be a valid number");
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
               !phoneField.getText().isEmpty() &&
               !licenseField.getText().isEmpty() &&
               specializationComboBox.getValue() != null &&
               departmentComboBox.getValue() != null;
    }
    
    private void clearForm() {
        nameField.clear();
        ageField.clear();
        genderComboBox.setValue(null);
        phoneField.clear();
        licenseField.clear();
        specializationComboBox.setValue(null);
        departmentComboBox.setValue(null);
        doctorComboBox.setValue("Select a doctor");
    }
    
    private void showSuccessAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Nurse Added Successfully");
        alert.setContentText("Nurse has been added to the system and is now available in the Available Nurses list.");
        alert.showAndWait();
    }
    
    public VBox getForm() {
        return formContainer;
    }
}