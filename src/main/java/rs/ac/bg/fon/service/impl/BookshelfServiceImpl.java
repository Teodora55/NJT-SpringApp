package rs.ac.bg.fon.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.bg.fon.model.dto.BookshelfDTO;
import rs.ac.bg.fon.model.mapper.BookshelfMapper;
import rs.ac.bg.fon.repository.BookshelfRepository;
import rs.ac.bg.fon.service.BookshelfService;

@Service
public class BookshelfServiceImpl implements BookshelfService {

    @Autowired
    private BookshelfRepository bookshelfRepository;

    @Override
    public List<BookshelfDTO> getAllBookshelves() {
        return bookshelfRepository.findAll()
                .stream().map(BookshelfMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public BookshelfDTO saveBookshelves(BookshelfDTO bookshelf) {
        return BookshelfMapper.toDto(bookshelfRepository.save(BookshelfMapper.toEntity(bookshelf)));
    }

}
