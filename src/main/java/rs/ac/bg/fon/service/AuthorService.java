package rs.ac.bg.fon.service;

import java.util.List;
import rs.ac.bg.fon.model.dto.AuthorDTO;

public interface AuthorService {
    
    AuthorDTO saveAuthor(AuthorDTO author);
                            
    List<AuthorDTO> getAllAuthors();
}
