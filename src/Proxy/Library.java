package Proxy;

import java.util.*;

public class Library {
    // All real documents live here (not directly accessible by users)
    private final Map<String, Document> realDocs = new HashMap<>();
    // What the outside world sees (can be real or proxy)
    private final Map<String, Document> visible = new HashMap<>();

    public void addUnprotected(String id, String creationDate, String content) {
        Document real = new RealDocument(id, creationDate, content);
        realDocs.put(id, real);
        visible.put(id, real); // unprotected: expose the real doc
    }

    public void addProtected(String id,
                             String creationDate,
                             String content,
                             Collection<String> allowedUsers) {
        Document real = new RealDocument(id, creationDate, content);
        realDocs.put(id, real);

        // Configure who is allowed
        AccessControlService.getInstance().setAllowedUsers(id, allowedUsers);

        // Expose only the proxy
        Document proxy = new DocumentProxy(id, this);
        visible.put(id, proxy);
    }

    public Document get(String id) {
        return visible.get(id); // may be RealDocument or DocumentProxy
    }

    // Used internally by proxies
    Document getRealDocument(String id) {
        Document doc = realDocs.get(id);
        if (doc == null) throw new IllegalArgumentException("No document with id " + id);
        return doc;
    }
}

