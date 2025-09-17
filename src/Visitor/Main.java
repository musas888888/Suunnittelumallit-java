package Visitor;

public class Main {
    public static void main(String[] args) {
        // Build a small tree:
        Directory root = new Directory("root")
                .add(new File("readme.txt", 0.5))
                .add(new File("photo.jpg", 2.3))
                .add(new Directory("docs")
                        .add(new File("report.docx", 1.2))
                        .add(new File("notes.txt", 0.3)))
                .add(new Directory("src")
                        .add(new File("Main.java", 0.02))
                        .add(new File("Utils.java", 0.01)));

        // 1) Size calculator
        SizeCalculatorVisitor sizeV = new SizeCalculatorVisitor();
        root.accept(sizeV);
        System.out.println("Total size: " + sizeV.totalMb() + " MB");

        // 2) Search by extension
        SearchVisitor txtSearch = SearchVisitor.byNameEndsWith(".txt");
        root.accept(txtSearch);
        System.out.println("*.txt matches:");
        txtSearch.matches().forEach(e -> System.out.println(" - " + e));

        // 3) Search by name part
        SearchVisitor javaSearch = SearchVisitor.byNameContains("java");
        root.accept(javaSearch);
        System.out.println("'java' matches:");
        javaSearch.matches().forEach(e -> System.out.println(" - " + e));
    }
}

