package com.HMS.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "billing")
public class Billing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long billId;

    // 🧍 Patient details (no need to enter ID manually)
    private Long patientId;
    private String patientName;
    private String patientPhone;

    // 📅 Optional link to appointment
    private Long appointmentId;

    // 💰 Charges
    private Double consultationFee;
    private Double medicineCharges;
    private Double labCharges;
    private Double totalAmount;

    // 💳 Payment
    private String paymentStatus; // e.g., Paid / Pending / Cancelled
    private LocalDateTime billingDate = LocalDateTime.now();

    // ---------- Getters & Setters ----------

    public Long getBillId() {
        return billId;
    }

    public void setBillId(Long billId) {
        this.billId = billId;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPatientPhone() {
        return patientPhone;
    }

    public void setPatientPhone(String patientPhone) {
        this.patientPhone = patientPhone;
    }

    public Long getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(Long appointmentId) {
        this.appointmentId = appointmentId;
    }

    public Double getConsultationFee() {
        return consultationFee;
    }

    public void setConsultationFee(Double consultationFee) {
        this.consultationFee = consultationFee;
    }

    public Double getMedicineCharges() {
        return medicineCharges;
    }

    public void setMedicineCharges(Double medicineCharges) {
        this.medicineCharges = medicineCharges;
    }

    public Double getLabCharges() {
        return labCharges;
    }

    public void setLabCharges(Double labCharges) {
        this.labCharges = labCharges;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public LocalDateTime getBillingDate() {
        return billingDate;
    }

    public void setBillingDate(LocalDateTime billingDate) {
        this.billingDate = billingDate;
    }
}
