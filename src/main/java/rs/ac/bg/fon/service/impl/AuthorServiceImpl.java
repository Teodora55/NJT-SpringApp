package rs.ac.bg.fon.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.bg.fon.model.Author;
import rs.ac.bg.fon.repository.AuthorRepository;
import rs.ac.bg.fon.service.AuthorService;

@Service
public class AuthorServiceImpl implements AuthorService{

    @Autowired
    private AuthorRepository authorRepository;

    public Author saveAuthor(Author author) {
        return authorRepository.save(author);
    }

    public Author updateAuthor(Long id, Author updatedAuthor) {
        Author existingAuthor = authorRepository.findById(id).orElse(null);

        if (existingAuthor != null) {
            existingAuthor.setFirstname(updatedAuthor.getFirstname());
            existingAuthor.setLastname(updatedAuthor.getLastname());
            existingAuthor.setYearOfBirth(updatedAuthor.getYearOfBirth());
            existingAuthor.setYearOfDeath(updatedAuthor.getYearOfDeath());
            existingAuthor.setBooks(updatedAuthor.getBooks());

            return authorRepository.save(existingAuthor);
        }
        return null;
    }

    public Author deleteAuthor(Long id) {
        Author author = authorRepository.findById(id).orElse(null);
        if (author != null) {
            authorRepository.deleteById(id);
        }
        return author;
    }

    public Author getAuthor(Long id) {
        return authorRepository.findById(id).orElse(null);
    }

    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }
}

