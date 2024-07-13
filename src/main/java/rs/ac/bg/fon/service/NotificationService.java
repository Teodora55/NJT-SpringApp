package rs.ac.bg.fon.service;

import rs.ac.bg.fon.model.Notification;
import rs.ac.bg.fon.util.NotificationDTO;

public interface NotificationService {

    Notification sendNotification(NotificationDTO request);

    Notification sendNotificationToAll(NotificationDTO request);

    public boolean receivedNotification(Long id);

}
