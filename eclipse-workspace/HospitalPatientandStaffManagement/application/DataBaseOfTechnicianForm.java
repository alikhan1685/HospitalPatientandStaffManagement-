package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.beans.property.SimpleStringProperty;

public class DataBaseOfTechnicianForm {

    private VBox container;
    private TableView<Staff> technicianTable;
    private ObservableList<Staff> technicianData;

    public DataBaseOfTechnicianForm() {
        technicianData = FXCollections.observableArrayList();
        initializeForm();
    }
    
    private void initializeForm() {
        container = new VBox();
        container.setSpacing(20);
        container.setPadding(new Insets(20));
        container.setStyle("-fx-background-color: #f8f9fa;");

        Label title = new Label("ALL TECHNICIAN STATUS");
        title.setFont(new Font("Arial", 28));
        title.setStyle("-fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        // Table setup for technicians
        technicianTable = new TableView<>();
        technicianTable.setPrefWidth(900);
        
        // Get technicians from TechnicianDatabase
        technicianTable.setItems(
            FXCollections.observableArrayList(TechnicianDatabase.getInstance().getAllTechnicians())
        );

        // Define columns for technician information
        TableColumn<Staff, String> idCol = new TableColumn<>("Technician ID");
        idCol.setCellValueFactory(cellData -> {
            Staff technician = cellData.getValue();
            String id = technician.getStaffId();
            return new SimpleStringProperty(id != null ? id : "N/A");
        });
        
        TableColumn<Staff, String> nameCol = new TableColumn<>("Technician Name");
        nameCol.setCellValueFactory(cellData -> {
            Staff technician = cellData.getValue();
            String name = technician.getName();
            return new SimpleStringProperty(name != null ? name : "N/A");
        });
        
        TableColumn<Staff, String> specializationCol = new TableColumn<>("Specialization");
        specializationCol.setCellValueFactory(cellData -> {
            Staff technician = cellData.getValue();
            String spec = technician.getSpecialization();
            return new SimpleStringProperty(spec != null ? spec : "N/A");
        });
        
        TableColumn<Staff, String> experienceCol = new TableColumn<>("Experience (Years)");
        experienceCol.setCellValueFactory(cellData -> 
            new SimpleStringProperty(String.valueOf(cellData.getValue().getExperience())));
        
        TableColumn<Staff, String> departmentCol = new TableColumn<>("Department");
        departmentCol.setCellValueFactory(cellData -> {
            Staff technician = cellData.getValue();
            String dept = technician.getDepartment();
            return new SimpleStringProperty(dept != null ? dept : "N/A");
        });
        
        TableColumn<Staff, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(cellData -> {
            Staff technician = cellData.getValue();
            String status = technician.getStatus();
            return new SimpleStringProperty(status != null ? status : "Active");
        });
        
        // Add columns to table
        technicianTable.getColumns().addAll(idCol, nameCol, specializationCol, experienceCol, departmentCol, statusCol);

        // Buttons
        HBox buttonBox = new HBox(15);
        
        Button updateStatusButton = new Button("Update Technician Status");
        updateStatusButton.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white; -fx-font-weight: bold;");
        updateStatusButton.setOnAction(e -> updateTechnicianStatus());
        
        Button refreshButton = new Button("Refresh List");
        refreshButton.setStyle("-fx-background-color: #e67e22; -fx-text-fill: white; -fx-font-weight: bold;");
        refreshButton.setOnAction(e -> refreshTechnicianList());

        buttonBox.getChildren().addAll(updateStatusButton, refreshButton);

        container.getChildren().addAll(title, technicianTable, buttonBox);
    }
    
    private void updateTechnicianStatus() {
        Staff selectedTechnician = technicianTable.getSelectionModel().getSelectedItem();
        if (selectedTechnician != null) {
            // Create a dialog to update status
            Dialog<String> dialog = new Dialog<>();
            dialog.setTitle("Update Technician Status");
            dialog.setHeaderText("Update status for: " + selectedTechnician.getName());
            
            // Set the button types
            ButtonType updateButtonType = new ButtonType("Update", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(updateButtonType, ButtonType.CANCEL);
            
            // Create the status ComboBox
            ComboBox<String> statusCombo = new ComboBox<>();
            statusCombo.getItems().addAll("Active", "Available", "On Leave", "Busy", "Off Duty", "On Break", "At Capacity");
            statusCombo.setValue(selectedTechnician.getStatus() != null ? selectedTechnician.getStatus() : "Active");
            
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
                // Update the technician status
                selectedTechnician.setStatus(newStatus);
                refreshTechnicianList();
                
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Status Updated");
                alert.setHeaderText("Technician Status Updated");
                alert.setContentText(selectedTechnician.getName() + "'s status has been updated to: " + newStatus);
                alert.showAndWait();
            });
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Technician Selected");
            alert.setContentText("Please select a technician from the table to update their status.");
            alert.showAndWait();
        }
    }
    
    private void refreshTechnicianList() {
        // Refresh the table with current data from TechnicianDatabase
        technicianTable.setItems(
            FXCollections.observableArrayList(TechnicianDatabase.getInstance().getAllTechnicians())
        );
        technicianTable.refresh();
        
        // Show summary statistics
        int totalTechnicians = TechnicianDatabase.getInstance().getTechnicianCount();
        int availableTechnicians = TechnicianDatabase.getInstance().getAvailableTechnicianCount();
       
        System.out.println("\n" + "=".repeat(50));
        System.out.println("TECHNICIAN DATABASE REFRESHED");
        System.out.println("=".repeat(50));
        System.out.println("Total Technicians: " + totalTechnicians);
        System.out.println("Available Technicians: " + availableTechnicians);
        System.out.println("=".repeat(50));
    }

    public VBox getForm() {
        return container;
    }
}
