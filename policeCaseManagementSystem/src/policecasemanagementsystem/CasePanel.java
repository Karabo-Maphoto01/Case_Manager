/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package policecasemanagementsystem;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.Date;
import java.util.List;
/**
 *
 * @author Karabo
 */
public class CasePanel extends JPanel {
    private JTextField txtCaseId, txtCaseDetails, txtLocation;
    private JComboBox<String> cmbOfficer, cmbCategory, cmbStatus;
    private JButton btnAdd, btnUpdate, btnClear, btnDelete;
    private JTable caseTable;
    private DefaultTableModel tableModel;
    private CaseDAO caseDAO;
    private OfficerDAO officerDAO;
    
    public CasePanel() {
        caseDAO = new CaseDAO();
        officerDAO = new OfficerDAO();
        initializeComponents();
        layoutComponents();
        loadCaseData();
        loadOfficersComboBox();
        attatchEventHandlers();
    }
    
    private void initializeComponents() {
        //Text fields
        txtCaseId = new JTextField(10);
        txtCaseId.setEditable(false);
        txtCaseDetails = new JTextField(30);
        txtLocation = new JTextField(25);
        
        //Combo boxes
        cmbOfficer = new JComboBox<>();
        cmbCategory = new JComboBox<>(new String[]{"THF", "FRD","ASL"}); //Note THF = Theft, FRD = Fraud, ASL = Assault
        cmbStatus = new JComboBox<>(new String[]{"Open","Under Investigation","Closed"});
        
        //Buttons
        btnAdd = new JButton("Add Case");
        btnUpdate = new JButton ("Update Case");
        btnUpdate.setEnabled(false);
        btnDelete = new JButton("Delete Case");
        btnDelete.setEnabled(false);
        btnClear = new JButton("Clear");
        
        //Table
        String[] columnNames = {"Case ID", "Details", "Officer", "Category", "Location", "Reported On", "Status"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        caseTable = new JTable(tableModel);
        caseTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }
    
    private void layoutComponents() {
        setLayout(new BorderLayout(10, 10));
        
        // Input panel
        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Case ID
        gbc.gridx = 0; gbc.gridy = 0;
        inputPanel.add(new JLabel("Case ID:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(txtCaseId, gbc);
        
        // Case Details
        gbc.gridx = 0; gbc.gridy = 1;
        inputPanel.add(new JLabel("Case Details:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(txtCaseDetails, gbc);
        
        // Attending Officer
        gbc.gridx = 0; gbc.gridy = 2;
        inputPanel.add(new JLabel("Attending Officer:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(cmbOfficer, gbc);
        
        // Category
        gbc.gridx = 0; gbc.gridy = 3;
        inputPanel.add(new JLabel("Category:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(cmbCategory, gbc);
        
        // Location
        gbc.gridx = 0; gbc.gridy = 4;
        inputPanel.add(new JLabel("Location:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(txtLocation, gbc);
        
        // Status
        gbc.gridx = 0; gbc.gridy = 5;
        inputPanel.add(new JLabel("Status:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(cmbStatus, gbc);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnClear);
        
        gbc.gridx = 0; gbc.gridy = 6;
        gbc.gridwidth = 2;
        inputPanel.add(buttonPanel, gbc);
        
        // Table with scroll pane
        JScrollPane tableScrollPane = new JScrollPane(caseTable);
        
        // Add components to main panel
        add(inputPanel, BorderLayout.NORTH);
        add(tableScrollPane, BorderLayout.CENTER);
        
        // Set borders
        inputPanel.setBorder(BorderFactory.createTitledBorder("Case Information"));
        tableScrollPane.setBorder(BorderFactory.createTitledBorder("Case Records"));
    }
    
    
    private void attatchEventHandlers() {
        // Add button
        btnAdd.addActionListener(e -> addCase());
        
        // Update button
        btnUpdate.addActionListener(e -> updateCase());
        
        // Delete button
        btnDelete.addActionListener(e -> deleteCase());
        
        // Clear button
        btnClear.addActionListener(e -> clearForm());
        
        // Table selection listener
        caseTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && caseTable.getSelectedRow() != -1) {
                selectCaseFromTable();
            }
        });
    }
    
    private void addCase() {
        try {
            String caseDetails = txtCaseDetails.getText().trim();
            String location = txtLocation.getText().trim();
            
            // Validation
            if (caseDetails.isEmpty() || cmbOfficer.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(this, "Please fill in all required fields", "Validation Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            // Get selected officer ID
            String selectedOfficer = (String) cmbOfficer.getSelectedItem();
            int officerId = Integer.parseInt(selectedOfficer.split(" - ")[0]);
            
            String category = (String) cmbCategory.getSelectedItem();
            int status = cmbStatus.getSelectedIndex() + 1; // 1 for Open, 2 for Closed
            
            Case caseObj = new Case(caseDetails, officerId, category, location, new Date(), status);
            boolean success = caseDAO.insertCase(caseObj);
            
            if (success) {
                JOptionPane.showMessageDialog(this, "Case added successfully!");
                clearForm();
                loadCaseData();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add case", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void updateCase() {
        try {
            int caseId = Integer.parseInt(txtCaseId.getText());
            String caseDetails = txtCaseDetails.getText().trim();
            String location = txtLocation.getText().trim();
            
            // Validation
            if (caseDetails.isEmpty() || cmbOfficer.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(this, "Please fill in all required fields", "Validation Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            // Get selected officer ID
            String selectedOfficer = (String) cmbOfficer.getSelectedItem();
            int officerId = Integer.parseInt(selectedOfficer.split(" - ")[0]);
            
            String category = (String) cmbCategory.getSelectedItem();
            int status = cmbStatus.getSelectedIndex() + 1;
            
            Case caseObj = new Case(caseDetails, officerId, category, location, new Date(), status);
            caseObj.setCaseId(caseId);
            
            boolean success = caseDAO.updateCase(caseObj);
            
            if (success) {
                JOptionPane.showMessageDialog(this, "Case updated successfully!");
                clearForm();
                loadCaseData();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to update case", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void deleteCase() {
        try {
            int selectedRow = caseTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Please select a case to delete", "No Selection", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            int caseId = (int) tableModel.getValueAt(selectedRow, 0);
            String caseDetails = tableModel.getValueAt(selectedRow, 1).toString();
            
            int confirm = JOptionPane.showConfirmDialog(this, 
                "Are you sure you want to delete case: " + caseDetails + "?", 
                "Confirm Delete", JOptionPane.YES_NO_OPTION);
            
            if (confirm == JOptionPane.YES_OPTION) {
                boolean success = caseDAO.deleteCase(caseId);
                if (success) {
                    JOptionPane.showMessageDialog(this, "Case deleted successfully!");
                    clearForm();
                    loadCaseData();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to delete case", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void selectCaseFromTable() {
        int selectedRow = caseTable.getSelectedRow();
        if (selectedRow != -1) {
            txtCaseId.setText(tableModel.getValueAt(selectedRow, 0).toString());
            txtCaseDetails.setText(tableModel.getValueAt(selectedRow, 1).toString());
            txtLocation.setText(tableModel.getValueAt(selectedRow, 4).toString());
            
            // Set combo box values
            String officerInfo = tableModel.getValueAt(selectedRow, 2).toString();
            cmbOfficer.setSelectedItem(officerInfo);
            
            String category = tableModel.getValueAt(selectedRow, 3).toString();
            cmbCategory.setSelectedItem(category);
            
            String status = tableModel.getValueAt(selectedRow, 6).toString();
            cmbStatus.setSelectedItem(status);
            
            btnAdd.setEnabled(false);
            btnUpdate.setEnabled(true);
            btnDelete.setEnabled(true);
        }
    }
    
    private void clearForm() {
        txtCaseId.setText("");
        txtCaseDetails.setText("");
        txtLocation.setText("");
        cmbCategory.setSelectedIndex(0);
        cmbStatus.setSelectedIndex(0);
        
        caseTable.clearSelection();
        btnAdd.setEnabled(true);
        btnUpdate.setEnabled(false);
        btnDelete.setEnabled(false);
    }
    
    private void loadCaseData() {
        try {
            tableModel.setRowCount(0);
            
            List<Case> cases = caseDAO.getAllCases();
            for (Case caseObj : cases) {
                // Get officer name for display
                String officerName = getOfficerName(caseObj.getAttendingOfficer());
                String status = "";
                switch (caseObj.getStatus()) {
                case 1: status = "Open"; 
                break;
                case 2: status = "Under Investigation";
                break;  // NEW STATUS
                case 3: status = "Closed"; 
                break;
                default: status = "Unknown";
            }
                
                Object[] rowData = {
                    caseObj.getCaseId(),
                    caseObj.getCaseDetails(),
                    officerName,
                    caseObj.getCategory(),
                    caseObj.getLocation(),
                    caseObj.getReportedOn(),
                    status
                };
                tableModel.addRow(rowData);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error loading case data: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void loadOfficersComboBox() {
        try {
            cmbOfficer.removeAllItems();
            List<Officer> officers = officerDAO.getAllOfficers();
            for (Officer officer : officers) {
                cmbOfficer.addItem(officer.getOfficerId() + " - " + officer.getFirstName() + " " + officer.getLastName());
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error loading officers: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private String getOfficerName(int officerId) {
        try {
            List<Officer> officers = officerDAO.getAllOfficers();
            for (Officer officer : officers) {
                if (officer.getOfficerId() == officerId) {
                    return officer.getFirstName() + " " + officer.getLastName();
                }
            }
        } catch (Exception e) {
            // Log error
        }
        return "Unknown";
    }
}


