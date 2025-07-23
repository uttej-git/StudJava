public class Student {
    private int id;
    private String name;
    private int age;
    private String course;
    private double result;      // e.g. GPA or percentage
    private int attendance;     // e.g. attendance percentage or days
    private double fee;         // fee for course/year

    public Student(int id, String name, int age, String course, double result, int attendance, double fee) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.course = course;
        this.result = result;
        this.attendance = attendance;
        this.fee = fee;
    }

    // Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public int getAge() { return age; }
    public String getCourse() { return course; }
    public double getResult() { return result; }
    public int getAttendance() { return attendance; }
    public double getFee() { return fee; }

    // Setters
    public void setName(String name) { this.name = name; }
    public void setAge(int age) { this.age = age; }
    public void setCourse(String course) { this.course = course; }
    public void setResult(double result) { this.result = result; }
    public void setAttendance(int attendance) { this.attendance = attendance; }
    public void setFee(double fee) { this.fee = fee; }

    @Override
    public String toString() {
        return id + " | " + name + " | " + age + " | " + course + " | Result: " + result + " | Attendance: " + attendance + "% | Fee: $" + fee;
    }
}
