package application;
import java.time.LocalDate;

public class Assignment {
    private String doctorId;
    private String doctorName;
    private String patientId;
    private String patientName;
    private LocalDate assignmentDate;
    private String status;
    
    public Assignment(String doctorId, String doctorName, String patientId, String patientName) {
        this.doctorId = doctorId;
        this.doctorName = doctorName;
        this.patientId = patientId;
        this.patientName = patientName;
        this.assignmentDate = LocalDate.now();
        this.status = "Active";
    }
    
    // Getters
    public String getDoctorId() { 
        return doctorId; 
    }
    
    public String getDoctorName() { 
        return doctorName; 
    }
    
    public String getPatientId() { 
        return patientId; 
    }
    
    public String getPatientName() { 
        return patientName; 
    }
    
    public LocalDate getAssignmentDate() { 
        return assignmentDate; 
    }
    
    public String getStatus() { 
        return status; 
    }
    
    // Setters
    public void setDoctorId(String doctorId) { 
        this.doctorId = doctorId; 
    }
    
    public void setDoctorName(String doctorName) { 
        this.doctorName = doctorName; 
    }
    
    public void setPatientId(String patientId) { 
        this.patientId = patientId; 
    }
    
    public void setPatientName(String patientName) { 
        this.patientName = patientName; 
    }
    
    public void setAssignmentDate(LocalDate assignmentDate) { 
        this.assignmentDate = assignmentDate; 
    }
    
    public void setStatus(String status) { 
        this.status = status; 
    }
    
    @Override
    public String toString() {
        return String.format("Doctor: %s (%s) | Patient: %s (%s) | Date: %s | Status: %s",
            doctorName, doctorId, patientName, patientId, assignmentDate, status);
    }
}