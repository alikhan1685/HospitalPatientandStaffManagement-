package application;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

public class Doctors extends StaffID {
    private static final String role = "Doctor";
    private String specialty;
    private String qualification;
    private int experience;
    private String licenseNo;
    private List<String> assignedPatients;
    private String schedule;
    private int maxPatients;
    private int currentPatientsCount;
    private String department;
    
    // Constructor
    public Doctors(String id, String name, int age, String speciality, 
                   String qualification, int experience, String licenseNo, 
                   String gender, String address, String phone) {
        super();
        try {
            this.id = id;
            this.name = name;
            this.age = age;
            this.specialty = specialty;
            this.qualification = qualification;
            this.experience = experience;
            this.licenseNo = licenseNo;
            this.gender = gender;
            this.address = address;
            this.phone = phone;
            this.status = "Available";
            this.maxPatients = 50;
            this.currentPatientsCount = 0;
            this.department = "General";
            
            this.assignedPatients = new ArrayList<>();
            this.schedule = "Mon-Fri: 9 AM - 5 PM";
            
        } catch (Exception e) {
            System.out.println("Error Adding Doctor: " + e);
        }
    }
    
    // Constructor with additional parameters
    public Doctors(String id, String name, int age, String specialization, 
                   String qualification, int experience, String licenseNo, 
                   String gender, String address, String phone, 
                   String department, String schedule, int maxPatients) {
        this(id, name, age, specialization, qualification, experience, 
             licenseNo, gender, address, phone);
        this.department = department;
        this.schedule = schedule;
        this.maxPatients = maxPatients;
    }
    Doctors(String id , String name , String status, String license_no, String department){
    	this.id = id ;
    	this.name = name ;
    	this.status = status ;
    	this.licenseNo = licenseNo ;
    	this.department = department ;
    	
    }
    
    // Getters and Setters
    public String getSpecialty() { return specialty; }
    
    public void setSpecialization(String speciality) {
        this.specialty = speciality;
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
    
    public String getSchedule() {
        return schedule;
    }
    
    public void setSchedule(String schedule) {
        this.schedule = schedule;
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
    
    // Display Information
    public void displayInfo() {
        try {
            System.out.println("\n" + "=".repeat(50));
            System.out.println("DOCTOR INFORMATION");
            System.out.println("=".repeat(50));
            super.displayBasicInfo();
            System.out.println("Specialization: " + this.specialty);
            System.out.println("Qualification: " + this.qualification);
            System.out.println("Experience: " + this.experience + " years");
            System.out.println("License No: " + this.licenseNo);
            System.out.println("Department: " + this.department);
            System.out.println("Schedule: " + this.schedule);
            System.out.println("Current Patients: " + this.currentPatientsCount + "/" + this.maxPatients);
            System.out.println("Workload: " + getWorkloadPercentage() + "%");
            System.out.println("Availability: " + getAvailabilityStatus());
            
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
            System.out.println("Error displaying doctor info: " + e);
        }
    }
    
    public String getDoctorDetail() {
        return String.format("ID: %s | Dr. %s | %s | %s | Exp: %d years | Status: %s | Patients: %d/%d",
                this.id,
                this.name,
                this.specialty,
                this.department,
                this.experience,
                this.status,
                this.currentPatientsCount,
                this.maxPatients);
    }
    
    public String getFullInfo() {
        return String.format("Doctor ID: %s\nName: Dr. %s\nSpecialization: %s\n" +
                           "Department: %s\nQualification: %s\nExperience: %d years\n" +
                           "License: %s\nStatus: %s\nPatients: %d/%d\nPhone: %s\nSchedule: %s",
                this.id, this.name, this.specialty, this.department,
                this.qualification, this.experience, this.licenseNo, this.status,
                this.currentPatientsCount, this.maxPatients, this.phone, this.schedule);
    }
    
    @Override
    public String toString() {
        return getDoctorDetail();
    }
    
    public boolean isValidDoctor() {
        return this.id != null && !this.id.isEmpty() &&
               this.name != null && !this.name.isEmpty() &&
               this.age > 25 && this.age < 70 &&
               this.specialty!= null && !this.specialty.isEmpty() &&
               this.licenseNo != null && !this.licenseNo.isEmpty();
    }
    
    public boolean isAvailableForEmergency() {
        return status.equals("Available") || 
               (currentPatientsCount < maxPatients * 0.9);
    }
    
    public void goOnLeave(String reason, LocalDate returnDate) {
        this.status = "On Leave";
        addAdditionalNote("Doctor went on leave: " + reason + 
                         ". Return date: " + returnDate);
    }
    
    public void returnFromLeave() {
        this.status = "Available";
        addAdditionalNote("Doctor returned from leave on " + LocalDate.now());
    }
}
