package com.HMS.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.HMS.entity.Patient;

import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    // 🔍 Find patients by name (case-insensitive search)
    List<Patient> findByNameContainingIgnoreCase(String name);

    // 🔍 Find patient by phone number
    Patient findByPhoneNumber(String phoneNumber);

    // 🔍 Find patients by status (Active or Inactive)
    List<Patient> findByStatus(String status);

    // 🔍 Find patients by gender
    List<Patient> findByGender(String gender);
}