package com.suza.safety_map_backend.Service;


import com.suza.safety_map_backend.Entity.Comment;
import com.suza.safety_map_backend.Entity.Hostel;
import com.suza.safety_map_backend.Repository.HostelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HostelService {

    private final HostelRepository hostelRepository;

    // Find hostels near a given point
    public List<Object[]> getHostelsNearCampus(double lng, double lat, double radius) {
        return hostelRepository.findHostelsNearCampus(lng, lat, radius);
    }

    // Find top rated hostels
    public List<Hostel> getTopRatedHostels() {
        return hostelRepository.findTopRatedHostels();
    }

    // Find unsafe hostels (near crime reports)
    public List<Hostel> getHostelsNearCrimes() {
        return hostelRepository.findHostelsNearCrimes();
    }

    // Get monthly ranked hostels (by ratings)
    public List<Hostel> getMonthlyRankedHostels() {
        return hostelRepository.getMonthlyRankedHostels();
    }

    public Hostel registerHostel(Hostel hostel) {
        return hostelRepository.save(hostel);
    }


    public List<Comment> getCommentsByOwnerUsername(String ownerUsername) {
        return hostelRepository.findCommentsByOwnerUsername(ownerUsername);
    }

    public List<Hostel> ListHostels() {
        return hostelRepository.findAll();
    }
}
