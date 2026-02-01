package application;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import java.util.List;

public class StatusOfNurseForm {
    private VBox formContainer;
    private TextArea nursesArea;
    private ComboBox<String> filterComboBox;
    private ComboBox<String> departmentComboBox;
    private NurseDatabase nurseDatabase;
    
    public StatusOfNurseForm() {
        nurseDatabase = NurseDatabase.getInstance();
        initializeForm();
    }
    
    private void initializeForm() {
        formContainer = new VBox();
        formContainer.setSpacing(20);
        formContainer.setPadding(new Insets(20));
        formContainer.setStyle("-fx-background-color: #f8f9fa;");
        
        // Title
        Label title = new Label("Available Nurses");
        title.setFont(new Font("Arial", 28));
        title.setStyle("-fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        
        // Stats Panel
        HBox statsPanel = new HBox(20);
        statsPanel.setPadding(new Insets(10));
        statsPanel.setStyle("-fx-background-color: #2c3e50; -fx-background-radius: 8;");
        
        Label totalNursesLabel = new Label();
        Label availableNursesLabel = new Label();
        updateStatsLabels(totalNursesLabel, availableNursesLabel);
        
        totalNursesLabel.setStyle("-fx-text-fill: #ecf0f1; -fx-font-size: 14px; -fx-font-weight: bold;");
        availableNursesLabel.setStyle("-fx-text-fill: #2ecc71; -fx-font-size: 14px; -fx-font-weight: bold;");
        
        statsPanel.getChildren().addAll(totalNursesLabel, availableNursesLabel);
        
        // Filter Panel
        HBox filterPanel = new HBox(10);
        filterPanel.setPadding(new Insets(10));
        
        filterComboBox = new ComboBox<>();
        filterComboBox.getItems().addAll(
            "All Nurses",
            "Available Only",
            "By Department",
            "By Shift",
            "Can Accept Patients"
        );
        filterComboBox.setValue("Available Only");
        filterComboBox.setPrefWidth(150);
        
        departmentComboBox = new ComboBox<>();
        List<String> departments = nurseDatabase.getAllDepartments();
        departmentComboBox.getItems().addAll("All Departments");
        departmentComboBox.getItems().addAll(departments);
        departmentComboBox.setValue("All Departments");
        departmentComboBox.setPrefWidth(200);
        
        ComboBox<String> shiftComboBox = new ComboBox<>();
        shiftComboBox.getItems().addAll(
            "All Shifts",
            "Day Shift",
            "Evening Shift", 
            "Night Shift",
            "Morning Shift"
        );
        shiftComboBox.setValue("All Shifts");
        shiftComboBox.setPrefWidth(150);
        
        Button filterButton = new Button("Apply Filter");
        filterButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-weight: bold;");
        filterButton.setOnAction(e -> {
            String filterType = filterComboBox.getValue();
            String department = departmentComboBox.getValue();
            String shift = shiftComboBox.getValue();
            displayNurses(filterType, department, shift);
        });
        
        Button refreshButton = new Button("🔄 Refresh");
        refreshButton.setStyle("-fx-background-color: #95a5a6; -fx-text-fill: white; -fx-font-weight: bold;");
        refreshButton.setOnAction(e -> {
            updateStatsLabels(totalNursesLabel, availableNursesLabel);
            displayNurses("Available Only", "All Departments", "All Shifts");
        });
        
        filterPanel.getChildren().addAll(
            new Label("Filter:"), filterComboBox,
            new Label("Department:"), departmentComboBox,
            new Label("Shift:"), shiftComboBox,
            filterButton, refreshButton
        );
        
        // Nurses Display Area
        nursesArea = new TextArea();
        nursesArea.setPrefRowCount(20);
        nursesArea.setEditable(false);
        nursesArea.setWrapText(true);
        nursesArea.setStyle("-fx-font-family: 'Monospaced'; -fx-font-size: 12px;");
        
        ScrollPane scrollPane = new ScrollPane(nursesArea);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: white; -fx-border-color: #dee2e6; -fx-border-radius: 8;");
        
        // Action Buttons
        HBox actionPanel = new HBox(15);
        actionPanel.setPadding(new Insets(10));
        
        Button viewAllButton = new Button("View All Nurses");
        viewAllButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 8 15;");
        viewAllButton.setOnAction(e -> displayAllNurses());
        
        Button viewAvailableButton = new Button("View Available");
        viewAvailableButton.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 8 15;");
        viewAvailableButton.setOnAction(e -> displayAvailableNurses());
        
        Button updateStatusButton = new Button("Update Status");
        updateStatusButton.setStyle("-fx-background-color: #e67e22; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 8 15;");
        updateStatusButton.setOnAction(e -> showStatusUpdateDialog());
        
        actionPanel.getChildren().addAll(viewAllButton, viewAvailableButton, updateStatusButton);
        
        // Add components
        formContainer.getChildren().addAll(
            title, statsPanel, filterPanel, scrollPane, actionPanel
        );
        
        // Display initial data
        displayAvailableNurses();
    }
    
    private void updateStatsLabels(Label totalLabel, Label availableLabel) {
        int total = nurseDatabase.getNurseCount();
        int available = nurseDatabase.getAvailableNurseCount();
        
        totalLabel.setText("Total Nurses: " + total);
        availableLabel.setText("Available Now: " + available);
    }
    
    private void displayNurses(String filterType, String department, String shift) {
        List<Nurses> nurses;
        
        switch (filterType) {
            case "All Nurses":
                nurses = nurseDatabase.getAllNurses();
                break;
            case "Available Only":
                nurses = nurseDatabase.getAvailableNurses();
                break;
            case "By Department":
                if ("All Departments".equals(department)) {
                    nurses = nurseDatabase.getAllNurses();
                } else {
                    nurses = nurseDatabase.searchByDepartment(department);
                }
                break;
            case "Can Accept Patients":
                nurses = nurseDatabase.getAllNurses();
                // Filter to show only nurses who can accept more patients
                nurses.removeIf(n -> !n.canAcceptMorePatients());
                break;
            default:
                nurses = nurseDatabase.getAllNurses();
        }
        
        // Apply shift filter if needed
        if (!"All Shifts".equals(shift) && nurses != null) {
            nurses.removeIf(n -> !n.getShift().contains(shift.replace(" Shift", "")));
        }
        
        displayNursesList(nurses, filterType, department, shift);
    }
    
    private void displayNursesList(List<Nurses> nurses, String filterType, String department, String shift) {
        if (nurses == null || nurses.isEmpty()) {
            nursesArea.setText("No nurses found matching the criteria.");
            return;
        }
        
        StringBuilder displayText = new StringBuilder();
        
        displayText.append("╔══════════════════════════════════════════════════════════════════════════════════════════╗\n");
        displayText.append("║                                     NURSE LIST                                           ║\n");
        displayText.append("║                   Filter: ").append(String.format("%-20s", filterType));
        if (!"All Departments".equals(department)) {
            displayText.append(" | Dept: ").append(department);
        }
        if (!"All Shifts".equals(shift)) {
            displayText.append(" | Shift: ").append(shift);
        }
        displayText.append("     ║\n");
        displayText.append("╚══════════════════════════════════════════════════════════════════════════════════════════╝\n\n");
        
        displayText.append("┌────┬────────────┬──────────────────────────┬─────────────────────┬────────────┬────────────┬────────────┬────────────────┐\n");
        displayText.append("│ No │    ID      │          Name            │    Department       │   Shift    │   Status   │  Patients  │  Availability  │\n");
        displayText.append("├────┼────────────┼──────────────────────────┼─────────────────────┼────────────┼────────────┼────────────┼────────────────┤\n");
        
        for (int i = 0; i < nurses.size(); i++) {
            Nurses nurse = nurses.get(i);
            
            // Availability indicator
            String availability;
            if (nurse.canAcceptMorePatients()) {
                availability = "✅ Available";
            } else if ("At Capacity".equals(nurse.getStatus())) {
                availability = "🔴 Full";
            } else {
                availability = "🟡 " + nurse.getStatus();
            }
            
            // Patients count
            String patients = nurse.getCurrentPatientsCount() + "/" + nurse.getMaxPatients();
            
            displayText.append(String.format("│ %2d │ %-10s │ %-24s │ %-19s │ %-10s │ %-10s │ %-10s │ %-14s │\n",
                i + 1,
                nurse.getId(),
                nurse.getName().length() > 24 ? nurse.getName().substring(0, 21) + "..." : nurse.getName(),
                nurse.getDepartment().length() > 19 ? nurse.getDepartment().substring(0, 16) + "..." : nurse.getDepartment(),
               // getShiftAbbreviation(nurse.getShift()),
                nurse.getStatus(),
                patients,
                availability
            ));
        }
        
        displayText.append("└────┴────────────┴──────────────────────────┴─────────────────────┴────────────┴────────────┴────────────┴────────────────┘\n\n");
        
        // Add detailed information
        displayText.append("\nDETAILED INFORMATION:\n");
        displayText.append("─".repeat(100)).append("\n");
        
        for (int i = 0; i < Math.min(nurses.size(), 10); i++) { // Show details for first 10 nurses
            Nurses nurse = nurses.get(i);
            displayText.append("\n").append(i + 1).append(". Nurse ").append(nurse.getName()).append(" (ID: ").append(nurse.getId()).append(")\n");
            displayText.append("   Specialization: ").append(nurse.getSpecialization()).append("\n");
            displayText.append("   Qualification: ").append(nurse.getQualification()).append("\n");
            displayText.append("   Experience: ").append(nurse.getExperience()).append(" years\n");
            displayText.append("   Department: ").append(nurse.getDepartment()).append("\n");
            displayText.append("   Shift: ").append(nurse.getShift()).append("\n");
            displayText.append("   License: ").append(nurse.getLicenseNo()).append("\n");
            displayText.append("   Phone: ").append(nurse.getPhone()).append("\n");
            
            if (nurse.getCertifications() != null && !nurse.getCertifications().isEmpty()) {
                displayText.append("   Certifications: ").append(nurse.getCertifications()).append("\n");
            }
            
            displayText.append("   Workload: ").append(nurse.getCurrentPatientsCount()).append("/").append(nurse.getMaxPatients())
                      .append(" patients (").append(nurse.getWorkloadPercentage()).append("%)\n");
            displayText.append("   Status: ").append(nurse.getAvailabilityStatus()).append("\n");
            displayText.append("─".repeat(100)).append("\n");
        }
        
        if (nurses.size() > 10) {
            displayText.append("\n... and ").append(nurses.size() - 10).append(" more nurses.\n");
        }
        
        nursesArea.setText(displayText.toString());
    }
    
   // private String getShiftAbbreviation(String fullShift) {
  //      if (fullShift.contains("Day")) return "Day";
   //     if (fullShift.contains("Evening")) return "Evening";
    //    if (fullShift.contains("Night")) return "Night";
   //     if (fullShift.contains("Morning")) return "Morning";
   //     return fullShift[0];
   // }
    
    private void displayAllNurses() {
        displayNurses("All Nurses", "All Departments", "All Shifts");
    }
    
    private void displayAvailableNurses() {
        displayNurses("Available Only", "All Departments", "All Shifts");
    }
    
    private void showStatusUpdateDialog() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Update Nurse Status");
        dialog.setHeaderText("Change nurse availability status");
        
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));
        
        ComboBox<String> nurseComboBox = new ComboBox<>();
        List<Nurses> allNurses = nurseDatabase.getAllNurses();
        for (Nurses nurse : allNurses) {
            nurseComboBox.getItems().add(nurse.getId() + " - " + nurse.getName());
        }
        nurseComboBox.setPromptText("Select nurse");
        nurseComboBox.setPrefWidth(250);
        
        ComboBox<String> statusComboBox = new ComboBox<>();
        statusComboBox.getItems().addAll(
            "Available",
            "On Duty",
            "On Break",
            "On Leave",
            "At Capacity",
            "Not Available",
            "In Training"
        );
        statusComboBox.setValue("Available");
        
        grid.add(new Label("Nurse:"), 0, 0);
        grid.add(nurseComboBox, 1, 0);
        grid.add(new Label("New Status:"), 0, 1);
        grid.add(statusComboBox, 1, 1);
        
        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        
        dialog.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                String selectedNurse = nurseComboBox.getValue();
                String newStatus = statusComboBox.getValue();
                
                if (selectedNurse != null && !selectedNurse.isEmpty()) {
                    String nurseId = selectedNurse.split(" - ")[0];
                    
                    if (nurseDatabase.updateNurseStatus(nurseId, newStatus)) {
                        showAlert("Status Updated", "Nurse status has been updated successfully.");
                        displayAvailableNurses(); // Refresh display
                    } else {
                        showAlert("Error", "Failed to update nurse status.");
                    }
                }
            }
        });
    }
    
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    public VBox getForm() {
        return formContainer;
    }
}