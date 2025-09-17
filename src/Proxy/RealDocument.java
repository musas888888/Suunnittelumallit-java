package Proxy;

public class RealDocument implements Document {
    private final String id;
    private final String creationDate;
    private final String content;

    public RealDocument(String id, String creationDate, String content) {
        this.id = id;
        this.creationDate = creationDate;
        this.content = content;
    }

    @Override
    public String getContent(User user) {
        // Real document itself does no access checks.
        return content;
    }

    @Override
    public String getCreationDate() {
        return creationDate;
    }

    @Override
    public String getId() {
        return id;
    }
}

