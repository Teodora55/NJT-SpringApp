package rs.ac.bg.fon.model.mapper;

import rs.ac.bg.fon.model.BookRental;
import rs.ac.bg.fon.model.dto.BookRentalDTO;

public class BookRentalMapper {
    public static BookRentalDTO toDto(BookRental bookRental) {
        if (bookRental == null) {
            return null;
        }
        
        BookRentalDTO bookRentalDTO = new BookRentalDTO();
        bookRentalDTO.setId(bookRental.getId());
        bookRentalDTO.setBorrowedAt(bookRental.getBorrowedAt());
        bookRentalDTO.setReturnBy(bookRental.getReturnBy());
        bookRentalDTO.setReturnedAt(bookRental.getReturnedAt());
        bookRentalDTO.setCustomer(CustomerMapper.toDto(bookRental.getCustomer()));
        bookRentalDTO.setBookCopy(BookCopyMapper.toDto(bookRental.getBookCopy()));
        
        return bookRentalDTO;
    }

    public static BookRental toEntity(BookRentalDTO bookRentalDTO) {
        if (bookRentalDTO == null) {
            return null;
        }
        
        BookRental bookRental = new BookRental();
        bookRental.setId(bookRentalDTO.getId());
        bookRental.setBorrowedAt(bookRentalDTO.getBorrowedAt());
        bookRental.setReturnBy(bookRentalDTO.getReturnBy());
        bookRental.setReturnedAt(bookRentalDTO.getReturnedAt());
        bookRental.setCustomer(CustomerMapper.toEntity(bookRentalDTO.getCustomer()));
        bookRental.setBookCopy(BookCopyMapper.toEntity(bookRentalDTO.getBookCopy()));
        
        return bookRental;
    }
}
