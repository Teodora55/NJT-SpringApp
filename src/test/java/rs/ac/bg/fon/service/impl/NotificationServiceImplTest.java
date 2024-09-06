package rs.ac.bg.fon.service.impl;

import java.time.LocalDate;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import rs.ac.bg.fon.model.Notification;
import rs.ac.bg.fon.model.User;
import rs.ac.bg.fon.model.dto.NotificationDTO;
import rs.ac.bg.fon.repository.NotificationRepository;
import rs.ac.bg.fon.repository.UserRepository;

@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
public class NotificationServiceImplTest {

    @Mock
    private NotificationRepository notificationRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private NotificationServiceImpl notificationService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSendNotification() {
        NotificationDTO request = new NotificationDTO(null, "message", "title", 1L, "senderUsername", LocalDate.now(), false);
        User sender = new User();
        User recipient = new User();
        Notification notification = new Notification("message", "title", recipient, sender);

        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(sender));
        when(userRepository.findByCustomerId(anyLong())).thenReturn(Optional.of(recipient));
        when(notificationRepository.save(any(Notification.class))).thenReturn(notification);

        Notification response = notificationService.sendNotification(request);

        assertNotNull(response);
        verify(userRepository, times(1)).findByUsername(anyString());
        verify(userRepository, times(1)).findByCustomerId(anyLong());
        verify(notificationRepository, times(1)).save(any(Notification.class));
    }

    @Test
    public void testSendNotificationToAll() {
        NotificationDTO request = new NotificationDTO(null, "message", "title", 1L, "senderUsername", LocalDate.now(), false);
        User sender = new User();
        List<User> users = new ArrayList<>();
        users.add(new User());
        users.add(new User());
        Notification notification = new Notification("message", "title", null, sender);

        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(sender));
        when(userRepository.findAll()).thenReturn(users);
        when(notificationRepository.save(any(Notification.class))).thenReturn(notification);

        Notification response = notificationService.sendNotificationToAll(request);

        assertNotNull(response);
        verify(userRepository, times(1)).findByUsername(anyString());
        verify(userRepository, times(1)).findAll();
        verify(notificationRepository, times(users.size())).save(any(Notification.class));
    }

    @Test
    public void testReceivedNotification() {
        when(notificationRepository.markAsReceived(anyLong())).thenReturn(1);

        boolean result = notificationService.receivedNotification(1L);

        assertTrue(result);
        verify(notificationRepository, times(1)).markAsReceived(anyLong());
    }
}
