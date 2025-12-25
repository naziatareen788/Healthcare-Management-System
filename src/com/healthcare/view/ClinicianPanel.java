package com.healthcare.view;

import com.healthcare.controller.HealthcareController;
import com.healthcare.model.Clinician;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;

public class ClinicianPanel extends JPanel {

    private final HealthcareController controller;
    private JTable table;
    private DefaultTableModel tableModel;

    private JTextField clinicianIDField, firstNameField, lastNameField, qualificationField;
    private JTextField specialtyField, gmcNumberField, workplaceField, workplaceTypeField;
    private JTextField employmentStatusField, startDateField, emailField, phoneField;

    public ClinicianPanel(HealthcareController controller) {
        this.controller = controller;
        initializePanel();
        refreshData();
    }

    private void initializePanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setBackground(new Color(245, 247, 250));

        // ===== Buttons =====
        JPanel buttonPanel = createButtonPanel();
        buttonPanel.setBackground(new Color(245, 247, 250));
        add(buttonPanel, BorderLayout.NORTH);

        // ===== Table =====
        String[] columns = {
                "Clinician ID", "First Name", "Last Name", "Qualification",
                "Specialty", "GMC Number", "Workplace", "Workplace Type",
                "Employment Status", "Start Date", "Email", "Phone"
        };

        tableModel = new DefaultTableModel(columns, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };

