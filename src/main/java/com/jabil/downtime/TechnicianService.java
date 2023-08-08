package com.jabil.downtime;

import com.jabil.downtime.dto.TechnicianDto;
import com.jabil.downtime.mapper.TechnicianMapper;
import com.jabil.downtime.model.Technician;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TechnicianService {


    private final TechnicianRepository technicianRepository;
    private final TechnicianMapper technicianMapper;

    public TechnicianDto saveTechnician(TechnicianDto technicianDto) {
        Technician saveTechnician = technicianRepository.save(technicianMapper.fromDto(technicianDto));
        return technicianMapper.toDto(saveTechnician);
    }

}