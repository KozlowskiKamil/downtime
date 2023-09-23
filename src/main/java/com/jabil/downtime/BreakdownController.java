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
    final String token1 = "cLo_qHgFQHqH73niSesQ0P:APA91bF8q64xIXm7yrnWHQhO7IN683QKgcQIw-2F4DhUpeiKCC7cPpIPtFzXXY8u2WF1q9Yr1n3mRwPbosqStiPsapSmKEKOKNCwIRZ4z4R-4nWb6_nc6O1nKMpbPL5I_6wVJSx6v2Sj";
    final String token2 = "dWD0H9t3TXenCvuEbxpyCR:APA91bF4k6YQNFOs-wL_5FEoxdMPdcslR4os8f3Byv9aQdiqm6AiUseoEUMzBizgS4yfCdZ1_naIDSaFrtQgNHrNmxlBq3IacLQ05sslSxxTHnIZsOBDFLO7oW74FB5N2lvnCVyejcsf";
    final String token3 = "cLGB1ZIoS2-AI6iRdIHKot:APA91bEYSjLbGV00OCDoFagVV6yougsFmSIf0TjkwYOBAGX_jP-5MvL-KHddTaKX0x6ezekp-96OCweH7GlAQfueMBjF7aoQEMvJJLOiSCml5Zl7Pohe_Dg6hYtnpJWcMCoV6090bx2q";
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