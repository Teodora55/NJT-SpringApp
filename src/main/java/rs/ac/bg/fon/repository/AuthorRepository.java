package rs.ac.bg.fon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.bg.fon.model.Author;

public interface AuthorRepository extends JpaRepository<Author, Long>{
    
}
