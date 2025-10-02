package mediatorchat;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        ChatMediator mediator = new InMemoryChatMediator();
        ChatClientController a = new ChatClientController("Alice", mediator);
        ChatClientController b = new ChatClientController("Bob", mediator);
        ChatClientController c = new ChatClientController("Carol", mediator);
        mediator.register("Alice", a);
        mediator.register("Bob", b);
        mediator.register("Carol", c);
    }

    public static void main(String[] args) {
        launch(args);
    }
}

