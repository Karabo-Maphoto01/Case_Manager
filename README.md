# Case_Manager

  <img width="1086" height="620" alt="Screenshot 2025-11-05 214653" src="https://github.com/user-attachments/assets/d7c2c2b4-3c55-4572-8b16-27ca2d4bab41" />
# Project Structure

- policecasemanagementsystem/
    - src/   
       - policecasemanagementsystem/
          - PoliceCaseManagementSystem.java  
          - OfficerPanel.java               
          - CasePanel.java                  
          - DatabaseConnection.java         
          - OfficerDAO.java                 
          - CaseDAO.java                    
          - Officer.java                    
          - Case.java                       
- database/
        - policecasemanager.sql          
- README.md

# **Database Schema Documentation**


**Table: officer**

|Column	      |Type	        |Constraints	                 |Description
|--- |---|---|---|
|officerID	  |INT	        |PRIMARY KEY, AUTO_INCREMENT	 |Unique identifier for each officer|
|firstName	  |VARCHAR(50)	|NOT NULL	                 |Officer's first name|
|lastName	  |VARCHAR(50)	|NOT NULL	                 |Officer's last name|
|officerRank	  |VARCHAR(12)	|NOT NULL	                |Officer's rank/title|
|phone	      |VARCHAR(15)	|-	                         |Contact phone number



**Table: crimeCategory**

|Column         	 | Type	       | Constraints	  | Description
|--- |---|---|---|
|crimeTypeID	      |VARCHAR(12)	|PRIMARY KEY	   |Crime type code (THF, FRD, ASL)



**Table: caseStatus**

|Column	           |Type	      |Constraints	|Description
|--- |---|---|---|
|statusCode      	|INT	        |PRIMARY KEY	|Status code (1=Open, 2=Under Investigation, 3=Closed)|
|statusDescription	|VARCHAR(6)	 | -	           |Status description



**Table: caseRecord**
  
|Column	           |Type	          |Constraints	                |Description
|--- |---|---|---|
|caseID	           |INT	        |  PRIMARY KEY, AUTO_INCREMENT	|Unique case identifier|
|caseDetails	      | VARCHAR(255)   |NOT NULL	                    |Description of the case|
|attendingOfficer   |INT	          |NOT NULL, FOREIGN KEY	       |ID of assigned officer|
|category	       |VARCHAR(3)	  |FOREIGN KEY	                |Crime category code|
|location	       |VARCHAR(50)	  |-                             |Crime location/address|
|reportedOn	       |DATE	         | -	                            |Date case was reported|
|statuses	       |INT	          |FOREIGN KEY	                |Current case status|

 # Setup Instruction
 
 **Prerequisites**
 
 Java Development Kit (JDK) 8 or higher

MySQL Server 8.0 or higher

MySQL Connector/J 8.0

**Step 1: Database Setup**
```SQL
-- Create database
CREATE DATABASE policecasemanager;

-- Use the database
USE policecasemanager;

-- Create tables 
-- Insert initial data
INSERT INTO crimeCategory (crimeTypeID) VALUES 
('THF'), ('FRD'), ('ASL');

INSERT INTO caseStatus (statusCode, statusDescription) VALUES 
(1, 'Open'), (2, 'Under Investigation'), (3, 'Closed');
```

**Step 2: Configure Database Connection**
```SQL
private static final String URL = "jdbc:mysql://localhost:3306/policecasemanager";
private static final String USERNAME = "your_username";
private static final String PASSWORD = "your_password";
```

**Step 3: Add MySQL Connector**
- Download MySQL Connector/J from https://dev.mysql.com/downloads/connector/j/
- Add the JAR file to your project's classpath

 **Step 4: Run the Application**
 ```Java
javac PoliceCaseManagementSystem.java
java PoliceCaseManagementSystem
```
# Usage Instructions
**Officer Management**

- Add New Officer: Fill in first name, last name, rank, and phone number, then click "Add Officer"
- Update Officer: Select an officer from the table, modify details, click "Update"
- View Officers: All officers are displayed in the table automatically

**Case Management**

1. Add New Case:
- Enter case details and location
- Select attending officer from dropdown
- Choose crime category (THF=Theft, FRD=Fraud, ASL=Assault)
- Set status (Open/Closed)
- Click "Add Case"
2. Update Case: Select case from table, modify details, click "Update Case"

