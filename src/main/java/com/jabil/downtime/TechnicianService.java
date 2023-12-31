package com.jabil.downtime;

import com.jabil.downtime.dto.TechnicianDto;
import com.jabil.downtime.mapper.TechnicianMapper;
import com.jabil.downtime.model.Technician;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TechnicianService {


    private final TechnicianRepository technicianRepository;
    private final TechnicianMapper technicianMapper;


    public List<TechnicianDto> findAllTechnican() {
        return technicianRepository.findAll()
                .stream()
                .map(technicianMapper::toDto)
                .collect(Collectors.toList());
    }

    public TechnicianDto registerTechnician(TechnicianDto technicianDto) {
        if (!technicianExist(technicianDto)) {
            saveTechnician(technicianDto);
            return technicianDto;
        }
        throw new IllegalArgumentException("BT istnieje");
    }


    public String registerUserId(TechnicianDto technicianDto) {
        boolean userExist = technicianExist(technicianDto);
        if (userExist) {
            return "Technician BT: " + technicianDto.getBadgeNumber() + " already exists";
        }

        TechnicianDto savedUser = registerTechnician(technicianDto);
        if (savedUser != null) {
            return "Add: " + technicianDto.getName();
        } else {
            return "There was a problem registering the user";
        }
    }


    public boolean technicianExist(TechnicianDto technicianDto) {
        int badgeNumberToCheck = technicianDto.getBadgeNumber();
        return findAllTechnican().stream()
                .anyMatch(technicianDto1 -> technicianDto1.getBadgeNumber() == badgeNumberToCheck);
    }

    public TechnicianDto saveTechnician(TechnicianDto technicianDto) {
        Technician saveTechnician = technicianRepository.save(technicianMapper.fromDto(technicianDto));
        return technicianMapper.toDto(saveTechnician);
    }

    public TechnicianDto findById(Long id) {
        Technician technicianById = technicianRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Brak technika o takim id"));
        return technicianMapper.toDto(technicianById);
    }

    public TechnicianDto findByBadgeNumber(int badgeNumber) {
        Technician technicianByBadgeNumber = technicianRepository.findByBadgeNumber(badgeNumber);
        return technicianMapper.toDto(technicianByBadgeNumber);
    }


    public void UpdateTechnican(TechnicianDto technicianDto) {
        Optional<Technician> technicianById = technicianRepository.findById(technicianDto.getId());

        if (!technicianById.isPresent()) {
            throw new IllegalArgumentException("There is no technician with this id");
        }
        Technician tuUpdate = technicianById.get();
        tuUpdate.setName(technicianDto.getName());
        tuUpdate.setBadgeNumber(technicianDto.getBadgeNumber());
        technicianRepository.save(tuUpdate);
    }

    public void deleteById(Long id) {
        technicianRepository.deleteById(id);
    }
}