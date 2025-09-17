package Proxy;


import java.util.*;

public final class AccessControlService {
    private static final AccessControlService INSTANCE = new AccessControlService();

    // Map: documentId -> allowed usernames
    private final Map<String, Set<String>> allowList = new HashMap<>();

    private AccessControlService() {}

    public static AccessControlService getInstance() {
        return INSTANCE;
    }

    public boolean isAllowed(String documentId, String username) {
        Set<String> users = allowList.get(documentId);
        return users != null && users.contains(username);
    }

    public void setAllowedUsers(String documentId, Collection<String> usernames) {
        allowList.put(documentId, new HashSet<>(usernames));
    }
}

