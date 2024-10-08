package rs.ac.bg.fon.controller;

import jakarta.validation.Valid;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
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
    public ResponseEntity notifyUser(@RequestBody @Valid NotificationDTO request, BindingResult result) {
        if (result.hasErrors()) {
            String errorMessage = result.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.joining("; "));
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }
        return notificationService.sendNotification(request) != null
                ? new ResponseEntity("User successfully notified!", HttpStatus.OK)
                : new ResponseEntity("There were error while notifying user!", HttpStatus.NOT_ACCEPTABLE);
    }

    @PostMapping("/all")
    public ResponseEntity notifyAllUsers(@RequestBody @Valid NotificationDTO request, BindingResult result) {
        if (result.hasErrors()) {
            String errorMessage = result.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.joining("; "));
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }
        return notificationService.sendNotificationToAll(request) != null
                ? new ResponseEntity("Users successfully notified!", HttpStatus.OK)
                : new ResponseEntity("There were error while notifying users!", HttpStatus.NOT_ACCEPTABLE);
    }

    @PutMapping("/received/{id}")
    public ResponseEntity userReceivedNotification(@PathVariable("id") Long id) {
        return notificationService.receivedNotification(id)
                ? new ResponseEntity(HttpStatus.OK) : new ResponseEntity(HttpStatus.NOT_ACCEPTABLE);
    }

}
