package application;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class AddDoctorForm {
    
    private VBox formContainer;
    private TextField doctorIdField;
    private TextField nameField;
    private TextField ageField;
    private ComboBox<String> genderComboBox;
    private ComboBox<String> specializationComboBox;
    private ComboBox<String> qualificationComboBox;
    private ComboBox<String> departmentComboBox;
    private TextField experienceField;
    private TextField contactField;
    private TextField emailField;
    private ComboBox<String> statusComboBox;
    
    public AddDoctorForm() {
        initializeForm();
    }
    
    private void initializeForm() {
        // Create form container
        formContainer = new VBox();
        formContainer.setSpacing(20);
        formContainer.setPadding(new Insets(20));
        
        // Title
        Label title = new Label("Add New Doctor");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        
        // Create form grid
        GridPane form = new GridPane();
        form.setVgap(15);
        form.setHgap(15);
        form.setPadding(new Insets(20));
        form.setStyle("-fx-background-color: white; -fx-border-color: #bdc3c7; -fx-border-radius: 5; -fx-border-width: 1;");
        
        // Initialize form fields
        doctorIdField = new TextField();
        doctorIdField.setPromptText("Enter Doctor ID");
        doctorIdField.setPrefWidth(250);
        
        nameField = new TextField();
        nameField.setPromptText("Enter Full Name (Dr. First Last)");
        nameField.setPrefWidth(250);
        
        ageField = new TextField();
        ageField.setPromptText("Enter Age");
        ageField.setPrefWidth(250);
        
        // Gender ComboBox
        genderComboBox = new ComboBox<>();
        genderComboBox.getItems().addAll("Male", "Female", "Other");
        genderComboBox.setPromptText("Select Gender");
        genderComboBox.setPrefWidth(250);
        
        // Specialization ComboBox
        specializationComboBox = new ComboBox<>();
        specializationComboBox.getItems().addAll(
            "Cardiologist",
            "Neurologist",
            "Orthopedist",
            "Pediatrician",
            "General Physician",
            "Surgeon",
            "Dermatologist",
            "Ophthalmologist",
            "ENT Specialist",
            "Gynecologist",
            "Psychiatrist",
            "Radiologist",
            "Anesthesiologist",
            "Oncologist",
            "Urologist",
            "Nephrologist"
        );
        specializationComboBox.setPromptText("Select Specialization");
        specializationComboBox.setPrefWidth(250);
        
        // Qualification ComboBox
        qualificationComboBox = new ComboBox<>();
        qualificationComboBox.getItems().addAll(
            "MBBS",
            "MD",
            "MS",
            "DM",
            "MCh",
            "DNB",
            "PhD",
            "FRCS",
            "MRCP",
            "Board Certified"
        );
        qualificationComboBox.setPromptText("Select Qualification");
        qualificationComboBox.setPrefWidth(250);
        
        // Department ComboBox
        departmentComboBox = new ComboBox<>();
        departmentComboBox.getItems().addAll(
            "Cardiology",
            "Neurology",
            "Orthopedics",
            "Pediatrics",
            "General Medicine",
            "Surgery",
            "Dermatology",
            "Ophthalmology",
            "ENT",
            "Gynecology",
            "Psychiatry",
            "Radiology",
            "Anesthesiology",
            "Oncology",
            "Urology",
            "Emergency Medicine",
            "ICU"
        );
        departmentComboBox.setPromptText("Select Department");
        departmentComboBox.setPrefWidth(250);
        
        experienceField = new TextField();
        experienceField.setPromptText("Years of Experience");
        experienceField.setPrefWidth(250);
        
        contactField = new TextField();
        contactField.setPromptText("Contact Number");
        contactField.setPrefWidth(250);
        
        emailField = new TextField();
        emailField.setPromptText("Email Address");
        emailField.setPrefWidth(250);
        
        // Status ComboBox
        statusComboBox = new ComboBox<>();
        statusComboBox.getItems().addAll(
            "Active",
            "On Duty",
            "Off Duty",
            "On Leave",
            "Available",
            "Not Available",
            "Consulting",
            "In Surgery"
        );
        statusComboBox.setValue("Active");
        statusComboBox.setPrefWidth(250);
        
        // Add labels and fields to grid
        int row = 0;
        
        form.add(createLabel("Doctor ID:"), 0, row);
        form.add(doctorIdField, 1, row++);
        
        form.add(createLabel("Full Name:"), 0, row);
        form.add(nameField, 1, row++);
        
        form.add(createLabel("Age:"), 0, row);
        form.add(ageField, 1, row++);
        
        form.add(createLabel("Gender:"), 0, row);
        form.add(genderComboBox, 1, row++);
        
        form.add(createLabel("Specialization:"), 0, row);
        form.add(specializationComboBox, 1, row++);
        
        form.add(createLabel("Qualification:"), 0, row);
        form.add(qualificationComboBox, 1, row++);
        
        form.add(createLabel("Department:"), 0, row);
        form.add(departmentComboBox, 1, row++);
        
        form.add(createLabel("Experience:"), 0, row);
        form.add(experienceField, 1, row++);
        
        form.add(createLabel("Contact Number:"), 0, row);
        form.add(contactField, 1, row++);
        
        form.add(createLabel("Email:"), 0, row);
        form.add(emailField, 1, row++);
        
        form.add(createLabel("Status:"), 0, row);
        form.add(statusComboBox, 1, row++);
        
        // Additional fields
        form.add(createLabel("License Number:"), 0, row);
        TextField licenseField = new TextField();
        licenseField.setPromptText("Medical License Number");
        licenseField.setPrefWidth(250);
        form.add(licenseField, 1, row++);
        
        form.add(createLabel("Consultation Hours:"), 0, row);
        TextField hoursField = new TextField();
        hoursField.setPromptText("e.g., 9 AM - 5 PM, Mon-Fri");
        hoursField.setPrefWidth(250);
        form.add(hoursField, 1, row++);
        
        form.add(createLabel("Consultation Fee:"), 0, row);
        TextField feeField = new TextField();
        feeField.setPromptText("Fee per consultation");
        feeField.setPrefWidth(250);
        form.add(feeField, 1, row);
        
        // Buttons
        HBox buttonBox = new HBox(15);
        buttonBox.setPadding(new Insets(20, 0, 0, 0));
        
        Button saveButton = new Button("Save Doctor");
        saveButton.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 20;");
        saveButton.setOnAction(e -> saveDoctor(licenseField, hoursField, feeField));
        
        Button clearButton = new Button("Clear Form");
        clearButton.setStyle("-fx-background-color: #95a5a6; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 20;");
        clearButton.setOnAction(e -> clearForm(licenseField, hoursField, feeField));
        
        buttonBox.getChildren().addAll(saveButton, clearButton);
        
        // Add components to container
        formContainer.getChildren().addAll(title, form, buttonBox);
    }
    
    private Label createLabel(String text) {
        Label label = new Label(text);
        label.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        return label;
    }
    
    private void saveDoctor(TextField licenseField, TextField hoursField, TextField feeField) {
        // Validate and save doctor data
        if (validateForm()) {
            System.out.println("=== DOCTOR ADDED ===");
            System.out.println("Doctor ID: " + doctorIdField.getText());
            System.out.println("Name: " + nameField.getText());
            System.out.println("Age: " + ageField.getText());
            System.out.println("Gender: " + genderComboBox.getValue());
            System.out.println("Specialization: " + specializationComboBox.getValue());
            System.out.println("Qualification: " + qualificationComboBox.getValue());
            System.out.println("Department: " + departmentComboBox.getValue());
            System.out.println("Experience: " + experienceField.getText());
            System.out.println("Contact: " + contactField.getText());
            System.out.println("Email: " + emailField.getText());
            System.out.println("Status: " + statusComboBox.getValue());
            System.out.println("License: " + licenseField.getText());
            System.out.println("Consultation Hours: " + hoursField.getText());
            System.out.println("Consultation Fee: " + feeField.getText());
            System.out.println("=== END ===");
            
            clearForm(licenseField, hoursField, feeField);
        } else {
            System.out.println("Please fill in all required fields.");
        }
    }
    
    private boolean validateForm() {
        return !doctorIdField.getText().isEmpty() &&
               !nameField.getText().isEmpty() &&
               !ageField.getText().isEmpty() &&
               genderComboBox.getValue() != null &&
               specializationComboBox.getValue() != null &&
               qualificationComboBox.getValue() != null &&
               !contactField.getText().isEmpty();
    }
    
    private void clearForm(TextField licenseField, TextField hoursField, TextField feeField) {
        doctorIdField.clear();
        nameField.clear();
        ageField.clear();
        genderComboBox.setValue(null);
        specializationComboBox.setValue(null);
        qualificationComboBox.setValue(null);
        departmentComboBox.setValue(null);
        experienceField.clear();
        contactField.clear();
        emailField.clear();
        statusComboBox.setValue("Active");
        licenseField.clear();
        hoursField.clear();
        feeField.clear();
    }
    
    public VBox getForm() {
        return formContainer;
    }
}