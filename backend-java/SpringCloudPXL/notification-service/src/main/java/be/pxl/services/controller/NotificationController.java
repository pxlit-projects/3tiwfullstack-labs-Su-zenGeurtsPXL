package be.pxl.services.controller;

import be.pxl.services.domain.dto.NotificationRequest;
import be.pxl.services.domain.dto.NotificationResponse;
import be.pxl.services.services.INotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notification")
@RequiredArgsConstructor
public class NotificationController {

    private final INotificationService notificationService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void add(@RequestBody NotificationRequest notificationRequest) {
        notificationService.addNotification(notificationRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<NotificationResponse> findAll() {
        return notificationService.getAllNotifications();
    }
}
