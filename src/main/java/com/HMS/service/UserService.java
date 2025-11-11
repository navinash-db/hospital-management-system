package com.HMS.service;

import com.HMS.entity.User;
import com.HMS.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 🔍 Find user by username
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        // ✅ Add "ROLE_" prefix to make Spring Security recognize the role
        String roleName = user.getRole().name(); // e.g., ADMIN or RECEPTIONIST
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + roleName);

        // ✅ Return a Spring Security user with correct role
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                List.of(authority));
    }
}