package com.HMS.service;

import com.HMS.dto.AppointmentDTO;
import com.HMS.entity.Appointment;
import com.HMS.repository.AppointmentRepository;
import com.HMS.entity.Doctor;
import com.HMS.entity.Patient;
import com.HMS.repository.DoctorRepository;
import com.HMS.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepo;

    @Autowired
    private DoctorRepository doctorRepo;

    @Autowired
    private PatientRepository patientRepo;

    // List all appointments
    public List<AppointmentDTO> getAllAppointments() {
        return appointmentRepo.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    // Create appointment
    public AppointmentDTO createAppointment(AppointmentDTO dto) {
        Doctor doctor = doctorRepo.findById(dto.getDoctorId()).orElseThrow(() -> new RuntimeException("Doctor not found"));
        Patient patient = patientRepo.findById(dto.getPatientId()).orElseThrow(() -> new RuntimeException("Patient not found"));

        Appointment appointment = new Appointment();
        appointment.setDoctor(doctor);
        appointment.setPatient(patient);
        appointment.setAppointmentDate(dto.getAppointmentDate());
        appointment.setNotes(dto.getNotes());
        appointment.setStatus("Booked");

        return convertToDTO(appointmentRepo.save(appointment));
    }

    // Update appointment
    public AppointmentDTO updateAppointment(Long id, AppointmentDTO dto) {
        Appointment appointment = appointmentRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));
        appointment.setNotes(dto.getNotes());
        appointment.setStatus(dto.getStatus());
        appointment.setAppointmentDate(dto.getAppointmentDate());
        return convertToDTO(appointmentRepo.save(appointment));
    }

    // Delete appointment
    public boolean deleteAppointment(Long id) {
        if (appointmentRepo.existsById(id)) {
            appointmentRepo.deleteById(id);
            return true;
        }
        return false;
    }

    // Convert entity → DTO
    private AppointmentDTO convertToDTO(Appointment a) {
        AppointmentDTO dto = new AppointmentDTO();
        dto.setAppointmentId(a.getAppointmentId());
        dto.setDoctorId(a.getDoctor().getId());
        dto.setDoctorName(a.getDoctor().getDoctorName());
        dto.setPatientId(a.getPatient().getPatientId());
        dto.setPatientName(a.getPatient().getName());
        dto.setAppointmentDate(a.getAppointmentDate());
        dto.setNotes(a.getNotes());
        dto.setStatus(a.getStatus());
        return dto;
    }
}
