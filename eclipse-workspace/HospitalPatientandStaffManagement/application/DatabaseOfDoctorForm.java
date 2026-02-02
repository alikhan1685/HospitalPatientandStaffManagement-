package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

public class DatabaseOfDoctorForm {

    private VBox container;
    private TableView<Treatments> treatmentTable;
    private ObservableList<Treatments> treatmentData;

    public DatabaseOfDoctorForm() {
        treatmentData = FXCollections.observableArrayList();
       initializeForm();
    }
    private void initializeForm() {
        container = new VBox();
        container.setSpacing(20);
        container.setPadding(new Insets(20));
        container.setStyle("-fx-background-color: #f8f9fa;");

        Label title = new Label("Current Treatment Status");
        title.setFont(new Font("Arial", 28));
        title.setStyle("-fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        // Table setup
        treatmentTable = new TableView<>();
        treatmentTable.setPrefWidth(900);
        treatmentTable.setItems(
        	    TreatmentDatabase.getInstance().getTreatments()
        		);

        TableColumn<Treatments, String> idCol = new TableColumn<>("Treatment ID");
        idCol.setCellValueFactory(cellData -> cellData.getValue().treatmentIdProperty());

        TableColumn<Treatments, String> patientCol = new TableColumn<>("Patient Name");
        patientCol.setCellValueFactory(cellData -> cellData.getValue().patientNameProperty());

        TableColumn<Treatments, String> doctorCol = new TableColumn<>("Doctor");
        doctorCol.setCellValueFactory(cellData -> cellData.getValue().doctorProperty());

        TableColumn<Treatments, String> typeCol = new TableColumn<>("Treatment Type");
        typeCol.setCellValueFactory(cellData -> cellData.getValue().treatmentTypeProperty());

        TableColumn<Treatments, String> durationCol = new TableColumn<>("Duration");
        durationCol.setCellValueFactory(cellData -> cellData.getValue().durationProperty());

        TableColumn<Treatments, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(cellData -> cellData.getValue().statusProperty());

        treatmentTable.getColumns().addAll(idCol, patientCol, doctorCol, typeCol, durationCol, statusCol);

        // Buttons
        HBox buttonBox = new HBox(15);
        Button addSampleButton = new Button("Add Sample Data");
        addSampleButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-weight: bold;");
        addSampleButton.setOnAction(e -> addSampleData());

        Button markCompletedButton = new Button("Mark Selected Completed");
        markCompletedButton.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white; -fx-font-weight: bold;");
        markCompletedButton.setOnAction(e -> markSelectedCompleted());

        Button refreshButton = new Button("Refresh List");
        refreshButton.setStyle("-fx-background-color: #e67e22; -fx-text-fill: white; -fx-font-weight: bold;");
        refreshButton.setOnAction(e -> treatmentTable.refresh());

        buttonBox.getChildren().addAll(addSampleButton, markCompletedButton, refreshButton);

        container.getChildren().addAll(title, treatmentTable, buttonBox);
    }
    

    private void addSampleData() {
        // For testing/demo, you can replace this with real AddTreatment records
        treatmentData.add(new Treatments("TREAT-1001", "John Smith", "Dr. Ahmed Khan", "Surgery", "2 weeks", "Active", null));
        treatmentData.add(new Treatments("TREAT-1002", "Emma Johnson", "Dr. Farooq", "Chemotherapy", "1 month", "Active", null));
        treatmentData.add(new Treatments("TREAT-1003", "Robert Brown", "Dr. Aleem", "Physical Therapy", "3 weeks", "Completed", null));
    }

    private void markSelectedCompleted() {
    	Treatments selected = treatmentTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            selected.setStatus("Completed");
            treatmentTable.refresh();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Treatment Selected");
            alert.setContentText("Please select a treatment from the table to mark as completed.");
            alert.showAndWait();
        }
    }

    public VBox getForm() {
        return container;
    }
}