3. Delete Case: Select case from table, click "Delete Case"

4. View Cases: All cases displayed with officer names and status descriptions

# Code Documentation
Main Application Class
```Java
/**
 * Police Case Management System - Main Application Frame
 * Provides tabbed interface for officer and case management
 * Implements MVC architecture with separate data, business logic, and presentation layers
 */
public class PoliceCaseManagementSystem extends JFrame {
    // Class implementation...
}
```
Database Connection Class
```Java
/**
 * Handles database connection management using JDBC
 * Implements singleton pattern for connection reuse
 * Provides centralized configuration for database access
 */
public class DatabaseConnection {
    // Database connection implementation...
}
```
**Data Access Objects (DAOs)**

OfficerDAO
```Java
/**
 * Data Access Object for Officer entities
 * Provides CRUD operations for officer management
 * Handles all database interactions for officer records
 */
public class OfficerDAO {
    /**
     * Inserts a new officer record into the database
     * @param officer Officer object containing officer details
     * @return boolean indicating success of the operation
     */
    public boolean insertOfficer(Officer officer) {
        // Implementation...
    }
    
    /**
     * Retrieves all officers from the database
     * @return List of Officer objects representing all officers
     */
    public List<Officer> getAllOfficers() {
        // Implementation...
    }
}
```
CaseDAO
```Java
/**
 * Data Access Object for Case entities
 * Manages all case-related database operations
 * Handles foreign key constraints and data integrity
 */
public class CaseDAO {
    // Case data access methods...
}
```
**Model Classes**

Officer Model
```Java
/**
 * Represents an Officer entity in the system
 * Follows JavaBean pattern with private fields and public getters/setters
 * Maps directly to the officer database table
 */
public class Officer {
    private int officerId;
    private String firstName;
    private String lastName;
    private String rank;
    private String phone;
    
    // Constructors, getters, and setters...
}
```
Case Model
```java
/**
 * Represents a Case entity in the system
 * Contains case details and relationships to other entities
 * Includes date handling for case reporting
 */
public class Case {
    private int caseId;
    private String caseDetails;
    private int attendingOfficer;
    private String category;
    private String location;
    private Date reportedOn;
    private int status;
    
    // Constructors, getters, and setters...
}
```
**GUI Components Documentation**

OfficerPanel Class
```Java
/**
 * GUI panel for officer management operations
 * Provides form for officer data entry and table for display
 * Implements MVC pattern with separation of concerns
 */
public class OfficerPanel extends JPanel {
    // UI components and event handlers...
}
```
CasePanel Class
```Java
/**
 * GUI panel for case management operations
 * Includes form controls for case data and relationship management
 * Handles complex data validation and foreign key constraints
 */
public class CasePanel extends JPanel {
    // UI components and event handlers...
}
```
# Configuration
**Database Configuration**
- Database Name: policecasemanager
- Port: 3306 (default MySQL port)
- Driver: com.mysql.cj.jdbc.Driver

**Application Settings**
- Window Size: 900x600 pixels
- Background Color: White
- Font: System default

# Troubleshooting

**Common Issues**

1. Database Connection Failed
   - Verify MySQL service is running
   - Check database credentials in DatabaseConnection.java
   - Ensure MySQL Connector/J is in classpath
2. Foreign Key Constraint Errors
   - Ensure crime categories and status codes are inserted
   - Verify officer exists before assigning to case
3. GUI Components Not Visible
   - Check layout manager configurations
   - Verify component initialization order

**Error Messages and Solutions**

|Error Message |Solution
|---|---|
|"No suitable driver found" |Add MySQL Connector/J to classpath|
|"Access denied for user"	|Verify database username and password|
|"Unknown database"	|Create policecasemanager database|
|"Foreign key constraint fails"	|Insert required reference data

# Java Best Practices Implemented
**Naming Conventions**
- Classes: PascalCase (PoliceCaseManagementSystem)
- Methods: camelCase (getAllOfficers())
- Variables: camelCase (txtFirstName)
- Constants: UPPER_CASE (DATABASE_URL)

**Error Handling**
- Comprehensive exception handling
- User-friendly error messages
- Database transaction management
- Input validatio

**Security Considerations**
- Prepared statements used to prevent SQL injection
- Input validation on all user inputs
- Proper resource management with try-with-resources







