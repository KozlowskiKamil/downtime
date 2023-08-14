package com.jabil.downtime;

import com.jabil.downtime.dto.BreakdownDto;
import com.jabil.downtime.dto.TechnicianDto;
import com.jabil.downtime.model.Breakdown;
import com.jabil.downtime.model.NotificationMessage;
import com.jabil.downtime.model.Technician;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class BreakdownController {

    private static final Logger logger = LoggerFactory.getLogger(BreakdownController.class);
    final String token1 = "e3e_TLX_TxSK3ii63nqLjW:APA91bFSaFIEMzBTpRmu7AN0uOrfDwOauYA53dDwWZr5UA9bOJ0EG4j-magFdd4TrnbDgO-LhClrL2V-kxcre5Qo8hRValn6EoRYLq3NpdEktanqv_6o_WnlZyrTa3Yrj6I59pCjoF16";
    final String token2 = "e6e8Sxz2SL2lQyS1AF3XBd:APA91bF_CYulgRdobkmHuimqO0THEaRQnFA8Bp_IvCf4kslB5m_cQK73FLNHZpRvlsoCOpc9EB94pXsCtoWBH4l8IqJyKVTogIW3K0EdgSAvu8Omfhbe8mJvUHqtl2HfUoALn9X1q3s-";
    final String token3 = "eokZ6Dw2TPaUQyLqyPjsnU:APA91bG7eaPvQE7a-mBY6Plf6pF8u1XG-ihBGBfErdwE2o6TfbIjV0pcj3rTfEw6Pbxmq-gwjTsz0PZ-UbXudnj4wgHIK_MkV0tE7r1Ije-p9WbgebRn-lH_z8ZJ8C0_3P10v5s8mnBd";
    final String token4 = "";

    @Autowired
    private final BreakdownService breakdownService;

    @Autowired
    private final TechnicianRepository technicianRepository;

    @Autowired
    private final BreakdownRepository breakdownRepository;

    @Autowired
    private final TechnicianService technicianService;
    @Autowired
    private final FirebaseMessagingService firebaseMessagingService;


    @PostMapping("/breakdown")
    public ResponseEntity<BreakdownDto> addBreakdown(@RequestBody BreakdownDto breakdownRequest) {
        String computerName = breakdownRequest.getComputerName();
        String failureName = breakdownRequest.getFailureName();
        BreakdownDto breakdown = new BreakdownDto(failureName, computerName);
        BreakdownDto savedBreakdown = breakdownService.saveBreakdown(breakdown);
        logger.info("Dodano nową awarię na testerze: " + breakdown.getComputerName());
        firebaseMessagingService.sendNotificationByToken(new NotificationMessage(token1, breakdown.getComputerName(), breakdown.getFailureName()));
        firebaseMessagingService.sendNotificationByToken(new NotificationMessage(token2, breakdown.getComputerName(), breakdown.getFailureName()));
        firebaseMessagingService.sendNotificationByToken(new NotificationMessage(token3, breakdown.getComputerName(), breakdown.getFailureName()));
        return new ResponseEntity<>(savedBreakdown, HttpStatus.CREATED);
    }

    @PatchMapping("/breakdown")
    public ResponseEntity<BreakdownDto> stopBreakdown(@RequestBody BreakdownDto breakdown) {
        breakdownService.updateBreakedown(breakdown);
        logger.info("Zamknięto awarię o id: " + breakdown.getId());
        return new ResponseEntity<>(breakdown, HttpStatus.OK);
    }


    @PatchMapping("/breakdownclose")
    public ResponseEntity<BreakdownDto> closeBreakdown(@RequestBody BreakdownDto breakdown) {
        BreakdownDto byId = breakdownService.findById(breakdown.getId());
        long waitingTime = byId.getWaitingTime();
        if (waitingTime == 0) {
            waitingTime = breakdown.getWaitingTime();
        }
        TechnicianDto byBadgeNumber = technicianService.findByBadgeNumber(Integer.valueOf(breakdown.getFailureName()));
        if (byBadgeNumber != null) {
            breakdown.setWaitingTime(waitingTime);
            Technician technicianServiceById = technicianRepository.findByBadgeNumber(Integer.valueOf(breakdown.getFailureName()));
            breakdownService.assignTechnicianToBreakdown(technicianServiceById.getId(), breakdown.getId(), waitingTime);
            logger.info("Zamknięto awarię o id: " + breakdown.getId());
            breakdownService.updateBreakedown(breakdown);
            return new ResponseEntity<>(breakdown, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(breakdown, HttpStatus.NOT_FOUND);
        }

    }

    @PostMapping("/assign")
    public ResponseEntity<String> assignTechnicianToBreakdown(@RequestParam Long technicianId, @RequestParam Long breakdownId, @RequestParam Long waitingTime) {
        try {
            TechnicianDto technicianServiceById = technicianService.findById(technicianId);
            breakdownService.assignTechnicianToBreakdown(technicianId, breakdownId, waitingTime);
            return ResponseEntity.ok("Przypisano technika " + technicianServiceById.getName() + " do awarii.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/findall")
    public List<Breakdown> findAll() {
        return breakdownRepository.findAll();
    }


    @GetMapping("/ongoing")
    public List<Breakdown> findAllOngoing() {
        return breakdownRepository.findAllByOngoing(true);
    }

    @GetMapping("/ended")
    public List<Breakdown> findAllEnded() {
        return breakdownRepository.findAllByOngoing(false);
    }

    @GetMapping("/samefailure/{computerName}/{failureName}")
    public List<Breakdown> findAllByComputerNameAndFailureName(@PathVariable(value = "computerName") String computerName, @PathVariable(value = "failureName") String failureName) {
        return breakdownRepository.findAllByComputerNameAndFailureNameOrderByFailureStartTimeDesc(computerName, failureName);
    }
}