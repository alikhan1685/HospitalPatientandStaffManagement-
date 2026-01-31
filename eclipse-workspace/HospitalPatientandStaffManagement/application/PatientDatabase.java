package application;
import java.util.ArrayList;
import java.util.List;

public class PatientDatabase {
    private static PatientDatabase instance;
    private List<Patients> patients;
    
    private PatientDatabase() {
        patients = new ArrayList<>();
    }
    
    public static PatientDatabase getInstance() {
        if (instance == null) {
            instance = new PatientDatabase();
        }
        return instance;
    }
    
    public void addPatient(Patients patient) {
        patients.add(patient);
        System.out.println("✅ Patient added to database: " + patient.getName() + " (ID: " + patient.getId() + ")");
        System.out.println("📊 Total patients in database: " + patients.size());
    }
    
    public List<Patients> getAllPatients() {
        return new ArrayList<>(patients); 
    }
    
    public List<Patients> searchById(String id) {
        List<Patients> results = new ArrayList<>();
        for (Patients patient : patients) {
            if (patient.getId().equalsIgnoreCase(id)) {
                results.add(patient);
            }
        }
        return results;
    }
    
    public List<Patients> searchByName(String name) {
        List<Patients> results = new ArrayList<>();
        String searchLower = name.toLowerCase();
        for (Patients patient : patients) {
            if (patient.getName().toLowerCase().contains(searchLower)) {
                results.add(patient);
            }
        }
        return results;
    }
    
    // NEW METHOD: Get patient name by ID
    public String getPatientName(String patientId) {
        List<Patients> foundPatients = searchById(patientId);
        if (!foundPatients.isEmpty()) {
            return foundPatients.get(0).getName();
        } else {
            return "Patient not found";
        }
    }
    
    // NEW METHOD: Check if patient exists
    public boolean patientExists(String patientId) {
        return !searchById(patientId).isEmpty();
    }
    
    // NEW METHOD: Get patient by ID (returns null if not found)
    public Patients getPatient(String patientId) {
        List<Patients> foundPatients = searchById(patientId);
        if (!foundPatients.isEmpty()) {
            return foundPatients.get(0);
        }
        return null;
    }
    
    // NEW METHOD: Get total patient count
    public int getPatientCount() {
        return patients.size();
    }
    
    // Optional: Print all patients (for debugging)
    public void printAllPatients() {
        System.out.println("\n" + "=".repeat(70));
        System.out.println("PATIENT DATABASE (" + patients.size() + " patients)");
        System.out.println("=".repeat(70));
        
        if (patients.isEmpty()) {
            System.out.println("No patients in database.");
        } else {
            for (int i = 0; i < patients.size(); i++) {
                Patients p = patients.get(i);
                System.out.printf("%2d. %-10s | %-20s | Age: %3d | Doctor: %s\n",
                    i + 1, p.getId(), p.getName(), p.getAge(), 
                    p.getAssignedDoctorName() != null ? p.getAssignedDoctorName() : "Not assigned");
            }
        }
        System.out.println("=".repeat(70));
    }
}