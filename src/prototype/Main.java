package prototype;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Main {
    private static final Scanner in = new Scanner(System.in);
    private static final List<Recommendation> recommendations = new ArrayList<>();

    public static void main(String[] args) {
        seed();
        while (true) {
            menu();
            String c = in.nextLine().trim();
            switch (c) {
                case "1" -> listAll();
                case "2" -> viewOne();
                case "3" -> cloneOne();
                case "4" -> createNew();
                case "5" -> modify();
                case "6" -> saveAll();
                case "0" -> { System.out.println("Bye"); return; }
                default -> System.out.println("Virheellinen valinta");
            }
        }
    }

    private static void menu() {
        System.out.println("\n=== Prototype: Book Recommendations ===");
        System.out.println("1) Näytä kaikki");
        System.out.println("2) Näytä yksi");
        System.out.println("3) Kloonaa");
        System.out.println("4) Luo uusi");
        System.out.println("5) Muokkaa");
        System.out.println("6) Tallenna tiedostoon");
        System.out.println("0) Poistu");
        System.out.print("Valinta: ");
    }

    private static void listAll() {
        if (recommendations.isEmpty()) { System.out.println("Ei suosituksia"); return; }
        for (int i = 0; i < recommendations.size(); i++) {
            System.out.printf("%d) %s (kirjoja: %d)%n", i, recommendations.get(i).getTargetAudience(), recommendations.get(i).getBooks().size());
        }
    }

    private static void viewOne() {
        int i = askIndex("Indeksi: ");
        if (!valid(i)) return;
        System.out.println(recommendations.get(i));
    }

    private static void cloneOne() {
        int i = askIndex("Kloonattava indeksi: ");
        if (!valid(i)) return;
        Recommendation copy = recommendations.get(i).clone();
        System.out.print("Uusi kohderyhmä (tyhjä = sama): ");
        String s = in.nextLine().trim();
        if (!s.isEmpty()) copy.setTargetAudience(s);
        recommendations.add(copy);
        System.out.println("Kloonattu.");
    }

    private static void createNew() {
        System.out.print("Kohderyhmä: ");
        String a = in.nextLine().trim();
        Recommendation r = new Recommendation(a);
        while (true) {
            System.out.print("Lisätäänkö kirja? (y/n): ");
            String ans = in.nextLine().trim().toLowerCase(Locale.ROOT);
            if (!ans.equals("y")) break;
            r.addBook(promptBook());
        }
        recommendations.add(r);
        System.out.println("Luotu.");
    }

    private static void modify() {
        int i = askIndex("Muokattava indeksi: ");
        if (!valid(i)) return;
        Recommendation r = recommendations.get(i);
        while (true) {
            System.out.println("\nMuokkaus – " + r.getTargetAudience());
            System.out.println("a) Vaihda kohderyhmä");
            System.out.println("b) Lisää kirja");
            System.out.println("c) Poista kirja");
            System.out.println("d) Näytä");
            System.out.println("x) Takaisin");
            System.out.print("Valinta: ");
            String c = in.nextLine().trim().toLowerCase(Locale.ROOT);
            switch (c) {
                case "a" -> { System.out.print("Uusi kohderyhmä: "); r.setTargetAudience(in.nextLine().trim()); System.out.println("OK"); }
                case "b" -> { r.addBook(promptBook()); System.out.println("OK"); }
                case "c" -> {
                    System.out.print("Kirjan indeksi: ");
                    try {
                        int bi = Integer.parseInt(in.nextLine().trim());
                        System.out.println(r.removeBook(bi) ? "OK" : "Virheellinen indeksi");
                    } catch (NumberFormatException e) { System.out.println("Anna numero"); }
                }
                case "d" -> System.out.println(r);
                case "x" -> { return; }
                default -> System.out.println("Virheellinen valinta");
            }
        }
    }

    private static void saveAll() {
        try (FileWriter fw = new FileWriter("recommendations.txt")) {
            for (int i = 0; i < recommendations.size(); i++) {
                fw.write("### Recommendation " + i + " ###\n");
                fw.write(recommendations.get(i).toString());
                fw.write("\n");
            }
            System.out.println("Tallennettu: recommendations.txt");
        } catch (IOException e) {
            System.out.println("Virhe: " + e.getMessage());
        }
    }

    private static Book promptBook() {
        System.out.print("Kirjailija: ");
        String author = in.nextLine().trim();
        System.out.print("Otsikko: ");
        String title = in.nextLine().trim();
        System.out.print("Genre: ");
        String genre = in.nextLine().trim();
        int year = readInt("Julkaisuvuosi: ");
        return new Book(author, title, genre, year);
    }

    private static int askIndex(String p) {
        System.out.print(p);
        try { return Integer.parseInt(in.nextLine().trim()); }
        catch (NumberFormatException e) { System.out.println("Anna numero"); return -1; }
    }

    private static boolean valid(int i) { return i >= 0 && i < recommendations.size(); }

    private static int readInt(String p) {
        while (true) {
            System.out.print(p);
            try { return Integer.parseInt(in.nextLine().trim()); }
            catch (NumberFormatException e) { System.out.println("Anna numero"); }
        }
    }

    private static void seed() {
        Recommendation r1 = new Recommendation("Teinit – scifi");
        r1.addBook(new Book("Suzanne Collins", "The Hunger Games", "Sci-Fi", 2008));
        r1.addBook(new Book("James Dashner", "The Maze Runner", "Sci-Fi", 2009));
        Recommendation r2 = new Recommendation("Business-johtajat");
        r2.addBook(new Book("Jim Collins", "Good to Great", "Business", 2001));
        r2.addBook(new Book("Eric Ries", "The Lean Startup", "Business", 2011));
        Recommendation r3 = new Recommendation("Fantasia-fanit");
        r3.addBook(new Book("J.R.R. Tolkien", "The Hobbit", "Fantasy", 1937));
        r3.addBook(new Book("Patrick Rothfuss", "The Name of the Wind", "Fantasy", 2007));
        recommendations.add(r1);
        recommendations.add(r2);
        recommendations.add(r3);
    }
}

