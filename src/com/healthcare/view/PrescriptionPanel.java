package com.healthcare.view;

import com.healthcare.controller.HealthcareController;
import com.healthcare.model.Prescription;

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

public class PrescriptionPanel extends JPanel {

    private final HealthcareController controller;

    private JTable table;
    private DefaultTableModel tableModel;

    private JTextField prescriptionIDField;
    private JTextField patientIDField;
    private JTextField clinicianIDField;
    private JTextField appointmentIDField;

    private JTextField medicationField;
    private JTextField dosageField;
    private JTextField frequencyField;

    private JTextField durationDaysField;
    private JTextField quantityField;
    private JTextField pharmacyField;

    private JTextField datePrescribedField;
    private JTextField issueDateField;
    private JTextField collectionDateField;
    private JTextField collectionStatusField;

    private JTextField notesField;

    private JButton addBtn, updateBtn, deleteBtn, clearBtn, refreshBtn;

    public PrescriptionPanel(HealthcareController controller) {
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
                "Prescription ID", "Patient ID", "Clinician ID", "Appointment ID",
                "Medication", "Dosage", "Frequency",
                "Duration Days", "Quantity", "Pharmacy",
                "Date Prescribed", "Issue Date", "Collection Date", "Collection Status",
                "Notes"
        };

        tableModel = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
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

