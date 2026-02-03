package application;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.List;

public class GetPatientForm {
   
    private VBox container;
    private TableView<Patients> patientTable;
    private ObservableList<Patients> patientData;
    private TextField searchField;
    private ComboBox<String> searchTypeComboBox;
    
    public GetPatientForm() {
        patientData = FXCollections.observableArrayList();
        initializeForm();
    }
    
    private void initializeForm() {
        container = new VBox();
        container.setSpacing(20);
        container.setPadding(new Insets(20));
        container.setStyle("-fx-background-color: #f8f9fa;");
        
        // Title
        Label title = new Label("PATIENT DATABASE");
        title.setFont(new Font("Arial", 28));
        title.setStyle("-fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        
        // Search Panel
        HBox searchPanel = new HBox(15);
        searchPanel.setPadding(new Insets(15));
        searchPanel.setStyle("-fx-background-color: white; -fx-border-color: #dee2e6; -fx-border-radius: 8;");
        
        searchTypeComboBox = new ComboBox<>();
        searchTypeComboBox.getItems().addAll("Search by ID", "Search by Name", "View All Patients");
        searchTypeComboBox.setValue("View All Patients");
        searchTypeComboBox.setPrefWidth(150);
        
        searchField = new TextField();
        searchField.setPromptText("Enter search term...");
        searchField.setPrefWidth(250);
        
        Button searchButton = new Button("Search");
        searchButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 8 20;");
        searchButton.setOnAction(e -> searchPatients());
        
        Button refreshButton = new Button("Refresh List");
        refreshButton.setStyle("-fx-background-color: #e67e22; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 8 20;");
        refreshButton.setOnAction(e -> refreshPatientList());
        
        searchPanel.getChildren().addAll(
            new Label("Search Type:"),
            searchTypeComboBox,
            searchField,
            searchButton,
            refreshButton
        );
        
        // Table setup
        patientTable = new TableView<>();
        patientTable.setPrefWidth(900);
        
        // Define columns for patient information
        TableColumn<Patients, String> idCol = new TableColumn<>("Patient ID");
        idCol.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getId()));
        idCol.setPrefWidth(100);
        
