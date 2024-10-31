package be.pxl.services.services;

import be.pxl.services.domain.Notification;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class NotificationService {

    public void sendMessage(Notification notification) {
        log.info("Receiving notification...");
        log.info("Sending... {}", notification.getMessage());
        log.info("TO {}", notification.getSender());
    }
}
