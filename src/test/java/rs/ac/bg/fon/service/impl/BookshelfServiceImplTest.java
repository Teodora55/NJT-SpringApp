package rs.ac.bg.fon.service.impl;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import rs.ac.bg.fon.model.Bookshelf;
import rs.ac.bg.fon.model.dto.BookshelfDTO;
import rs.ac.bg.fon.model.mapper.BookshelfMapper;
import rs.ac.bg.fon.repository.BookshelfRepository;
import rs.ac.bg.fon.service.BookshelfService;

@SpringBootTest
public class BookshelfServiceImplTest {

    @Autowired
    private BookshelfService bookshelfService;

    @MockBean
    private BookshelfRepository bookshelfRepository;

    @Test
    void testGetAllBookshelves() {
        Bookshelf bookshelf1 = new Bookshelf(1L, "Bookshelf 1");
        Bookshelf bookshelf2 = new Bookshelf(2L, "Bookshelf 2");

        List<Bookshelf> bookshelves = Arrays.asList(bookshelf1, bookshelf2);
        when(bookshelfRepository.findAll()).thenReturn(bookshelves);

        List<BookshelfDTO> result = bookshelfService.getAllBookshelves();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(BookshelfMapper.toDto(bookshelf1), result.get(0));
        assertEquals(BookshelfMapper.toDto(bookshelf2), result.get(1));
    }
}
