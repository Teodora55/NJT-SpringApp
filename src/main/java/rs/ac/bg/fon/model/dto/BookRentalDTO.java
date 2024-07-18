package rs.ac.bg.fon.model.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class BookRentalDTO {
    private Long id;
    private LocalDate borrowedAt;
    private LocalDate returnBy;
    private LocalDate returnedAt;
    private CustomerDTO customer;
    private BookCopyDTO bookCopy;

}
