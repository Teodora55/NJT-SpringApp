package rs.ac.bg.fon.model.mapper;

import java.util.stream.Collectors;
import rs.ac.bg.fon.model.Book;
import rs.ac.bg.fon.model.dto.BookDTO;

public class BookMapper {

    public static BookDTO toDto(Book book) {
        if (book == null) {
            return null;
        }

        BookDTO bookDTO = new BookDTO();
        bookDTO.setId(book.getId());
        bookDTO.setName(book.getName());
        bookDTO.setCoverUrl(book.getCoverUrl());
        bookDTO.setBookshelves(book.getBookshelves().stream().map(BookshelfMapper::toDto).collect(Collectors.toSet()));
        bookDTO.setAuthors(book.getAuthors().stream().map(AuthorMapper::toDto).collect(Collectors.toSet()));

        return bookDTO;
    }

    public static Book toEntity(BookDTO bookDTO) {
        if (bookDTO == null) {
            return null;
        }

        Book book = new Book();
        book.setId(bookDTO.getId());
        book.setName(bookDTO.getName());
        book.setCoverUrl(bookDTO.getCoverUrl());
        book.setBookshelves(bookDTO.getBookshelves().stream().map(BookshelfMapper::toEntity).collect(Collectors.toSet()));
        book.setAuthors(bookDTO.getAuthors().stream().map(AuthorMapper::toEntity).collect(Collectors.toSet()));

        return book;
    }
}
