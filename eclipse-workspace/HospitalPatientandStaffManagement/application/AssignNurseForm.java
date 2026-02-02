package application;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import java.util.List;

public class AssignNurseForm {
    private VBox formContainer;
    private TextField patientIdField;
    private TextField patientNameField;
    private TextField nurseIdField;
    private TextField nurseNameField;
    private ComboBox<String> nurseComboBox;
    private TextArea assignmentDetailsArea;
    private NurseDatabase nurseDatabase;  
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
        title.setStyle("-fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        
        // Create form grid
        GridPane form = new GridPane();
        form.setVgap(15);
        form.setHgap(20);
        form.setPadding(new Insets(25));
        form.setStyle("-fx-background-color: white; -fx-border-color: #dee2e6; -fx-border-radius: 8; -fx-border-width: 1;");
        
        // Patient Information section
        Label patientSection = new Label("PATIENT INFORMATION");
        patientSection.setStyle("-fx-font-weight: bold; -fx-font-size: 16px; -fx-text-fill: #2c3e50; -fx-padding: 0 0 10 0;");
        form.add(patientSection, 0, 0, 4, 1);
        
        int row = 1;
        
        // Patient ID field
        form.add(createLabel("Patient ID:*"), 0, row);
        patientIdField = createTextField(150, "Enter patient ID");
        form.add(patientIdField, 1, row);
        
        Button searchPatientButton = new Button("🔍 Search Patient");
        searchPatientButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 5 10;");
        searchPatientButton.setOnAction(e -> searchPatient());
        
        HBox patientIdBox = new HBox(5);
        patientIdBox.getChildren().addAll(patientIdField, searchPatientButton);
        form.add(patientIdBox, 1, row, 3, 1);
        row++;
        
        form.add(createLabel("Patient Name:"), 0, row);
        patientNameField = createTextField(250, "Patient name (auto-filled)");
        patientNameField.setEditable(false);
        patientNameField.setStyle("-fx-background-color: #ecf0f1;");
        form.add(patientNameField, 1, row, 3, 1);
        row++;
        
        // Add some spacing
        form.add(new Label(""), 0, row);
        row++;
        
        // Nurse Information section
        Label nurseSection = new Label("NURSE INFORMATION");
        nurseSection.setStyle("-fx-font-weight: bold; -fx-font-size: 16px; -fx-text-fill: #2c3e50; -fx-padding: 10 0 10 0;");
        form.add(nurseSection, 0, row, 4, 1);
        row++;
        
        // Nurse ID and Name fields
        form.add(createLabel("Nurse ID:*"), 0, row);
        nurseIdField = createTextField(150, "Nurse ID (auto-filled)");
        nurseIdField.setEditable(false);
        nurseIdField.setStyle("-fx-background-color: #ecf0f1;");
        form.add(nurseIdField, 1, row);
        
        form.add(createLabel("Nurse Name:"), 2, row);
        nurseNameField = createTextField(250, "Nurse name (auto-filled)");
        nurseNameField.setEditable(false);
        nurseNameField.setStyle("-fx-background-color: #ecf0f1;");
        form.add(nurseNameField, 3, row);
        row++;
        
        // Nurse selection ComboBox
        form.add(createLabel("Select Nurse:*"), 0, row);
        nurseComboBox = new ComboBox<>();
        updateNurseComboBox();
        nurseComboBox.setPromptText("Select a nurse");
        nurseComboBox.setPrefWidth(400);
        nurseComboBox.setOnAction(e -> {
            String selected = nurseComboBox.getValue();
            if (selected != null && !selected.isEmpty() && !selected.contains("No available")) {
                // Extract nurse ID and name from selection
                String[] parts = selected.split(" - ");
                if (parts.length >= 2) {
                    nurseIdField.setText(parts[0].trim());
                    
                    // Extract name from display text
                    String namePart = parts[1].split(" \\| ")[0];
                    nurseNameField.setText(namePart.trim());
                    
                    // Set background color to indicate selection
                    nurseIdField.setStyle("-fx-background-color: #d4edda;");
                    nurseNameField.setStyle("-fx-background-color: #d4edda;");
                }
            }
        });
        
        HBox nurseSelectionBox = new HBox(10);
        nurseSelectionBox.getChildren().addAll(nurseComboBox);
        form.add(nurseSelectionBox, 1, row, 3, 1);
        row++;
        
        // Show available nurses button
        Button showAvailableNursesButton = new Button("📋 View Available Nurses List");
        showAvailableNursesButton.setStyle("-fx-background-color: #2ecc71; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 8 15;");
        showAvailableNursesButton.setOnAction(e -> showAvailableNurses());
        form.add(showAvailableNursesButton, 1, row, 3, 1);
        row++;
        
        // Add some spacing
        form.add(new Label(""), 0, row);
        row++;
        
        // Assignment Details section
        Label assignmentSection = new Label("ASSIGNMENT DETAILS");
        assignmentSection.setStyle("-fx-font-weight: bold; -fx-font-size: 16px; -fx-text-fill: #2c3e50; -fx-padding: 10 0 10 0;");
        form.add(assignmentSection, 0, row, 4, 1);
        row++;
        
        // Assignment details area
        form.add(createLabel("Assignment Details:*"), 0, row);
        assignmentDetailsArea = new TextArea();
        assignmentDetailsArea.setPrefRowCount(4);
        assignmentDetailsArea.setPrefWidth(400);
        assignmentDetailsArea.setPromptText("Enter assignment details (reason, duration, special instructions)...");
        assignmentDetailsArea.setWrapText(true);
        form.add(assignmentDetailsArea, 1, row, 3, 1);
        row++;
        
        // Duration and Priority
        form.add(createLabel("Duration:*"), 0, row);
        ComboBox<String> durationComboBox = new ComboBox<>();
        durationComboBox.getItems().addAll(
            "1 day",
            "3 days", 
            "1 week",
            "2 weeks",
            "1 month",
            "Until discharge",
            "Continuous care"
        );
        durationComboBox.setValue("1 week");
        durationComboBox.setPrefWidth(150);
        form.add(durationComboBox, 1, row);
        
        form.add(createLabel("Priority:*"), 2, row);
        ComboBox<String> priorityComboBox = new ComboBox<>();
        priorityComboBox.getItems().addAll("Normal", "High", "Emergency", "Critical");
        priorityComboBox.setValue("Normal");
        priorityComboBox.setPrefWidth(150);
        form.add(priorityComboBox, 3, row);
        row++;
        
        // Assignment date (auto-generated)
        form.add(createLabel("Assignment Date:"), 0, row);
        TextField dateField = createTextField(150, java.time.LocalDate.now().toString());
        dateField.setEditable(false);
        dateField.setStyle("-fx-background-color: #ecf0f1;");
        form.add(dateField, 1, row);
        row++;
        
        // Form validation message
        Label validationLabel = new Label();
        validationLabel.setStyle("-fx-text-fill: #e74c3c; -fx-font-size: 12px;");
        form.add(validationLabel, 1, row, 3, 1);
        
        // Button container
        HBox buttonContainer = new HBox(15);
        buttonContainer.setPadding(new Insets(20, 0, 0, 0));
        
        // Assign button
        Button assignButton = new Button("👩‍⚕️ Assign Nurse");
        assignButton.setStyle("-fx-background-color: #e67e22; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 25; -fx-font-size: 14px; -fx-background-radius: 5;");
        assignButton.setOnAction(e -> {
            if (assignNurseToPatient(durationComboBox, priorityComboBox, dateField, validationLabel)) {
                showSuccessAlert();
            }
        });
        
        // Clear button
        Button clearButton = new Button("Clear Form");
        clearButton.setStyle("-fx-background-color: #95a5a6; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 25; -fx-font-size: 14px; -fx-background-radius: 5;");
        clearButton.setOnAction(e -> {
            clearForm();
            validationLabel.setText("");
        });
        
        // View all assignments button
        Button viewAssignmentsButton = new Button("View All Assignments");
        viewAssignmentsButton.setStyle("-fx-background-color: #9b59b6; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 25; -fx-font-size: 14px; -fx-background-radius: 5;");
        viewAssignmentsButton.setOnAction(e -> showAllAssignments());
        
        buttonContainer.getChildren().addAll(assignButton, clearButton, viewAssignmentsButton);
        formContainer.getChildren().addAll(title, form, buttonContainer);
    }
    
