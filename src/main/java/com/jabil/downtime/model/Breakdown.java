package com.jabil.downtime.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Duration;
import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Breakdown {

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

    @CreationTimestamp
    @Column(name = "failure_start", nullable = false, updatable = false)
    private LocalDateTime failureStartTime;

    @Column(name = "failure_end", nullable = true, updatable = true)
    private LocalDateTime failureEndTime;


    public void setFailureEndTime(LocalDateTime failureEndTime) {
        this.failureEndTime = failureEndTime;
//        calculateCounter();
    }

    private void calculateCounter() {
        if (failureEndTime != null) {
            Duration duration = Duration.between(failureStartTime, failureEndTime);
            this.counter = duration.toMinutes();
        }
    }

}