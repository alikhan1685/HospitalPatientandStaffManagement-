package application;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

public class Technician extends StaffID {
    private static final String role = "Technician";
    private String specialization;
    private String qualification;
    private int experience;
    private String licenseNo;
    private List<String> assignedEquipment;
    private String shift;
    private int maxDailyTests;
    private int testsToday;
    private String department;
    private List<String> certifications;
    
    // Constructor
    public Technician(String id, String name, int age, String specialization, 
                     String qualification, int experience, String licenseNo, 
                     String gender, String address, String phone) {
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
            this.maxDailyTests = 30; // Technicians have daily test capacity
            this.testsToday = 0;
            this.department = "Laboratory";
            this.shift = "Day Shift (8 AM - 4 PM)";
            
            this.assignedEquipment = new ArrayList<>();
            this.certifications = new ArrayList<>();
            
        } catch (Exception e) {
            System.out.println("Error Adding Technician: " + e);
        }
    }
    
    // Constructor with additional parameters
    public Technician(String id, String name, int age, String specialization, 
                     String qualification, int experience, String licenseNo, 
                     String gender, String address, String phone, 
                     String department, String shift, int maxDailyTests) {
        this(id, name, age, specialization, qualification, experience, 
             licenseNo, gender, address, phone);
        this.department = department;
        this.shift = shift;
        this.maxDailyTests = maxDailyTests;
    }
    
    // Getters and Setters
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
    
    public List<String> getAssignedEquipment() {
        return new ArrayList<>(assignedEquipment);
    }
    
    public String getShift() {
        return shift;
    }
    
    public void setShift(String shift) {
        this.shift = shift;
    }
    
    public int getMaxDailyTests() {
        return maxDailyTests;
    }
    
    public void setMaxDailyTests(int maxDailyTests) {
        this.maxDailyTests = maxDailyTests;
    }
    
    public int getTestsToday() {
        return testsToday;
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
    
    public List<String> getCertifications() {
        return new ArrayList<>(certifications);
    }
    
    // Add certification
    public void addCertification(String certification) {
        if (certification != null && !certification.trim().isEmpty()) {
            this.certifications.add(certification);
            addAdditionalNote("Certification added: " + certification + " on " + LocalDate.now());
        }
    }
    
    // getStatus() method - Inherited from StaffID
    // This method is already available from the parent StaffID class
    
    // Business Logic Methods
    public boolean assignEquipment(String equipmentId) {
        if (equipmentId != null && !equipmentId.trim().isEmpty() && 
            !assignedEquipment.contains(equipmentId)) {
            
            assignedEquipment.add(equipmentId);
            addAdditionalNote("Equipment ID " + equipmentId + " assigned on " + LocalDate.now());
            return true;
        }
        return false;
    }
    
    public boolean removeEquipment(String equipmentId) {
        if (equipmentId != null && assignedEquipment.contains(equipmentId)) {
            assignedEquipment.remove(equipmentId);
            addAdditionalNote("Equipment ID " + equipmentId + " removed on " + LocalDate.now());
            return true;
        }
        return false;
    }
    
    // Add test to daily count
    public boolean addTest() {
        if (testsToday < maxDailyTests) {
            testsToday++;
            
            addAdditionalNote("Test performed. Tests today: " + testsToday + "/" + maxDailyTests);
            
            // Update status based on workload
            if (testsToday >= maxDailyTests) {
                status = "At Capacity";
                addAdditionalNote("Reached daily capacity of " + maxDailyTests + " tests on " + LocalDate.now());
            } else if (testsToday >= maxDailyTests * 0.8) {
                status = "Busy";
            }
            
            return true;
        }
        return false;
    }
    
    // Reset daily tests (call at end of day)
    public void resetDailyTests() {
        this.testsToday = 0;
        if (status.equals("At Capacity") || status.equals("Busy")) {
            status = "Available";
        }
        addAdditionalNote("Daily tests reset on " + LocalDate.now() + ". Tests today: " + testsToday);
    }
    
    public boolean canPerformMoreTests() {
        return testsToday < maxDailyTests && !status.equals("At Capacity");
    }
    
    public String getAvailabilityStatus() {
        if (status.equals("Available") && testsToday < maxDailyTests) {
            return "Available for tests";
        } else if (status.equals("At Capacity")) {
            return "At daily capacity (" + testsToday + "/" + maxDailyTests + ")";
        } else if (testsToday >= maxDailyTests * 0.8) {
            return "Nearly at capacity (" + testsToday + "/" + maxDailyTests + ")";
        } else {
            return status;
        }
    }
    
    public int getCapacityPercentage() {
        if (maxDailyTests > 0) {
            return (testsToday * 100) / maxDailyTests;
        }
        return 0;
    }
    
    // Display Information
    public void displayInfo() {
        try {
            System.out.println("\n" + "=".repeat(50));
            System.out.println("TECHNICIAN INFORMATION");
            System.out.println("=".repeat(50));
            super.displayBasicInfo();
            System.out.println("Specialization: " + this.specialization);
            System.out.println("Qualification: " + this.qualification);
            System.out.println("Experience: " + this.experience + " years");
            System.out.println("License No: " + this.licenseNo);
            System.out.println("Department: " + this.department);
            System.out.println("Shift: " + this.shift);
            System.out.println("Tests Today: " + this.testsToday + "/" + this.maxDailyTests);
            System.out.println("Capacity: " + getCapacityPercentage() + "%");
            System.out.println("Availability: " + getAvailabilityStatus());
            
            System.out.println("\nAssigned Equipment:");
            if (this.assignedEquipment.isEmpty()) {
                System.out.println("  No equipment currently assigned");
            } else {
                for (int i = 0; i < this.assignedEquipment.size(); i++) {
                    System.out.println("  " + (i + 1) + ". Equipment ID: " + this.assignedEquipment.get(i));
                }
            }
            
            System.out.println("\nCertifications:");
            if (this.certifications.isEmpty()) {
                System.out.println("  No certifications recorded");
            } else {
                for (int i = 0; i < this.certifications.size(); i++) {
                    System.out.println("  " + (i + 1) + ". " + this.certifications.get(i));
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
            System.out.println("Error displaying technician info: " + e);
        }
    }
    
    public String getTechnicianDetail() {
        return String.format("ID: %s | Tech %s | %s | %s | %s | Exp: %d years | Status: %s | Tests: %d/%d",
                this.id,
                this.name,
                this.specialization,
                this.department,
                this.shift.split(" ")[0], // Just show shift type (Day, Evening, Night)
                this.experience,
                this.status,
                this.testsToday,
                this.maxDailyTests);
    }
    
    public String getFullInfo() {
        String certInfo = "";
        if (!certifications.isEmpty()) {
            certInfo = "\nCertifications: " + String.join(", ", certifications);
        }
        
        return String.format("Technician ID: %s\nName: Tech %s\nSpecialization: %s\n" +
                           "Department: %s\nQualification: %s\nExperience: %d years\n" +
                           "License: %s\nStatus: %s\nShift: %s\nTests Today: %d/%d\nPhone: %s%s",
                this.id, this.name, this.specialization, this.department,
                this.qualification, this.experience, this.licenseNo, this.status,
                this.shift, this.testsToday, this.maxDailyTests, this.phone,
                certInfo);
    }
    
    @Override
    public String toString() {
        return getTechnicianDetail();
    }
    
    public boolean isValidTechnician() {
        return this.id != null && !this.id.isEmpty() &&
               this.name != null && !this.name.isEmpty() &&
               this.age > 20 && this.age < 65 && // Technician age range
               this.specialization != null && !this.specialization.isEmpty() &&
               this.licenseNo != null && !this.licenseNo.isEmpty();
    }
    
    public boolean isAvailableForEmergency() {
        return status.equals("Available") || 
               (testsToday < maxDailyTests * 0.7); // 70% capacity or less for emergency
    }
    
    public void goOnLeave(String reason, LocalDate returnDate) {
        this.status = "On Leave";
        addAdditionalNote("Technician went on leave: " + reason + 
                         ". Return date: " + returnDate);
    }
    
    public void returnFromLeave() {
        this.status = "Available";
        addAdditionalNote("Technician returned from leave on " + LocalDate.now());
    }
    
    // Check if technician is on duty based on shift
    public boolean isOnDuty() {
        return this.shift != null && !this.shift.contains("Off") && 
               (this.status.equals("Available") || this.status.equals("On Duty"));
    }
    
    // Get equipment list as string
    public String getEquipmentListAsString() {
        if (assignedEquipment.isEmpty()) {
            return "No equipment assigned";
        }
        return String.join(", ", assignedEquipment);
    }
    
    // Get certifications as string
    public String getCertificationsAsString() {
        if (certifications.isEmpty()) {
            return "No certifications";
        }
        return String.join(", ", certifications);
    }
}