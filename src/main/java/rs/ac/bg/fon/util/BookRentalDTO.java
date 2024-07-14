package rs.ac.bg.fon.util;

import java.time.LocalDate;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookRentalDTO {

    private Long id;
    private String name;
    private Set<String> author;
    private String customer;
    private LocalDate borrowed;
    private LocalDate returnBy;
    private LocalDate returned;

}
