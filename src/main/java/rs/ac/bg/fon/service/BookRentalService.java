package rs.ac.bg.fon.service;

import java.util.List;
import org.springframework.data.domain.Page;
import rs.ac.bg.fon.model.Book;
import rs.ac.bg.fon.model.BookRental;
import rs.ac.bg.fon.model.Customer;

public interface BookRentalService {

    BookRental saveBookRental(BookRental bookRental);

    List<BookRental> findAll();

    BookRental findById(Long id);

    BookRental update(BookRental bookRental, Long id);

    BookRental delete(Long id);

    BookRental findExistingRental(Long customerId,String isbn);
    
    BookRental findExistingRentalByBookId(Long customerId,Long bookId);

    void deleteByCustomerId(Long customerId);

    Page<BookRental> search(String searchInput, int pageNo, int itemsPerPage);

    List<BookRental> findByCustomer(Long customerId);

    BookRental createRental(Customer customer, Book book);
}
