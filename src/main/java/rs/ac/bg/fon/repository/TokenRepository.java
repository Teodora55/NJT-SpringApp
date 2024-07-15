package rs.ac.bg.fon.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.bg.fon.model.Token;

public interface TokenRepository  extends JpaRepository<Token, Long>{
    
    Optional<Token> findByToken(String token);
    
}
