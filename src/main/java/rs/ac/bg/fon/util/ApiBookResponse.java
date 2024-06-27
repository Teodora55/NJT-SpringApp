package rs.ac.bg.fon.util;

import java.util.List;
import java.util.Map;
import lombok.Data;

@Data
public class ApiBookResponse {

    private int count;
    private String next;
    private String previous;
    private List<ApiBook> results;

    @Data
    public static class ApiBook {

        private int id;
        private String title;
        private List<Author> authors;
        private List<Author> translators;
        private List<String> subjects;
        private List<String> bookshelves;
        private List<String> languages;
        private boolean copyright;
        private String media_type;
        private Map<String,String> formats;
        private int download_count;

        @Data
        public static class Author {

            private String name;
            private int birth_year;
            private int death_year;
        }
    }

}
