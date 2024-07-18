package rs.ac.bg.fon.model.mapper;

import rs.ac.bg.fon.model.BookCopy;
import rs.ac.bg.fon.model.dto.BookCopyDTO;

public class BookCopyMapper {

    public static BookCopyDTO toDto(BookCopy bookCopy) {
        if (bookCopy == null) {
            return null;
        }

        BookCopyDTO bookCopyDTO = new BookCopyDTO();
        bookCopyDTO.setIsbn(bookCopy.getIsbn());
        bookCopyDTO.setStatus(bookCopy.getStatus());
        bookCopyDTO.setBook(BookMapper.toDto(bookCopy.getBook()));

        return bookCopyDTO;
    }

    public static BookCopy toEntity(BookCopyDTO bookCopyDTO) {
        if (bookCopyDTO == null) {
            return null;
        }

        BookCopy bookCopy = new BookCopy();
        bookCopy.setIsbn(bookCopyDTO.getIsbn());
        bookCopy.setStatus(bookCopyDTO.getStatus());
        bookCopy.setBook(BookMapper.toEntity(bookCopyDTO.getBook()));

        return bookCopy;
    }
}
