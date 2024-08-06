package rs.ac.bg.fon.model.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDTO {

    private Long id;
    private String message;
    private String title;
    private Long recipientId;
    private String senderUsername;
    private LocalDate date;
    private boolean received;

}
