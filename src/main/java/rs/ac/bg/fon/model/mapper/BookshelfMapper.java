package rs.ac.bg.fon.model.mapper;

import rs.ac.bg.fon.model.Bookshelf;
import rs.ac.bg.fon.model.dto.BookshelfDTO;

public class BookshelfMapper {

    public static BookshelfDTO toDto(Bookshelf bookshelf) {
        if (bookshelf == null) {
            return null;
        }

        BookshelfDTO bookshelfDTO = new BookshelfDTO();
        bookshelfDTO.setId(bookshelf.getId());
        bookshelfDTO.setName(bookshelf.getName());

        return bookshelfDTO;
    }

    public static Bookshelf toEntity(BookshelfDTO bookshelfDTO) {
        if (bookshelfDTO == null) {
            return null;
        }

        Bookshelf bookshelf = new Bookshelf();
        bookshelf.setId(bookshelfDTO.getId());
        bookshelf.setName(bookshelfDTO.getName());

        return bookshelf;
    }
}
