package rs.ac.bg.fon.service.impl;

import java.util.List;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import rs.ac.bg.fon.model.Book;
import rs.ac.bg.fon.model.BookCopy;
import rs.ac.bg.fon.model.BookCopyStatus;
import rs.ac.bg.fon.model.Customer;
import rs.ac.bg.fon.model.BookRental;
import rs.ac.bg.fon.repository.BookRentalRepository;
import rs.ac.bg.fon.service.BookRentalService;

@Service
public class BookRentalServiceImpl implements BookRentalService {

    @Autowired
    private BookRentalRepository bookRentalRepository;

    @Transactional
    @Override
    public BookRental saveBookRental(BookRental bookRental) {
        return bookRentalRepository.save(bookRental);
    }

    @Override
    public List<BookRental> findAll() {
        return bookRentalRepository.findAll();
    }

    @Override
    public BookRental findById(Long id) {
        return bookRentalRepository.findOneById(id);
    }

    @Transactional
    @Override
    public BookRental update(BookRental bookRental, Long id) {
        BookRental existing = findById(id);

        if (existing != null) {
            existing.setReturnBy(bookRental.getReturnBy());
            existing.setReturnedAt(bookRental.getReturnedAt());
            existing.setBookCopy(bookRental.getBookCopy());
            existing.setCustomer(bookRental.getCustomer());
            existing.setApprovedByAdmin(bookRental.getApprovedByAdmin());
            bookRentalRepository.save(existing);
        }
        return existing;
    }

    @Override
    public BookRental delete(Long id) {
        BookRental bookRental = findById(id);
        if (bookRental != null) {
            bookRental.getBookCopy().getBookRentals().remove(bookRental);
            bookRental.getCustomer().getBookRentals().remove(bookRental);
            bookRental.getCustomer().getBookRentalApprovals().remove(bookRental);
            bookRentalRepository.delete(bookRental);
        }
        return bookRental;
    }

    @Override
    public BookRental findExistingRental(Long customerId, String isbn) {
        return bookRentalRepository.findByCustomerIdAndIsbnAndReturnedAtIsNull(customerId,isbn);
    }
    
    @Override
    public BookRental findExistingRentalByBookId(Long customerId, Long bookId) {
        return bookRentalRepository.findByCustomerIdAndBookIdAndReturnedAtIsNull(customerId, bookId);
    }

    @Override
    public void deleteByCustomerId(Long customerId) {
        bookRentalRepository.deleteByCustomerId(customerId);
    }

    @Override
    public Page<BookRental> search(String searchInput, int pageNo, int itemsPerPage) {
        Pageable pageable = PageRequest.of(pageNo, itemsPerPage);
        List<BookRental> rentalsList = bookRentalRepository.filterBookRentals(searchInput);
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), rentalsList.size());
        return new PageImpl<>(rentalsList.subList(start, end), pageable, rentalsList.size());
    }

    @Override
    public List<BookRental> findByCustomer(Long customerId) {
        return bookRentalRepository.findByCustomerId(customerId);
    }

    @Override
    public BookRental createRental(Customer customer, Book book) {
        BookCopy bookCopy = book.getBookCopies().stream()
                .filter(copy -> copy.getStatus() == BookCopyStatus.AVAILABLE)
                .findFirst().orElse(null);
        if(bookCopy == null)
            return null;
        return new BookRental(customer, bookCopy);
    }
    
}
