package Library;


public class Book {

    String title;
    String author;
    String genre;
    String shelfCode;

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
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Book book = (Book) obj;

        if (!(title.equals(book.title))) return false;
        if (!(author.equals(book.author))) return false;
        if (!(genre.equals(book.genre))) return false;
        return shelfCode.equals(book.shelfCode);
    }
}
