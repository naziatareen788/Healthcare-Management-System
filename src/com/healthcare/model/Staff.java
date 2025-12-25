package com.healthcare.model;

/**
 * Model class representing Staff (non-clinical)
 */
public class Staff {
    private String staffID;
    private String firstName;
    private String lastName;
    private String role;
    private String department;
    private String facilityID;
    private String email;
    private String phone;
    private String employmentStatus;
    private String startDate;
    private String lineManager;
    private String accessLevel;

    public Staff() {
    }

    public Staff(String staffID, String firstName, String lastName, String role,
                 String facilityID, String email, String phone) {
        this(staffID, firstName, lastName, role, "", facilityID, email, phone, "", "", "", "");
    }

    public Staff(String staffID, String firstName, String lastName, String role, String department,
                 String facilityID, String email, String phone, String employmentStatus,
                 String startDate, String lineManager, String accessLevel) {
        this.staffID = staffID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.department = department;
        this.facilityID = facilityID;
        this.email = email;
        this.phone = phone;
        this.employmentStatus = employmentStatus;
        this.startDate = startDate;
        this.lineManager = lineManager;
        this.accessLevel = accessLevel;
    }

    // Getters and Setters
    public String getStaffID() {
        return staffID;
    }

    public void setStaffID(String staffID) {
        this.staffID = staffID;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getFacilityID() {
        return facilityID;
    }

    public void setFacilityID(String facilityID) {
        this.facilityID = facilityID;
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

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
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

    public String getLineManager() {
        return lineManager;
    }

    public void setLineManager(String lineManager) {
        this.lineManager = lineManager;
    }

    public String getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(String accessLevel) {
        this.accessLevel = accessLevel;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName + " - " + role + " (" + staffID + ")";
    }
}



