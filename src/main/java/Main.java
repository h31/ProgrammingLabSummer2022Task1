public class Main {
    public static void main(String[] args) {
        Book f = new Book("ПП", "Egor", "gachi", "A3");
        Library l = new Library();
        l.addBook(f);
        f.setName("->");
        l.addBook(f);
    }
}