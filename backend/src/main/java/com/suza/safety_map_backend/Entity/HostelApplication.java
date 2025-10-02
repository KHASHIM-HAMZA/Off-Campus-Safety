package com.suza.safety_map_backend.Entity;

import jakarta.persistence.*;
        import lombok.*;
        import java.time.LocalDateTime;

@Entity
@Table(name = "hostel_applications")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class HostelApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id",referencedColumnName = "reg_number")
    private User student;

    @ManyToOne
    @JoinColumn(name = "hostel_id")
    private Hostel hostel;

    @Enumerated(EnumType.STRING)
    private Status status = Status.PENDING;

    private LocalDateTime appliedDate = LocalDateTime.now();

    public enum Status {
        PENDING, APPROVED, REJECTED
    }
}
