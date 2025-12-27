package com.healthcare.view;

import com.healthcare.controller.HealthcareController;
import com.healthcare.model.Patient;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;

/**
 * Panel for Patient management (CRUD operations)
 */
public class PatientPanel extends JPanel {
    private HealthcareController controller;
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField patientIDField, firstNameField, lastNameField, dobField, genderField;
    private JTextField nhsNumberField, emailField, phoneField, addressField, postcodeField;
    private JTextField emergencyContactNameField, emergencyContactPhoneField, registrationDateField, gpSurgeryField;

    public PatientPanel(HealthcareController controller) {
        this.controller = controller;
        initializePanel();
    }

    private void initializePanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Soft background for a cleaner "admin panel" appearance
        setBackground(new Color(245, 247, 250));

        // Table
        String[] columns = {"Patient ID", "First Name", "Last Name", "DOB", "Gender", 
                          "NHS Number", "Email", "Phone", "Address", "Postcode", 
                          "Emergency Contact", "Emergency Phone", "Registration Date", "GP Surgery"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        table.setFillsViewportHeight(true);
        table.setRowHeight(24);
        table.setShowGrid(true);
        table.setGridColor(new Color(230, 230, 230));
        table.setSelectionBackground(new Color(227, 242, 253));
        table.setSelectionForeground(Color.BLACK);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                loadSelectedPatient();
            }
        });

        // Style table header for a dashboard-like look
        JTableHeader header = table.getTableHeader();
        header.setReorderingAllowed(false);
        header.setBackground(new Color(236, 239, 241));
        header.setForeground(new Color(55, 71, 79));
        header.setFont(header.getFont().deriveFont(Font.BOLD, 13f));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Patients"));
        add(scrollPane, BorderLayout.CENTER);

        // Form panel
        JPanel formPanel = createFormPanel();
        formPanel.setBorder(BorderFactory.createTitledBorder("Patient Details"));
        formPanel.setBackground(new Color(250, 250, 250));
        add(formPanel, BorderLayout.SOUTH);

        // Buttons panel
        JPanel buttonPanel = createButtonPanel();
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
        buttonPanel.setBackground(new Color(245, 247, 250));
        add(buttonPanel, BorderLayout.NORTH);
    }

    private JPanel createFormPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(true);
        panel.setBackground(new Color(250, 250, 250));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        int row = 0;
        panel.add(new JLabel("Patient ID:"), gbc);
        gbc.gridx = 1;
        patientIDField = new JTextField(15);
        panel.add(patientIDField, gbc);

        gbc.gridx = 2;
        panel.add(new JLabel("First Name:"), gbc);
        gbc.gridx = 3;
        firstNameField = new JTextField(15);
        panel.add(firstNameField, gbc);

        gbc.gridx = 4;
        panel.add(new JLabel("Last Name:"), gbc);
        gbc.gridx = 5;
        lastNameField = new JTextField(15);
        panel.add(lastNameField, gbc);

        row++;
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(new JLabel("Date of Birth:"), gbc);
        gbc.gridx = 1;
        dobField = new JTextField(15);
        panel.add(dobField, gbc);

        gbc.gridx = 2;
        panel.add(new JLabel("Gender:"), gbc);
        gbc.gridx = 3;
        genderField = new JTextField(15);
        panel.add(genderField, gbc);

        gbc.gridx = 4;
        panel.add(new JLabel("NHS Number:"), gbc);
        gbc.gridx = 5;
        nhsNumberField = new JTextField(15);
        panel.add(nhsNumberField, gbc);

        row++;
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        emailField = new JTextField(15);
        panel.add(emailField, gbc);

        gbc.gridx = 2;
        panel.add(new JLabel("Phone:"), gbc);
        gbc.gridx = 3;
        phoneField = new JTextField(15);
        panel.add(phoneField, gbc);

        gbc.gridx = 4;
        panel.add(new JLabel("Address:"), gbc);
        gbc.gridx = 5;
        addressField = new JTextField(15);
        panel.add(addressField, gbc);

        row++;
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(new JLabel("Postcode:"), gbc);
        gbc.gridx = 1;
        postcodeField = new JTextField(15);
        panel.add(postcodeField, gbc);

        gbc.gridx = 2;
        panel.add(new JLabel("Emergency Contact:"), gbc);
        gbc.gridx = 3;
        emergencyContactNameField = new JTextField(15);
        panel.add(emergencyContactNameField, gbc);

        gbc.gridx = 4;
        panel.add(new JLabel("Emergency Phone:"), gbc);
        gbc.gridx = 5;
        emergencyContactPhoneField = new JTextField(15);
        panel.add(emergencyContactPhoneField, gbc);

        row++;
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(new JLabel("Registration Date:"), gbc);
        gbc.gridx = 1;
        registrationDateField = new JTextField(15);
        panel.add(registrationDateField, gbc);

        gbc.gridx = 2;
        panel.add(new JLabel("GP Surgery:"), gbc);
        gbc.gridx = 3;
        gpSurgeryField = new JTextField(15);
        panel.add(gpSurgeryField, gbc);

        return panel;
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));

        JButton addButton = createPrimaryButton("Add");
        addButton.addActionListener(e -> addPatient());
        panel.add(addButton);

        JButton updateButton = createPrimaryButton("Update");
        updateButton.addActionListener(e -> updatePatient());
        panel.add(updateButton);

        JButton deleteButton = createDangerButton("Delete");
        deleteButton.addActionListener(e -> deletePatient());
        panel.add(deleteButton);

        JButton clearButton = createSecondaryButton("Clear");
        clearButton.addActionListener(e -> clearForm());
        panel.add(clearButton);

        JButton refreshButton = createSecondaryButton("Refresh");
        refreshButton.addActionListener(e -> refreshData());
        panel.add(refreshButton);

        return panel;
    }

    /**
     * Button styling helpers to give an "admin dashboard" look
     */
    private JButton createPrimaryButton(String text) {
        JButton button = new JButton(text);
        styleButton(button, new Color(33, 150, 243), Color.WHITE);
        return button;
    }

    private JButton createSecondaryButton(String text) {
        JButton button = new JButton(text);
        styleButton(button, new Color(236, 239, 241), new Color(55, 71, 79));
        return button;
    }

    private JButton createDangerButton(String text) {
        JButton button = new JButton(text);
        styleButton(button, new Color(229, 57, 53), Color.WHITE);
        return button;
    }

    private void styleButton(JButton button, Color background, Color foreground) {
        button.setBackground(background);
        button.setForeground(foreground);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(6, 14, 6, 14));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setFont(button.getFont().deriveFont(Font.BOLD, 12f));
    }

    private void addPatient() {
        try {
            Patient patient = createPatientFromForm();
            if (patient != null) {
                controller.addPatient(patient);
                refreshData();
                clearForm();
                JOptionPane.showMessageDialog(this, "Patient added successfully!");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error adding patient: " + e.getMessage(), 
                                        "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updatePatient() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select a patient to update.");
            return;
        }

        try {
            String patientID = (String) tableModel.getValueAt(selectedRow, 0);
            Patient patient = createPatientFromForm();
            if (patient != null) {
                controller.deletePatient(patientID);
                controller.addPatient(patient);
                refreshData();
                clearForm();
                JOptionPane.showMessageDialog(this, "Patient updated successfully!");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error updating patient: " + e.getMessage(), 
                                        "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deletePatient() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select a patient to delete.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to delete this patient?", 
            "Confirm Delete", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            String patientID = (String) tableModel.getValueAt(selectedRow, 0);
            controller.deletePatient(patientID);
            refreshData();
            clearForm();
            JOptionPane.showMessageDialog(this, "Patient deleted successfully!");
        }
    }

    private Patient createPatientFromForm() {
        if (patientIDField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Patient ID is required.");
            return null;
        }

        return new Patient(
            patientIDField.getText().trim(),
            firstNameField.getText().trim(),
            lastNameField.getText().trim(),
            dobField.getText().trim(),
            genderField.getText().trim(),
            nhsNumberField.getText().trim(),
            emailField.getText().trim(),
            phoneField.getText().trim(),
            addressField.getText().trim(),
            postcodeField.getText().trim(),
            emergencyContactNameField.getText().trim(),
            emergencyContactPhoneField.getText().trim(),
            registrationDateField.getText().trim(),
            gpSurgeryField.getText().trim()
        );
    }

    private void loadSelectedPatient() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            patientIDField.setText((String) tableModel.getValueAt(selectedRow, 0));
            firstNameField.setText((String) tableModel.getValueAt(selectedRow, 1));
            lastNameField.setText((String) tableModel.getValueAt(selectedRow, 2));
            dobField.setText((String) tableModel.getValueAt(selectedRow, 3));
            genderField.setText((String) tableModel.getValueAt(selectedRow, 4));
            nhsNumberField.setText((String) tableModel.getValueAt(selectedRow, 5));
            emailField.setText((String) tableModel.getValueAt(selectedRow, 6));
            phoneField.setText((String) tableModel.getValueAt(selectedRow, 7));
            addressField.setText((String) tableModel.getValueAt(selectedRow, 8));
            postcodeField.setText((String) tableModel.getValueAt(selectedRow, 9));
            emergencyContactNameField.setText((String) tableModel.getValueAt(selectedRow, 10));
            emergencyContactPhoneField.setText((String) tableModel.getValueAt(selectedRow, 11));
            registrationDateField.setText((String) tableModel.getValueAt(selectedRow, 12));
            gpSurgeryField.setText((String) tableModel.getValueAt(selectedRow, 13));
        }
    }

    private void clearForm() {
        patientIDField.setText("");
        firstNameField.setText("");
        lastNameField.setText("");
        dobField.setText("");
        genderField.setText("");
        nhsNumberField.setText("");
        emailField.setText("");
        phoneField.setText("");
        addressField.setText("");
        postcodeField.setText("");
        emergencyContactNameField.setText("");
        emergencyContactPhoneField.setText("");
        registrationDateField.setText("");
        gpSurgeryField.setText("");
    }

    public void refreshData() {
        tableModel.setRowCount(0);
        List<Patient> patients = controller.getAllPatients();
        for (Patient patient : patients) {
            tableModel.addRow(new Object[]{
                patient.getPatientID(),
                patient.getFirstName(),
                patient.getLastName(),
                patient.getDateOfBirth(),
                patient.getGender(),
                patient.getNhsNumber(),
                patient.getEmail(),
                patient.getPhone(),
                patient.getAddress(),
                patient.getPostcode(),
                patient.getEmergencyContactName(),
                patient.getEmergencyContactPhone(),
                patient.getRegistrationDate(),
                patient.getGpSurgery()
            });
        }
    }
}



