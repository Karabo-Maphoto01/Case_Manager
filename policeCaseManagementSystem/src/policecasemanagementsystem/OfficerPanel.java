/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package policecasemanagementsystem;

import javax.swing.table.DefaultTableModel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

/**
 *
 * @author Karabo
 */
public class OfficerPanel extends JPanel{
    
    private JTextField txtOfficerId, txtFirstName, txtLastName, txtRank, txtPhone;
    private JButton btnAdd, btnUpdate, btnClear, btnDelete;
    private JTable officerTable;
    private DefaultTableModel tableModel;
    private OfficerDAO officerDAO; //Data Access Object - talks to the database
    
    public OfficerPanel() {
        officerDAO = new OfficerDAO();
        initializeComponents();
        layoutComponents();
        loadOfficerData();
        attatchEventHandler();
        
    }
    
    private void initializeComponents() {
        //Text fields
        txtOfficerId = new JTextField(10);
        txtOfficerId.setEditable(false); // Can't edit ID
        txtFirstName = new JTextField(20);
        txtLastName = new JTextField(20); 
        
        //txtLastName = new JTextField(20);
        txtRank = new JTextField(15);
        txtPhone = new JTextField(15);
        
        //Buttons
        btnAdd = new JButton("Add officer");
        btnUpdate = new JButton("Update");
        btnUpdate.setEnabled(false); // Start disabled until user selects an officer
        btnDelete = new JButton("Delete");
        btnDelete.setEnabled(false);
        btnClear = new JButton("Clear");
        
        // Set up the table to show officer data
        // Column names for our table
        String[] columnNames = {"Officer ID", "First Name", "Last Name", "Rank", "Phone"};
        tableModel = new DefaultTableModel(columnNames, 0){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }   
        };
        officerTable = new JTable(tableModel);
        officerTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Can only select one row at a time
    }
    
    private void layoutComponents() {
        setLayout(new BorderLayout(10,10));

        //Input panel
        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        //Ofiicer ID
        gbc.gridx = 0; gbc. gridy = 0;
        inputPanel.add(new JLabel("Officer ID:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(txtOfficerId, gbc);
        
        //First Name
        gbc.gridx = 0; gbc.gridy = 1;
        inputPanel.add(new JLabel("First Name:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(txtFirstName, gbc);
        
        //Last Name
        // Last Name - Row 2 (MAKE SURE THIS IS CORRECT)
        gbc.gridx = 0; gbc.gridy = 2;
        inputPanel.add(new JLabel("Last Name:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(txtLastName, gbc);   
        
        //Rank
        gbc.gridx = 0; gbc.gridy = 3;
        inputPanel.add(new JLabel("Rank:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(txtRank, gbc);
        
        //Phone
        gbc.gridx = 0; gbc.gridy = 4;
        inputPanel.add(new JLabel("Phone"), gbc);
        gbc.gridx = 1;
        inputPanel.add(txtPhone, gbc);
        
        //Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        //buttonPanel.add(btnDelete);
        buttonPanel.add(btnClear);
        
        gbc.gridx = 0; gbc.gridy = 5;
        gbc.gridwidth = 2;
        inputPanel.add(buttonPanel, gbc);
        
        //Table with scroll panel
        JScrollPane tableScrollPane = new JScrollPane(officerTable);
        
        //Addign components to main panel
        add(inputPanel, BorderLayout.NORTH); // Form goes at top
        add(tableScrollPane, BorderLayout.CENTER);
        
        //Setting borders
        inputPanel.setBorder(BorderFactory.createTitledBorder("Officer Information"));
        tableScrollPane.setBorder(BorderFactory.createTitledBorder("Officer Records"));     
        
    }
    
    private void attatchEventHandler() {
        //add button
        btnAdd.addActionListener(e -> addOfficer());
        
        //Update button
        btnUpdate.addActionListener(e -> updateOfficer());
        
        // Delete button
       // btnDelete.addActionListener(e -> deleteOfficer());
        
        //Clear button
        btnClear.addActionListener(e -> clearForm());
        
        //Table selection listener
        officerTable.getSelectionModel().addListSelectionListener(e ->{
            if(!e.getValueIsAdjusting() && officerTable.getSelectedRow() != -1){
                selectOfficerFromTable();
            }
        });     
    }
    
    private void addOfficer() {
        try {
            // Get data from text fields and remove extra spaces
            String firstName = txtFirstName.getText().trim();
            String lastName = txtLastName.getText().trim();
            String rank = txtRank.getText().trim();
            String phone = txtPhone.getText().trim();
            
            //Validating every field is not empty
            if(firstName.isEmpty() || lastName.isEmpty() || rank.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please fill in all required fields","Validation Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            // Create new Officer object with the entered data
            Officer officer = new Officer(firstName, lastName, rank, phone);
            boolean success =officerDAO.insertOfficer(officer);
            
            if(success) {
                JOptionPane.showMessageDialog(null,"Officer added successfully!");
                clearForm();
                loadOfficerData();
            }else {
                JOptionPane.showMessageDialog(null, "Failed to add officer", "Error", JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Error" + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    /*I got rid of this because o thought to myself deleting an officer will be discrediting them of their work
    private void deleteOfficer(){
        try {
            int selectedRow = officerTable.getSelectedRow();
            if(selectedRow == -1){
                JOptionPane.showMessageDialog(null, "Please select an Officer to delete", "No Selection", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            int officerId = (int) tableModel.getValueAt(selectedRow, 0);
            String officerName = tableModel.getValueAt(selectedRow, 1) + " " + tableModel.getValueAt(selectedRow, 2);
            
            int confirm = JOptionPane.showMessageDialog(null, "Are you sure you want to delete officer: " + officerName + "?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
            
            if(confirm == JOptionPane.YES_OPTION){
                
            }
        } catch (Exception e) {
        }
        
    }*/
    
    // When user selects an officer from the table, load their data into the form
    private void selectOfficerFromTable() {
        int selectedRow  =officerTable.getSelectedRow();
        if(selectedRow != -1) {
            // Get data from table and put it in text fields
            txtOfficerId.setText(tableModel.getValueAt(selectedRow, 0).toString());
            txtFirstName.setText(tableModel.getValueAt(selectedRow, 1).toString());
            txtLastName.setText(tableModel.getValueAt(selectedRow, 2).toString());
            txtRank.setText(tableModel.getValueAt(selectedRow, 3).toString());
            txtPhone.setText(tableModel.getValueAt(selectedRow, 4).toString());
            
            // Switch button states, can't add when editing, but can update/delete
            btnAdd.setEnabled(false);
            btnUpdate.setEnabled(true);
            btnDelete.setEnabled(true);          
        }
    }
    private void updateOfficer() {
    try {
        // Get the officer ID from the hidden field
        int officerId = Integer.parseInt(txtOfficerId.getText());
        
        // Get updated data from text fields
        String firstName = txtFirstName.getText().trim();
        String lastName = txtLastName.getText().trim();
        String rank = txtRank.getText().trim();
        String phone = txtPhone.getText().trim();
        
        // Validation - check if required fields are filled
        if (firstName.isEmpty() || lastName.isEmpty() || rank.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Please fill in all required fields (First Name, Last Name, Rank)", 
                "Validation Error", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Create officer object with updated data
        Officer officer = new Officer(firstName, lastName, rank, phone);
        officer.setOfficerId(officerId); // Set the ID so we know which officer to update
        
        // Try to update in database
        boolean success = officerDAO.updateOfficer(officer);
        
        if (success) {
            JOptionPane.showMessageDialog(this, 
                "Officer updated successfully!", 
                "Success", 
                JOptionPane.INFORMATION_MESSAGE);
            clearForm();
            loadOfficerData(); // Refresh the table
        } else {
            JOptionPane.showMessageDialog(this, 
                "Failed to update officer", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
        
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, 
            "Invalid officer ID", 
            "Error", 
            JOptionPane.ERROR_MESSAGE);
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, 
            "Error: " + e.getMessage(), 
            "Database Error", 
            JOptionPane.ERROR_MESSAGE);
    }
}
    
    // Clear all form fields and reset button states
    private void clearForm(){
        txtOfficerId.setText("");
        txtFirstName.setText("");
        txtLastName.setText("");
        txtRank.setText("");
        txtPhone.setText("");
        
        officerTable.clearSelection();// Deselect any selected row
        btnAdd.setEnabled(true);
        btnUpdate.setEnabled(false);
        btnDelete.setEnabled(false);
    }
    
    private void loadOfficerData() {
        try {
            tableModel.setRowCount(0); // Clear existing data
            
            // Get all officers from database
            List<Officer> officers = officerDAO.getAllOfficers();
            for(Officer officer: officers){
                
                // Add each officer to the table
                Object[] rowData = {
                    officer.getOfficerId(),
                    officer.getFirstName(),
                    officer.getLastName(),
                    officer.getRank(),
                    officer.getPhone()
                };
                tableModel.addRow(rowData);// Add row
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Error loading officer data: " + e.getMessage(), "Database Error" , JOptionPane.ERROR_MESSAGE);
        }      
    }
}