        table = new JTable(tableModel);
        table.setFillsViewportHeight(true);
        table.setRowHeight(24);
        table.setShowGrid(true);
        table.setGridColor(new Color(230, 230, 230));
        table.setSelectionBackground(new Color(227, 242, 253));
        table.setSelectionForeground(Color.BLACK);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JTableHeader header = table.getTableHeader();
        header.setReorderingAllowed(false);
        header.setBackground(new Color(236, 239, 241));
        header.setForeground(new Color(55, 71, 79));
        header.setFont(header.getFont().deriveFont(Font.BOLD, 13f));

        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) loadSelectedClinician();
        });

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Clinicians"));
        add(scrollPane, BorderLayout.CENTER);

        // ===== Form (Patient-style multi-column) =====
        JPanel formPanel = createFormPanel();
        formPanel.setBorder(BorderFactory.createTitledBorder("Clinician Details"));
        formPanel.setBackground(new Color(250, 250, 250));
        add(formPanel, BorderLayout.SOUTH);
    }

    private JPanel createFormPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(true);
        panel.setBackground(new Color(250, 250, 250));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        int row = 0;

        // Row 0: Clinician ID | First | Last
        addTriple(panel, gbc, row++,
                "Clinician ID:", clinicianIDField = new JTextField(15),
                "First Name:", firstNameField = new JTextField(15),
                "Last Name:", lastNameField = new JTextField(15)
        );

        // Row 1: Qualification | Specialty | GMC
        addTriple(panel, gbc, row++,
                "Qualification:", qualificationField = new JTextField(15),
                "Specialty:", specialtyField = new JTextField(15),
                "GMC Number:", gmcNumberField = new JTextField(15)
        );

        // Row 2: Workplace | Workplace Type | Employment
        addTriple(panel, gbc, row++,
                "Workplace:", workplaceField = new JTextField(15),
                "Workplace Type:", workplaceTypeField = new JTextField(15),
                "Employment Status:", employmentStatusField = new JTextField(15)
        );

        // Row 3: Start Date | Email | Phone
        addTriple(panel, gbc, row++,
                "Start Date:", startDateField = new JTextField(15),
                "Email:", emailField = new JTextField(15),
                "Phone:", phoneField = new JTextField(15)
        );

        return panel;
    }

    private void addTriple(JPanel panel, GridBagConstraints gbc, int row,
                           String l1, JTextField f1,
                           String l2, JTextField f2,
                           String l3, JTextField f3) {

        gbc.gridy = row;

        gbc.gridx = 0; panel.add(new JLabel(l1), gbc);
        gbc.gridx = 1; panel.add(f1, gbc);

        gbc.gridx = 2; panel.add(new JLabel(l2), gbc);
        gbc.gridx = 3; panel.add(f2, gbc);

        gbc.gridx = 4; panel.add(new JLabel(l3), gbc);
        gbc.gridx = 5; panel.add(f3, gbc);
    }

    // ===== Buttons (Patient style) =====
    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));

        JButton addBtn = createPrimaryButton("Add");
        addBtn.addActionListener(e -> addClinician());

        JButton updateBtn = createPrimaryButton("Update");
        updateBtn.addActionListener(e -> updateClinician());

        JButton deleteBtn = createDangerButton("Delete");
        deleteBtn.addActionListener(e -> deleteClinician());

        JButton clearBtn = createSecondaryButton("Clear");
        clearBtn.addActionListener(e -> clearForm());

        JButton refreshBtn = createSecondaryButton("Refresh");
        refreshBtn.addActionListener(e -> refreshData());

        panel.add(addBtn);
        panel.add(updateBtn);
        panel.add(deleteBtn);
        panel.add(clearBtn);
        panel.add(refreshBtn);

        return panel;
    }

    private JButton createPrimaryButton(String text) {
        JButton b = new JButton(text);
        styleButton(b, new Color(33, 150, 243), Color.WHITE);
        return b;
    }

    private JButton createSecondaryButton(String text) {
        JButton b = new JButton(text);
        styleButton(b, new Color(236, 239, 241), new Color(55, 71, 79));
        return b;
    }

    private JButton createDangerButton(String text) {
        JButton b = new JButton(text);
        styleButton(b, new Color(229, 57, 53), Color.WHITE);
        return b;
    }

    private void styleButton(JButton button, Color background, Color foreground) {
        button.setBackground(background);
        button.setForeground(foreground);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(6, 14, 6, 14));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setFont(button.getFont().deriveFont(Font.BOLD, 12f));
    }

    // ===== CRUD =====
    private void addClinician() {
        try {
            Clinician c = createClinicianFromForm();
            if (c != null) {
                controller.addClinician(c);
                refreshData();
                clearForm();
                JOptionPane.showMessageDialog(this, "Clinician added successfully!");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateClinician() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Please select a clinician.");
            return;
        }
        try {
            String id = (String) tableModel.getValueAt(row, 0);
            controller.deleteClinician(id);
            controller.addClinician(createClinicianFromForm());
            refreshData();
            clearForm();
            JOptionPane.showMessageDialog(this, "Clinician updated!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteClinician() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Please select a clinician.");
            return;
        }
        if (JOptionPane.showConfirmDialog(this, "Delete this clinician?", "Confirm",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            controller.deleteClinician((String) tableModel.getValueAt(row, 0));
            refreshData();
            clearForm();
        }
    }

    private Clinician createClinicianFromForm() {
        if (clinicianIDField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Clinician ID required.");
            return null;
        }
        return new Clinician(
                clinicianIDField.getText().trim(),
                firstNameField.getText().trim(),
                lastNameField.getText().trim(),
                qualificationField.getText().trim(),
                specialtyField.getText().trim(),
                gmcNumberField.getText().trim(),
                workplaceField.getText().trim(),
                workplaceTypeField.getText().trim(),
                employmentStatusField.getText().trim(),
                startDateField.getText().trim(),
                emailField.getText().trim(),
                phoneField.getText().trim()
        );
    }

    private void loadSelectedClinician() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            clinicianIDField.setText((String) tableModel.getValueAt(row, 0));
            firstNameField.setText((String) tableModel.getValueAt(row, 1));
            lastNameField.setText((String) tableModel.getValueAt(row, 2));
            qualificationField.setText((String) tableModel.getValueAt(row, 3));
            specialtyField.setText((String) tableModel.getValueAt(row, 4));
            gmcNumberField.setText((String) tableModel.getValueAt(row, 5));
            workplaceField.setText((String) tableModel.getValueAt(row, 6));
            workplaceTypeField.setText((String) tableModel.getValueAt(row, 7));
            employmentStatusField.setText((String) tableModel.getValueAt(row, 8));
            startDateField.setText((String) tableModel.getValueAt(row, 9));
            emailField.setText((String) tableModel.getValueAt(row, 10));
            phoneField.setText((String) tableModel.getValueAt(row, 11));
        }
    }

    private void clearForm() {
        clinicianIDField.setText("");
        firstNameField.setText("");
        lastNameField.setText("");
        qualificationField.setText("");
        specialtyField.setText("");
        gmcNumberField.setText("");
        workplaceField.setText("");
        workplaceTypeField.setText("");
        employmentStatusField.setText("");
        startDateField.setText("");
        emailField.setText("");
        phoneField.setText("");
    }

    public void refreshData() {
        tableModel.setRowCount(0);
        List<Clinician> list = controller.getAllClinicians();
        for (Clinician c : list) {
            tableModel.addRow(new Object[]{
                    c.getClinicianID(), c.getFirstName(), c.getLastName(),
                    c.getQualification(), c.getSpecialty(), c.getGmcNumber(),
                    c.getWorkplace(), c.getWorkplaceType(),
                    c.getEmploymentStatus(), c.getStartDate(),
                    c.getEmail(), c.getPhone()
            });
        }
    }
}
