package application;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainMenu extends Application {

    private VBox contentArea = new VBox();
    private BorderPane root = new BorderPane();

    @Override
    public void start(Stage stage) {

        // ---------------- HAMBURGER BUTTON ----------------
        Label hamburgerBtn = new Label("☰ Menu");
        hamburgerBtn.setStyle(
                "-fx-font-size: 13px;" +
                "-fx-padding: 6 10;" +
                "-fx-background-color: #34495e;" +
                "-fx-text-fill: white;" +
                "-fx-cursor: hand;"
        );
        hamburgerBtn.setOnMouseEntered(e -> hamburgerBtn.setStyle(
                "-fx-font-size: 13px;" +
                "-fx-padding: 6 10;" +
                "-fx-background-color: #3b5998;" +
                "-fx-text-fill: white;" +
                "-fx-cursor: hand;"
        ));
        hamburgerBtn.setOnMouseExited(e -> hamburgerBtn.setStyle(
                "-fx-font-size: 13px;" +
                "-fx-padding: 6 10;" +
                "-fx-background-color: #34495e;" +
                "-fx-text-fill: white;" +
                "-fx-cursor: hand;"
        ));

        // ---------------- SIDE MENU ----------------
        VBox sideMenu = new VBox(0);
        sideMenu.setStyle("-fx-background-color: #2c3e50;");
        sideMenu.setPrefWidth(200);

        // Create scrollable container for menu
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(sideMenu);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: #2c3e50; -fx-border-color: #2c3e50;");
        scrollPane.setPrefWidth(200);

        // ---------------- MAIN MENU ITEMS ----------------
        Label patientsMenu = new Label("PATIENTS");
        Label treatmentMenu = new Label("TREATMENT");
        Label appointmentMenu = new Label("APPOINTMENT");
        Label staffMenu = new Label("STAFF");

        for (Label lbl : new Label[]{patientsMenu, treatmentMenu, appointmentMenu, staffMenu}) {
            lbl.setStyle("-fx-text-fill: white; -fx-font-size: 12px; -fx-padding: 6 5 6 10; -fx-cursor: hand;");
            lbl.setOnMouseEntered(e -> lbl.setStyle("-fx-text-fill: #3498db; -fx-font-size: 12px; -fx-padding: 6 5 6 10; -fx-cursor: hand;"));
            lbl.setOnMouseExited(e -> lbl.setStyle("-fx-text-fill: white; -fx-font-size: 12px; -fx-padding: 6 5 6 10; -fx-cursor: hand;"));
        }

        // ---------------- PATIENT OPTIONS ----------------
        VBox patientOptions = new VBox(0);
        patientOptions.setPadding(new Insets(0, 0, 5, 0));
        Label addPatient = new Label("ADD PATIENT");
        Label getPatient = new Label("GET PATIENT");
        for (Label lbl : new Label[]{addPatient, getPatient}) {
            lbl.setStyle("-fx-text-fill: white; -fx-font-size: 11px; -fx-padding: 4 5 4 20; -fx-cursor: hand;");
            lbl.setOnMouseEntered(e -> lbl.setStyle("-fx-text-fill: #1abc9c; -fx-font-size: 11px; -fx-padding: 4 5 4 20; -fx-cursor: hand;"));
            lbl.setOnMouseExited(e -> lbl.setStyle("-fx-text-fill: white; -fx-font-size: 11px; -fx-padding: 4 5 4 20; -fx-cursor: hand;"));
        }
        patientOptions.getChildren().addAll(addPatient, getPatient);

        // ---------------- TREATMENT OPTIONS ----------------
        VBox treatmentOptions = new VBox(0);
        treatmentOptions.setPadding(new Insets(0, 0, 5, 0));
        Label addTreatment = new Label("ADD");
        Label updateTreatment = new Label("UPDATE");
        for (Label lbl : new Label[]{addTreatment, updateTreatment}) {
            lbl.setStyle("-fx-text-fill: white; -fx-font-size: 11px; -fx-padding: 4 5 4 20; -fx-cursor: hand;");
            lbl.setOnMouseEntered(e -> lbl.setStyle("-fx-text-fill: #e67e22; -fx-font-size: 11px; -fx-padding: 4 5 4 20; -fx-cursor: hand;"));
            lbl.setOnMouseExited(e -> lbl.setStyle("-fx-text-fill: white; -fx-font-size: 11px; -fx-padding: 4 5 4 20; -fx-cursor: hand;"));
        }
        treatmentOptions.getChildren().addAll(addTreatment, updateTreatment);

        // ---------------- APPOINTMENT OPTIONS ----------------
        VBox appointmentOptions = new VBox(0);
        appointmentOptions.setPadding(new Insets(0, 0, 5, 0));
        Label scheduleAppointment = new Label("SCHEDULE");
        Label cancelAppointment = new Label("CANCEL");
        for (Label lbl : new Label[]{scheduleAppointment, cancelAppointment}) {
            lbl.setStyle("-fx-text-fill: white; -fx-font-size: 11px; -fx-padding: 4 5 4 20; -fx-cursor: hand;");
            lbl.setOnMouseEntered(e -> lbl.setStyle("-fx-text-fill: #f1c40f; -fx-font-size: 11px; -fx-padding: 4 5 4 20; -fx-cursor: hand;"));
            lbl.setOnMouseExited(e -> lbl.setStyle("-fx-text-fill: white; -fx-font-size: 11px; -fx-padding: 4 5 4 20; -fx-cursor: hand;"));
        }
        appointmentOptions.getChildren().addAll(scheduleAppointment, cancelAppointment);

        // ---------------- STAFF OPTIONS ----------------
        VBox staffOptions = new VBox(0);
        staffOptions.setPadding(new Insets(0, 0, 5, 0));

        // Doctor
        Label doctorOption = new Label("DOCTOR");
        doctorOption.setStyle("-fx-text-fill: #3498db; -fx-font-size: 11px; -fx-padding: 4 5 4 15; -fx-font-weight: bold;");
        VBox doctorOptions = new VBox(0);
        doctorOptions.setPadding(new Insets(0, 0, 3, 0));
        Label addDoctor = new Label("ADD");
        Label assignDoctor = new Label("ASSIGN");
        Label availableDoctor = new Label("AVAILABLE");
        for (Label lbl : new Label[]{addDoctor, availableDoctor, assignDoctor}) {
            lbl.setStyle("-fx-text-fill: white; -fx-font-size: 10px; -fx-padding: 3 5 3 20; -fx-cursor: hand;");
            lbl.setOnMouseEntered(e -> lbl.setStyle("-fx-text-fill: #1abc9c; -fx-font-size: 10px; -fx-padding: 3 5 3 20; -fx-cursor: hand;"));
            lbl.setOnMouseExited(e -> lbl.setStyle("-fx-text-fill: white; -fx-font-size: 10px; -fx-padding: 3 5 3 20; -fx-cursor: hand;"));
        }
        doctorOptions.getChildren().addAll(addDoctor, availableDoctor, assignDoctor);

        // Nurse
        Label nurseOption = new Label("NURSE");
        nurseOption.setStyle("-fx-text-fill: #3498db; -fx-font-size: 11px; -fx-padding: 4 5 4 15; -fx-font-weight: bold;");
        VBox nurseOptions = new VBox(0);
        nurseOptions.setPadding(new Insets(0, 0, 3, 0));
        Label addNurse = new Label("ADD");
        Label availableNurse = new Label("AVAILABLE");
        Label assignNurse = new Label("ASSIGN");
        for (Label lbl : new Label[]{addNurse, availableNurse, assignNurse}) {
            lbl.setStyle("-fx-text-fill: white; -fx-font-size: 10px; -fx-padding: 3 5 3 20; -fx-cursor: hand;");
            lbl.setOnMouseEntered(e -> lbl.setStyle("-fx-text-fill: #1abc9c; -fx-font-size: 10px; -fx-padding: 3 5 3 20; -fx-cursor: hand;"));
            lbl.setOnMouseExited(e -> lbl.setStyle("-fx-text-fill: white; -fx-font-size: 10px; -fx-padding: 3 5 3 20; -fx-cursor: hand;"));
        }
        nurseOptions.getChildren().addAll(addNurse, availableNurse, assignNurse);

        // Technician
        Label technicianOption = new Label("TECHNICIAN");
        technicianOption.setStyle("-fx-text-fill: #3498db; -fx-font-size: 11px; -fx-padding: 4 5 4 15; -fx-font-weight: bold;");
        VBox technicianOptions = new VBox(0);
        technicianOptions.setPadding(new Insets(0, 0, 3, 0));
        Label addTechnician = new Label("ADD");
        Label assignTechnician = new Label("ASSIGN");
        Label availableTechnician = new Label("AVAILABLES");
        for (Label lbl : new Label[]{addTechnician, assignTechnician, availableTechnician}) {
            lbl.setStyle("-fx-text-fill: white; -fx-font-size: 10px; -fx-padding: 3 5 3 20; -fx-cursor: hand;");
            lbl.setOnMouseEntered(e -> lbl.setStyle("-fx-text-fill: #1abc9c; -fx-font-size: 10px; -fx-padding: 3 5 3 20; -fx-cursor: hand;"));
            lbl.setOnMouseExited(e -> lbl.setStyle("-fx-text-fill: white; -fx-font-size: 10px; -fx-padding: 3 5 3 20; -fx-cursor: hand;"));
        }
        technicianOptions.getChildren().addAll(addTechnician, assignTechnician, availableTechnician);

        staffOptions.getChildren().addAll(
                doctorOption, doctorOptions,
                nurseOption, nurseOptions,
                technicianOption, technicianOptions
        );

        // ---------------- ADD ALL TO SIDE MENU ----------------
        sideMenu.getChildren().addAll(
                patientsMenu, patientOptions,
                treatmentMenu, treatmentOptions,
                appointmentMenu, appointmentOptions,
                staffMenu, staffOptions
        );

        // Add padding to the side menu
        sideMenu.setPadding(new Insets(10, 0, 15, 0));

        // ---------------- TOGGLE HAMBURGER MENU ----------------
        scrollPane.setVisible(false);
        hamburgerBtn.setOnMouseClicked(e -> scrollPane.setVisible(!scrollPane.isVisible()));

        // ---------------- CONTENT AREA ----------------
        contentArea.setPadding(new Insets(15));
        contentArea.setStyle("-fx-background-color: #ecf0f1;");
        contentArea.setSpacing(5);

        // ---------------- CLICK EVENTS ----------------
        addPatient.setOnMouseClicked(e -> {
            AddPatientForm addPatientForm = new AddPatientForm();
            showContent(addPatientForm.getForm());
        });
        
        getPatient.setOnMouseClicked(e -> {
            GetPatientForm patientSearchForm = new GetPatientForm();
            showContent(patientSearchForm.getForm());
        });

        // Treatment menu click events
        addTreatment.setOnMouseClicked(e -> {
            AddTreatmentForm addTreatmentForm = new AddTreatmentForm();
            showContent(addTreatmentForm.getForm());
        });
        
        updateTreatment.setOnMouseClicked(e -> {
        	CurrentTreatmentForm updateTreatmentForm = new CurrentTreatmentForm();
            showContent(updateTreatmentForm.getForm());
        });

        scheduleAppointment.setOnMouseClicked(e -> {
            ScheduleAppointmentForm scheduleForm = new ScheduleAppointmentForm();
            showContent(scheduleForm.getForm());
        });
        
        cancelAppointment.setOnMouseClicked(e -> {
            CancelAppointmentForm cancelForm = new CancelAppointmentForm();
            showContent(cancelForm.getForm());
        });

        // Doctor menu click events
        addDoctor.setOnMouseClicked(e -> {
            StatusOfDoctorForm addDoctorForm = new StatusOfDoctorForm();
            showContent(addDoctorForm.getForm());
        });
        
        availableDoctor.setOnMouseClicked(e -> {
            // FIXED: Changed from AvailableStaffForm to AvailableDoctorForm
            StatusOfDoctorForm availableDoctors = new StatusOfDoctorForm();
            showContent(availableDoctors.getForm());
        });
        
        availableDoctor.setOnMouseClicked(e -> {
            AssignDoctorForm assignDoctorForm = new AssignDoctorForm();
            showContent(assignDoctorForm.getForm());
        });

        // Nurse menu click events
        addNurse.setOnMouseClicked(e -> {
            AddNurseForm addNurseForm = new AddNurseForm();
            showContent(addNurseForm.getForm());
        });
        
        availableNurse.setOnMouseClicked(e -> {
            StatusOfNurseForm availableNurses = new StatusOfNurseForm();
            showContent(availableNurses.getForm());
        });
        
        assignNurse.setOnMouseClicked(e -> {
            AssignNurseForm assignNurseForm = new AssignNurseForm();
            showContent(assignNurseForm.getForm());
        });

        // Technician menu click events
        addTechnician.setOnMouseClicked(e -> {
            AddTechnicianForm addTechnicianForm = new AddTechnicianForm();
            showContent(addTechnicianForm.getForm());
        });
        
        assignTechnician.setOnMouseClicked(e -> {
            AssignTechnicianForm assignTechnicianForm = new AssignTechnicianForm();
            showContent(assignTechnicianForm.getForm());
        });
        
        // FIXED: Changed from AvailableNurseForm to AvailableTechnicianForm
        availableTechnician.setOnMouseClicked(e -> {
            AddTechnicianForm availableTechnicians = new AddTechnicianForm();
            showContent(availableTechnicians.getForm());
        });

        // ---------------- LAYOUT ----------------
        root.setTop(hamburgerBtn);
        root.setLeft(scrollPane);
        root.setCenter(contentArea);
        
        showDefaultContent();

        Scene scene = new Scene(root, 900, 650);
        stage.setTitle("Hospital Management System");
        stage.setScene(scene);
        stage.show();
    }

    private void showDefaultContent() {
        contentArea.getChildren().clear();
        Label welcomeLabel = new Label("Welcome to Hospital Management System");
        welcomeLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        contentArea.getChildren().add(welcomeLabel);
    }

    private void showContent(javafx.scene.Parent content) {
        contentArea.getChildren().clear();
        contentArea.getChildren().add(content);
    }

    public static void main(String[] args) {
        launch(args);
    }
}