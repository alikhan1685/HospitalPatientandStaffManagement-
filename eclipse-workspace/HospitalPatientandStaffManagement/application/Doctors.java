package application;

public class Doctors extends Staff {
    private String consultationHours;
    private double consultationFee;
    private int patientLoad;
    private boolean canPerformSurgery;
    
    public Doctors() {
        super();
        this.patientLoad = 0;
        this.canPerformSurgery = true;
        this.consultationHours = "9:00 AM - 5:00 PM";
        this.consultationFee = 1000.0;
    }
    

    
    
    public void increasePatientLoad() {
        this.patientLoad++;
    }
    
    public void decreasePatientLoad() {
        if (this.patientLoad > 0) {
            this.patientLoad--;
        }
    }
    
    public int getPatientLoad() {
        return patientLoad;
    }
    
    public void setPatientLoad(int patientLoad) {
        this.patientLoad = patientLoad;
    }
    
    public boolean canPerformSurgery() {
        return canPerformSurgery;
    }
    
    public void setCanPerformSurgery(boolean canPerformSurgery) {
        this.canPerformSurgery = canPerformSurgery;
    }
    
    public String getConsultationHours() {
        return consultationHours;
    }
    
    public void setConsultationHours(String consultationHours) {
        this.consultationHours = consultationHours;
    }
    
    public double getConsultationFee() {
        return consultationFee;
    }
    
    public void setConsultationFee(double consultationFee) {
        this.consultationFee = consultationFee;
    }
    
    @Override
    public void displayBasicInfo() {
        System.out.println("\n=== DOCTOR DETAILS ===");
        super.displayBasicInfo();
        System.out.println("Consultation Hours: " + consultationHours);
        System.out.println("Consultation Fee: $" + consultationFee);
        System.out.println("Patient Load: " + patientLoad);
        System.out.println("Can Perform Surgery: " + (canPerformSurgery ? "Yes" : "No"));
    }
    
    // Check if doctor can take more patients
    public boolean canTakeMorePatients() {
        return isAvailable() && patientLoad < 50; // Max 50 patients per doctor
    }
}
