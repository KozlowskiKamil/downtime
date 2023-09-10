package com.jabil.downtime;

import com.jabil.downtime.dto.TechnicianDto;
import com.jabil.downtime.model.Technician;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TechnicianController {

    public static final Logger logger = LoggerFactory.getLogger(TechnicianController.class);

    private final TechnicianService technicianService;

    private final TechnicianRepository technicianRepository;


    @PostMapping("/addtechnician")
    public ResponseEntity<TechnicianDto> addTechnican(@RequestBody TechnicianDto technicianRequest) {
        TechnicianDto technicianDto = new TechnicianDto(technicianRequest.getId(), technicianRequest.getName(), technicianRequest.getBadgeNumber());
        TechnicianDto saveTechnican = technicianService.registerTechnician(technicianDto);
        logger.info("Dodano nowego technika: " + saveTechnican);
        return new ResponseEntity<>(saveTechnican, HttpStatus.CREATED);
    }

    @GetMapping("/technican")
    public List<Technician> findAllTechnican() {
        return technicianRepository.findAll();
    }

    @PostMapping("/checkbadgenumber")
    public ResponseEntity<TechnicianDto> findTechnician(@RequestParam int badgeNumber) {
        TechnicianDto byBadgeNumber = technicianService.findByBadgeNumber(badgeNumber);
        if (byBadgeNumber == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(byBadgeNumber, HttpStatus.OK);
    }
}