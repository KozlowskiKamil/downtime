package com.jabil.downtime.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class BreakdownDto {

    private Long id;
    private String failureName;
    private String computerName;
    private String description;
    private boolean ongoing;
    private long counter;
    private long waitingTime;
    private LocalDateTime failureStartTime;

    private LocalDateTime failureEndTime;


    public BreakdownDto(String failureName, String computerName) {
        this.failureName = failureName;
        this.computerName = computerName;
        this.description = null;
        this.ongoing = true;
        this.counter = 0L;
        this.waitingTime = 0L;
        this.failureStartTime = LocalDateTime.now();
        this.failureEndTime = null;
    }

    public void setFailureEndTime(LocalDateTime failureEndTime) {
        this.failureEndTime = failureEndTime;
        calculateCounter();
    }

    private void calculateCounter() {
        if (failureEndTime != null) {
            Duration duration = Duration.between(failureStartTime, failureEndTime);
            this.counter = duration.toMinutes();
        }
    }
}
