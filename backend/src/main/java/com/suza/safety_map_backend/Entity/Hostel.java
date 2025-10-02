package com.suza.safety_map_backend.Entity;

import jakarta.persistence.*;
import lombok.*;
import org.locationtech.jts.geom.Point;

import java.util.List;

@Entity
@Table(name = "hostels")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Hostel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "owner_id",referencedColumnName = "id")
    private User owner;

    private String name;
    private String description;
    private int capacity;
    private double price;
    private int rating;
    @OneToMany(mappedBy = "hostel", cascade = CascadeType.ALL)
    private List<Comment> comments;

    @Column(columnDefinition = "geometry(Point,4326)")
    private Point location;

    private Double distanceFromCampus;
}
