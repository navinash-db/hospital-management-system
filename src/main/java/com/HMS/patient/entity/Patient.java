package com.HMS.patient.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDateTime;

@Entity
@Table(name = "patient")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long patientId;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false)
    private Integer age;

    @Column(length = 255)
    private String address;

    @Pattern(regexp = "^[6-9]\\d{9}$", message = "Invalid phone number! Must be 10 digits starting with 6-9")
    @Column(name = "phone_number", nullable = false, unique = true, length = 15)
    private String phoneNumber;


    @Column(nullable = false, length = 10)
    private String gender;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @Column(nullable = false)
    private String status = "Active"; // Active / Inactive

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    // ✅ Constructors
    public Patient() {}

    public Patient(String name, Integer age, String address, String phoneNumber, 
                   String gender, String notes, String status) {
        this.name = name;
        this.age = age;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.notes = notes;
        this.status = status;
    }

    // ✅ Getters and Setters
    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
