package rs.ac.bg.fon.service;

import java.util.List;
import rs.ac.bg.fon.model.Book;
import rs.ac.bg.fon.model.BookRental;
import rs.ac.bg.fon.model.Customer;
import rs.ac.bg.fon.model.dto.BookRentalDTO;

public interface BookRentalService {

    BookRental findById(Long id);

    BookRental returnBook(Long id);
    
    BookRental extendReturnByDate(Long id);
    
    BookRental findExistingRentalByBookId(Long customerId,Long bookId);
    
    BookRental createRental(Customer customer, Book book);

    List<BookRentalDTO> findByCustomer(Long customerId);

    public List<BookRentalDTO> findCustomersCurrentRentals(Long id);
}
