package application;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

public class StaffID {
    protected String id;
    protected String name;
    protected int age;
    protected String gender;
    protected String address;
    protected String phone;
    protected String status;
    protected List<String> additionalNotes;
    protected LocalDate dateJoined;
    
    public StaffID() {
        this.additionalNotes = new ArrayList<>();
        this.dateJoined = LocalDate.now();
        this.status = "Active";
    }
    
    // Common methods for all staff
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }
    
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
    
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public List<String> getAdditionalNotes() { return new ArrayList<>(additionalNotes); }
    public void addAdditionalNote(String note) {
        if (note != null && !note.trim().isEmpty()) {
            this.additionalNotes.add(note);
        }
    }
    
    public LocalDate getDateJoined() { return dateJoined; }
    
    // Common display method
    public void displayBasicInfo() {
        System.out.println("ID: " + this.id);
        System.out.println("Name: " + this.name);
        System.out.println("Age: " + this.age);
        System.out.println("Gender: " + this.gender);
        System.out.println("Status: " + this.status);
        System.out.println("Phone: " + this.phone);
        System.out.println("Address: " + this.address);
        System.out.println("Date Joined: " + this.dateJoined);
    }
}