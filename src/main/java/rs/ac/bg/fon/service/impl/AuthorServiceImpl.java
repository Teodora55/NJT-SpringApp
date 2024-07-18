package rs.ac.bg.fon.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.bg.fon.model.Author;
import rs.ac.bg.fon.model.dto.AuthorDTO;
import rs.ac.bg.fon.model.mapper.AuthorMapper;
import rs.ac.bg.fon.repository.AuthorRepository;
import rs.ac.bg.fon.service.AuthorService;

@Service
public class AuthorServiceImpl implements AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    @Override
    public AuthorDTO saveAuthor(AuthorDTO author) {
        return AuthorMapper.toDto(authorRepository.save(AuthorMapper.toEntity(author)));
    }

    @Override
    public AuthorDTO updateAuthor(Long id, AuthorDTO updatedAuthor) {
        Author existingAuthor = authorRepository.findById(id).orElse(null);

        if (existingAuthor != null) {
            existingAuthor.setFirstname(updatedAuthor.getFirstname());
            existingAuthor.setLastname(updatedAuthor.getLastname());
            existingAuthor.setYearOfBirth(updatedAuthor.getYearOfBirth());
            existingAuthor.setYearOfDeath(updatedAuthor.getYearOfDeath());

            return AuthorMapper.toDto(authorRepository.save(existingAuthor));
        }
        return null;
    }

    @Override
    public AuthorDTO deleteAuthor(Long id) {
        Author author = authorRepository.findById(id).orElse(null);
        if (author != null) {
            authorRepository.deleteById(id);
        }
        return AuthorMapper.toDto(author);
    }

    @Override
    public AuthorDTO getAuthor(Long id) {
        return AuthorMapper.toDto(authorRepository.findById(id).orElse(null));
    }

    @Override
    public List<AuthorDTO> getAllAuthors() {
        return authorRepository.findAll().stream().map(AuthorMapper::toDto).collect(Collectors.toList());
    }

}
