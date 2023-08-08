package com.jabil.downtime.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Breakdown {

    @ManyToOne
    private Technician technician;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "faliure_name", updatable = false)
    private String failureName;

    @Column(name = "computer_name", updatable = false)
    private String computerName;

    @Column(name = "description")
    private String description;

    @Column(name = "ongoing")
    private boolean ongoing;

    @Column(name = "counter")
    private long counter;

    @Column(name = "waiting_time_sec")
    private long waitingTime;

    @CreationTimestamp
    @Column(name = "failure_start", nullable = false, updatable = false)
    private LocalDateTime failureStartTime;

    @Column(name = "failure_end", nullable = true, updatable = true)
    private LocalDateTime failureEndTime;


}