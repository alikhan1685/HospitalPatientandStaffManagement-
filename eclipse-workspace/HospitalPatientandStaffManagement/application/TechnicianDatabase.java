package application;

import java.util.ArrayList;
import java.util.List;

public class TechnicianDatabase {
    private static TechnicianDatabase instance;
    private List<Technicians> technicians;
    
    private TechnicianDatabase() {
        technicians = new ArrayList<>();
    }
    
    public static TechnicianDatabase getInstance() {
        if (instance == null) {
            instance = new TechnicianDatabase();
        }
        return instance;
    }
    
    public void addTechnician(Technicians technician) {
        technicians.add(technician);
        System.out.println("✅ Technician added: " + technician.getName() + " (ID: " + technician.getStaffId() + ")");
        System.out.println("📊 Total technicians: " + technicians.size());
    }
    
    public List<Technicians> getAllTechnicians() {
        return new ArrayList<>(technicians);
    }
   
    public List<Technicians> getAvailableTechnicians() {
        List<Technicians> available = new ArrayList<>();
        for (Technicians technician : technicians) {
            if ("Available".equals(technician.getStatus()) && technician.canAcceptMorePatients()) {
                available.add(technician);
            }
        }
        return available;
    }
    
    public Technicians getTechnicianById(String technicianId) {
        for (Technicians technician : technicians) {
            if (technician.getStaffId() != null && technician.getStaffId().equalsIgnoreCase(technicianId)) {
                return technician;
            }
        }
        return null;
    }
    
    public List<Technicians> searchByName(String name) {
        List<Technicians> results = new ArrayList<>();
        String searchLower = name.toLowerCase();
        for (Technicians technician : technicians) {
            if (technician.getName().toLowerCase().contains(searchLower)) {
                results.add(technician);
            }
        }
        return results;
    }
    
    public List<Technicians> searchByDepartment(String department) {
        List<Technicians> results = new ArrayList<>();
        String searchLower = department.toLowerCase();
        for (Technicians technician : technicians) {
            if (technician.getDepartment().toLowerCase().contains(searchLower)) {
                results.add(technician);
            }
        }
        return results;
    }
    
    public List<String> getAllDepartments() {
        List<String> departments = new ArrayList<>();
        for (Technicians technician : technicians) {
            String dept = technician.getDepartment();
            if (dept != null && !departments.contains(dept)) {
                departments.add(dept);
            }
        }
        return departments;
    }
    
    public int getTechnicianCount() {
        return technicians.size();
    }
    
    public int getAvailableTechnicianCount() {
        return getAvailableTechnicians().size();
    }
    
    public boolean assignTechnicianToPatient(String technicianId, String patientId) {
        Technicians technician = getTechnicianById(technicianId);
        if (technician != null && technician.canAcceptMorePatients()) {
            if (technician.assignPatient(patientId)) {
                System.out.println("✅ Technician " + technician.getName() + " assigned to patient " + patientId);
                return true;
            }
        }
        return false;
    }
    
    public boolean updateTechnicianStatus(String technicianId, String newStatus) {
        Technicians technician = getTechnicianById(technicianId);
        if (technician != null) {
            technician.setStatus(newStatus);
            System.out.println("✅ Technician status updated: " + technician.getName() + " -> " + newStatus);
            return true;
        }
        return false;
    }
    
  
    public void printAllTechnicians() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("TECHNICIAN DATABASE (" + technicians.size() + " technicians)");
        System.out.println("=".repeat(80));
        
        if (technicians.isEmpty()) {
            System.out.println("No technicians in database.");
        } else {
            for (int i = 0; i < technicians.size(); i++) {
                Technicians t = technicians.get(i);
                System.out.printf("%2d. %-8s | %-20s | %-15s | Shift: %-15s | Status: %-10s | Patients: %d/%d\n",
                    i + 1, 
                    t.getStaffId(),
                    t.getName(), 
                    t.getDepartment(), 
                    t.getShift(), 
                    t.getStatus(), 
                    t.getCurrentPatientsCount(), 
                    t.getMaxPatients());
            }
        }
        System.out.println("=".repeat(80));
    }
}