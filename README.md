# 🎓 Student Management System (Java)

A simple yet powerful **Java-based Student Management System** with:
- File-based storage
- Interactive console UI
- PDF export with iText 7 (color-coded & categorized)
- Input validations (age, CGPA, attendance, fee, unique ID)

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

    ➕ Add new students with full validation

    👀 View all students (standard and tabular format)

    🔄 Update existing student details

    ❌ Delete students by ID

    🔍 Search student by ID

    📄 Export to PDF

        Color-coded CGPA & attendance

        Categorized sections: Detained, Non-detained, Toppers

        User-defined file name prompt

    ✅ CGPA-only result system (0.0 – 10.0)

    🧍‍♂️ Gender support added

    ✔️ Robust input validation (age, attendance %, fee non-negative, unique IDs)


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

