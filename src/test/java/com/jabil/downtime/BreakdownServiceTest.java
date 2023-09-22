package com.jabil.downtime;

import com.jabil.downtime.dto.BreakdownDto;
import com.jabil.downtime.mapper.BreakdownMapper;
import com.jabil.downtime.model.Breakdown;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BreakdownServiceTest {

    private BreakdownService breakdownService;

    @Mock
    private TechnicianRepository technicianRepository;

    @Mock
    private BreakdownRepository breakdownRepository;

    @Mock
    private BreakdownMapper breakdownMapper;

    @Test
    void contextLoads() {
    }

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        breakdownService = new BreakdownService(breakdownRepository, breakdownMapper, technicianRepository);
    }

    @Test
    public void testSaveBreakdown() {
        BreakdownDto breakdownDto = new BreakdownDto();
        BreakdownDto savedBreakdown = new BreakdownDto();
        savedBreakdown.setId(1L);
        when(breakdownService.saveBreakdown(any(BreakdownDto.class))).thenReturn(savedBreakdown);
        when(breakdownMapper.fromDto(breakdownDto)).thenReturn(new Breakdown());
        BreakdownDto result = breakdownService.saveBreakdown(breakdownDto);
        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals(savedBreakdown.getId(), result.getId());
    }

    @Test
    public void testUpdateBreakdown() {
        BreakdownDto breakdownDto = new BreakdownDto();
        breakdownDto.setId(1L);
        breakdownDto.setDescription("Test Description");
        Breakdown existingBreakdown = new Breakdown();
        existingBreakdown.setId(1L);
        existingBreakdown.setFailureStartTime(LocalDateTime.now());
        when(breakdownRepository.findById(anyLong())).thenReturn(Optional.of(existingBreakdown));
        when(breakdownMapper.toDto(existingBreakdown)).thenReturn(breakdownDto);
        breakdownService.updateBreakedown(breakdownDto);
        assertEquals("Test Description", existingBreakdown.getDescription());
        assertNotNull(existingBreakdown.getFailureEndTime());
        assertFalse(existingBreakdown.isOngoing());
        Duration duration = Duration.between(existingBreakdown.getFailureStartTime(), existingBreakdown.getFailureEndTime());
        assertEquals(duration.toMinutes(), existingBreakdown.getCounter());
    }
}
