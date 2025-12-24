package com.healthcare.model;

/**
 * Model class representing a Prescription
 */
public class Prescription {
    private String prescriptionID;
    private String patientID;
    private String clinicianID;
    private String appointmentID;
    private String medication;
    private String dosage;
    private String frequency;
    private String durationDays;
    private String quantity;
    private String pharmacy;
    private String datePrescribed;
    private String issueDate;
    private String collectionDate;
    private String collectionStatus;
    private String notes;

    public Prescription() {
    }

    public Prescription(String prescriptionID, String patientID, String clinicianID, String medication,
                        String dosage, String quantity, String pharmacy, String datePrescribed,
                        String collectionStatus, String notes) {
        this(prescriptionID, patientID, clinicianID, "", medication, dosage, "", "", quantity, pharmacy, datePrescribed, "", "", collectionStatus, notes);
    }

    public Prescription(String prescriptionID, String patientID, String clinicianID, String appointmentID,
                        String medication, String dosage, String frequency, String durationDays,
                        String quantity, String pharmacy, String datePrescribed, String issueDate,
                        String collectionDate, String collectionStatus, String notes) {
        this.prescriptionID = prescriptionID;
        this.patientID = patientID;
        this.clinicianID = clinicianID;
        this.appointmentID = appointmentID;
        this.medication = medication;
        this.dosage = dosage;
        this.frequency = frequency;
        this.durationDays = durationDays;
        this.quantity = quantity;
        this.pharmacy = pharmacy;
        this.datePrescribed = datePrescribed;
        this.issueDate = issueDate;
        this.collectionDate = collectionDate;
        this.collectionStatus = collectionStatus;
        this.notes = notes;
    }

    // Getters and Setters
    public String getPrescriptionID() {
        return prescriptionID;
    }

    public void setPrescriptionID(String prescriptionID) {
        this.prescriptionID = prescriptionID;
    }

    public String getPatientID() {
        return patientID;
    }

    public void setPatientID(String patientID) {
        this.patientID = patientID;
    }

    public String getClinicianID() {
        return clinicianID;
    }

    public void setClinicianID(String clinicianID) {
        this.clinicianID = clinicianID;
    }

    public String getMedication() {
        return medication;
    }

    public void setMedication(String medication) {
        this.medication = medication;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPharmacy() {
        return pharmacy;
    }

    public void setPharmacy(String pharmacy) {
        this.pharmacy = pharmacy;
    }

    public String getDatePrescribed() {
        return datePrescribed;
    }

    public void setDatePrescribed(String datePrescribed) {
        this.datePrescribed = datePrescribed;
    }

    public String getCollectionStatus() {
        return collectionStatus;
    }

    public void setCollectionStatus(String collectionStatus) {
        this.collectionStatus = collectionStatus;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getAppointmentID() {
        return appointmentID;
    }

    public void setAppointmentID(String appointmentID) {
        this.appointmentID = appointmentID;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getDurationDays() {
        return durationDays;
    }

    public void setDurationDays(String durationDays) {
        this.durationDays = durationDays;
    }

    public String getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(String issueDate) {
        this.issueDate = issueDate;
    }

    public String getCollectionDate() {
        return collectionDate;
    }

    public void setCollectionDate(String collectionDate) {
        this.collectionDate = collectionDate;
    }

    @Override
    public String toString() {
        return prescriptionID + " - " + medication + " (" + datePrescribed + ")";
    }
}



