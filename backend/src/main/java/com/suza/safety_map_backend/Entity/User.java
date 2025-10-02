package com.suza.safety_map_backend.Entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@Data
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "reg_number", unique = true, nullable = true)
    private String regNumber; // Only for students, nullable for others

    @Column(unique = true, nullable = true)
    private String username; // For owners/admins, nullable for students

    @Column(nullable = false)
    private String password;

    private String email;
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role; // STUDENT, OWNER, ADMIN

    public enum Role {
        STUDENT, OWNER, ADMIN
    }

    // Business logic validation
    @PrePersist
    @PreUpdate
    public void validateUser() {
        if (role == Role.STUDENT) {
            if (regNumber == null || regNumber.trim().isEmpty()) {
                throw new IllegalArgumentException("Student must have a registration number");
            }
            // Username should be null for students
            this.username = null;
        } else {
            if (username == null || username.trim().isEmpty()) {
                throw new IllegalArgumentException("Owner/Admin must have a username");
            }
            // RegNumber should be null for non-students
            this.regNumber = null;
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    @Override
    public String getUsername() {
        // For Spring Security, return the appropriate identifier based on role
        if (role == Role.STUDENT) {
            return regNumber;
        } else {
            return username;
        }
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    // Helper method to get login identifier (for JWT)
    public String getLoginIdentifier() {
        return getUsername(); // This uses the overridden getUsername method
    }
}