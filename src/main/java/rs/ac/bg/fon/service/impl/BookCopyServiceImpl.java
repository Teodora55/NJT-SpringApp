package rs.ac.bg.fon.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.bg.fon.model.BookCopyStatus;
import rs.ac.bg.fon.model.dto.BookCopyDTO;
import rs.ac.bg.fon.model.mapper.BookCopyMapper;
import rs.ac.bg.fon.repository.BookCopyRepository;
import rs.ac.bg.fon.service.BookCopyService;

@Service
public class BookCopyServiceImpl implements BookCopyService {

    @Autowired
    private BookCopyRepository bookCopyRepository;

    @Override
    public BookCopyDTO createBookCopy(BookCopyDTO book) {
        if(book.getStatus() == null)
            book.setStatus(BookCopyStatus.AVAILABLE);
        return BookCopyMapper.toDto(bookCopyRepository.save(BookCopyMapper.toEntity(book)));
    }

    @Override
    public List<BookCopyDTO> findBorrowedBooks(Long customerId) {
        return bookCopyRepository.findBorrowedBooksByCustomer(customerId)
                .stream().map(BookCopyMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public Long getAvailableCopiesCount(Long bookId) {
        return bookCopyRepository.countAvailableCopiesByBookId(bookId);
    }

}
