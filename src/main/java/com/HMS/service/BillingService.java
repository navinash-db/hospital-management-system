package com.HMS.service;

import com.HMS.dto.BillingDTO;
import com.HMS.entity.Billing;
import com.HMS.repository.BillingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BillingService {

    @Autowired
    private BillingRepository billingRepository;

    public List<Billing> getAllBills() {
        return billingRepository.findAll();
    }

    public Optional<Billing> getBillById(Long billId) {
        return billingRepository.findById(billId);
    }

    public Billing createBill(BillingDTO dto) {
        Billing bill = new Billing();
        bill.setPatientId(dto.getPatientId());
        bill.setAppointmentId(dto.getAppointmentId());
        bill.setConsultationFee(dto.getConsultationFee());
        bill.setMedicineCharges(dto.getMedicineCharges());
        bill.setLabCharges(dto.getLabCharges());

        double total = (dto.getConsultationFee() != null ? dto.getConsultationFee() : 0)
                + (dto.getMedicineCharges() != null ? dto.getMedicineCharges() : 0)
                + (dto.getLabCharges() != null ? dto.getLabCharges() : 0);

        bill.setTotalAmount(total);
        bill.setPaymentStatus(dto.getPaymentStatus());
        bill.setBillingDate(dto.getBillingDate() != null ? dto.getBillingDate() : bill.getBillingDate());

        return billingRepository.save(bill);
    }

    public Billing updateBill(Long id, BillingDTO dto) {
        return billingRepository.findById(id).map(bill -> {
            bill.setConsultationFee(dto.getConsultationFee());
            bill.setMedicineCharges(dto.getMedicineCharges());
            bill.setLabCharges(dto.getLabCharges());
            bill.setPaymentStatus(dto.getPaymentStatus());
            double total = (dto.getConsultationFee() != null ? dto.getConsultationFee() : 0)
                    + (dto.getMedicineCharges() != null ? dto.getMedicineCharges() : 0)
                    + (dto.getLabCharges() != null ? dto.getLabCharges() : 0);
            bill.setTotalAmount(total);
            return billingRepository.save(bill);
        }).orElseThrow(() -> new RuntimeException("Bill not found"));
    }

    public void deleteBill(Long id) {
        billingRepository.deleteById(id);
    }
}
