package com.healthcare.view;

import com.healthcare.controller.HealthcareController;
import com.healthcare.model.Referral;

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

public class ReferralPanel extends JPanel {

    private final HealthcareController controller;

    private JTable table;
    private DefaultTableModel tableModel;

    private JTextField referralIDField;
    private JTextField patientIDField;

    private JTextField referringClinicianIDField;
    private JTextField receivingClinicianIDField;

    private JTextField referringFacilityField;
    private JTextField receivingFacilityField;

    private JTextField dateField;
    private JTextField urgencyField;

    private JTextField referralReasonField;
    private JTextField clinicalSummaryField;
    private JTextField investigationsField;

    private JTextField appointmentIDField;
    private JTextField notesField;
    private JTextField statusField;

    private JTextField createdDateField;
    private JTextField lastUpdatedField;

    private JButton addBtn, updateBtn, deleteBtn, clearBtn, refreshBtn;

    public ReferralPanel(HealthcareController controller) {
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
                "Referral ID", "Patient ID",
                "Referring Clinician ID", "Receiving Clinician ID",
                "Referring Facility", "Receiving Facility",
                "Date", "Urgency",
                "Referral Reason", "Clinical Summary", "Investigations",
                "Appointment ID", "Notes", "Status",
                "Created Date", "Last Updated"
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
        scroll.setBorder(BorderFactory.createTitledBorder("Referrals"));
        return scroll;
    }

