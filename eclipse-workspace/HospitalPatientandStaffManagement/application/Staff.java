package application;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

public class Staff {
    protected String staffId;
    protected String name;
    protected int age;
    protected String gender;
    protected String address;
    protected String phone;
    protected String email;
    protected String emergencyContact;
    protected String status;
    protected String specialization;
    protected int experience;
    protected String licenseNumber;
    protected String qualification;
    protected String department;
    protected List<String> additionalNotes;
    protected LocalDate dateJoined;
    
    // Constructor for DoctorDatabase
    public Staff(String staffId, String name, String specialization, int experience, String status) {
        this.staffId = staffId;
        this.name = name;
        this.specialization = specialization;
        this.experience = experience;
        this.status = status;
        this.additionalNotes = new ArrayList<>();
        this.dateJoined = LocalDate.now();
    }
    
    // Additional constructor with more details
    public Staff(String staffId, String name, int age, String gender, String specialization, 
                 int experience, String licenseNumber, String qualification, String department,
                 String phone, String address, String status) {
        this.staffId = staffId;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.specialization = specialization;
        this.experience = experience;
        this.licenseNumber = licenseNumber;
        this.qualification = qualification;
        this.department = department;
        this.phone = phone;
        this.address = address;
        this.status = status;
        this.additionalNotes = new ArrayList<>();
        this.dateJoined = LocalDate.now();
    }
    
    // Empty constructor
    public Staff() {
        this.additionalNotes = new ArrayList<>();
        this.dateJoined = LocalDate.now();
        this.status = "Active";
    }
    
    // Getters and Setters
    public String getStaffId() { return staffId; }
    public void setStaffId(String staffId) { this.staffId = staffId; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }
    
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
    
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getEmergencyContact() { return emergencyContact; }
    public void setEmergencyContact(String emergencyContact) { this.emergencyContact = emergencyContact; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public String getSpecialization() { return specialization; }
    public void setSpecialization(String specialization) { this.specialization = specialization; }
    
    public int getExperience() { return experience; }
    public void setExperience(int experience) { this.experience = experience; }
    
    public String getLicenseNumber() { return licenseNumber; }
    public void setLicenseNumber(String licenseNumber) { this.licenseNumber = licenseNumber; }
    
    public String getQualification() { return qualification; }
    public void setQualification(String qualification) { this.qualification = qualification; }
    
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
    
    public List<String> getAdditionalNotes() { return new ArrayList<>(additionalNotes); }
    public void addAdditionalNote(String note) {
        if (note != null && !note.trim().isEmpty()) {
            this.additionalNotes.add(note);
        }
    }
    
    public LocalDate getDateJoined() { return dateJoined; }
    public void setDateJoined(LocalDate dateJoined) { this.dateJoined = dateJoined; }
    
    // Common display method
    public void displayBasicInfo() {
        System.out.println("\n=== Staff Information ===");
        System.out.println("ID: " + this.staffId);
        System.out.println("Name: " + this.name);
        System.out.println("Age: " + this.age);
        System.out.println("Gender: " + this.gender);
        System.out.println("Specialization: " + this.specialization);
        System.out.println("Experience: " + this.experience + " years");
        System.out.println("Status: " + this.status);
        System.out.println("Phone: " + this.phone);
        System.out.println("Email: " + this.email);
        System.out.println("Address: " + this.address);
        System.out.println("Department: " + this.department);
        System.out.println("Qualification: " + this.qualification);
        System.out.println("License: " + this.licenseNumber);
        System.out.println("Date Joined: " + this.dateJoined);
        System.out.println("========================\n");
    }
    
    // Check if staff is available
    public boolean isAvailable() {
        return "Active".equals(status) || 
               "Available".equals(status) || 
               "Available for Consultation".equals(status);
    }
    
    // Common validation method
    public boolean validateStaff() {
        return staffId != null && !staffId.isEmpty() &&
               name != null && !name.isEmpty() &&
               age >= 18 && age <= 70 &&
               phone != null && phone.matches("\\d{10,}");
    }
    
    // Helper method to set all fields from AddDoctorForm
    public void setAllDetails(String name, int age, String gender, String specialization, 
                              int experience, String licenseNumber, String qualification, 
                              String department, String phone, String address, String email) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.specialization = specialization;
        this.experience = experience;
        this.licenseNumber = licenseNumber;
        this.qualification = qualification;
        this.department = department;
        this.phone = phone;
        this.address = address;
        this.email = email;
    }
}