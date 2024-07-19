package rs.ac.bg.fon.model.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import rs.ac.bg.fon.model.Notification;
import rs.ac.bg.fon.model.dto.NotificationDTO;
import rs.ac.bg.fon.repository.UserRepository;

public class NotificationMapper {

    @Autowired
    private static UserRepository userRepository;

    public static NotificationDTO toDto(Notification notification) {
        if (notification == null) {
            return null;
        }

        NotificationDTO notificationDTO = new NotificationDTO();
        notificationDTO.setId(notification.getId());
        notificationDTO.setMessage(notification.getMessage());
        notificationDTO.setTitle(notification.getTitle());
        notificationDTO.setRecipientId(notification.getRecipient().getId());
        if (notification.getSender() != null) {
            notificationDTO.setSenderUsername(notification.getSender().getUsername());
        }
        notificationDTO.setDate(notification.getDate());
        notificationDTO.setReceived(notification.isReceived());

        return notificationDTO;
    }

    public static Notification toEntity(NotificationDTO notificationDTO) {
        if (notificationDTO == null) {
            return null;
        }

        Notification notification = new Notification();
        notification.setId(notificationDTO.getId());
        notification.setMessage(notificationDTO.getMessage());
        notification.setTitle(notificationDTO.getTitle());
        notification.setRecipient(userRepository.findById(notificationDTO.getRecipientId()).orElse(null));
        notification.setSender(userRepository.findByUsername(notificationDTO.getSenderUsername()).orElse(null));
        notification.setDate(notificationDTO.getDate());
        notification.setReceived(notificationDTO.isReceived());

        return notification;
    }
}
