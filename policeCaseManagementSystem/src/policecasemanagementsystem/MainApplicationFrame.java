/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package policecasemanagementsystem;

import java.awt.Color;
import javax.swing.*;
/**
 *
 * @author Karabo
 */
public class MainApplicationFrame extends JFrame {
    private JTabbedPane tabbedPane; // For switching between Officer and Case tabs
    private OfficerPanel officerPanel; // Panel for managing officers
    private CasePanel casePanel; // Panel for managing cases
    
    // Constructor - called when we create a new PoliceCaseManagementSystem
    public MainApplicationFrame() {
        initializeUI();
    }
    
    // Method to set up the visual parts of our application
    private void initializeUI(){
        setTitle("Police Case Management System"); // Window title
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close button stops program
        setSize(900, 600);
        setLocationRelativeTo(null); // Center the window on screen
        
        // Set JFrame background color to white
        getContentPane().setBackground(Color.WHITE);
        
        //Tabbed pane
        tabbedPane = new JTabbedPane();
        
        officerPanel= new OfficerPanel();
        casePanel = new CasePanel();
        
        //Adding tabs
        tabbedPane.addTab("Officer Management", officerPanel);
        tabbedPane.addTab("Case Management", casePanel);
        
        add(tabbedPane);   
    }
    
    
    
}
