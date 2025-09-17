package Proxy;


public interface Document {
    String getContent(User user);      // returns content if allowed (proxy checks)
    String getCreationDate();          // always returns date, no permission check
    String getId();                    // identifier (helps for logs)
}
