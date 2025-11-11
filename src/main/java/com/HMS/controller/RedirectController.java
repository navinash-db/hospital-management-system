package com.HMS.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RedirectController {

    @GetMapping("/redirect")
    public String redirectByRole(HttpServletRequest request) {
        if (request.isUserInRole("ROLE_ADMIN")) {
            // Redirect admin to doctor management
            return "redirect:/doctors.html";
        } else if (request.isUserInRole("ROLE_RECEPTIONIST")) {
            // Redirect receptionist to patient management
            return "redirect:/patients.html";
        } else {
            // Default fallback
            return "redirect:/login.html?error=unauthorized";
        }
    }
}