package application;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) {

        // ---------------- HAMBURGER BUTTON ----------------
        Label hamburgerBtn = new Label("☰ Menu");
        hamburgerBtn.setStyle(
                "-fx-font-size: 18px;" +
                "-fx-padding: 8 15 8 15;" +
                "-fx-background-color: #34495e;" +
                "-fx-text-fill: white;" +
                "-fx-cursor: hand;"
        );
        hamburgerBtn.setOnMouseEntered(e -> hamburgerBtn.setStyle(
                "-fx-font-size: 18px;" +
                "-fx-padding: 8 15 8 15;" +
                "-fx-background-color: #3b5998;" +
                "-fx-text-fill: white;" +
                "-fx-cursor: hand;"
        ));
        hamburgerBtn.setOnMouseExited(e -> hamburgerBtn.setStyle(
                "-fx-font-size: 18px;" +
                "-fx-padding: 8 15 8 15;" +
                "-fx-background-color: #34495e;" +
                "-fx-text-fill: white;" +
                "-fx-cursor: hand;"
        ));

        // ---------------- SIDE MENU ----------------
        VBox sideMenu = new VBox(0);
        sideMenu.setPadding(new Insets(15));
        sideMenu.setStyle("-fx-background-color: #2c3e50;");
        sideMenu.setPrefWidth(240);

        // ---------------- MAIN MENU ITEMS ----------------
        Label patientsMenu = new Label("PATIENTS");
        Label treatmentMenu = new Label("TREATMENT");
        Label appointmentMenu = new Label("APPOINTMENT");
        Label staffMenu = new Label("STAFF");

        for (Label lbl : new Label[]{patientsMenu, treatmentMenu, appointmentMenu, staffMenu}) {
            lbl.setStyle("-fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 8 5 8 10; -fx-cursor: hand;");
            lbl.setOnMouseEntered(e -> lbl.setStyle("-fx-text-fill: #3498db; -fx-font-size: 14px; -fx-padding: 8 5 8 10; -fx-cursor: hand;"));
            lbl.setOnMouseExited(e -> lbl.setStyle("-fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 8 5 8 10; -fx-cursor: hand;"));
        }

        // ---------------- PATIENT OPTIONS ----------------
        VBox patientOptions = new VBox(0);
        Label addPatient = new Label("ADD PATIENT");
        Label getPatient = new Label("GET PATIENT");
        for (Label lbl : new Label[]{addPatient, getPatient}) {
            lbl.setStyle("-fx-text-fill: white; -fx-font-size: 13px; -fx-padding: 4 5 4 25; -fx-cursor: hand;");
            lbl.setOnMouseEntered(e -> lbl.setStyle("-fx-text-fill: #1abc9c; -fx-font-size: 13px; -fx-padding: 4 5 4 25; -fx-cursor: hand;"));
            lbl.setOnMouseExited(e -> lbl.setStyle("-fx-text-fill: white; -fx-font-size: 13px; -fx-padding: 4 5 4 25; -fx-cursor: hand;"));
        }
        patientOptions.getChildren().addAll(addPatient, getPatient);

        // ---------------- TREATMENT OPTIONS ----------------
        VBox treatmentOptions = new VBox(0);
        Label saveTreatment = new Label("SAVE");
        Label updateTreatment = new Label("UPDATE");
        for (Label lbl : new Label[]{saveTreatment, updateTreatment}) {
            lbl.setStyle("-fx-text-fill: white; -fx-font-size: 13px; -fx-padding: 4 5 4 25; -fx-cursor: hand;");
            lbl.setOnMouseEntered(e -> lbl.setStyle("-fx-text-fill: #e67e22; -fx-font-size: 13px; -fx-padding: 4 5 4 25; -fx-cursor: hand;"));
            lbl.setOnMouseExited(e -> lbl.setStyle("-fx-text-fill: white; -fx-font-size: 13px; -fx-padding: 4 5 4 25; -fx-cursor: hand;"));
        }
        treatmentOptions.getChildren().addAll(saveTreatment, updateTreatment);

        // ---------------- APPOINTMENT OPTIONS ----------------
        VBox appointmentOptions = new VBox(0);
        Label scheduleAppointment = new Label("SCHEDULE");
        Label cancelAppointment = new Label("CANCEL");
        for (Label lbl : new Label[]{scheduleAppointment, cancelAppointment}) {
            lbl.setStyle("-fx-text-fill: white; -fx-font-size: 13px; -fx-padding: 4 5 4 25; -fx-cursor: hand;");
            lbl.setOnMouseEntered(e -> lbl.setStyle("-fx-text-fill: #f1c40f; -fx-font-size: 13px; -fx-padding: 4 5 4 25; -fx-cursor: hand;"));
            lbl.setOnMouseExited(e -> lbl.setStyle("-fx-text-fill: white; -fx-font-size: 13px; -fx-padding: 4 5 4 25; -fx-cursor: hand;"));
        }
        appointmentOptions.getChildren().addAll(scheduleAppointment, cancelAppointment);

        // ---------------- STAFF OPTIONS ----------------
        VBox staffOptions = new VBox(0);

        // Doctor
        Label doctorOption = new Label("DOCTOR");
        VBox doctorOptions = new VBox(0);
        Label managePatientsDoctor = new Label("AVAILABLE");
        Label specificationDoctor = new Label("SPECIFICATION");
        doctorOptions.getChildren().addAll(managePatientsDoctor, specificationDoctor);

        // Nurse
        Label nurseOption = new Label("NURSE");
        VBox nurseOptions = new VBox(0);
        Label availableNurse = new Label("AVAILABLE");
        Label managePatientsNurse = new Label("ASSIGN");
        nurseOptions.getChildren().addAll(availableNurse,managePatientsNurse);

        // Technician
        Label technicianOption = new Label("TECHNICIAN");
        VBox technicianOptions = new VBox(0);
        Label available2= new Label("AVAILABLE"); // only option
        technicianOptions.getChildren().add(available2);

        // Apply consistent styling
        for (Label lbl : new Label[]{
                managePatientsDoctor, specificationDoctor,
                managePatientsNurse, availableNurse,
                available2
        }) {
            lbl.setStyle("-fx-text-fill: white; -fx-font-size: 13px; -fx-padding: 4 5 4 25; -fx-cursor: hand;");
            lbl.setOnMouseEntered(e -> lbl.setStyle("-fx-text-fill: #1abc9c; -fx-font-size: 13px; -fx-padding: 4 5 4 25; -fx-cursor: hand;"));
            lbl.setOnMouseExited(e -> lbl.setStyle("-fx-text-fill: white; -fx-font-size: 13px; -fx-padding: 4 5 4 25; -fx-cursor: hand;"));
        }

        for (Label lbl : new Label[]{doctorOption, nurseOption, technicianOption}) {
            lbl.setStyle("-fx-text-fill: white; -fx-font-size: 13px; -fx-padding: 4 5 4 15; -fx-cursor: hand;");
            lbl.setOnMouseEntered(e -> lbl.setStyle("-fx-text-fill: #1abc9c; -fx-font-size: 13px; -fx-padding: 4 5 4 15; -fx-cursor: hand;"));
            lbl.setOnMouseExited(e -> lbl.setStyle("-fx-text-fill: white; -fx-font-size: 13px; -fx-padding: 4 5 4 15; -fx-cursor: hand;"));
        }

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

        // ---------------- TOGGLE HAMBURGER MENU ----------------
        sideMenu.setVisible(false);
        hamburgerBtn.setOnMouseClicked(e -> sideMenu.setVisible(!sideMenu.isVisible()));

        // ---------------- CLICK EVENTS ----------------
        addPatient.setOnMouseClicked(e -> System.out.println("Add Patient Panel"));
        getPatient.setOnMouseClicked(e -> System.out.println("Get Patient Panel"));

        saveTreatment.setOnMouseClicked(e -> System.out.println("Save Treatment Action"));
        updateTreatment.setOnMouseClicked(e -> System.out.println("Update Treatment Action"));

        scheduleAppointment.setOnMouseClicked(e -> System.out.println("Schedule Appointment Panel"));
        cancelAppointment.setOnMouseClicked(e -> System.out.println("Cancel Appointment Panel"));

        managePatientsDoctor.setOnMouseClicked(e -> System.out.println("Manage Patients (Doctor) Panel"));
        specificationDoctor.setOnMouseClicked(e -> System.out.println("Specification Panel"));

        managePatientsNurse.setOnMouseClicked(e -> System.out.println("Manage Patients (Nurse) Panel"));
        availableNurse.setOnMouseClicked(e -> System.out.println("Available Nurse Panel"));

        available2.setOnMouseClicked(e -> System.out.println("Available Technician Panel"));

        // ---------------- LAYOUT ----------------
        BorderPane root = new BorderPane();
        root.setTop(hamburgerBtn);
        root.setLeft(sideMenu);

        Scene scene = new Scene(root, 450, 500);
        stage.setTitle("Hospital Management System Menu");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

