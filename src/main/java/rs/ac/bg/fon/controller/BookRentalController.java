package rs.ac.bg.fon.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.bg.fon.model.Book;
import rs.ac.bg.fon.model.Customer;
import rs.ac.bg.fon.model.User;
import rs.ac.bg.fon.model.dto.BookDTO;
import rs.ac.bg.fon.service.BookRentalService;
import rs.ac.bg.fon.service.BookService;
import rs.ac.bg.fon.service.impl.JwtService;
import rs.ac.bg.fon.model.dto.BookRentalDTO;

@RestController
@RequestMapping(value = "rental", produces = MediaType.APPLICATION_JSON_VALUE)
public class BookRentalController {

    @Autowired
    private BookRentalService bookRentalService;

    @Autowired
    private BookService bookService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserDetailsService userDetailsService;

    @GetMapping("/all")
    public ResponseEntity<List<BookRentalDTO>> getAllRentals() {
        List<BookRentalDTO> bookRentals = bookRentalService.findAll();
        if (bookRentals.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(bookRentals, HttpStatus.OK);
        }
    }

    @GetMapping
    public ResponseEntity<List<BookRentalDTO>> getUsersRentals(@NotNull HttpServletRequest request) {
        String customerUsername = jwtService.extractUsername(jwtService.extractToken(request));
        User user = (User) userDetailsService.loadUserByUsername(customerUsername);
        List<BookRentalDTO> bookRentals = bookRentalService.findByCustomer(user.getCustomer().getId());
        if (bookRentals.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(bookRentals, HttpStatus.OK);
        }
    }

    @GetMapping("/current")
    public ResponseEntity<List<BookRentalDTO>> getUsersCurrentRentals(@NotNull HttpServletRequest request) {
        String customerUsername = jwtService.extractUsername(jwtService.extractToken(request));
        User user = (User) userDetailsService.loadUserByUsername(customerUsername);
        List<BookRentalDTO> bookRentals = bookRentalService.findCustomersCurrentRentals(user.getCustomer().getId());
        if (bookRentals.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(bookRentals, HttpStatus.OK);
        }
    }

    @PostMapping(value = "/borrow")
    public ResponseEntity<BookRentalDTO> borrowBook(@NotNull HttpServletRequest request, @RequestBody Long bookId) {
        String customerUsername = jwtService.extractUsername(jwtService.extractToken(request));
        User user = (User) userDetailsService.loadUserByUsername(customerUsername);
        Book book = bookService.getBook(bookId);
        if (user != null && user.getCustomer() != null
                && book != null) {
            if(bookRentalService.findExistingRentalByBookId(user.getCustomer().getId(), bookId) != null)
                return new ResponseEntity<>(HttpStatus.ALREADY_REPORTED);
            Customer customer = user.getCustomer();
            BookRentalDTO rental = bookRentalService.createRental(customer, book);
            if (rental == null) {
                return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
            }
            return new ResponseEntity<>(rental, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping(value = "/return")
    public ResponseEntity<BookRentalDTO> returnBook(@RequestBody Long id) {
        BookRentalDTO rental = bookRentalService.returnBook(id);
        if (rental == null) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity<>(rental, HttpStatus.CREATED);
    }

    @PostMapping(value = "/extend")
    public ResponseEntity<BookRentalDTO> extendReturnBy(@RequestBody Long id) {
        BookRentalDTO rental = bookRentalService.extendReturnByDate(id);
        if (rental == null) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity<>(rental, HttpStatus.CREATED);
    }
}