        TableColumn<Patients, String> nameCol = new TableColumn<>("Patient Name");
        nameCol.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getName()));
        nameCol.setPrefWidth(200);
        
        TableColumn<Patients, String> ageCol = new TableColumn<>("Age");
        ageCol.setCellValueFactory(cellData -> 
            new SimpleStringProperty(String.valueOf(cellData.getValue().getAge())));
        ageCol.setPrefWidth(80);
        
        TableColumn<Patients, String> genderCol = new TableColumn<>("Gender");
        genderCol.setCellValueFactory(cellData -> {
            String gender = cellData.getValue().getGender();
            return new SimpleStringProperty(gender != null ? gender : "N/A");
        });
        genderCol.setPrefWidth(100);
        
        TableColumn<Patients, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().isAdmitted() ? "Admitted" : "Outpatient"));
        statusCol.setPrefWidth(120);
        
        TableColumn<Patients, String> doctorCol = new TableColumn<>("Assigned Doctor");
        doctorCol.setCellValueFactory(cellData -> {
            String doctor = cellData.getValue().getAssignedDoctorName();
            return new SimpleStringProperty(doctor != null ? doctor : "Not Assigned");
        });
        doctorCol.setPrefWidth(200);
        
        // Add columns to table
        patientTable.getColumns().addAll(idCol, nameCol, ageCol, genderCol, statusCol, doctorCol);
        
        // Sort by ID column initially
        patientTable.getSortOrder().add(idCol);
        
        // Buttons for actions
        HBox buttonBox = new HBox(15);
        
        Button viewDetailsButton = new Button("View Patient Details");
        viewDetailsButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-weight: bold;");
        viewDetailsButton.setOnAction(e -> viewPatientDetails());
        
        Button updateStatusButton = new Button("Update Admission Status");
        updateStatusButton.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white; -fx-font-weight: bold;");
        updateStatusButton.setOnAction(e -> updatePatientStatus());
        
        Button deleteButton = new Button("Delete Patient");
        deleteButton.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-weight: bold;");
        deleteButton.setOnAction(e -> deletePatient());
        
        Button clearSearchButton = new Button("Clear Search");
        clearSearchButton.setStyle("-fx-background-color: #95a5a6; -fx-text-fill: white; -fx-font-weight: bold;");
        clearSearchButton.setOnAction(e -> {
            searchField.clear();
            refreshPatientList();
        });
        
        buttonBox.getChildren().addAll(viewDetailsButton, updateStatusButton, deleteButton, clearSearchButton);
        
        // Add all components to container
        container.getChildren().addAll(title, searchPanel, patientTable, buttonBox);
        
        // Initial load
        refreshPatientList();
    }
    
    private void searchPatients() {
        String searchTerm = searchField.getText().trim();
        String searchType = searchTypeComboBox.getValue();
        
        List<Patients> results;
        PatientDatabase patientDatabase = PatientDatabase.getInstance();
        
        if (searchType.equals("View All Patients")) {
            results = patientDatabase.getAllPatients();
        } else {
            if (searchTerm.isEmpty()) {
                showAlert("Search Error", "Please enter a search term");
                return;
            }
            
            switch (searchType) {
                case "Search by ID":
                    results = patientDatabase.searchById(searchTerm);
                    break;
                case "Search by Name":
                    results = patientDatabase.searchByName(searchTerm);
                    break;
                default:
                    results = patientDatabase.getAllPatients();
                    break;
            }
        }
        
        updateTable(results);
    }
    
    private void updateTable(List<Patients> patients) {
        patientData.clear();
        patientData.addAll(patients);
        patientTable.setItems(patientData);
        patientTable.sort();
    }
    
    private void refreshPatientList() {
        List<Patients> allPatients = PatientDatabase.getInstance().getAllPatients();
        updateTable(allPatients);
        
        // Show summary statistics in console
        int totalPatients = allPatients.size();
        int admittedPatients = 0;
        
        for (Patients patient : allPatients) {
            if (patient.isAdmitted()) {
                admittedPatients++;
            }
        }
        
        System.out.println("\n" + "=".repeat(50));
        System.out.println("PATIENT DATABASE REFRESHED");
        System.out.println("=".repeat(50));
        System.out.println("Total Patients: " + totalPatients);
        System.out.println("Admitted Patients: " + admittedPatients);
        System.out.println("Outpatients: " + (totalPatients - admittedPatients));
        System.out.println("=".repeat(50));
    }
    
    private void viewPatientDetails() {
        Patients selectedPatient = patientTable.getSelectionModel().getSelectedItem();
        if (selectedPatient != null) {
            // Create a dialog to show patient details
            Dialog<Void> dialog = new Dialog<>();
            dialog.setTitle("Patient Details");
            dialog.setHeaderText("Details for: " + selectedPatient.getName() + " (ID: " + selectedPatient.getId() + ")");
            
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
            
            // Create scrollable content with text that can be copied
            VBox content = new VBox(10);
            content.setPadding(new Insets(20));
            
            // Create TextArea for copyable text
            TextArea detailsText = new TextArea();
            detailsText.setEditable(false);
            detailsText.setWrapText(true);
            detailsText.setPrefRowCount(15);
            detailsText.setPrefWidth(400);
            detailsText.setStyle("-fx-font-family: 'Consolas', 'Monospaced'; -fx-font-size: 12px;");
            
            // Format patient details as plain text that can be copied
            StringBuilder details = new StringBuilder();
            details.append("=== PATIENT DETAILS ===\n\n");
            details.append("PATIENT ID: ").append(selectedPatient.getId()).append("\n");
            details.append("NAME: ").append(selectedPatient.getName()).append("\n");
            details.append("AGE: ").append(selectedPatient.getAge()).append("\n");
            details.append("GENDER: ").append(selectedPatient.getGender() != null ? selectedPatient.getGender() : "Not specified").append("\n");
            details.append("BLOOD GROUP: ").append(selectedPatient.getBloodGroup() != null ? selectedPatient.getBloodGroup() : "Not specified").append("\n");
            details.append("STATUS: ").append(selectedPatient.isAdmitted() ? "ADMITTED" : "OUTPATIENT").append("\n\n");
            
            details.append("--- CONTACT INFORMATION ---\n");
            details.append("PHONE: ").append(selectedPatient.getPhone() != null ? selectedPatient.getPhone() : "Not specified").append("\n");
            details.append("ADDRESS: ").append(selectedPatient.getAddress() != null ? selectedPatient.getAddress() : "Not specified").append("\n\n");
            
            details.append("--- MEDICAL INFORMATION ---\n");
            details.append("ASSIGNED DOCTOR: ").append(selectedPatient.getAssignedDoctorName() != null ? selectedPatient.getAssignedDoctorName() : "Not assigned").append("\n");
            
            if (selectedPatient.getMedicalHistory() != null && !selectedPatient.getMedicalHistory().isEmpty()) {
                details.append("\nMEDICAL HISTORY:\n");
                for (String history : selectedPatient.getMedicalHistory()) {
                    details.append("• ").append(history).append("\n");
                }
            } else {
                details.append("\nMEDICAL HISTORY: No medical history recorded\n");
            }
            
            detailsText.setText(details.toString());
            
            // Add copy button
            Button copyButton = new Button("Copy to Clipboard");
            copyButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-weight: bold;");
            copyButton.setOnAction(e -> {
                Clipboard clipboard = Clipboard.getSystemClipboard();
                ClipboardContent contentClip = new ClipboardContent();
                contentClip.putString(detailsText.getText());
                clipboard.setContent(contentClip);
                
                Alert copiedAlert = new Alert(Alert.AlertType.INFORMATION);
                copiedAlert.setTitle("Copied");
                copiedAlert.setHeaderText("Text Copied");
                copiedAlert.setContentText("Patient details copied to clipboard!");
                copiedAlert.showAndWait();
            });
            
            HBox buttonBox = new HBox(10, copyButton);
            buttonBox.setPadding(new Insets(10, 0, 0, 0));
            
            content.getChildren().addAll(detailsText, buttonBox);
            
            // Create a scroll pane in case content is too long
            ScrollPane scrollPane = new ScrollPane(content);
            scrollPane.setFitToWidth(true);
            scrollPane.setPrefHeight(400);
            
            dialog.getDialogPane().setContent(scrollPane);
            dialog.getDialogPane().setPrefSize(450, 450);
            dialog.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Patient Selected");
            alert.setContentText("Please select a patient from the table to view details.");
            alert.showAndWait();
        }
    }
    
    private void updatePatientStatus() {
        Patients selectedPatient = patientTable.getSelectionModel().getSelectedItem();
        if (selectedPatient != null) {
            // Create a dialog to update status
            Dialog<String> dialog = new Dialog<>();
            dialog.setTitle("Update Patient Status");
            dialog.setHeaderText("Update status for: " + selectedPatient.getName());
            
            // Set the button types
            ButtonType updateButtonType = new ButtonType("Update", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(updateButtonType, ButtonType.CANCEL);
            
            // Create the status ComboBox
            ComboBox<String> statusCombo = new ComboBox<>();
            statusCombo.getItems().addAll("Admit Patient", "Discharge Patient");
            statusCombo.setValue(selectedPatient.isAdmitted() ? "Discharge Patient" : "Admit Patient");
            
            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(20, 150, 10, 10));
            
            grid.add(new Label("Action:"), 0, 0);
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
            dialog.showAndWait().ifPresent(action -> {
                if (action.equals("Admit Patient")) {
                    selectedPatient.setAdmitted(true);
                } else {
                    selectedPatient.setAdmitted(false);
                }
                
                refreshPatientList();
                
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Status Updated");
                alert.setHeaderText("Patient Status Updated");
                alert.setContentText(selectedPatient.getName() + " has been " + 
                                   (action.equals("Admit Patient") ? "admitted" : "discharged"));
                alert.showAndWait();
            });
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Patient Selected");
            alert.setContentText("Please select a patient from the table to update their status.");
            alert.showAndWait();
        }
    }
    
    private void deletePatient() {
        Patients selectedPatient = patientTable.getSelectionModel().getSelectedItem();
        if (selectedPatient != null) {
            // Create a confirmation dialog
            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmAlert.setTitle("Delete Patient");
            confirmAlert.setHeaderText("Delete Patient: " + selectedPatient.getName());
            confirmAlert.setContentText("Are you sure you want to delete this patient?\n" +
                                      "Patient ID: " + selectedPatient.getId() + "\n" +
                                      "Name: " + selectedPatient.getName() + "\n" +
                                      "This action cannot be undone and all patient data will be lost.");
            
            // Show confirmation dialog
            confirmAlert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    // Delete from database
                    boolean deleted = PatientDatabase.getInstance().deletePatient(selectedPatient.getId());
                    
                    if (deleted) {
                        refreshPatientList();
                        
                        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                        successAlert.setTitle("Patient Deleted");
                        successAlert.setHeaderText("Patient Deleted Successfully");
                        successAlert.setContentText("Patient " + selectedPatient.getName() + 
                                                 " (ID: " + selectedPatient.getId() + 
                                                 ") has been deleted from the database.");
                        successAlert.showAndWait();
                    } else {
                        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                        errorAlert.setTitle("Delete Failed");
                        errorAlert.setHeaderText("Failed to Delete Patient");
                        errorAlert.setContentText("Could not delete patient from database.");
                        errorAlert.showAndWait();
                    }
                }
            });
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Patient Selected");
            alert.setContentText("Please select a patient from the table to delete.");
            alert.showAndWait();
        }
    }
    
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    public VBox getForm() {
        return container;
    }
}