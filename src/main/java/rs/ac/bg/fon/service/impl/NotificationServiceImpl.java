package rs.ac.bg.fon.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.bg.fon.model.Notification;
import rs.ac.bg.fon.model.User;
import rs.ac.bg.fon.repository.NotificationRepository;
import rs.ac.bg.fon.repository.UserRepository;
import rs.ac.bg.fon.service.NotificationService;
import rs.ac.bg.fon.model.dto.NotificationDTO;
import rs.ac.bg.fon.model.mapper.NotificationMapper;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Notification sendNotification(NotificationDTO request) {
        User sender = userRepository.findByUsername(request.getSenderUsername()).orElse(null);
        User recipient = userRepository.findByCustomerId(request.getRecipientId()).orElse(null);
        Notification notification = new Notification(request.getMessage(), request.getTitle(), recipient, sender);
        return notificationRepository.save(notification);
    }

    @Override
    public Notification sendNotificationToAll(NotificationDTO request) {
        User sender = userRepository.findByUsername(request.getSenderUsername()).orElse(null);
        Notification notification = new Notification(request.getMessage(), request.getTitle(), null, sender);
        List<User> users = userRepository.findAll();
        boolean successfullySaved = true;
        for (User user : users) {
            notification.setRecipient(user);
            Notification savedNotification = notificationRepository.save(notification);
            if (savedNotification == null) {
                successfullySaved = false;
            }
        }
        return successfullySaved ? notification : null;
    }

    @Override
    public boolean receivedNotification(Long id) {
        return notificationRepository.markAsReceived(id) > 0;
    }

}
