package com.jabil.downtime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BreakdownController {

    private static final Logger logger = LoggerFactory.getLogger(BreakdownController.class);

    @Autowired
    private final BreakdownService breakdownService;

    @Autowired
    private final BreakdownRepository breakdownRepository;

    public BreakdownController(BreakdownService breakdownService, BreakdownRepository breakdownRepository) {
        this.breakdownService = breakdownService;
        this.breakdownRepository = breakdownRepository;
    }

    @PostMapping("/breakdown")
    public ResponseEntity<Breakdown> addBreakdown(@RequestBody Breakdown breakdown) {
        Breakdown savedBreakdown = breakdownService.saveBreakdown(breakdown);
        logger.info("Dodano nową awarię");
        return new ResponseEntity<>(savedBreakdown, HttpStatus.CREATED);
    }

    @GetMapping("/findall")
    public List<Breakdown> findAll() {
        return breakdownRepository.findAll();
    }
}
