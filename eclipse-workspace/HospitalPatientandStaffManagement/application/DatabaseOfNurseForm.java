package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.beans.property.SimpleStringProperty;

public class DatabaseOfNurseForm {

    private VBox container;
    private TableView<Staff> nurseTable;
    private ObservableList<Staff> nurseData;

    public DatabaseOfNurseForm() {
        nurseData = FXCollections.observableArrayList();
        initializeForm();
    }
    
    private void initializeForm() {
        container = new VBox();
        container.setSpacing(20);
        container.setPadding(new Insets(20));
        container.setStyle("-fx-background-color: #f8f9fa;");

        Label title = new Label("ALL NURSE STATUS");
        title.setFont(new Font("Arial", 28));
        title.setStyle("-fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        // Table setup for nurses
        nurseTable = new TableView<>();
        nurseTable.setPrefWidth(900);
        
        // Get nurses from NurseDatabase
        refreshNurseList();

        // Define columns for nurse information
        TableColumn<Staff, String> idCol = new TableColumn<>("Nurse ID");
        idCol.setCellValueFactory(cellData -> {
            Staff nurse = cellData.getValue();
            String id = nurse.getStaffId();
            return new SimpleStringProperty(id != null ? id : "N/A");
        });
        
        TableColumn<Staff, String> nameCol = new TableColumn<>("Nurse Name");
        nameCol.setCellValueFactory(cellData -> {
            Staff nurse = cellData.getValue();
            String name = nurse.getName();
            return new SimpleStringProperty(name != null ? name : "N/A");
        });
        
        TableColumn<Staff, String> specializationCol = new TableColumn<>("Specialization");
        specializationCol.setCellValueFactory(cellData -> {
            Staff nurse = cellData.getValue();
            String spec = nurse.getSpecialization();
            return new SimpleStringProperty(spec != null ? spec : "N/A");
        });
        
        TableColumn<Staff, String> experienceCol = new TableColumn<>("Experience (Years)");
        experienceCol.setCellValueFactory(cellData -> 
            new SimpleStringProperty(String.valueOf(cellData.getValue().getExperience())));
        
        TableColumn<Staff, String> departmentCol = new TableColumn<>("Department");
        departmentCol.setCellValueFactory(cellData -> {
            Staff nurse = cellData.getValue();
            String dept = nurse.getDepartment();
            return new SimpleStringProperty(dept != null ? dept : "N/A");
        });
        
        TableColumn<Staff, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(cellData -> {
            Staff nurse = cellData.getValue();
            String status = nurse.getStatus();
            return new SimpleStringProperty(status != null ? status : "Active");
        });
        
        // Add columns to table
        nurseTable.getColumns().addAll(idCol, nameCol, specializationCol, experienceCol, departmentCol, statusCol);

        // Buttons
        HBox buttonBox = new HBox(15);
        
        Button updateStatusButton = new Button("Update Nurse Status");
        updateStatusButton.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white; -fx-font-weight: bold;");
        updateStatusButton.setOnAction(e -> updateNurseStatus());
        
        Button refreshButton = new Button("Refresh List");
        refreshButton.setStyle("-fx-background-color: #e67e22; -fx-text-fill: white; -fx-font-weight: bold;");
        refreshButton.setOnAction(e -> refreshNurseList());

        buttonBox.getChildren().addAll(updateStatusButton, refreshButton);

        container.getChildren().addAll(title, nurseTable, buttonBox);
    }
    
    private void updateNurseStatus() {
        Staff selectedNurse = nurseTable.getSelectionModel().getSelectedItem();
        if (selectedNurse != null) {
            // Create a dialog to update status
            Dialog<String> dialog = new Dialog<>();
            dialog.setTitle("Update Nurse Status");
            dialog.setHeaderText("Update status for: " + selectedNurse.getName());
            
            // Set the button types
            ButtonType updateButtonType = new ButtonType("Update", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(updateButtonType, ButtonType.CANCEL);
            
            // Create the status ComboBox
            ComboBox<String> statusCombo = new ComboBox<>();
            statusCombo.getItems().addAll("Active", "Available", "On Leave", "Busy", "Off Duty", "On Break", "At Capacity");
            statusCombo.setValue(selectedNurse.getStatus() != null ? selectedNurse.getStatus() : "Active");
            
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
                // Update the nurse status
                selectedNurse.setStatus(newStatus);
                refreshNurseList();
                
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Status Updated");
                alert.setHeaderText("Nurse Status Updated");
                alert.setContentText(selectedNurse.getName() + "'s status has been updated to: " + newStatus);
                alert.showAndWait();
            });
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Nurse Selected");
            alert.setContentText("Please select a nurse from the table to update their status.");
            alert.showAndWait();
        }
    }
    
    private void refreshNurseList() {
        // Refresh the table with current data from NurseDatabase
        nurseTable.setItems(
            FXCollections.observableArrayList(NurseDatabase.getInstance().getAllNurses())
        );
        nurseTable.refresh();
        
        // Show summary statistics
        int totalNurses = NurseDatabase.getInstance().getNurseCount();
        int availableNurses = NurseDatabase.getInstance().getAvailableNurseCount();
        
        System.out.println("\n" + "=".repeat(50));
        System.out.println("NURSE DATABASE REFRESHED");
        System.out.println("=".repeat(50));
        System.out.println("Total Nurses: " + totalNurses);
        System.out.println("Available Nurses: " + availableNurses);
        System.out.println("=".repeat(50));
    }

    public VBox getForm() {
        return container;
    }
}