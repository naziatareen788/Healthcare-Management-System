package com.healthcare.view;

import com.healthcare.controller.HealthcareController;
import com.healthcare.model.Staff;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;

public class StaffPanel extends JPanel {

    private final HealthcareController controller;
    private JTable table;
    private DefaultTableModel tableModel;

    private JTextField staffIDField, firstNameField, lastNameField, roleField, departmentField;
    private JTextField facilityIDField, emailField, phoneField, employmentStatusField;
    private JTextField startDateField, lineManagerField, accessLevelField;

    public StaffPanel(HealthcareController controller) {
        this.controller = controller;
        initializePanel();
        refreshData();
    }

    private void initializePanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setBackground(new Color(245, 247, 250));

        JPanel buttonPanel = createButtonPanel();
        buttonPanel.setBackground(new Color(245, 247, 250));
        add(buttonPanel, BorderLayout.NORTH);

        String[] columns = {
                "Staff ID", "First Name", "Last Name", "Role", "Department", "Facility ID",
                "Email", "Phone", "Employment Status", "Start Date", "Line Manager", "Access Level"
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
            if (!e.getValueIsAdjusting()) loadSelectedStaff();
        });

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Staff"));
        add(scrollPane, BorderLayout.CENTER);

        JPanel formPanel = createFormPanel();
        formPanel.setBorder(BorderFactory.createTitledBorder("Staff Details"));
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

        // Row0: StaffID | First | Last
        addTriple(panel, gbc, row++,
                "Staff ID:", staffIDField = new JTextField(15),
                "First Name:", firstNameField = new JTextField(15),
                "Last Name:", lastNameField = new JTextField(15)
        );

        // Row1: Role | Department | FacilityID
        addTriple(panel, gbc, row++,
                "Role:", roleField = new JTextField(15),
                "Department:", departmentField = new JTextField(15),
                "Facility ID:", facilityIDField = new JTextField(15)
        );

        // Row2: Email | Phone | Employment
        addTriple(panel, gbc, row++,
                "Email:", emailField = new JTextField(15),
                "Phone:", phoneField = new JTextField(15),
                "Employment Status:", employmentStatusField = new JTextField(15)
        );

        // Row3: StartDate | LineManager | AccessLevel
        addTriple(panel, gbc, row++,
                "Start Date:", startDateField = new JTextField(15),
                "Line Manager:", lineManagerField = new JTextField(15),
                "Access Level:", accessLevelField = new JTextField(15)
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

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));

        JButton addBtn = createPrimaryButton("Add");
        addBtn.addActionListener(e -> addStaff());

        JButton updateBtn = createPrimaryButton("Update");
        updateBtn.addActionListener(e -> updateStaff());

        JButton deleteBtn = createDangerButton("Delete");
        deleteBtn.addActionListener(e -> deleteStaff());

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

    private void addStaff() {
        try {
            Staff s = createStaffFromForm();
            if (s != null) {
                controller.addStaff(s);
                refreshData();
                clearForm();
                JOptionPane.showMessageDialog(this, "Staff added!");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateStaff() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Please select a staff member.");
            return;
        }
        try {
            String id = (String) tableModel.getValueAt(row, 0);
            controller.deleteStaff(id);
            controller.addStaff(createStaffFromForm());
            refreshData();
            clearForm();
            JOptionPane.showMessageDialog(this, "Staff updated!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteStaff() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Please select a staff member.");
            return;
        }
        if (JOptionPane.showConfirmDialog(this, "Delete this staff member?", "Confirm",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            controller.deleteStaff((String) tableModel.getValueAt(row, 0));
            refreshData();
            clearForm();
        }
    }

    private Staff createStaffFromForm() {
        if (staffIDField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Staff ID required.");
            return null;
        }
        return new Staff(
                staffIDField.getText().trim(),
                firstNameField.getText().trim(),
                lastNameField.getText().trim(),
                roleField.getText().trim(),
                departmentField.getText().trim(),
                facilityIDField.getText().trim(),
                emailField.getText().trim(),
                phoneField.getText().trim(),
                employmentStatusField.getText().trim(),
                startDateField.getText().trim(),
                lineManagerField.getText().trim(),
                accessLevelField.getText().trim()
        );
    }

    private void loadSelectedStaff() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            staffIDField.setText((String) tableModel.getValueAt(row, 0));
            firstNameField.setText((String) tableModel.getValueAt(row, 1));
            lastNameField.setText((String) tableModel.getValueAt(row, 2));
            roleField.setText((String) tableModel.getValueAt(row, 3));
            departmentField.setText((String) tableModel.getValueAt(row, 4));
            facilityIDField.setText((String) tableModel.getValueAt(row, 5));
            emailField.setText((String) tableModel.getValueAt(row, 6));
            phoneField.setText((String) tableModel.getValueAt(row, 7));
            employmentStatusField.setText((String) tableModel.getValueAt(row, 8));
            startDateField.setText((String) tableModel.getValueAt(row, 9));
            lineManagerField.setText((String) tableModel.getValueAt(row, 10));
            accessLevelField.setText((String) tableModel.getValueAt(row, 11));
        }
    }

    private void clearForm() {
        staffIDField.setText("");
        firstNameField.setText("");
        lastNameField.setText("");
        roleField.setText("");
        departmentField.setText("");
        facilityIDField.setText("");
        emailField.setText("");
        phoneField.setText("");
        employmentStatusField.setText("");
        startDateField.setText("");
        lineManagerField.setText("");
        accessLevelField.setText("");
    }

    public void refreshData() {
        tableModel.setRowCount(0);
        List<Staff> list = controller.getAllStaff();
        for (Staff s : list) {
            tableModel.addRow(new Object[]{
                    s.getStaffID(), s.getFirstName(), s.getLastName(),
                    s.getRole(), s.getDepartment(), s.getFacilityID(),
                    s.getEmail(), s.getPhone(),
                    s.getEmploymentStatus(), s.getStartDate(),
                    s.getLineManager(), s.getAccessLevel()
            });
        }
    }
}
