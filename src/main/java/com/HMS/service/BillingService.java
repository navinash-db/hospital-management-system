package com.HMS.service;

import com.HMS.dto.BillingDTO;
import com.HMS.entity.Billing;
import com.HMS.entity.Patient;
import com.HMS.repository.BillingRepository;
import com.HMS.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class BillingService {

    @Autowired
    private BillingRepository billingRepository;

    @Autowired
    private PatientRepository patientRepository;

    // 🔹 Get all bills
    public List<Billing> getAllBills() {
        return billingRepository.findAll();
    }

    // 🔹 Get bill by ID
    public Optional<Billing> getBillById(Long billId) {
        return billingRepository.findById(billId);
    }

    // 🔹 Create new billing record
    public Billing createBill(BillingDTO dto) {
        Billing bill = new Billing();

        // ✅ Find patient using name & phone number
        Optional<Patient> patientOpt = patientRepository.findByNameAndPhoneNumber(
                dto.getPatientName(), dto.getPatientPhone()
        );

        if (patientOpt.isEmpty()) {
            throw new RuntimeException("Patient not found with given name and phone number");
        }

        Patient patient = patientOpt.get();

        // 🧍 Save both patientId and readable info
        bill.setPatientId(patient.getPatientId());
        bill.setPatientName(patient.getName());
        bill.setPatientPhone(patient.getPhoneNumber());

        // 💰 Set charge details
        bill.setAppointmentId(dto.getAppointmentId());
        bill.setConsultationFee(dto.getConsultationFee());
        bill.setMedicineCharges(dto.getMedicineCharges());
        bill.setLabCharges(dto.getLabCharges());

        // 🧮 Calculate total amount
        double total = (dto.getConsultationFee() != null ? dto.getConsultationFee() : 0)
                + (dto.getMedicineCharges() != null ? dto.getMedicineCharges() : 0)
                + (dto.getLabCharges() != null ? dto.getLabCharges() : 0);

        bill.setTotalAmount(total);

        // 💳 Payment + Date
        bill.setPaymentStatus(dto.getPaymentStatus());
        bill.setBillingDate(dto.getBillingDate() != null ? dto.getBillingDate() : LocalDateTime.now());

        return billingRepository.save(bill);
    }

    // 🔹 Update existing billing record
    public Billing updateBill(Long id, BillingDTO dto) {
        return billingRepository.findById(id).map(bill -> {

            // ✅ Update patient details if provided
            if (dto.getPatientName() != null && dto.getPatientPhone() != null) {
                Optional<Patient> patientOpt = patientRepository.findByNameAndPhoneNumber(
                        dto.getPatientName(), dto.getPatientPhone()
                );
                if (patientOpt.isEmpty()) {
                    throw new RuntimeException("Patient not found with given name and phone number");
                }

                Patient patient = patientOpt.get();
                bill.setPatientId(patient.getPatientId());
                bill.setPatientName(patient.getName());
                bill.setPatientPhone(patient.getPhoneNumber());
            }

            // 💰 Update charge fields
            bill.setConsultationFee(dto.getConsultationFee());
            bill.setMedicineCharges(dto.getMedicineCharges());
            bill.setLabCharges(dto.getLabCharges());

            // 🧮 Recalculate total
            double total = (dto.getConsultationFee() != null ? dto.getConsultationFee() : 0)
                    + (dto.getMedicineCharges() != null ? dto.getMedicineCharges() : 0)
                    + (dto.getLabCharges() != null ? dto.getLabCharges() : 0);
            bill.setTotalAmount(total);

            // 💳 Payment
            bill.setPaymentStatus(dto.getPaymentStatus());
            bill.setBillingDate(dto.getBillingDate() != null ? dto.getBillingDate() : bill.getBillingDate());

            return billingRepository.save(bill);
        }).orElseThrow(() -> new RuntimeException("Bill not found"));
    }

    // 🔹 Delete bill
    public void deleteBill(Long id) {
        billingRepository.deleteById(id);
    }
}
