package rs.ac.bg.fon.service;

import rs.ac.bg.fon.model.dto.NotificationDTO;

public interface NotificationService {

    NotificationDTO sendNotification(NotificationDTO request);

    NotificationDTO sendNotificationToAll(NotificationDTO request);

    public boolean receivedNotification(Long id);

}
