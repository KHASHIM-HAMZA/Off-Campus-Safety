package com.suza.safety_map_backend.Repository;


import com.suza.safety_map_backend.Entity.Comment;
import com.suza.safety_map_backend.Entity.CrimeReport;
import com.suza.safety_map_backend.Entity.Hostel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface HostelRepository extends JpaRepository<Hostel, Long> {

    // Find hostels within X meters from given point
    @Query(value = "SELECT h.*, ST_Distance(h.location, ST_SetSRID(ST_MakePoint(:lng, :lat), 4326)) AS distance " +
            "FROM hostels h " +
            "WHERE ST_DWithin(h.location, ST_SetSRID(ST_MakePoint(:lng, :lat), 4326), :radius) " +
            "ORDER BY distance ASC",
            nativeQuery = true)
    List<Object[]> findHostelsNearCampus(@Param("lng") double lng,
                                         @Param("lat") double lat,
                                         @Param("radius") double radius);
// find hostels with high ratings
    @Query("SELECT h FROM Hostel h ORDER BY h.rating DESC")
    List<Hostel> findTopRatedHostels();

    // find near crime areas
    @Query(value = "SELECT h.* " +
            "FROM hostels h " +
            "JOIN crime_reports c ON ST_DWithin(h.location, c.location, 300) " +
            "WHERE c.approved = true",
            nativeQuery = true)
    List<Hostel> findHostelsNearCrimes();


    @Query("SELECT h FROM Hostel h ORDER BY h.rating DESC")
    List<Hostel> getMonthlyRankedHostels();


    @Query("SELECT c FROM Comment c JOIN c.hostel h JOIN h.owner u WHERE u.username = :ownerUsername")
    List<Comment> findCommentsByOwnerUsername(@Param("ownerUsername") String ownerUsername);
}