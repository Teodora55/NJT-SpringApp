package rs.ac.bg.fon.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.bg.fon.model.Bookshelf;
import rs.ac.bg.fon.repository.BookshelfRepository;
import rs.ac.bg.fon.service.BookshelfService;

@Service
public class BookshelfServiceImpl implements BookshelfService {

    @Autowired
    private BookshelfRepository bookshelfRepository;
    
    @Override
    public List<Bookshelf> getAllBookshelves() {
        return bookshelfRepository.findAll();
    }

}
