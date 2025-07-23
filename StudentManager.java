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
import com.itextpdf.layout.properties.TextAlignment;

import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.DeviceRgb;


public class StudentManager {
    private Map<Integer, Student> students = new HashMap<>();

    public void addStudent(Student s) {
        students.put(s.getId(), s);
        System.out.println("Student added!");
    }

    public void viewStudents() {
        if (students.isEmpty()) {
            System.out.println("No students found.");
            return;
        }
        students.values().forEach(System.out::println);
    }

    public void updateStudent(int id, String name, int age, String course, double result, int attendance, double fee) {
        Student s = students.get(id);
        if (s != null) {
            s.setName(name);
            s.setAge(age);
            s.setCourse(course);
            s.setResult(result);
            s.setAttendance(attendance);
            s.setFee(fee);
            System.out.println("Student updated.");
        } else {
            System.out.println("Student not found.");
        }
    }

    public void deleteStudent(int id) {
        if (students.remove(id) != null)
            System.out.println("Student deleted.");
        else
            System.out.println("Student not found.");
    }

    public Student searchStudent(int id) {
        Student s = students.get(id);
        if (s != null) {
            System.out.println(s);
        } else {
            System.out.println("Student not found.");
        }
        return s;
    }

    public void loadFromFile(String filename) {
        try (Scanner fileScanner = new Scanner(new File(filename))) {
            while (fileScanner.hasNextLine()) {
                String[] data = fileScanner.nextLine().split(",");
                if (data.length < 7) continue;
                int id = Integer.parseInt(data[0]);
                String name = data[1];
                int age = Integer.parseInt(data[2]);
                String course = data[3];
                double result = Double.parseDouble(data[4]);
                int attendance = Integer.parseInt(data[5]);
                double fee = Double.parseDouble(data[6]);

                students.put(id, new Student(id, name, age, course, result, attendance, fee));
            }
            System.out.println("Students loaded from file.");
        } catch (FileNotFoundException e) {
            System.out.println("No existing student file found. Starting fresh.");
        }
    }

    public void saveToFile(String filename) {
        try (PrintWriter writer = new PrintWriter(new File(filename))) {
            for (Student s : students.values()) {
                writer.println(s.getId() + "," + s.getName() + "," + s.getAge() + "," + s.getCourse() + "," +
                        s.getResult() + "," + s.getAttendance() + "," + s.getFee());
            }
            System.out.println("Students saved to file.");
        } catch (IOException e) {
            System.out.println("Error saving file: " + e.getMessage());
        }
    }

    public void printStudentsTable() {
        if (students.isEmpty()) {
            System.out.println("No students found.");
            return;
        }

        System.out.printf("%-5s | %-15s | %-3s | %-10s | %-8s | %-10s | %-10s%n",
                "ID", "Name", "Age", "Course", "Result", "Attendance", "Fee");
        System.out.println("-------------------------------------------------------------------------------");
        for (Student s : students.values()) {
            System.out.printf("%-5d | %-15s | %-3d | %-10s | %-8.2f | %-10d | $%-9.2f%n",
                    s.getId(), s.getName(), s.getAge(), s.getCourse(), s.getResult(), s.getAttendance(), s.getFee());
        }
    }

