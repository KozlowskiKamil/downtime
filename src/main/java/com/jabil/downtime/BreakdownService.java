package com.jabil.downtime;

import com.jabil.downtime.model.Breakdown;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BreakdownService {

    private final BreakdownRepository breakdownRepository;

    public List<Breakdown> findAll() {
        return breakdownRepository.findAll()
                .stream().collect(Collectors.toList());
    }


    public Breakdown saveBreakdown(Breakdown breakdown) {
        return breakdownRepository.save(breakdown);
    }
//
//    public LocalDateTime findBreakdownByIda(Long id) {
//        Breakdown breakdownById = breakdownRepository.findBreakdownById(id);
//        breakdownById.getFailureEndTime();
//        breakdownById.getComputerName();
//        breakdownById.getFailureName();
//        return breakdownById.getFailureStartTime();
//    }
//
//    public LocalDateTime getFailureStartTimeById(Long id) {
//        Breakdown breakdown = breakdownRepository.findBreakdownById(id);
//        return breakdown != null ? breakdown.getFailureStartTime() : null;
//    }
//
//
//    public void calculateCounter(Breakdown breakdown) {
//        LocalDateTime failureStartTime = getFailureStartTimeById(breakdown.getId());
//        LocalDateTime failureEndTime = breakdown.getFailureEndTime();
//
//        if (failureEndTime != null) {
//            Duration duration = Duration.between(failureStartTime, failureEndTime);
//            breakdown.setCounter(duration.toMinutes());
//        }
//    }


}