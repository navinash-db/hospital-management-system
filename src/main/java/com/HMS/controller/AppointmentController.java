package com.HMS.controller;

import com.HMS.dto.AppointmentDTO;
import com.HMS.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
@CrossOrigin("*")
public class AppointmentController {

    @Autowired
    private AppointmentService service;

    @GetMapping
    public List<AppointmentDTO> getAllAppointments() {
        return service.getAllAppointments();
    }

    @PostMapping
    public AppointmentDTO createAppointment(@RequestBody AppointmentDTO dto) {
        return service.createAppointment(dto);
    }

    @PutMapping("/{id}")
    public AppointmentDTO updateAppointment(@PathVariable Long id, @RequestBody AppointmentDTO dto) {
        return service.updateAppointment(id, dto);
    }

    @DeleteMapping("/{id}")
    public String deleteAppointment(@PathVariable Long id) {
        return service.deleteAppointment(id) ? "Deleted Successfully" : "Not Found";
    }
}
