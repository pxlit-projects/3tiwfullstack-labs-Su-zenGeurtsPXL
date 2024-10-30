package be.pxl.services.services;

import be.pxl.services.domain.dto.NotificationRequest;
import be.pxl.services.domain.dto.NotificationResponse;

import java.util.List;

public interface INotificationService {
    void addNotification(NotificationRequest notificationRequest);

    List<NotificationResponse> getAllNotifications();
}
