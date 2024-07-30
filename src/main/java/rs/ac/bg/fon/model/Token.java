package rs.ac.bg.fon.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "token")
@NoArgsConstructor
@Data
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    String token;

    @Column
    LocalDateTime createdAt;

    @Column
    boolean isTokenValid;

    public Token(String token) {
        this.token = token;
        this.createdAt = LocalDateTime.now();
        this.isTokenValid = true;
    }
}
