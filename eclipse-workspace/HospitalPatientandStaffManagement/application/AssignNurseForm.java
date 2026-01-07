package application;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class AssignNurseForm {
    
    private VBox formContainer;
    private TextField patientIdField;
    private ComboBox<String> nurseComboBox;
    private ComboBox<String> assignmentTypeComboBox;
    private CheckBox assistDoctorCheckBox;
    private ComboBox<String> doctorComboBox;
    private ComboBox<String> wardComboBox;
    private TextField notesField;
    
    public AssignNurseForm() {
        initializeForm();
    }
    
    private void initializeForm() {
        // Create form container
        formContainer = new VBox();
        formContainer.setSpacing(15);
        formContainer.setPadding(new Insets(20));
        
        // Title
        Label title = new Label("Assign Nurse to Patient");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        
        // Create form grid
        GridPane form = new GridPane();
        form.setVgap(12);
        form.setHgap(12);
        form.setPadding(new Insets(15));
        form.setStyle("-fx-background-color: white; -fx-border-color: #bdc3c7; -fx-border-radius: 5; -fx-border-width: 1;");
        
        // Initialize form fields
        patientIdField = new TextField();
        patientIdField.setPromptText("Enter Patient ID");
        patientIdField.setPrefWidth(220);
        
        // Nurse ComboBox
        nurseComboBox = new ComboBox<>();
        nurseComboBox.getItems().addAll(
            "Nurse Sarah Johnson - ICU",
            "Nurse Michael Brown - Emergency",
            "Nurse Emily Davis - Pediatrics",
            "Nurse Robert Wilson - Surgery",
            "Nurse Lisa Miller - Maternity",
            "Nurse David Taylor - Cardiology",
            "Nurse Jennifer Lee - Oncology",
            "Nurse James Anderson - General Ward"
        );
        nurseComboBox.setPromptText("Select Nurse");
        nurseComboBox.setPrefWidth(220);
        
        // Assignment Type ComboBox
        assignmentTypeComboBox = new ComboBox<>();
        assignmentTypeComboBox.getItems().addAll(
            "Primary Care",
            "Medication Administration",
            "Vital Signs Monitoring",
            "Post-Operative Care",
            "Wound Care",
            "Patient Education",
            "Emergency Response",
            "Shift Duty"
        );
        assignmentTypeComboBox.setPromptText("Select Assignment Type");
        assignmentTypeComboBox.setPrefWidth(220);
        
        // Assist with Doctor CheckBox
        assistDoctorCheckBox = new CheckBox("Assist with Doctor");
        assistDoctorCheckBox.setStyle("-fx-font-weight: bold;");
        assistDoctorCheckBox.setSelected(false);
        
        // Doctor ComboBox (enabled only when checkbox is checked)
        doctorComboBox = new ComboBox<>();
        doctorComboBox.getItems().addAll(
            "Dr. Smith - Cardiology",
            "Dr. Johnson - Neurology",
            "Dr. Williams - Orthopedics",
            "Dr. Brown - Pediatrics",
            "Dr. Jones - General Medicine"
        );
        doctorComboBox.setPromptText("Select Doctor to Assist");
        doctorComboBox.setPrefWidth(220);
        doctorComboBox.setDisable(true); // Initially disabled
        
        // Ward ComboBox
        wardComboBox = new ComboBox<>();
        wardComboBox.getItems().addAll(
            "ICU - Intensive Care Unit",
            "Emergency Room",
            "Pediatrics Ward",
            "Maternity Ward",
            "Surgery Ward",
            "Cardiology Ward",
            "Oncology Ward",
            "General Ward",
            "Isolation Ward",
            "Recovery Room"
        );
        wardComboBox.setPromptText("Select Ward");
        wardComboBox.setPrefWidth(220);
        
        notesField = new TextField();
        notesField.setPromptText("Special instructions or notes");
        notesField.setPrefWidth(220);
        
        // Add event listener for checkbox
        assistDoctorCheckBox.setOnAction(e -> {
            doctorComboBox.setDisable(!assistDoctorCheckBox.isSelected());
            if (!assistDoctorCheckBox.isSelected()) {
                doctorComboBox.setValue(null);
            }
        });
        
        // Add labels and fields to grid
        int row = 0;
        
        form.add(createSmallLabel("Patient ID:"), 0, row);
        form.add(patientIdField, 1, row++);
        
        form.add(createSmallLabel("Nurse:"), 0, row);
        form.add(nurseComboBox, 1, row++);
        
        form.add(createSmallLabel("Assignment Type:"), 0, row);
        form.add(assignmentTypeComboBox, 1, row++);
        
        form.add(createSmallLabel(""), 0, row);
        form.add(assistDoctorCheckBox, 1, row++);
        
        form.add(createSmallLabel("Doctor to Assist:"), 0, row);
        form.add(doctorComboBox, 1, row++);
        
        form.add(createSmallLabel("Ward:"), 0, row);
        form.add(wardComboBox, 1, row++);
        
        form.add(createSmallLabel("Notes:"), 0, row);
        form.add(notesField, 1, row++);
        
        // Additional small fields
        form.add(createSmallLabel("Shift:"), 0, row);
        ComboBox<String> shiftComboBox = new ComboBox<>();
        shiftComboBox.getItems().addAll("Morning", "Evening", "Night", "12-Hour", "24-Hour");
        shiftComboBox.setPromptText("Select Shift");
        shiftComboBox.setPrefWidth(220);
        form.add(shiftComboBox, 1, row++);
        
        form.add(createSmallLabel("Duration:"), 0, row);
        TextField durationField = new TextField();
        durationField.setPromptText("e.g., 3 days, 1 week");
        durationField.setPrefWidth(220);
        form.add(durationField, 1, row++);
        
        form.add(createSmallLabel("Priority:"), 0, row);
        ComboBox<String> priorityComboBox = new ComboBox<>();
        priorityComboBox.getItems().addAll("Low", "Medium", "High", "Critical");
        priorityComboBox.setValue("Medium");
        priorityComboBox.setPrefWidth(220);
        form.add(priorityComboBox, 1, row);
        
        // Buttons
        HBox buttonBox = new HBox(10);
        buttonBox.setPadding(new Insets(15, 0, 0, 0));
        
        Button assignButton = new Button("Assign Nurse");
        assignButton.setStyle("-fx-background-color: #9b59b6; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 8 15;");
        assignButton.setOnAction(e -> assignNurse(shiftComboBox, durationField, priorityComboBox));
        
        Button clearButton = new Button("Clear");
        clearButton.setStyle("-fx-background-color: #95a5a6; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 8 15;");
        clearButton.setOnAction(e -> clearForm(shiftComboBox, durationField, priorityComboBox));
        
        buttonBox.getChildren().addAll(assignButton, clearButton);
        
        // Add components to container
        formContainer.getChildren().addAll(title, form, buttonBox);
    }
    
    private Label createSmallLabel(String text) {
        Label label = new Label(text);
        label.setStyle("-fx-font-weight: bold; -fx-font-size: 13px;");
        return label;
    }
    
    private void assignNurse(ComboBox<String> shiftComboBox, TextField durationField, ComboBox<String> priorityComboBox) {
        // Validate and assign nurse
        if (validateForm()) {
            System.out.println("=== NURSE ASSIGNED ===");
            System.out.println("Patient ID: " + patientIdField.getText());
            System.out.println("Nurse: " + nurseComboBox.getValue());
            System.out.println("Assignment Type: " + assignmentTypeComboBox.getValue());
            System.out.println("Assist with Doctor: " + (assistDoctorCheckBox.isSelected() ? "Yes" : "No"));
            if (assistDoctorCheckBox.isSelected()) {
                System.out.println("Doctor to Assist: " + doctorComboBox.getValue());
            }
            System.out.println("Ward: " + wardComboBox.getValue());
            System.out.println("Notes: " + notesField.getText());
            System.out.println("Shift: " + shiftComboBox.getValue());
            System.out.println("Duration: " + durationField.getText());
            System.out.println("Priority: " + priorityComboBox.getValue());
            System.out.println("=== END ===");
            
            clearForm(shiftComboBox, durationField, priorityComboBox);
        } else {
            System.out.println("Please fill in all required fields.");
        }
    }
    
    private boolean validateForm() {
        return !patientIdField.getText().isEmpty() &&
               nurseComboBox.getValue() != null &&
               assignmentTypeComboBox.getValue() != null &&
               wardComboBox.getValue() != null;
    }
    
    private void clearForm(ComboBox<String> shiftComboBox, TextField durationField, ComboBox<String> priorityComboBox) {
        patientIdField.clear();
        nurseComboBox.setValue(null);
        assignmentTypeComboBox.setValue(null);
        assistDoctorCheckBox.setSelected(false);
        doctorComboBox.setValue(null);
        doctorComboBox.setDisable(true);
        wardComboBox.setValue(null);
        notesField.clear();
        shiftComboBox.setValue(null);
        durationField.clear();
        priorityComboBox.setValue("Medium");
    }
    
    public VBox getForm() {
        return formContainer;
    }
}