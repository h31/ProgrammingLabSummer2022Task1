public class Main {
    public static void main(String[] args) {
        Book first = new Book("GG", "Egor", "gachi", "A3");
        Book second = new Book("Sasha Lukichev", "Egor", "gachi", "A3");
        Book third = new Book("GG", "Egor", "gachi", "A4");
        Library lib = new Library(first, second);
        lib.addBook(third);
        lib.allBooks();
    }
}