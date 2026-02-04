package application;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import java.util.List;
import java.time.LocalDate;

public class AssignNurseForm {
    private VBox formContainer;
    private TextField patientIdField;
    private TextField patientNameField;
    private ComboBox<String> nurseComboBox;
    private NurseDatabase nurseDatabase;
    private Button refreshButton;
    
    public AssignNurseForm() {
        nurseDatabase = NurseDatabase.getInstance();
        initializeForm();
    }
    
    private void initializeForm() {
        formContainer = new VBox();
        formContainer.setSpacing(20);
        formContainer.setPadding(new Insets(20));
        formContainer.setStyle("-fx-background-color: #f8f9fa;");
        
        // Title
        Label title = new Label("Assign Nurse to Patient");
        title.setFont(new Font("Arial", 28));
        title.setStyle("-fx-font-weight: bold; -fx-text-fill: #2c3e50; -fx-padding: 0 0 10 0;");
        
        // Create form grid
        GridPane form = new GridPane();
        form.setVgap(15);
        form.setHgap(20);
        form.setPadding(new Insets(25));
        form.setStyle("-fx-background-color: white; -fx-border-color: #dee2e6; -fx-border-radius: 8; -fx-border-width: 1;");
        
        // Initialize form fields
        patientIdField = createTextField(200, "Enter patient ID");
        patientNameField = createTextField(250, "Patient name (auto-filled)");
        patientNameField.setEditable(false);
        patientNameField.setStyle("-fx-background-color: #ecf0f1;");
        
        // Nurse ComboBox with refresh button
        nurseComboBox = new ComboBox<>();
        nurseComboBox.setPrefWidth(300);
        
        refreshButton = new Button("🔄");
        refreshButton.setStyle("-fx-background-color: #2ecc71; -fx-text-fill: white; -fx-font-weight: bold;");
        refreshButton.setTooltip(new Tooltip("Refresh nurses list"));
        refreshButton.setOnAction(e -> updateNurseComboBox());
        
        HBox nurseContainer = new HBox(10);
        nurseContainer.getChildren().addAll(nurseComboBox, refreshButton);
        
        // Add event listener for patient ID field
        patientIdField.setOnKeyReleased(e -> {
            String patientId = patientIdField.getText().trim();
            if (!patientId.isEmpty()) {
                String patientName = getPatientName(patientId);
                patientNameField.setText(patientName);
                
                // Change background color
                if (patientName.equals("Patient not found")) {
                    patientNameField.setStyle("-fx-background-color: #ffcccc;");
                } else {
                    patientNameField.setStyle("-fx-background-color: #d4edda;");
                }
            } else {
                patientNameField.clear();
                patientNameField.setStyle("-fx-background-color: #ecf0f1;");
            }
        });
        
        // Add search button next to patient ID
        Button searchPatientButton = new Button("🔍");
        searchPatientButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-weight: bold;");
        searchPatientButton.setOnAction(e -> {
            String patientId = patientIdField.getText().trim();
            if (!patientId.isEmpty()) {
                String patientName = getPatientName(patientId);
                patientNameField.setText(patientName);
                
                if (patientName.equals("Patient not found")) {
                    showAlert("Patient Not Found", "No patient found with ID: " + patientId);
                }
            }
        });
        
        HBox patientIdContainer = new HBox(5);
        patientIdContainer.getChildren().addAll(patientIdField, searchPatientButton);
        
        // Add labels and fields to grid
        int row = 0;
        
        // Patient Information
        form.add(createLabel("Patient ID:*"), 0, row);
        form.add(patientIdContainer, 1, row);
        row++;
        
        form.add(createLabel("Patient Name:"), 0, row);
        form.add(patientNameField, 1, row);
        row++;
        
        form.add(createLabel("Assign Nurse:*"), 0, row);
        form.add(nurseContainer, 1, row);
        row++;
        
        // Assignment Date
        form.add(createLabel("Assignment Date:"), 0, row);
        TextField dateField = createTextField(150, LocalDate.now().toString());
        dateField.setEditable(false);
        dateField.setStyle("-fx-background-color: #ecf0f1;");
        form.add(dateField, 1, row);
        row++;
        
        // Form validation message
        Label validationLabel = new Label();
        validationLabel.setStyle("-fx-text-fill: #e74c3c; -fx-font-size: 12px;");
        form.add(validationLabel, 1, row);
        row++;
        
        // Button container
        HBox buttonContainer = new HBox(15);
        buttonContainer.setPadding(new Insets(20, 0, 0, 0));
        
        // Assign button - MAIN BUTTON
        Button assignButton = new Button("Assign Nurse");
        assignButton.setStyle("-fx-background-color: #e67e22; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 25; -fx-font-size: 14px; -fx-background-radius: 5;");
        assignButton.setOnAction(e -> {
            if (assignNurseToPatient(validationLabel)) {
                showSuccessAlert();
                updateNurseComboBox(); // Refresh the list after assignment
            }
        });
        
        // Clear button
        Button clearButton = new Button("Clear Form");
        clearButton.setStyle("-fx-background-color: #95a5a6; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 25; -fx-font-size: 14px; -fx-background-radius: 5;");
        clearButton.setOnAction(e -> {
            clearForm();
            validationLabel.setText("");
        });
        
        // View assignments button
        Button viewAssignmentsButton = new Button("View Assignments");
        viewAssignmentsButton.setStyle("-fx-background-color: #9b59b6; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 25; -fx-font-size: 14px; -fx-background-radius: 5;");
        viewAssignmentsButton.setOnAction(e -> showAllAssignments());
        
        buttonContainer.getChildren().addAll(assignButton, clearButton, viewAssignmentsButton);
        form.add(buttonContainer, 0, row, 2, 1);
        
        // Add components to container
        formContainer.getChildren().addAll(title, form);
        
        // Initialize the nurse combobox
        updateNurseComboBox();
    }
    
