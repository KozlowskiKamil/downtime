package com.jabil.downtime;

import org.springframework.stereotype.Service;

@Service
public class BreakdownService {

    private final BreakdownRepository breakdownRepository;

    public BreakdownService(BreakdownRepository breakdownRepository) {
        this.breakdownRepository = breakdownRepository;
    }

    public Breakdown saveBreakdown(Breakdown breakdown) {
        return breakdownRepository.save(breakdown);
    }


}
