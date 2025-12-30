package com.healthcare.data;

import com.healthcare.model.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Manager class to load and manage all healthcare data
 * Uses BufferedReader for CSV parsing
 */
public class DataManager {
    private List<Patient> patients;
    private List<Clinician> clinicians;
    private List<Facility> facilities;
    private List<Appointment> appointments;
    private List<Prescription> prescriptions;
    private List<Referral> referrals;
    private List<Staff> staff;

    public DataManager() {
        patients = new ArrayList<>();
        clinicians = new ArrayList<>();
        facilities = new ArrayList<>();
        appointments = new ArrayList<>();
        prescriptions = new ArrayList<>();
        referrals = new ArrayList<>();
        staff = new ArrayList<>();
    }

    /**
     * Load all CSV files from the data directory
     */
    public void loadAllData(String dataDirectory) {
        String sep = File.separator;

        loadPatients(dataDirectory + sep + "patients.csv");
        loadClinicians(dataDirectory + sep + "clinicians.csv");
        loadFacilities(dataDirectory + sep + "facilities.csv");
        loadAppointments(dataDirectory + sep + "appointments.csv");
        loadPrescriptions(dataDirectory + sep + "prescriptions.csv");
        loadReferrals(dataDirectory + sep + "referrals.csv");
        loadStaff(dataDirectory + sep + "staff.csv");
    }

    /**
     * Parse CSV line handling quoted fields
     */
    private String[] parseCSVLine(String line) {
        List<String> fields = new ArrayList<>();
        boolean inQuotes = false;
        StringBuilder currentField = new StringBuilder();

        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);

