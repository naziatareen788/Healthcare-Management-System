package com.healthcare.view;

import com.healthcare.controller.HealthcareController;
import com.healthcare.model.Facility;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;

public class FacilityPanel extends JPanel {

    private final HealthcareController controller;
    private JTable table;
    private DefaultTableModel tableModel;

    private JTextField facilityIDField, nameField, typeField, addressField, postcodeField;
    private JTextField phoneField, emailField, openingHoursField, managerField, servicesField, capacityField;

    public FacilityPanel(HealthcareController controller) {
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
                "Facility ID", "Name", "Type", "Address", "Postcode", "Phone", "Email",
                "Opening Hours", "Manager", "Services", "Capacity"
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
            if (!e.getValueIsAdjusting()) loadSelectedFacility();
        });

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Facilities"));
        add(scrollPane, BorderLayout.CENTER);

        JPanel formPanel = createFormPanel();
        formPanel.setBorder(BorderFactory.createTitledBorder("Facility Details"));
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

        // Row 0: ID | Name | Type
        addTriple(panel, gbc, row++,
                "Facility ID:", facilityIDField = new JTextField(15),
                "Name:", nameField = new JTextField(15),
                "Type:", typeField = new JTextField(15)
        );

        // Row 1: Address | Postcode | Phone
        addTriple(panel, gbc, row++,
                "Address:", addressField = new JTextField(15),
                "Postcode:", postcodeField = new JTextField(15),
                "Phone:", phoneField = new JTextField(15)
        );

        // Row 2: Email | Opening Hours | Manager
        addTriple(panel, gbc, row++,
                "Email:", emailField = new JTextField(15),
                "Opening Hours:", openingHoursField = new JTextField(15),
                "Manager:", managerField = new JTextField(15)
        );

        // Row 3: Services | Capacity (2 fields only, third empty)
        gbc.gridy = row;
        gbc.gridx = 0; panel.add(new JLabel("Services:"), gbc);
        gbc.gridx = 1; panel.add(servicesField = new JTextField(15), gbc);
        gbc.gridx = 2; panel.add(new JLabel("Capacity:"), gbc);
        gbc.gridx = 3; panel.add(capacityField = new JTextField(15), gbc);

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
        addBtn.addActionListener(e -> addFacility());

        JButton updateBtn = createPrimaryButton("Update");
        updateBtn.addActionListener(e -> updateFacility());

        JButton deleteBtn = createDangerButton("Delete");
        deleteBtn.addActionListener(e -> deleteFacility());

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

    private void addFacility() {
        try {
            Facility f = createFacilityFromForm();
            if (f != null) {
                controller.addFacility(f);
                refreshData();
                clearForm();
                JOptionPane.showMessageDialog(this, "Facility added!");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateFacility() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Please select a facility.");
            return;
        }
        try {
            String id = (String) tableModel.getValueAt(row, 0);
            controller.deleteFacility(id);
            controller.addFacility(createFacilityFromForm());
            refreshData();
            clearForm();
            JOptionPane.showMessageDialog(this, "Facility updated!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteFacility() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Please select a facility.");
            return;
        }
        if (JOptionPane.showConfirmDialog(this, "Delete this facility?", "Confirm",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            controller.deleteFacility((String) tableModel.getValueAt(row, 0));
            refreshData();
            clearForm();
        }
    }

    private Facility createFacilityFromForm() {
        if (facilityIDField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Facility ID required.");
            return null;
        }
        return new Facility(
                facilityIDField.getText().trim(),
                nameField.getText().trim(),
                typeField.getText().trim(),
                addressField.getText().trim(),
                postcodeField.getText().trim(),
                phoneField.getText().trim(),
                emailField.getText().trim(),
                openingHoursField.getText().trim(),
                managerField.getText().trim(),
                servicesField.getText().trim(),
                capacityField.getText().trim()
        );
    }

    private void loadSelectedFacility() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            facilityIDField.setText((String) tableModel.getValueAt(row, 0));
            nameField.setText((String) tableModel.getValueAt(row, 1));
            typeField.setText((String) tableModel.getValueAt(row, 2));
            addressField.setText((String) tableModel.getValueAt(row, 3));
            postcodeField.setText((String) tableModel.getValueAt(row, 4));
            phoneField.setText((String) tableModel.getValueAt(row, 5));
            emailField.setText((String) tableModel.getValueAt(row, 6));
            openingHoursField.setText((String) tableModel.getValueAt(row, 7));
            managerField.setText((String) tableModel.getValueAt(row, 8));
            servicesField.setText((String) tableModel.getValueAt(row, 9));
            capacityField.setText((String) tableModel.getValueAt(row, 10));
        }
    }

    private void clearForm() {
        facilityIDField.setText("");
        nameField.setText("");
        typeField.setText("");
        addressField.setText("");
        postcodeField.setText("");
        phoneField.setText("");
        emailField.setText("");
        openingHoursField.setText("");
        managerField.setText("");
        servicesField.setText("");
        capacityField.setText("");
    }

    public void refreshData() {
        tableModel.setRowCount(0);
        List<Facility> list = controller.getAllFacilities();
        for (Facility f : list) {
            tableModel.addRow(new Object[]{
                    f.getFacilityID(), f.getName(), f.getType(),
                    f.getAddress(), f.getPostcode(),
                    f.getPhone(), f.getEmail(),
                    f.getOpeningHours(), f.getManagerName(),
                    f.getServices(), f.getCapacity()
            });
        }
    }
}
