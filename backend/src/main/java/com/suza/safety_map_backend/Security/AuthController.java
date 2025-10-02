package com.suza.safety_map_backend.Security;

import com.suza.safety_map_backend.Entity.User;
import com.suza.safety_map_backend.Repository.UserRepository;
import com.suza.safety_map_backend.Security.JwtUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getIdentifier(),
                            loginRequest.getPassword()
                    )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            User user = userRepository.findByIdentifier(loginRequest.getIdentifier())
                    .orElseThrow(() -> new UsernameNotFoundException("User not found: " + loginRequest.getIdentifier()));

            String token = jwtUtil.generateToken(user.getLoginIdentifier(), user.getRole().name());

            return ResponseEntity.ok(new JwtResponse(
                    token,
                    user.getRole().name(),
                    user.getRegNumber(),
                    user.getUsername(),
                    user.getId()
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials: " + e.getMessage());
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        try {
            // Validate role-specific requirements
            if (registerRequest.getRole() == User.Role.STUDENT) {
                if (registerRequest.getRegNumber() == null || registerRequest.getRegNumber().trim().isEmpty()) {
                    return ResponseEntity.badRequest().body("Registration number is required for students");
                }
                if (userRepository.existsByRegNumber(registerRequest.getRegNumber())) {
                    return ResponseEntity.badRequest().body("Registration number already exists");
                }
            } else {
                if (registerRequest.getUsername() == null || registerRequest.getUsername().trim().isEmpty()) {
                    return ResponseEntity.badRequest().body("Username is required for owners/admins");
                }
                if (userRepository.existsByUsername(registerRequest.getUsername())) {
                    return ResponseEntity.badRequest().body("Username already exists");
                }
            }

            User user = new User();
            user.setRole(registerRequest.getRole());

            if (registerRequest.getRole() == User.Role.STUDENT) {
                user.setRegNumber(registerRequest.getRegNumber());
                user.setUsername(null); // Ensure username is null for students
            } else {
                user.setUsername(registerRequest.getUsername());
                user.setRegNumber(null); // Ensure regNumber is null for non-students
            }

            user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
            user.setEmail(registerRequest.getEmail());
            user.setPhone(registerRequest.getPhone());

            User savedUser = userRepository.save(user);

            // Generate JWT token
            String token = jwtUtil.generateToken(savedUser.getLoginIdentifier(), savedUser.getRole().name());
            return ResponseEntity.ok(new JwtResponse(
                    token,
                    savedUser.getRole().name(),
                    savedUser.getRegNumber(),
                    savedUser.getUsername(),
                    savedUser.getId()
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Registration failed: " + e.getMessage());
        }
    }

    @GetMapping("/me")
    public ResponseEntity<JwtResponse> getCurrentUser(Authentication authentication) {
        User user = userRepository.findByIdentifier(authentication.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return ResponseEntity.ok(new JwtResponse(
                null, user.getRole().name(), user.getRegNumber(), user.getUsername(), user.getId()
        ));
    }

    @Data
    public static class LoginRequest {
        private String identifier;
        private String password;
    }

    @Data
    public static class RegisterRequest {
        private String username;
        private String regNumber;
        private String password;
        private String email;
        private String phone;
        private User.Role role;
    }

    @Data
    @AllArgsConstructor
    public static class JwtResponse {
        private String token;
        private String role;
        private String regNumber;
        private String username;
        private Long userId;
    }
}