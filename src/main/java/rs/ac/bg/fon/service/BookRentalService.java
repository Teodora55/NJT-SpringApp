package rs.ac.bg.fon.service;

import java.util.List;
import rs.ac.bg.fon.model.Book;
import rs.ac.bg.fon.model.BookRental;
import rs.ac.bg.fon.model.Customer;
import rs.ac.bg.fon.model.dto.BookRentalDTO;

public interface BookRentalService {

    List<BookRentalDTO> findAll();

    BookRental findById(Long id);

    BookRentalDTO returnBook(Long id);
    
    BookRentalDTO extendReturnByDate(Long id);
    
    BookRental findExistingRentalByBookId(Long customerId,Long bookId);
    
    BookRentalDTO createRental(Customer customer, Book book);

    List<BookRentalDTO> findByCustomer(Long customerId);

    public List<BookRentalDTO> findCustomersCurrentRentals(Long id);
}