    // ✅ COMPACT form (AppointmentPanel style)
    private JPanel buildDetailsPanel() {
        JPanel outer = new JPanel(new GridBagLayout());
        outer.setBorder(BorderFactory.createTitledBorder("Referral Details"));
        outer.setBackground(new Color(250, 250, 250));
        outer.setOpaque(true);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        referralIDField = createNormalField(15);
        patientIDField  = createNormalField(15);
        statusField     = createNormalField(15);

        referringClinicianIDField = createNormalField(15);
        receivingClinicianIDField = createNormalField(15);
        appointmentIDField        = createNormalField(15);

        referringFacilityField = createNormalField(15);
        receivingFacilityField = createNormalField(15);
        urgencyField           = createNormalField(15);

        dateField             = createNormalField(15);
        referralReasonField   = createNormalField(15);
        clinicalSummaryField  = createNormalField(15);

        investigationsField = createNormalField(15);
        notesField          = createNormalField(15);

        createdDateField = createReadOnlyField(15);
        lastUpdatedField = createReadOnlyField(15);

        int row = 0;
        addField(outer, gbc, row, 0, "Referral ID:", referralIDField);
        addField(outer, gbc, row, 2, "Patient ID:", patientIDField);
        addField(outer, gbc, row, 4, "Status:", statusField);

        row++;
        addField(outer, gbc, row, 0, "Referring Clinician:", referringClinicianIDField);
        addField(outer, gbc, row, 2, "Receiving Clinician:", receivingClinicianIDField);
        addField(outer, gbc, row, 4, "Appointment ID:", appointmentIDField);

        row++;
        addField(outer, gbc, row, 0, "Referring Facility:", referringFacilityField);
        addField(outer, gbc, row, 2, "Receiving Facility:", receivingFacilityField);
        addField(outer, gbc, row, 4, "Urgency:", urgencyField);

        row++;
        addField(outer, gbc, row, 0, "Date:", dateField);
        addField(outer, gbc, row, 2, "Referral Reason:", referralReasonField);
        addField(outer, gbc, row, 4, "Clinical Summary:", clinicalSummaryField);

        row++;
        addField(outer, gbc, row, 0, "Investigations:", investigationsField);
        addField(outer, gbc, row, 2, "Notes:", notesField);
        addField(outer, gbc, row, 4, "Created:", createdDateField);

        row++;
        addField(outer, gbc, row, 0, "Last Updated:", lastUpdatedField);

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

    private JTextField createReadOnlyField(int cols) {
        JTextField f = createNormalField(cols);
        f.setEditable(false);
        f.setBackground(Color.WHITE);
        f.setDisabledTextColor(new Color(55, 71, 79));
        return f;
    }

    private void onRowSelected(ListSelectionEvent e) {
        if (e.getValueIsAdjusting()) return;

        int r = table.getSelectedRow();
        if (r < 0) { // ✅ if selection cleared -> blank form
            clearFields();
            return;
        }

        referralIDField.setText(val(r, 0));
        patientIDField.setText(val(r, 1));
        referringClinicianIDField.setText(val(r, 2));
        receivingClinicianIDField.setText(val(r, 3));
        referringFacilityField.setText(val(r, 4));
        receivingFacilityField.setText(val(r, 5));
        dateField.setText(val(r, 6));
        urgencyField.setText(val(r, 7));
        referralReasonField.setText(val(r, 8));
        clinicalSummaryField.setText(val(r, 9));
        investigationsField.setText(val(r, 10));
        appointmentIDField.setText(val(r, 11));
        notesField.setText(val(r, 12));
        statusField.setText(val(r, 13));
        createdDateField.setText(val(r, 14));
        lastUpdatedField.setText(val(r, 15));
    }

    private String val(int row, int col) {
        Object o = tableModel.getValueAt(row, col);
        return o == null ? "" : o.toString();
    }

    public void refreshData() {
        tableModel.setRowCount(0);

        List<Referral> list = getListFromController(controller,
                new String[]{"getReferrals","getAllReferrals","getReferralList","getReferralsList","getAllReferrals"},
                new String[]{"getDataManager","getData","getStore"},
                new String[]{"getReferrals","getAllReferrals","getReferralList","getReferralsList"}
        );

        if (list == null) list = Collections.emptyList();

        for (Referral r : list) {
            String rid = getStrSmart(r, "getReferralID","getReferralId","getId","referralID","referralId","id");
            String pid = getStrSmart(r, "getPatientID","getPatientId","patientID","patientId","patient");

            String refCid = getStrSmart(r, "getReferringClinicianID","getReferringClinicianId","referringClinicianID","referringClinicianId");
            String recCid = getStrSmart(r, "getReceivingClinicianID","getReceivingClinicianId","receivingClinicianID","receivingClinicianId");

            String refFac = getStrSmart(r, "getReferringFacility","getFromFacility","getFromFacilityId","referringFacility","fromFacility","fromFacilityId");
            String recFac = getStrSmart(r, "getReceivingFacility","getToFacility","getToFacilityId","receivingFacility","toFacility","toFacilityId");

            String date = getStrSmart(r, "getDate","getReferralDate","date","referralDate");
            String urg  = getStrSmart(r, "getUrgency","getPriority","urgency","priority");

            String reason = getStrSmart(r, "getReferralReason","getReason","referralReason","reason");
            String cs     = getStrSmart(r, "getClinicalSummary","clinicalSummary","getSummary","summary");

            String inv = getStrSmart(r,
                    "getInvestigations","getInvestigation","getTests","getInvestigationsDone",
                    "investigations","investigation","tests","investigationDetails","testResults",
                    "getInvestigationDetails","getTestResults"
            );

            String appt = getStrSmart(r, "getAppointmentID","getAppointmentId","appointmentID","appointmentId","appointment");
            String notes= getStrSmart(r, "getNotes","getComment","getRemarks","notes","comment","remarks");
            String status = getStrSmart(r, "getStatus","status");

            String created = getStrSmart(r, "getCreatedDate","getCreated","getCreatedAt","createdDate","created","createdAt");
            String updated = getStrSmart(r, "getLastUpdated","getLastModified","getUpdatedAt","lastUpdated","lastModified","updatedAt");

            tableModel.addRow(new Object[]{
                    rid, pid, refCid, recCid, refFac, recFac,
                    date, urg, reason, cs, inv,
                    appt, notes, status, created, updated
            });
        }

        // ✅ IMPORTANT: no auto select first row
        table.clearSelection();
        clearFields();
    }

    // ✅ BUTTONS
    private void onAdd() {
        try {
            Referral obj = buildReferralObject();
            Method add = controller.getClass().getMethod("addReferral", Referral.class);
            add.invoke(controller, obj);

            refreshData();
            clearFields();
            JOptionPane.showMessageDialog(this, "Referral added successfully!", "Message", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Add failed: " + ex.getMessage(), "Message", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onUpdate() {
        try {
            int row = table.getSelectedRow();
            if (row < 0) {
                JOptionPane.showMessageDialog(this, "Please select a referral.", "Message", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String id = referralIDField.getText().trim();
            if (id.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Referral ID missing.", "Message", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Method del = controller.getClass().getMethod("deleteReferral", String.class);
            del.invoke(controller, id);

            Referral obj = buildReferralObject();
            Method add = controller.getClass().getMethod("addReferral", Referral.class);
            add.invoke(controller, obj);

            refreshData();
            JOptionPane.showMessageDialog(this, "Referral updated successfully!", "Message", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Update failed: " + ex.getMessage(), "Message", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onDelete() {
        try {
            int row = table.getSelectedRow();
            if (row < 0) {
                JOptionPane.showMessageDialog(this, "Please select a referral.", "Message", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String id = referralIDField.getText().trim();
            if (id.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Select a referral first.", "Message", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Method del = controller.getClass().getMethod("deleteReferral", String.class);
            del.invoke(controller, id);

            refreshData();
            clearFields();
            JOptionPane.showMessageDialog(this, "Referral deleted successfully!", "Message", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Delete failed: " + ex.getMessage(), "Message", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String[] collectArgs() {
        return new String[] {
                referralIDField.getText().trim(),
                patientIDField.getText().trim(),
                referringClinicianIDField.getText().trim(),
                receivingClinicianIDField.getText().trim(),
                referringFacilityField.getText().trim(),
                receivingFacilityField.getText().trim(),
                dateField.getText().trim(),
                urgencyField.getText().trim(),
                referralReasonField.getText().trim(),
                clinicalSummaryField.getText().trim(),
                investigationsField.getText().trim(),
                appointmentIDField.getText().trim(),
                notesField.getText().trim(),
                statusField.getText().trim()
        };
    }

    private Referral buildReferralObject() throws Exception {
        String[] a = collectArgs();

        for (Constructor<?> c : Referral.class.getConstructors()) {
            Class<?>[] p = c.getParameterTypes();
            if (p.length != a.length) continue;

            boolean allString = true;
            for (Class<?> t : p) {
                if (t != String.class) { allString = false; break; }
            }
            if (allString) return (Referral) c.newInstance((Object[]) a);
        }

        Referral r = Referral.class.getDeclaredConstructor().newInstance();
        setIfExists(r, "referralID", a[0]);
        setIfExists(r, "patientID", a[1]);
        setIfExists(r, "referringClinicianID", a[2]);
        setIfExists(r, "receivingClinicianID", a[3]);
        setIfExists(r, "referringFacility", a[4]);
        setIfExists(r, "receivingFacility", a[5]);
        setIfExists(r, "date", a[6]);
        setIfExists(r, "urgency", a[7]);
        setIfExists(r, "referralReason", a[8]);
        setIfExists(r, "clinicalSummary", a[9]);
        setIfExists(r, "investigations", a[10]);
        setIfExists(r, "appointmentID", a[11]);
        setIfExists(r, "notes", a[12]);
        setIfExists(r, "status", a[13]);
        return r;
    }

    private void setIfExists(Object obj, String fieldName, String value) {
        try {
            Field f = obj.getClass().getDeclaredField(fieldName);
            f.setAccessible(true);
            f.set(obj, value);
        } catch (Exception ignored) {}
    }

    private void clearFields() {
        referralIDField.setText("");
        patientIDField.setText("");
        referringClinicianIDField.setText("");
        receivingClinicianIDField.setText("");
        referringFacilityField.setText("");
        receivingFacilityField.setText("");
        dateField.setText("");
        urgencyField.setText("");
        referralReasonField.setText("");
        clinicalSummaryField.setText("");
        investigationsField.setText("");
        appointmentIDField.setText("");
        notesField.setText("");
        statusField.setText("");
        createdDateField.setText("");
        lastUpdatedField.setText("");

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

    // ---------- SMART GETTERS for table ----------

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
