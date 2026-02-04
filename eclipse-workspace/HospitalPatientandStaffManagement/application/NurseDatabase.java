package application;
import java.util.ArrayList;
import java.util.List;

public class NurseDatabase {
    private static NurseDatabase instance;
    private List<Nurses> nurses;
    
    private NurseDatabase() {
        nurses = new ArrayList<>();
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
            nurse.setStaffId(newId);
        }
        
        nurses.add(nurse);
        System.out.println("✅ Nurse added to database: " + nurse.getName() + " (ID: " + nurse.getStaffId() + ")");
        System.out.println("📊 Total nurses in database: " + nurses.size());
    }
    
    public String generateNextNurseId() {
        if (nurses.isEmpty()) {
            return "N001";
        }
        
        // Find the maximum existing ID
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