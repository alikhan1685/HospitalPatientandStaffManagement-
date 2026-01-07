package application;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ScheduleAppointmentForm {
    
    private VBox formContainer;
    private TextField idField;
    private TextField patientIdField;
    private TextField doctorIdField;
    private TextField dateField;
    private TextField timeField;
    private TextField statusField;
    private TextField descriptionField;
    
    public ScheduleAppointmentForm() {
        initializeForm();
    }
    
    private void initializeForm() {
        // Create form container
        formContainer = new VBox();
        formContainer.setSpacing(20);
        formContainer.setPadding(new Insets(20));
        
        // Title
        Label title = new Label("Schedule Appointment");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        
        // Create form grid
        GridPane form = new GridPane();
        form.setVgap(15);
        form.setHgap(15);
        form.setPadding(new Insets(20));
        form.setStyle("-fx-background-color: white; -fx-border-color: #bdc3c7; -fx-border-radius: 5; -fx-border-width: 1;");
        
        // Initialize form fields
        idField = new TextField();
        idField.setPromptText("Enter Appointment ID");
        
        patientIdField = new TextField();
        patientIdField.setPromptText("Enter Patient ID");
        
        doctorIdField = new TextField();
        doctorIdField.setPromptText("Enter Doctor ID");
        
        dateField = new TextField();
        dateField.setPromptText("YYYY-MM-DD");
        
        timeField = new TextField();
        timeField.setPromptText("HH:MM");
        
        statusField = new TextField();
        statusField.setText("Scheduled");
        statusField.setEditable(false);
        statusField.setStyle("-fx-background-color: #f0f0f0;");
        
        descriptionField = new TextField();
        descriptionField.setPrefWidth(300);
        descriptionField.setPromptText("Enter appointment description");
        
        // Add labels and fields to grid
        form.add(createLabel("Appointment ID:"), 0, 0);
        form.add(idField, 1, 0);
        
        form.add(createLabel("Patient ID:"), 0, 1);
        form.add(patientIdField, 1, 1);
        
        form.add(createLabel("Doctor ID:"), 0, 2);
        form.add(doctorIdField, 1, 2);
        
        form.add(createLabel("Date:"), 0, 3);
        form.add(dateField, 1, 3);
        
        form.add(createLabel("Time:"), 0, 4);
        form.add(timeField, 1, 4);
        
        form.add(createLabel("Status:"), 0, 5);
        form.add(statusField, 1, 5);
        
        form.add(createLabel("Description:"), 0, 6);
        form.add(descriptionField, 1, 6);
        
        // Additional fields
        form.add(createLabel("Appointment Type:"), 0, 7);
        TextField typeField = new TextField();
        typeField.setPromptText("e.g., Consultation, Follow-up");
        form.add(typeField, 1, 7);
        
        form.add(createLabel("Room Number:"), 0, 8);
        TextField roomField = new TextField();
        form.add(roomField, 1, 8);
        
        // Buttons
        HBox buttonBox = new HBox(15);
        
        Button saveButton = new Button("Schedule Appointment");
        saveButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 20;");
        saveButton.setOnAction(e -> saveAppointment(typeField, roomField));
        
        Button clearButton = new Button("Clear Form");
        clearButton.setStyle("-fx-background-color: #95a5a6; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 20;");
        clearButton.setOnAction(e -> clearForm(typeField, roomField));
        
        buttonBox.getChildren().addAll(saveButton, clearButton);
        
        // Add components to container
        formContainer.getChildren().addAll(title, form, buttonBox);
    }
    
    private Label createLabel(String text) {
        Label label = new Label(text);
        label.setStyle("-fx-font-weight: bold;");
        return label;
    }
    
    private void saveAppointment(TextField typeField, TextField roomField) {
        // Validate and save appointment data
        if (validateForm()) {
            System.out.println("=== APPOINTMENT SCHEDULED ===");
            System.out.println("Appointment ID: " + idField.getText());
            System.out.println("Patient ID: " + patientIdField.getText());
            System.out.println("Doctor ID: " + doctorIdField.getText());
            System.out.println("Date: " + dateField.getText());
            System.out.println("Time: " + timeField.getText());
            System.out.println("Status: " + statusField.getText());
            System.out.println("Description: " + descriptionField.getText());
            System.out.println("Type: " + typeField.getText());
            System.out.println("Room: " + roomField.getText());
            System.out.println("=== END ===");
            
            clearForm(typeField, roomField);
        } else {
            System.out.println("Please fill in all required fields.");
        }
    }
    
    private boolean validateForm() {
        return !idField.getText().isEmpty() &&
               !patientIdField.getText().isEmpty() &&
               !doctorIdField.getText().isEmpty() &&
               !dateField.getText().isEmpty() &&
               !timeField.getText().isEmpty();
    }
    
    private void clearForm(TextField typeField, TextField roomField) {
        idField.clear();
        patientIdField.clear();
        doctorIdField.clear();
        dateField.clear();
        timeField.clear();
        descriptionField.clear();
        typeField.clear();
        roomField.clear();
        statusField.setText("Scheduled");
    }
    
    public VBox getForm() {
        return formContainer;
    }
}