    private void updateNurseComboBox() {
        nurseComboBox.getItems().clear();
        List<Nurses> availableNurses = nurseDatabase.getAvailableNurses();
        
        for (Nurses nurse : availableNurses) {
            // Use getId() method to get nurse ID
            String displayText = String.format("%s - %s | Department: %s | Patients: %d/%d | Shift: %s | Status: %s",
                nurse.getId(),  // This should work if Nurses class has getId() method
                nurse.getName(),
                nurse.getDepartment(),
                nurse.getCurrentPatientsCount(),
                nurse.getMaxPatients(),
                nurse.getShift(),
                nurse.getStatus()
            );
            nurseComboBox.getItems().add(displayText);
        }
        
        if (availableNurses.isEmpty()) {
            nurseComboBox.getItems().add("No available nurses - all at capacity");
            nurseComboBox.setDisable(true);
        } else {
            nurseComboBox.setDisable(false);
        }
        
        // Clear ID and name fields
        nurseIdField.clear();
        nurseNameField.clear();
        nurseIdField.setStyle("-fx-background-color: #ecf0f1;");
        nurseNameField.setStyle("-fx-background-color: #ecf0f1;");
    }
    
    private void searchPatient() {
        String patientId = patientIdField.getText().trim();
        if (!patientId.isEmpty()) {
            // Search patient in database
            PatientDatabase patientDb = PatientDatabase.getInstance();
            List<Patients> patients = patientDb.searchById(patientId);
            
            if (!patients.isEmpty()) {
                Patients patient = patients.get(0);
                patientNameField.setText(patient.getName());
                patientNameField.setStyle("-fx-background-color: #d4edda;"); // Green background
                
                // Show patient info
                System.out.println("Found patient: " + patient.getName() + " (ID: " + patient.getId() + ")");
            } else {
                patientNameField.setText("Patient not found");
                patientNameField.setStyle("-fx-background-color: #ffcccc;"); // Red background
            }
        }
    }
  
