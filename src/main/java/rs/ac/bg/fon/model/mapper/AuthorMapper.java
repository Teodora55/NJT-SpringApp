package rs.ac.bg.fon.model.mapper;

import rs.ac.bg.fon.model.Author;
import rs.ac.bg.fon.model.dto.AuthorDTO;

public class AuthorMapper {

    public static AuthorDTO toDto(Author author) {
        if (author == null) {
            return null;
        }

        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setId(author.getId());
        authorDTO.setFirstname(author.getFirstname());
        authorDTO.setLastname(author.getLastname());
        authorDTO.setYearOfBirth(author.getYearOfBirth());
        authorDTO.setYearOfDeath(author.getYearOfDeath());

        return authorDTO;
    }

    public static Author toEntity(AuthorDTO authorDTO) {
        if (authorDTO == null) {
            return null;
        }

        Author author = new Author();
        author.setId(authorDTO.getId());
        author.setFirstname(authorDTO.getFirstname());
        author.setLastname(authorDTO.getLastname());
        author.setYearOfBirth(authorDTO.getYearOfBirth());
        author.setYearOfDeath(authorDTO.getYearOfDeath());

        return author;
    }
}
