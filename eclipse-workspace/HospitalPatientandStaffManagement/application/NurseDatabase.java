package application;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

public class NurseDatabase {
    private static NurseDatabase instance;
    private List<Nurses> nurses;
    private int nextNurseId = 1;
    
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
        // Check if nurse has ID, if not generate one
        if (nurse.getStaffId() == null || nurse.getStaffId().isEmpty()) {
            String newId = generateNextNurseId();
            nurse.setStaffId(newId); // Use setStaffId() instead of setId()
        }
        
        nurses.add(nurse);
        System.out.println("✅ Nurse added to database: " + nurse.getName() + " (ID: " + nurse.getStaffId() + ")");
        System.out.println("📊 Total nurses in database: " + nurses.size());
    }
    
    public String generateNextNurseId() {
        if (nurses.isEmpty()) {
            return String.format("N%03d", 1);
        }
        
        // Find the lowest available ID starting from 1
        for (int i = 1; i <= 999; i++) {
            String potentialId = String.format("N%03d", i);
            boolean idExists = false;
            
            for (Nurses nurse : nurses) {
                if (nurse.getStaffId() != null && nurse.getStaffId().equals(potentialId)) {
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
        for (Nurses nurse : nurses) {
            String id = nurse.getStaffId();
            if (id != null && id.startsWith("N")) {
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
        
        return String.format("N%03d", maxNumber + 1);
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
            if (nurse.getStaffId() != null && nurse.getStaffId().equalsIgnoreCase(nurseId)) {
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
            if (dept != null && !departments.contains(dept)) {
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
            
            // Sample Nurses with proper N001, N002 format
            Nurses nurse1 = new Nurses(
                "N001",
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
            nurse1.setStatus("Available");
            nurses.add(nurse1);
            
            Nurses nurse2 = new Nurses(
                "N002",
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
            nurse2.setStatus("Available");
            nurses.add(nurse2);
            
            Nurses nurse3 = new Nurses(
                "N003",
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
            nurse3.setStatus("Available");
            nurses.add(nurse3);
            
            Nurses nurse4 = new Nurses(
                "N004",
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
            nurse4.setStatus("Available");
            nurses.add(nurse4);
            
            System.out.println("✅ " + nurses.size() + " sample nurses added.");
            
            // Update nextNurseId based on existing nurses
            for (Nurses nurse : nurses) {
                if (nurse.getStaffId() != null && nurse.getStaffId().startsWith("N")) {
                    try {
                        String numberPart = nurse.getStaffId().substring(1);
                        int number = Integer.parseInt(numberPart);
                        if (number >= nextNurseId) {
                            nextNurseId = number + 1;
                        }
                    } catch (NumberFormatException e) {
                        // Skip if not a valid number format
                    }
                }
            }
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
                    i + 1, 
                    n.getStaffId(),
                    n.getName(), 
                    n.getDepartment(), 
                    n.getShift(), 
                    n.getStatus(), 
                    n.getCurrentPatientsCount(), 
                    n.getMaxPatients());
            }
        }
        System.out.println("=".repeat(80));
    }
}