package Memento.App;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;



public class Ui {
    private final Model model = new Model();
    private final Caretaker caretaker = new Caretaker();

    private final Rectangle r1 = rect(model.getC1());
    private final Rectangle r2 = rect(model.getC2());
    private final Rectangle r3 = rect(model.getC3());
    private final CheckBox cb = new CheckBox("Checked");

    private final BorderPane root = new BorderPane();
    private HistoryWindow historyWindow;

    public Ui() {
        caretaker.pushUndo(model.saveToMemento());

        Button undoBtn = new Button("Undo (Ctrl+Z)");
        Button redoBtn = new Button("Redo (Ctrl+Y)");
        Button historyBtn = new Button("Open History");

        undoBtn.setOnAction(e -> undo());
        redoBtn.setOnAction(e -> redo());
        historyBtn.setOnAction(e -> { if (historyWindow != null) historyWindow.show(); });

        HBox top = new HBox(8, undoBtn, redoBtn, historyBtn);
        top.setPadding(new Insets(10));

        HBox colors = new HBox(16, tile("Color 1", r1), tile("Color 2", r2), tile("Color 3", r3));
        colors.setPadding(new Insets(10));

        cb.setSelected(model.isChecked());
        cb.selectedProperty().addListener((obs, old, val) -> {
            model.setChecked(val);
            onUserChange();
            updateRects();
        });

        VBox center = new VBox(16, colors, cb);
        center.setPadding(new Insets(16));

        root.setTop(top);
        root.setCenter(center);

        historyWindow = new HistoryWindow(new Stage(), caretaker);
        historyWindow.getListView().setOnMouseClicked(ev -> {
            if (ev.getClickCount() == 2) {
                Memento m = historyWindow.getListView().getSelectionModel().getSelectedItem();
                if (m != null) restoreFromHistory(m);
            }
        });

        registerColorPicker(r1, c -> { model.setC1(c); onUserChange(); updateRects(); });
        registerColorPicker(r2, c -> { model.setC2(c); onUserChange(); updateRects(); });
        registerColorPicker(r3, c -> { model.setC3(c); onUserChange(); updateRects(); });

        updateRects();
    }

    public Pane getRoot() { return root; }

    public void afterStageShown(Stage owner) {
        historyWindow.getStage().initOwner(owner);

        var undoComb = new KeyCodeCombination(KeyCode.Z, KeyCombination.CONTROL_DOWN);
        var redoComb1 = new KeyCodeCombination(KeyCode.Y, KeyCombination.CONTROL_DOWN);
        var redoComb2 = new KeyCodeCombination(KeyCode.Z, KeyCombination.CONTROL_DOWN, KeyCombination.SHIFT_DOWN);

        owner.getScene().getAccelerators().put(undoComb, this::undo);
        owner.getScene().getAccelerators().put(redoComb1, this::redo);
        owner.getScene().getAccelerators().put(redoComb2, this::redo);
    }

    private void undo() {
        if (!caretaker.canUndo()) return;
        caretaker.pushRedo(model.saveToMemento());
        Memento prev = caretaker.popUndo();
        model.restoreFromMemento(prev);
        updateRects();
    }

    private void redo() {
        if (!caretaker.canRedo()) return;
        caretaker.pushUndo(model.saveToMemento());
        Memento next = caretaker.popRedo();
        model.restoreFromMemento(next);
        updateRects();
        if (!caretaker.history().contains(next)) {
            caretaker.history().add(next);
        }
    }

    private void onUserChange() {
        caretaker.pushUndo(model.saveToMemento());
        caretaker.clearRedo();
    }

    private void restoreFromHistory(Memento chosen) {
        model.restoreFromMemento(chosen);
        updateRects();
        caretaker.trimHistoryAfter(chosen);
    }

    private static Rectangle rect(Color c) {
        Rectangle r = new Rectangle(180, 120, c);
        r.setArcWidth(16);
        r.setArcHeight(16);
        r.setStroke(Color.BLACK);
        return r;
    }

    private VBox tile(String title, Rectangle r) {
        Label lbl = new Label(title);
        VBox box = new VBox(8, lbl, r);
        box.setPadding(new Insets(8));
        box.setBorder(new Border(new BorderStroke(Color.LIGHTGRAY,
                BorderStrokeStyle.SOLID, new CornerRadii(10), BorderWidths.DEFAULT)));
        return box;
    }

    private void updateRects() {
        r1.setFill(model.getC1());
        r2.setFill(model.getC2());
        r3.setFill(model.getC3());
        cb.setSelected(model.isChecked());
    }

    private void registerColorPicker(Rectangle rect, java.util.function.Consumer<Color> onPick) {
        rect.setOnMouseClicked(e -> {
            Color initial = (Color) rect.getFill();
            ColorPicker picker = new ColorPicker(initial);
            Dialog<Color> dialog = new Dialog<>();
            dialog.setTitle("Pick color");
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
            dialog.getDialogPane().setContent(picker);
            dialog.setResultConverter(bt -> bt == ButtonType.OK ? picker.getValue() : null);
            dialog.showAndWait().ifPresent(col -> onPick.accept(col));
        });
    }
}


