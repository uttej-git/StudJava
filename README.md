# 🎓 Student Management System (Java)

A simple yet powerful **Java-based Student Management System** with:
- File-based storage
- Interactive console UI
- PDF export with iText 7 (color-coded, categorized)

---

## 📂 Project Structure

```plaintext
├── Main.java              # Entry point with menu-driven CLI
├── Student.java           # Model class for student data
├── StudentManager.java    # Core logic: CRUD, file I/O, PDF export
├── students.txt           # Persistent local storage of student data
└── lib/                   # iText .jar files (if needed)
```

🚀 Features
➕ Add new students

👀 View all students (console or table format)

🔄 Update existing student details

❌ Delete students by ID

🔍 Search by student ID

📄 Export all records to a structured PDF file

🎨 Color-coded GPA & attendance in PDF

📂 Categorized export: Detained, Non-detained, Toppers

🧠 How It Works
When the app starts, it loads students from students.txt.

User is presented with a menu to perform CRUD operations.

All changes are saved back to file (students.txt).

Optionally, data can be exported to PDF using iTex


📦 Dependencies
Java 8+

iText 7 PDF Library
(Include kernel, layout, and other core jars in lib/ if compiling manually)


```
📸 Sample 
--- Student Management System ---
1. Add Student
2. View Students
3. Update Student
4. Delete Student
5. Search Student
6. Exit
7. View Students (Table Format)
8. Export to PDF
```


🛠 Future Enhancements (Ideas)
🔐 Admin login/authentication

🎯 Search by name/course

📊 Sort/filter by GPA, attendance, fee

💻 GUI version (JavaFX or Swing)

🧪 Add JUnit tests



✅ Author
Made by Uttej
[https://github.com/uttej-git/]