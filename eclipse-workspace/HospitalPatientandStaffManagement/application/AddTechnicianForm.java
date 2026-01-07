package application;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class AddTechnicianForm {
    
    private VBox formContainer;
    private TextField nameField;
    private TextField idField;
    private ComboBox<String> roleComboBox;
    private ComboBox<String> operationStatusComboBox;
    private TextField departmentField;
    private TextField qualificationField;
    private TextField experienceField;
    private TextField contactField;
    
    public AddTechnicianForm() {
        initializeForm();
    }
    
    private void initializeForm() {
        // Create form container
        formContainer = new VBox();
        formContainer.setSpacing(20);
        formContainer.setPadding(new Insets(20));
        
        // Title
        Label title = new Label("Add New Technician");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        
        // Create form grid
        GridPane form = new GridPane();
        form.setVgap(15);
        form.setHgap(15);
        form.setPadding(new Insets(20));
        form.setStyle("-fx-background-color: white; -fx-border-color: #bdc3c7; -fx-border-radius: 5; -fx-border-width: 1;");
        
        // Initialize form fields
        idField = new TextField();
        idField.setPromptText("Enter Technician ID");
        idField.setPrefWidth(250);
        
        nameField = new TextField();
        nameField.setPromptText("Enter Full Name");
        nameField.setPrefWidth(250);
        
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
            "Medical Laboratory Technician"
        );
        roleComboBox.setPromptText("Select Role");
        roleComboBox.setPrefWidth(250);
        
        // Operation Status ComboBox
        operationStatusComboBox = new ComboBox<>();
        operationStatusComboBox.getItems().addAll(
            "Active",
            "On Leave",
            "On Duty",
            "Off Duty",
            "Available",
            "Unavailable",
            "Training"
        );
        operationStatusComboBox.setValue("Active");
        operationStatusComboBox.setPrefWidth(250);
        
        departmentField = new TextField();
        departmentField.setPromptText("Enter Department");
        departmentField.setPrefWidth(250);
        
        qualificationField = new TextField();
        qualificationField.setPromptText("Enter Qualifications");
        qualificationField.setPrefWidth(250);
        
        experienceField = new TextField();
        experienceField.setPromptText("Years of Experience");
        experienceField.setPrefWidth(250);
        
        contactField = new TextField();
        contactField.setPromptText("Contact Number");
        contactField.setPrefWidth(250);
        
        // Add labels and fields to grid
        int row = 0;
        
        form.add(createLabel("Technician ID:"), 0, row);
        form.add(idField, 1, row++);
        
        form.add(createLabel("Full Name:"), 0, row);
        form.add(nameField, 1, row++);
        
        form.add(createLabel("Role:"), 0, row);
        form.add(roleComboBox, 1, row++);
        
        form.add(createLabel("Operation Status:"), 0, row);
        form.add(operationStatusComboBox, 1, row++);
        
        form.add(createLabel("Department:"), 0, row);
        form.add(departmentField, 1, row++);
        
        form.add(createLabel("Qualifications:"), 0, row);
        form.add(qualificationField, 1, row++);
        
        form.add(createLabel("Experience:"), 0, row);
        form.add(experienceField, 1, row++);
        
        form.add(createLabel("Contact Number:"), 0, row);
        form.add(contactField, 1, row++);
        
        // Additional fields
        form.add(createLabel("Email:"), 0, row);
        TextField emailField = new TextField();
        emailField.setPromptText("Email Address");
        emailField.setPrefWidth(250);
        form.add(emailField, 1, row++);
        
        form.add(createLabel("Shift:"), 0, row);
        TextField shiftField = new TextField();
        shiftField.setPromptText("e.g., Morning, Evening, Night");
        shiftField.setPrefWidth(250);
        form.add(shiftField, 1, row++);
        
        form.add(createLabel("Specialization:"), 0, row);
        TextField specializationField = new TextField();
        specializationField.setPromptText("Specialized Skills");
        specializationField.setPrefWidth(250);
        form.add(specializationField, 1, row);
        
        // Buttons
        HBox buttonBox = new HBox(15);
        buttonBox.setPadding(new Insets(20, 0, 0, 0));
        
        Button saveButton = new Button("Save Technician");
        saveButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 20;");
        saveButton.setOnAction(e -> saveTechnician(emailField, shiftField, specializationField));
        
        Button clearButton = new Button("Clear Form");
        clearButton.setStyle("-fx-background-color: #95a5a6; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 20;");
        clearButton.setOnAction(e -> clearForm(emailField, shiftField, specializationField));
        
        buttonBox.getChildren().addAll(saveButton, clearButton);
        
        // Add components to container
        formContainer.getChildren().addAll(title, form, buttonBox);
    }
    
    private Label createLabel(String text) {
        Label label = new Label(text);
        label.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        return label;
    }
    
    private void saveTechnician(TextField emailField, TextField shiftField, TextField specializationField) {
        // Validate and save technician data
        if (validateForm()) {
            System.out.println("=== TECHNICIAN ADDED ===");
            System.out.println("Technician ID: " + idField.getText());
            System.out.println("Name: " + nameField.getText());
            System.out.println("Role: " + roleComboBox.getValue());
            System.out.println("Operation Status: " + operationStatusComboBox.getValue());
            System.out.println("Department: " + departmentField.getText());
            System.out.println("Qualifications: " + qualificationField.getText());
            System.out.println("Experience: " + experienceField.getText());
            System.out.println("Contact: " + contactField.getText());
            System.out.println("Email: " + emailField.getText());
            System.out.println("Shift: " + shiftField.getText());
            System.out.println("Specialization: " + specializationField.getText());
            System.out.println("=== END ===");
            
            clearForm(emailField, shiftField, specializationField);
        } else {
            System.out.println("Please fill in all required fields.");
        }
    }
    
    private boolean validateForm() {
        return !idField.getText().isEmpty() &&
               !nameField.getText().isEmpty() &&
               roleComboBox.getValue() != null &&
               operationStatusComboBox.getValue() != null &&
               !departmentField.getText().isEmpty() &&
               !contactField.getText().isEmpty();
    }
    
    private void clearForm(TextField emailField, TextField shiftField, TextField specializationField) {
        idField.clear();
        nameField.clear();
        roleComboBox.setValue(null);
        operationStatusComboBox.setValue("Active");
        departmentField.clear();
        qualificationField.clear();
        experienceField.clear();
        contactField.clear();
        emailField.clear();
        shiftField.clear();
        specializationField.clear();
    }
    
    public VBox getForm() {
        return formContainer;
    }
}