    private void showAvailableNurses() {
        List<Nurses> availableNurses = nurseDatabase.getAvailableNurses();
        
        StringBuilder nursesInfo = new StringBuilder();
        nursesInfo.append("AVAILABLE NURSES FOR ASSIGNMENT\n");
        nursesInfo.append("=".repeat(80)).append("\n\n");
        
        if (availableNurses.isEmpty()) {
            nursesInfo.append("No nurses currently available. All nurses are at capacity.\n");
        } else {
            nursesInfo.append(String.format("%-8s | %-20s | %-15s | %-12s | %-15s | %s\n",
                "ID", "Name", "Department", "Patients", "Shift", "Status"));
            nursesInfo.append("-".repeat(80)).append("\n");
           
            for (Nurses nurse : availableNurses) {
                nursesInfo.append(String.format("%-8s | %-20s | %-15s | %3d/%-8d | %-15s | %s\n",
                    nurse.getId(),  // Use getId() method
                    nurse.getName(),
                    nurse.getDepartment(),
                    nurse.getCurrentPatientsCount(),
                    nurse.getMaxPatients(),
                    getShiftAbbreviation(nurse.getShift()),
                    nurse.getStatus()
                ));
            }
        }
        
        nursesInfo.append("\nTotal available nurses: ").append(availableNurses.size());
        nursesInfo.append("\nTotal nurses in system: ").append(nurseDatabase.getNurseCount());
        
        // Show in a dialog with text area for better formatting
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Available Nurses");
        alert.setHeaderText("Nurses Available for Assignment");
        
        TextArea textArea = new TextArea(nursesInfo.toString());
        textArea.setEditable(false);
        textArea.setWrapText(true);
        textArea.setPrefRowCount(15);
        textArea.setPrefColumnCount(80);
        textArea.setStyle("-fx-font-family: 'Monospaced'; -fx-font-size: 12px;");
        
        alert.getDialogPane().setContent(textArea);
        alert.getDialogPane().setPrefSize(800, 500);
        alert.showAndWait();
    }
    
