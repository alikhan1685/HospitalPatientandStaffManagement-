package application;

import java.util.ArrayList;
import java.util.List;

public class DoctorDatabase  {
    private static DoctorDatabase instance;
    private List<Staff> doctors;
    
    private DoctorDatabase() {
        doctors = new ArrayList<>();
        initializeSampleDoctors();
    }
    
    public static DoctorDatabase getInstance() {
        if (instance == null) {
            instance = new DoctorDatabase();
        }
        return instance;
    }
    
    public void addDoctor(Staff doctor) {
        doctors.add(doctor);
        System.out.println("✅ Doctor added: " + doctor.getName() + " (ID: " + doctor.getStaffId() + ")");
        System.out.println("📊 Total doctors: " + doctors.size());
    }
    
    public List<Staff> getAllDoctors() {
        return new ArrayList<>(doctors);
    }
    
    public List<Staff> getAvailableDoctors() {
        List<Staff> available = new ArrayList<>();
        for (Staff doctor : doctors) {
            if ("Active".equals(doctor.getStatus()) || 
                "Available".equals(doctor.getStatus()) ||
                "Available for Consultation".equals(doctor.getStatus())) {
                available.add(doctor);
            }
        }
        return available;
    }
    
    public List<Staff> searchByName(String name) {
        List<Staff> results = new ArrayList<>();
        String searchLower = name.toLowerCase();
        for (Staff doctor : doctors) {
            if (doctor.getName().toLowerCase().contains(searchLower)) {
                results.add(doctor);
            }
        }
        return results;
    }
    
    public List<Staff> searchBySpecialization(String specialization) {
        List<Staff> results = new ArrayList<>();
        String searchLower = specialization.toLowerCase();
        for (Staff doctor : doctors) {
            if (doctor.getSpecialization().toLowerCase().contains(searchLower)) {
                results.add(doctor);
            }
        }
        return results;
    }
    
    public Staff getDoctorById(String doctorId) {
        for (Staff doctor : doctors) {
            if (doctor.getStaffId().equalsIgnoreCase(doctorId)) {
                return doctor;
            }
        }
        return null;
    }
    
    public List<String> getAllSpecializations() {
        List<String> specializations = new ArrayList<>();
        for (Staff doctor : doctors) {
            String spec = doctor.getSpecialization();
            if (!specializations.contains(spec)) {
                specializations.add(spec);
            }
        }
        return specializations;
    }
    
    public int getDoctorCount() {
        return doctors.size();
    }
    
    public int getAvailableDoctorCount() {
        return getAvailableDoctors().size();
    }
    
    public boolean updateDoctorStatus(String doctorId, String newStatus) {
        Staff doctor = getDoctorById(doctorId);
        if (doctor != null) {
            doctor.setStatus(newStatus);
            System.out.println("✅ Doctor status updated: " + doctor.getName() + " -> " + newStatus);
            return true;
        }
        return false;
    }
    
    public boolean assignDoctorToPatient(String doctorId, String patientId) {
        Staff doctor = getDoctorById(doctorId);
        if (doctor != null && "Active".equals(doctor.getStatus())) {
            // Here you would typically link doctor to patient
            System.out.println("✅ Doctor " + doctor.getName() + " assigned to patient " + patientId);
            return true;
        }
        return false;
    }
    
    private void initializeSampleDoctors() {
        if (doctors.isEmpty()) {
            System.out.println("📝 Initializing sample doctor data...");
            
            // Sample doctors
            Staff doc1 = new Staff();
            doc1.setStaffId("DOC-001");
            doc1.setName("Dr. Ahmed Khan");
            doc1.setAge(45);
            doc1.setGender("Male");
            doc1.setSpecialization("Cardiology");
            doc1.setExperience(15);
            doc1.setPhone("5550101234");
            doc1.setEmail("ahmed.khan@hospital.com");
            doc1.setLicenseNumber("MED-123456");
            doc1.setAddress("Main Hospital, Cardiology Wing");
            doc1.setStatus("Active");
            doc1.setQualification("MD, Cardiology");
            doctors.add(doc1);
            
            Staff doc2 = new Staff();
            doc2.setStaffId("DOC-002");
            doc2.setName("Dr. Sarah Miller");
            doc2.setAge(38);
            doc2.setGender("Female");
            doc2.setSpecialization("Pediatrics");
            doc2.setExperience(12);
            doc2.setPhone("5550202345");
            doc2.setEmail("sarah.miller@hospital.com");
            doc2.setLicenseNumber("MED-234567");
            doc2.setAddress("Children's Hospital, 2nd Floor");
            doc2.setStatus("Active");
            doc2.setQualification("MD, Pediatrics");
            doctors.add(doc2);
            
            Staff doc3 = new Staff();
            doc3.setStaffId("DOC-003");
            doc3.setName("Dr. Robert Chen");
            doc3.setAge(52);
            doc3.setGender("Male");
            doc3.setSpecialization("Neurology");
            doc3.setExperience(20);
            doc3.setPhone("5550303456");
            doc3.setEmail("robert.chen@hospital.com");
            doc3.setLicenseNumber("MED-345678");
            doc3.setAddress("Neuro Center, Room 301");
            doc3.setStatus("Available for Consultation");
            doc3.setQualification("PhD, Neurology");
            doctors.add(doc3);
            
            Staff doc4 = new Staff();
            doc4.setStaffId("DOC-004");
            doc4.setName("Dr. Fatima Ali");
            doc4.setAge(41);
            doc4.setGender("Female");
            doc4.setSpecialization("Dermatology");
            doc4.setExperience(10);
            doc4.setPhone("5550404567");
            doc4.setEmail("fatima.ali@hospital.com");
            doc4.setLicenseNumber("MED-456789");
            doc4.setAddress("Skin Care Center, Ground Floor");
            doc4.setStatus("On Leave");
            doc4.setQualification("MD, Dermatology");
            doctors.add(doc4);
            
            System.out.println("✅ " + doctors.size() + " sample doctors added.");
        }
    }
    
    public void printAllDoctors() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("DOCTOR DATABASE (" + doctors.size() + " doctors)");
        System.out.println("=".repeat(80));
        
        if (doctors.isEmpty()) {
            System.out.println("No doctors in database.");
        } else {
            for (int i = 0; i < doctors.size(); i++) {
                Staff d = doctors.get(i);
                System.out.printf("%2d. %-8s | %-20s | %-15s | Exp: %2d yrs | Status: %-20s\n",
                    i + 1, d.getStaffId(), d.getName(), d.getSpecialization(), 
                    d.getExperience(), d.getStatus());
            }
        }
        System.out.println("=".repeat(80));
    }
}