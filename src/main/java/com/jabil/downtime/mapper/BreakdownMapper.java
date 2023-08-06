package com.jabil.downtime.mapper;

import com.jabil.downtime.dto.BreakdownDto;
import com.jabil.downtime.model.Breakdown;
import org.springframework.stereotype.Service;

@Service
public class BreakdownMapper {

    public Breakdown fromDto(BreakdownDto breakdownDto) {
        return Breakdown.builder()
                .id(breakdownDto.getId())
                .failureName(breakdownDto.getFailureName())
                .computerName(breakdownDto.getComputerName())
                .description(breakdownDto.getDescription())
                .ongoing(breakdownDto.isOngoing())
                .counter(breakdownDto.getCounter())
                .failureStartTime(breakdownDto.getFailureStartTime())
                .failureEndTime(breakdownDto.getFailureEndTime())
                .build();

    }

    public BreakdownDto toDto(Breakdown breakdown) {
        return BreakdownDto.builder()
                .id(breakdown.getId())
                .failureName(breakdown.getFailureName())
                .computerName(breakdown.getComputerName())
                .description(breakdown.getDescription())
                .ongoing(breakdown.isOngoing())
                .counter(breakdown.getCounter())
                .failureStartTime(breakdown.getFailureStartTime())
                .failureEndTime(breakdown.getFailureEndTime())
                .build();
    }

}