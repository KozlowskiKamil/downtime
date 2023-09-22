package com.jabil.downtime;

import com.jabil.downtime.dto.TechnicianDto;
import com.jabil.downtime.mapper.TechnicianMapper;
import com.jabil.downtime.model.Technician;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class TechnicianServiceTest {

    @Autowired
    private TechnicianService technicianService;

    @MockBean
    private TechnicianRepository technicianRepository;

    @Autowired
    private TechnicianMapper technicianMapper;


    @Test
    @Transactional
    void testFindAllTechnican() {
        List<Technician> technicianList = new ArrayList<>();
        technicianList.add(new Technician());
        technicianList.add(new Technician());
        when(technicianRepository.findAll()).thenReturn(technicianList);
        List<TechnicianDto> result = technicianService.findAllTechnican();
        assertEquals(2, result.size());
        verify(technicianRepository, times(1)).findAll();
    }

    @Test
    @Transactional
    void testSaveTechnician() {
        TechnicianDto technicianDto = new TechnicianDto();
        technicianDto.setName("Steve Wozniak");
        technicianDto.setBadgeNumber(1950);
        Technician technician = technicianMapper.fromDto(technicianDto);
        when(technicianRepository.save(any(Technician.class))).thenReturn(technician);
        TechnicianDto savedTechnician = technicianService.saveTechnician(technicianDto);
        assertEquals(technicianDto.getName(), savedTechnician.getName());
        assertEquals(technicianDto.getBadgeNumber(), savedTechnician.getBadgeNumber());
        verify(technicianRepository, times(1)).save(any(Technician.class));
    }
}