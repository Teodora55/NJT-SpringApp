package rs.ac.bg.fon.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.ac.bg.fon.model.Book;
import rs.ac.bg.fon.model.dto.BookCopyDTO;
import rs.ac.bg.fon.model.dto.BookDTO;
import rs.ac.bg.fon.model.mapper.AuthorMapper;
import rs.ac.bg.fon.model.mapper.BookCopyMapper;
import rs.ac.bg.fon.model.mapper.BookMapper;
import rs.ac.bg.fon.repository.BookRepository;
import rs.ac.bg.fon.service.BookService;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Override
    public BookDTO saveBook(BookDTO book) {
        return BookMapper.toDto(bookRepository.save(BookMapper.toEntity(book)));
    }

    @Transactional
    @Override
    public BookDTO updateBook(Long id, BookDTO book) {
        Book existing = bookRepository.findById(id).orElse(null);
        if (existing != null) {
            existing.setName(book.getName());
            existing.setAuthor(book.getAuthors()
                    .stream().map(AuthorMapper::toEntity).collect(Collectors.toSet()));
            bookRepository.save(existing);
            return BookMapper.toDto(existing);
        }
        return null;
    }

    @Transactional
    @Override
    public BookDTO deleteBook(Long id) {
        Book existing = bookRepository.findById(id).orElse(null);
        if (existing != null) {
            bookRepository.delete(existing);
            return BookMapper.toDto(existing);
        }
        return null;
    }

    @Override
    public Book getBook(Long id) {
        return bookRepository.findById(id).orElse(null);
    }

    @Override
    public BookDTO getBookDTO(Long id) {
        return BookMapper.toDto(bookRepository.findById(id).orElse(null));
    }

    @Override
    public List<BookDTO> getAllBooks() {
        return bookRepository.findAll()
                .stream().map(BookMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<BookCopyDTO> getAllBookCopies(Long bookId) {
        return bookRepository.getAllBookCopies(bookId)
                .stream().map(BookCopyMapper::toDto).collect(Collectors.toList());
    }

}
