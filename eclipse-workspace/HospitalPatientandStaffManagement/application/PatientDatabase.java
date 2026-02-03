package application;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

public class PatientDatabase {
    private static PatientDatabase instance;
    private List<Patients> patients;
    private int nextPatientId = 1;
    
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
        // Check if patient has ID, if not generate one
        if (patient.getId() == null || patient.getId().isEmpty()) {
            String newId = generateNextPatientId();
            patient.setId(newId);
        }
        
        patients.add(patient);
        System.out.println("✅ Patient added to database: " + patient.getName() + " (ID: " + patient.getId() + ")");
        System.out.println("📊 Total patients in database: " + patients.size());
    }
    
    public String generateNextPatientId() {
        if (patients.isEmpty()) {
            return String.format("P%03d", 1);
        }
        
        // Find the lowest available ID starting from 1
        for (int i = 1; i <= 999; i++) {
            String potentialId = String.format("P%03d", i);
            boolean idExists = false;
            
            for (Patients patient : patients) {
                if (patient.getId().equals(potentialId)) {
                    idExists = true;
                    break;
                }
            }
            
            if (!idExists) {
                return potentialId;
            }
        }
        
        // If all IDs 1-999 are used, find the maximum ID and add 1
        int maxNumber = 0;
        for (Patients patient : patients) {
            String id = patient.getId();
            if (id != null && id.startsWith("P")) {
                try {
                    String numberPart = id.substring(1);
                    int number = Integer.parseInt(numberPart);
                    if (number > maxNumber) {
                        maxNumber = number;
                    }
                } catch (NumberFormatException e) {
                    // Skip if not a valid number format
                }
            }
        }
        
        return String.format("P%03d", maxNumber + 1);
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
    
    public boolean deletePatient(String patientId) {
        Iterator<Patients> iterator = patients.iterator();
        while (iterator.hasNext()) {
            Patients patient = iterator.next();
            if (patient.getId().equals(patientId)) {
                iterator.remove();
                System.out.println("❌ Patient deleted from database: " + patient.getName() + " (ID: " + patientId + ")");
                System.out.println("📊 Total patients in database: " + patients.size());
                return true;
            }
        }
        System.out.println("⚠️ Patient not found for deletion: " + patientId);
        return false;
    }
    
    public String getPatientName(String patientId) {
        List<Patients> foundPatients = searchById(patientId);
        if (!foundPatients.isEmpty()) {
            return foundPatients.get(0).getName();
        } else {
            return "Patient not found";
        }
    }
    
    public boolean patientExists(String patientId) {
        return !searchById(patientId).isEmpty();
    }
    
    public Patients getPatient(String patientId) {
        List<Patients> foundPatients = searchById(patientId);
        if (!foundPatients.isEmpty()) {
            return foundPatients.get(0);
        }
        return null;
    }
    
    public int getPatientCount() {
        return patients.size();
    }
    
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