    // Method to update nurse combobox with available nurses
    private void updateNurseComboBox() {
        nurseComboBox.getItems().clear();
        
        List<Nurses> availableNurses = nurseDatabase.getAvailableNurses();
        
        if (availableNurses.isEmpty()) {
            nurseComboBox.getItems().add("No available nurses");
            nurseComboBox.setValue("No available nurses");
            nurseComboBox.setPromptText("No nurses available");
        } else {
            for (Nurses nurse : availableNurses) {
                // Format: "Nurse Name (Department) - ID: NUR-001"
                String displayText = String.format("%s (%s) - ID: %s", 
                    nurse.getName(),
                    nurse.getDepartment(),
                    nurse.getId()
                );
                nurseComboBox.getItems().add(displayText);
            }
            nurseComboBox.setPromptText("Select nurse to assign");
            
            // Sort alphabetically
            nurseComboBox.getItems().sort(String::compareTo);
        }
    }
    
    // Method to get patient name
    private String getPatientName(String patientId) {
        PatientDatabase patientDatabase = PatientDatabase.getInstance();
        List<Patients> patients = patientDatabase.searchById(patientId);
        
        if (!patients.isEmpty()) {
            Patients patient = patients.get(0);
            System.out.println("Patient found: " + patient.getName() + " (ID: " + patient.getId() + ")");
            return patient.getName();
        } else {
            return "Patient not found";
        }
    }
    
