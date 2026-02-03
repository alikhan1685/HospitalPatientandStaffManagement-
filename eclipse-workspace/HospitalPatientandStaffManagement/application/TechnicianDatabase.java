package application;

import java.util.ArrayList;
import java.util.List;

public class TechnicianDatabase {
    private static TechnicianDatabase instance;
    private List<Technicians> technicians;
    
    private TechnicianDatabase() {
        technicians = new ArrayList<>();
        initializeSampleTechnicians();
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
    
    private void initializeSampleTechnicians() {
        if (technicians.isEmpty()) {
            System.out.println("📝 Initializing sample technician data...");
            
            // Sample Technicians
            Technicians tech1 = new Technicians(
                "TEC-001",
                "David Wilson",
                35,
                "Lab Technician",
                "MLT",
                8,
                "TEC-123456",
                "Male",
                "Medical Lab, Room 101",
                "5550202345",
                "Laboratory"
            );
            tech1.setDepartment("Laboratory Department");
            tech1.setShift("Day Shift (7 AM - 3 PM)");
            tech1.setCertifications("ASCP, Phlebotomy");
            technicians.add(tech1);
            
            Technicians tech2 = new Technicians(
                "TEC-002",
                "Lisa Garcia",
                28,
                "Radiology Technician",
                "RT",
                5,
                "TEC-234567",
                "Female",
                "Radiology Wing, Room 205",
                "5550303456",
                "Radiology"
            );
            tech2.setDepartment("Radiology Department");
            tech2.setShift("Evening Shift (3 PM - 11 PM)");
            tech2.setCertifications("ARRT, BLS");
            technicians.add(tech2);
            
            Technicians tech3 = new Technicians(
                "TEC-003",
                "James Miller",
                40,
                "Surgical Technician",
                "CST",
                12,
                "TEC-345678",
                "Male",
                "Surgical Wing, Room 305",
                "5550404567",
                "Operating Room"
            );
            tech3.setDepartment("Surgery Department");
            tech3.setShift("Morning Shift (6 AM - 2 PM)");
            tech3.setCertifications("CST, CPR");
            technicians.add(tech3);
            
            Technicians tech4 = new Technicians(
                "TEC-004",
                "Maria Rodriguez",
                32,
                "Cardiology Technician",
                "CCT",
                7,
                "TEC-456789",
                "Female",
                "Cardiology Lab, Room 401",
                "5550505678",
                "Cardiology"
            );
            tech4.setDepartment("Cardiology Department");
            tech4.setShift("Day Shift (7 AM - 3 PM)");
            tech4.setCertifications("CCT, ACLS");
            technicians.add(tech4);
            
            System.out.println("✅ " + technicians.size() + " sample technicians added.");
        }
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