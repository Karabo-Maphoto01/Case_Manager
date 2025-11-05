/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package policecasemanagementsystem;

import java.util.Date;

/**
 *
 * @author Karabo
 */
public class Case {
    
    private int caseId;
    private String caseDetails;
    private int attendingOfficer;
    private String category; 
    private String location;
    private Date reportedOn;
    private int status;
    
    //Constructors

    public Case() {
    }

    public Case( String caseDetails, int attendingOfficer, String category, String location, Date reportedOn, int status) {
        this.caseDetails = caseDetails;
        this.attendingOfficer = attendingOfficer;
        this.category = category;
        this.location = location;
        this.reportedOn = reportedOn;
        this.status = status;
    }

    public int getCaseId() {
        return caseId;
    }

    public void setCaseId(int caseId) {
        this.caseId = caseId;
    }

    public String getCaseDetails() {
        return caseDetails;
    }

    public void setCaseDetails(String caseDetails) {
        this.caseDetails = caseDetails;
    }

    public int getAttendingOfficer() {
        return attendingOfficer;
    }

    public void setAttendingOfficer(int attendingOfficer) {
        this.attendingOfficer = attendingOfficer;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getReportedOn() {
        return reportedOn;
    }

    public void setReportedOn(Date reportedOn) {
        this.reportedOn = reportedOn;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    
}
