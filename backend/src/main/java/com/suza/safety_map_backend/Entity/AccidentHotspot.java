package com.suza.safety_map_backend.Entity;

import jakarta.persistence.*;
import org.geolatte.geom.Point;

@Entity
@Table(name = "accident_hotspots")
public class AccidentHotspot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;

    @Column(columnDefinition = "geometry(Point,4326)")
    private Point location; // from org.locationtech.jts.geom.Point
}
