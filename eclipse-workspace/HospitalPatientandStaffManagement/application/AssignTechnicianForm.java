package application;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class AssignTechnicianForm {
    
    private VBox formContainer;
    private TextField patientIdField;
    private ComboBox<String> technicianComboBox;
    private ComboBox<String> equipmentComboBox;
    private ComboBox<String> testTypeComboBox;
    private TextField notesField;
    
    public AssignTechnicianForm() {
        initializeForm();
    }
    
    private void initializeForm() {
        // Create form container
        formContainer = new VBox();
        formContainer.setSpacing(15);
        formContainer.setPadding(new Insets(20));
        
        // Title
        Label title = new Label("Assign Technician for Tests/Procedures");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        
        // Create form grid - SMALLER FORM
        GridPane form = new GridPane();
        form.setVgap(10);
        form.setHgap(10);
        form.setPadding(new Insets(15));
        form.setStyle("-fx-background-color: white; -fx-border-color: #bdc3c7; -fx-border-radius: 5; -fx-border-width: 1;");
        
        // Initialize form fields
        patientIdField = new TextField();
        patientIdField.setPromptText("Enter Patient ID");
        patientIdField.setPrefWidth(200);
        
        // Technician ComboBox
        technicianComboBox = new ComboBox<>();
        technicianComboBox.getItems().addAll(
            "Tech. Alex Morgan - Lab",
            "Tech. Brian Wilson - Radiology",
            "Tech. Carol Davis - Surgery",
            "Tech. David Lee - Cardiology",
            "Tech. Emma Taylor - Respiratory",
            "Tech. Frank Miller - Emergency",
            "Tech. Grace Brown - Phlebotomy"
        );
        technicianComboBox.setPromptText("Select Technician");
        technicianComboBox.setPrefWidth(200);
        
        // Equipment ComboBox
        equipmentComboBox = new ComboBox<>();
        equipmentComboBox.getItems().addAll(
            "X-Ray Machine",
            "MRI Scanner",
            "CT Scanner",
            "Ultrasound Machine",
            "ECG Machine",
            "Blood Analyzer",
            "Microscope",
            "Ventilator",
            "Defibrillator",
            "Surgical Instruments"
        );
        equipmentComboBox.setPromptText("Select Equipment");
        equipmentComboBox.setPrefWidth(200);
        
        // Test Type ComboBox
        testTypeComboBox = new ComboBox<>();
        testTypeComboBox.getItems().addAll(
            "Blood Test",
            "X-Ray",
            "MRI Scan",
            "CT Scan",
            "Ultrasound",
            "ECG",
            "Biopsy",
            "Culture Test",
            "Urinalysis",
            "Pulmonary Function Test"
        );
        testTypeComboBox.setPromptText("Select Test/Procedure");
        testTypeComboBox.setPrefWidth(200);
        
        notesField = new TextField();
        notesField.setPromptText("Special instructions");
        notesField.setPrefWidth(200);
        
        // Add labels and fields to grid
        int row = 0;
        
        form.add(createSmallLabel("Patient ID:"), 0, row);
        form.add(patientIdField, 1, row++);
        
        form.add(createSmallLabel("Technician:"), 0, row);
        form.add(technicianComboBox, 1, row++);
        
        form.add(createSmallLabel("Test/Procedure:"), 0, row);
        form.add(testTypeComboBox, 1, row++);
        
        form.add(createSmallLabel("Equipment:"), 0, row);
        form.add(equipmentComboBox, 1, row++);
        
        form.add(createSmallLabel("Notes:"), 0, row);
        form.add(notesField, 1, row++);
        
        // Additional small fields
        form.add(createSmallLabel("Urgency:"), 0, row);
        ComboBox<String> urgencyComboBox = new ComboBox<>();
        urgencyComboBox.getItems().addAll("Routine", "Urgent", "Emergency");
        urgencyComboBox.setValue("Routine");
        urgencyComboBox.setPrefWidth(200);
        form.add(urgencyComboBox, 1, row++);
        
        form.add(createSmallLabel("Location:"), 0, row);
        TextField locationField = new TextField();
        locationField.setPromptText("e.g., Lab Room 3, Radiology");
        locationField.setPrefWidth(200);
        form.add(locationField, 1, row);
        
        // Buttons
        HBox buttonBox = new HBox(10);
        buttonBox.setPadding(new Insets(15, 0, 0, 0));
        
        Button assignButton = new Button("Assign Technician");
        assignButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 8 15;");
        assignButton.setOnAction(e -> assignTechnician(urgencyComboBox, locationField));
        
        Button clearButton = new Button("Clear");
        clearButton.setStyle("-fx-background-color: #95a5a6; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 8 15;");
        clearButton.setOnAction(e -> clearForm(urgencyComboBox, locationField));
        
        buttonBox.getChildren().addAll(assignButton, clearButton);
        
        // Add components to container
        formContainer.getChildren().addAll(title, form, buttonBox);
    }
    
    private Label createSmallLabel(String text) {
        Label label = new Label(text);
        label.setStyle("-fx-font-weight: bold; -fx-font-size: 12px;");
        return label;
    }
    
    private void assignTechnician(ComboBox<String> urgencyComboBox, TextField locationField) {
        // Validate and assign technician
        if (validateForm()) {
            System.out.println("=== TECHNICIAN ASSIGNED ===");
            System.out.println("Patient ID: " + patientIdField.getText());
            System.out.println("Technician: " + technicianComboBox.getValue());
            System.out.println("Test/Procedure: " + testTypeComboBox.getValue());
            System.out.println("Equipment: " + equipmentComboBox.getValue());
            System.out.println("Notes: " + notesField.getText());
            System.out.println("Urgency: " + urgencyComboBox.getValue());
            System.out.println("Location: " + locationField.getText());
            System.out.println("=== END ===");
            
            clearForm(urgencyComboBox, locationField);
        } else {
            System.out.println("Please fill in all required fields.");
        }
    }
    
    private boolean validateForm() {
        return !patientIdField.getText().isEmpty() &&
               technicianComboBox.getValue() != null &&
               testTypeComboBox.getValue() != null;
    }
    
    private void clearForm(ComboBox<String> urgencyComboBox, TextField locationField) {
        patientIdField.clear();
        technicianComboBox.setValue(null);
        testTypeComboBox.setValue(null);
        equipmentComboBox.setValue(null);
        notesField.clear();
        urgencyComboBox.setValue("Routine");
        locationField.clear();
    }
    
    public VBox getForm() {
        return formContainer;
    }
}