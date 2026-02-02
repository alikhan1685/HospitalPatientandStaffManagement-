package application;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

public class Doctors extends Staff {
    private static final String role = "Doctor";  // Changed from "Nurse" to "Doctor"
    private String ward;
    private String licenseNo;
    private List<String> assignedPatients;
    private String shift;
    private int maxPatients;
    private int currentPatientsCount;
    private String certifications;
    private String assignedDoctorId;
    
    // Constructor
    public Doctors(String id, String name, int age, String specialization, 
                   String qualification, int experience, String licenseNo, 
                   String gender, String address, String phone, String ward) {
        super();  // Changed from super(name) - calling default constructor
        
        try {
            // Set inherited fields from Staff class
            this.staffId = id;
            this.name = name;
            this.age = age;
            this.gender = gender;
            this.address = address;
            this.phone = phone;
            this.email = "";  // Initialize empty email
            this.emergencyContact = "";
            
            // Set specialization and experience (inherited from Staff)
            this.specialization = specialization;
            this.experience = experience;
            
            // Set qualification and license (inherited from Staff)
            this.qualification = qualification;
            this.licenseNumber = licenseNo;
            
            // Initialize Doctors-specific fields
            this.licenseNo = licenseNo;
            this.ward = ward;
            this.status = "Available";
            this.maxPatients = 15;
            this.currentPatientsCount = 0;
            this.department = specialization + " Department";
            this.shift = "Day Shift (7 AM - 3 PM)";
            this.certifications = "";
            this.assignedDoctorId = "";
            this.assignedPatients = new ArrayList<>();
            
        } catch (Exception e) {
            System.out.println("Error Creating Doctor: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    // Override getId() to use staffId from Staff class
    //@Override
    public String getId() {
        return this.staffId;
    }
    
    // Doctors-specific getters and setters
    public String getWard() { return ward; }
    public void setWard(String ward) { this.ward = ward; }
    
    public String getLicenseNo() { return licenseNo; }
    public void setLicenseNo(String licenseNo) { 
        this.licenseNo = licenseNo;
        this.licenseNumber = licenseNo; // Update inherited field too
    }
    
    public List<String> getAssignedPatients() { 
        return new ArrayList<>(assignedPatients); 
    }
    
    public String getShift() { return shift; }
    public void setShift(String shift) { this.shift = shift; }
    
    public int getMaxPatients() { return maxPatients; }
    public void setMaxPatients(int maxPatients) { this.maxPatients = maxPatients; }
    
    public int getCurrentPatientsCount() { return currentPatientsCount; }
    
    public String getRole() { return role; }
    
    public String getCertifications() { return certifications; }
    public void setCertifications(String certifications) { 
        this.certifications = certifications; 
    }
    
    public String getAssignedDoctorId() { return assignedDoctorId; }
    public void setAssignedDoctorId(String assignedDoctorId) { 
        this.assignedDoctorId = assignedDoctorId; 
    }
    
    // Business Logic Methods
    public boolean assignPatient(String patientId) {
        if (patientId != null && !patientId.trim().isEmpty() && 
            !assignedPatients.contains(patientId) && 
            currentPatientsCount < maxPatients) {
            
            assignedPatients.add(patientId);
            currentPatientsCount++;
            
            if (currentPatientsCount >= maxPatients) {
                status = "At Capacity";
            }
            
            return true;
        }
        return false;
    }
    
    public boolean removePatient(String patientId) {
        if (patientId != null && assignedPatients.contains(patientId)) {
            assignedPatients.remove(patientId);
            currentPatientsCount--;
            
            if (currentPatientsCount < maxPatients && "At Capacity".equals(status)) {
                status = "Available";
            }
            
            return true;
        }
        return false;
    }
    
    public boolean canAcceptMorePatients() {
        return currentPatientsCount < maxPatients && "Available".equals(status);
    }
    
    public String getAvailabilityStatus() {
        if ("Available".equals(status) && currentPatientsCount < maxPatients) {
            return "Available for new patients";
        } else if ("At Capacity".equals(status)) {
            return "At full capacity";
        } else if (currentPatientsCount >= maxPatients * 0.8) {
            return "Nearly full (" + currentPatientsCount + "/" + maxPatients + ")";
        } else {
            return status;
        }
    }
    
    public int getWorkloadPercentage() {
        if (maxPatients > 0) {
            return (currentPatientsCount * 100) / maxPatients;
        }
        return 0;
    }
    
    // Additional helper method to display doctor info
    public void displayDoctorInfo() {
        System.out.println("Doctor Information:");
        System.out.println("ID: " + this.staffId);
        System.out.println("Name: " + this.name);
        System.out.println("Specialization: " + this.specialization);
        System.out.println("Department: " + this.department);
        System.out.println("Experience: " + this.experience + " years");
        System.out.println("License: " + this.licenseNo);
        System.out.println("Status: " + this.status);
        System.out.println("Current Patients: " + currentPatientsCount + "/" + maxPatients);
        System.out.println("Shift: " + shift);
        System.out.println("Ward: " + ward);
    }
}