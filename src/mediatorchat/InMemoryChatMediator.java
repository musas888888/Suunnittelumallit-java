package mediatorchat;

import java.util.*;

public class InMemoryChatMediator implements ChatMediator {
    private final Map<String, ChatClientController> clients = new LinkedHashMap<>();

    @Override
    public void register(String username, ChatClientController controller) {
        clients.put(username, controller);
        refreshUsers();
    }

    @Override
    public void send(String from, String to, String message) {
        if (to == null || to.isBlank()) return;
        ChatClientController target = clients.get(to);
        if (target != null) target.receive(from, message);
        ChatClientController sender = clients.get(from);
        if (sender != null) sender.sent(to, message);
    }

    @Override
    public List<String> getUsernames() {
        return new ArrayList<>(clients.keySet());
    }

    @Override
    public void refreshUsers() {
        List<String> list = getUsernames();
        for (ChatClientController c : clients.values()) c.updateUsers(list);
    }
}

