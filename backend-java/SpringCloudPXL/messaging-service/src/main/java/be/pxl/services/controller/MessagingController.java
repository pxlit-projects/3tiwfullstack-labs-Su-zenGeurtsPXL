package be.pxl.services.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/messaging")
@RequiredArgsConstructor
public class MessagingController {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    private static final Logger log = LoggerFactory.getLogger(MessagingController.class);

    @PostMapping(path = "{message}")
    @ResponseStatus(HttpStatus.OK)
    public void sendMessage(@PathVariable String message) {
        try {
            log.info("Testing sender...");
            rabbitTemplate.convertAndSend("myQueue", message);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
