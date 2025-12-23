package com.healthcare.model;

/**
 * Model class representing a Clinician
 */
public class Clinician {
    private String clinicianID;
    private String firstName;
    private String lastName;
    private String qualification;
    private String specialty;
    private String gmcNumber;
    private String workplace;
    private String workplaceType;
    private String employmentStatus;
    private String startDate;
    private String email;
    private String phone;

    public Clinician() {
    }

    public Clinician(String clinicianID, String firstName, String lastName, String qualification,
                     String specialty, String workplace, String email, String phone) {
        this(clinicianID, firstName, lastName, qualification, specialty, "", workplace, "", "", "", email, phone);
    }

    public Clinician(String clinicianID, String firstName, String lastName, String qualification,
                     String specialty, String gmcNumber, String workplace, String workplaceType,
                     String employmentStatus, String startDate, String email, String phone) {
        this.clinicianID = clinicianID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.qualification = qualification;
        this.specialty = specialty;
        this.gmcNumber = gmcNumber;
        this.workplace = workplace;
        this.workplaceType = workplaceType;
        this.employmentStatus = employmentStatus;
        this.startDate = startDate;
        this.email = email;
        this.phone = phone;
    }

    // Getters and Setters
    public String getClinicianID() {
        return clinicianID;
    }

    public void setClinicianID(String clinicianID) {
        this.clinicianID = clinicianID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public String getWorkplace() {
        return workplace;
    }

    public void setWorkplace(String workplace) {
        this.workplace = workplace;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGmcNumber() {
        return gmcNumber;
    }

    public void setGmcNumber(String gmcNumber) {
        this.gmcNumber = gmcNumber;
    }

    public String getWorkplaceType() {
        return workplaceType;
    }

    public void setWorkplaceType(String workplaceType) {
        this.workplaceType = workplaceType;
    }

    public String getEmploymentStatus() {
        return employmentStatus;
    }

    public void setEmploymentStatus(String employmentStatus) {
        this.employmentStatus = employmentStatus;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName + " - " + specialty + " (" + clinicianID + ")";
    }
}



