# Healthcare Management System

A Java application for managing healthcare records including patients, clinicians, facilities, appointments, prescriptions, referrals, and staff.

## Architecture

This application follows the **MVC (Model-View-Controller)** architectural pattern:

- **Model**: Data classes representing healthcare entities (Patient, Clinician, Facility, Appointment, Prescription, Referral, Staff)
- **View**: Java Swing GUI components for user interaction
- **Controller**: Mediates between View and Model, handles business logic

## Design Patterns

### Singleton Pattern
The `ReferralManager` class implements the Singleton pattern to ensure:
- Single instance creation for referral management
- Consistent referral processing
- Prevention of duplicate communications
- Proper audit trails throughout referral lifecycle

## Features

### Data Management
- Load data from CSV files using BufferedReader
- Create, Read, Update, Delete (CRUD) operations for all entities
- In-memory data storage (session-based)

### Entities Supported
1. **Patients** - Patient demographic information, contact details, NHS numbers
2. **Clinicians** - Doctors, nurses, specialists with qualifications and specialties
3. **Facilities** - GP surgeries and hospitals with services and capacity
4. **Appointments** - Appointment records with scheduling details and status
5. **Prescriptions** - Prescription data including medications, dosages, and pharmacies
6. **Referrals** - Referral information between primary and secondary care
7. **Staff** - Non-clinical staff including administrators and receptionists

### Referral Management
- Generate referral text files with clinical summaries
- Email communication simulation (text file generation)
- Electronic Health Record (EHR) update simulation
- Referral queue management

## How to Run

1. Ensure all CSV data files are in the `data/` directory
2. Compile the Java source files
3. Run `HealthcareApplication` main class
4. Use File > Load Data menu to load CSV files, or data will auto-load from `data/` directory if it exists

## Project Structure

```
Assignment/
├── src/
│   ├── com/healthcare/
│   │   ├── model/          # Model classes (Patient, Clinician, etc.)
│   │   ├── data/            # DataManager for CSV loading
│   │   ├── controller/       # HealthcareController (MVC)
│   │   ├── view/            # Swing GUI panels
│   │   ├── referral/        # ReferralManager (Singleton)
│   │   └── HealthcareApplication.java
│   └── module-info.java
├── data/                    # CSV data files
└── README.md
```

## CSV Files

The application expects the following CSV files in the `data/` directory:
- patients.csv
- clinicians.csv
- facilities.csv
- appointments.csv
- prescriptions.csv
- referrals.csv
- staff.csv

## Requirements Met

✅ MVC Architecture Pattern
✅ Singleton Pattern for Referral Management
✅ Java Swing GUI (no JavaFX)
✅ Plain Java (no external libraries)
✅ CSV loading with BufferedReader
✅ CRUD operations for all entities
✅ Referral text file generation
✅ Class relationships (association, composition, inheritance)



