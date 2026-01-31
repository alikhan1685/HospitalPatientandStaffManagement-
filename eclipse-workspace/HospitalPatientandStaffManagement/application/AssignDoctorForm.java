package application;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import java.util.List;

public class AssignDoctorForm {
    private VBox formContainer;
    private TextArea doctorsArea;
    private ComboBox<String> filterComboBox;
    private DoctorDatabase doctorDatabase;
    
    public AssignDoctorForm() {
        doctorDatabase = DoctorDatabase.getInstance();
        initializeForm();
    }
    
    private void initializeForm() {
        formContainer = new VBox();
        formContainer.setSpacing(20);
        formContainer.setPadding(new Insets(20));
        formContainer.setStyle("-fx-background-color: #f8f9fa;");
        
        // Title
        Label title = new Label("Available Doctors");
        title.setFont(new Font("Arial", 28));
        title.setStyle("-fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        
        // Stats Panel
        HBox statsPanel = new HBox(20);
        statsPanel.setPadding(new Insets(10));
        statsPanel.setStyle("-fx-background-color: #2c3e50; -fx-background-radius: 8;");
        
        Label totalDoctorsLabel = new Label();
        Label availableDoctorsLabel = new Label();
        updateStatsLabels(totalDoctorsLabel, availableDoctorsLabel);
        
        totalDoctorsLabel.setStyle("-fx-text-fill: #ecf0f1; -fx-font-size: 14px; -fx-font-weight: bold;");
        availableDoctorsLabel.setStyle("-fx-text-fill: #2ecc71; -fx-font-size: 14px; -fx-font-weight: bold;");
        
        statsPanel.getChildren().addAll(totalDoctorsLabel, availableDoctorsLabel);
        
        // Filter Panel
        HBox filterPanel = new HBox(10);
        filterPanel.setPadding(new Insets(10));
        
        filterComboBox = new ComboBox<>();
        filterComboBox.getItems().addAll(
            "All Doctors",
            "Available Only",
            "By Specialization",
            "Active Status"
        );
        filterComboBox.setValue("Available Only");
        filterComboBox.setPrefWidth(150);
        
        ComboBox<String> specializationComboBox = new ComboBox<>();
        List<String> specializations = doctorDatabase.getAllSpecializations();
        specializationComboBox.getItems().addAll("All Specializations");
        specializationComboBox.getItems().addAll(specializations);
        specializationComboBox.setValue("All Specializations");
        specializationComboBox.setPrefWidth(180);
        
        Button filterButton = new Button("Apply Filter");
        filterButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-weight: bold;");
        filterButton.setOnAction(e -> {
            String filterType = filterComboBox.getValue();
            String specialization = specializationComboBox.getValue();
            displayDoctors(filterType, specialization);
        });
        
        Button refreshButton = new Button("🔄 Refresh");
        refreshButton.setStyle("-fx-background-color: #95a5a6; -fx-text-fill: white; -fx-font-weight: bold;");
        refreshButton.setOnAction(e -> {
            updateStatsLabels(totalDoctorsLabel, availableDoctorsLabel);
            displayDoctors("Available Only", "All Specializations");
        });
        
        filterPanel.getChildren().addAll(
            new Label("Filter:"), filterComboBox,
            new Label("Specialization:"), specializationComboBox,
            filterButton, refreshButton
        );
        
        // Doctors Display Area
        doctorsArea = new TextArea();
        doctorsArea.setPrefRowCount(20);
        doctorsArea.setEditable(false);
        doctorsArea.setWrapText(true);
        doctorsArea.setStyle("-fx-font-family: 'Monospaced'; -fx-font-size: 12px;");
        
        ScrollPane scrollPane = new ScrollPane(doctorsArea);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: white; -fx-border-color: #dee2e6; -fx-border-radius: 8;");
        
        // Action Buttons
        HBox actionPanel = new HBox(15);
        actionPanel.setPadding(new Insets(10));
        
        Button viewAllButton = new Button("View All Doctors");
        viewAllButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 8 15;");
        viewAllButton.setOnAction(e -> displayAllDoctors());
        
        Button viewAvailableButton = new Button("View Available");
        viewAvailableButton.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 8 15;");
        viewAvailableButton.setOnAction(e -> displayAvailableDoctors());
        
        Button updateStatusButton = new Button("Update Status");
        updateStatusButton.setStyle("-fx-background-color: #e67e22; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 8 15;");
        updateStatusButton.setOnAction(e -> showStatusUpdateDialog());
        
        actionPanel.getChildren().addAll(viewAllButton, viewAvailableButton, updateStatusButton);
        
        // Add components
        formContainer.getChildren().addAll(
            title, statsPanel, filterPanel, scrollPane, actionPanel
        );
        
        // Display initial data
        displayAvailableDoctors();
    }
    
    private void updateStatsLabels(Label totalLabel, Label availableLabel) {
        int total = doctorDatabase.getDoctorCount();
        int available = doctorDatabase.getAvailableDoctorCount();
        
        totalLabel.setText("Total Doctors: " + total);
        availableLabel.setText("Available Now: " + available);
    }
    
    private void displayDoctors(String filterType, String specialization) {
        List<Staff> doctors;
        
        switch (filterType) {
            case "All Doctors":
                doctors = doctorDatabase.getAllDoctors();
                break;
            case "Available Only":
                doctors = doctorDatabase.getAvailableDoctors();
                break;
            case "By Specialization":
                if ("All Specializations".equals(specialization)) {
                    doctors = doctorDatabase.getAllDoctors();
                } else {
                    doctors = doctorDatabase.searchBySpecialization(specialization);
                }
                break;
            default:
                doctors = doctorDatabase.getAllDoctors();
        }
        
        StringBuilder displayText = new StringBuilder();
        
        if (doctors.isEmpty()) {
            displayText.append("No doctors found matching the criteria.");
        } else {
            displayText.append("╔════════════════════════════════════════════════════════════════════════════════╗\n");
            displayText.append("║                             DOCTOR LIST - ").append(String.format("%-20s", filterType)).append(" ║\n");
            displayText.append("╚════════════════════════════════════════════════════════════════════════════════╝\n\n");
            
            displayText.append("┌────┬────────────┬──────────────────────────┬─────────────────┬─────┬──────────────┬────────────────────┐\n");
            displayText.append("│ No │    ID      │          Name            │ Specialization  │ Exp │    Status    │   Contact          │\n");
            displayText.append("├────┼────────────┼──────────────────────────┼─────────────────┼─────┼──────────────┼────────────────────┤\n");
            
            for (int i = 0; i < doctors.size(); i++) {
                Staff doctor = doctors.get(i);
                
                // Status color indicator
                String statusIcon = "●";
                String statusColor = getStatusColor(doctor.getStatus());
                
                displayText.append(String.format("│ %2d │ %-10s │ %-24s │ %-15s │ %3d │ %-12s │ %-18s │\n",
                    i + 1,
                    doctor.getStaffId(),
                    doctor.getName().length() > 24 ? doctor.getName().substring(0, 21) + "..." : doctor.getName(),
                    doctor.getSpecialization(),
                    doctor.getExperience(),
                    statusIcon + " " + doctor.getStatus(),
                    doctor.getPhone()
                ));
            }
            
            displayText.append("└────┴────────────┴──────────────────────────┴─────────────────┴─────┴──────────────┴────────────────────┘\n\n");
            
            // Add doctor details
            displayText.append("\nDOCTOR DETAILS:\n");
            displayText.append("─".repeat(80)).append("\n");
            
            for (int i = 0; i < doctors.size(); i++) {
                Staff doctor = doctors.get(i);
                displayText.append("\n").append(i + 1).append(". ").append(doctor.getName()).append("\n");
                displayText.append("   ID: ").append(doctor.getStaffId()).append("\n");
                displayText.append("   Specialization: ").append(doctor.getSpecialization()).append("\n");
                displayText.append("   Experience: ").append(doctor.getExperience()).append(" years\n");
                displayText.append("   Qualification: ").append(doctor.getQualification()).append("\n");
                displayText.append("   Status: ").append(doctor.getStatus()).append("\n");
                displayText.append("   Phone: ").append(doctor.getPhone()).append("\n");
                displayText.append("   Email: ").append(doctor.getEmail()).append("\n");
                displayText.append("   Address: ").append(doctor.getAddress()).append("\n");
                displayText.append("─".repeat(80)).append("\n");
            }
        }
        
        doctorsArea.setText(displayText.toString());
    }
    
    private String getStatusColor(String status) {
        switch (status) {
            case "Active":
            case "Available":
            case "Available for Consultation":
                return "#27ae60"; // Green
            case "On Duty":
                return "#3498db"; // Blue
            case "On Leave":
                return "#e74c3c"; // Red
            case "In Surgery":
                return "#f39c12"; // Orange
            default:
                return "#95a5a6"; // Gray
        }
    }
    
    private void displayAllDoctors() {
        displayDoctors("All Doctors", "All Specializations");
    }
    
    private void displayAvailableDoctors() {
        displayDoctors("Available Only", "All Specializations");
    }
    
    private void showStatusUpdateDialog() {
        // Create dialog for updating doctor status
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Update Doctor Status");
        dialog.setHeaderText("Change doctor availability status");
        
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));
        