    private String getShiftAbbreviation(String fullShift) {
        if (fullShift.contains("Day")) return "Day";
        if (fullShift.contains("Evening")) return "Evening";
        if (fullShift.contains("Night")) return "Night";
        if (fullShift.contains("Morning")) return "Morning";
        return fullShift.length() > 10 ? fullShift.substring(0, 10) + "..." : fullShift;
    }
    
    private boolean assignNurseToPatient(ComboBox<String> durationComboBox, 
                                        ComboBox<String> priorityComboBox,
                                        TextField dateField,
                                        Label validationLabel) {
        // Validate patient
        String patientId = patientIdField.getText().trim();
        if (patientId.isEmpty() || patientNameField.getText().equals("Patient not found")) {
            validationLabel.setText("Please enter a valid patient ID");
            return false;
        }
        
        // Validate nurse selection
        String selectedNurse = nurseComboBox.getValue();
        if (selectedNurse == null || selectedNurse.isEmpty() || 
            selectedNurse.contains("No available nurses")) {
            validationLabel.setText("Please select an available nurse");
            return false;
        }
        
        // Validate nurse ID
        String nurseId = nurseIdField.getText().trim();
        if (nurseId.isEmpty()) {
            validationLabel.setText("Please select a nurse from the list");
            return false;
        }
        
        try {
            String assignmentDetails = assignmentDetailsArea.getText().trim();
            if (assignmentDetails.isEmpty()) {
                validationLabel.setText("Please enter assignment details");
                return false;
            }
            
            String duration = durationComboBox.getValue();
            String priority = priorityComboBox.getValue();
            String assignmentDate = dateField.getText();
            
            // Get the nurse object by searching through all nurses
            Nurses assignedNurse = null;
            List<Nurses> allNurses = nurseDatabase.getAllNurses();
            for (Nurses nurse : allNurses) {
                // Use getId() method to compare IDs
                if (nurse.getId().equalsIgnoreCase(nurseId)) {
                    assignedNurse = nurse;
                    break;
                }
            }
            
            // Assign nurse to patient
            if (nurseDatabase.assignNurseToPatient(nurseId, patientId)) {
                // Log assignment
                System.out.println("\n" + "=".repeat(80));
                System.out.println("NURSE ASSIGNMENT RECORD");
                System.out.println("=".repeat(80));
                System.out.println("ASSIGNMENT DETAILS:");
                System.out.println("  Patient ID: " + patientId);
                System.out.println("  Patient Name: " + patientNameField.getText());
                System.out.println("  Nurse ID: " + nurseId);
                System.out.println("  Nurse Name: " + nurseNameField.getText());
                System.out.println("  Duration: " + duration);
                System.out.println("  Priority: " + priority);
                System.out.println("  Assignment Date: " + assignmentDate);
                System.out.println("  Assignment Details: " + assignmentDetails);
                
                // Get nurse details for logging
                if (assignedNurse != null) {
                    System.out.println("\nNURSE DETAILS:");
                    System.out.println("  Department: " + assignedNurse.getDepartment());
                    System.out.println("  Shift: " + assignedNurse.getShift());
                    System.out.println("  Current Patients: " + assignedNurse.getCurrentPatientsCount() + 
                                     "/" + assignedNurse.getMaxPatients());
                    System.out.println("  Status: " + assignedNurse.getStatus());
                }
                
                System.out.println("\nAssignment successfully recorded in system.");
                System.out.println("=".repeat(80));
                
                // Update nurse combo box
                updateNurseComboBox();
                
                return true;
            } else {
                validationLabel.setText("Failed to assign nurse. Nurse may be at capacity.");
                return false;
            }
            
        } catch (Exception e) {
            validationLabel.setText("Error: " + e.getMessage());
            return false;
        }
    }
    
