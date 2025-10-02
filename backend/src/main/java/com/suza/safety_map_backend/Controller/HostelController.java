package com.suza.safety_map_backend.Controller;


import com.suza.safety_map_backend.Entity.Comment;
import com.suza.safety_map_backend.Entity.Hostel;
import com.suza.safety_map_backend.Service.HostelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api/hostels")
@RequiredArgsConstructor
public class HostelController {

    private final HostelService hostelService;

    @GetMapping("/all-hostels")
    public List<Hostel> ListHostels(){
        return hostelService.ListHostels();
    }

    // GET /api/hostels/near-campus?lng=39.50&lat=-6.20&radius=2000
    @GetMapping("/near-campus")
    public ResponseEntity<List<Object[]>> getHostelsNearCampus(
            @RequestParam double lng,
            @RequestParam double lat,
            @RequestParam(defaultValue = "2000") double radius) {
        return ResponseEntity.ok(hostelService.getHostelsNearCampus(lng, lat, radius));
    }

    // GET /api/hostels/top-rated
    @GetMapping("/top-rated")
    public ResponseEntity<List<Hostel>> getTopRatedHostels() {
        return ResponseEntity.ok(hostelService.getTopRatedHostels());
    }

    // GET /api/hostels/unsafe
    @GetMapping("/unsafe")
    public ResponseEntity<List<Hostel>> getHostelsNearCrimes() {
        return ResponseEntity.ok(hostelService.getHostelsNearCrimes());
    }

    // GET /api/hostels/monthly-ranking
    @GetMapping("/monthly-ranking")
    public ResponseEntity<List<Hostel>> getMonthlyRankedHostels() {
        return ResponseEntity.ok(hostelService.getMonthlyRankedHostels());
    }

    @GetMapping("/comments/owner")
    public ResponseEntity<List<Comment>> getCommentsByOwner(@RequestParam String ownerUsername) {
        List<Comment> comments = hostelService.getCommentsByOwnerUsername(ownerUsername);
        return ResponseEntity.ok(comments);
    }
}

