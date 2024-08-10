package rs.ac.bg.fon.controller;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.bg.fon.model.dto.AuthorDTO;
import rs.ac.bg.fon.service.AuthorService;

@RestController
@RequestMapping("/author")
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    @GetMapping("/all")
    public ResponseEntity<List<AuthorDTO>> getAuthors() {
        List<AuthorDTO> authors = authorService.getAllAuthors();
        return authors.isEmpty() ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(authors, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity saveAuthors(@RequestBody @Valid AuthorDTO author, BindingResult result) {
        if (result.hasErrors()) {
            String errorMessage = result.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.joining("; "));
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }
        return authorService.saveAuthor(author) != null ? new ResponseEntity<>("Author is successfully saved!", HttpStatus.OK)
                : new ResponseEntity<>("There were problem saving author!", HttpStatus.BAD_REQUEST);
    }
}
