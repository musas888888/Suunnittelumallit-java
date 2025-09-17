package Visitor;

public interface FileSystemElement {
    String name();
    void accept(FileSystemVisitor visitor); // key of Visitor pattern
}

