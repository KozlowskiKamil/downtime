package com.jabil.downtime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Breakdown {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    Long id;

    @Column(name = "faliure_name")
    String failureName;

    @Column(name = "computer_name")
    String computerName;

    @Column(name = "description")
    String description;

    @Column(name = "ongoing")
    boolean ongoing;

    @Column(name = "counter")
    long counter;

    @CreationTimestamp
    @Column(name = "failure_start", nullable = false, updatable = false)
    private LocalDateTime failureStartTime;

    @CreationTimestamp
    @Column(name = "failure_end", nullable = false, updatable = false)
    private LocalDateTime failureEndTime;


}