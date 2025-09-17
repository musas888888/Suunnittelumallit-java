package Proxy;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        Library lib = new Library();

        // Create users
        User alice = new User("alice");
        User bob   = new User("bob");
        User eve   = new User("eve");

        // Add documents
        lib.addUnprotected("readme", "2025-09-01", "Welcome! This is public.");
        lib.addProtected("payroll-2025", "2025-09-20",
                "Salaries: [SECRET NUMBERS]",
                List.of("alice", "bob")); // allowed users

        // --- Demo scenarios ---
        read(lib, "readme", eve);           // public, should work
        read(lib, "payroll-2025", alice);   // protected, allowed
        read(lib, "payroll-2025", eve);     // protected, not allowed
    }

    private static void read(Library lib, String docId, User user) {
        System.out.println("\nUser '" + user.username() + "' tries to open '" + docId + "'");
        Document d = lib.get(docId);
        System.out.println("Creation date: " + d.getCreationDate());
        try {
            String content = d.getContent(user);
            System.out.println("CONTENT: " + content);
        } catch (AccessDeniedException ex) {
            System.out.println("ACCESS DENIED: " + ex.getMessage());
        }
    }
}

