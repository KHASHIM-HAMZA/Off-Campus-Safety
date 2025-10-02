package com.suza.safety_map_backend.Repository;

import com.suza.safety_map_backend.Entity.HostelApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HostelApplicationRepository extends JpaRepository<HostelApplication, Long> {
}