    // Helper method to extract nurse ID from combobox selection
    private String extractNurseId(String displayText) {
        if (displayText == null || displayText.equals("No available nurses")) {
            return null;
        }
        
        try {
            // Format: "Nurse Name (Department) - ID: NUR-001"
            if (displayText.contains("ID: ")) {
                String[] parts = displayText.split("ID: ");
                if (parts.length > 1) {
                    return parts[1].trim();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    // Helper method to extract nurse name from combobox selection
    private String extractNurseName(String displayText) {
        if (displayText == null || displayText.equals("No available nurses")) {
            return null;
        }
        
        try {
            // Format: "Nurse Name (Department) - ID: NUR-001"
            String[] parts = displayText.split(" \\(");
            if (parts.length > 0) {
                return parts[0].trim();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return displayText;
    }
    
    // Helper method to extract nurse department from combobox selection
    private String extractNurseDepartment(String displayText) {
        if (displayText == null || displayText.equals("No available nurses")) {
            return null;
        }
        
        try {
            // Format: "Nurse Name (Department) - ID: NUR-001"
            String[] parts = displayText.split(" \\(");
            if (parts.length > 1) {
                // Remove the ")" and everything after
                String departmentPart = parts[1];
                if (departmentPart.contains(") - ID: ")) {
                    return departmentPart.split("\\) - ID: ")[0].trim();
                }
                return departmentPart.replace(")", "").trim();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    // MAIN METHOD: Assign nurse to patient
    private boolean assignNurseToPatient(Label validationLabel) {
        // Validate patient
        String patientId = patientIdField.getText().trim();
        String patientName = patientNameField.getText();
        
        if (patientName.equals("Patient not found") || patientName.isEmpty()) {
            validationLabel.setText("Error: Patient not found. Please enter a valid Patient ID.");
            patientIdField.setStyle("-fx-border-color: #ff0000; -fx-border-width: 2px;");
            return false;
        }
        
        // Validate nurse selection
        String selectedNurse = nurseComboBox.getValue();
        if (selectedNurse == null || selectedNurse.equals("No available nurses")) {
            validationLabel.setText("Please select an available nurse.");
            nurseComboBox.setStyle("-fx-border-color: #ff0000; -fx-border-width: 2px;");
            return false;
        }
        
        try {
            // Extract nurse information
            String nurseId = extractNurseId(selectedNurse);
            String nurseName = extractNurseName(selectedNurse);
            String department = extractNurseDepartment(selectedNurse);
            
            if (nurseId == null || nurseName == null) {
                validationLabel.setText("Invalid nurse selection.");
                return false;
            }
            
            // Get the nurse object
            Nurses nurse = findNurseById(nurseId);
            if (nurse == null) {
                validationLabel.setText("Nurse not found in database.");
                return false;
            }
            
            // Assign nurse to patient
            boolean assignmentSuccess = nurseDatabase.assignNurseToPatient(nurseId, patientId);
            
            if (assignmentSuccess) {
                // Display success message
                System.out.println("\n" + "=".repeat(60));
                System.out.println("NURSE ASSIGNED SUCCESSFULLY");
                System.out.println("=".repeat(60));
                System.out.println("Patient ID: " + patientId);
                System.out.println("Patient Name: " + patientName);
                System.out.println("Nurse ID: " + nurseId);
                System.out.println("Nurse Name: " + nurseName);
                System.out.println("Department: " + department);
                System.out.println("Assignment Date: " + LocalDate.now());
                System.out.println("Current Patients: " + nurse.getCurrentPatientsCount() + "/" + nurse.getMaxPatients());
                System.out.println("Status: Active");
                System.out.println("=".repeat(60));
                
                // Print database status
                printNurseDatabaseStatus();
                
                return true;
            } else {
                validationLabel.setText("Failed to assign nurse. The nurse might be at capacity.");
                return false;
            }
            
        } catch (Exception e) {
            validationLabel.setText("An error occurred: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    // Helper method to find nurse by ID
    private Nurses findNurseById(String nurseId) {
        List<Nurses> allNurses = nurseDatabase.getAllNurses();
        for (Nurses nurse : allNurses) {
            if (nurse.getId().equals(nurseId)) {
                return nurse;
            }
        }
        return null;
    }
    
    // Method to print nurse database status
    private void printNurseDatabaseStatus() {
        List<Nurses> allNurses = nurseDatabase.getAllNurses();
        int availableNurses = nurseDatabase.getAvailableNurses().size();
        int totalAssignments = 0;
        
        for (Nurses nurse : allNurses) {
            totalAssignments += nurse.getCurrentPatientsCount();
        }
        
        System.out.println("\n📊 NURSE DATABASE STATUS");
        System.out.println("Total Nurses: " + allNurses.size());
        System.out.println("Available Nurses: " + availableNurses);
        System.out.println("Total Patient Assignments: " + totalAssignments);
    }
    
    private void showAllAssignments() {
        List<Nurses> allNurses = nurseDatabase.getAllNurses();
        int totalAssignments = 0;
        int nursesWithAssignments = 0;
        
        StringBuilder assignmentsInfo = new StringBuilder();
        assignmentsInfo.append("CURRENT NURSE ASSIGNMENTS\n");
        assignmentsInfo.append("=".repeat(60)).append("\n\n");
        
        for (Nurses nurse : allNurses) {
            int patientCount = nurse.getCurrentPatientsCount();
            if (patientCount > 0) {
                nursesWithAssignments++;
                totalAssignments += patientCount;
                
                assignmentsInfo.append("Nurse: ").append(nurse.getName())
                              .append(" (ID: ").append(nurse.getId()).append(")\n");
                assignmentsInfo.append("Patients Assigned: ").append(patientCount)
                              .append("/").append(nurse.getMaxPatients()).append("\n");
                assignmentsInfo.append("-".repeat(40)).append("\n");
            }
        }
        
        if (totalAssignments == 0) {
            assignmentsInfo.append("No active nurse assignments found.\n");
        } else {
            assignmentsInfo.append("\n📊 SUMMARY:\n");
            assignmentsInfo.append("Nurses with Assignments: ").append(nursesWithAssignments).append("/").append(allNurses.size()).append("\n");
            assignmentsInfo.append("Total Patient Assignments: ").append(totalAssignments).append("\n");
        }
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Current Assignments");
        alert.setHeaderText("Nurse-Patient Assignments");
        alert.setContentText(assignmentsInfo.toString());
        alert.showAndWait();
    }
    
    private void clearForm() {
        patientIdField.clear();
        patientNameField.clear();
        patientNameField.setStyle("-fx-background-color: #ecf0f1;");
        nurseComboBox.setValue(null);
        patientIdField.setStyle("");
        nurseComboBox.setStyle("");
    }
    
    private void showSuccessAlert() {
        int totalNurses = nurseDatabase.getAllNurses().size();
        int availableNurses = nurseDatabase.getAvailableNurses().size();
        int totalAssignments = 0;
        
        for (Nurses nurse : nurseDatabase.getAllNurses()) {
            totalAssignments += nurse.getCurrentPatientsCount();
        }
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("✅ Nurse Assigned Successfully");
        
        String content = String.format(
            "Nurse has been successfully assigned to the patient.\n\n" +
            "Database Status:\n" +
            "• Total Nurses: %d\n" +
            "• Available Nurses: %d\n" +
            "• Total Assignments: %d\n\n" +
            "You can view all assignments by clicking 'View Assignments'.",
            totalNurses,
            availableNurses,
            totalAssignments
        );
        
        alert.setContentText(content);
        alert.showAndWait();
    }
    
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    private TextField createTextField(double width, String prompt) {
        TextField field = new TextField();
        field.setPrefWidth(width);
        field.setPromptText(prompt);
        return field;
    }
    
    private Label createLabel(String text) {
        Label label = new Label(text);
        label.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        return label;
    }
    
    public VBox getForm() {
        return formContainer;
    }
}