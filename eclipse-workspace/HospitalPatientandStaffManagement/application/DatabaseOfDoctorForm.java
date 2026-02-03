package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.beans.property.SimpleStringProperty;
import java.util.List;

public class DatabaseOfDoctorForm {

    private VBox container;
    private TableView<Staff> doctorTable;
    private ObservableList<Staff> doctorData;

    public DatabaseOfDoctorForm() {
        doctorData = FXCollections.observableArrayList();
        initializeForm();
        refreshDoctorList();
    }
    
    private void initializeForm() {
        container = new VBox();
        container.setSpacing(20);
        container.setPadding(new Insets(20));
        container.setStyle("-fx-background-color: #f8f9fa;");

        Label title = new Label("ALL DOCTOR STATUS");
        title.setFont(new Font("Arial", 28));
        title.setStyle("-fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        // Table setup for doctors
        doctorTable = new TableView<>();
        doctorTable.setPrefWidth(1000);
        
        // Define columns for doctor information
        TableColumn<Staff, String> idCol = new TableColumn<>("Doctor ID");
        idCol.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getStaffId()));
        idCol.setPrefWidth(100);
        
        TableColumn<Staff, String> nameCol = new TableColumn<>("Doctor Name");
        nameCol.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getName()));
        nameCol.setPrefWidth(150);
        
        TableColumn<Staff, String> specializationCol = new TableColumn<>("Specialization");
        specializationCol.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getSpecialization()));
        specializationCol.setPrefWidth(150);
        
        TableColumn<Staff, String> experienceCol = new TableColumn<>("Experience");
        experienceCol.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getExperience() + " yrs"));
        experienceCol.setPrefWidth(100);
        
        TableColumn<Staff, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getStatus()));
        statusCol.setPrefWidth(120);
        
        // NEW COLUMN: Assigned Patients
        TableColumn<Staff, String> assignedPatientsCol = new TableColumn<>("Assigned Patients");
        assignedPatientsCol.setCellValueFactory(cellData -> {
            Staff doctor = cellData.getValue();
            List<Assignment> assignments = DoctorDatabase.getInstance().getDoctorAssignments(doctor.getStaffId());
            if (assignments.isEmpty()) {
                return new SimpleStringProperty("None");
            } else {
                StringBuilder patients = new StringBuilder();
                for (int i = 0; i < assignments.size(); i++) {
                    Assignment a = assignments.get(i);
                    patients.append(a.getPatientName()).append(" (").append(a.getPatientId()).append(")");
                    if (i < assignments.size() - 1) {
                        patients.append(", ");
                    }
                }
                return new SimpleStringProperty(patients.toString());
            }
        });
        assignedPatientsCol.setPrefWidth(300);
        
        // Add columns to table
        doctorTable.getColumns().addAll(idCol, nameCol, specializationCol, experienceCol, statusCol, assignedPatientsCol);

        // Buttons
        HBox buttonBox = new HBox(15);
        
        Button updateStatusButton = new Button("Update Doctor Status");
        updateStatusButton.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white; -fx-font-weight: bold;");
        updateStatusButton.setOnAction(e -> updateDoctorStatus());
        
        Button viewAssignmentsButton = new Button("View Assignments Details");
        viewAssignmentsButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-weight: bold;");
        viewAssignmentsButton.setOnAction(e -> viewAssignmentsDetails());
        
        Button refreshButton = new Button("Refresh List");
        refreshButton.setStyle("-fx-background-color: #e67e22; -fx-text-fill: white; -fx-font-weight: bold;");
        refreshButton.setOnAction(e -> refreshDoctorList());

        buttonBox.getChildren().addAll(updateStatusButton, viewAssignmentsButton, refreshButton);

        container.getChildren().addAll(title, doctorTable, buttonBox);
    }
    
    private void updateDoctorStatus() {
        Staff selectedDoctor = doctorTable.getSelectionModel().getSelectedItem();
        if (selectedDoctor != null) {
            // Create a dialog to update status
            Dialog<String> dialog = new Dialog<>();
            dialog.setTitle("Update Doctor Status");
            dialog.setHeaderText("Update status for: " + selectedDoctor.getName());
            
            // Set the button types
            ButtonType updateButtonType = new ButtonType("Update", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(updateButtonType, ButtonType.CANCEL);
            
            // Create the status ComboBox
            ComboBox<String> statusCombo = new ComboBox<>();
            statusCombo.getItems().addAll("Active", "Available", "On Leave", "Busy", "Off Duty");
            statusCombo.setValue(selectedDoctor.getStatus());
            
            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(20, 150, 10, 10));
            
            grid.add(new Label("New Status:"), 0, 0);
            grid.add(statusCombo, 1, 0);
            
            dialog.getDialogPane().setContent(grid);
            
            // Convert the result
            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == updateButtonType) {
                    return statusCombo.getValue();
                }
                return null;
            });
            
            // Show dialog and update if confirmed
            dialog.showAndWait().ifPresent(newStatus -> {
                DoctorDatabase.getInstance().updateDoctorStatus(selectedDoctor.getStaffId(), newStatus);
                refreshDoctorList();
                
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Status Updated");
                alert.setHeaderText("Doctor Status Updated");
                alert.setContentText("Dr. " + selectedDoctor.getName() + "'s status has been updated to: " + newStatus);
                alert.showAndWait();
            });
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Doctor Selected");
            alert.setContentText("Please select a doctor from the table to update their status.");
            alert.showAndWait();
        }
    }
    
    private void viewAssignmentsDetails() {
        Staff selectedDoctor = doctorTable.getSelectionModel().getSelectedItem();
        if (selectedDoctor != null) {
            List<Assignment> assignments = DoctorDatabase.getInstance().getDoctorAssignments(selectedDoctor.getStaffId());
            
            StringBuilder assignmentsInfo = new StringBuilder();
            assignmentsInfo.append("ASSIGNMENT DETAILS FOR DR. ").append(selectedDoctor.getName().toUpperCase()).append("\n");
            assignmentsInfo.append("=".repeat(80)).append("\n\n");
            
            if (assignments.isEmpty()) {
                assignmentsInfo.append("No patients assigned to this doctor.\n");
            } else {
                assignmentsInfo.append(String.format("%-10s | %-20s | %-15s | %-10s\n",
                    "Patient ID", "Patient Name", "Date Assigned", "Status"));
                assignmentsInfo.append("-".repeat(80)).append("\n");
                
                for (Assignment assignment : assignments) {
                    assignmentsInfo.append(String.format("%-10s | %-20s | %-15s | %-10s\n",
                        assignment.getPatientId(),
                        assignment.getPatientName(),
                        assignment.getAssignmentDate(),
                        assignment.getStatus()
                    ));
                }
            }
            
            assignmentsInfo.append("\n📊 Total patients assigned: ").append(assignments.size());
            
            // Show in a dialog
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Assignment Details");
            alert.setHeaderText("Patients Assigned to Dr. " + selectedDoctor.getName());
            
            TextArea textArea = new TextArea(assignmentsInfo.toString());
            textArea.setEditable(false);
            textArea.setWrapText(true);
            textArea.setPrefRowCount(15);
            textArea.setPrefColumnCount(80);
            textArea.setStyle("-fx-font-family: 'Monospaced'; -fx-font-size: 12px;");
            
            alert.getDialogPane().setContent(textArea);
            alert.getDialogPane().setPrefSize(800, 500);
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Doctor Selected");
            alert.setContentText("Please select a doctor from the table to view assignment details.");
            alert.showAndWait();
        }
    }
    
    private void refreshDoctorList() {
        // Clear existing data
        doctorData.clear();
        
        // Get fresh data from database
        List<Staff> allDoctors = DoctorDatabase.getInstance().getAllDoctors();
        doctorData.addAll(allDoctors);
        
        // Set the items to table
        doctorTable.setItems(doctorData);
        doctorTable.refresh();
        
        // Print to console for debugging
        System.out.println("\n" + "=".repeat(50));
        System.out.println("DOCTOR DATABASE REFRESHED");
        System.out.println("=".repeat(50));
        System.out.println("Total Doctors: " + allDoctors.size());
        System.out.println("Total Assignments: " + DoctorDatabase.getInstance().getAllAssignments().size());
        System.out.println("=".repeat(50));
    }

    public VBox getForm() {
        return container;
    }
}