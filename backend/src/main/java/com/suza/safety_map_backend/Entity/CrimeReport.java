package com.suza.safety_map_backend.Entity;


import jakarta.persistence.*;
import lombok.Data;
import org.locationtech.jts.geom.Point;

import java.time.LocalDateTime;

@Entity
@Table(name = "crime_reports")
@Data
public class CrimeReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String type;
    private String description;
    private String reporterComment;
    @Column(columnDefinition = "geometry(Point,4326)")
    private Point location;
    @ManyToOne
    @JoinColumn(name = "reporter_id", referencedColumnName = "reg_number")
    private User reporter;
    private boolean approved = false;
    private LocalDateTime incidentDate = LocalDateTime.now();
    private LocalDateTime createdAt = LocalDateTime.now();
}
