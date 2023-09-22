package com.jabil.downtime;

import com.jabil.downtime.dto.BreakdownDto;
import com.jabil.downtime.mapper.BreakdownMapper;
import com.jabil.downtime.model.Breakdown;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest
public class BreakdownServiceTest {

    @Autowired
    private BreakdownService breakdownService;

    @MockBean
    private BreakdownRepository breakdownRepository;

    @Autowired
    private BreakdownMapper breakdownMapper;

    @Test
    @Transactional
    void testSaveBreakdown() {
        BreakdownDto breakdownDto = new BreakdownDto();
        breakdownDto.setDescription("Test breakdown");
        breakdownDto.setFailureStartTime(LocalDateTime.now());
        Breakdown breakdown = breakdownMapper.fromDto(breakdownDto);
        when(breakdownRepository.save(any(Breakdown.class))).thenReturn(breakdown);
        BreakdownDto savedBreakdown = breakdownService.saveBreakdown(breakdownDto);
        assertNotNull(savedBreakdown.getId());
        verify(breakdownRepository, times(1)).save(any(Breakdown.class));
    }


    @Test
    @Transactional
    void testUpdateBreakdown() {
        BreakdownDto breakdownDto = new BreakdownDto();
        breakdownDto.setId(1L);
        breakdownDto.setDescription("Updated breakdown");
        breakdownDto.setFailureEndTime(LocalDateTime.now());
        Breakdown breakdown = breakdownMapper.fromDto(breakdownDto);
        when(breakdownRepository.findById(anyLong())).thenReturn(Optional.of(breakdown));
        breakdownService.updateBreakedown(breakdownDto);
        assertEquals(breakdownDto.getDescription(), breakdown.getDescription());
        verify(breakdownRepository, times(1)).findById(anyLong());
        verify(breakdownRepository, times(1)).save(any(Breakdown.class));
    }
}