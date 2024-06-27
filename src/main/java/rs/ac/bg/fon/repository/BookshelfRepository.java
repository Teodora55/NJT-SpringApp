package rs.ac.bg.fon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.bg.fon.model.Bookshelf;

public interface BookshelfRepository  extends JpaRepository<Bookshelf, Long>{
    
    public Bookshelf findByName(String name);
    
}
