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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.bg.fon.model.Book;
import rs.ac.bg.fon.model.BookRental;
import rs.ac.bg.fon.model.Customer;
import rs.ac.bg.fon.model.User;
import rs.ac.bg.fon.service.BookRentalService;
import rs.ac.bg.fon.service.BookService;
import rs.ac.bg.fon.service.CustomerService;
import rs.ac.bg.fon.service.impl.JwtService;
import rs.ac.bg.fon.util.DateHelper;

@RestController
@RequestMapping(value = "rental", produces = MediaType.APPLICATION_JSON_VALUE)
public class BookRentalController {

    @Autowired
    private BookRentalService bookRentalService;

    @Autowired
    private BookService bookService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserDetailsService userDetailsService;

    @GetMapping
    public ResponseEntity<List<BookRental>> getAllRentals() {
        List<BookRental> bookRentals = bookRentalService.findAll();
        if (bookRentals.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(bookRentals, HttpStatus.OK);
        }
    }

    @PostMapping(value = "/borrow")
    public ResponseEntity<BookRental> borrowBook(@NotNull HttpServletRequest request, @RequestBody Long bookId) {
        String customerUsername = jwtService.extractUsername(jwtService.extractToken(request));
        User user = (User) userDetailsService.loadUserByUsername(customerUsername);
        Book book = bookService.getBook(bookId);
        if (user != null && user.getCustomer() != null 
                && book != null 
                && bookRentalService.findExistingRentalByBookId(user.getCustomer().getId(), bookId) == null) {
            Customer customer = user.getCustomer();
            BookRental rental = bookRentalService.createRental(customer, book);
            if (rental == null) {
                return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
            }
            return new ResponseEntity<BookRental>(bookRentalService.saveBookRental(rental),
                    HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping(value = "/customers/{customerId}/books/{isbn}/return")
    public ResponseEntity<BookRental> returnBook(@PathVariable("customerId") Long customerId,
            @PathVariable("isbn") String isbn) {
        BookRental bookRental = bookRentalService.findExistingRental(customerId, isbn);
        if (bookRental != null) {
            bookRental.setReturnedAt(DateHelper.getTodayDate());
            bookRentalService.update(bookRental, bookRental.getId());
            return new ResponseEntity<BookRental>(bookRental, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/customers/{customerId}/books/{isbn}/extend")
    public ResponseEntity<BookRental> extendReturnBy(@PathVariable("customerId") Long customerId,
            @PathVariable("isbn") String isbn) {
        BookRental bookRental = bookRentalService.findExistingRental(customerId, isbn);
        if (bookRental != null) {
            bookRental.setReturnBy(bookRental.getReturnBy().plusWeeks(1));
            bookRentalService.update(bookRental, bookRental.getId());
            return new ResponseEntity<BookRental>(bookRental, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
