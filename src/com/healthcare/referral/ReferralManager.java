package com.healthcare.referral;

import com.healthcare.model.Referral;
import com.healthcare.data.DataManager;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Singleton pattern implementation for Referral Management
 * Manages referral queues, email communications, and EHR updates
 */
public class ReferralManager {
    private static ReferralManager instance;
    private List<Referral> referralQueue;
    private List<String> emailCommunications;
    private List<String> ehrUpdates;
    private DataManager dataManager;

    // Private constructor to prevent instantiation
    private ReferralManager() {
        referralQueue = new ArrayList<>();
        emailCommunications = new ArrayList<>();
        ehrUpdates = new ArrayList<>();
    }

    /**
     * Get the singleton instance
     */
    public static synchronized ReferralManager getInstance() {
        if (instance == null) {
            instance = new ReferralManager();
        }
        return instance;
    }

    /**
     * Set the data manager (dependency injection)
     */
    public void setDataManager(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    /**
     * Add referral to queue
     */
    public void addToQueue(Referral referral) {
        referralQueue.add(referral);
        generateEmailCommunication(referral);
        generateEHRUpdate(referral);
    }

    /**
     * Process referral queue
     */
    public void processQueue() {
        for (Referral referral : referralQueue) {
            if ("Pending".equals(referral.getStatus())) {
                processReferral(referral);
            }
        }
    }

    /**
     * Process a single referral
     */
    private void processReferral(Referral referral) {
        // Simulate referral processing
        generateEmailCommunication(referral);
        generateEHRUpdate(referral);
    }

    /**
     * Generate email communication content for referral
     */
    private void generateEmailCommunication(Referral referral) {
        StringBuilder email = new StringBuilder();
        email.append("=== REFERRAL EMAIL COMMUNICATION ===\n");
        email.append("Date: ").append(LocalDate.now().format(DateTimeFormatter.ISO_DATE)).append("\n");
        email.append("Referral ID: ").append(referral.getReferralID()).append("\n");
        email.append("Patient ID: ").append(referral.getPatientID()).append("\n");
        email.append("From: ").append(referral.getReferringFacility()).append("\n");
        email.append("To: ").append(referral.getReceivingFacility()).append("\n");
        email.append("Urgency: ").append(referral.getUrgency()).append("\n");
        email.append("Clinical Summary: ").append(referral.getClinicalSummary()).append("\n");
        email.append("Status: ").append(referral.getStatus()).append("\n");
        email.append("=====================================\n\n");

        emailCommunications.add(email.toString());
    }

    /**
     * Generate EHR update content for referral
     */
    private void generateEHRUpdate(Referral referral) {
        StringBuilder ehr = new StringBuilder();
        ehr.append("=== ELECTRONIC HEALTH RECORD UPDATE ===\n");
        ehr.append("Timestamp: ").append(LocalDate.now().format(DateTimeFormatter.ISO_DATE)).append("\n");
        ehr.append("Referral ID: ").append(referral.getReferralID()).append("\n");
        ehr.append("Patient ID: ").append(referral.getPatientID()).append("\n");
        ehr.append("Action: Referral Created/Updated\n");
        ehr.append("Referring Clinician: ").append(referral.getReferringClinicianID()).append("\n");
        ehr.append("Receiving Clinician: ").append(referral.getReceivingClinicianID()).append("\n");
        ehr.append("Clinical Summary: ").append(referral.getClinicalSummary()).append("\n");
        ehr.append("Audit Trail: Referral processed by system\n");
        ehr.append("========================================\n\n");

        ehrUpdates.add(ehr.toString());
    }

    /**
     * Generate referral text file
     */
    public void generateReferralFile(Referral referral, String outputPath) {
        try (FileWriter writer = new FileWriter(outputPath)) {
            writer.write("=== PATIENT REFERRAL ===\n\n");
            writer.write("Referral ID: " + referral.getReferralID() + "\n");
            writer.write("Date: " + referral.getDate() + "\n");
            writer.write("Urgency Level: " + referral.getUrgency() + "\n");
            writer.write("Status: " + referral.getStatus() + "\n\n");

            writer.write("PATIENT INFORMATION:\n");
            writer.write("Patient ID: " + referral.getPatientID() + "\n\n");

            writer.write("REFERRING INFORMATION:\n");
            writer.write("Clinician ID: " + referral.getReferringClinicianID() + "\n");
            writer.write("Facility: " + referral.getReferringFacility() + "\n\n");

            writer.write("RECEIVING INFORMATION:\n");
            writer.write("Clinician ID: " + referral.getReceivingClinicianID() + "\n");
            writer.write("Facility: " + referral.getReceivingFacility() + "\n\n");

            writer.write("CLINICAL SUMMARY:\n");
            writer.write(referral.getClinicalSummary() + "\n\n");

            writer.write("=== END OF REFERRAL ===\n");

            // Also add to email communications
            generateEmailCommunication(referral);
            generateEHRUpdate(referral);

        } catch (IOException e) {
            System.err.println("Error generating referral file: " + e.getMessage());
        }
    }

    /**
     * Get all email communications
     */
    public List<String> getEmailCommunications() {
        return new ArrayList<>(emailCommunications);
    }

    /**
     * Get all EHR updates
     */
    public List<String> getEHRUpdates() {
        return new ArrayList<>(ehrUpdates);
    }

    /**
     * Get referral queue
     */
    public List<Referral> getReferralQueue() {
        return new ArrayList<>(referralQueue);
    }

    /**
     * Clear audit trail (for testing purposes)
     */
    public void clearAuditTrail() {
        emailCommunications.clear();
        ehrUpdates.clear();
    }
}



