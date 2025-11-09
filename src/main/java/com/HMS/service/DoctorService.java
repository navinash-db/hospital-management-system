package com.HMS.service;

import com.HMS.entity.Doctor;
import com.HMS.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    public Doctor addDoctor(Doctor doctor) {
        doctor.setStatus("Active");
        return doctorRepository.save(doctor);
    }

    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    public Doctor getDoctorById(Long id) {
        return doctorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor not found with ID: " + id));
    }

    public Doctor updateDoctor(Long id, Doctor updatedDoctor) {
        Doctor doctor = getDoctorById(id);
        doctor.setDoctorName(updatedDoctor.getDoctorName());
        doctor.setSpecialization(updatedDoctor.getSpecialization());
        doctor.setQualification(updatedDoctor.getQualification());
        doctor.setPhoneNumber(updatedDoctor.getPhoneNumber());
        doctor.setEmail(updatedDoctor.getEmail());
        doctor.setGender(updatedDoctor.getGender());
        doctor.setExperience(updatedDoctor.getExperience());
        doctor.setAddress(updatedDoctor.getAddress());
        return doctorRepository.save(doctor);
    }

    public Doctor setInactive(Long id) {
        Doctor doctor = getDoctorById(id);
        doctor.setStatus("Inactive");
        return doctorRepository.save(doctor);
    }

    public void deleteDoctor(Long id) {
        doctorRepository.deleteById(id);
    }
    public Doctor setActive(Long id) {
        Doctor doctor = getDoctorById(id);
        doctor.setStatus("Active");
        return doctorRepository.save(doctor);
    }

}
