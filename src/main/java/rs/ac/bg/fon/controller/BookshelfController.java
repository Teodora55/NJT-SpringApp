package rs.ac.bg.fon.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.bg.fon.model.Bookshelf;
import rs.ac.bg.fon.service.BookshelfService;

@RestController
@RequestMapping("/bookshelf")
public class BookshelfController {
    
    @Autowired
    private BookshelfService bookshelfService;
    
        @GetMapping("/all")
    public ResponseEntity<List<Bookshelf>> getBookshelves(){
        List<Bookshelf> shelves = bookshelfService.getAllBookshelves();
        return shelves.isEmpty() ?  new ResponseEntity<List<Bookshelf>>(HttpStatus.NOT_FOUND) : new ResponseEntity<List<Bookshelf>>(shelves, HttpStatus.OK);
    }
    
}