            if (c == '"') {
                inQuotes = !inQuotes;
            } else if (c == ',' && !inQuotes) {
                fields.add(currentField.toString().trim());
                currentField = new StringBuilder();
            } else {
                currentField.append(c);
            }
        }
        fields.add(currentField.toString().trim());
        return fields.toArray(new String[0]);
    }

    private boolean fileExists(String filePath, String label) {
        File f = new File(filePath);
        if (!f.exists()) {
            System.err.println("Missing " + label + " file: " + filePath);
            return false;
        }
        return true;
    }

    public void loadPatients(String filePath) {
        if (!fileExists(filePath, "patients")) return;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
        	String line = br.readLine(); // header
            while ((line = br.readLine()) != null && !line.trim().isEmpty()) {
                String[] fields = parseCSVLine(line);

                // patient_id,first_name,last_name,date_of_birth,nhs_number,gender,phone_number,email,address,postcode,emergency_contact_name,emergency_contact_phone,registration_date,gp_surgery_id
                if (fields.length >= 14) {
                    Patient patient = new Patient(
                            fields[0],  // patient_id
                            fields[1],  // first_name
                            fields[2],  // last_name
                            fields[3],  // date_of_birth
                            fields[5],  // gender
                            fields[4],  // nhs_number
                            fields[7],  // email
                            fields[6],  // phone_number
                            fields[8],  // address
                            fields[9],  // postcode
                            fields[10], // emergency_contact_name
                            fields[11], // emergency_contact_phone
                            fields[12], // registration_date
                            fields[13]  // gp_surgery_id
                    );
                    patients.add(patient);
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading patients: " + e.getMessage());
        }
    }

    public void loadClinicians(String filePath) {
        if (!fileExists(filePath, "clinicians")) return;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine(); // header
            while ((line = br.readLine()) != null && !line.trim().isEmpty()) {
                String[] fields = parseCSVLine(line);

                // clinician_id,first_name,last_name,title,speciality,gmc_number,phone_number,email,workplace_id,workplace_type,employment_status,start_date
                if (fields.length >= 12) {
                    Clinician clinician = new Clinician(
                            fields[0],  // clinician_id
                            fields[1],  // first_name
                            fields[2],  // last_name
                            fields[3],  // title -> qualification
                            fields[4],  // speciality -> specialty
                            fields[5],  // gmc_number
                            fields[8],  // workplace_id -> workplace
                            fields[9],  // workplace_type
                            fields[10], // employment_status
                            fields[11], // start_date
                            fields[7],  // email
                            fields[6]   // phone_number
                    );
                    clinicians.add(clinician);
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading clinicians: " + e.getMessage());
        }
    }

    public void loadFacilities(String filePath) {
        if (!fileExists(filePath, "facilities")) return;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine(); // header
            while ((line = br.readLine()) != null && !line.trim().isEmpty()) {
                String[] fields = parseCSVLine(line);

                // facility_id,facility_name,facility_type,address,postcode,phone_number,email,opening_hours,manager_name,capacity,specialities_offered
                if (fields.length >= 11) {
                    Facility facility = new Facility(
                            fields[0],  // facility_id
                            fields[1],  // facility_name
                            fields[2],  // facility_type
                            fields[3],  // address
                            fields[4],  // postcode
                            fields[5],  // phone_number
                            fields[6],  // email
                            fields[7],  // opening_hours
                            fields[8],  // manager_name
                            fields[10], // specialities_offered -> services
                            fields[9]   // capacity
                    );
                    facilities.add(facility);
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading facilities: " + e.getMessage());
        }
    }

    public void loadAppointments(String filePath) {
        if (!fileExists(filePath, "appointments")) return;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine(); // header
            while ((line = br.readLine()) != null && !line.trim().isEmpty()) {
                String[] fields = parseCSVLine(line);

                // appointment_id,patient_id,clinician_id,facility_id,appointment_date,appointment_time,duration_minutes,appointment_type,status,reason_for_visit,notes,created_date,last_modified
                if (fields.length >= 13) {
                    Appointment appointment = new Appointment(
                            fields[0],
                            fields[1],
                            fields[2],
                            fields[3],
                            fields[4],
                            fields[5],
                            fields[6],
                            fields[7],
                            fields[8],
                            fields[9],
                            fields[10],
                            fields[11],
                            fields[12]
                    );
                    appointments.add(appointment);
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading appointments: " + e.getMessage());
        }
    }

    public void loadPrescriptions(String filePath) {
        if (!fileExists(filePath, "prescriptions")) return;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine(); // header
            while ((line = br.readLine()) != null && !line.trim().isEmpty()) {
                String[] fields = parseCSVLine(line);

                // prescription_id,patient_id,clinician_id,appointment_id,prescription_date,medication_name,
                // dosage,frequency,duration_days,quantity,instructions,pharmacy_name,status,issue_date,collection_date
                if (fields.length >= 15) {
                    Prescription prescription = new Prescription(
                            fields[0],   // prescription_id
                            fields[1],   // patient_id
                            fields[2],   // clinician_id
                            fields[3],   // appointment_id
                            fields[5],   // medication_name
                            fields[6],   // dosage
                            fields[7],   // frequency
                            fields[8],   // duration_days
                            fields[9],   // quantity
                            fields[11],  // pharmacy_name
                            fields[4],   // prescription_date
                            fields[13],  // issue_date
                            fields[14],  // collection_date
                            fields[12],  // status -> collectionStatus
                            fields[10]   // instructions -> notes
                    );
                    prescriptions.add(prescription);
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading prescriptions: " + e.getMessage());
        }
    }

    public void loadReferrals(String filePath) {
        if (!fileExists(filePath, "referrals")) return;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine(); // header
            while ((line = br.readLine()) != null && !line.trim().isEmpty()) {
                String[] fields = parseCSVLine(line);

                // referral_id,patient_id,referring_clinician_id,referred_to_clinician_id,
                // referring_facility_id,referred_to_facility_id,referral_date,urgency_level,
                // referral_reason,clinical_summary,requested_investigations,status,appointment_id,notes,created_date,last_updated
                if (fields.length >= 16) {
                    Referral referral = new Referral(
                            fields[0],
                            fields[1],
                            fields[2],
                            fields[3],
                            fields[4],
                            fields[5],
                            fields[6],
                            fields[7],
                            fields[8],
                            fields[9],
                            fields[10],
                            fields[12],
                            fields[13],
                            fields[11],
                            fields[14],
                            fields[15]
                    );
                    referrals.add(referral);
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading referrals: " + e.getMessage());
        }
    }

    public void loadStaff(String filePath) {
        if (!fileExists(filePath, "staff")) return;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine(); // header
            while ((line = br.readLine()) != null && !line.trim().isEmpty()) {
                String[] fields = parseCSVLine(line);

                // staff_id,first_name,last_name,role,department,facility_id,phone_number,email,employment_status,start_date,line_manager,access_level
                if (fields.length >= 12) {
                    Staff staffMember = new Staff(
                            fields[0],
                            fields[1],
                            fields[2],
                            fields[3],
                            fields[4],
                            fields[5],
                            fields[7],  // email
                            fields[6],  // phone
                            fields[8],
                            fields[9],
                            fields[10],
                            fields[11]
                    );
                    staff.add(staffMember);
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading staff: " + e.getMessage());
        }
    }

    // (Optional safety) If file is empty
    private String RnReadLineSafe(BufferedReader br) throws IOException {
        return br.readLine();
    }

    // Getters
    public List<Patient> getPatients() { return patients; }
    public List<Clinician> getClinicians() { return clinicians; }
    public List<Facility> getFacilities() { return facilities; }
    public List<Appointment> getAppointments() { return appointments; }
    public List<Prescription> getPrescriptions() { return prescriptions; }
    public List<Referral> getReferrals() { return referrals; }
    public List<Staff> getStaff() { return staff; }

    // Add methods
    public void addPatient(Patient patient) { patients.add(patient); }
    public void addClinician(Clinician clinician) { clinicians.add(clinician); }
    public void addFacility(Facility facility) { facilities.add(facility); }
    public void addAppointment(Appointment appointment) { appointments.add(appointment); }
    public void addPrescription(Prescription prescription) { prescriptions.add(prescription); }
    public void addReferral(Referral referral) { referrals.add(referral); }
    public void addStaff(Staff staffMember) { staff.add(staffMember); }

    // Delete methods
    public boolean deletePatient(String patientID) { return patients.removeIf(p -> p.getPatientID().equals(patientID)); }
    public boolean deleteClinician(String clinicianID) { return clinicians.removeIf(c -> c.getClinicianID().equals(clinicianID)); }
    public boolean deleteFacility(String facilityID) { return facilities.removeIf(f -> f.getFacilityID().equals(facilityID)); }
    public boolean deleteAppointment(String appointmentID) { return appointments.removeIf(a -> a.getAppointmentID().equals(appointmentID)); }
    public boolean deletePrescription(String prescriptionID) { return prescriptions.removeIf(p -> p.getPrescriptionID().equals(prescriptionID)); }
    public boolean deleteReferral(String referralID) { return referrals.removeIf(r -> r.getReferralID().equals(referralID)); }
    public boolean deleteStaff(String staffID) { return staff.removeIf(s -> s.getStaffID().equals(staffID)); }

    // Find methods
    public Patient findPatient(String patientID) {
        return patients.stream().filter(p -> p.getPatientID().equals(patientID)).findFirst().orElse(null);
    }

    public Clinician findClinician(String clinicianID) {
        return clinicians.stream().filter(c -> c.getClinicianID().equals(clinicianID)).findFirst().orElse(null);
    }

    public Facility findFacility(String facilityID) {
        return facilities.stream().filter(f -> f.getFacilityID().equals(facilityID)).findFirst().orElse(null);
    }
}
