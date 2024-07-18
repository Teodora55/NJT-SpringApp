package rs.ac.bg.fon.model.dto;

import java.time.LocalDate;
import lombok.Data;

@Data
public class NotificationDTO {

    private String message;
    private String title;
    private Long recipientId;
    private String senderUsername;
    private LocalDate date;
    private boolean received;

}
