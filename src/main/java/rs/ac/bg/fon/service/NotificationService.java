package rs.ac.bg.fon.service;

import rs.ac.bg.fon.model.Notification;
import rs.ac.bg.fon.util.NotificationRequest;

public interface NotificationService {

    Notification sendNotification(NotificationRequest request);

    Notification sendNotificationToAll(NotificationRequest request);

}
