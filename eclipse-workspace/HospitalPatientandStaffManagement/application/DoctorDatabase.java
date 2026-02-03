package application;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class DoctorDatabase {

    private static DoctorDatabase instance;
    private List<Staff> doctors;
    private Map<String, List<Assignment>> doctorAssignments; // Track doctor-patient assignments

    private DoctorDatabase() {
        doctors = new ArrayList<Staff>();
        doctorAssignments = new HashMap<>();
    }

    public static DoctorDatabase getInstance() {
        if (instance == null) {
            instance = new DoctorDatabase();
        }
        return instance;
    }

    public void addDoctor(Staff doctor) {
        doctors.add(doctor);
        doctorAssignments.put(doctor.getStaffId(), new ArrayList<>()); // Initialize assignments list
        System.out.println("✅ Doctor added: " + doctor.getName()
               + " (ID: " + doctor.getStaffId() + ")");
        System.out.println("📊 Total doctors: " + doctors.size());
    }

    public List<Staff> getAllDoctors() {
        return new ArrayList<Staff>(doctors);
    }

    public List<Staff> getAvailableDoctors() {
        List<Staff> available = new ArrayList<Staff>();

        for (Staff doctor : doctors) {
            if ("Active".equals(doctor.getStatus())
                    || "Available".equals(doctor.getStatus())
                    || "Available for Consultation".equals(doctor.getStatus())) {
                available.add(doctor);
            }
        }
        return available;
    }

    public List<Staff> searchByName(String name) {
        List<Staff> results = new ArrayList<Staff>();
        String searchLower = name.toLowerCase();

        for (Staff doctor : doctors) {
            if (doctor.getName().toLowerCase().contains(searchLower)) {
                results.add(doctor);
            }
        }
        return results;
    }

    public List<Staff> searchBySpecialization(String specialization) {
        List<Staff> results = new ArrayList<Staff>();
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
        List<String> specializations = new ArrayList<String>();

        for (Staff doctor : doctors) {
            String spec = doctor.getSpecialization();
            if (spec != null && !spec.isEmpty() && !specializations.contains(spec)) {
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
            System.out.println("✅ Doctor status updated: "
                    + doctor.getName() + " -> " + newStatus);
            return true;
        }
        return false;
    }

    // Updated method with patientName parameter
    public boolean assignDoctorToPatient(String doctorId, String patientId, String patientName) {
        Staff doctor = getDoctorById(doctorId);

        if (doctor != null && "Active".equals(doctor.getStatus())) {
            // Create assignment record
            Assignment assignment = new Assignment(doctorId, doctor.getName(), patientId, patientName);
            
            // Add to doctor's assignments
            if (!doctorAssignments.containsKey(doctorId)) {
                doctorAssignments.put(doctorId, new ArrayList<>());
            }
            doctorAssignments.get(doctorId).add(assignment);
            
            System.out.println("✅ Doctor " + doctor.getName()
                    + " assigned to patient " + patientName + " (ID: " + patientId + ")");
            
            // Print assignment details
            printAssignmentDetails(assignment);
            return true;
        }
        return false;
    }

    // Overloaded method for backward compatibility
    public boolean assignDoctorToPatient(String doctorId, String patientId) {
        return assignDoctorToPatient(doctorId, patientId, "Unknown Patient");
    }

    // Get all assignments for a specific doctor
    public List<Assignment> getDoctorAssignments(String doctorId) {
        return doctorAssignments.getOrDefault(doctorId, new ArrayList<>());
    }

    // Get all assignments across all doctors
    public List<Assignment> getAllAssignments() {
        List<Assignment> allAssignments = new ArrayList<>();
        for (List<Assignment> assignments : doctorAssignments.values()) {
            allAssignments.addAll(assignments);
        }
        return allAssignments;
    }

    // Get number of patients assigned to a doctor
    public int getAssignedPatientsCount(String doctorId) {
        return doctorAssignments.getOrDefault(doctorId, new ArrayList<>()).size();
    }

    // Print assignment details
    private void printAssignmentDetails(Assignment assignment) {
        System.out.println("\n📝 ASSIGNMENT DETAILS:");
        System.out.println("   Doctor ID: " + assignment.getDoctorId());
        System.out.println("   Doctor Name: " + assignment.getDoctorName());
        System.out.println("   Patient ID: " + assignment.getPatientId());
        System.out.println("   Patient Name: " + assignment.getPatientName());
        System.out.println("   Assignment Date: " + assignment.getAssignmentDate());
        System.out.println("   Status: " + assignment.getStatus());
        System.out.println("-".repeat(50));
    }

    // Print all assignments
    public void printAllAssignments() {
        System.out.println("\n" + "=".repeat(100));
        System.out.println("ALL DOCTOR-PATIENT ASSIGNMENTS");
        System.out.println("=".repeat(100));

        if (doctorAssignments.isEmpty()) {
            System.out.println("No assignments found.");
        } else {
            int totalAssignments = 0;
            for (String doctorId : doctorAssignments.keySet()) {
                List<Assignment> assignments = doctorAssignments.get(doctorId);
                if (!assignments.isEmpty()) {
                    Staff doctor = getDoctorById(doctorId);
                    if (doctor != null) {
                        System.out.println("\nDoctor: " + doctor.getName() + " (ID: " + doctorId + ")");
                        System.out.println("Specialization: " + doctor.getSpecialization());
                        System.out.println("-".repeat(50));
                        
                        for (int i = 0; i < assignments.size(); i++) {
                            Assignment a = assignments.get(i);
                            System.out.printf("  %d. Patient: %-20s (ID: %-8s) | Date: %-12s | Status: %s\n",
                                i + 1,
                                a.getPatientName(),
                                a.getPatientId(),
                                a.getAssignmentDate(),
                                a.getStatus()
                            );
                            totalAssignments++;
                        }
                    }
                }
            }
            System.out.println("\n📊 Total Assignments: " + totalAssignments);
        }
        System.out.println("=".repeat(100));
    }

    public void printAllDoctors() {
        System.out.println("\n" + "=".repeat(100));
        System.out.println("DOCTOR DATABASE (" + doctors.size() + " doctors)");
        System.out.println("=".repeat(100));

        if (doctors.isEmpty()) {
            System.out.println("No doctors in database.");
        } else {
            for (int i = 0; i < doctors.size(); i++) {
                Staff d = doctors.get(i);
                int assignedPatients = getAssignedPatientsCount(d.getStaffId());
                
                System.out.printf(
                        "%2d. %-8s | %-20s | %-20s | Exp: %2d yrs | Status: %-15s | Assigned Patients: %d\n",
                        i + 1,
                        d.getStaffId(),
                        d.getName(),
                        d.getSpecialization(),
                        d.getExperience(),
                        d.getStatus(),
                        assignedPatients
                );
            }
        }
        System.out.println("=".repeat(100));
    }
}