package application;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class AddNurseForm {
    
    private VBox formContainer;
    private TextField nurseIdField;
    private TextField nameField;
    private TextField ageField;
    private ComboBox<String> genderComboBox;
    private ComboBox<String> qualificationComboBox;
    private ComboBox<String> specializationComboBox;
    private ComboBox<String> shiftComboBox;
    private ComboBox<String> statusComboBox;
    private TextField experienceField;
    private TextField contactField;
    private TextField emailField;
    
    public AddNurseForm() {
        initializeForm();
    }
    
    private void initializeForm() {
        // Create form container
        formContainer = new VBox();
        formContainer.setSpacing(20);
        formContainer.setPadding(new Insets(20));
        
        // Title
        Label title = new Label("Add New Nurse");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        
        // Create form grid
        GridPane form = new GridPane();
        form.setVgap(15);
        form.setHgap(15);
        form.setPadding(new Insets(20));
        form.setStyle("-fx-background-color: white; -fx-border-color: #bdc3c7; -fx-border-radius: 5; -fx-border-width: 1;");
        
        // Initialize form fields
        nurseIdField = new TextField();
        nurseIdField.setPromptText("Enter Nurse ID");
        nurseIdField.setPrefWidth(250);
        
        nameField = new TextField();
        nameField.setPromptText("Enter Full Name");
        nameField.setPrefWidth(250);
        
        ageField = new TextField();
        ageField.setPromptText("Enter Age");
        ageField.setPrefWidth(250);
        
        // Gender ComboBox
        genderComboBox = new ComboBox<>();
        genderComboBox.getItems().addAll("Male", "Female", "Other");
        genderComboBox.setPromptText("Select Gender");
        genderComboBox.setPrefWidth(250);
        
        // Qualification ComboBox
        qualificationComboBox = new ComboBox<>();
        qualificationComboBox.getItems().addAll(
            "B.Sc Nursing",
            "M.Sc Nursing",
            "GNM (General Nursing & Midwifery)",
            "ANM (Auxiliary Nurse Midwife)",
            "Post Basic B.Sc Nursing",
            "Diploma in Nursing",
            "Nurse Practitioner"
        );
        qualificationComboBox.setPromptText("Select Qualification");
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
            "Geriatric Nurse"
        );
        specializationComboBox.setPromptText("Select Specialization");
        specializationComboBox.setPrefWidth(250);
        
        // Shift ComboBox
        shiftComboBox = new ComboBox<>();
        shiftComboBox.getItems().addAll(
            "Morning Shift (6 AM - 2 PM)",
            "Evening Shift (2 PM - 10 PM)",
            "Night Shift (10 PM - 6 AM)",
            "Rotating Shift",
            "Day Shift",
            "24-Hour Duty"
        );
        shiftComboBox.setPromptText("Select Shift");
        shiftComboBox.setPrefWidth(250);
        
        // Status ComboBox
        statusComboBox = new ComboBox<>();
        statusComboBox.getItems().addAll(
            "Active",
            "On Duty",
            "Off Duty",
            "On Leave",
            "Training",
            "Available",
            "Not Available"
        );
        statusComboBox.setValue("Active");
        statusComboBox.setPrefWidth(250);
        
        experienceField = new TextField();
        experienceField.setPromptText("Years of Experience");
        experienceField.setPrefWidth(250);
        
        contactField = new TextField();
        contactField.setPromptText("Contact Number");
        contactField.setPrefWidth(250);
        
        emailField = new TextField();
        emailField.setPromptText("Email Address");
        emailField.setPrefWidth(250);
        
        // Add labels and fields to grid
        int row = 0;
        
        form.add(createLabel("Nurse ID:"), 0, row);
        form.add(nurseIdField, 1, row++);
        
        form.add(createLabel("Full Name:"), 0, row);
        form.add(nameField, 1, row++);
        
        form.add(createLabel("Age:"), 0, row);
        form.add(ageField, 1, row++);
        
        form.add(createLabel("Gender:"), 0, row);
        form.add(genderComboBox, 1, row++);
        
        form.add(createLabel("Qualification:"), 0, row);
        form.add(qualificationComboBox, 1, row++);
        
        form.add(createLabel("Specialization:"), 0, row);
        form.add(specializationComboBox, 1, row++);
        
        form.add(createLabel("Shift:"), 0, row);
        form.add(shiftComboBox, 1, row++);
        
        form.add(createLabel("Status:"), 0, row);
        form.add(statusComboBox, 1, row++);
        
        form.add(createLabel("Experience:"), 0, row);
        form.add(experienceField, 1, row++);
        
        form.add(createLabel("Contact Number:"), 0, row);
        form.add(contactField, 1, row++);
        
        form.add(createLabel("Email:"), 0, row);
        form.add(emailField, 1, row++);
        
        // Additional fields
        form.add(createLabel("Ward/Department:"), 0, row);
        TextField departmentField = new TextField();
        departmentField.setPromptText("e.g., ICU, Emergency, Pediatrics");
        departmentField.setPrefWidth(250);
        form.add(departmentField, 1, row++);
        
        form.add(createLabel("License Number:"), 0, row);
        TextField licenseField = new TextField();
        licenseField.setPromptText("Nursing License Number");
        licenseField.setPrefWidth(250);
        form.add(licenseField, 1, row++);
        
        form.add(createLabel("Address:"), 0, row);
        TextField addressField = new TextField();
        addressField.setPromptText("Residential Address");
        addressField.setPrefWidth(250);
        form.add(addressField, 1, row);
        
        // Buttons
        HBox buttonBox = new HBox(15);
        buttonBox.setPadding(new Insets(20, 0, 0, 0));
        
        Button saveButton = new Button("Save Nurse");
        saveButton.setStyle("-fx-background-color: #9b59b6; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 20;");
        saveButton.setOnAction(e -> saveNurse(departmentField, licenseField, addressField));
        
        Button clearButton = new Button("Clear Form");
        clearButton.setStyle("-fx-background-color: #95a5a6; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 20;");
        clearButton.setOnAction(e -> clearForm(departmentField, licenseField, addressField));
        
        buttonBox.getChildren().addAll(saveButton, clearButton);
        
        // Add components to container
        formContainer.getChildren().addAll(title, form, buttonBox);
    }
    
    private Label createLabel(String text) {
        Label label = new Label(text);
        label.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        return label;
    }
    
    private void saveNurse(TextField departmentField, TextField licenseField, TextField addressField) {
        // Validate and save nurse data
        if (validateForm()) {
            System.out.println("=== NURSE ADDED ===");
            System.out.println("Nurse ID: " + nurseIdField.getText());
            System.out.println("Name: " + nameField.getText());
            System.out.println("Age: " + ageField.getText());
            System.out.println("Gender: " + genderComboBox.getValue());
            System.out.println("Qualification: " + qualificationComboBox.getValue());
            System.out.println("Specialization: " + specializationComboBox.getValue());
            System.out.println("Shift: " + shiftComboBox.getValue());
            System.out.println("Status: " + statusComboBox.getValue());
            System.out.println("Experience: " + experienceField.getText());
            System.out.println("Contact: " + contactField.getText());
            System.out.println("Email: " + emailField.getText());
            System.out.println("Department: " + departmentField.getText());
            System.out.println("License: " + licenseField.getText());
            System.out.println("Address: " + addressField.getText());
            System.out.println("=== END ===");
            
            clearForm(departmentField, licenseField, addressField);
        } else {
            System.out.println("Please fill in all required fields.");
        }
    }
    
    private boolean validateForm() {
        return !nurseIdField.getText().isEmpty() &&
               !nameField.getText().isEmpty() &&
               !ageField.getText().isEmpty() &&
               genderComboBox.getValue() != null &&
               qualificationComboBox.getValue() != null &&
               !contactField.getText().isEmpty();
    }
    
    private void clearForm(TextField departmentField, TextField licenseField, TextField addressField) {
        nurseIdField.clear();
        nameField.clear();
        ageField.clear();
        genderComboBox.setValue(null);
        qualificationComboBox.setValue(null);
        specializationComboBox.setValue(null);
        shiftComboBox.setValue(null);
        statusComboBox.setValue("Active");
        experienceField.clear();
        contactField.clear();
        emailField.clear();
        departmentField.clear();
        licenseField.clear();
        addressField.clear();
    }
    
    public VBox getForm() {
        return formContainer;
    }
}