package com.HMS.controller;

import com.HMS.dto.BillingDTO;
import com.HMS.entity.Billing;
import com.HMS.service.BillingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/billings")
@CrossOrigin("*")
public class BillingController {

    @Autowired
    private BillingService billingService;

    @GetMapping
    public List<Billing> getAllBills() {
        return billingService.getAllBills();
    }

    @GetMapping("/{id}")
    public Optional<Billing> getBillById(@PathVariable Long id) {
        return billingService.getBillById(id);
    }

    @PostMapping
    public Billing createBill(@RequestBody BillingDTO dto) {
        return billingService.createBill(dto);
    }

    @PutMapping("/{id}")
    public Billing updateBill(@PathVariable Long id, @RequestBody BillingDTO dto) {
        return billingService.updateBill(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteBill(@PathVariable Long id) {
        billingService.deleteBill(id);
    }
}
