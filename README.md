# 🎓 Student Management System (Java)

A robust and feature-rich Java-based Student Management System with:

📁 Persistent file-based storage

📊 Tabular console UI with student statistics

📄 Categorized PDF export using iText 7

✅ Full input validations and gender support

---

## 📂 Project Structure

```plaintext
├── src/
│   ├── Main.java              # Entry point with menu-driven CLI
│   ├── Student.java           # Model class for student data (with gender)
│   └── StudentManager.java    # Core logic: CRUD, file I/O, PDF export
├── students.txt               # Persistent local storage of student data
├── reports/                   # Auto-saves exported PDF reports here
└── lib/                       # iText .jar files (kernel, layout, etc.)
└── README.md                                  
```

🚀 Features

    🧾 Student Operations
    ➕ Add Student (with full validation)
    👁️ View Students (tabular format with color codes)
    🔄 Update student details
    ❌ Delete by ID
    🔍 Search by ID
    📄 PDF Export (via iText 7)

Categorized tables:

    📌 All students (sorted by ID)
    🚫 Detained: attendance < 50%
    🧠 Toppers: CGPA ≥ 7.5
    🎨 Color-coded cells (for CGPA and attendance)
    📝 Custom filename prompt (e.g., my_report.pdf)
    📁 Auto-saved in reports/ folder

📊 Student Count Summary 
After viewing all students, you’ll see:

    👥 Total students
    🚫 Detained students (Attendance < 50%)
    🧠 Toppers (CGPA ≥ 7.5)
    ⚠️ Students with Attendance < 75%
    🔐 Input Validations
    🎂 Age must be between 15 and 100
    🎓 CGPA must be between 0.0 and 10.0
    📉 Attendance must be between 0% and 100%
    💰 Fee must be non-negative
    🆔 Student ID must be unique
    🚻 Gender must be "Male", "Female", or "Other"

🧠 How It Works

    On startup, it loads all students from students.txt
    Offers a menu-based CLI for all operations
    Saves all changes automatically to file
    User can choose to export data as PDF into /reports/ directory
    Prompts user for desired filename for the PDF
    
📦 Dependencies

    Java 8 or later
    iText 7 PDF Library

    Include the following JARs in lib/:
    
    kernel.jar
    layout.jar
    io.jar
    commons.jar
    slf4j-api.jar

Compile command (if using terminal):
    javac -cp "lib/*" -d . src/*.java

Run:
    java -cp "lib/*;." Main

✅ Author
Made by Uttej
GitHub: https://github.com/uttej-git

