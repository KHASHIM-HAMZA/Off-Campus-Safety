//package com.suza.safety_map_backend.Daata;
//
//import com.suza.safety_map_backend.Entity.CrimeReport;
//import com.suza.safety_map_backend.Entity.Hostel;
//import com.suza.safety_map_backend.Entity.User;
//import com.suza.safety_map_backend.Repository.CrimeReportRepository;
//import com.suza.safety_map_backend.Repository.HostelRepository;
//import com.suza.safety_map_backend.Repository.UserRepository;
//import lombok.RequiredArgsConstructor;
//import org.locationtech.jts.geom.Coordinate;
//import org.locationtech.jts.geom.GeometryFactory;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.transaction.annotation.Transactional;
//
//@Configuration
//@RequiredArgsConstructor
//public class DataSeeder {
//
//    private final UserRepository userRepository;
//    private final HostelRepository hostelRepository;
//    private final CrimeReportRepository crimeReportRepository;
//    private final PasswordEncoder passwordEncoder;
//
//    @Bean
//    @Transactional
//    CommandLineRunner initDatabase() {
//        return args -> {
//            if (userRepository.count() == 0) {
//                GeometryFactory geometryFactory = new GeometryFactory();
//
//                // ---- STUDENT ----
//                User student = new User();
//                student.setRegNumber("BITAM/10/22/121/tz");
//                student.setUsername(null);
//                student.setPassword(passwordEncoder.encode("student123"));
//                student.setRole(User.Role.STUDENT);
//                student.setEmail("student@suza.ac.tz");
//                student.setPhone("+255123456789");
//                userRepository.save(student);
//
//                // ---- OWNER ----
//                User owner = new User();
//                owner.setRegNumber(null);
//                owner.setUsername("hostelowner");
//                owner.setPassword(passwordEncoder.encode("owner123"));
//                owner.setRole(User.Role.OWNER);
//                owner.setEmail("owner@hostels.com");
//                owner.setPhone("+255987654321");
//                owner.setId(2L); // Explicitly set id to match your data
//                userRepository.save(owner);
//
//                // ---- ADMIN ----
//                User admin = new User();
//                admin.setRegNumber(null);
//                admin.setUsername("admin");
//                admin.setPassword(passwordEncoder.encode("admin123"));
//                admin.setRole(User.Role.ADMIN);
//                admin.setEmail("admin@safetymap.com");
//                admin.setPhone("+255111223344");
//                userRepository.save(admin);
//
//                // ---- HOSTELS ----
//                Hostel h1 = new Hostel();
//                h1.setName("Kilimanjaro Hostel");
//                h1.setOwner(owner); // Use the saved owner
//                h1.setDescription("Comfortable hostel with great amenities");
//                h1.setCapacity(50);
//                h1.setPrice(250000.0);
//                h1.setRating(4);
//                h1.setLocation(geometryFactory.createPoint(new Coordinate(39.5100, -6.2000)));
//                h1.setDistanceFromCampus(0.5);
//                hostelRepository.save(h1);
//
//                // ---- CRIME REPORTS ----
//                CrimeReport c1 = new CrimeReport();
//                c1.setType("Theft");
//                c1.setDescription("Phone snatching reported near campus gate");
//                c1.setReporterComment("I saw two young men on a motorcycle snatch a phone");
//                c1.setReporter(student);
//                c1.setApproved(true);
//                c1.setLocation(geometryFactory.createPoint(new Coordinate(39.5120, -6.2025)));
//                crimeReportRepository.save(c1);
//
//                System.out.println("✅ Sample data inserted!");
//            }
//        };
//    }
//}