package com.healthcare.view;

import com.healthcare.controller.HealthcareController;

import javax.swing.*;
import java.awt.*;

/**
 * Main View class - Main GUI window with tabs for different entities
 */
public class MainView extends JFrame {

    private final HealthcareController controller;

    private JTabbedPane tabbedPane;

    private PatientPanel patientPanel;
    private ClinicianPanel clinicianPanel;
    private FacilityPanel facilityPanel;
    private AppointmentPanel appointmentPanel;
    private PrescriptionPanel prescriptionPanel;
    private ReferralPanel referralPanel;
    private StaffPanel staffPanel;

    public MainView(HealthcareController controller) {
        this.controller = controller;
        initializeGUI();
        refreshAllPanels(); // populate tables once after UI is ready
    }

    private void initializeGUI() {
        setTitle("Healthcare Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // App background
        getContentPane().setBackground(new Color(245, 247, 250));

        // Header
        add(buildHeader(), BorderLayout.NORTH);

        // Menu bar
        createMenuBar();

        // Tabs
        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(tabbedPane.getFont().deriveFont(Font.PLAIN, 14f));
        tabbedPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        tabbedPane.setBackground(new Color(245, 247, 250));

        // Panels
        patientPanel = new PatientPanel(controller);
        clinicianPanel = new ClinicianPanel(controller);
        facilityPanel = new FacilityPanel(controller);
        appointmentPanel = new AppointmentPanel(controller);
        prescriptionPanel = new PrescriptionPanel(controller);
        referralPanel = new ReferralPanel(controller);
        staffPanel = new StaffPanel(controller);

        // Add tabs
        tabbedPane.addTab("Patients", patientPanel);
        tabbedPane.addTab("Clinicians", clinicianPanel);
        tabbedPane.addTab("Facilities", facilityPanel);
        tabbedPane.addTab("Appointments", appointmentPanel);
        tabbedPane.addTab("Prescriptions", prescriptionPanel);
        tabbedPane.addTab("Referrals", referralPanel);
        tabbedPane.addTab("Staff", staffPanel);

        add(tabbedPane, BorderLayout.CENTER);

        // Status bar
        add(buildStatusBar(), BorderLayout.SOUTH);
    }

    private JPanel buildHeader() {
        // Professional blue
        Color headerBg = new Color(21, 101, 192);
        Color subtitleColor = new Color(225, 240, 255);

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(headerBg);
        header.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        JPanel centerBox = new JPanel();
        centerBox.setOpaque(false);
        centerBox.setLayout(new BoxLayout(centerBox, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel("Healthcare Management System");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 26));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitleLabel = new JLabel("Patient, Clinician, Facility, Appointment, Prescription & Referral Management");
        subtitleLabel.setForeground(subtitleColor);
        subtitleLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        centerBox.add(titleLabel);
        centerBox.add(Box.createVerticalStrut(4));
        centerBox.add(subtitleLabel);

        header.add(centerBox, BorderLayout.CENTER);
        return header;
    }

    private JPanel buildStatusBar() {
        JPanel statusBar = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 6));
        statusBar.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(230, 230, 230)));
        statusBar.setBackground(Color.WHITE);

        JLabel statusLabel = new JLabel("Ready");
        statusLabel.setForeground(new Color(110, 110, 110));
        statusBar.add(statusLabel);

        return statusBar;
    }

    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");

        JMenuItem loadMenuItem = new JMenuItem("Load Data");
        loadMenuItem.addActionListener(e -> loadData());
        fileMenu.add(loadMenuItem);

        fileMenu.addSeparator();

        JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.addActionListener(e -> System.exit(0));
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);
        setJMenuBar(menuBar);
    }

    private void loadData() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            String dataDirectory = fileChooser.getSelectedFile().getAbsolutePath();
            controller.loadData(dataDirectory);
            refreshAllPanels();
            JOptionPane.showMessageDialog(this, "Data loaded successfully!");
        }
    }

    public void refreshAllPanels() {
        if (patientPanel != null) patientPanel.refreshData();
        if (clinicianPanel != null) clinicianPanel.refreshData();
        if (facilityPanel != null) facilityPanel.refreshData();
        if (appointmentPanel != null) appointmentPanel.refreshData();
        if (prescriptionPanel != null) prescriptionPanel.refreshData();
        if (referralPanel != null) referralPanel.refreshData();
        if (staffPanel != null) staffPanel.refreshData();
    }
}
