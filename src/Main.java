import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String filename = "students.txt";
        StudentManager sm = new StudentManager();
        sm.loadFromFile(filename);

        while (true) {
            System.out.println("\n--- Student Management System ---");
            System.out.println("1. Add Student");
            System.out.println("2. View Students");
            System.out.println("3. Update Student");
            System.out.println("4. Delete Student");
            System.out.println("5. Search Student");
            System.out.println("6. Exit");
            System.out.println("7. View Students (Table Format)");
            System.out.println("8. Export to PDF");
            System.out.print("Enter choice: ");

            int choice;
            if (sc.hasNextInt()) {
                choice = sc.nextInt();
                sc.nextLine(); // consume newline
            } else {
                System.out.println("Invalid input. Please enter a number from 1 to 8.");
                sc.nextLine(); // discard invalid input
                continue;
            }

            switch (choice) {
                case 1:
                    System.out.print("Enter ID: ");
                    int id = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Enter name: ");
                    String name = sc.nextLine();

                    System.out.print("Enter age: ");
                    int age = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Enter course: ");
                    String course = sc.nextLine();

                    System.out.print("Enter result (e.g., GPA or percentage): ");
                    double result = sc.nextDouble();
                    sc.nextLine();

                    System.out.print("Enter attendance percentage: ");
                    int attendance = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Enter fee amount: ");
                    double fee = sc.nextDouble();
                    sc.nextLine();

                    sm.addStudent(new Student(id, name, age, course, result, attendance, fee));
                    sm.saveToFile(filename);
                    break;

                case 2:
                    sm.viewStudents();
                    break;

                case 3:
                    System.out.print("Enter ID to update: ");
                    id = sc.nextInt();
                    sc.nextLine();

                    System.out.print("New name: ");
                    name = sc.nextLine();

                    System.out.print("New age: ");
                    age = sc.nextInt();
                    sc.nextLine();

                    System.out.print("New course: ");
                    course = sc.nextLine();

                    System.out.print("New result (e.g., GPA or percentage): ");
                    result = sc.nextDouble();
                    sc.nextLine();

                    System.out.print("New attendance percentage: ");
                    attendance = sc.nextInt();
                    sc.nextLine();

                    System.out.print("New fee amount: ");
                    fee = sc.nextDouble();
                    sc.nextLine();

                    sm.updateStudent(id, name, age, course, result, attendance, fee);
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
                    Student student = sm.searchStudent(id);
                    if (student != null) {
                        System.out.println("Student found: " + student);
                    } else {
                        System.out.println("Student not found.");
                    }
                    break;

                case 6:
                    sm.saveToFile(filename);
                    System.out.println("Exiting... Goodbye!");
                    sc.close();
                    System.exit(0);
                    break;

                case 7:
                    sm.printStudentsTable();
                    break;

                case 8:
                    System.out.print("Enter PDF file name to export (e.g., students.pdf): ");
                    String pdfFile = sc.nextLine();
                    sm.exportToPDF(pdfFile);
                    break;

                default:
                    System.out.println("Invalid choice. Please enter a number from 1 to 8.");
            }
        }
    }
}
