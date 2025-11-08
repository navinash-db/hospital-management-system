package com.HMS.patient.service;

import com.HMS.patient.dto.PatientDTO;
import com.HMS.patient.entity.Patient;
import com.HMS.patient.exception.PatientNotFoundException;
import com.HMS.patient.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PatientService {

	@Autowired
	private PatientRepository patientRepository;

	// ➕ Add new patient
	public Patient addPatient(PatientDTO patientDTO) {
		Patient patient = new Patient();
		patient.setName(patientDTO.getName());
		patient.setAge(patientDTO.getAge());
		patient.setAddress(patientDTO.getAddress());
		patient.setPhoneNumber(patientDTO.getPhoneNumber());
		patient.setGender(patientDTO.getGender());
		patient.setNotes(patientDTO.getNotes());
		patient.setStatus(patientDTO.getStatus() != null ? patientDTO.getStatus() : "Active");

		return patientRepository.save(patient);
	}

	// 📜 Get all patients
	public List<Patient> getAllPatients() {
		return patientRepository.findAll();
	}

	// 🔍 Get patient by ID
	public Patient getPatientById(Long id) {
		return patientRepository.findById(id)
				.orElseThrow(() -> new PatientNotFoundException("Patient not found with ID: " + id));

	}

	// ✏️ Update patient
	public Patient updatePatient(Long id, PatientDTO patientDTO) {
		Patient existingPatient = getPatientById(id);

		existingPatient.setName(patientDTO.getName());
		existingPatient.setAge(patientDTO.getAge());
		existingPatient.setAddress(patientDTO.getAddress());
		existingPatient.setPhoneNumber(patientDTO.getPhoneNumber());
		existingPatient.setGender(patientDTO.getGender());
		existingPatient.setNotes(patientDTO.getNotes());
		existingPatient.setStatus(patientDTO.getStatus());

		return patientRepository.save(existingPatient);
	}

	// ❌ Delete patient
	public void deletePatient(Long id) {
		Patient patient = getPatientById(id);
		patientRepository.delete(patient);
	}

	// 🔎 Search by name
	public List<Patient> searchByName(String name) {
		return patientRepository.findByNameContainingIgnoreCase(name);
	}

	// 🩺 Get patients by status
	public List<Patient> getPatientsByStatus(String status) {
		return patientRepository.findByStatus(status);
	}

	// 🚫 Set patient status to Inactive
	public Patient deactivatePatient(Long id) {
		Patient patient = getPatientById(id);
		patient.setStatus("Inactive");
		return patientRepository.save(patient);
	}

	// 🚫 Set patient status to active
	public Patient activatePatient(Long id) {
		Patient patient = getPatientById(id);
		patient.setStatus("Active");
		return patientRepository.save(patient);
	}

}
