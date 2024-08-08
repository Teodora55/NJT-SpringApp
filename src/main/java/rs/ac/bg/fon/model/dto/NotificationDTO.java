package rs.ac.bg.fon.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDTO {

    private Long id;
    @NotBlank(message = "Message is required")
    @Pattern(regexp = "^.{20,}$", message = "Message must be at least 20 characters long")
    private String message;
    @NotBlank(message = "Title is required")
    @Pattern(regexp = "^[a-zA-Z][a-zA-Z '-]{5,50}[a-zA-Z]$",
            message = "Title can only contain alphabetic characters, spaces, hyphens, and must be 5-50 characters long")
    private String title;
    private Long recipientId;
    private String senderUsername;
    private LocalDate date;
    private boolean received;

}
