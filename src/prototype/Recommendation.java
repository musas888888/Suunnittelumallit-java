package prototype;

import java.util.ArrayList;
import java.util.List;

public class Recommendation implements Cloneable {
    private String targetAudience;
    private List<Book> books = new ArrayList<>();

    public Recommendation(String targetAudience) {
        this.targetAudience = targetAudience;
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public boolean removeBook(int index) {
        if (index < 0 || index >= books.size()) return false;
        books.remove(index);
        return true;
    }

    public void setTargetAudience(String targetAudience) {
        this.targetAudience = targetAudience;
    }

    public String getTargetAudience() {
        return targetAudience;
    }

    public List<Book> getBooks() {
        return books;
    }

    @Override
    public Recommendation clone() {
        Recommendation copy = new Recommendation(this.targetAudience);
        for (Book b : this.books) copy.addBook(b.clone());
        return copy;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Audience: " + targetAudience + "\n");
        for (int i = 0; i < books.size(); i++) sb.append("  ").append(i).append(") ").append(books.get(i)).append("\n");
        return sb.toString();
    }
}