    private void showAllAssignments() {
        List<Nurses> allNurses = nurseDatabase.getAllNurses();
        
        StringBuilder assignmentsInfo = new StringBuilder();
        assignmentsInfo.append("CURRENT NURSE ASSIGNMENTS\n");
        assignmentsInfo.append("=".repeat(100)).append("\n\n");
        
        int totalAssignments = 0;
        int nursesWithAssignments = 0;
        
        for (Nurses nurse : allNurses) {
            // Check if nurse has patients assigned (using getCurrentPatientsCount)
            if (nurse.getCurrentPatientsCount() > 0) {
                nursesWithAssignments++;
                totalAssignments += nurse.getCurrentPatientsCount();
                
                assignmentsInfo.append("Nurse: ").append(nurse.getName())
                              .append(" (ID: ").append(nurse.getId()).append(")\n");  // Use getId()
                assignmentsInfo.append("Department: ").append(nurse.getDepartment())
                              .append(" | Shift: ").append(nurse.getShift()).append("\n");
                assignmentsInfo.append("Status: ").append(nurse.getStatus())
                              .append(" | Patients: ").append(nurse.getCurrentPatientsCount())
                              .append("/").append(nurse.getMaxPatients()).append("\n");
                
                assignmentsInfo.append("Number of assigned patients: ").append(nurse.getCurrentPatientsCount()).append("\n");
                assignmentsInfo.append("-".repeat(100)).append("\n");
            }
        }
        
        if (totalAssignments == 0) {
            assignmentsInfo.append("No active nurse assignments found.\n");
        } else {
            assignmentsInfo.append("\nSUMMARY:\n");
            assignmentsInfo.append("  Total nurses with assignments: ").append(nursesWithAssignments).append("\n");
            assignmentsInfo.append("  Total patient assignments: ").append(totalAssignments).append("\n");
            assignmentsInfo.append("  Assignment rate: ").append(String.format("%.1f%%", 
                (nursesWithAssignments * 100.0 / allNurses.size()))).append("\n");
        }
        
        // Show in a dialog
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Current Assignments");
        alert.setHeaderText("Nurse-Patient Assignments");
        
        TextArea textArea = new TextArea(assignmentsInfo.toString());
        textArea.setEditable(false);
        textArea.setWrapText(true);
        textArea.setPrefRowCount(20);
        textArea.setPrefColumnCount(100);
        textArea.setStyle("-fx-font-family: 'Monospaced'; -fx-font-size: 12px;");
        
        alert.getDialogPane().setContent(textArea);
        alert.getDialogPane().setPrefSize(800, 500);
        alert.showAndWait();
    }
    
    private void clearForm() {
        patientIdField.clear();
        patientNameField.clear();
        patientNameField.setStyle("-fx-background-color: #ecf0f1;");
        nurseIdField.clear();
        nurseNameField.clear();
        nurseIdField.setStyle("-fx-background-color: #ecf0f1;");
        nurseNameField.setStyle("-fx-background-color: #ecf0f1;");
        assignmentDetailsArea.clear();
        updateNurseComboBox();
    }
    
    private void showSuccessAlert() {
        String nurseId = nurseIdField.getText();
        String nurseName = nurseNameField.getText();
        String patientId = patientIdField.getText();
        String patientName = patientNameField.getText();
        
        // Get nurse details by searching through all nurses
        Nurses nurse = null;
        List<Nurses> allNurses = nurseDatabase.getAllNurses();
        for (Nurses n : allNurses) {
            // Use getId() to compare
            if (n.getId().equalsIgnoreCase(nurseId)) {
                nurse = n;
                break;
            }
        }
        
        String workloadInfo = "";
        if (nurse != null) {
            workloadInfo = "\nNurse workload: " + nurse.getCurrentPatientsCount() + 
                          "/" + nurse.getMaxPatients() + " patients";
        }
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Assignment Successful");
        alert.setHeaderText("✅ Nurse Assigned to Patient");
        
        String content = "Nurse has been successfully assigned to the patient.\n\n" +
                        "PATIENT INFORMATION:\n" +
                        "  ID: " + patientId + "\n" +
                        "  Name: " + patientName + "\n\n" +
                        "NURSE INFORMATION:\n" +
                        "  ID: " + nurseId + "\n" +
                        "  Name: " + nurseName + "\n" +
                        workloadInfo + "\n\n" +
                        "Assignment has been recorded in the system.";
        
        alert.setContentText(content);
        alert.showAndWait();
        clearForm();
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