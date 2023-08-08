package com.jabil.downtime;

import com.jabil.downtime.dto.TechnicianDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TechnicianController {

    public static final Logger logger = LoggerFactory.getLogger(TechnicianController.class);

    private final TechnicianService technicianService;

    private final TechnicianRepository technicianRepository;


    @PostMapping("/addtechnician")
    public ResponseEntity<TechnicianDto> addTechnican(@RequestBody TechnicianDto technicianRequest) {
        TechnicianDto technicianDto = new TechnicianDto(technicianRequest.getId(), technicianRequest.getName(), technicianRequest.getBadgeNumber());
        TechnicianDto saveTechnican = technicianService.saveTechnician(technicianDto);
        logger.info("Dodano nowego technika: " + saveTechnican);
        return new ResponseEntity<>(saveTechnican, HttpStatus.CREATED);

    }
}