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
import rs.ac.bg.fon.model.dto.BookshelfDTO;
import rs.ac.bg.fon.service.BookshelfService;

@RestController
@RequestMapping("/bookshelf")
public class BookshelfController {

    @Autowired
    private BookshelfService bookshelfService;

    @GetMapping("/all")
    public ResponseEntity<List<BookshelfDTO>> getBookshelves() {
        List<BookshelfDTO> shelves = bookshelfService.getAllBookshelves();
        return shelves.isEmpty() ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(shelves, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity saveBookshelves(@RequestBody @Valid BookshelfDTO bookshelf, BindingResult result) {
        if (result.hasErrors()) {
            String errorMessage = result.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.joining("; "));
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }
        return bookshelfService.saveBookshelves(bookshelf) != null
                ? new ResponseEntity<>("Bookshelf is successfully saved!", HttpStatus.OK)
                : new ResponseEntity<>("There were problem saving bookshelf!", HttpStatus.BAD_REQUEST);
    }

}
