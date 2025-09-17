package Visitor;

public class SizeCalculatorVisitor implements FileSystemVisitor {
    private double totalMb = 0.0;

    public double totalMb() {
        return totalMb;
    }

    public void reset() {
        totalMb = 0.0;
    }

    @Override
    public void visit(File file) {
        totalMb += file.sizeMb();
    }

    @Override
    public void visit(Directory directory) {
        // nothing to add directly; traversal happens in Directory.accept(...)
    }
}

