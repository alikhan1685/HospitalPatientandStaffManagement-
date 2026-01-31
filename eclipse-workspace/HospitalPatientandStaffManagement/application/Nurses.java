package application;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

public class Nurses extends Staff {
	public String id;
    private static final String role = "Nurse";
    private String ward;
    private String specialization;
    private String qualification;
    private int experience;
    private String licenseNo;
    private List<String> assignedPatients;
    private String shift;
    private int maxPatients;
    private int currentPatientsCount;
    private String department;
    private String certifications;
    private String assignedDoctorId;
    
    // Constructor
    public Nurses(String id, String name, int age, String specialization, 
                 String qualification, int experience, String licenseNo, 
                 String gender, String address, String phone, String ward) {
        super();
        try {
            this.id = id;
            this.name = name;
            this.age = age;
            this.specialization = specialization;
            this.qualification = qualification;
            this.experience = experience;
            this.licenseNo = licenseNo;
            this.gender = gender;
            this.address = address;
            this.phone = phone;
            this.status = "Available";
            this.maxPatients = 15;
            this.currentPatientsCount = 0;
            this.department = "General Ward";
            this.shift = "Day Shift (7 AM - 3 PM)";
            this.certifications = "";
            this.ward = ward;
            this.assignedDoctorId = "";
            
            this.assignedPatients = new ArrayList<>();
            
        } catch (Exception e) {
            System.out.println("Error Adding Nurse: " + e);
        }
    }
    
    // Getters and Setters
    public String getId() {
        return this.id;  // 'id' should be inherited from Staff class
    }
    public String getWard() { return ward; }
    public void setWard(String ward) { this.ward = ward; }
    
    public String getSpecialization() { return specialization; }
    public void setSpecialization(String specialization) { this.specialization = specialization; }
    
    public String getQualification() { return qualification; }
    public void setQualification(String qualification) { this.qualification = qualification; }
    
    public int getExperience() { return experience; }
    public void setExperience(int experience) { this.experience = experience; }
    
    public String getLicenseNo() { return licenseNo; }
    public void setLicenseNo(String licenseNo) { this.licenseNo = licenseNo; }
    
    public List<String> getAssignedPatients() { return new ArrayList<>(assignedPatients); }
    
    public String getShift() { return shift; }
    public void setShift(String shift) { this.shift = shift; }
    
    public int getMaxPatients() { return maxPatients; }
    public void setMaxPatients(int maxPatients) { this.maxPatients = maxPatients; }
    
    public int getCurrentPatientsCount() { return currentPatientsCount; }
    
    public String getRole() { return role; }
    
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
    
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
            
            if (currentPatientsCount < maxPatients && status.equals("At Capacity")) {
                status = "Available";
            }
            
            return true;
        }
        return false;
    }
    
    public boolean canAcceptMorePatients() {
        return currentPatientsCount < maxPatients && status.equals("Available");
    }
    
    public String getAvailabilityStatus() {
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
}