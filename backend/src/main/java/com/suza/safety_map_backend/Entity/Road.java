package com.suza.safety_map_backend.Entity;


import jakarta.persistence.*;
import lombok.Data;
import org.locationtech.jts.geom.LineString;

@Entity
@Table(name = "roads")
@Data
public class Road {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(columnDefinition = "geometry(LineString,4326)")
    private LineString geom;  // PostGIS linestring
    private Double cost;      // Length or travel time
    private Integer source;   // pgRouting topology
    private Integer target;
}