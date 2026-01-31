package application;
import java.time.*;
import java.util.*;
public class Person {
    private String id;
    private String name;
    private int age;
    private String gender;
    private String address;
    private String phone;
    private String medicalHistory;
	public String bloodGroup;
	public String doctor;
    
    
    public Person(String name, int age, String gender, String address, 
                  String phone, String email, String doctor) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.address = address;
        this.phone = phone;
        this.doctor = doctor;
        this.id = generateId();
    }
    
    private String generateId() {
        Random rand = new Random();
        int randomNum = rand.nextInt(900) + 100; // Generates number between 100-999
        return "P" + randomNum + LocalDate.now().getYear();
    }
    
    // Getters
    public String getId() { return id; }
    public String getName() { return name; }
    public int getAge() { return age; }
    public String getGender() { return gender; }
    public String getAddress() { return address; }
    public String getPhone() { return phone; }    
    public String getmedicalHistory() {return medicalHistory ;}
	public String getdoctor() {return doctor;}
	public String getbloodGroup(){return  bloodGroup;}


    // Display basic info
    public void displayBasicInfo() {
        System.out.println("=== PERSON INFORMATION ===");
        System.out.println("ID: " + this.id);
        System.out.println("Name: " + this.name);
        System.out.println("Age: " + this.age);
        System.out.println("Gender: " + this.gender);
        System.out.println("Address: " + this.address);
        System.out.println("Phone: " + this.phone);
        System.out.println("Doctor: " + this.doctor);
        System.out.println("Medical History: " + this.medicalHistory);
    }

}