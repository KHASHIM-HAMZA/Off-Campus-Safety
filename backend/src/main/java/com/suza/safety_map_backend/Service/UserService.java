package com.suza.safety_map_backend.Service;

import com.suza.safety_map_backend.Entity.User;
import com.suza.safety_map_backend.Repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(User user) {
        if (user.getRole() == User.Role.STUDENT) {
            if (user.getRegNumber() == null || user.getRegNumber().trim().isEmpty()) {
                throw new IllegalArgumentException("Registration number is required for students");
            }
            user.setUsername(null);
        } else if (user.getRole() == User.Role.OWNER || user.getRole() == User.Role.ADMIN) {
            if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
                throw new IllegalArgumentException("Username is required for owners/admins");
            }
            user.setRegNumber(null);
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public Optional<User> findByRegNo(String regNo) {
        return userRepository.findByRegNumber(regNo);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> findByIdentifier(String identifier) {
        return userRepository.findByUsername(identifier)
                .or(() -> userRepository.findByRegNumber(identifier));
    }
}
