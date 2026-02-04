package application;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import java.util.*;
import java.time.LocalDate;

public class AssignTechnicianForm {
    private VBox formContainer;
    private TextField patientIdField;
    private TextField patientNameField;
    private ComboBox<String> technicianComboBox;
    private Button refreshButton;
    private Map<String, List<String>> technicianAssignments; // Track technician-patient assignments
    
    public AssignTechnicianForm() {
        technicianAssignments = new HashMap<>();
        initializeForm();
    }
    
    private void initializeForm() {
        formContainer = new VBox();
        formContainer.setSpacing(20);
        formContainer.setPadding(new Insets(20));
        formContainer.setStyle("-fx-background-color: #f8f9fa;");
        
        // Title
        Label title = new Label("Assign Technician to Patient");
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
        
        // Technician ComboBox with refresh button
        technicianComboBox = new ComboBox<>();
        technicianComboBox.setPrefWidth(300);
        
        refreshButton = new Button("🔄");
        refreshButton.setStyle("-fx-background-color: #2ecc71; -fx-text-fill: white; -fx-font-weight: bold;");
        refreshButton.setTooltip(new Tooltip("Refresh technicians list"));
        refreshButton.setOnAction(e -> updateTechnicianComboBox());
        
        HBox technicianContainer = new HBox(10);
        technicianContainer.getChildren().addAll(technicianComboBox, refreshButton);
        
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
        
        form.add(createLabel("Assign Technician:*"), 0, row);
        form.add(technicianContainer, 1, row);
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
        Button assignButton = new Button("Assign Technician");
        assignButton.setStyle("-fx-background-color: #16a085; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 25; -fx-font-size: 14px; -fx-background-radius: 5;");
        assignButton.setOnAction(e -> {
            if (assignTechnicianToPatient(validationLabel)) {
                showSuccessAlert();
                updateTechnicianComboBox(); // Refresh the list after assignment
            }
        });
        
        // Clear button
        Button clearButton = new Button("Clear Form");
        clearButton.setStyle("-fx-background-color: #95a5a6; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 25; -fx-font-size: 14px; -fx-background-radius: 5;");
        clearButton.setOnAction(e -> {
            clearForm();
            validationLabel.setText("");
        });
        
        // View assignments button - CHANGED TO SHOW ASSIGNMENTS
        Button viewAssignmentsButton = new Button("View Assignments");
        viewAssignmentsButton.setStyle("-fx-background-color: #8e44ad; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 25; -fx-font-size: 14px; -fx-background-radius: 5;");
        viewAssignmentsButton.setOnAction(e -> showAllAssignments());
        
        buttonContainer.getChildren().addAll(assignButton, clearButton, viewAssignmentsButton);
        form.add(buttonContainer, 0, row, 2, 1);
        
        // Add components to container
        formContainer.getChildren().addAll(title, form);
        
        // Initialize the technician combobox
        updateTechnicianComboBox();
    }
    
    // Method to update technician combobox with available technicians
    private void updateTechnicianComboBox() {
        technicianComboBox.getItems().clear();
        
        // Get available technicians
        List<String> technicians = getAvailableTechnicians();
        
        if (technicians.isEmpty()) {
            technicianComboBox.getItems().add("No available technicians");
            technicianComboBox.setValue("No available technicians");
            technicianComboBox.setPromptText("No technicians available");
        } else {
            technicianComboBox.getItems().addAll(technicians);
            technicianComboBox.setPromptText("Select technician to assign");
            
            // Sort alphabetically
            technicianComboBox.getItems().sort(String::compareTo);
        }
    }
    
