package rs.ac.bg.fon.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.bg.fon.model.dto.AuthorDTO;
import rs.ac.bg.fon.service.AuthorService;

@RestController
@RequestMapping("/author")
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    @GetMapping("/{id}")
    public ResponseEntity<AuthorDTO> getAuthorById(@PathVariable("id") Long id) {
        AuthorDTO author = authorService.getAuthor(id);
        return author != null ? new ResponseEntity<AuthorDTO>(author, HttpStatus.OK)
                : new ResponseEntity<AuthorDTO>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/all")
    public ResponseEntity<List<AuthorDTO>> getAuthors() {
        List<AuthorDTO> authors = authorService.getAllAuthors();
        return authors.isEmpty() ? new ResponseEntity<List<AuthorDTO>>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<List<AuthorDTO>>(authors, HttpStatus.OK);
    }
}
