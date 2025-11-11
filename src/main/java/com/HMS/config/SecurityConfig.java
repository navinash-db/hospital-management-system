package com.HMS.config;

import com.HMS.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private final UserService userService;

    public SecurityConfig(UserService userService) {
        this.userService = userService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // Public pages
                        .requestMatchers(
                                "/auth/**",
                                "/login.html",
                                "/styles.css",
                                "/js/**",
                                "/images/**",
                                "/favicon.ico")
                        .permitAll()

                        // Allow both ADMIN and RECEPTIONIST to view doctors
                        .requestMatchers("/doctors.html", "/api/doctors/**").hasAnyRole("ADMIN", "RECEPTIONIST")

                        // Allow both roles access to these
                        .requestMatchers(
                                "/patients.html", "/api/patients/**",
                                "/api/appointments/**",
                                "/api/billings/**")
                        .hasAnyRole("ADMIN", "RECEPTIONIST")

                        // Any other page requires login
                        .anyRequest().authenticated())

                // Form-based login
                .formLogin(form -> form
                        .loginPage("/login.html")
                        .loginProcessingUrl("/auth/login")
                        .defaultSuccessUrl("/redirect", true)
                        .failureUrl("/login.html?error=true")
                        .permitAll())

                // Logout
                .logout(logout -> logout
                        .logoutUrl("/auth/logout")
                        .logoutSuccessUrl("/login.html?logout=true")
                        .permitAll())

                // Session-based login
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))

                // Authentication provider
                .authenticationProvider(daoAuthenticationProvider());

        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}