package Proxy;


public class DocumentProxy implements Document {
    private final String id;                       // id of the real document
    private final Library library;                 // to fetch the real document
    private final AccessControlService acs;        // singleton

    public DocumentProxy(String id, Library library) {
        this.id = id;
        this.library = library;
        this.acs = AccessControlService.getInstance();
    }

    @Override
    public String getContent(User user) {
        if (acs.isAllowed(id, user.username())) {
            Document real = library.getRealDocument(id);
            return real.getContent(user);
        }
        throw new AccessDeniedException("User '" + user.username()
                + "' is not allowed to access document '" + id + "'");
    }

    @Override
    public String getCreationDate() {
        // Creation date is public information
        return library.getRealDocument(id).getCreationDate();
    }

    @Override
    public String getId() {
        return id;
    }
}

