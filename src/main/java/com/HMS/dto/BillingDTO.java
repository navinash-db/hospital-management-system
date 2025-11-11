package com.HMS.dto;

import java.time.LocalDateTime;

public class BillingDTO {

	private Long billId;
	private Long appointmentId;

	private String patientName; // ✅ added
	private String patientPhone; // ✅ added

	private Double consultationFee;
	private Double medicineCharges;
	private Double labCharges;
	private Double totalAmount;

	private String paymentStatus;
	private LocalDateTime billingDate;

	// Getters & Setters
	public Long getBillId() {
		return billId;
	}

	public void setBillId(Long billId) {
		this.billId = billId;
	}

	public Long getAppointmentId() {
		return appointmentId;
	}

	public void setAppointmentId(Long appointmentId) {
		this.appointmentId = appointmentId;
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
