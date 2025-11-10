package com.HMS.service;

import com.HMS.dto.AppointmentDTO;
import com.HMS.entity.Appointment;
import com.HMS.entity.Doctor;
import com.HMS.entity.Patient;
import com.HMS.repository.AppointmentRepository;
import com.HMS.repository.DoctorRepository;
import com.HMS.repository.PatientRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepo;

    @Autowired
    private DoctorRepository doctorRepo;

    @Autowired
    private PatientRepository patientRepo;

    /** 🔹 Get all appointments (returns DTO list) */
    public List<AppointmentDTO> getAllAppointments() {
        return appointmentRepo.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /** 🔹 Create a new appointment */
    public AppointmentDTO createAppointment(AppointmentDTO dto) {
        Doctor doctor = doctorRepo.findById(dto.getDoctorId())
                .orElseThrow(() -> new NoSuchElementException("Doctor not found with ID: " + dto.getDoctorId()));

        Patient patient = patientRepo.findById(dto.getPatientId())
                .orElseThrow(() -> new NoSuchElementException("Patient not found with ID: " + dto.getPatientId()));

        Appointment appointment = new Appointment();
        appointment.setDoctor(doctor);
        appointment.setPatient(patient);
        appointment.setAppointmentDate(dto.getAppointmentDate());
        appointment.setNotes(dto.getNotes());
        appointment.setStatus("Booked");

        Appointment saved = appointmentRepo.save(appointment);
        return convertToDTO(saved);
    }

    /** 🔹 Update an existing appointment */
    public AppointmentDTO updateAppointment(Long id, AppointmentDTO dto) {
        Appointment appointment = appointmentRepo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Appointment not found with ID: " + id));

        appointment.setAppointmentDate(dto.getAppointmentDate());
        appointment.setNotes(dto.getNotes());
        appointment.setStatus(dto.getStatus());

        Appointment updated = appointmentRepo.save(appointment);
        return convertToDTO(updated);
    }

    /** 🔹 Delete appointment */
    public boolean deleteAppointment(Long id) {
        if (!appointmentRepo.existsById(id)) return false;
        appointmentRepo.deleteById(id);
        return true;
    }

    /** 🔹 Convert Entity → DTO */
    private AppointmentDTO convertToDTO(Appointment appointment) {
        AppointmentDTO dto = new AppointmentDTO();
        dto.setAppointmentId(appointment.getAppointmentId());
        dto.setAppointmentDate(appointment.getAppointmentDate());
        dto.setNotes(appointment.getNotes());
        dto.setStatus(appointment.getStatus());

        if (appointment.getDoctor() != null) {
            dto.setDoctorId(appointment.getDoctor().getId());
            dto.setDoctorName(appointment.getDoctor().getDoctorName());
        }

        if (appointment.getPatient() != null) {
            dto.setPatientId(appointment.getPatient().getPatientId());
            dto.setPatientName(appointment.getPatient().getName());
        }

        return dto;
    }
}
