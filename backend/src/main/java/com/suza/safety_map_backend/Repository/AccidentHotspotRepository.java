package com.suza.safety_map_backend.Repository;

import com.suza.safety_map_backend.Entity.AccidentHotspot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccidentHotspotRepository extends JpaRepository<AccidentHotspot, Long> {
    // Custom spatial queries later
}
