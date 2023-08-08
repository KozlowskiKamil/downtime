package com.jabil.downtime.mapper;

import com.jabil.downtime.dto.TechnicianDto;
import com.jabil.downtime.model.Technician;
import org.springframework.stereotype.Service;

@Service
public class TechnicianMapper {

    public Technician fromDto(TechnicianDto technicianDto) {
        return Technician.builder()
                .id(technicianDto.getId())
                .badgeNumber(technicianDto.getBadgeNumber())
                .name(technicianDto.getName())
                .build();
    }

    public TechnicianDto toDto(Technician technician) {
        return TechnicianDto.builder()
                .id(technician.getId())
                .badgeNumber(technician.getBadgeNumber())
                .name(technician.getName())
                .build();
    }

}
