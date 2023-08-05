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

    final String token1 = "dcrBS40mTBOo8YlgydBkyq:APA91bGli-sGk8wlWy1uKZeZ2BocOcBaJa1XeCbuv5b2FLdC53o1HzyyM-qwcIo3WtkhTiCjsEdJZgbQvNTnLIQI5mbQksd8NLN3YA0584cslDKaYTU79Uc16hQEfZS7qGX5hJMjyI4u";
    final String token2 = "fpyPHgl-Tsez78NnFhmZ21:APA91bEjlpG-sTAJn_d_VqK2y9xKOktOE5q7WzwRBiHLOVCrt2Ai75uy-FS9S_LzTPPTRPgx0BUUT5D331Ta52Bm0hKA5M6u7eBjciTJyuBSpbE8gqsctMoRkw---3vZA9nMmcjLCW4L";
    final String token3 = "eokZ6Dw2TPaUQyLqyPjsnU:APA91bG7eaPvQE7a-mBY6Plf6pF8u1XG-ihBGBfErdwE2o6TfbIjV0pcj3rTfEw6Pbxmq-gwjTsz0PZ-UbXudnj4wgHIK_MkV0tE7r1Ije-p9WbgebRn-lH_z8ZJ8C0_3P10v5s8mnBd";
    final String token4 = "dpmdkHn3RGqo_j7YVUNQXn:APA91bHBZRJW-LLto5tx-ShfdooJBpnWeDdR-m60zV5pTqLf2hyPbG7qVqLMGDVaCGMLT6OW-Cn0csFAQGbrAsPT8PIVEO6qxcsPzMGZ6_u3hbzATL_swha-irbTXARJCllipq0WNYY7";


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
        firebaseMessagingService.sendNotificationByToken(new NotificationMessage(token1, breakdown.getComputerName(), breakdown.getFailureName()));
        firebaseMessagingService.sendNotificationByToken(new NotificationMessage(token4, breakdown.getComputerName(), breakdown.getFailureName()));
        return new ResponseEntity<>(savedBreakdown, HttpStatus.CREATED);
    }

    @GetMapping("/findall")
    public List<Breakdown> findAll() {
        return breakdownRepository.findAll();
    }
}
