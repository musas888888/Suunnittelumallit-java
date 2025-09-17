package Visitor;

import java.util.ArrayList;
import java.util.List;

public class Directory implements FileSystemElement {
    private final String name;
    private final List<FileSystemElement> children = new ArrayList<>();

    public Directory(String name) {
        this.name = name;
    }

    public Directory add(FileSystemElement element) {
        children.add(element);
        return this;
    }

    public List<FileSystemElement> children() {
        return children;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public void accept(FileSystemVisitor visitor) {
        visitor.visit(this);
        // Important: let visitor traverse children (common in Visitor examples)
        for (FileSystemElement child : children) {
            child.accept(visitor);
        }
    }

    @Override
    public String toString() {
        return "Directory(" + name + ")";
    }
}

