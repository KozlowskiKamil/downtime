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

    final String token1 = "dXccypV5QSuNnyqmHocZoA:APA91bGuVlBeFpjA9Mmnmg_ZBz_yNNqA1hye_26WbEiAbU9BYxiG0tOXGpDgjBfIgXmFBz4XLBBwoJX9jKG1C83NfFsGNisbfXOIQV6n6xGkvIZ62fAovZF0teJclr0jSMoq03uQ2sht";
    final String token2 = "fpyPHgl-Tsez78NnFhmZ21:APA91bEjlpG-sTAJn_d_VqK2y9xKOktOE5q7WzwRBiHLOVCrt2Ai75uy-FS9S_LzTPPTRPgx0BUUT5D331Ta52Bm0hKA5M6u7eBjciTJyuBSpbE8gqsctMoRkw---3vZA9nMmcjLCW4L";
    final String token3 = "eokZ6Dw2TPaUQyLqyPjsnU:APA91bG7eaPvQE7a-mBY6Plf6pF8u1XG-ihBGBfErdwE2o6TfbIjV0pcj3rTfEw6Pbxmq-gwjTsz0PZ-UbXudnj4wgHIK_MkV0tE7r1Ije-p9WbgebRn-lH_z8ZJ8C0_3P10v5s8mnBd";


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
    public ResponseEntity<Breakdown> addBreakdown(@RequestBody Breakdown breakdown) {
        Breakdown savedBreakdown = breakdownService.saveBreakdown(breakdown);
        logger.info("Dodano nową awarię");
        firebaseMessagingService.sendNotificationByToken(new NotificationMessage(token3, breakdown.getComputerName(), breakdown.getFailureName()));
        return new ResponseEntity<>(savedBreakdown, HttpStatus.CREATED);
    }

    @GetMapping("/findall")
    public List<Breakdown> findAll() {
        return breakdownRepository.findAll();
    }
}
