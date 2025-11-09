package com.HMS.repository;

import com.HMS.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByDoctor_DoctorId(Long doctorId);
    List<Appointment> findByPatient_PatientId(Long patientId);
}
