package application;

import java.time.LocalDate;

public class Appointment {
    private String appointmentId;
    private String patientId;
    private String patientName;
    private String doctor;
    private String appointmentType;
    private String department;
    private String urgency;
    private LocalDate date;
    private String time;
    private String contact;
    private String room;
    private String symptoms;
    private String notes;
    private String status;
    
    public Appointment(String appointmentId, String patientId, String patientName, 
                      String doctor, String appointmentType, String department,
                      String urgency, LocalDate date, String time, String contact,
                      String room, String symptoms, String notes, String status) {
        this.appointmentId = appointmentId;
        this.patientId = patientId;
        this.patientName = patientName;
        this.doctor = doctor;
        this.appointmentType = appointmentType;
        this.department = department;
        this.urgency = urgency;
        this.date = date;
        this.time = time;
        this.contact = contact;
        this.room = room;
        this.symptoms = symptoms;
        this.notes = notes;
        this.status = status;
    }
    
    // Getters and Setters
    public String getAppointmentId() { return appointmentId; }
    public void setAppointmentId(String appointmentId) { this.appointmentId = appointmentId; }
    
    public String getPatientId() { return patientId; }
    public void setPatientId(String patientId) { this.patientId = patientId; }
    
    public String getPatientName() { return patientName; }
    public void setPatientName(String patientName) { this.patientName = patientName; }
    
    public String getDoctor() { return doctor; }
    public void setDoctor(String doctor) { this.doctor = doctor; }
    
    public String getAppointmentType() { return appointmentType; }
    public void setAppointmentType(String appointmentType) { this.appointmentType = appointmentType; }
    
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
    
    public String getUrgency() { return urgency; }
    public void setUrgency(String urgency) { this.urgency = urgency; }
    
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    
    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }
    
    public String getContact() { return contact; }
    public void setContact(String contact) { this.contact = contact; }
    
    public String getRoom() { return room; }
    public void setRoom(String room) { this.room = room; }
    
    public String getSymptoms() { return symptoms; }
    public void setSymptoms(String symptoms) { this.symptoms = symptoms; }
    
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}