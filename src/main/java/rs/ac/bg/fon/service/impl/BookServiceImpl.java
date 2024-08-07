package rs.ac.bg.fon.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
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

    @Value("${file.upload-dir}")
    private String uploadDir;

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
            existing.setAuthors(book.getAuthors()
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
    public List<BookDTO> getAllBooks() {
        List<Book> b = bookRepository.findAll();
        return bookRepository.findAll()
                .stream().map(BookMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<BookCopyDTO> getAllBookCopies(Long bookId) {
        return bookRepository.getAllBookCopies(bookId)
                .stream().map(BookCopyMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public BookDTO uploadBookCover(Long bookId, MultipartFile file) {
        Book existing = bookRepository.findById(bookId).orElse(null);
        if (existing != null && file != null) {
            String coverPath = readFile(file);
            if (!coverPath.equals("")) {
                existing.setCoverUrl(coverPath);
                bookRepository.save(existing);
                return BookMapper.toDto(existing);
            }
        }
        return null;
    }

    private String readFile(MultipartFile coverFile) {
        String fileName = coverFile.getOriginalFilename();
        Path filePath = Paths.get(fileName);
        if (uploadDir != null) {
            filePath = Paths.get(uploadDir, fileName);
        }
        try {
            Files.write(filePath, coverFile.getBytes());
            return fileName;
        } catch (IOException ex) {
            Logger.getLogger(BookServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return "";
        }
    }

}
