package rs.ac.bg.fon.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import rs.ac.bg.fon.model.Author;
import rs.ac.bg.fon.model.dto.AuthorDTO;
import rs.ac.bg.fon.model.mapper.AuthorMapper;
import rs.ac.bg.fon.repository.AuthorRepository;

@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
public class AuthorServiceImplTest {

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private AuthorServiceImpl authorService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveAuthor() {
        AuthorDTO authorDTO = new AuthorDTO(null, "John", "Doe", 1970, null);

        Author author = AuthorMapper.toEntity(authorDTO);
        when(authorRepository.save(any(Author.class))).thenReturn(author);

        AuthorDTO result = authorService.saveAuthor(authorDTO);

        assertNotNull(result);
        assertEquals("John", result.getFirstname());
        verify(authorRepository).save(any(Author.class));
    }

    @Test
    void testGetAllAuthors() {
        List<Author> authors = Arrays.asList(
                new Author(1L, "John", "Doe", 1970, null, null),
                new Author(2L, "Jane", "Doe", 1980, null, null)
        );
        when(authorRepository.findAll()).thenReturn(authors);

        List<AuthorDTO> result = authorService.getAllAuthors();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("John", result.get(0).getFirstname());
        assertEquals("Jane", result.get(1).getFirstname());
        verify(authorRepository).findAll();
    }
}
