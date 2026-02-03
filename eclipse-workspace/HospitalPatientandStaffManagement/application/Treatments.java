package application;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Treatments {
    private final StringProperty treatmentId;
    private final StringProperty patientId;
    private final StringProperty patientName;
    private final StringProperty doctor;
    private final StringProperty treatmentType;
    private final StringProperty duration;
    private final StringProperty status;
    public Treatments(String treatmentId, String patientId, String patientName,
                     String doctor, String treatmentType, String duration, String status) {
        this.treatmentId = new SimpleStringProperty(treatmentId);
        this.patientId = new SimpleStringProperty(patientId);
        this.patientName = new SimpleStringProperty(patientName);
        this.doctor = new SimpleStringProperty(doctor);
        this.treatmentType = new SimpleStringProperty(treatmentType);
        this.duration = new SimpleStringProperty(duration);
        this.status = new SimpleStringProperty(status);
    }
    public StringProperty treatmentIdProperty() { return treatmentId; }
    public StringProperty patientIdProperty() { return patientId; }
    public StringProperty patientNameProperty() { return patientName; }
    public StringProperty doctorProperty() { return doctor; }
    public StringProperty treatmentTypeProperty() { return treatmentType; }
    public StringProperty durationProperty() { return duration; }
    public StringProperty statusProperty() { return status; }
    public void setStatus(String status) {
        this.status.set(status);
    }
}
