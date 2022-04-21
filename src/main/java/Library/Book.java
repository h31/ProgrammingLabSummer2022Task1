package Library;


import java.util.Objects;

public class Book {

    private String title;
    private String author;
    private String genre;
    private String shelfCode;

    public Book(String title, String author, String genre, String code){
        if (title.isBlank() || author.isBlank() || genre.isBlank() || code.isBlank())
            throw new IllegalArgumentException();
        if (!code.matches("[А-Я][1-9]"))
            throw new IllegalArgumentException();
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.shelfCode = code;
    }

    @Override
    public String toString() {
        return title + ", " + author + ", " + genre + ", " + shelfCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(title, book.title) && Objects.equals(author, book.author)
                && Objects.equals(genre, book.genre) && Objects.equals(shelfCode, book.shelfCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, author, genre, shelfCode);
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getGenre() {
        return genre;
    }

    public String getShelfCode() {
        return shelfCode;
    }
}
