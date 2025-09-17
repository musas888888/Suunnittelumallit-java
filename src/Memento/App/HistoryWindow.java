package Memento.App;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class HistoryWindow {
    private final Stage stage = new Stage();
    private final ListView<Memento> listView = new ListView<>();
    private Memento selected;

    public HistoryWindow(Stage owner, Caretaker caretaker) {
        stage.initOwner(owner);
        stage.initModality(Modality.NONE);
        stage.setTitle("History");

        listView.setItems(caretaker.history());
        listView.getSelectionModel().selectedItemProperty().addListener((obs, old, val) -> {
            selected = val;
        });

        Button close = new Button("Close");
        close.setOnAction(e -> stage.hide());

        BorderPane root = new BorderPane();
        root.setTop(new Label("Double-click a row to restore"));
        root.setCenter(listView);
        root.setBottom(new HBox(8, close));

        listView.setOnMouseClicked(ev -> {
            if (ev.getClickCount() == 2 && selected != null) {
                stage.hide();
            }
        });

        stage.setScene(new Scene(root, 560, 360));
    }

    public void show() { stage.show(); }
    public ListView<Memento> getListView() { return listView; }
    public Stage getStage() { return stage; }
}


