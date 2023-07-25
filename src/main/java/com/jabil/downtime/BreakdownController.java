package com.jabil.downtime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BreakdownController {

    private final BreakdownService breakdownService;
    private final BreakdownRepository breakdownRepository;

    public BreakdownController(BreakdownService breakdownService, BreakdownRepository breakdownRepository) {
        this.breakdownService = breakdownService;
        this.breakdownRepository = breakdownRepository;
    }

    @PostMapping("/breakdown")
    public ResponseEntity<Breakdown> addBreakdown(@RequestBody Breakdown breakdown) {
        Breakdown savedBreakdown = breakdownService.saveBreakdown(breakdown);
        return new ResponseEntity<>(savedBreakdown, HttpStatus.CREATED);
    }

    @GetMapping("/findall")
    public List<Breakdown> findAll() {
        return breakdownRepository.findAll();
    }
}
