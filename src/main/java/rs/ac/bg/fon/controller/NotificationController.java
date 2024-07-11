package rs.ac.bg.fon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.bg.fon.model.Notification;
import rs.ac.bg.fon.service.NotificationService;
import rs.ac.bg.fon.util.NotificationRequest;

@RestController
@RequestMapping(value = "/notify")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @PostMapping
    public ResponseEntity notifyUser(@RequestBody NotificationRequest request) {
        return notificationService.sendNotification(request) != null
                ? new ResponseEntity(HttpStatus.OK) : new ResponseEntity(HttpStatus.NOT_ACCEPTABLE);
    }

    @PostMapping("/all")
    public ResponseEntity notifyAllUsers(@RequestBody NotificationRequest request) {
        return notificationService.sendNotificationToAll(request) != null
                ? new ResponseEntity(HttpStatus.OK) : new ResponseEntity(HttpStatus.NOT_ACCEPTABLE);
    }

}
