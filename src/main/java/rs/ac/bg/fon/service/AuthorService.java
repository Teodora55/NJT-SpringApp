package rs.ac.bg.fon.service;

import java.util.List;
import rs.ac.bg.fon.model.Author;

public interface AuthorService {
    
    Author saveAuthor(Author author);
    
    Author updateAuthor(Long id,Author author);
    
    Author deleteAuthor(Long id);
    
    Author getAuthor(Long id);
    
    List<Author> getAllAuthors();
}
