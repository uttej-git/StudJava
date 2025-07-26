import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        StudentManager sm = new StudentManager();
        String filename = "students.txt";
        sm.loadFromFile(filename);

        while (true) {
            System.out.println("\n1. Add Student\n2. View Students\n3. Update Student\n4. Delete Student\n5. Search Student\n6. Export to PDF\n7. Exit");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter ID: ");
                    int id = sc.nextInt();
                    sc.nextLine();

                    if (sm.searchStudent(id) != null) {
                        System.out.println("Student with ID " + id + " already exists.");
                        break;
                    }

                    System.out.print("Enter name: ");
                    String name = sc.nextLine();

                    System.out.print("Enter age: ");
                    int age = sc.nextInt();
                    sc.nextLine();
                    if (age < 15 || age > 100) {
                        System.out.println("Invalid age! Must be between 15 and 100.");
                        break;
                    }

                    System.out.print("Enter gender (Male/Female/Other): ");
                    String gender = sc.nextLine().trim();

                    System.out.print("Enter course: ");
                    String course = sc.nextLine();

                    System.out.print("Enter CGPA (0 to 10): ");
                    double result = sc.nextDouble();
                    sc.nextLine();
                    if (result < 0 || result > 10) {
                        System.out.println("Invalid CGPA! Must be between 0 and 10.");
                        break;
                    }

                    System.out.print("Enter attendance percentage: ");
                    int attendance = sc.nextInt();
                    sc.nextLine();
                    if (attendance < 0 || attendance > 100) {
                        System.out.println("Invalid attendance! Must be between 0 and 100.");
                        break;
                    }

                    System.out.print("Enter fee amount: ");
                    double fee = sc.nextDouble();
                    sc.nextLine();
                    if (fee < 0) {
                        System.out.println("Invalid fee! Must be non-negative.");
                        break;
                    }

                    sm.addStudent(new Student(id, name, age, gender, course, result, attendance, fee));
                    sm.saveToFile(filename);
                    break;

                case 2:
                    sm.viewStudents();
                    break;

                case 3:
                    System.out.print("Enter ID to update: ");
                    id = sc.nextInt();
                    sc.nextLine();

                    if (sm.searchStudent(id) == null) {
                        System.out.println("Student with ID " + id + " not found.");
                        break;
                    }

                    System.out.print("New name: ");
                    name = sc.nextLine();

                    System.out.print("New age: ");
                    age = sc.nextInt();
                    sc.nextLine();
                    if (age < 15 || age > 100) {
                        System.out.println("Invalid age! Must be between 15 and 100.");
                        break;
                    }

                    System.out.print("New gender (Male/Female/Other): ");
                    gender = sc.nextLine().trim();

                    System.out.print("New course: ");
                    course = sc.nextLine();

                    System.out.print("New CGPA (0 to 10): ");
                    result = sc.nextDouble();
                    sc.nextLine();
                    if (result < 0 || result > 10) {
                        System.out.println("Invalid CGPA! Must be between 0 and 10.");
                        break;
                    }

                    System.out.print("New attendance percentage: ");
                    attendance = sc.nextInt();
                    sc.nextLine();
                    if (attendance < 0 || attendance > 100) {
                        System.out.println("Invalid attendance! Must be between 0 and 100.");
                        break;
                    }

                    System.out.print("New fee amount: ");
                    fee = sc.nextDouble();
                    sc.nextLine();
                    if (fee < 0) {
                        System.out.println("Invalid fee! Must be non-negative.");
                        break;
                    }

                    sm.updateStudent(id, name, age, gender, course, result, attendance, fee);
                    sm.saveToFile(filename);
                    break;

                case 4:
                    System.out.print("Enter ID to delete: ");
                    id = sc.nextInt();
                    sc.nextLine();
                    sm.deleteStudent(id);
                    sm.saveToFile(filename);
                    break;

                case 5:
                    System.out.print("Enter ID to search: ");
                    id = sc.nextInt();
                    sc.nextLine();
                    Student s = sm.searchStudent(id);
                    if (s != null) {
                        System.out.println(s);
                    } else {
                        System.out.println("Student not found.");
                    }
                    break;

                case 6:
                    System.out.print("Enter the name for the PDF file (e.g., `MyReport.pdf`): ");
                    String pdfFileName = sc.nextLine().trim();
                    if (!pdfFileName.toLowerCase().endsWith(".pdf")) {
                        System.out.println("Invalid file name. Must end with `.pdf`");
                        break;
                    }
                    sm.exportToPDF();
                    break;

                case 7:
                    System.out.println("Exiting...");
                    return;

                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
}
