# Event Management System

## 📌 Project Overview
The **Event Management System** is a Java-based application designed to manage events, attendees, users, and administrators.  
It provides features to register users, create and manage events, handle attendee records, and ensure smooth event operations.

This project demonstrates **Core Java, OOP concepts, Exception Handling, and Database Connectivity (MySQL)**.

---

## 🎯 Features
- **Admin Module**
  - Manage events (create, update, delete).
  - Manage users and attendees.
- **User Module**
  - Register and log in.
  - View available events.
- **Attendee Module**
  - Register as an attendee for events.
  - Store attendee details (name, phone, email).
- **Event Module**
  - Add new events with date & time validation.
  - Handle attendee participation.
- **Validation & Exception Handling**
  - Phone number, email, and date/time validations.

---

## 🛠️ Technologies Used
- **Java (Core Java, OOPs, Exception Handling)**
- **MySQL (Database for users, events, attendees, admin)**
- **JDBC (MySQL Connector)**
- **VS Code / Eclipse (IDE)**

---

## 📂 Project Structure
sem2/<br>
│── src/ # Source code<br>
│ ├── Admin.java<br>
│ ├── Attendee.java<br>
│ ├── Event.java<br>
│ ├── User.java<br>
│ ├── App.java<br>
│ ├── Main.java<br>
│── bin/ # Compiled class files<br>
│── lib/ # External libraries (mysql-connector)<br>
│── README.md # Project documentation<br>

---

## ⚙️ Installation & Setup
1. Install **Java JDK 17+** and set up environment variables.
2. Install **MySQL** and create a database (example: `eventdb`).
3. Import required sql file <b>projectsemtwo</b>
4. Add the MySQL connector (`mysql-connector-j-9.0.0.jar`) to your project.
5. Open the project in **VS Code** or **Eclipse**.
6. Compile and run the program:
   ```sh
   javac -d bin src/*.java
   java -cp "bin;lib/mysql-connector-j-9.0.0.jar" Main
📊 Database Tables (Sample)
<img width="1303" height="232" alt="image" src="https://github.com/user-attachments/assets/e2c8adf6-3095-440e-bbf5-81aebfc66e6d" />

👨‍💻 <b>Author</b>

Developed by <b>PAL MIYANI</b><br>
GitHub: palmiyani<br>
