package mediatorchat;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.List;
import java.util.stream.Collectors;

public class ChatClientController {
    private final String username;
    private final ChatMediator mediator;
    private final TextArea log = new TextArea();
    private final TextField input = new TextField();
    private final ComboBox<String> recipient = new ComboBox<>();
    private final Button sendBtn = new Button("Send");
    private final Stage stage = new Stage();

    public ChatClientController(String username, ChatMediator mediator) {
        this.username = username;
        this.mediator = mediator;
        buildUI();
    }

    private void buildUI() {
        log.setEditable(false);
        HBox bottom = new HBox(8, new Label("To:"), recipient, input, sendBtn);
        bottom.setPadding(new Insets(8));
        BorderPane root = new BorderPane();
        root.setCenter(log);
        root.setBottom(bottom);
        Scene scene = new Scene(root, 520, 360);
        stage.setTitle(username);
        stage.setScene(scene);
        sendBtn.setOnAction(e -> {
            String to = recipient.getValue();
            String msg = input.getText().trim();
            if (!msg.isEmpty() && to != null) {
                mediator.send(username, to, msg);
                input.clear();
            }
        });
        input.setOnAction(e -> sendBtn.fire());
        stage.show();
    }

    public void updateUsers(List<String> users) {
        List<String> others = users.stream().filter(u -> !u.equals(username)).collect(Collectors.toList());
        String prev = recipient.getValue();
        recipient.setItems(FXCollections.observableArrayList(others));
        if (prev != null && others.contains(prev)) recipient.setValue(prev);
        else if (!others.isEmpty()) recipient.getSelectionModel().selectFirst();
    }

    public void receive(String from, String message) {
        log.appendText(from + " -> " + username + ": " + message + "\n");
    }

    public void sent(String to, String message) {
        log.appendText(username + " -> " + to + ": " + message + "\n");
    }
}

