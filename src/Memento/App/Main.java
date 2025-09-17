package Memento.App;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) {
        Ui ui = new Ui();
        Scene scene = new Scene(ui.getRoot(), 800, 420);
        stage.setTitle("Memento – Undo / Redo + History");
        stage.setScene(scene);
        stage.show();

        ui.afterStageShown(stage); // rekisteröi näppäinyhdistelmät
    }

    public static void main(String[] args) {
        launch(args);
    }
}