        JScrollPane scroll = new JScrollPane(table);
        scroll.getViewport().setBackground(Color.WHITE);
        scroll.setBorder(BorderFactory.createTitledBorder("Prescriptions"));
        return scroll;
    }

    // ✅ COMPACT form (AppointmentPanel style)
    private JPanel buildDetailsPanel() {
        JPanel outer = new JPanel(new GridBagLayout());
        outer.setBorder(BorderFactory.createTitledBorder("Prescription Details"));
        outer.setBackground(new Color(250, 250, 250));
        outer.setOpaque(true);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        prescriptionIDField = createNormalField(15);
        patientIDField      = createNormalField(15);
        clinicianIDField    = createNormalField(15);

        appointmentIDField  = createNormalField(15);
        medicationField     = createNormalField(15);
        dosageField         = createNormalField(15);

        frequencyField      = createNormalField(15);
        durationDaysField   = createNormalField(15);
        quantityField       = createNormalField(15);

        pharmacyField       = createNormalField(15);
        datePrescribedField = createNormalField(15);
        issueDateField      = createNormalField(15);

        collectionDateField   = createNormalField(15);
        collectionStatusField = createNormalField(15);
        notesField            = createNormalField(15);

        int row = 0;
        addField(outer, gbc, row, 0, "Prescription ID:", prescriptionIDField);
        addField(outer, gbc, row, 2, "Patient ID:", patientIDField);
        addField(outer, gbc, row, 4, "Clinician ID:", clinicianIDField);

        row++;
        addField(outer, gbc, row, 0, "Appointment ID:", appointmentIDField);
        addField(outer, gbc, row, 2, "Medication:", medicationField);
        addField(outer, gbc, row, 4, "Dosage:", dosageField);

        row++;
        addField(outer, gbc, row, 0, "Frequency:", frequencyField);
        addField(outer, gbc, row, 2, "Duration Days:", durationDaysField);
        addField(outer, gbc, row, 4, "Quantity:", quantityField);

        row++;
        addField(outer, gbc, row, 0, "Pharmacy:", pharmacyField);
        addField(outer, gbc, row, 2, "Date Prescribed:", datePrescribedField);
        addField(outer, gbc, row, 4, "Issue Date:", issueDateField);

        row++;
        addField(outer, gbc, row, 0, "Collection Date:", collectionDateField);
        addField(outer, gbc, row, 2, "Collection Status:", collectionStatusField);
        addField(outer, gbc, row, 4, "Notes:", notesField);

        return outer;
    }

    private void addField(JPanel panel, GridBagConstraints gbc, int row, int col,
                          String label, JTextField field) {
        gbc.gridx = col;
        gbc.gridy = row;
        panel.add(new JLabel(label), gbc);

        gbc.gridx = col + 1;
        panel.add(field, gbc);
    }

    private JTextField createNormalField(int cols) {
        JTextField f = new JTextField(cols);
        f.setBackground(Color.WHITE);
        f.setForeground(new Color(55, 71, 79));
        f.setOpaque(true);
        return f;
    }

    private void onRowSelected(ListSelectionEvent e) {
        if (e.getValueIsAdjusting()) return;

        int r = table.getSelectedRow();
        if (r < 0) { // ✅ if selection cleared -> blank form
            clearFields();
            return;
        }

        prescriptionIDField.setText(val(r, 0));
        patientIDField.setText(val(r, 1));
        clinicianIDField.setText(val(r, 2));
        appointmentIDField.setText(val(r, 3));

        medicationField.setText(val(r, 4));
        dosageField.setText(val(r, 5));
        frequencyField.setText(val(r, 6));

        durationDaysField.setText(val(r, 7));
        quantityField.setText(val(r, 8));
        pharmacyField.setText(val(r, 9));

        datePrescribedField.setText(val(r, 10));
        issueDateField.setText(val(r, 11));
        collectionDateField.setText(val(r, 12));
        collectionStatusField.setText(val(r, 13));

        notesField.setText(val(r, 14));
    }

    private String val(int row, int col) {
        Object o = tableModel.getValueAt(row, col);
        return o == null ? "" : o.toString();
    }

    public void refreshData() {
        tableModel.setRowCount(0);

        List<Prescription> list = getListFromController(controller,
                new String[]{"getPrescriptions","getAllPrescriptions","getPrescriptionList","getPrescriptionsList","getAllPrescriptions"},
                new String[]{"getDataManager","getData","getStore"},
                new String[]{"getPrescriptions","getAllPrescriptions","getPrescriptionList","getPrescriptionsList"}
        );

        if (list == null) list = Collections.emptyList();

        for (Prescription p : list) {
            String rxId = getStrSmart(p, "getPrescriptionID","getPrescriptionId","getId","prescriptionID","prescriptionId","id");
            String pid  = getStrSmart(p, "getPatientID","getPatientId","patientID","patientId","patient");
            String cid  = getStrSmart(p, "getClinicianID","getClinicianId","clinicianID","clinicianId","clinician");
            String appt = getStrSmart(p, "getAppointmentID","getAppointmentId","appointmentID","appointmentId","appointment");

            String med  = getStrSmart(p, "getMedication","getMedicine","getDrugName","medication","medicine","drugName");
            String dose = getStrSmart(p, "getDosage","getDose","dosage","dose");
            String freq = getStrSmart(p, "getFrequency","frequency");

            String dur  = getStrSmart(p, "getDurationDays","getDuration","durationDays","duration");
            String qty  = getStrSmart(p, "getQuantity","quantity","qty","getQty");
            String pharm= getStrSmart(p, "getPharmacy","pharmacy","getPharmacyName","pharmacyName");

            String dp   = getStrSmart(p, "getDatePrescribed","getPrescribedDate","datePrescribed","prescribedDate");
            String issue= getStrSmart(p, "getIssueDate","issueDate");
            String colD = getStrSmart(p, "getCollectionDate","collectionDate");
            String colS = getStrSmart(p, "getCollectionStatus","collectionStatus");

            String notes= getStrSmart(p, "getNotes","getComment","getRemarks","notes","comment","remarks");

            tableModel.addRow(new Object[]{
                    rxId, pid, cid, appt,
                    med, dose, freq,
                    dur, qty, pharm,
                    dp, issue, colD, colS,
                    notes
            });
        }

        // ✅ IMPORTANT: no auto select first row
        table.clearSelection();
        clearFields();
    }

    // ✅ BUTTONS
    private void onAdd() {
        try {
            Prescription obj = buildPrescriptionObject();
            Method add = controller.getClass().getMethod("addPrescription", Prescription.class);
            add.invoke(controller, obj);

            refreshData();
            clearFields();
            JOptionPane.showMessageDialog(this, "Prescription added successfully!", "Message", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Add failed: " + ex.getMessage(), "Message", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onUpdate() {
        try {
            int row = table.getSelectedRow();
            if (row < 0) {
                JOptionPane.showMessageDialog(this, "Please select a prescription.", "Message", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String id = prescriptionIDField.getText().trim();
            if (id.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Prescription ID missing.", "Message", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Method del = controller.getClass().getMethod("deletePrescription", String.class);
            del.invoke(controller, id);

            Prescription obj = buildPrescriptionObject();
            Method add = controller.getClass().getMethod("addPrescription", Prescription.class);
            add.invoke(controller, obj);

            refreshData();
            JOptionPane.showMessageDialog(this, "Prescription updated successfully!", "Message", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Update failed: " + ex.getMessage(), "Message", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onDelete() {
        try {
            int row = table.getSelectedRow();
            if (row < 0) {
                JOptionPane.showMessageDialog(this, "Please select a prescription.", "Message", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String id = prescriptionIDField.getText().trim();
            if (id.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Select a prescription first.", "Message", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Method del = controller.getClass().getMethod("deletePrescription", String.class);
            del.invoke(controller, id);

            refreshData();
            clearFields();
            JOptionPane.showMessageDialog(this, "Prescription deleted successfully!", "Message", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Delete failed: " + ex.getMessage(), "Message", JOptionPane.ERROR_MESSAGE);
        }
    }

    private Prescription buildPrescriptionObject() throws Exception {
        String[] a = new String[] {
                prescriptionIDField.getText().trim(),
                patientIDField.getText().trim(),
                clinicianIDField.getText().trim(),
                appointmentIDField.getText().trim(),
                medicationField.getText().trim(),
                dosageField.getText().trim(),
                frequencyField.getText().trim(),
                durationDaysField.getText().trim(),
                quantityField.getText().trim(),
                pharmacyField.getText().trim(),
                datePrescribedField.getText().trim(),
                issueDateField.getText().trim(),
                collectionDateField.getText().trim(),
                collectionStatusField.getText().trim(),
                notesField.getText().trim()
        };

        // 1) try constructor with all String params
        for (Constructor<?> c : Prescription.class.getConstructors()) {
            Class<?>[] p = c.getParameterTypes();
            if (p.length != a.length) continue;
            boolean allString = true;
            for (Class<?> t : p) {
                if (t != String.class) { allString = false; break; }
            }
            if (allString) return (Prescription) c.newInstance((Object[]) a);
        }

        // 2) default ctor + set fields/setters
        Prescription obj = Prescription.class.getDeclaredConstructor().newInstance();

        smartSet(obj, new String[]{"prescriptionID","prescriptionId","id"}, a[0]);
        smartSet(obj, new String[]{"patientID","patientId","patient"}, a[1]);
        smartSet(obj, new String[]{"clinicianID","clinicianId","clinician"}, a[2]);
        smartSet(obj, new String[]{"appointmentID","appointmentId","appointment"}, a[3]);

        smartSet(obj, new String[]{"medication","medicine","drugName"}, a[4]);
        smartSet(obj, new String[]{"dosage","dose"}, a[5]);
        smartSet(obj, new String[]{"frequency"}, a[6]);

        smartSet(obj, new String[]{"durationDays","duration"}, a[7]);
        smartSet(obj, new String[]{"quantity","qty"}, a[8]);
        smartSet(obj, new String[]{"pharmacy","pharmacyName"}, a[9]);

        smartSet(obj, new String[]{"datePrescribed","prescribedDate"}, a[10]);
        smartSet(obj, new String[]{"issueDate"}, a[11]);
        smartSet(obj, new String[]{"collectionDate"}, a[12]);
        smartSet(obj, new String[]{"collectionStatus"}, a[13]);

        smartSet(obj, new String[]{"notes","comment","remarks"}, a[14]);

        return obj;
    }

    private void smartSet(Object obj, String[] possibleNames, String value) {
        if (obj == null) return;

        // setters
        for (String n : possibleNames) {
            String setter = "set" + Character.toUpperCase(n.charAt(0)) + n.substring(1);
            try {
                Method m = obj.getClass().getMethod(setter, String.class);
                m.invoke(obj, value);
                return;
            } catch (Exception ignored) {}
        }

        // fields
        for (String n : possibleNames) {
            Class<?> c = obj.getClass();
            while (c != null) {
                try {
                    Field f = c.getDeclaredField(n);
                    f.setAccessible(true);
                    f.set(obj, value);
                    return;
                } catch (Exception ignored) {
                    c = c.getSuperclass();
                }
            }
        }
    }

    private void clearFields() {
        prescriptionIDField.setText("");
        patientIDField.setText("");
        clinicianIDField.setText("");
        appointmentIDField.setText("");

        medicationField.setText("");
        dosageField.setText("");
        frequencyField.setText("");

        durationDaysField.setText("");
        quantityField.setText("");
        pharmacyField.setText("");

        datePrescribedField.setText("");
        issueDateField.setText("");
        collectionDateField.setText("");
        collectionStatusField.setText("");

        notesField.setText("");

        if (table != null) table.clearSelection();
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

    // -------- SMART GETTERS (TABLE) --------

    private String getStrSmart(Object obj, String... names) {
        if (obj == null) return "";
        for (String n : names) {
            Object v = null;

            if (n != null && n.startsWith("get")) v = invokeNoArg(obj, n);
            if (v == null) v = readField(obj, n);

            if (v != null && !(v instanceof String) && !(v instanceof Number) && !(v instanceof Boolean)) {
                Object nestedId = invokeNoArg(v, "getId");
                if (nestedId != null) return nestedId.toString();
                return v.toString();
            }
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

    @SuppressWarnings("unchecked")
    private <T> List<T> getListFromController(Object controllerObj,
                                              String[] directMethods,
                                              String[] midGetterMethods,
                                              String[] dataMethods) {
        if (controllerObj == null) return Collections.emptyList();

        List<T> direct = (List<T>) invokeFirstList(controllerObj, directMethods);
        if (direct != null) return direct;

        Object dataObj = invokeFirstObj(controllerObj, midGetterMethods);
        if (dataObj != null) {
            List<T> nested = (List<T>) invokeFirstList(dataObj, dataMethods);
            if (nested != null) return nested;
        }
        return Collections.emptyList();
    }

    private Object invokeFirstObj(Object obj, String[] methodNames) {
        for (String m : methodNames) {
            try {
                Method mm = obj.getClass().getMethod(m);
                return mm.invoke(obj);
            } catch (Exception ignored) {}
        }
        return null;
    }

    private Object invokeFirstList(Object obj, String[] methodNames) {
        for (String m : methodNames) {
            try {
                Method mm = obj.getClass().getMethod(m);
                Object out = mm.invoke(obj);
                if (out instanceof List) return out;
            } catch (Exception ignored) {}
        }
        return null;
    }
}
