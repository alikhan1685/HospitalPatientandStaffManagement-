package application;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

public class Nurses extends StaffID {
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
                 String gender, String address, String phone , String ward) {
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
            this.maxPatients = 15; // Nurses typically handle fewer patients than doctors
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
    
    // Constructor with additional parameters
    public Nurses(String id, String name, int age, String specialization, 
                 String qualification, int experience, String licenseNo, 
                 String gender, String address, String phone, 
                 String department, String shift, int maxPatients, 
                 String certifications, String assignedDoctorId , String ward) {
        this(id, name, age, specialization, qualification, experience, 
             licenseNo, gender, address, phone , ward);
        this.department = department;
        this.shift = shift;
        this.maxPatients = maxPatients;
        this.certifications = certifications;
        this.assignedDoctorId = assignedDoctorId;
    }
    
    Nurses(String id, String name, String status, String licneseNo, String department){
    	this.id = id ;
    	this.name = name ;
    	this.department = department ;
    	this.licenseNo = licenseNo ;
    	this.status = status ;
    }

    // Getters and Setters
    
    public String getWard() { return ward; }

    public String getSpecialization() {
        return specialization;
    }
    
    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }
    
    public String getQualification() {
        return qualification;
    }
    
    public void setQualification(String qualification) {
        this.qualification = qualification;
    }
    
    public int getExperience() {
        return experience;
    }
    
    public void setExperience(int experience) {
        this.experience = experience;
    }
    
    public String getLicenseNo() {
        return licenseNo;
    }
    
    public void setLicenseNo(String licenseNo) {
        this.licenseNo = licenseNo;
    }
    
    public List<String> getAssignedPatients() {
        return new ArrayList<>(assignedPatients);
    }
    
    public String getShift() {
        return shift;
    }
    
    public void setShift(String shift) {
        this.shift = shift;
    }
    
    public int getMaxPatients() {
        return maxPatients;
    }
    
    public void setMaxPatients(int maxPatients) {
        this.maxPatients = maxPatients;
    }
    
    public int getCurrentPatientsCount() {
        return currentPatientsCount;
    }
    
    public String getRole() {
        return role;
    }
    
    public String getDepartment() {
        return department;
    }
    
    public void setDepartment(String department) {
        this.department = department;
    }
    
    public String getCertifications() {
        return certifications;
    }
    
    public void setCertifications(String certifications) {
        this.certifications = certifications;
    }
    
    public String getAssignedDoctorId() {
        return assignedDoctorId;
    }
    
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
            
            addAdditionalNote("Patient ID " + patientId + " assigned on " + 
                            LocalDate.now() + ". Total patients: " + currentPatientsCount);
            
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
            
            addAdditionalNote("Patient ID " + patientId + " removed on " + 
                            LocalDate.now() + ". Total patients: " + currentPatientsCount);
            
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
    
    // Add certification
    public void addCertification(String certification) {
        if (certifications == null || certifications.isEmpty()) {
            certifications = certification;
        } else {
            certifications += ", " + certification;
        }
        addAdditionalNote("Certification added: " + certification + " on " + LocalDate.now());
    }
    
    // Assign to a doctor
    public boolean assignToDoctor(String doctorId) {
        if (doctorId != null && !doctorId.trim().isEmpty()) {
            this.assignedDoctorId = doctorId;
            addAdditionalNote("Assigned to Doctor ID: " + doctorId + " on " + LocalDate.now());
            return true;
        }
        return false;
    }
    
    // Remove doctor assignment
    public void removeDoctorAssignment() {
        addAdditionalNote("Removed from Doctor ID: " + this.assignedDoctorId + " on " + LocalDate.now());
        this.assignedDoctorId = "";
    }
    
    // Display Information
    public void displayInfo() {
        try {
            System.out.println("\n" + "=".repeat(50));
            System.out.println("NURSE INFORMATION");
            System.out.println("=".repeat(50));
            super.displayBasicInfo();
            System.out.println("Specialization: " + this.specialization);
            System.out.println("Qualification: " + this.qualification);
            System.out.println("Experience: " + this.experience + " years");
            System.out.println("License No: " + this.licenseNo);
            System.out.println("Department: " + this.department);
            System.out.println("Shift: " + this.shift);
            System.out.println("Current Patients: " + this.currentPatientsCount + "/" + this.maxPatients);
            System.out.println("Workload: " + getWorkloadPercentage() + "%");
            System.out.println("Availability: " + getAvailabilityStatus());
            
            if (this.certifications != null && !this.certifications.isEmpty()) {
                System.out.println("Certifications: " + this.certifications);
            }
            
            if (this.assignedDoctorId != null && !this.assignedDoctorId.isEmpty()) {
                System.out.println("Assigned Doctor ID: " + this.assignedDoctorId);
            }
            
            System.out.println("\nAssigned Patients:");
            if (this.assignedPatients.isEmpty()) {
                System.out.println("  No patients currently assigned");
            } else {
                for (int i = 0; i < this.assignedPatients.size(); i++) {
                    System.out.println("  " + (i + 1) + ". Patient ID: " + this.assignedPatients.get(i));
                }
            }
            
            System.out.println("\nAdditional Notes:");
            if (this.additionalNotes.isEmpty()) {
                System.out.println("  No additional notes recorded");
            } else {
                for (int i = 0; i < this.additionalNotes.size(); i++) {
                    System.out.println("  " + (i + 1) + ". " + this.additionalNotes.get(i));
                }
            }
            System.out.println("=".repeat(50) + "\n");
            
        } catch (Exception e) {
            System.out.println("Error displaying nurse info: " + e);
        }
    }
    
    public String getNurseDetail() {
        String doctorInfo = (assignedDoctorId != null && !assignedDoctorId.isEmpty()) 
                          ? " | Assigned to: " + assignedDoctorId 
                          : "";
        
        return String.format("ID: %s | Nurse %s | %s | %s | %s | Exp: %d years | Status: %s | Patients: %d/%d%s",
                this.id,
                this.name,
                this.specialization,
                this.department,
                this.shift.split(" ")[0], // Just show shift type (Day, Evening, Night)
                this.experience,
                this.status,
                this.currentPatientsCount,
                this.maxPatients,
                doctorInfo);
    }
    
    public String getFullInfo() {
        String doctorInfo = (assignedDoctorId != null && !assignedDoctorId.isEmpty()) 
                          ? "\nAssigned Doctor ID: " + this.assignedDoctorId 
                          : "";
        
        String certInfo = (certifications != null && !certifications.isEmpty()) 
                        ? "\nCertifications: " + this.certifications 
                        : "";
        
        return String.format("Nurse ID: %s\nName: Nurse %s\nSpecialization: %s\n" +
                           "Department: %s\nQualification: %s\nExperience: %d years\n" +
                           "License: %s\nStatus: %s\nShift: %s\nPatients: %d/%d\nPhone: %s%s%s",
                this.id, this.name, this.specialization, this.department,
                this.qualification, this.experience, this.licenseNo, this.status,
                this.shift, this.currentPatientsCount, this.maxPatients, this.phone,
                doctorInfo, certInfo);
    }
    
    @Override
    public String toString() {
        return getNurseDetail();
    }
    
    public boolean isValidNurse() {
        return this.id != null && !this.id.isEmpty() &&
               this.name != null && !this.name.isEmpty() &&
               this.age > 21 && this.age < 65 && // Nurse age range
               this.specialization != null && !this.specialization.isEmpty() &&
               this.licenseNo != null && !this.licenseNo.isEmpty();
    }
    
    public boolean isAvailableForEmergency() {
        return status.equals("Available") || 
               (currentPatientsCount < maxPatients * 0.8); // 80% capacity or less for emergency
    }
    
    public void goOnLeave(String reason, LocalDate returnDate) {
        this.status = "On Leave";
        addAdditionalNote("Nurse went on leave: " + reason + 
                         ". Return date: " + returnDate);
    }
    
    public void returnFromLeave() {
        this.status = "Available";
        addAdditionalNote("Nurse returned from leave on " + LocalDate.now());
    }
    
    // Check if nurse is on duty based on shift
    public boolean isOnDuty() {
        String currentTime = LocalDate.now().toString(); // Simplified - in real app, check actual time
        return this.shift != null && !this.shift.contains("Off") && 
               this.status.equals("Available") || this.status.equals("On Duty");
    }
    
    // Get next shift
    public String getNextShift() {
        if (this.shift.contains("Day")) {
            return "Evening Shift (3 PM - 11 PM)";
        } else if (this.shift.contains("Evening")) {
            return "Night Shift (11 PM - 7 AM)";
        } else {
            return "Day Shift (7 AM - 3 PM)";
        }
    }
}