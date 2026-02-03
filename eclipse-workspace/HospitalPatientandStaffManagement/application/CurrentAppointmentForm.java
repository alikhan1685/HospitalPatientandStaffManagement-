package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.beans.property.SimpleStringProperty;
import java.util.List;

public class CurrentAppointmentForm {

    private VBox container;
    private TableView<Appointment> appointmentTable;
    private ObservableList<Appointment> appointmentData;

    public CurrentAppointmentForm() {
        appointmentData = FXCollections.observableArrayList();
        initializeForm();
        // Load appointments from database immediately
        refreshAppointmentList();
    }
    
    private void initializeForm() {
        container = new VBox();
        container.setSpacing(20);
        container.setPadding(new Insets(20));
        container.setStyle("-fx-background-color: #f8f9fa;");

        Label title = new Label("CURRENT APPOINTMENT STATUS");
        title.setFont(new Font("Arial", 28));
        title.setStyle("-fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        // Table setup
        appointmentTable = new TableView<>();
        appointmentTable.setPrefWidth(900);
        
        // Define columns for appointment information
        TableColumn<Appointment, String> idCol = new TableColumn<>("Appointment ID");
        idCol.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getAppointmentId()));
        idCol.setPrefWidth(120);
        
        TableColumn<Appointment, String> patientIdCol = new TableColumn<>("Patient ID");
        patientIdCol.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getPatientId()));
        patientIdCol.setPrefWidth(80);
        
        TableColumn<Appointment, String> patientCol = new TableColumn<>("Patient Name");
        patientCol.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getPatientName()));
        patientCol.setPrefWidth(150);
        
        TableColumn<Appointment, String> doctorCol = new TableColumn<>("Doctor");
        doctorCol.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getDoctor()));
        doctorCol.setPrefWidth(200);
        
        TableColumn<Appointment, String> typeCol = new TableColumn<>("Appointment Type");
        typeCol.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getAppointmentType()));
        typeCol.setPrefWidth(150);
        
        TableColumn<Appointment, String> dateCol = new TableColumn<>("Date");
        dateCol.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getDate().toString()));
        dateCol.setPrefWidth(100);
        
        TableColumn<Appointment, String> timeCol = new TableColumn<>("Time");
        timeCol.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getTime()));
        timeCol.setPrefWidth(80);
        
        TableColumn<Appointment, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getStatus()));
        statusCol.setPrefWidth(100);

        // Add columns to table
        appointmentTable.getColumns().addAll(idCol, patientIdCol, patientCol, doctorCol, 
                                            typeCol, dateCol, timeCol, statusCol);
        
        // Set the items
        appointmentTable.setItems(appointmentData);

        // Buttons
        HBox buttonBox = new HBox(15);
        
        Button updateStatusButton = new Button("Update Status");
        updateStatusButton.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white; -fx-font-weight: bold;");
        updateStatusButton.setOnAction(e -> updateAppointmentStatus());
        
        Button deleteButton = new Button("Delete Appointment");
        deleteButton.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-weight: bold;");
        deleteButton.setOnAction(e -> deleteAppointment());
        
        Button refreshButton = new Button("Refresh List");
        refreshButton.setStyle("-fx-background-color: #e67e22; -fx-text-fill: white; -fx-font-weight: bold;");
        refreshButton.setOnAction(e -> refreshAppointmentList());

        buttonBox.getChildren().addAll(updateStatusButton, deleteButton, refreshButton);

        container.getChildren().addAll(title, appointmentTable, buttonBox);
    }
    
    private void updateAppointmentStatus() {
        Appointment selectedAppointment = appointmentTable.getSelectionModel().getSelectedItem();
        if (selectedAppointment != null) {
            // Create a dialog to update status
            Dialog<String> dialog = new Dialog<>();
            dialog.setTitle("Update Appointment Status");
            dialog.setHeaderText("Update status for appointment: " + selectedAppointment.getAppointmentId());
            
            // Set the button types
            ButtonType updateButtonType = new ButtonType("Update", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(updateButtonType, ButtonType.CANCEL);
            
            // Create the status ComboBox
            ComboBox<String> statusCombo = new ComboBox<>();
            statusCombo.getItems().addAll("Scheduled", "Confirmed", "In Progress", "Completed", "Cancelled", "No Show");
            statusCombo.setValue(selectedAppointment.getStatus());
            
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
                selectedAppointment.setStatus(newStatus);
                refreshAppointmentList();
                
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Status Updated");
                alert.setHeaderText("Appointment Status Updated");
                alert.setContentText("Appointment " + selectedAppointment.getAppointmentId() + 
                                   " status has been updated to: " + newStatus);
                alert.showAndWait();
            });
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Appointment Selected");
            alert.setContentText("Please select an appointment from the table to update its status.");
            alert.showAndWait();
        }
    }
    
    private void deleteAppointment() {
        Appointment selectedAppointment = appointmentTable.getSelectionModel().getSelectedItem();
        if (selectedAppointment != null) {
            // Create a confirmation dialog
            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmAlert.setTitle("Delete Appointment");
            confirmAlert.setHeaderText("Delete Appointment: " + selectedAppointment.getAppointmentId());
            confirmAlert.setContentText("Are you sure you want to delete this appointment?\n" +
                                      "Patient: " + selectedAppointment.getPatientName() + "\n" +
                                      "Doctor: " + selectedAppointment.getDoctor() + "\n" +
                                      "This action cannot be undone.");
            
            // Show confirmation dialog
            confirmAlert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    // Remove from database
                    boolean deleted = AppointmentDatabase.getInstance().deleteAppointment(selectedAppointment.getAppointmentId());
                    
                    if (deleted) {
                        refreshAppointmentList();
                        
                        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                        successAlert.setTitle("Appointment Deleted");
                        successAlert.setHeaderText("Appointment Deleted Successfully");
                        successAlert.setContentText("Appointment " + selectedAppointment.getAppointmentId() + 
                                                 " has been deleted from the database.");
                        successAlert.showAndWait();
                    }
                }
            });
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Appointment Selected");
            alert.setContentText("Please select an appointment from the table to delete.");
            alert.showAndWait();
        }
    }
    
    private void refreshAppointmentList() {
        // Clear existing data
        appointmentData.clear();
        
        // Get fresh data from database
        List<Appointment> allAppointments = AppointmentDatabase.getInstance().getAllAppointments();
        appointmentData.addAll(allAppointments);
        
        // Set the items to table
        appointmentTable.setItems(appointmentData);
        appointmentTable.refresh();
        
        // Show summary statistics
        int totalAppointments = AppointmentDatabase.getInstance().getAppointmentCount();
        int scheduledAppointments = AppointmentDatabase.getInstance().getScheduledAppointmentCount();
        
        System.out.println("\n" + "=".repeat(50));
        System.out.println("APPOINTMENT DATABASE REFRESHED");
        System.out.println("=".repeat(50));
        System.out.println("Total Appointments: " + totalAppointments);
        System.out.println("Scheduled Appointments: " + scheduledAppointments);
        System.out.println("=".repeat(50));
    }

    public VBox getForm() {
        return container;
    }
}