    public void exportToPDF(String pdfFilename) {
    if (students.isEmpty()) {
        System.out.println("No students to export.");
        return;
    }

    try {
        PdfWriter writer = new PdfWriter(pdfFilename);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document document = new Document(pdfDoc);

        document.add(new Paragraph("Student Records")
                .setTextAlignment(TextAlignment.CENTER)
                .setFontSize(16)
                .setBold()
                .setMarginBottom(15f));

        float[] columnWidths = {30f, 100f, 30f, 80f, 50f, 60f, 60f};
        Table table = new Table(UnitValue.createPercentArray(columnWidths));
        table.setWidth(UnitValue.createPercentValue(100));

        String[] headers = {"ID", "Name", "Age", "Course", "Result", "Attendance", "Fee"};
        for (String header : headers) {
            table.addHeaderCell(new Cell().add(new Paragraph(header)).setBold());
        }

        // Separate lists for categories
        List<Student> detainedStudents = new ArrayList<>();
        List<Student> nonDetainedStudents = new ArrayList<>();
        List<Student> toppers = new ArrayList<>();

        for (Student s : students.values()) {
            table.addCell(String.valueOf(s.getId()));
            table.addCell(s.getName());
            table.addCell(String.valueOf(s.getAge()));
            table.addCell(s.getCourse());

            // Result cell with conditional color
            Cell resultCell = new Cell().add(new Paragraph(String.format("%.2f", s.getResult())));
            double resultValue = s.getResult();
            if (resultValue < 55) {
                resultCell.setFontColor(new DeviceRgb(255, 0, 0));  // Red
            } else if (resultValue < 69) {
                resultCell.setFontColor(new DeviceRgb(255, 140, 0)); // Dark Orange
            } else if (resultValue < 75) {
                resultCell.setFontColor(new DeviceRgb(255, 215, 0)); // Gold (Yellow)
            }
            table.addCell(resultCell);

            // Attendance cell with conditional color
            Cell attendanceCell = new Cell().add(new Paragraph(s.getAttendance() + "%"));
            int attendanceValue = s.getAttendance();
            if (attendanceValue < 65) {
                attendanceCell.setFontColor(new DeviceRgb(255, 0, 0));  // Red
            } else if (attendanceValue < 70) {
                attendanceCell.setFontColor(new DeviceRgb(255, 140, 0)); // Dark Orange
            } else if (attendanceValue < 75) {
                attendanceCell.setFontColor(new DeviceRgb(255, 215, 0)); // Gold (Yellow)
            }
            table.addCell(attendanceCell);

            table.addCell("$" + String.format("%.2f", s.getFee()));

            // Add to detained or non-detained list
            if (attendanceValue < 50) {
                detainedStudents.add(s);
            } else {
                nonDetainedStudents.add(s);
            }

            // Add to toppers if result >= 88
            if (resultValue >= 88) {
                toppers.add(s);
            }
        }

        document.add(table);

        // Helper method to add each category table
        addCategoryTable(document, "DETAINED STUDENTS", detainedStudents, headers, columnWidths);
        addCategoryTable(document, "NON DETAINED STUDENTS", nonDetainedStudents, headers, columnWidths);
        addCategoryTable(document, "TOPPERS", toppers, headers, columnWidths);

        document.close();
        System.out.println("PDF exported successfully to " + pdfFilename);

    } catch (Exception e) {
        System.out.println("Error exporting to PDF: " + e.getMessage());
    }
}

// Helper method to reduce code duplication for category tables
private void addCategoryTable(Document document, String title, List<Student> studentList, String[] headers, float[] columnWidths) {
    if (studentList.isEmpty()) return;

    document.add(new Paragraph("\n" + title)
            .setTextAlignment(TextAlignment.CENTER)
            .setFontSize(14)
            .setBold()
            .setMarginTop(20f)
            .setMarginBottom(10f));

    Table table = new Table(UnitValue.createPercentArray(columnWidths));
    table.setWidth(UnitValue.createPercentValue(100));

    for (String header : headers) {
        table.addHeaderCell(new Cell().add(new Paragraph(header)).setBold());
    }

    for (Student s : studentList) {
        table.addCell(String.valueOf(s.getId()));
        table.addCell(s.getName());
        table.addCell(String.valueOf(s.getAge()));
        table.addCell(s.getCourse());

        Cell resultCell = new Cell().add(new Paragraph(String.format("%.2f", s.getResult())));
        double resultValue = s.getResult();
        if (resultValue < 55) {
            resultCell.setFontColor(new DeviceRgb(255, 0, 0));
        } else if (resultValue < 69) {
            resultCell.setFontColor(new DeviceRgb(255, 140, 0));
        } else if (resultValue < 75) {
            resultCell.setFontColor(new DeviceRgb(255, 215, 0));
        }
        table.addCell(resultCell);

        Cell attendanceCell = new Cell().add(new Paragraph(s.getAttendance() + "%"));
        int attendanceValue = s.getAttendance();
        if (attendanceValue < 65) {
            attendanceCell.setFontColor(new DeviceRgb(255, 0, 0));
        } else if (attendanceValue < 70) {
            attendanceCell.setFontColor(new DeviceRgb(255, 140, 0));
        } else if (attendanceValue < 75) {
            attendanceCell.setFontColor(new DeviceRgb(255, 215, 0));
        }
        table.addCell(attendanceCell);

        table.addCell("$" + String.format("%.2f", s.getFee()));
    }

    document.add(table);
}
}