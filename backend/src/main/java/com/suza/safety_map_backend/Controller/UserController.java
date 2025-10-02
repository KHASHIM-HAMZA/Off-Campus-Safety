package com.suza.safety_map_backend.Controller;

import com.suza.safety_map_backend.Entity.AccidentHotspot;
import com.suza.safety_map_backend.Entity.Hostel;
import com.suza.safety_map_backend.Entity.User;
import com.suza.safety_map_backend.Service.CrimeReportService;
import com.suza.safety_map_backend.Service.HostelService;
import com.suza.safety_map_backend.Service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final HostelService hostelService;
    private final CrimeReportService crimeReportService;

    public UserController(UserService userService, HostelService hostelService, CrimeReportService crimeReportService) {
        this.userService = userService;
        this.hostelService = hostelService;
        this.crimeReportService = crimeReportService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        try {
            // Validate role-specific fields
            if (user.getRole() == null) {
                return ResponseEntity.badRequest().body("Role is required");
            }http://localhost:8081/api/hostels/top-rated

            if (User.Role.STUDENT.equals(user.getRole())) {
                if (user.getRegNumber() == null || user.getRegNumber().trim().isEmpty()) {
                    return ResponseEntity.badRequest().body("Registration number is required for students");
                }
                if (user.getUsername() != null) {
                    return ResponseEntity.badRequest().body("Username must be null for students");
                }
            } else if (User.Role.OWNER.equals(user.getRole()) || User.Role.ADMIN.equals(user.getRole())) {
                if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
                    return ResponseEntity.badRequest().body("Username is required for owners/admins");
                }
                if (user.getRegNumber() != null) {
                    return ResponseEntity.badRequest().body("Registration number must be null for owners/admins");
                }
            }

            // Register the user
            User savedUser = userService.registerUser(user);
            return ResponseEntity.ok(savedUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Registration failed: " + e.getMessage());
        }
    }

    @GetMapping("/{regNo}")
    public ResponseEntity<User> getByRegNo(@PathVariable String regNo) {
        Optional<User> user = userService.findByRegNo(regNo);
        return user.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/all")
    public ResponseEntity<List<User>> getAll() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PostMapping("/hostels/register")
    public ResponseEntity<Hostel> registerHostel(@RequestBody Hostel hostel, Authentication authentication) {
        User owner = userService.findByIdentifier(authentication.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        hostel.setOwner(owner);
        return ResponseEntity.ok(hostelService.registerHostel(hostel));
    }

    @GetMapping("/unsafe-areas")
    public ResponseEntity<List<AccidentHotspot>> getUnsafeAreas() {
        return ResponseEntity.ok(crimeReportService.getUnsafeAreas());
    }
}