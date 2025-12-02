#  Library Management System (Java + H2 Database)

A simple, enhanced console-based library system built using **Java**, **H2 embedded database**, and **JDBC**.  
This project demonstrates database CRUD operations, validation, error handling, and clean modular code.

---

##  Features
✔ Add books  
✔ List all books  
✔ Update books  
✔ Delete books  
✔ Search (by title or author)  
✔ Validation for inputs  
✔ Duplicate entry prevention  
✔ Uses embedded H2 database (no installation needed)

---

##  Project Structure
LibraryManagement/
│
├── Main.java
├── LibraryManagement.java
├── BookValidator.java
└── README.md

---

##  Requirements
- Java JDK 17 or later  
- No external DB required (H2 creates file automatically)

---

##  How to Run

1. Clone or download the project.
2. Open in any Java IDE (VS Code, IntelliJ, Eclipse).
3. Run **Main.java**.
4. A database file named `librarydb.mv.db` will be created automatically.

---

##  Notes
- The program handles incorrect input (invalid numbers, missing fields).
- Search works with partial text matching.
- Database persists even after closing the app.

---

##  Author
shubham pandey  
Student Project – Library Management System
