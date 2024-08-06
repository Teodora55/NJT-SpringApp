package rs.ac.bg.fon.service;

import java.util.Set;
import rs.ac.bg.fon.model.Book;

public interface LoadDataService {
    
    Set<Book> fetchBooksFromApi();
    
}
