package com.jabil.downtime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Duration;
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

    @Column(name = "faliure_name", updatable = false)
    String failureName;

    @Column(name = "computer_name", updatable = false)
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

//    @UpdateTimestamp
    @Column(name = "failure_end", nullable = true, updatable = true)
    private LocalDateTime failureEndTime;


    public Breakdown(String failureName, String computerName) {
        this.failureName = failureName;
        this.computerName = computerName;
        this.description = null;
        this.ongoing = true;
        this.counter = 0L;
        this.failureStartTime = LocalDateTime.now();
        this.failureEndTime = null;
    }

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