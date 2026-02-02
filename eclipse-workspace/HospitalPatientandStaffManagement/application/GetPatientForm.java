package application;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import java.util.List;

public class GetPatientForm {
    
    private VBox formContainer;
    private TextField searchField;
    private ComboBox<String> searchTypeComboBox;
    private Button searchButton;
    private VBox resultsContainer;
    private TextArea detailsArea;
    private Button clearButton;
    private PatientDatabase patientDatabase;
    
    public GetPatientForm() {
        initializeForm();
        patientDatabase = PatientDatabase.getInstance();
    }
    private void initializeForm() {
        formContainer = new VBox(20);
        formContainer.setPadding(new Insets(20));
        formContainer.setStyle("-fx-background-color: #f8f9fa;");
        
        // Title
        Label title = new Label("Find / View Patient");
        title.setFont(new Font("Arial", 28));
        title.setStyle("-fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        
        // Search Panel
        HBox searchPanel = new HBox(10);
        searchPanel.setPadding(new Insets(15));
        searchPanel.setStyle("-fx-background-color: white; -fx-border-color: #dee2e6; -fx-border-radius: 8;");
        
        searchTypeComboBox = new ComboBox<>();
        searchTypeComboBox.getItems().addAll("Search by ID", "Search by Name", "Search by Phone", "View All Patients");
        searchTypeComboBox.setValue("Search by ID");
        searchTypeComboBox.setPrefWidth(150);
        
        searchField = new TextField();
        searchField.setPromptText("Enter search term...");
        searchField.setPrefWidth(250);
        
        searchButton = new Button("🔍 Search");
        searchButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 8 20;");
        searchButton.setOnAction(e -> searchPatient());
        
        clearButton = new Button("Clear");
        clearButton.setStyle("-fx-background-color: #95a5a6; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 8 20;");
        clearButton.setOnAction(e -> {
            searchField.clear();
            resultsContainer.setVisible(false);
        });
        
        // Add database status button
        Button dbStatusButton = new Button("📊 DB Status");
        dbStatusButton.setStyle("-fx-background-color: #9b59b6; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 8 15;");
        dbStatusButton.setOnAction(e -> showDatabaseStatus());
        
        searchPanel.getChildren().addAll(
            new Label("Search:"),
            searchTypeComboBox,
            searchField,
            searchButton,
            clearButton,
            dbStatusButton
        );
        
        // Results Container
        resultsContainer = new VBox(15);
        resultsContainer.setPadding(new Insets(15));
        resultsContainer.setStyle("-fx-background-color: white; -fx-border-color: #dee2e6; -fx-border-radius: 8;");
        
        // Patient Details Area
        detailsArea = new TextArea();
        detailsArea.setPrefRowCount(15);
        detailsArea.setEditable(false);
        detailsArea.setWrapText(true);
        detailsArea.setStyle("-fx-font-family: 'Monospaced'; -fx-font-size: 12px;");
        
        Label resultsLabel = new Label("Patient Details:");
        resultsLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 16px; -fx-text-fill: #2c3e50;");
        
        resultsContainer.getChildren().addAll(resultsLabel, detailsArea);
        resultsContainer.setVisible(false);
        
        // Add all to form
        formContainer.getChildren().addAll(title, searchPanel, resultsContainer);
    }
    
    private void searchPatient() {
        String searchTerm = searchField.getText().trim();
        String searchType = searchTypeComboBox.getValue();
        
        List<Patients> results;
        
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
        
        displayResults(results);
    }
    
    private void displayResults(List<Patients> patients) {
        if (patients.isEmpty()) {
            detailsArea.setText("No patients found matching your search criteria.");
            resultsContainer.setVisible(true);
            return;
        }
        
        StringBuilder details = new StringBuilder();
        
        if (patients.size() == 1) {
            Patients patient = patients.get(0);
            details.append("╔══════════════════════════════════════════════════════════╗\n");
            details.append("║                    PATIENT DETAILS                       ║\n");
            details.append("╚══════════════════════════════════════════════════════════╝\n\n");
            
            details.append("┌──────────────────────────────────────────────────────────┐\n");
            details.append("│ BASIC INFORMATION                                        │\n");
            details.append("├──────────────────────────────────────────────────────────┤\n");
            details.append("│ Patient ID: ").append(patient.getId()).append("\n");
            details.append("│ Name: ").append(patient.getName()).append("\n");
            details.append("│ Age: ").append(patient.getAge()).append("\n");
            details.append("│ Gender: ").append(patient.getGender() != null ? patient.getGender() : "Not specified").append("\n");
            details.append("│ Blood Group: ").append(patient.getBloodGroup() != null ? patient.getBloodGroup() : "Not specified").append("\n");
            details.append("│ Status: ").append(patient.isAdmitted() ? "● ADMITTED" : "○ OUTPATIENT").append("\n");
            details.append("└──────────────────────────────────────────────────────────┘\n\n");
            
            details.append("┌──────────────────────────────────────────────────────────┐\n");
            details.append("│ CONTACT INFORMATION                                      │\n");
            details.append("├──────────────────────────────────────────────────────────┤\n");
            details.append("│ Address: ").append(patient.getAddress() != null ? patient.getAddress() : "Not specified").append("\n");
            details.append("│ Phone: ").append(patient.getPhone() != null ? patient.getPhone() : "Not specified").append("\n");
            details.append("└──────────────────────────────────────────────────────────┘\n\n");
            
            details.append("┌──────────────────────────────────────────────────────────┐\n");
            details.append("│ MEDICAL INFORMATION                                      │\n");
            details.append("├──────────────────────────────────────────────────────────┤\n");
            details.append("│ Assigned Doctor: ").append(patient.getAssignedDoctorName() != null ? patient.getAssignedDoctorName() : "Not assigned").append("\n");
            details.append("├──────────────────────────────────────────────────────────┤\n");
            details.append("│ MEDICAL HISTORY:\n");
            if (patient.getMedicalHistory() == null || patient.getMedicalHistory().isEmpty()) {
                details.append("│   No medical history recorded\n");
            } else {
                details.append("│   ").append(patient.getMedicalHistory()).append("\n");
            }
            details.append("└──────────────────────────────────────────────────────────┘\n");
            
        } else {
            details.append("╔══════════════════════════════════════════════════════════╗\n");
            details.append("║                SEARCH RESULTS (").append(patients.size()).append(" patients)               ║\n");
            details.append("╚══════════════════════════════════════════════════════════╝\n\n");
            
            details.append("┌────┬────────────┬─────────────────────┬─────┬─────────────┬────────────┬─────────────────────┐\n");
            details.append("│ No │    ID      │        Name         │ Age │    Gender   │   Status   │       Doctor        │\n");
            details.append("├────┼────────────┼─────────────────────┼─────┼─────────────┼────────────┼─────────────────────┤\n");
            
            for (int i = 0; i < patients.size(); i++) {
                Patients patient = patients.get(i);
                String gender = patient.getGender();
                if (gender == null || gender.isEmpty()) {
                    gender = "-";
                } else if (gender.length() > 6) {
                    gender = gender.substring(0, 4) + ".";
                }
                
                details.append(String.format("│ %2d │ %-10s │ %-19s │ %3d │ %-11s │ %-10s │ %-19s │\n",
                    i + 1,
                    patient.getId(),
                    patient.getName().length() > 19 ? patient.getName().substring(0, 16) + "..." : patient.getName(),
                    patient.getAge(),
                    gender,
                    patient.isAdmitted() ? "Admitted" : "Outpatient",
                    patient.getAssignedDoctorName() != null ? 
                        (patient.getAssignedDoctorName().length() > 19 ? 
                         patient.getAssignedDoctorName().substring(0, 16) + "..." : 
                         patient.getAssignedDoctorName()) : 
                        "Not assigned"
                ));
            }
            
            details.append("└────┴────────────┴─────────────────────┴─────┴─────────────┴────────────┴─────────────────────┘\n\n");
            details.append("Note: Select 'Search by ID' with a specific ID to view full details.\n");
        }
        
        detailsArea.setText(details.toString());
        resultsContainer.setVisible(true);
    }
    
    private void showDatabaseStatus() {
        List<Patients> allPatients = patientDatabase.getAllPatients();
        int patientCount = allPatients.size();
        
        StringBuilder status = new StringBuilder();
        
        status.append("╔══════════════════════════════════════════════════════════╗\n");
        status.append("║              PATIENT DATABASE STATUS                     ║\n");
        status.append("╚══════════════════════════════════════════════════════════╝\n\n");
        status.append("Total patients in database: ").append(patientCount).append("\n\n");
        
        if (patientCount > 0) {
            status.append("Patient IDs in database:\n");
            for (int i = 0; i < allPatients.size(); i++) {
                Patients p = allPatients.get(i);
                status.append(String.format("%2d. %s - %s (Age: %d)\n", 
                    i + 1, p.getId(), p.getName(), p.getAge()));
            }
        } else {
            status.append("Database is empty. Add patients using the 'Add Patient' form.\n");
        }
        
        detailsArea.setText(status.toString());
        resultsContainer.setVisible(true);
    }
    
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    public VBox getForm() {
        return formContainer;
    }
}