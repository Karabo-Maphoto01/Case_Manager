CREATE DATABASE  IF NOT EXISTS `policecasemanager` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `policecasemanager`;
-- MySQL dump 10.13  Distrib 8.0.41, for Win64 (x86_64)
--
-- Host: localhost    Database: policecasemanager
-- ------------------------------------------------------
-- Server version	8.0.41

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `caserecord`
--

DROP TABLE IF EXISTS `caserecord`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `caserecord` (
  `caseID` int NOT NULL AUTO_INCREMENT,
  `caseDetails` varchar(255) NOT NULL,
  `attendingOfficer` int NOT NULL,
  `category` varchar(12) DEFAULT NULL,
  `location` varchar(50) DEFAULT NULL,
  `reportedOn` date DEFAULT NULL,
  `statuses` int DEFAULT NULL,
  PRIMARY KEY (`caseID`),
  KEY `attendingOfficer` (`attendingOfficer`),
  KEY `category` (`category`),
  KEY `caserecord_ibfk_3` (`statuses`),
  CONSTRAINT `caserecord_ibfk_1` FOREIGN KEY (`attendingOfficer`) REFERENCES `officer` (`officerID`) ON DELETE RESTRICT,
  CONSTRAINT `caserecord_ibfk_2` FOREIGN KEY (`category`) REFERENCES `crimecategory` (`crimeTypeID`) ON DELETE RESTRICT,
  CONSTRAINT `caserecord_ibfk_3` FOREIGN KEY (`statuses`) REFERENCES `casestatus` (`statusCode`)
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `caserecord`
--

LOCK TABLES `caserecord` WRITE;
/*!40000 ALTER TABLE `caserecord` DISABLE KEYS */;
INSERT INTO `caserecord` VALUES (44,'Burglary at City Mall',3,'THF','City Mall, Downtown','2025-05-05',2),(45,'Cyber Fraud Complaint',4,'FRD','Online/Internet','2025-05-03',1),(46,'Car Theft Case',5,'THF','Secure Parking Garage','2025-05-01',2),(47,'Assault Report at Nightclub',6,'ASL','Electric Nightclub','2025-04-28',3),(48,'Domestic Dispute',7,'ASL','123 Maple Street','2025-04-30',1);
/*!40000 ALTER TABLE `caserecord` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-11-05 21:49:18
