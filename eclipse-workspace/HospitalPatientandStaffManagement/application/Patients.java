package application;
import java.util.*;

public class Patients {
    
    private static final String role = "Patient";
    private List<String> medicalHistory;
    private String assignedDoctorName;
    private boolean admitted;
    private String id;
    private String name;
    private int age;
    private String gender;
    private String address;
    private String bloodGroup;
    private String phone;
    
    // Constructor matching your parameters
    public Patients(String id, String name, int age, boolean admitted, 
                   String medicalHistory, String doctorName) {  // Removed email and emergencyContact
        try {
            this.id = id;
            this.name = name;
            this.age = age;
            this.admitted = admitted;
            this.assignedDoctorName = doctorName;
            
            // Initialize medicalHistory as ArrayList
            this.medicalHistory = new ArrayList<>();
            if (medicalHistory != null && !medicalHistory.trim().isEmpty()) {
                this.medicalHistory.add(medicalHistory);
            }
            
        } catch (Exception e) {
            System.out.println("Error Initializing Patient: " + e);
        }
    }
    
    // Alternative constructor with more fields
    public Patients(String id, String name, int age, String gender, String address, 
                   String phone, String bloodGroup,String assignedDoctorName) {
       // this(id, name, age, false, medicalHistory, doctorName);
        this.id = id ;
        this.name = name;
        this.age=age;
        this.gender=gender;
        this.address=address;
        this.phone=phone;
        this.bloodGroup=bloodGroup;
        this.assignedDoctorName=assignedDoctorName;
        this.medicalHistory=medicalHistory;    
    }
    
    // Constructor that accepts Person object
    public Patients(Person person, String medicalHistory, String doctorName) {
        this(person.getId(), person.getName(), person.getAge(), 
             person.getGender(), person.getAddress(), person.getPhone(), 
             person.getmedicalHistory(), doctorName);
    }
    
    // Getters and Setters
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public int getAge() {
        return age;
    }
    
    public void setAge(int age) {
        this.age = age;
    }
    
    public String getGender() {
        return gender;
    }
    
    public void setGender(String gender) {
        this.gender = gender;
    }
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public List<String> getMedicalHistory() {
        return medicalHistory;
    }
    
    public void setMedicalHistory(List<String> medicalHistory) {
        this.medicalHistory = medicalHistory;
    }
    
    public void addMedicalHistory(String history) {
        if (history != null && !history.trim().isEmpty()) {
            this.medicalHistory.add(history);
        }
    }
    
    public String getAssignedDoctorName() {
        return assignedDoctorName;
    }
    
    public void setAssignedDoctorName(String doctorName) {
        this.assignedDoctorName = doctorName;
    }
    
    public boolean isAdmitted() {
        return admitted;
    }
    
    public void setAdmitted(boolean admitted) {
        this.admitted = admitted;
    }
    
    public String getRole() {
        return role;
    }
	public String getBloodGroup() {
		return bloodGroup;
	}
    
    // Display Information
    public void displayInfo() {
        try {
            System.out.println("\n=== PATIENT INFORMATION ===");
            System.out.println("Patient ID: " + this.id);
            System.out.println("Name: " + this.name);
            System.out.println("Age: " + this.age);
            System.out.println("Gender: " + (this.gender != null ? this.gender : "Not specified"));
            System.out.println("Address: " + (this.address != null ? this.address : "Not specified"));
            System.out.println("Phone: " + (this.phone != null ? this.phone : "Not specified"));
            System.out.println("Status: " + (this.admitted ? "ADMITTED" : "NOT ADMITTED"));
            System.out.println("Assigned Doctor: " + (this.assignedDoctorName != null ? this.assignedDoctorName : "Not assigned"));
            
            System.out.println("\nMedical History:");
            if (this.medicalHistory == null || this.medicalHistory.isEmpty()) {
                System.out.println("  No medical history recorded");
            } else {
                for (int i = 0; i < this.medicalHistory.size(); i++) {
                    System.out.println("  " + (i + 1) + ". " + this.medicalHistory.get(i));
                }
            }
            System.out.println("=============================\n");
            
        } catch (Exception e) {
            System.out.println("Error displaying patient info: " + e);
        }
    }
    
    // Method to admit patient
    public void admitPatient() {
        this.admitted = true;
        this.addMedicalHistory("Patient admitted on: " + java.time.LocalDate.now());
    }
    
    // Method to discharge patient
    public void dischargePatient() {
        this.admitted = false;
        this.addMedicalHistory("Patient discharged on: " + java.time.LocalDate.now());
    }
    
    // Method to assign doctor
    public void assignDoctor(String doctorName) {
        this.assignedDoctorName = doctorName;
        this.addMedicalHistory("Assigned to " + doctorName + " on " + java.time.LocalDate.now());
    }
    
    // Get patient summary for quick view
    public String getPatientDetail() {
        return String.format("ID: %s | Name: %s | Age: %d | Status: %s | Doctor: %s",
                this.id,
                this.name,
                this.age,
                this.admitted ? "Admitted" : "Outpatient", 
                this.assignedDoctorName != null ? this.assignedDoctorName : "Unassigned");
    }
    
    @Override
    public String toString() {
        return getPatientDetail();
    }
    
    // Validation method
    public boolean isValidPatient() {
        return this.id != null && !this.id.isEmpty() &&
               this.name != null && !this.name.isEmpty() &&
               this.age > 0 && this.age < 150;
    }

}

