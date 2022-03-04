import java.lang.String;
import java.util.Objects;


public class Book {
    private String name;
    private String author;
    private String genre;
    private String number;


    public Book(String name, String author, String genre, String number) {
        this.name = name;
        this.author = author;
        this.genre = genre;
        this.number = number;
    }

    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append(name + " ").append(author + " ").append(genre + " ").append(number);
        return str.toString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(name, book.name) && Objects.equals(author, book.author) && Objects.equals(genre, book.genre) && Objects.equals(number, book.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, author, genre, number);
    }
}