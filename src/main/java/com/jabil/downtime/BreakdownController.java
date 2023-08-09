package com.jabil.downtime;

import com.jabil.downtime.dto.BreakdownDto;
import com.jabil.downtime.mapper.BreakdownMapper;
import com.jabil.downtime.model.Breakdown;
import com.jabil.downtime.model.NotificationMessage;
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
    final String token1 = "e3e_TLX_TxSK3ii63nqLjW:APA91bFSaFIEMzBTpRmu7AN0uOrfDwOauYA53dDwWZr5UA9bOJ0EG4j-magFdd4TrnbDgO-LhClrL2V-kxcre5Qo8hRValn6EoRYLq3NpdEktanqv_6o_WnlZyrTa3Yrj6I59pCjoF16";
    final String token2 = "e6e8Sxz2SL2lQyS1AF3XBd:APA91bF_CYulgRdobkmHuimqO0THEaRQnFA8Bp_IvCf4kslB5m_cQK73FLNHZpRvlsoCOpc9EB94pXsCtoWBH4l8IqJyKVTogIW3K0EdgSAvu8Omfhbe8mJvUHqtl2HfUoALn9X1q3s-";
    final String token3 = "";
    final String token4 = "";

    @Autowired
    private final BreakdownMapper breakdownMapper;
    @Autowired
    private final BreakdownService breakdownService;
    @Autowired
    private final BreakdownRepository breakdownRepository;

    @Autowired
    FirebaseMessagingService firebaseMessagingService;

    public BreakdownController(BreakdownMapper breakdownMapper, BreakdownService breakdownService, BreakdownRepository breakdownRepository) {
        this.breakdownMapper = breakdownMapper;
        this.breakdownService = breakdownService;
        this.breakdownRepository = breakdownRepository;
    }  //todo wyrzucic konstruktor zastapic adnotacja

    @PostMapping("/breakdown")
    public ResponseEntity<BreakdownDto> addBreakdown(@RequestBody BreakdownDto breakdownRequest) {
        String computerName = breakdownRequest.getComputerName();
        String failureName = breakdownRequest.getFailureName();
        BreakdownDto breakdown = new BreakdownDto(failureName, computerName);
        BreakdownDto savedBreakdown = breakdownService.saveBreakdown(breakdown);
        logger.info("Dodano nową awarię na testerze: " + breakdown.getComputerName());
        firebaseMessagingService.sendNotificationByToken(new NotificationMessage(token1, breakdown.getComputerName(), breakdown.getFailureName()));
        firebaseMessagingService.sendNotificationByToken(new NotificationMessage(token2, breakdown.getComputerName(), breakdown.getFailureName()));
        return new ResponseEntity<>(savedBreakdown, HttpStatus.CREATED);
    }

    @PatchMapping("/breakdown")
    public ResponseEntity<BreakdownDto> stopBreakdown(@RequestBody BreakdownDto breakdown) {
        breakdownService.updateBreakedown(breakdown);
        logger.info("Zamknięto awarię o id: " + breakdown.getId());
        return new ResponseEntity<>(breakdown, HttpStatus.OK);
    }

    @PostMapping("/assign")
    public ResponseEntity<String> assignTechnicianToBreakdown(@RequestParam Long technicianId, @RequestParam Long breakdownId, @RequestParam Long waitingTime) {
        try {
            breakdownService.assignTechnicianToBreakdown(technicianId, breakdownId, waitingTime);
            return ResponseEntity.ok("Przypisano technika do awarii.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/findall")
    public List<Breakdown> findAll() {
        return breakdownRepository.findAll();
    }
}