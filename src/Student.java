public class Student {
    private int id;
    private String name;
    private int age;
    private String gender;
    private String course;
    private double cgpa;         // CGPA (0.0 - 10.0)
    private int attendance;      // Attendance percentage (0 - 100)
    private double fee;          // Course fee

    public Student(int id, String name, int age, String gender, String course, double cgpa, int attendance, double fee) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.course = course;
        this.cgpa = cgpa;
        this.attendance = attendance;
        this.fee = fee;
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

    // Required by StudentManager for PDF export
    public double getResult() {
        return cgpa;
    }
    public void setName(String name) { this.name = name; }
    public void setAge(int age) { this.age = age; }
    public void setGender(String gender) { this.gender = gender; }
    public void setCourse(String course) { this.course = course; }
    public void setCgpa(double cgpa) { this.cgpa = cgpa; }
    public void setAttendance(int attendance) { this.attendance = attendance; }
    public void setFee(double fee) { this.fee = fee; }
    @Override
    public String toString() {
        return id + " | " + name + " | " + age + " | " + gender + " | " + course +
               " | CGPA: " + cgpa + " | Attendance: " + attendance + " | Fee: " + String.format("%.2f", fee);
    }
}
