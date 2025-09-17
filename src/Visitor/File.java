package Visitor;

public class File implements FileSystemElement {
    private final String name;
    private final double sizeMb;  // size in megabytes

    public File(String name, double sizeMb) {
        this.name = name;
        this.sizeMb = sizeMb;
    }

    public double sizeMb() {
        return sizeMb;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public void accept(FileSystemVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return "File(" + name + ", " + sizeMb + " MB)";
    }
}

