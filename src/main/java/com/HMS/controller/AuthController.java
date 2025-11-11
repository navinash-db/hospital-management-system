package com.HMS.controller;

import com.HMS.entity.User;
import com.HMS.repository.UserRepository;
import com.HMS.service.UserService;
import com.HMS.util.JwtUtil;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    // ✅ Constructor injection
    public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository,
            UserService userService, JwtUtil jwtUtil, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public String register(@RequestBody User user) {
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "User registered successfully";
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
        System.out.println(">>> HIT /auth/login");
        try {
            String username = request.get("username");
            String password = request.get("password");

            if (username == null || password == null) {
                return ResponseEntity.badRequest().body(Map.of("error", "Username and password are required"));
            }

            // Authenticate credentials
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password));

            // Fetch user
            User user = userRepository.findByUsername(username).orElse(null);
            if (user != null) {
                System.out.println("DB HASH: " + user.getPassword());
                System.out.println("MATCH: " + passwordEncoder.matches(password, user.getPassword()));
            }

            // Generate JWT
            String token = jwtUtil.generateToken(user.getUsername(), user.getRole().name());

            // ✅ Return proper JSON with status 200 OK
            return ResponseEntity.ok(Map.of(
                    "token", token,
                    "role", user.getRole().name(),
                    "username", user.getUsername()));

        } catch (BadCredentialsException e) {
            System.out.println("Navi");
            // ❌ Wrong username/password
            return ResponseEntity.status(401).body(Map.of("error", "Invalid username or password"));
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(404).body(Map.of("error", "User not found"));
        } catch (Exception e) {
            e.printStackTrace(); // Keep for debugging
            return ResponseEntity.status(500).body(Map.of("error", "Server error"));
        }
    }
}