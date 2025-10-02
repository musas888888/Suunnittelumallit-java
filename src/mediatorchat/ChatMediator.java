package mediatorchat;

import java.util.List;

public interface ChatMediator {
    void register(String username, ChatClientController controller);
    void send(String from, String to, String message);
    List<String> getUsernames();
    void refreshUsers();
}

