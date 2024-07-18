package rs.ac.bg.fon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.bg.fon.service.NotificationService;
import rs.ac.bg.fon.model.dto.NotificationDTO;

@RestController
@RequestMapping(value = "/notify")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @PostMapping
    public ResponseEntity notifyUser(@RequestBody NotificationDTO request) {
        return notificationService.sendNotification(request) != null
                ? new ResponseEntity(HttpStatus.OK) : new ResponseEntity(HttpStatus.NOT_ACCEPTABLE);
    }

    @PostMapping("/all")
    public ResponseEntity notifyAllUsers(@RequestBody NotificationDTO request) {
        return notificationService.sendNotificationToAll(request) != null
                ? new ResponseEntity(HttpStatus.OK) : new ResponseEntity(HttpStatus.NOT_ACCEPTABLE);
    }

    @PutMapping("/received/{id}")
    public ResponseEntity userReceivedNotification(@PathVariable("id") Long id) {
        return notificationService.receivedNotification(id)
                ? new ResponseEntity(HttpStatus.OK) : new ResponseEntity(HttpStatus.NOT_ACCEPTABLE);
    }

}
