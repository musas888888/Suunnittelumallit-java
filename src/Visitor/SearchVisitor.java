package Visitor;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.function.Predicate;

public class SearchVisitor implements FileSystemVisitor {
    private final Predicate<FileSystemElement> rule;
    private final List<FileSystemElement> matches = new ArrayList<>();

    // Examples:
    // name ends with ".txt"
    // name contains "report"
    public static SearchVisitor byNameEndsWith(String suffix) {
        return new SearchVisitor(e -> e.name().toLowerCase(Locale.ROOT)
                .endsWith(suffix.toLowerCase(Locale.ROOT)));
    }
    public static SearchVisitor byNameContains(String part) {
        return new SearchVisitor(e -> e.name().toLowerCase(Locale.ROOT)
                .contains(part.toLowerCase(Locale.ROOT)));
    }

    public SearchVisitor(Predicate<FileSystemElement> rule) {
        this.rule = rule;
    }

    public List<FileSystemElement> matches() {
        return matches;
    }

    public void reset() {
        matches.clear();
    }

    @Override
    public void visit(File file) {
        if (rule.test(file)) matches.add(file);
    }

    @Override
    public void visit(Directory directory) {
        if (rule.test(directory)) matches.add(directory);
    }
}

