package Library;

public class Book {

    String title;
    String author;
    String genre;
    String shelfCode;

    public Book(String title, String author, String genre, String shelfCode){
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.shelfCode = shelfCode;
        if (title.isBlank() || author.isBlank() || genre.isBlank() || shelfCode.isBlank())
            throw new IllegalArgumentException();
        if (!shelfCode.matches("[А-Я][1-9]"))
            throw new IllegalArgumentException();
    }
}
