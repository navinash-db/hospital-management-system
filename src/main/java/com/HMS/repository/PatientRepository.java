package com.HMS.repository;

import com.HMS.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

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

    // 🔍 Find patient by name and phone number (case-insensitive name match)
    @Query("SELECT p FROM Patient p WHERE LOWER(p.name) = LOWER(:name) AND p.phoneNumber = :phone")
    Optional<Patient> findByNameAndPhoneNumber(@Param("name") String name, @Param("phone") String phone);
}