package com.healthcare.view;

import com.healthcare.controller.HealthcareController;
import com.healthcare.model.Appointment;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;

public class AppointmentPanel extends JPanel {

    private final HealthcareController controller;

    private JTable table;
    private DefaultTableModel tableModel;

    private JTextField appointmentIDField, patientIDField, clinicianIDField, facilityIDField;
    private JTextField dateField, timeField, durationField, typeField, statusField;
    private JTextField reasonField, notesField, createdField, lastModifiedField;

    private JButton addBtn, updateBtn, deleteBtn, clearBtn, refreshBtn;

    public AppointmentPanel(HealthcareController controller) {
        this.controller = controller;
        initializePanel();
        refreshData();
        clearFields(); // ✅ ensure blank on open
    }

    private void initializePanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setBackground(new Color(245, 247, 250));

        add(buildButtonPanel(), BorderLayout.NORTH);
        add(buildTablePanel(), BorderLayout.CENTER);
        add(buildDetailsPanel(), BorderLayout.SOUTH);
    }

    private JPanel buildButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        panel.setBackground(new Color(245, 247, 250));

        addBtn = createPrimaryButton("Add");
        updateBtn = createPrimaryButton("Update");
        deleteBtn = createDangerButton("Delete");
        clearBtn = createSecondaryButton("Clear");
        refreshBtn = createSecondaryButton("Refresh");

        panel.add(addBtn);
        panel.add(updateBtn);
        panel.add(deleteBtn);
        panel.add(clearBtn);
        panel.add(refreshBtn);

        addBtn.addActionListener(e -> onAdd());
        updateBtn.addActionListener(e -> onUpdate());
        deleteBtn.addActionListener(e -> onDelete());
        clearBtn.addActionListener(e -> clearFields());
        refreshBtn.addActionListener(e -> refreshData());

        return panel;
    }

    private JScrollPane buildTablePanel() {
        String[] cols = {
                "Appointment ID", "Patient ID", "Clinician ID", "Facility ID",
                "Date", "Time", "Duration", "Type", "Status", "Reason", "Notes",
                "Created", "Last Modified"
        };

        tableModel = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int row, int col) { return false; }
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

        table.getSelectionModel().addListSelectionListener(this::onRowSelected);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Appointments"));
        return scrollPane;
    }

    private JPanel buildDetailsPanel() {
        JPanel outer = new JPanel(new GridBagLayout());
        outer.setBorder(BorderFactory.createTitledBorder("Appointment Details"));
        outer.setBackground(new Color(250, 250, 250));
        outer.setOpaque(true);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        appointmentIDField = createNormalField(15);
        patientIDField     = createNormalField(15);
        clinicianIDField   = createNormalField(15);

        facilityIDField = createNormalField(15);
        dateField       = createNormalField(15);
        timeField       = createNormalField(15);

        durationField = createNormalField(15);
        typeField     = createNormalField(15);
        statusField   = createNormalField(15);

        reasonField = createNormalField(15);
        notesField  = createNormalField(15);

        createdField      = createReadOnlyField(15);
        lastModifiedField = createReadOnlyField(15);

        int row = 0;
        addField(outer, gbc, row, 0, "Appointment ID:", appointmentIDField);
        addField(outer, gbc, row, 2, "Patient ID:", patientIDField);
        addField(outer, gbc, row, 4, "Clinician ID:", clinicianIDField);

        row++;
        addField(outer, gbc, row, 0, "Facility ID:", facilityIDField);
        addField(outer, gbc, row, 2, "Date:", dateField);
        addField(outer, gbc, row, 4, "Time:", timeField);

        row++;
        addField(outer, gbc, row, 0, "Duration (min):", durationField);
        addField(outer, gbc, row, 2, "Type:", typeField);
        addField(outer, gbc, row, 4, "Status:", statusField);

        row++;
        addField(outer, gbc, row, 0, "Reason:", reasonField);
        addField(outer, gbc, row, 2, "Notes:", notesField);
        addField(outer, gbc, row, 4, "Created:", createdField);

        row++;
        addField(outer, gbc, row, 0, "Last Modified:", lastModifiedField);

        return outer;
    }

    private JTextField createNormalField(int cols) {
        JTextField f = new JTextField(cols);
        f.setBackground(Color.WHITE);
        f.setForeground(new Color(55, 71, 79));
        f.setOpaque(true);
        return f;
    }

    private JTextField createReadOnlyField(int cols) {
        JTextField f = createNormalField(cols);
        f.setEditable(false);
        f.setBackground(Color.WHITE);
        f.setDisabledTextColor(new Color(55, 71, 79));
        return f;
    }

    private void addField(JPanel panel, GridBagConstraints gbc, int row, int col,
                          String label, JTextField field) {
        gbc.gridx = col;
        gbc.gridy = row;
        panel.add(new JLabel(label), gbc);

        gbc.gridx = col + 1;
        panel.add(field, gbc);
    }

    private void onRowSelected(ListSelectionEvent e) {
        if (e.getValueIsAdjusting()) return;

        int row = table.getSelectedRow();
        if (row < 0) {          // ✅ NEW: if nothing selected -> blank form
            clearFields();
            return;
        }

        appointmentIDField.setText(val(row, 0));
        patientIDField.setText(val(row, 1));
        clinicianIDField.setText(val(row, 2));
        facilityIDField.setText(val(row, 3));
        dateField.setText(val(row, 4));
        timeField.setText(val(row, 5));
        durationField.setText(val(row, 6));
        typeField.setText(val(row, 7));
        statusField.setText(val(row, 8));
        reasonField.setText(val(row, 9));
        notesField.setText(val(row, 10));
        createdField.setText(val(row, 11));
        lastModifiedField.setText(val(row, 12));
    }

    private String val(int row, int col) {
        Object o = tableModel.getValueAt(row, col);
        return o == null ? "" : o.toString();
    }

    public void refreshData() {
        tableModel.setRowCount(0);

        List<Appointment> list = null;
        try { list = controller.getAllAppointments(); } catch (Exception ignored) {}
        if (list == null) list = Collections.emptyList();

        for (Appointment a : list) {
            String id = getStrSmart(a, "getAppointmentID","getAppointmentId","getId","appointmentID","appointmentId","id");
            String pid = getStrSmart(a, "getPatientID","getPatientId","patientID","patientId");
            String cid = getStrSmart(a, "getClinicianID","getClinicianId","clinicianID","clinicianId");
            String fid = getStrSmart(a, "getFacilityID","getFacilityId","facilityID","facilityId");

            String date = getStrSmart(a, "getDate","getAppointmentDate","date","appointmentDate");
            String time = getStrSmart(a, "getTime","getAppointmentTime","time","appointmentTime");

            String dur = getStrSmart(a, "getDuration","getDurationMinutes","duration","durationMinutes");
            String type = getStrSmart(a, "getType","getAppointmentType","type","appointmentType");
            String status = getStrSmart(a, "getStatus","status");
            String reason = getStrSmart(a, "getReason","reason");
            String notes = getStrSmart(a, "getNotes","notes");

            String created = getStrSmart(a, "getCreated","getCreatedAt","getCreatedDate","created","createdAt","createdDate");
            String lastMod = getStrSmart(a, "getLastUpdated","getLastModified","updatedAt","lastUpdated","lastModified");

            tableModel.addRow(new Object[]{
                    id, pid, cid, fid, date, time, dur, type, status, reason, notes, created, lastMod
            });
        }

        // ✅ NEW: no auto-select first row
        table.clearSelection();
        clearFields();
    }

    // ---------------- BUTTONS ----------------

    private void onAdd() {
        try {
            Appointment appt = buildAppointmentObject();
            controller.addAppointment(appt);
            refreshData();
            clearFields();
            JOptionPane.showMessageDialog(this, "Appointment added successfully!", "Message", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Add failed: " + ex.getMessage(), "Message", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onUpdate() {
        try {
            int row = table.getSelectedRow();
            if (row < 0) {
                JOptionPane.showMessageDialog(this, "Please select an appointment.", "Message", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String id = appointmentIDField.getText().trim();
            if (id.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Appointment ID is required.", "Message", JOptionPane.WARNING_MESSAGE);
                return;
            }

            controller.deleteAppointment(id);

            Appointment appt = buildAppointmentObject();
            controller.addAppointment(appt);

            refreshData();
            JOptionPane.showMessageDialog(this, "Appointment updated successfully!", "Message", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Update failed: " + ex.getMessage(), "Message", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onDelete() {
        try {
            int row = table.getSelectedRow();
            if (row < 0) {
                JOptionPane.showMessageDialog(this, "Please select an appointment.", "Message", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String id = appointmentIDField.getText().trim();
            if (id.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Appointment ID missing.", "Message", JOptionPane.WARNING_MESSAGE);
                return;
            }

            controller.deleteAppointment(id);
            refreshData();
            clearFields();
            JOptionPane.showMessageDialog(this, "Appointment deleted successfully!", "Message", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Delete failed: " + ex.getMessage(), "Message", JOptionPane.ERROR_MESSAGE);
        }
    }

    private Appointment buildAppointmentObject() {
        String[] args = new String[]{
                appointmentIDField.getText().trim(),
                patientIDField.getText().trim(),
                clinicianIDField.getText().trim(),
                facilityIDField.getText().trim(),
                dateField.getText().trim(),
                timeField.getText().trim(),
                durationField.getText().trim(),
                typeField.getText().trim(),
                statusField.getText().trim(),
                reasonField.getText().trim(),
                notesField.getText().trim()
        };

        try {
            for (Constructor<?> c : Appointment.class.getConstructors()) {
                Class<?>[] p = c.getParameterTypes();
                if (p.length != args.length) continue;

                boolean allString = true;
                for (Class<?> t : p) {
                    if (t != String.class) { allString = false; break; }
                }
                if (!allString) continue;

                return (Appointment) c.newInstance((Object[]) args);
            }
        } catch (Exception ignored) {}

        try {
            Appointment a = Appointment.class.getDeclaredConstructor().newInstance();
            setIfExists(a, "appointmentID", args[0]);
            setIfExists(a, "patientID", args[1]);
            setIfExists(a, "clinicianID", args[2]);
            setIfExists(a, "facilityID", args[3]);
            setIfExists(a, "date", args[4]);
            setIfExists(a, "time", args[5]);
            setIfExists(a, "duration", args[6]);
            setIfExists(a, "type", args[7]);
            setIfExists(a, "status", args[8]);
            setIfExists(a, "reason", args[9]);
            setIfExists(a, "notes", args[10]);
            return a;
        } catch (Exception ignored) {}

        throw new RuntimeException("Appointment constructor not matched. Please check Appointment.java constructors/fields.");
    }

    private void setIfExists(Object obj, String fieldName, String value) {
        try {
            Field f = obj.getClass().getDeclaredField(fieldName);
            f.setAccessible(true);
            f.set(obj, value);
        } catch (Exception ignored) {}
    }

    private void clearFields() {
        appointmentIDField.setText("");
        patientIDField.setText("");
        clinicianIDField.setText("");
        facilityIDField.setText("");
        dateField.setText("");
        timeField.setText("");
        durationField.setText("");
        typeField.setText("");
        statusField.setText("");
        reasonField.setText("");
        notesField.setText("");
        createdField.setText("");
        lastModifiedField.setText("");
        table.clearSelection();
    }

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

    private String getStrSmart(Object obj, String... names) {
        if (obj == null) return "";
        for (String n : names) {
            Object v = null;

            if (n != null && n.startsWith("get")) v = invokeNoArg(obj, n);
            if (v == null) v = readField(obj, n);

            if (v != null) return v.toString();
        }
        return "";
    }

    private Object invokeNoArg(Object obj, String method) {
        try {
            Method m = obj.getClass().getMethod(method);
            return m.invoke(obj);
        } catch (Exception ignored) {}
        return null;
    }

    private Object readField(Object obj, String fieldName) {
        if (fieldName == null || fieldName.isEmpty()) return null;
        Class<?> c = obj.getClass();
        while (c != null) {
            try {
                Field f = c.getDeclaredField(fieldName);
                f.setAccessible(true);
                return f.get(obj);
            } catch (Exception ignored) {
                c = c.getSuperclass();
            }
        }
        return null;
    }
}
