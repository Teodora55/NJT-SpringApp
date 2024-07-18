package rs.ac.bg.fon.service.impl;

import java.util.List;
import java.time.LocalDate;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.bg.fon.model.Book;
import rs.ac.bg.fon.model.BookCopy;
import rs.ac.bg.fon.model.BookCopyStatus;
import rs.ac.bg.fon.model.Customer;
import rs.ac.bg.fon.model.BookRental;
import rs.ac.bg.fon.repository.BookCopyRepository;
import rs.ac.bg.fon.repository.BookRentalRepository;
import rs.ac.bg.fon.service.BookRentalService;
import rs.ac.bg.fon.model.dto.BookRentalDTO;
import rs.ac.bg.fon.model.mapper.BookRentalMapper;

@Service
public class BookRentalServiceImpl implements BookRentalService {

    @Autowired
    private BookRentalRepository bookRentalRepository;

    @Autowired
    private BookCopyRepository bookCopyRepository;

    @Override
    public List<BookRentalDTO> findAll() {
        return bookRentalRepository.findAll()
                .stream().map(BookRentalMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public BookRental findById(Long id) {
        return bookRentalRepository.findById(id).orElse(null);
    }

    @Override
    public BookRentalDTO returnBook(Long id) {
        BookRental existing = findById(id);
        if (existing != null) {
            existing.setReturnedAt(LocalDate.now());
            bookRentalRepository.save(existing);
            BookCopy bookCopy = existing.getBookCopy();
            bookCopy.setStatus(BookCopyStatus.AVAILABLE);
            bookCopyRepository.save(bookCopy);
        }
        return BookRentalMapper.toDto(existing);
    }

    @Override
    public BookRentalDTO extendReturnByDate(Long id) {
        BookRental existing = findById(id);
        if (existing != null) {
            existing.setReturnBy(existing.getReturnBy().plusWeeks(2));
            bookRentalRepository.save(existing);
        }
        return BookRentalMapper.toDto(existing);
    }

    @Override
    public BookRental findExistingRentalByBookId(Long customerId, Long bookId) {
        return bookRentalRepository.findByCustomerIdAndBookIdAndReturnedAtIsNull(customerId, bookId);
    }

    @Override
    public List<BookRentalDTO> findByCustomer(Long customerId) {
        return bookRentalRepository.findByCustomerId(customerId)
                .stream().map(BookRentalMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public BookRentalDTO createRental(Customer customer, Book book) {
        BookCopy bookCopy = book.getBookCopies().stream()
                .filter(copy -> copy.getStatus() == BookCopyStatus.AVAILABLE)
                .findFirst().orElse(null);
        if (bookCopy == null) {
            return null;
        }
        BookRental rental = new BookRental(customer, bookCopy);
        bookRentalRepository.save(rental);
        bookCopy.setStatus(BookCopyStatus.RENTED);
        bookCopyRepository.save(bookCopy);
        return BookRentalMapper.toDto(rental);
    }
    
    @Override
    public List<BookRentalDTO> findCustomersCurrentRentals(Long id) {
        return bookRentalRepository.findByCustomerIdAndReturnedAtIsNull(id)
                .stream().map(BookRentalMapper::toDto).collect(Collectors.toList());
    }

}
