package com.healthcare.model;

/**
 * Model class representing a Facility
 */
public class Facility {
    private String facilityID;
    private String name;
    private String type;
    private String address;
    private String postcode;
    private String phone;
    private String email;
    private String openingHours;
    private String managerName;
    private String services;
    private String capacity;

    public Facility() {
    }

    public Facility(String facilityID, String name, String type, String address,
                    String phone, String email, String services, String capacity) {
        this(facilityID, name, type, address, "", phone, email, "", "", services, capacity);
    }

    public Facility(String facilityID, String name, String type, String address, String postcode,
                    String phone, String email, String openingHours, String managerName,
                    String services, String capacity) {
        this.facilityID = facilityID;
        this.name = name;
        this.type = type;
        this.address = address;
        this.postcode = postcode;
        this.phone = phone;
        this.email = email;
        this.openingHours = openingHours;
        this.managerName = managerName;
        this.services = services;
        this.capacity = capacity;
    }

    // Getters and Setters
    public String getFacilityID() {
        return facilityID;
    }

    public void setFacilityID(String facilityID) {
        this.facilityID = facilityID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getServices() {
        return services;
    }

    public void setServices(String services) {
        this.services = services;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getOpeningHours() {
        return openingHours;
    }

    public void setOpeningHours(String openingHours) {
        this.openingHours = openingHours;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    @Override
    public String toString() {
        return name + " (" + facilityID + ")";
    }
}



