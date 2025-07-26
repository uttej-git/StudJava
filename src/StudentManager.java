import java.io.*;
import java.util.*;

// iText 7 imports
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.properties.UnitValue;

public class StudentManager {
    private List<Student> students = new ArrayList<>();

    private boolean isValidStudent(Student s) {
        return s.getAge() >= 15 && s.getAge() <= 100
                && s.getAttendance() >= 0 && s.getAttendance() <= 100
                && s.getFee() >= 0
                && s.getCgpa() >= 0 && s.getCgpa() <= 10;
    }

    private boolean idExists(int id) {
        for (Student s : students) {
            if (s.getId() == id) return true;
        }
        return false;
    }

    public boolean addStudent(Student s) {
        if (!isValidStudent(s)) {
            System.out.println("❌ Invalid student data.");
            return false;
        }
        if (idExists(s.getId())) {
            System.out.println("❌ Student ID already exists.");
            return false;
        }
        students.add(s);
        System.out.println("✅ Student added.");
        return true;
    }

    public boolean updateStudent(int id, String name, int age, String gender, String course, double cgpa, int attendance, double fee) {
        for (Student s : students) {
            if (s.getId() == id) {
                Student temp = new Student(id, name, age, gender, course, cgpa, attendance, fee);
                if (!isValidStudent(temp)) {
                    System.out.println("❌ Invalid updated data.");
                    return false;
                }

                s.setName(name);
                s.setAge(age);
                s.setGender(gender);
                s.setCourse(course);
                s.setCgpa(cgpa);
                s.setAttendance(attendance);
                s.setFee(fee);
                System.out.println("✅ Student updated.");
                return true;
            }
        }
        System.out.println("❌ Student not found.");
        return false;
    }

    public void deleteStudent(int id) {
        Iterator<Student> it = students.iterator();
        while (it.hasNext()) {
            if (it.next().getId() == id) {
                it.remove();
                System.out.println("✅ Student deleted.");
                return;
            }
        }
        System.out.println("❌ Student not found.");
    }

    public Student searchStudent(int id) {
        for (Student s : students) {
            if (s.getId() == id) return s;
        }
        return null;
    }

    public void viewStudents() {
        if (students.isEmpty()) {
            System.out.println("No students found.");
            return;
        }

        students.sort(Comparator.comparingInt(Student::getId));

        System.out.printf("%-6s | %-15s | %-5s | %-6s | %-15s | %-12s | %-12s | %-10s\n",
                "ID", "Name", "Age", "Gender", "Course", "CGPA", "Attendance (%)", "Fee");

        System.out.println("-----------------------------------------------------------------------------------------------");

        for (Student s : students) {
            System.out.printf("%-6d | %-15s | %-5d | %-6s | %-15s | %-12.2f | %-12d | %-10.2f\n",
                    s.getId(),
                    s.getName(),
                    s.getAge(),
                    s.getGender(),
                    s.getCourse(),
                    s.getResult(),
                    s.getAttendance(),
                    s.getFee()
            );
        }
    }

    public void saveToFile(String filename) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filename))) {
            for (Student s : students) {
                pw.println(s.getId() + "," + s.getName() + "," + s.getAge() + "," + s.getGender() + "," +
                        s.getCourse() + "," + s.getCgpa() + "," + s.getAttendance() + "," + s.getFee());
            }
        } catch (IOException e) {
            System.out.println("❌ Error saving: " + e.getMessage());
        }
    }

    public void loadFromFile(String filename) {
        students.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 8) {
                    try {
                        int id = Integer.parseInt(parts[0]);
                        String name = parts[1];
                        int age = Integer.parseInt(parts[2]);
                        String gender = parts[3];
                        String course = parts[4];
                        double cgpa = Double.parseDouble(parts[5]);
                        int attendance = Integer.parseInt(parts[6]);
                        double fee = Double.parseDouble(parts[7]);

                        Student s = new Student(id, name, age, gender, course, cgpa, attendance, fee);
                        if (isValidStudent(s)) {
                            students.add(s);
                        } else {
                            System.out.println("⚠️ Skipped invalid student: ID=" + id);
                        }
                    } catch (Exception e) {
                        System.out.println("⚠️ Corrupt line skipped: " + line);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("📁 File not found.");
        } catch (IOException e) {
            System.out.println("❌ Error reading file: " + e.getMessage());
        }
    }

    public void exportToPDF() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter filename to save (with .pdf extension): ");
        String fileName = scanner.nextLine().trim();

        if (!fileName.toLowerCase().endsWith(".pdf")) {
            fileName += ".pdf";
        }

        File reportsDir = new File("reports");
        if (!reportsDir.exists()) {
            reportsDir.mkdirs();
        }

        String fullPath = "reports/" + fileName;

        try {
            PdfWriter writer = new PdfWriter(fullPath);
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document doc = new Document(pdfDoc);

            doc.add(new Paragraph("Student Report").setFontSize(18).setBold());

            Table table = new Table(UnitValue.createPercentArray(new float[]{1, 3, 1, 2, 2, 2, 2, 2}))
                    .useAllAvailableWidth();

            table.addHeaderCell("ID");
            table.addHeaderCell("Name");
            table.addHeaderCell("Age");
            table.addHeaderCell("Gender");
            table.addHeaderCell("Course");
            table.addHeaderCell("CGPA");
            table.addHeaderCell("Attendance (%)");
            table.addHeaderCell("Fee");

            students.sort(Comparator.comparingInt(Student::getId));

            for (Student s : students) {
                table.addCell(String.valueOf(s.getId()));
                table.addCell(s.getName());
                table.addCell(String.valueOf(s.getAge()));
                table.addCell(s.getGender());
                table.addCell(s.getCourse());
                table.addCell(String.format("%.2f", s.getCgpa()));
                table.addCell(String.valueOf(s.getAttendance()));
                table.addCell(String.format("%.2f", s.getFee()));
            }

            doc.add(table);
            doc.close();
            System.out.println("✅ PDF exported to " + fullPath);
        } catch (Exception e) {
            System.out.println("❌ PDF export failed: " + e.getMessage());
        }
    }
}
