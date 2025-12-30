package com.healthcare.controller;

import com.healthcare.data.DataManager;
import com.healthcare.model.*;
import com.healthcare.referral.ReferralManager;
import java.util.List;

public class HealthcareController {
    private DataManager dataManager;
    private ReferralManager referralManager;

    public HealthcareController() {
        dataManager = new DataManager();
        referralManager = ReferralManager.getInstance();
        referralManager.setDataManager(dataManager);
    }

    public void loadData(String dataDirectory) {
        dataManager.loadAllData(dataDirectory);
    }

    // Patient operations
    public List<Patient> getAllPatients() {
        return dataManager.getPatients();
    }

    public void addPatient(Patient patient) {
        dataManager.addPatient(patient);
    }

    public boolean deletePatient(String patientID) {
        return dataManager.deletePatient(patientID);
    }

    public Patient findPatient(String patientID) {
        return dataManager.findPatient(patientID);
    }

    // Clinician operations
    public List<Clinician> getAllClinicians() {
        return dataManager.getClinicians();
    }

    public void addClinician(Clinician clinician) {
        dataManager.addClinician(clinician);
    }

    public boolean deleteClinician(String clinicianID) {
        return dataManager.deleteClinician(clinicianID);
    }

    public Clinician findClinician(String clinicianID) {
        return dataManager.findClinician(clinicianID);
    }

    // Facility operations
    public List<Facility> getAllFacilities() {
        return dataManager.getFacilities();
    }

    public void addFacility(Facility facility) {
        dataManager.addFacility(facility);
    }

    public boolean deleteFacility(String facilityID) {
        return dataManager.deleteFacility(facilityID);
    }

    public Facility findFacility(String facilityID) {
        return dataManager.findFacility(facilityID);
    }

    // Appointment operations
    public List<Appointment> getAllAppointments() {
        return dataManager.getAppointments();
    }

    public void addAppointment(Appointment appointment) {
        dataManager.addAppointment(appointment);
    }

    public boolean deleteAppointment(String appointmentID) {
        return dataManager.deleteAppointment(appointmentID);
    }

    // Prescription operations
    public List<Prescription> getAllPrescriptions() {
        return dataManager.getPrescriptions();
    }

    public void addPrescription(Prescription prescription) {
        dataManager.addPrescription(prescription);
    }

    public boolean deletePrescription(String prescriptionID) {
        return dataManager.deletePrescription(prescriptionID);
    }

    // Referral operations
    public List<Referral> getAllReferrals() {
        return dataManager.getReferrals();
    }

    public void addReferral(Referral referral) {
        dataManager.addReferral(referral);
        referralManager.addToQueue(referral);
    }

    public boolean deleteReferral(String referralID) {
        return dataManager.deleteReferral(referralID);
    }

    public void generateReferralFile(Referral referral, String outputPath) {
        referralManager.generateReferralFile(referral, outputPath);
    }

    // Staff operations
    public List<Staff> getAllStaff() {
        return dataManager.getStaff();
    }

    public void addStaff(Staff staff) {
        dataManager.addStaff(staff);
    }

    public boolean deleteStaff(String staffID) {
        return dataManager.deleteStaff(staffID);
    }

    // Get data manager for direct access if needed
    public DataManager getDataManager() {
        return dataManager;
    }

    // Get referral manager
    public ReferralManager getReferralManager() {
        return referralManager;
    }
}



