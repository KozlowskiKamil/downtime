package com.jabil.downtime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
public class BreakdownController {

    final String token = "dXccypV5QSuNnyqmHocZoA:APA91bGuVlBeFpjA9Mmnmg_ZBz_yNNqA1hye_26WbEiAbU9BYxiG0tOXGpDgjBfIgXmFBz4XLBBwoJX9jKG1C83NfFsGNisbfXOIQV6n6xGkvIZ62fAovZF0teJclr0jSMoq03uQ2sht";

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
    public ResponseEntity<Breakdown> addBreakdown(@RequestBody Breakdown breakdown, NotificationMessage notificationMessage) {
        Breakdown savedBreakdown = breakdownService.saveBreakdown(breakdown);
        logger.info("Dodano nową awarię");
        firebaseMessagingService.sendNotificationByToken(new NotificationMessage(token,"Title", "body","",new HashMap<>()));
        return new ResponseEntity<>(savedBreakdown, HttpStatus.CREATED);
    }

    @GetMapping("/findall")
    public List<Breakdown> findAll() {
        return breakdownRepository.findAll();
    }
}
