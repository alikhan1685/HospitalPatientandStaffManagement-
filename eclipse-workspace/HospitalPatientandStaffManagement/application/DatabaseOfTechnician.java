package application;

import java.util.ArrayList;
import java.util.List;

public class DatabaseOfTechnician {

    private static DatabaseOfTechnician instance;
    private static List<Staff> doctors;

    private DatabaseOfTechnician() {
        doctors = new ArrayList<Staff>();
    }

    public static DatabaseOfTechnician getInstance() {
        if (instance == null) {
            instance = new DatabaseOfTechnician();
        }
        return instance;
    }

    public void addDoctor(Staff doctor) {
        doctors.add(doctor);
        System.out.println("✅ Doctor added: " + doctor.getName()
               + " (ID: " + doctor.getStaffId() + ")");
        System.out.println("📊 Total doctors: " + doctors.size());
    }

    public static List<Staff> getAllDoctors() {
        return new ArrayList<Staff>(doctors);
    }

    // ✅ FIX: Changed return type to List<Staff>
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
            System.out.println("✅ Doctor status updated: "
                    + doctor.getName() + " -> " + newStatus);
            return true;
        }
        return false;
    }

    public boolean assignDoctorToPatient(String doctorId, String patientId) {
        Staff doctor = getDoctorById(doctorId);

        if (doctor != null && "Active".equals(doctor.getStatus())) {
            System.out.println("✅ Doctor " + doctor.getName()
                    + " assigned to patient " + patientId);
            return true;
        }
        return false;
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
                System.out.printf(
                        "%2d. %-8s | %-20s | %-15s | Exp: %2d yrs | Status: %-20s\n",
                        i + 1,
                        d.getStaffId(),
                        d.getName(),
                        d.getSpecialization(),
                        d.getExperience(),
                        d.getStatus()
                );
            }
        }
        System.out.println("=".repeat(80));
    }
}
