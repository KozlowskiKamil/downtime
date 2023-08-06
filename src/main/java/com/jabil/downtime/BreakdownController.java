package com.jabil.downtime;

import com.jabil.downtime.model.Breakdown;
import com.jabil.downtime.model.NotificationMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class BreakdownController {

    final String token1 = "e3e_TLX_TxSK3ii63nqLjW:APA91bFSaFIEMzBTpRmu7AN0uOrfDwOauYA53dDwWZr5UA9bOJ0EG4j-magFdd4TrnbDgO-LhClrL2V-kxcre5Qo8hRValn6EoRYLq3NpdEktanqv_6o_WnlZyrTa3Yrj6I59pCjoF16";
    final String token2 = "e6e8Sxz2SL2lQyS1AF3XBd:APA91bF_CYulgRdobkmHuimqO0THEaRQnFA8Bp_IvCf4kslB5m_cQK73FLNHZpRvlsoCOpc9EB94pXsCtoWBH4l8IqJyKVTogIW3K0EdgSAvu8Omfhbe8mJvUHqtl2HfUoALn9X1q3s-";
    final String token3 = "";
    final String token4 = "";


    private static final Logger logger = LoggerFactory.getLogger(BreakdownController.class);

    @Autowired
    private final BreakdownService breakdownService;

    @Autowired
    private final BreakdownRepository breakdownRepository;

    @Autowired
    FirebaseMessagingService firebaseMessagingService;

    public BreakdownController(BreakdownService breakdownService, BreakdownRepository breakdownRepository) {
        this.breakdownService = breakdownService;
        this.breakdownRepository = breakdownRepository;
    }

    @PostMapping("/breakdown")
    public ResponseEntity<Breakdown> addBreakdown(@RequestBody Breakdown breakdownRequest) {
        String computerName = breakdownRequest.getComputerName();
        String failureName = breakdownRequest.getFailureName();

        Breakdown breakdown = new Breakdown(failureName, computerName);
        Breakdown savedBreakdown = breakdownService.saveBreakdown(breakdown);
        logger.info("Dodano nową awarię");
        firebaseMessagingService.sendNotificationByToken(new NotificationMessage(token1, breakdown.getComputerName(), breakdown.getFailureName()));
        firebaseMessagingService.sendNotificationByToken(new NotificationMessage(token2, breakdown.getComputerName(), breakdown.getFailureName()));
        return new ResponseEntity<>(savedBreakdown, HttpStatus.CREATED);
    }

    @PatchMapping("/breakdown")
    public ResponseEntity<Breakdown> stopBreakdown(@RequestBody Breakdown breakdown) {
        breakdown.getId();
        breakdown.setFailureEndTime(LocalDateTime.now());
        breakdown.setOngoing(false);
        breakdown.setDescription(breakdown.getDescription());
        Breakdown savedBreakdown = breakdownService.saveBreakdown(breakdown);
//        breakdownService.calculateCounter(breakdown);
        logger.info("Zamknięto awarię");
        return new ResponseEntity<>(savedBreakdown, HttpStatus.CREATED);
    }

    @GetMapping("/findall")
    public List<Breakdown> findAll() {
        return breakdownRepository.findAll();
    }
}