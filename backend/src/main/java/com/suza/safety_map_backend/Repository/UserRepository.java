package com.suza.safety_map_backend.Repository;

import com.suza.safety_map_backend.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByUsername(String username);
    Optional<User> findByRegNumber(String regNumber);

    @Query("SELECT u FROM User u WHERE u.regNumber = :identifier OR u.username = :identifier")
    Optional<User> findByIdentifier(@Param("identifier") String identifier);

    // Monthly rating query (unchanged, assuming ratings table exists)
    @Query(value = "SELECT h.id, h.name, AVG(r.rating) as avg_rating " +
            "FROM hostels h " +
            "JOIN ratings r ON h.id = r.hostel_id " +
            "WHERE DATE_TRUNC('month', r.created_at) = DATE_TRUNC('month', CURRENT_DATE) " +
            "GROUP BY h.id, h.name " +
            "ORDER BY avg_rating DESC",
            nativeQuery = true)
    List<Object[]> getMonthlyRankedHostels();


    boolean existsByUsername(String username);
    boolean existsByRegNumber(String regNumber);
}