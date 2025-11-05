# Case_Manager

# Project Structure

--policecasemanagementsystem/

├── src/   
    
    ├── policecasemanagementsystem/
    │   ├── PoliceCaseManagementSystem.java  // Main application frame 
    │   ├── OfficerPanel.java               // Officer management GUI
    │   ├── CasePanel.java                  // Case management GUI
    │   ├── DatabaseConnection.java         // Database connection handler
    │   ├── OfficerDAO.java                 // Officer data access object
    │   ├── CaseDAO.java                    // Case data access object
    │   ├── Officer.java                    // Officer model class
    │   └── Case.java                       // Case model class
    └── database/
        └── policecasemanager.sql          // Database schema and setup
├──README.md

# **Database Schema Documentation**


**Table: officer**

Column	      Type	        Constraints	                 Description

officerID	  INT	        PRIMARY KEY, AUTO_INCREMENT	 Unique identifier for each officer

firstName	  VARCHAR(50)	NOT NULL	                 Officer's first name

lastName	  VARCHAR(50)	NOT NULL	                 Officer's last name

officerRank	  VARCHAR(12)	NOT NULL	                 Officer's rank/title

phone	      VARCHAR(15)	-	                         Contact phone number



**Table: crimeCategory**

Column         	  Type	        Constraints	   Description

crimeTypeID	      VARCHAR(12)	PRIMARY KEY	   Crime type code (THF, FRD, ASL)



**Table: caseStatus**

Column	            Type	      Constraints	Description

statusCode      	INT	          PRIMARY KEY	Status code (1=Open, 2=Under Investigation, 3=Closed)

statusDescription	VARCHAR(6)	  -	            Status description



**Table: caseRecord**
  
Column	           Type	          Constraints	                Description

caseID	           INT	          PRIMARY KEY, AUTO_INCREMENT	Unique case identifier

caseDetails	       VARCHAR(255)   NOT NULL	                    Description of the case

attendingOfficer   INT	          NOT NULL, FOREIGN KEY	        ID of assigned officer

category	       VARCHAR(3)	  FOREIGN KEY	                Crime category code

location	       VARCHAR(50)	  -                             Crime location/address

reportedOn	       DATE	          -	                            Date case was reported

statuses	       INT	          FOREIGN KEY	                Current case status

 # **Setup Instructions**
 
 **Prerequisites**
 
Java Development Kit (JDK) 8 or higher

MySQL Server 8.0 or higher

MySQL Connector/J 8.0

-- Create database

CREATE DATABASE policecasemanager;

-- Use the database

USE policecasemanager;

-- Create tables (see database schema above)

-- Insert initial data

INSERT INTO crimeCategory (crimeTypeID) VALUES 

('THF'), ('FRD'), ('ASL');

INSERT INTO caseStatus (statusCode, statusDescription) VALUES 

(1, 'Open'), (2, 'Under Inverstigation'), (3, 'Closed');

**Step 2: Configure Database Connection**

Update DatabaseConnection.java with your MySQL credentials:

private static final String URL = "jdbc:mysql://localhost:3306/policecasemanager";

private static final String USERNAME = "your_username";

private static final String PASSWORD = "your_password";
 



