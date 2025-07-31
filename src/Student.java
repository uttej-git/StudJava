import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Student {
    private int id;
    private String name;
    private int age;
    private String gender;
    private String course;
    private double cgpa;
    private int attendance;
    private double fee;
    private String createdAt;  // ✅ Only one declaration

    public Student(int id, String name, int age, String gender, String course,
                   double cgpa, int attendance, double fee) {
        this(id, name, age, gender, course, cgpa, attendance, fee,
             LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }

    public Student(int id, String name, int age, String gender, String course,
                   double cgpa, int attendance, double fee, String createdAt) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.course = course;
        this.cgpa = cgpa;
        this.attendance = attendance;
        this.fee = fee;
        this.createdAt = createdAt;
    }

    // Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public int getAge() { return age; }
    public String getGender() { return gender; }
    public String getCourse() { return course; }
    public double getCgpa() { return cgpa; }
    public int getAttendance() { return attendance; }
    public double getFee() { return fee; }
    public String getCreatedAt() { return createdAt; }

    // Setters
    public void setName(String name) { this.name = name; }
    public void setAge(int age) { this.age = age; }
    public void setGender(String gender) { this.gender = gender; }
    public void setCourse(String course) { this.course = course; }
    public void setCgpa(double cgpa) { this.cgpa = cgpa; }
    public void setAttendance(int attendance) { this.attendance = attendance; }
    public void setFee(double fee) { this.fee = fee; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }

    @Override
    public String toString() {
        return id + " | " + name + " | " + age + " | " + gender + " | " + course +
               " | CGPA: " + cgpa + " | Attendance: " + attendance + " | Fee: " +
               String.format("%.2f", fee) + " | Created: " + createdAt;
    }
}
