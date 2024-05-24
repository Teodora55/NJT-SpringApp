package rs.ac.bg.fon.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.bg.fon.model.Author;
import rs.ac.bg.fon.service.AuthorService;

@RestController
@RequestMapping("/author")
public class AuthorController {
        @Autowired
    private AuthorService authorService;
    
    @GetMapping("/{id}")
    public ResponseEntity<Author> getAuthorById(@PathVariable("id") Long id){
        Author author = authorService.getAuthor(id);
        return author != null ? new ResponseEntity<Author>(author, HttpStatus.OK) : 
                new ResponseEntity<Author>(HttpStatus.NOT_FOUND);
    }
    
    @GetMapping("/all")
    public ResponseEntity<List<Author>> getAuthors(){
        List<Author> authors = authorService.getAllAuthors();
        return authors.isEmpty() ?  new ResponseEntity<List<Author>>(HttpStatus.NOT_FOUND) : new ResponseEntity<List<Author>>(authors, HttpStatus.OK);
    }
}
