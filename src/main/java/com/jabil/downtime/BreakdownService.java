package com.jabil.downtime;

import com.jabil.downtime.dto.BreakdownDto;
import com.jabil.downtime.mapper.BreakdownMapper;
import com.jabil.downtime.model.Breakdown;
import com.jabil.downtime.model.Technician;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BreakdownService {

    private final BreakdownRepository breakdownRepository;
    private final BreakdownMapper breakdownMapper;
    private final TechnicianRepository technicianRepository;


    @Transactional
    public void assignTechnicianToBreakdown(Long technicianId, Long breakdownId, Long waitingTime) {
        log.info("Adding technican to breakdown by id: {}", technicianId);

        Technician technician = technicianRepository.findById(technicianId).orElseThrow(() -> new IllegalArgumentException("Technician with ID " + technicianId + " not found"));

        Breakdown breakdown = breakdownRepository.findById(breakdownId).orElseThrow(() -> new IllegalArgumentException("Breakdown with ID " + breakdownId + " not found"));

        breakdown.setTechnician(technician);
        breakdown.setWaitingTime(waitingTime);
        breakdownRepository.save(breakdown);
    }


    public List<BreakdownDto> findAll() {
        return breakdownRepository.findAll().stream().map(breakdownMapper::toDto).collect(Collectors.toList());
    }

    public List<BreakdownDto> findAllDistinct() {
        List<BreakdownDto> result = breakdownRepository.findAll()
                .stream()
                .collect(Collectors.toMap(
                        Breakdown::getComputerName,
                        breakdownMapper::toDto,
                        (existing, replacement) -> existing
                ))
                .values()
                .stream()
                .collect(Collectors.toList());
        return result;
    }


    public List<BreakdownDto> findAllDescending() {
        Sort descendingSort = Sort.by(Sort.Direction.DESC, "failureStartTime");
        return breakdownRepository.findAll(descendingSort).stream().map(breakdownMapper::toDto).collect(Collectors.toList());
    }

    public List<BreakdownDto> findAllDescendingEnd() {
        Sort descendingSort = Sort.by(Sort.Direction.DESC, "failureStartTime");
        return breakdownRepository.findAllByOngoingOrderByFailureStartTimeDesc(false).stream().sorted().map(breakdownMapper::toDto).collect(Collectors.toList());
    }

    public BreakdownDto findById(Long id) {
        Breakdown breakdownById = breakdownRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Brak awarii o takim id"));
        return breakdownMapper.toDto(breakdownById);
    }

//    public BreakdownDto saveBreakdown(BreakdownDto breakdownDto) {
//        breakdownRepository.save(breakdownMapper.fromDto(breakdownDto));
//        return breakdownDto;
//    }

    public BreakdownDto saveBreakdown(BreakdownDto breakdownDto) {
        Breakdown savedBreakdown = breakdownRepository.save(breakdownMapper.fromDto(breakdownDto));
        return breakdownMapper.toDto(savedBreakdown);
    }

    public void updateBreakedown(BreakdownDto breakdownDto) {
        Optional<Breakdown> breakdownById = breakdownRepository.findById(breakdownDto.getId());

        if (!breakdownById.isPresent()) {
            throw new IllegalArgumentException("Brak awarii o podanym id");
        }
        Breakdown toUpdate = breakdownById.get();
        toUpdate.setFailureEndTime(LocalDateTime.now());
        toUpdate.setDescription(breakdownDto.getDescription());
        toUpdate.setOngoing(false);
        toUpdate.setWaitingTime(breakdownDto.getWaitingTime());
        LocalDateTime failureStartTime = toUpdate.getFailureStartTime();
        LocalDateTime failureEndTime = toUpdate.getFailureEndTime();
        if (failureEndTime != null) {
            Duration duration = Duration.between(failureStartTime, failureEndTime);
            toUpdate.setCounter(duration.toMinutes());
        }
        breakdownRepository.save(toUpdate);
    }

    public void deleteById(Long id) {
        breakdownRepository.deleteById(id);
    }


//    public List<Breakdown> findAllByOngoing(boolean ongoing) {
//        return breakdownRepository.findAllByOngoing(ongoing);
//    }

    public List<Breakdown> findAllByOngoing(boolean ongoing) {
        return breakdownRepository.findAllByOngoingOrderByFailureStartTimeDesc(ongoing);
    }

    public List<Breakdown> findAllByComputerNameAndFailureName(String computerName, String failureName) {
        return breakdownRepository.findAllByComputerNameAndFailureNameOrderByFailureStartTimeDesc(computerName, failureName);
    }


}