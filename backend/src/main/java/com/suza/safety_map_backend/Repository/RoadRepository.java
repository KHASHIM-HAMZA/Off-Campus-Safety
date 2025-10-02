package com.suza.safety_map_backend.Repository;

import com.suza.safety_map_backend.Entity.Road;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoadRepository extends JpaRepository<Road, Long> {
}
