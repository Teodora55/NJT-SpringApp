package rs.ac.bg.fon.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NotificationDTO {
    
    private String message;
    private String title;
    private Long recipient;
    private String sender;
    
}
