package application;

import java.util.ArrayList;
import java.util.List;

public class Technicians extends Staff {
    private static final String role = "Technician";
    private String ward;
    private String qualification;
    private String licenseNo;
    private List<String> assignedPatients;
    private String shift;
    private int maxPatients;
    private int currentPatientsCount;
    private String certifications;
    private String assignedDoctorId;
    
    // Constructor
    public Technicians(String id, String name, int age, String specialization, 
                 String qualification, int experience, String licenseNo, 
                 String gender, String address, String phone, String ward) {
        // Call parent constructor properly
        super(id, name, age, gender, specialization, experience, licenseNo, 
              qualification, ward, phone, address, "Available");
        
        this.licenseNo = licenseNo;
        this.qualification = qualification;
        this.ward = ward;
        this.maxPatients = 15;
        this.currentPatientsCount = 0;
        this.shift = "Day Shift (7 AM - 3 PM)";
        this.certifications = "";
        this.assignedDoctorId = "";
        this.assignedPatients = new ArrayList<>();
    }
    
    // Alternative constructor for TechnicianDatabase
    public Technicians(String staffId, String name, String specialization, int experience, String status) {
        super(staffId, name, specialization, experience, status);
        this.maxPatients = 15;
        this.currentPatientsCount = 0;
        this.shift = "Day Shift (7 AM - 3 PM)";
        this.assignedPatients = new ArrayList<>();
    }
    
    // Getter for ID - Override to use parent's staffId
    public String getId() {
        return super.getStaffId(); // This gets the staffId from parent class
    }
    
    // Other getters and setters
    public String getWard() { return ward; }
    public void setWard(String ward) { this.ward = ward; }
    
    public String getQualification() { return qualification; }
    public void setQualification(String qualification) { this.qualification = qualification; }
    
    public String getLicenseNo() { return licenseNo; }
    public void setLicenseNo(String licenseNo) { this.licenseNo = licenseNo; }
    
    public List<String> getAssignedPatients() { return new ArrayList<>(assignedPatients); }
    
    public String getShift() { return shift; }
    public void setShift(String shift) { this.shift = shift; }
    
    public int getMaxPatients() { return maxPatients; }
    public void setMaxPatients(int maxPatients) { this.maxPatients = maxPatients; }
    
    public int getCurrentPatientsCount() { return currentPatientsCount; }
    
    public String getRole() { return role; }
    
    public String getCertifications() { return certifications; }
    public void setCertifications(String certifications) { this.certifications = certifications; }
    
    public String getAssignedDoctorId() { return assignedDoctorId; }
    public void setAssignedDoctorId(String assignedDoctorId) { this.assignedDoctorId = assignedDoctorId; }
    
    // Business Logic Methods
    public boolean assignPatient(String patientId) {
        if (patientId != null && !patientId.trim().isEmpty() && 
            !assignedPatients.contains(patientId) && 
            currentPatientsCount < maxPatients) {
            
            assignedPatients.add(patientId);
            currentPatientsCount++;
            
            if (currentPatientsCount >= maxPatients) {
                setStatus("At Capacity");
            }
            
            return true;
        }
        return false;
    }
    
    public boolean removePatient(String patientId) {
        if (patientId != null && assignedPatients.contains(patientId)) {
            assignedPatients.remove(patientId);
            currentPatientsCount--;
            
            if (currentPatientsCount < maxPatients && getStatus().equals("At Capacity")) {
                setStatus("Available");
            }
            
            return true;
        }
        return false;
    }
    
    public boolean canAcceptMorePatients() {
        return currentPatientsCount < maxPatients && getStatus().equals("Available");
    }
    
    public String getAvailabilityStatus() {
        String status = getStatus();
        if (status.equals("Available") && currentPatientsCount < maxPatients) {
            return "Available for new patients";
        } else if (status.equals("At Capacity")) {
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
    
    @Override
    public void displayBasicInfo() {
        System.out.println("\n=== Technician Information ===");
        System.out.println("Technician ID: " + getStaffId());
        System.out.println("Name: " + getName());
        System.out.println("Age: " + getAge());
        System.out.println("Gender: " + getGender());
        System.out.println("Specialization: " + getSpecialization());
        System.out.println("Experience: " + getExperience() + " years");
        System.out.println("Status: " + getStatus());
        System.out.println("Phone: " + getPhone());
        System.out.println("Address: " + getAddress());
        System.out.println("Department: " + getDepartment());
        System.out.println("Qualification: " + qualification);
        System.out.println("License: " + licenseNo);
        System.out.println("Shift: " + shift);
        System.out.println("Certifications: " + certifications);
        System.out.println("Max Patients: " + maxPatients);
        System.out.println("Current Patients: " + currentPatientsCount);
        System.out.println("Date Joined: " + getDateJoined());
        System.out.println("========================\n");
    }
}