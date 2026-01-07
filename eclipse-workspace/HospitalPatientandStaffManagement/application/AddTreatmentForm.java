package application;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class AddTreatmentForm {
    
    private VBox formContainer;
    private TextField treatmentIdField;
    private TextField diseaseField;
    private TextField medicineField;
    private TextArea notesArea;
    private ComboBox<String> assignedDoctorComboBox;
    private TextField patientIdField;
    private TextField startDateField;
    private TextField durationField;
    
    public AddTreatmentForm() {
        initializeForm();
    }
    
    private void initializeForm() {
        // Create form container
        formContainer = new VBox();
        formContainer.setSpacing(20);
        formContainer.setPadding(new Insets(20));
        
        // Title
        Label title = new Label("Add New Treatment");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        
        // Create form grid
        GridPane form = new GridPane();
        form.setVgap(15);
        form.setHgap(15);
        form.setPadding(new Insets(20));
        form.setStyle("-fx-background-color: white; -fx-border-color: #bdc3c7; -fx-border-radius: 5; -fx-border-width: 1;");
        
        // Initialize form fields
        treatmentIdField = new TextField();
        treatmentIdField.setPromptText("Enter Treatment ID");
        treatmentIdField.setPrefWidth(250);
        
        patientIdField = new TextField();
        patientIdField.setPromptText("Enter Patient ID");
        patientIdField.setPrefWidth(250);
        
        diseaseField = new TextField();
        diseaseField.setPromptText("Enter Disease/Diagnosis");
        diseaseField.setPrefWidth(250);
        
        medicineField = new TextField();
        medicineField.setPromptText("Enter Prescribed Medicines");
        medicineField.setPrefWidth(250);
        
        // Assigned Doctor ComboBox
        assignedDoctorComboBox = new ComboBox<>();
        assignedDoctorComboBox.getItems().addAll(
            "Dr. Smith - Cardiology",
            "Dr. Johnson - Neurology",
            "Dr. Williams - Orthopedics",
            "Dr. Brown - Pediatrics",
            "Dr. Jones - General Medicine",
            "Dr. Garcia - Surgery",
            "Dr. Miller - Dermatology",
            "Dr. Davis - Ophthalmology"
        );
        assignedDoctorComboBox.setPromptText("Select Doctor");
        assignedDoctorComboBox.setPrefWidth(250);
        
        startDateField = new TextField();
        startDateField.setPromptText("YYYY-MM-DD");
        startDateField.setPrefWidth(250);
        
        durationField = new TextField();
        durationField.setPromptText("e.g., 7 days, 2 weeks");
        durationField.setPrefWidth(250);
        
        // Notes TextArea
        notesArea = new TextArea();
        notesArea.setPromptText("Enter treatment notes, instructions, etc.");
        notesArea.setPrefWidth(250);
        notesArea.setPrefHeight(100);
        notesArea.setWrapText(true);
        
        // Add labels and fields to grid
        int row = 0;
        
        form.add(createLabel("Treatment ID:"), 0, row);
        form.add(treatmentIdField, 1, row++);
        
        form.add(createLabel("Patient ID:"), 0, row);
        form.add(patientIdField, 1, row++);
        
        form.add(createLabel("Disease/Diagnosis:"), 0, row);
        form.add(diseaseField, 1, row++);
        
        form.add(createLabel("Medicine:"), 0, row);
        form.add(medicineField, 1, row++);
        
        form.add(createLabel("Assigned Doctor:"), 0, row);
        form.add(assignedDoctorComboBox, 1, row++);
        
        form.add(createLabel("Start Date:"), 0, row);
        form.add(startDateField, 1, row++);
        
        form.add(createLabel("Duration:"), 0, row);
        form.add(durationField, 1, row++);
        
        form.add(createLabel("Treatment Notes:"), 0, row);
        form.add(notesArea, 1, row++);
        
        // Additional fields
        form.add(createLabel("Treatment Type:"), 0, row);
        ComboBox<String> treatmentTypeComboBox = new ComboBox<>();
        treatmentTypeComboBox.getItems().addAll(
            "Medication",
            "Physical Therapy",
            "Surgery",
            "Counseling",
            "Rehabilitation",
            "Emergency Care",
            "Preventive Care",
            "Chronic Care"
        );
        treatmentTypeComboBox.setPromptText("Select Treatment Type");
        form.add(treatmentTypeComboBox, 1, row++);
        
        form.add(createLabel("Priority Level:"), 0, row);
        ComboBox<String> priorityComboBox = new ComboBox<>();
        priorityComboBox.getItems().addAll("Low", "Medium", "High", "Critical");
        priorityComboBox.setValue("Medium");
        form.add(priorityComboBox, 1, row++);
        
        form.add(createLabel("Room/Ward:"), 0, row);
        TextField roomField = new TextField();
        roomField.setPromptText("Room Number/Ward");
        form.add(roomField, 1, row);
        
        // Buttons
        HBox buttonBox = new HBox(15);
        buttonBox.setPadding(new Insets(20, 0, 0, 0));
        
        Button saveButton = new Button("Save Treatment");
        saveButton.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 20;");
        saveButton.setOnAction(e -> saveTreatment(treatmentTypeComboBox, priorityComboBox, roomField));
        
        Button clearButton = new Button("Clear Form");
        clearButton.setStyle("-fx-background-color: #95a5a6; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 20;");
        clearButton.setOnAction(e -> clearForm(treatmentTypeComboBox, priorityComboBox, roomField));
        
        buttonBox.getChildren().addAll(saveButton, clearButton);
        
        // Add components to container
        formContainer.getChildren().addAll(title, form, buttonBox);
    }
    
    private Label createLabel(String text) {
        Label label = new Label(text);
        label.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        return label;
    }
    
    private void saveTreatment(ComboBox<String> treatmentTypeComboBox, 
                              ComboBox<String> priorityComboBox, 
                              TextField roomField) {
        // Validate and save treatment data
        if (validateForm()) {
            System.out.println("=== TREATMENT ADDED ===");
            System.out.println("Treatment ID: " + treatmentIdField.getText());
            System.out.println("Patient ID: " + patientIdField.getText());
            System.out.println("Disease/Diagnosis: " + diseaseField.getText());
            System.out.println("Medicine: " + medicineField.getText());
            System.out.println("Assigned Doctor: " + assignedDoctorComboBox.getValue());
            System.out.println("Start Date: " + startDateField.getText());
            System.out.println("Duration: " + durationField.getText());
            System.out.println("Notes: " + notesArea.getText());
            System.out.println("Treatment Type: " + treatmentTypeComboBox.getValue());
            System.out.println("Priority: " + priorityComboBox.getValue());
            System.out.println("Room/Ward: " + roomField.getText());
            System.out.println("=== END ===");
            
            clearForm(treatmentTypeComboBox, priorityComboBox, roomField);
        } else {
            System.out.println("Please fill in all required fields.");
        }
    }
    
    private boolean validateForm() {
        return !treatmentIdField.getText().isEmpty() &&
               !patientIdField.getText().isEmpty() &&
               !diseaseField.getText().isEmpty() &&
               !medicineField.getText().isEmpty() &&
               assignedDoctorComboBox.getValue() != null;
    }
    
    private void clearForm(ComboBox<String> treatmentTypeComboBox, 
                          ComboBox<String> priorityComboBox, 
                          TextField roomField) {
        treatmentIdField.clear();
        patientIdField.clear();
        diseaseField.clear();
        medicineField.clear();
        assignedDoctorComboBox.setValue(null);
        startDateField.clear();
        durationField.clear();
        notesArea.clear();
        treatmentTypeComboBox.setValue(null);
        priorityComboBox.setValue("Medium");
        roomField.clear();
    }
    
    public VBox getForm() {
        return formContainer;
    }
}
