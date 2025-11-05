/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package policecasemanagementsystem;

/**
 *
 * @author Karabo
 */
public class Officer {
    
    private int officerId;
    private String firstName;
    private String lastName;
    private String rank;
    private String phone;
    
    //Constructors

    public Officer() {
    }

    public Officer(String firstName, String lastName, String rank, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.rank = rank;
        this.phone = phone;
    }
    
    
    //Getters and setters

    public int getOfficerId() {
        return officerId;
    }

    public void setOfficerId(int officerId) {
        this.officerId = officerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    
}
