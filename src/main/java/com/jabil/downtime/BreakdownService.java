package com.jabil.downtime;

import com.jabil.downtime.dto.BreakdownDto;
import com.jabil.downtime.mapper.BreakdownMapper;
import com.jabil.downtime.model.Breakdown;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BreakdownService {

    private final BreakdownRepository breakdownRepository;
    private final BreakdownMapper breakdownMapper;

    public List<BreakdownDto> findAll() {
        return breakdownRepository.findAll()
                .stream()
                .map(breakdownMapper::toDto)
                .collect(Collectors.toList());
    }

    public BreakdownDto findById(Long id) {
        Breakdown breakdownById = breakdownRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Brak awarii o takim id"));
        return breakdownMapper.toDto(breakdownById);
    }

    public BreakdownDto saveBreakdown(BreakdownDto breakdownDto) {
        breakdownRepository.save(breakdownMapper.fromDto(breakdownDto));
        return breakdownDto;
    }

    public void updateBreakedown(BreakdownDto breakdownDto) {
        Optional<Breakdown> breakdownById = breakdownRepository.findById(breakdownDto.getId());

        if (!breakdownById.isPresent()) {
            throw new IllegalArgumentException("Brak awarii o podanym id");
        }

        Breakdown toUpdate = breakdownById.get();
        System.out.println("toUpdate = " + toUpdate);

        toUpdate.setFailureEndTime(LocalDateTime.now());
        toUpdate.setDescription(breakdownDto.getDescription());
        toUpdate.setOngoing(false);

        LocalDateTime failureStartTime = toUpdate.getFailureStartTime();
        LocalDateTime failureEndTime = toUpdate.getFailureEndTime();
        if (failureEndTime != null) {
            Duration duration = Duration.between(failureStartTime, failureEndTime);
            toUpdate.setCounter(duration.toMinutes());
        }

            breakdownRepository.save(toUpdate);
    }



//    public Breakdown saveBreakdown(Breakdown breakdown) {
//        return breakdownRepository.save(breakdown);
//    }
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