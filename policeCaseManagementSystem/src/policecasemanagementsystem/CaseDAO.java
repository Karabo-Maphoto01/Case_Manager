/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package policecasemanagementsystem;
 import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Karabo
 */
public class CaseDAO {
    
    //Insert new case
    public boolean insertCase (Case caseObj) {
        String sqlQuery = "INSERT INTO  caseRecord (caseDetails, attendingOfficer, category, location, reportedOn, statuses) VALUES (?, ?, ?, ?, ?, ?)";
        
        try(Connection con = DatabaseConnection.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sqlQuery)){
            
            pstmt.setString(1, caseObj.getCaseDetails());
            pstmt.setInt(2, caseObj.getAttendingOfficer());
            pstmt.setString(3, caseObj.getCategory());
            pstmt.setString(4, caseObj.getLocation());
            pstmt.setDate(5, new java.sql.Date(caseObj.getReportedOn().getTime()));
            pstmt.setInt(6, caseObj.getStatus());
            
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
            
        } catch (SQLException e) {
            System.out.println("Error inserting case: " + e.getMessage());
            return false;
        }
    }
    
    //Retrieve all cases
    public List<Case> getAllCases(){
        List<Case> cases = new ArrayList<>();
        String sqlQuery = "SELECT * FROM caseRecord";
        
        try(Connection con = DatabaseConnection.getConnection();
            Statement stm = con.createStatement();
            ResultSet rs =stm.executeQuery(sqlQuery)) {
            
            while(rs.next()) {
                Case caseObj = new Case();
                caseObj.setCaseId(rs.getInt("caseID"));
                caseObj.setCaseDetails(rs.getString("caseDetails"));
                caseObj.setAttendingOfficer(rs.getInt("attendingOfficer"));
                caseObj.setCategory(rs.getString("category"));
                caseObj.setLocation(rs.getString("location"));
                caseObj.setReportedOn(rs.getDate("reportedOn"));
                caseObj.setStatus(rs.getInt("statuses"));
                cases.add(caseObj);
                
            }
            
        } catch (SQLException e) {
            System.out.println("Error retrieving cases: " + e.getMessage());
        }
        
        return cases;
    }
    
    //Update case
    public boolean updateCase(Case caseObj) {
        String sqlQuery = "UPDATE caseRecord SET caseDetails = ?, attendingOfficer = ?, category = ?, location = ?, reportedOn = ?, statuses = ? WHERE caseID = ?";
        
        try(Connection con = DatabaseConnection.getConnection();
            PreparedStatement pstm = con.prepareStatement(sqlQuery)) {
            
            pstm.setString(1,caseObj.getCaseDetails());
            pstm.setInt(2, caseObj.getAttendingOfficer());
            pstm.setString(3, caseObj.getCategory());
            pstm.setString(4, caseObj.getLocation());
            pstm.setDate(5, new java.sql.Date(caseObj.getReportedOn().getTime()));
            pstm.setInt(6, caseObj.getStatus());
            pstm.setInt(7, caseObj.getCaseId());
            
            int affectedRows = pstm.executeUpdate();
            return affectedRows > 0;
            
        } catch (SQLException e) {
            System.out.println("Error updating case: " + e.getMessage());
            return false;
        }
    }
    
    //Delete case
    public boolean deleteCase(int caseId) {
        String sqlQuery = "DELETE FROM caseRecord WHERE caseID = ?" ;
        
        try(Connection con = DatabaseConnection.getConnection();
            PreparedStatement pstm = con.prepareStatement(sqlQuery)) {
            
            pstm.setInt(1, caseId);
            int affectedRows = pstm.executeUpdate();
            return affectedRows > 0;
            
        } catch (SQLException e) {
            System.out.println("Error deleting case: " + e.getMessage());
            return false;
        }       
    } 
}
