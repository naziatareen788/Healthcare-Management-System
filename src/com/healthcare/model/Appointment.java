package com.healthcare.model;

/**
 * Model class representing an Appointment
 */
public class Appointment {
    private String appointmentID;
    private String patientID;
    private String clinicianID;
    private String facilityID;
    private String date;
    private String time;
    private String durationMinutes;
    private String appointmentType;
    private String status;
    private String reason;
    private String notes;
    private String createdDate;
    private String lastModified;

    public Appointment() {
    }

    public Appointment(String appointmentID, String patientID, String clinicianID, String facilityID,
                       String date, String time, String status, String reason, String notes) {
        this(appointmentID, patientID, clinicianID, facilityID, date, time, "", "", status, reason, notes, "", "");
    }

    public Appointment(String appointmentID, String patientID, String clinicianID, String facilityID,
                       String date, String time, String durationMinutes, String appointmentType,
                       String status, String reason, String notes, String createdDate, String lastModified) {
        this.appointmentID = appointmentID;
        this.patientID = patientID;
        this.clinicianID = clinicianID;
        this.facilityID = facilityID;
        this.date = date;
        this.time = time;
        this.durationMinutes = durationMinutes;
        this.appointmentType = appointmentType;
        this.status = status;
        this.reason = reason;
        this.notes = notes;
        this.createdDate = createdDate;
        this.lastModified = lastModified;
    }

    // Getters and Setters
    public String getAppointmentID() {
        return appointmentID;
    }

    public void setAppointmentID(String appointmentID) {
        this.appointmentID = appointmentID;
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

    public String getFacilityID() {
        return facilityID;
    }

    public void setFacilityID(String facilityID) {
        this.facilityID = facilityID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(String durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public String getAppointmentType() {
        return appointmentType;
    }

    public void setAppointmentType(String appointmentType) {
        this.appointmentType = appointmentType;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModified() {
        return lastModified;
    }

    public void setLastModified(String lastModified) {
        this.lastModified = lastModified;
    }

    @Override
    public String toString() {
        return appointmentID + " - " + date + " " + time + " (" + status + ")";
    }
}



