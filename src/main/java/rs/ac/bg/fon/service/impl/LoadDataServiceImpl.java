package rs.ac.bg.fon.service.impl;

import jakarta.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import rs.ac.bg.fon.model.Author;
import rs.ac.bg.fon.model.Book;
import rs.ac.bg.fon.model.BookCopy;
import rs.ac.bg.fon.model.BookCopyStatus;
import rs.ac.bg.fon.model.Bookshelf;
import rs.ac.bg.fon.repository.AuthorRepository;
import rs.ac.bg.fon.repository.BookCopyRepository;
import rs.ac.bg.fon.repository.BookRepository;
import rs.ac.bg.fon.repository.BookshelfRepository;
import rs.ac.bg.fon.service.LoadDataService;
import rs.ac.bg.fon.util.ApiBookResponse;

@Service
public class LoadDataServiceImpl implements LoadDataService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookshelfRepository bookshelfRepository;

    @Autowired
    private BookCopyRepository bookCopyRepository;

    @Transactional
    @Override
    public Set<Book> fetchBooksFromApi() {
        RestTemplate restTemplate = new RestTemplate();
        String apiUrl = "https://gutendex.com/books/";

        ApiBookResponse response = restTemplate.getForObject(apiUrl, ApiBookResponse.class);

        if (response != null && response.getResults() != null) {
            Set<Book> books = response.getResults().stream()
                    .map(this::convertToBook)
                    .collect(Collectors.toSet());

            return books;
        }

        return null;
    }

    private Book convertToBook(ApiBookResponse.ApiBook apiBook) {
        String bookName = apiBook.getTitle();
        int index = bookName.indexOf(";");
        if (index != -1) {
            bookName = bookName.substring(0, index);
        }
        Book book = bookRepository.findByName(bookName);
        if (book == null) {
            book = Book.builder()
                    .name(bookName)
                    .authors(convertAuthors(apiBook.getAuthors()))
                    .bookshelves(readShelves(apiBook))
                    .coverUrl(apiBook.getFormats().get("image/jpeg"))
                    .build();
            bookRepository.saveAndFlush(book);
            for (int i = 0; i < 3; i++) {
                createBookCopies(book);
            }
        }
        return book;
    }

    private Set<Author> convertAuthors(List<ApiBookResponse.ApiBook.Author> authors) {
        Set<Author> convertedAuthors = new HashSet<>();
        for (ApiBookResponse.ApiBook.Author author : authors) {
            int splitNameIndex = author.getName().indexOf(",");
            String firstname, lastname = "";
            if (splitNameIndex == -1) {
                firstname = author.getName();
            } else {
                firstname = author.getName().substring(splitNameIndex + 2);
                lastname = author.getName().substring(0, splitNameIndex);
            }
            Author a = authorRepository.findByFirstnameAndLastname(firstname, lastname);
            if (a == null) {
                a = Author.builder()
                        .firstname(firstname)
                        .lastname(lastname)
                        .yearOfBirth(author.getBirth_year())
                        .yearOfDeath(author.getDeath_year())
                        .build();
                authorRepository.saveAndFlush(a);
            }
            convertedAuthors.add(a);
        }
        return convertedAuthors;
    }

    private Set<Bookshelf> readShelves(ApiBookResponse.ApiBook apiBook) {
        Set<Bookshelf> readShelves = new HashSet<>();
        for (String bookshelf : apiBook.getBookshelves()) {
            Bookshelf convertedBookshelf = bookshelfRepository.findByName(bookshelf);
            if (convertedBookshelf == null) {
                convertedBookshelf = new Bookshelf();
                convertedBookshelf.setName(bookshelf);
                bookshelfRepository.saveAndFlush(convertedBookshelf);
            }
            readShelves.add(convertedBookshelf);
        }
        return readShelves;
    }

    private void createBookCopies(Book book) {
        BookCopy bookCopy = BookCopy.builder()
                .book(book)
                .isbn(createIsbn(book.getId()))
                .status(BookCopyStatus.AVAILABLE)
                .build();
        bookCopyRepository.saveAndFlush(bookCopy);
    }

    private String createIsbn(Long bookId) {
        StringBuilder isbn = new StringBuilder();
        Random random = new Random();
        if (bookId == null) {
            isbn.append("000");
        } else if (bookId < 10) {
            isbn.append("00");
            isbn.append(bookId);
        } else if (bookId < 100) {
            isbn.append("0");
            isbn.append(bookId);
        } else {
            isbn.append(bookId);
        }
        for (int i = 0; i < 10; i++) {
            isbn.append(random.nextInt(10));
        }
        return isbn.toString();
    }

}
