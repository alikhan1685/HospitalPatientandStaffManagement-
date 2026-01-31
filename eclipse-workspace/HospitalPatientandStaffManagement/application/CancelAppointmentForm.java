package application;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.geometry.Pos;

public class CancelAppointmentForm {
    
    private VBox formContainer;
    private TextField appointmentIdField;
    private TextArea reasonField;
    private TextArea details1Field;
    private TextArea details2Field;
    private TextArea details3Field;
    private ComboBox<String> cancellationTypeCombo;
    private DatePicker appointmentDatePicker;
    private CheckBox notifyPatientCheck;
    private CheckBox refundRequestCheck;
    
    public CancelAppointmentForm() {
        initializeForm();
    }
    
    private void initializeForm() {
        // Create form container
        formContainer = new VBox();
        formContainer.setSpacing(20);
        formContainer.setPadding(new Insets(20));
        formContainer.setStyle("-fx-background-color: #f8f9fa;");
        
        // Title
        Label title = new Label("Cancel Appointment");
        title.setFont(new Font("Arial", 28));
        title.setStyle("-fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        
        // Information message
        Label infoMessage = new Label("Please fill in all required details to cancel an appointment");
        infoMessage.setStyle("-fx-font-size: 14px; -fx-text-fill: #7f8c8d; -fx-padding: 0 0 10 0;");
        
        // Main form container
        GridPane formGrid = new GridPane();
        formGrid.setHgap(15);
        formGrid.setVgap(15);
        formGrid.setPadding(new Insets(20));
        formGrid.setStyle("-fx-background-color: white; -fx-border-radius: 8; -fx-border-color: #dee2e6; -fx-border-width: 1;");
        
        // Row counter
        int row = 0;
        
        // Appointment ID
        Label appointmentIdLabel = new Label("Appointment ID *");
        appointmentIdLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        appointmentIdField = new TextField();
        appointmentIdField.setPromptText("Enter appointment ID");
        appointmentIdField.setPrefWidth(250);
        formGrid.add(appointmentIdLabel, 0, row);
        formGrid.add(appointmentIdField, 1, row);
        
        row++;
        
        // Appointment Date
        Label dateLabel = new Label("Appointment Date");
        dateLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        appointmentDatePicker = new DatePicker();
        appointmentDatePicker.setPrefWidth(250);
        formGrid.add(dateLabel, 0, row);
        formGrid.add(appointmentDatePicker, 1, row);
        
        row++;
        
        // Cancellation Type
        Label cancelTypeLabel = new Label("Cancellation Type *");
        cancelTypeLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        cancellationTypeCombo = new ComboBox<>();
        cancellationTypeCombo.getItems().addAll(
            "Patient Request",
            "Doctor Unavailable", 
            "Emergency",
            "Rescheduled",
            "Clinic Holiday",
            "Other"
        );
        cancellationTypeCombo.setPromptText("Select cancellation type");
        cancellationTypeCombo.setPrefWidth(250);
        formGrid.add(cancelTypeLabel, 0, row);
        formGrid.add(cancellationTypeCombo, 1, row);
        
        row++;
        
        // Reason for Cancellation
        Label reasonLabel = new Label("Reason for Cancellation *");
        reasonLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        reasonField = new TextArea();
        reasonField.setPromptText("Please provide a detailed reason for cancellation...");
        reasonField.setPrefRowCount(3);
        reasonField.setWrapText(true);
        GridPane.setColumnSpan(reasonField, 2);
        formGrid.add(reasonLabel, 0, row, 2, 1);
        row++;
        formGrid.add(reasonField, 0, row, 2, 1);
        
        row++;
        
        // Additional Details Section Title
        Label detailsTitle = new Label("Additional Details");
        detailsTitle.setStyle("-fx-font-weight: bold; -fx-font-size: 16px; -fx-text-fill: #2c3e50; -fx-padding: 10 0 5 0;");
        GridPane.setColumnSpan(detailsTitle, 2);
        formGrid.add(detailsTitle, 0, row, 2, 1);
        
        row++;
        
        // Detail Box 1
        Label details1Label = new Label("Impact on Patient:");
        details1Label.setStyle("-fx-font-weight: bold; -fx-text-fill: #34495e;");
        details1Field = new TextArea();
        details1Field.setPromptText("Describe impact on patient care/treatment...");
        details1Field.setPrefRowCount(2);
        details1Field.setWrapText(true);
        formGrid.add(details1Label, 0, row);
        formGrid.add(details1Field, 1, row);
        
        row++;
        
        // Detail Box 2
        Label details2Label = new Label("Follow-up Actions:");
        details2Label.setStyle("-fx-font-weight: bold; -fx-text-fill: #34495e;");
        details2Field = new TextArea();
        details2Field.setPromptText("Describe required follow-up actions...");
        details2Field.setPrefRowCount(2);
        details2Field.setWrapText(true);
        formGrid.add(details2Label, 0, row);
        formGrid.add(details2Field, 1, row);
        
        row++;
        
        // Detail Box 3
        Label details3Label = new Label("Additional Notes:");
        details3Label.setStyle("-fx-font-weight: bold; -fx-text-fill: #34495e;");
        details3Field = new TextArea();
        details3Field.setPromptText("Any additional notes or comments...");
        details3Field.setPrefRowCount(2);
        details3Field.setWrapText(true);
        formGrid.add(details3Label, 0, row);
        formGrid.add(details3Field, 1, row);
        
        row++;
        
        // Options Section
        Label optionsTitle = new Label("Options");
        optionsTitle.setStyle("-fx-font-weight: bold; -fx-font-size: 16px; -fx-text-fill: #2c3e50; -fx-padding: 10 0 5 0;");
        GridPane.setColumnSpan(optionsTitle, 2);
        formGrid.add(optionsTitle, 0, row, 2, 1);
        
        row++;
        
        // Checkboxes
        notifyPatientCheck = new CheckBox("Notify patient via email/SMS");
        notifyPatientCheck.setSelected(true);
        notifyPatientCheck.setStyle("-fx-text-fill: #2c3e50;");
        
        refundRequestCheck = new CheckBox("Process refund if applicable");
        refundRequestCheck.setStyle("-fx-text-fill: #2c3e50;");
        
        VBox checkBoxContainer = new VBox(10, notifyPatientCheck, refundRequestCheck);
        GridPane.setColumnSpan(checkBoxContainer, 2);
        formGrid.add(checkBoxContainer, 0, row, 2, 1);
        
        // Button container
        HBox buttonContainer = new HBox(15);
        buttonContainer.setPadding(new Insets(20, 0, 0, 0));
        buttonContainer.setAlignment(Pos.CENTER);
        
        // Cancel Appointment Button
        Button cancelButton = new Button("Cancel Appointment");
        cancelButton.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 12 30; -fx-font-size: 14px; -fx-background-radius: 5;");
        cancelButton.setOnAction(e -> cancelAppointment());
        
        // Clear Form Button
        Button clearButton = new Button("Clear Form");
        clearButton.setStyle("-fx-background-color: #95a5a6; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 12 30; -fx-font-size: 14px; -fx-background-radius: 5;");
        clearButton.setOnAction(e -> clearForm());
        
        // Back Button
        Button backButton = new Button("Back");
        backButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 12 30; -fx-font-size: 14px; -fx-background-radius: 5;");
        backButton.setOnAction(e -> System.out.println("Navigating back..."));
        
        buttonContainer.getChildren().addAll(cancelButton, clearButton, backButton);
        
        // Required fields note
        Label requiredNote = new Label("* Required fields");
        requiredNote.setStyle("-fx-font-size: 12px; -fx-text-fill: #e74c3c; -fx-padding: 10 0 0 0;");
        
        // Add components to container
        formContainer.getChildren().addAll(
            title, 
            infoMessage, 
            formGrid, 
            requiredNote,
            buttonContainer
        );
    }
    
    private void cancelAppointment() {
        // Validate required fields
        if (appointmentIdField.getText().trim().isEmpty()) {
            showAlert("Validation Error", "Please enter Appointment ID");
            appointmentIdField.requestFocus();
            return;
        }
        
        if (cancellationTypeCombo.getValue() == null) {
            showAlert("Validation Error", "Please select Cancellation Type");
            cancellationTypeCombo.requestFocus();
            return;
        }
        
        if (reasonField.getText().trim().isEmpty()) {
            showAlert("Validation Error", "Please provide Reason for Cancellation");
            reasonField.requestFocus();
            return;
        }
        
        // Gather all data
        String appointmentId = appointmentIdField.getText().trim();
        String cancellationType = cancellationTypeCombo.getValue();
        String reason = reasonField.getText().trim();
        String details1 = details1Field.getText().trim();
        String details2 = details2Field.getText().trim();
        String details3 = details3Field.getText().trim();
        String appointmentDate = appointmentDatePicker.getValue() != null ? 
            appointmentDatePicker.getValue().toString() : "Not specified";
        boolean notifyPatient = notifyPatientCheck.isSelected();
        boolean requestRefund = refundRequestCheck.isSelected();
        
        // Display confirmation (in real app, this would save to database)
        StringBuilder confirmation = new StringBuilder();
        confirmation.append("Appointment Cancellation Request Submitted:\n\n");
        confirmation.append("Appointment ID: ").append(appointmentId).append("\n");
        confirmation.append("Cancellation Type: ").append(cancellationType).append("\n");
        confirmation.append("Appointment Date: ").append(appointmentDate).append("\n");
        confirmation.append("Notify Patient: ").append(notifyPatient ? "Yes" : "No").append("\n");
        confirmation.append("Process Refund: ").append(requestRefund ? "Yes" : "No").append("\n\n");
        confirmation.append("Reason: ").append(reason).append("\n\n");
        
        if (!details1.isEmpty()) {
            confirmation.append("Impact on Patient: ").append(details1).append("\n");
        }
        if (!details2.isEmpty()) {
            confirmation.append("Follow-up Actions: ").append(details2).append("\n");
        }
        if (!details3.isEmpty()) {
            confirmation.append("Additional Notes: ").append(details3).append("\n");
        }
        
        showAlert("Cancellation Submitted", confirmation.toString());
        
        // Clear form after submission (optional)
        clearForm();
    }
    
    private void clearForm() {
        appointmentIdField.clear();
        appointmentDatePicker.setValue(null);
        cancellationTypeCombo.setValue(null);
        reasonField.clear();
        details1Field.clear();
        details2Field.clear();
        details3Field.clear();
        notifyPatientCheck.setSelected(true);
        refundRequestCheck.setSelected(false);
    }
    
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.getDialogPane().setPrefWidth(500);
        alert.showAndWait();
    }
    
    public VBox getForm() {
        return formContainer;
    }
    
    // Getters for form fields (optional - useful if you need to access them externally)
    public String getAppointmentId() {
        return appointmentIdField.getText();
    }
    
    public String getReason() {
        return reasonField.getText();
    }
    
    public String getDetails1() {
        return details1Field.getText();
    }
    
    public String getDetails2() {
        return details2Field.getText();
    }
    
    public String getDetails3() {
        return details3Field.getText();
    }
}