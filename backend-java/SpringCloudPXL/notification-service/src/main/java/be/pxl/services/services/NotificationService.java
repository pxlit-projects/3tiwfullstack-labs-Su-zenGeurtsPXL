package be.pxl.services.services;

import be.pxl.services.domain.Notification;
import be.pxl.services.domain.dto.NotificationRequest;
import be.pxl.services.domain.dto.NotificationResponse;
import be.pxl.services.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService implements INotificationService {
    private final NotificationRepository notificationRepository;

    @Override
    public void addNotification(NotificationRequest notificationRequest) {
    Notification notification = Notification.builder()
            .from(notificationRequest.getFrom())
            .to(notificationRequest.getTo())
            .subject(notificationRequest.getSubject())
            .message(notificationRequest.getMessage())
            .build();

    notificationRepository.save(notification);
    }

    @Override
    public List<NotificationResponse> getAllNotifications() {
        return notificationRepository.findAll()
                .stream()
                .map(this::mapToNotificationRespone)
                .toList();
    }

    private NotificationResponse mapToNotificationRespone(Notification notification) {
        return NotificationResponse.builder()
                .id(notification.getId())
                .from(notification.getFrom())
                .to(notification.getTo())
                .subject(notification.getSubject())
                .message(notification.getMessage())
                .build();
    }
}
