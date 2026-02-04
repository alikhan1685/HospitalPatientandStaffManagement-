package application;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

public class AppointmentDatabase {
    private static AppointmentDatabase instance;
    private List<Appointment> appointments;
    
    private AppointmentDatabase() {
        appointments = new ArrayList<>();
    
    }
    
    public static AppointmentDatabase getInstance() {
        if (instance == null) {
            instance = new AppointmentDatabase();
        }
        return instance;
    }
    
    public void addAppointment(Appointment appointment) {
        appointments.add(appointment);
        System.out.println("✅ Appointment added: " + appointment.getAppointmentId());
        System.out.println("📊 Total appointments: " + appointments.size());
    }
    
    public List<Appointment> getAllAppointments() {
        return new ArrayList<>(appointments);
    }
    
    public boolean deleteAppointment(String appointmentId) {
        Iterator<Appointment> iterator = appointments.iterator();
        while (iterator.hasNext()) {
            Appointment appointment = iterator.next();
            if (appointment.getAppointmentId().equals(appointmentId)) {
                iterator.remove();
                System.out.println("❌ Appointment deleted: " + appointmentId);
                System.out.println("📊 Total appointments: " + appointments.size());
                return true;
            }
        }
        System.out.println("⚠️ Appointment not found: " + appointmentId);
        return false;
    }
    
    public int getAppointmentCount() {
        return appointments.size();
    }
    
    public int getScheduledAppointmentCount() {
        int count = 0;
        for (Appointment appointment : appointments) {
            if ("Scheduled".equals(appointment.getStatus())) {
                count++;
            }
        }
        return count;
    }
    
    public void printAllAppointments() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("APPOINTMENT DATABASE (" + appointments.size() + " appointments)");
        System.out.println("=".repeat(80));
        
        if (appointments.isEmpty()) {
            System.out.println("No appointments in database.");
        } else {
            for (int i = 0; i < appointments.size(); i++) {
                Appointment a = appointments.get(i);
                System.out.printf("%2d. %-10s | %-20s | %-25s | %-15s | %-10s\n",
                    i + 1, 
                    a.getAppointmentId(),
                    a.getPatientName(), 
                    a.getDoctor(), 
                    a.getDate(),
                    a.getStatus());
            }
        }
        System.out.println("=".repeat(80));
    }
}