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
public class OfficerDAO {
    
    //Insert new officer
    public boolean insertOfficer(Officer officer) {
        String insertSQL = "INSERT INTO officer (firstName, lastName, officerRank, phone) VALUES (?, ?, ?, ?)";
        
        try(Connection con = DatabaseConnection.getConnection();
            PreparedStatement pstmt = con.prepareStatement(insertSQL)) {
            
            pstmt.setString(1, officer.getFirstName());
            pstmt.setString(2, officer.getLastName());
            pstmt.setString(3, officer.getRank()); //Maps to officerRank in the database
            pstmt.setString(4, officer.getPhone());
            
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
            
        } catch (SQLException e) {
            System.out.println("Error inserting officer: " + e.getMessage());
            return false;
        }
    }
    
    //Retrieve all ofiicers
    public List<Officer> getAllOfficers() {
        List<Officer> officers = new ArrayList<>();
        String retrieveSQL = "SELECT * FROM officer";
        
        try (Connection con = DatabaseConnection.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(retrieveSQL)){
            
            while(rs.next()) {
                Officer officer = new Officer();
                officer.setOfficerId(rs.getInt("officerID"));
                officer.setFirstName(rs.getString("firstName"));
                officer.setLastName(rs.getString("lastName"));
                officer.setRank(rs.getString("officerRank"));
                officer.setPhone(rs.getString("phone"));
                officers.add(officer);    
            }
    
        } catch (SQLException e) {
            System.out.println("Error retrieving officers: " + e.getMessage());
        }
        return officers;
    }
    
    //Update officer
    public boolean updateOfficer(Officer officer) {
        String updateSQL = "UPDATE officer SET firstName = ?, lastName = ?, officerRank = ?, phone = ? WHERE officerID = ?";
        
        try(Connection con = DatabaseConnection.getConnection();
            PreparedStatement pstmt = con.prepareStatement(updateSQL)) {
            
            pstmt.setString(1, officer.getFirstName());
            pstmt.setString(2, officer.getLastName());
            pstmt.setString(3, officer.getRank());
            pstmt.setString(4, officer.getPhone());
            pstmt.setInt(5, officer.getOfficerId());
            
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
            
        } catch(SQLException e) {
            System.out.println("Error updating officer" + e.getMessage());
            return false; 
        }   
    }
    
    public Officer getOfficerById(int officerId) {
    String sql = "SELECT * FROM OFFICER WHERE officerID = ?";
    
    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        
        pstmt.setInt(1, officerId);
        ResultSet rs = pstmt.executeQuery();
        
        if (rs.next()) {
            Officer officer = new Officer();
            officer.setOfficerId(rs.getInt("OfficerID"));
            officer.setFirstName(rs.getString("FirstName"));
            officer.setLastName(rs.getString("LastName"));
            officer.setRank(rs.getString("Rank"));
            officer.setPhone(rs.getString("Phone"));
            return officer;
        }
        
    } catch (SQLException e) {
        System.err.println("Error retrieving officer: " + e.getMessage());
    }
    
    return null;
}
}
