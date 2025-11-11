package com.HMS.config;

import com.HMS.entity.Role;
import com.HMS.entity.User;
import com.HMS.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DefaultUserConfig {

    @Bean
    CommandLineRunner initDefaultUsers(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (userRepository.findByUsername("admin").isEmpty()) {
                User admin = new User();
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setRole(Role.ADMIN);
                userRepository.save(admin);
                System.out.println("✅ Default Admin user created: admin / admin123");
            }

            if (userRepository.findByUsername("reception1").isEmpty()) {
                User recep = new User();
                recep.setUsername("reception1");
                recep.setPassword(passwordEncoder.encode("recep123"));
                recep.setRole(Role.RECEPTIONIST);
                userRepository.save(recep);
                System.out.println("✅ Default Receptionist created: reception1 / recep123");
            }
        };
    }
}
