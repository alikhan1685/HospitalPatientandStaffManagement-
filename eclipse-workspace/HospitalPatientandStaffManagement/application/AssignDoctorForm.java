package application;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class AssignDoctorForm {
    
    private VBox formContainer;
    private TextField patientIdField;
    private ComboBox<String> doctorComboBox;
    private ComboBox<String> assignmentTypeComboBox;
    private TextField notesField;
    
    public AssignDoctorForm() {
        initializeForm();
    }
    
    private void initializeForm() {
        // Create form container
        formContainer = new VBox();
        formContainer.setSpacing(15);
        formContainer.setPadding(new Insets(20));
        
        // Title
        Label title = new Label("Assign Doctor to Patient");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        
        // Create form grid - SMALLER FORM
        GridPane form = new GridPane();
        form.setVgap(12);
        form.setHgap(12);
        form.setPadding(new Insets(15));
        form.setStyle("-fx-background-color: white; -fx-border-color: #bdc3c7; -fx-border-radius: 5; -fx-border-width: 1;");
        
        // Initialize form fields
        patientIdField = new TextField();
        patientIdField.setPromptText("Enter Patient ID");
        patientIdField.setPrefWidth(200);
        
        // Doctor ComboBox - Sample doctors
        doctorComboBox = new ComboBox<>();
        doctorComboBox.getItems().addAll(
            "Dr. Smith - Cardiology",
            "Dr. Johnson - Neurology",
            "Dr. Williams - Orthopedics",
            "Dr. Brown - Pediatrics",
            "Dr. Jones - General Medicine",
            "Dr. Garcia - Surgery",
            "Dr. Miller - Dermatology",
            "Dr. Davis - Ophthalmology"
        );
        doctorComboBox.setPromptText("Select Doctor");
        doctorComboBox.setPrefWidth(200);
        
        // Assignment Type ComboBox
        assignmentTypeComboBox = new ComboBox<>();
        assignmentTypeComboBox.getItems().addAll(
            "Primary Care",
            "Consultation",
            "Emergency",
            "Follow-up",
            "Specialist Referral",
            "Surgical Team",
            "ICU Team"
        );
        assignmentTypeComboBox.setPromptText("Select Assignment Type");
        assignmentTypeComboBox.setPrefWidth(200);
        
        notesField = new TextField();
        notesField.setPromptText("Optional notes");
        notesField.setPrefWidth(200);
        
        // Add labels and fields to grid
        int row = 0;
        
        form.add(createSmallLabel("Patient ID:"), 0, row);
        form.add(patientIdField, 1, row++);
        
        form.add(createSmallLabel("Doctor:"), 0, row);
        form.add(doctorComboBox, 1, row++);
        
        form.add(createSmallLabel("Assignment Type:"), 0, row);
        form.add(assignmentTypeComboBox, 1, row++);
        
        form.add(createSmallLabel("Notes:"), 0, row);
        form.add(notesField, 1, row++);
        
        // Additional small fields
        form.add(createSmallLabel("Start Date:"), 0, row);
        TextField startDateField = new TextField();
        startDateField.setPromptText("YYYY-MM-DD");
        startDateField.setPrefWidth(200);
        form.add(startDateField, 1, row++);
        
        form.add(createSmallLabel("Priority:"), 0, row);
        ComboBox<String> priorityComboBox = new ComboBox<>();
        priorityComboBox.getItems().addAll("Normal", "Urgent", "Emergency");
        priorityComboBox.setValue("Normal");
        priorityComboBox.setPrefWidth(200);
        form.add(priorityComboBox, 1, row);
        
        // Buttons
        HBox buttonBox = new HBox(10);
        buttonBox.setPadding(new Insets(15, 0, 0, 0));
        
        Button assignButton = new Button("Assign Doctor");
        assignButton.setStyle("-fx-background-color: #e67e22; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 8 15;");
        assignButton.setOnAction(e -> assignDoctor(startDateField, priorityComboBox));
        
        Button clearButton = new Button("Clear");
        clearButton.setStyle("-fx-background-color: #95a5a6; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 8 15;");
        clearButton.setOnAction(e -> clearForm(startDateField, priorityComboBox));
        
        buttonBox.getChildren().addAll(assignButton, clearButton);
        
        // Add components to container
        formContainer.getChildren().addAll(title, form, buttonBox);
    }
    
    private Label createSmallLabel(String text) {
        Label label = new Label(text);
        label.setStyle("-fx-font-weight: bold; -fx-font-size: 13px;");
        return label;
    }
    
    private void assignDoctor(TextField startDateField, ComboBox<String> priorityComboBox) {
        // Validate and assign doctor
        if (validateForm()) {
            System.out.println("=== DOCTOR ASSIGNED ===");
            System.out.println("Patient ID: " + patientIdField.getText());
            System.out.println("Doctor: " + doctorComboBox.getValue());
            System.out.println("Assignment Type: " + assignmentTypeComboBox.getValue());
            System.out.println("Notes: " + notesField.getText());
            System.out.println("Start Date: " + startDateField.getText());
            System.out.println("Priority: " + priorityComboBox.getValue());
            System.out.println("=== END ===");
            
            clearForm(startDateField, priorityComboBox);
        } else {
            System.out.println("Please fill in all required fields.");
        }
    }
    
    private boolean validateForm() {
        return !patientIdField.getText().isEmpty() &&
               doctorComboBox.getValue() != null &&
               assignmentTypeComboBox.getValue() != null;
    }
    
    private void clearForm(TextField startDateField, ComboBox<String> priorityComboBox) {
        patientIdField.clear();
        doctorComboBox.setValue(null);
        assignmentTypeComboBox.setValue(null);
        notesField.clear();
        startDateField.clear();
        priorityComboBox.setValue("Normal");
    }
    
    public VBox getForm() {
        return formContainer;
    }
}