        ComboBox<String> doctorComboBox = new ComboBox<>();
        List<Staff> allDoctors = doctorDatabase.getAllDoctors();
        for (Staff doctor : allDoctors) {
            doctorComboBox.getItems().add(doctor.getStaffId() + " - " + doctor.getName());
        }
        doctorComboBox.setPromptText("Select doctor");
        doctorComboBox.setPrefWidth(250);
        
        ComboBox<String> statusComboBox = new ComboBox<>();
        statusComboBox.getItems().addAll(
            "Active",
            "Available",
            "Available for Consultation",
            "On Duty",
            "On Leave",
            "Not Available",
            "In Surgery"
        );
        statusComboBox.setValue("Active");
        
        grid.add(new Label("Doctor:"), 0, 0);
        grid.add(doctorComboBox, 1, 0);
        grid.add(new Label("New Status:"), 0, 1);
        grid.add(statusComboBox, 1, 1);
        
        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        
        dialog.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                String selectedDoctor = doctorComboBox.getValue();
                String newStatus = statusComboBox.getValue();
                
                if (selectedDoctor != null && !selectedDoctor.isEmpty()) {
                    // Extract doctor ID
                    String doctorId = selectedDoctor.split(" - ")[0];
                    
                    if (doctorDatabase.updateDoctorStatus(doctorId, newStatus)) {
                        showAlert("Status Updated", "Doctor status has been updated successfully.");
                        displayAvailableDoctors(); // Refresh display
                    } else {
                        showAlert("Error", "Failed to update doctor status.");
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