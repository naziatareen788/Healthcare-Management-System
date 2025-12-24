package com.healthcare.model;

/**
 * Model class representing a Patient
 */
public class Patient {
    private String patientID;
    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private String gender;
    private String nhsNumber;
    private String email;
    private String phone;
    private String address;
    private String postcode;
    private String emergencyContactName;
    private String emergencyContactPhone;
    private String registrationDate;
    private String gpSurgery;

    public Patient() {
    }

    public Patient(String patientID, String firstName, String lastName, String dateOfBirth,
                   String gender, String nhsNumber, String email, String phone, String address, String gpSurgery) {
        this(patientID, firstName, lastName, dateOfBirth, gender, nhsNumber, email, phone, address, "", "", "", "", gpSurgery);
    }

    public Patient(String patientID, String firstName, String lastName, String dateOfBirth,
                   String gender, String nhsNumber, String email, String phone, String address, 
                   String postcode, String emergencyContactName, String emergencyContactPhone, 
                   String registrationDate, String gpSurgery) {
        this.patientID = patientID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.nhsNumber = nhsNumber;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.postcode = postcode;
        this.emergencyContactName = emergencyContactName;
        this.emergencyContactPhone = emergencyContactPhone;
        this.registrationDate = registrationDate;
        this.gpSurgery = gpSurgery;
    }

    // Getters and Setters
    public String getPatientID() {
        return patientID;
    }

    public void setPatientID(String patientID) {
        this.patientID = patientID;
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

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getNhsNumber() {
        return nhsNumber;
    }

    public void setNhsNumber(String nhsNumber) {
        this.nhsNumber = nhsNumber;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGpSurgery() {
        return gpSurgery;
    }

    public void setGpSurgery(String gpSurgery) {
        this.gpSurgery = gpSurgery;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getEmergencyContactName() {
        return emergencyContactName;
    }

    public void setEmergencyContactName(String emergencyContactName) {
        this.emergencyContactName = emergencyContactName;
    }

    public String getEmergencyContactPhone() {
        return emergencyContactPhone;
    }

    public void setEmergencyContactPhone(String emergencyContactPhone) {
        this.emergencyContactPhone = emergencyContactPhone;
    }

    public String getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = registrationDate;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName + " (" + patientID + ")";
    }
}

