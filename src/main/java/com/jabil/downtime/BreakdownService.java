package com.jabil.downtime;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class BreakdownService {

    private final BreakdownRepository breakdownRepository;

    public BreakdownService(BreakdownRepository breakdownRepository) {
        this.breakdownRepository = breakdownRepository;
    }

    public Breakdown saveBreakdown(Breakdown breakdown) {
        return breakdownRepository.save(breakdown);
    }


    public Breakdown startStop(Breakdown breakdown) {
        LocalDateTime failureStartTime = breakdown.getFailureStartTime();
        System.out.println("failureStartTime = " + failureStartTime);
        return new Breakdown();
    }



}