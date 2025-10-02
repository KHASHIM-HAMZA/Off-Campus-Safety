package com.suza.safety_map_backend.Repository;

import com.suza.safety_map_backend.Entity.CrimeReport;
import org.geolatte.geom.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CrimeReportRepository extends JpaRepository<CrimeReport, Long> {
        @Query(value = "SELECT COUNT(*) FROM crime_reports WHERE ST_DWithin(location, :loc, 100) AND approved = false", nativeQuery = true)
        long countReportsWithin100m(@Param("loc") Point location);

    List<CrimeReport> location(org.locationtech.jts.geom.Point location);

//crime report near student
    @Query(value = "SELECT c.* " +
            "FROM crime_reports c " +
            "WHERE ST_DWithin(c.location, ST_SetSRID(ST_MakePoint(:lng, :lat), 4326), :radius) " +
            "AND c.approved = true",
            nativeQuery = true)
    List<CrimeReport> findNearbyCrimes(@Param("lng") double lng,
                                       @Param("lat") double lat,
                                       @Param("radius") double radius);
}
