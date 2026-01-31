package application;
import java.util.ArrayList;
import java.util.List;
public class NurseDatabase {
    private static NurseDatabase instance;
    private List<Nurses> nurses;
    private NurseDatabase() {
        nurses = new ArrayList<>();
        initializeSampleNurses();
    }
    public static NurseDatabase getInstance() {
        if (instance == null) {
            instance = new NurseDatabase();
        }
        return instance;
    }
    
    public void addNurse(Nurses nurse) {
        nurses.add(nurse);
        System.out.println("✅ Nurse added: " + nurse.getName() + " (ID: " + nurse.getId() + ")");
        System.out.println("📊 Total nurses: " + nurses.size());
    }
    
    public List<Nurses> getAllNurses() {
        return new ArrayList<>(nurses);
    }
    
    public List<Nurses> getAvailableNurses() {
        List<Nurses> available = new ArrayList<>();
        for (Nurses nurse : nurses) {
            if ("Available".equals(nurse.getStatus()) && nurse.canAcceptMorePatients()) {
                available.add(nurse);
            }
        }
        return available;
    }
    
    public Nurses getNurseById(String nurseId) {
        for (Nurses nurse : nurses) {
            if (nurse.getId().equalsIgnoreCase(nurseId)) {
                return nurse;
            }
        }
        return null;
    }
    
    public List<Nurses> searchByName(String name) {
        List<Nurses> results = new ArrayList<>();
        String searchLower = name.toLowerCase();
        for (Nurses nurse : nurses) {
            if (nurse.getName().toLowerCase().contains(searchLower)) {
                results.add(nurse);
            }
        }
        return results;
    }
    
    public List<Nurses> searchByDepartment(String department) {
        List<Nurses> results = new ArrayList<>();
        String searchLower = department.toLowerCase();
        for (Nurses nurse : nurses) {
            if (nurse.getDepartment().toLowerCase().contains(searchLower)) {
                results.add(nurse);
            }
        }
        return results;
    }
    
    public List<String> getAllDepartments() {
        List<String> departments = new ArrayList<>();
        for (Nurses nurse : nurses) {
            String dept = nurse.getDepartment();
            if (!departments.contains(dept)) {
                departments.add(dept);
            }
        }
        return departments;
    }
    
    public int getNurseCount() {
        return nurses.size();
    }
    
    public int getAvailableNurseCount() {
        return getAvailableNurses().size();
    }
    
    public boolean assignNurseToPatient(String nurseId, String patientId) {
        Nurses nurse = getNurseById(nurseId);
        if (nurse != null && nurse.canAcceptMorePatients()) {
            if (nurse.assignPatient(patientId)) {
                System.out.println("✅ Nurse " + nurse.getName() + " assigned to patient " + patientId);
                return true;
            }
        }
        return false;
    }
    
    public boolean updateNurseStatus(String nurseId, String newStatus) {
        Nurses nurse = getNurseById(nurseId);
        if (nurse != null) {
            nurse.setStatus(newStatus);
            System.out.println("✅ Nurse status updated: " + nurse.getName() + " -> " + newStatus);
            return true;
        }
        return false;
    }
    
    private void initializeSampleNurses() {
        if (nurses.isEmpty()) {
            System.out.println("📝 Initializing sample nurse data...");
            
            // Sample Nurses
            Nurses nurse1 = new Nurses(
                "NUR-001",
                "Sarah Johnson",
                32,
                "General Nursing",
                "BSN",
                8,
                "NUR-123456",
                "Female",
                "Nurses Quarters, Room 101",
                "5550202345",
                "General Ward"
            );
            nurse1.setDepartment("General Ward");
            nurse1.setShift("Day Shift (7 AM - 3 PM)");
            nurse1.setCertifications("CPR, BLS");
            nurses.add(nurse1);
            
            Nurses nurse2 = new Nurses(
                "NUR-002",
                "Michael Chen",
                28,
                "Pediatric Nursing",
                "RN",
                5,
                "NUR-234567",
                "Male",
                "Staff Housing, Block A",
                "5550303456",
                "Pediatric Ward"
            );
            nurse2.setDepartment("Pediatrics Department");
            nurse2.setShift("Evening Shift (3 PM - 11 PM)");
            nurse2.setCertifications("PALS, NRP");
            nurses.add(nurse2);
            
            Nurses nurse3 = new Nurses(
                "NUR-003",
                "Fatima Ali",
                35,
                "Critical Care Nursing",
                "CCRN",
                10,
                "NUR-345678",
                "Female",
                "ICU Staff Quarters",
                "5550404567",
                "ICU"
            );
            nurse3.setDepartment("Intensive Care Unit (ICU)");
            nurse3.setShift("Night Shift (11 PM - 7 AM)");
            nurse3.setCertifications("ACLS, CCRN");
            nurses.add(nurse3);
            
            Nurses nurse4 = new Nurses(
                "NUR-004",
                "Robert Smith",
                40,
                "Surgical Nursing",
                "CNOR",
                12,
                "NUR-456789",
                "Male",
                "Surgical Wing, Room 205",
                "5550505678",
                "Operating Theater"
            );
            nurse4.setDepartment("Surgery Department");
            nurse4.setShift("Day Shift (7 AM - 3 PM)");
            nurse4.setCertifications("CNOR, STABLE");
            nurses.add(nurse4);
            
            System.out.println("✅ " + nurses.size() + " sample nurses added.");
        }
    }
    
    public void printAllNurses() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("NURSE DATABASE (" + nurses.size() + " nurses)");
        System.out.println("=".repeat(80));
        
        if (nurses.isEmpty()) {
            System.out.println("No nurses in database.");
        } else {
            for (int i = 0; i < nurses.size(); i++) {
                Nurses n = nurses.get(i);
                System.out.printf("%2d. %-8s | %-20s | %-15s | Shift: %-15s | Status: %-10s | Patients: %d/%d\n",
                    i + 1, n.getId(), n.getName(), n.getDepartment(), 
                    n.getShift(), n.getStatus(), n.getCurrentPatientsCount(), n.getMaxPatients());
            }
        }
        System.out.println("=".repeat(80));
    }
}