package application;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class AddPatientForm {
    
    private VBox formContainer;
    private TextField nameField;
    private TextField ageField;
    private TextField genderField;
    private TextField addressField;
    private TextField phoneField;
    private TextField emailField;
    
    public AddPatientForm() {
        initializeForm();
    }
    
    private void initializeForm() {
        // Create form container
        formContainer = new VBox();
        formContainer.setSpacing(20);
        formContainer.setPadding(new Insets(20));
        
        // Title
        Label title = new Label("Add New Patient");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        
        // Create form grid
        GridPane form = new GridPane();
        form.setVgap(15);
        form.setHgap(15);
        form.setPadding(new Insets(20));
        form.setStyle("-fx-background-color: white; -fx-border-color: #bdc3c7; -fx-border-radius: 5; -fx-border-width: 1;");
        
        // Initialize form fields
        nameField = new TextField();
        nameField.setPrefWidth(300);
        
        ageField = new TextField();
        
        genderField = new TextField();
        
        addressField = new TextField();
        addressField.setPrefWidth(300);
        
        phoneField = new TextField();
        
        emailField = new TextField();
        emailField.setPrefWidth(300);
        
        // Add labels and fields to grid
        form.add(createLabel("Full Name:"), 0, 0);
        form.add(nameField, 1, 0);
        
        form.add(createLabel("Age:"), 0, 1);
        form.add(ageField, 1, 1);
        
        form.add(createLabel("Gender:"), 0, 2);
        form.add(genderField, 1, 2);
        
        form.add(createLabel("Address:"), 0, 3);
        form.add(addressField, 1, 3);
        
        form.add(createLabel("Phone:"), 0, 4);
        form.add(phoneField, 1, 4);
        
        form.add(createLabel("Email:"), 0, 5);
        form.add(emailField, 1, 5);
        
        // Additional fields
        form.add(createLabel("Emergency Contact:"), 0, 6);
        TextField emergencyContactField = new TextField();
        form.add(emergencyContactField, 1, 6);
        
        form.add(createLabel("Medical History:"), 0, 7);
        TextField medicalHistoryField = new TextField();
        medicalHistoryField.setPrefWidth(300);
        form.add(medicalHistoryField, 1, 7);
        
        // Save button
        Button saveButton = new Button("Save Patient");
        saveButton.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 20;");
        saveButton.setOnAction(e -> savePatient());
        
        // Clear button
        Button clearButton = new Button("Clear Form");
        clearButton.setStyle("-fx-background-color: #95a5a6; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 20;");
        clearButton.setOnAction(e -> clearForm());
        
        // Add components to container
        formContainer.getChildren().addAll(title, form, saveButton, clearButton);
    }
    
    private Label createLabel(String text) {
        Label label = new Label(text);
        label.setStyle("-fx-font-weight: bold;");
        return label;
    }
    
    private void savePatient() {
        // Validate and save patient data
        if (validateForm()) {
            System.out.println("=== PATIENT SAVED ===");
            System.out.println("Name: " + nameField.getText());
            System.out.println("Age: " + ageField.getText());
            System.out.println("Gender: " + genderField.getText());
            System.out.println("Address: " + addressField.getText());
            System.out.println("Phone: " + phoneField.getText());
            System.out.println("Email: " + emailField.getText());
            System.out.println("=== END ===");
            
            clearForm();
        } else {
            System.out.println("Please fill in all required fields.");
        }
    }
    
    private boolean validateForm() {
        return !nameField.getText().isEmpty() &&
               !ageField.getText().isEmpty() &&
               !genderField.getText().isEmpty() &&
               !phoneField.getText().isEmpty();
    }
    
    private void clearForm() {
        nameField.clear();
        ageField.clear();
        genderField.clear();
        addressField.clear();
        phoneField.clear();
        emailField.clear();
    }
    
    public VBox getForm() {
        return formContainer;
    }
}