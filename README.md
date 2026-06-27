# Citizen's Voice – A Public Complaint Reporter

A Java Swing desktop application enabling citizens to register 
and track public grievances with separate citizen and admin portals.

## Tech Stack
- Java, Swing
- JDBC
- MySQL

## Features
- Separate login flows for citizens and admin
- Complaint submission with category, department, and description
- Admin dashboard with complaint verification and status updates
- User management and department handling
- Feedback form for citizens

## Setup Instructions

### 1. Clone the repository
git clone https://github.com/Nikshay8/Citizens-Voice-A-Public-Complaint-Reporter.git

### 2. Open in IntelliJ IDEA
File → Open → Select project folder

### 3. Add MySQL Connector
MySQL JDBC Connector JAR is included in the project root.
Add it via: File → Project Structure → Libraries → + → Java → select mysql-connector-j-9.4.0.jar

### 4. Configure database
- Create a file named config.properties in the project root
- Use config.example.properties as reference:
  db.url=jdbc:mysql://localhost:3306/citizens_voice_db
  db.username=your_username
  db.password=your_password

### 5. Setup MySQL database
Create a database named citizens_voice_db and run the required tables.

### 6. Run the project
Run CitizensVoiceLogin.java as the main entry point.

## Note
Some features are currently under development.