    // Method to get available technicians
    private List<String> getAvailableTechnicians() {
        return List.of(
            "Alex Johnson (Lab Technician) - ID: TECH-001",
            "Sarah Williams (X-Ray Technician) - ID: TECH-002",
            "Michael Brown (MRI Technician) - ID: TECH-003",
            "Emily Davis (Ultrasound Technician) - ID: TECH-004",
            "David Wilson (Cardiology Technician) - ID: TECH-005"
        );
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
    
    // Helper method to extract technician ID from combobox selection
    private String extractTechnicianId(String displayText) {
        if (displayText == null || displayText.equals("No available technicians")) {
            return null;
        }
        
        try {
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
    
    // Helper method to extract technician name from combobox selection
    private String extractTechnicianName(String displayText) {
        if (displayText == null || displayText.equals("No available technicians")) {
            return null;
        }
        
        try {
            String[] parts = displayText.split(" \\(");
            if (parts.length > 0) {
                return parts[0].trim();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return displayText;
    }
    
    // Helper method to extract technician specialization from combobox selection
    private String extractTechnicianSpecialization(String displayText) {
        if (displayText == null || displayText.equals("No available technicians")) {
            return null;
        }
        
        try {
            String[] parts = displayText.split(" \\(");
            if (parts.length > 1) {
                String specializationPart = parts[1];
                if (specializationPart.contains(") - ID: ")) {
                    return specializationPart.split("\\) - ID: ")[0].trim();
                }
                return specializationPart.replace(")", "").trim();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    // Method to track technician assignment
    private void trackTechnicianAssignment(String technicianId, String technicianName, 
                                          String patientId, String patientName) {
        if (!technicianAssignments.containsKey(technicianId)) {
            technicianAssignments.put(technicianId, new ArrayList<>());
        }
        
        String assignment = String.format("Patient: %s (ID: %s) | Date: %s", 
            patientName, patientId, LocalDate.now());
        technicianAssignments.get(technicianId).add(assignment);
    }
    
    // Get total assignments count
    private int getTotalAssignments() {
        int total = 0;
        for (List<String> assignments : technicianAssignments.values()) {
            total += assignments.size();
        }
        return total;
    }
    
    // Get assignments for a specific technician
    private List<String> getTechnicianAssignments(String technicianId) {
        return technicianAssignments.getOrDefault(technicianId, new ArrayList<>());
    }
    
    // MAIN METHOD: Assign technician to patient
    private boolean assignTechnicianToPatient(Label validationLabel) {
        // Validate patient
        String patientId = patientIdField.getText().trim();
        String patientName = patientNameField.getText();
        
        if (patientName.equals("Patient not found") || patientName.isEmpty()) {
            validationLabel.setText("Error: Patient not found. Please enter a valid Patient ID.");
            patientIdField.setStyle("-fx-border-color: #ff0000; -fx-border-width: 2px;");
            return false;
        }
        
        // Validate technician selection
        String selectedTechnician = technicianComboBox.getValue();
        if (selectedTechnician == null || selectedTechnician.equals("No available technicians")) {
            validationLabel.setText("Please select an available technician.");
            technicianComboBox.setStyle("-fx-border-color: #ff0000; -fx-border-width: 2px;");
            return false;
        }
        
        try {
            // Extract technician information
            String technicianId = extractTechnicianId(selectedTechnician);
            String technicianName = extractTechnicianName(selectedTechnician);
            String specialization = extractTechnicianSpecialization(selectedTechnician);
            
            if (technicianId == null || technicianName == null) {
                validationLabel.setText("Invalid technician selection.");
                return false;
            }
            
            // Track the assignment
            trackTechnicianAssignment(technicianId, technicianName, patientId, patientName);
            
            // Display success message
            System.out.println("\n" + "=".repeat(60));
            System.out.println("TECHNICIAN ASSIGNED SUCCESSFULLY");
            System.out.println("=".repeat(60));
            System.out.println("Patient ID: " + patientId);
            System.out.println("Patient Name: " + patientName);
            System.out.println("Technician ID: " + technicianId);
            System.out.println("Technician Name: " + technicianName);
            System.out.println("Specialization: " + specialization);
            System.out.println("Assignment Date: " + LocalDate.now());
            System.out.println("Status: Active");
            System.out.println("Total Assignments: " + getTotalAssignments());
            System.out.println("=".repeat(60));
            
            return true;
            
        } catch (Exception e) {
            validationLabel.setText("An error occurred: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    // Show all assignments
    private void showAllAssignments() {
        int totalAssignments = getTotalAssignments();
        
        StringBuilder assignmentsInfo = new StringBuilder();
        assignmentsInfo.append("TECHNICIAN-PATIENT ASSIGNMENTS\n");
        assignmentsInfo.append("=".repeat(80)).append("\n\n");
        
        if (totalAssignments == 0) {
            assignmentsInfo.append("No technician assignments found.\n");
        } else {
            assignmentsInfo.append(String.format("%-30s | %-20s | %-15s | %s\n",
                "Technician", "Specialization", "Patient ID", "Patient Name"));
            assignmentsInfo.append("-".repeat(80)).append("\n");
            
            // Parse all technicians to show their assignments
            for (String techDisplay : getAvailableTechnicians()) {
                String techId = extractTechnicianId(techDisplay);
                String techName = extractTechnicianName(techDisplay);
                String specialization = extractTechnicianSpecialization(techDisplay);
                
                if (technicianAssignments.containsKey(techId)) {
                    List<String> assignments = technicianAssignments.get(techId);
                    for (String assignment : assignments) {
                        // Parse patient info from assignment string
                        String[] parts = assignment.split(" \\| ");
                        String patientInfo = parts[0].replace("Patient: ", "");
                        String patientName = patientInfo.split(" \\(")[0];
                        String patientId = patientInfo.split("ID: ")[1].replace(")", "");
                        
                        assignmentsInfo.append(String.format("%-30s | %-20s | %-15s | %s\n",
                            techName,
                            specialization,
                            patientId,
                            patientName
                        ));
                    }
                }
            }
        }
        
        assignmentsInfo.append("\n📊 SUMMARY:\n");
        assignmentsInfo.append("Total Assignments: ").append(totalAssignments).append("\n");
        assignmentsInfo.append("Technicians with Assignments: ").append(technicianAssignments.size()).append("\n");
        
        // Calculate assignments per technician
        if (!technicianAssignments.isEmpty()) {
            assignmentsInfo.append("\n📈 Assignments per Technician:\n");
            for (Map.Entry<String, List<String>> entry : technicianAssignments.entrySet()) {
                String techId = entry.getKey();
                String techName = "Unknown";
                
                // Find technician name by ID
                for (String techDisplay : getAvailableTechnicians()) {
                    if (extractTechnicianId(techDisplay).equals(techId)) {
                        techName = extractTechnicianName(techDisplay);
                        break;
                    }
                }
                
                assignmentsInfo.append("  ").append(techName)
                              .append(": ").append(entry.getValue().size())
                              .append(" patient(s)\n");
            }
        }
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Technician Assignments");
        alert.setHeaderText("All Technician-Patient Assignments");
        
        TextArea textArea = new TextArea(assignmentsInfo.toString());
        textArea.setEditable(false);
        textArea.setWrapText(true);
        textArea.setPrefRowCount(20);
        textArea.setPrefColumnCount(80);
        textArea.setStyle("-fx-font-family: 'Monospaced'; -fx-font-size: 12px;");
        
        alert.getDialogPane().setContent(textArea);
        alert.getDialogPane().setPrefSize(800, 500);
        alert.showAndWait();
    }
    
    private void clearForm() {
        patientIdField.clear();
        patientNameField.clear();
        patientNameField.setStyle("-fx-background-color: #ecf0f1;");
        technicianComboBox.setValue(null);
        patientIdField.setStyle("");
        technicianComboBox.setStyle("");
    }
    
    private void showSuccessAlert() {
        String selectedTechnician = technicianComboBox.getValue();
        String technicianName = extractTechnicianName(selectedTechnician);
        String patientName = patientNameField.getText();
        int totalAssignments = getTotalAssignments();
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("✅ Technician Assigned Successfully");
        
        String content = String.format(
            "Technician has been successfully assigned to the patient.\n\n" +
            "Patient: %s\n" +
            "Technician: %s\n" +
            "Date: %s\n\n" +
            "📊 Database Status:\n" +
            "Total Assignments: %d\n" +
            "Technicians with Assignments: %d\n\n" +
            "✅ Assignment has been recorded in the system.",
            patientName,
            technicianName,
            LocalDate.now(),
            totalAssignments,
            technicianAssignments.size()
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