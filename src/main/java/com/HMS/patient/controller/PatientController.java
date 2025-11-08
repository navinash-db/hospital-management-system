package com.HMS.patient.controller;

import com.HMS.patient.dto.PatientDTO;
import com.HMS.patient.entity.Patient;
import com.HMS.patient.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patients")
@Validated
public class PatientController {

	@Autowired
	private PatientService patientService;

	// ➕ Add new patient
	@PostMapping
	public ResponseEntity<Patient> addPatient(@RequestBody @Validated PatientDTO patientDTO) {
		Patient savedPatient = patientService.addPatient(patientDTO);
		return ResponseEntity.ok(savedPatient);
	}

	// 📜 Get all patients
	@GetMapping
	public ResponseEntity<List<Patient>> getAllPatients() {
		return ResponseEntity.ok(patientService.getAllPatients());
	}

	// 🔍 Get patient by ID
	@GetMapping("/{id}")
	public ResponseEntity<Patient> getPatientById(@PathVariable Long id) {
		return ResponseEntity.ok(patientService.getPatientById(id));
	}

	// ✏️ Update patient
	@PutMapping("/{id}")
	public ResponseEntity<Patient> updatePatient(@PathVariable Long id, @RequestBody @Validated PatientDTO patientDTO) {
		return ResponseEntity.ok(patientService.updatePatient(id, patientDTO));
	}

	// ❌ Delete patient
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deletePatient(@PathVariable Long id) {
		patientService.deletePatient(id);
		return ResponseEntity.ok("Patient with ID " + id + " deleted successfully");
	}

	// 🔎 Search patients by name
	@GetMapping("/search")
	public ResponseEntity<List<Patient>> searchByName(@RequestParam String name) {
		return ResponseEntity.ok(patientService.searchByName(name));
	}

	// 🩺 Filter patients by status
	@GetMapping("/status/{status}")
	public ResponseEntity<List<Patient>> getPatientsByStatus(@PathVariable String status) {
		return ResponseEntity.ok(patientService.getPatientsByStatus(status));
	}

	// 🚫 Deactivate a patient (set status to Inactive)
	@PutMapping("/{id}/deactivate")
	public ResponseEntity<String> deactivatePatient(@PathVariable Long id) {
		patientService.deactivatePatient(id);
		return ResponseEntity.ok("Patient with ID " + id + " has been set to Inactive");
	}

	// 🚫 Activate a patient (set status to Active)
	@PutMapping("/{id}/activate")
	public ResponseEntity<String> activatePatient(@PathVariable Long id) {
		patientService.activatePatient(id);
		return ResponseEntity.ok("Patient with ID " + id + " has been reactivated");
	}

}
