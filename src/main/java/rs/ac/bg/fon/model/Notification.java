package rs.ac.bg.fon.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "notification")
@Getter
@Setter
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column
    private String message;

    @ManyToOne
    @JoinColumn(name = "recipient_id")
    @JsonBackReference(value = "recipient-notifications")
    private User recipient;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    @JsonBackReference
    private User sender;

    @Column
    private LocalDate date;

    @Column
    private boolean received;

    public Notification() {
    }

    public Notification(String message, String title, User recipient, User sender) {
        this.title = title;
        this.message = message;
        this.recipient = recipient;
        this.sender = sender;
        this.date = LocalDate.now();
        this.received = false;
    }

}
