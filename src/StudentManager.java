import java.io.*;
import java.util.*;
import java.text.SimpleDateFormat;

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

        // Add timestamp
        String now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        s.setCreatedAt(now);

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
            System.out.println("⚠️ No students available.");
            return;
        }

        System.out.println("📋 All Student Details:");
        System.out.printf("%-5s %-15s %-5s %-8s %-10s %-6s %-12s %-8s %-20s%n",
                "ID", "Name", "Age", "Gender", "Course", "CGPA", "Attendance", "Fee", "Created At");

        for (Student s : students) {
            System.out.printf("%-5d %-15s %-5d %-8s %-10s %-6.2f %-12.2f %-8.2f %-20s%n",
                    s.getId(), s.getName(), s.getAge(), s.getGender(),
                    s.getCourse(), s.getCgpa(), (double) s.getAttendance(), (double) s.getFee(),
                    s.getCreatedAt() != null ? s.getCreatedAt() : "N/A");
        }

        int total = students.size();
        int detainedCount = 0, topperCount = 0, lowAttendanceCount = 0;

        for (Student s : students) {
            if (s.getAttendance() < 50) detainedCount++;
            if (s.getCgpa() >= 7.5) topperCount++;
            if (s.getAttendance() < 75) lowAttendanceCount++;
        }

        System.out.println("\n📊 Summary:");
        System.out.println("👥 Total students: " + total);
        System.out.println("🚫 Detained (Attendance < 50%): " + detainedCount);
        System.out.println("📉 Attendance < 75%: " + lowAttendanceCount);
        System.out.println("🏅 Toppers (CGPA ≥ 7.5): " + topperCount);
    }

    public void saveToFile(String filename) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filename))) {
            for (Student s : students) {
                pw.println(s.getId() + "," + s.getName() + "," + s.getAge() + "," + s.getGender() + "," +
                        s.getCourse() + "," + s.getCgpa() + "," + s.getAttendance() + "," + s.getFee() + "," +
                        (s.getCreatedAt() != null ? s.getCreatedAt() : ""));
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
                if (parts.length >= 8) {
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
                        if (parts.length >= 9) {
                            s.setCreatedAt(parts[8]);
                        }

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

    public void exportToPDF(String fileName) {
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

            Table table = new Table(UnitValue.createPercentArray(new float[]{1, 3, 1, 2, 2, 1.5f, 2, 2, 3}))
                    .useAllAvailableWidth();

            table.addHeaderCell("ID");
            table.addHeaderCell("Name");
            table.addHeaderCell("Age");
            table.addHeaderCell("Gender");
            table.addHeaderCell("Course");
            table.addHeaderCell("CGPA");
            table.addHeaderCell("Attendance");
            table.addHeaderCell("Fee");
            table.addHeaderCell("Created At");

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
                table.addCell(s.getCreatedAt() != null ? s.getCreatedAt() : "-");
            }

            doc.add(table);

            int total = students.size();
            int detained = 0, toppers = 0, lowAttendance = 0;

            for (Student s : students) {
                if (s.getCgpa() < 5.0) detained++;
                if (s.getCgpa() >= 7.5) toppers++;
                if (s.getAttendance() < 75) lowAttendance++;
            }

            doc.add(new Paragraph("\nSummary:").setFontSize(14).setBold());
            doc.add(new Paragraph("📊 Total Students: " + total));
            doc.add(new Paragraph("❌ Detained (CGPA < 5): " + detained));
            doc.add(new Paragraph("🏅 Toppers (CGPA ≥ 7.5): " + toppers));
            doc.add(new Paragraph("🚫 Low Attendance (< 75%): " + lowAttendance));

            doc.close();
            System.out.println("✅ PDF exported to " + fullPath);
        } catch (Exception e) {
            System.out.println("❌ PDF export failed: " + e.getMessage());
        }
    }
}
