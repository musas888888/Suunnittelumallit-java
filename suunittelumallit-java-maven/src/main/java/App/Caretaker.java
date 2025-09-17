package App;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayDeque;
import java.util.Deque;

public class Caretaker {
    private final Deque<Memento> undo = new ArrayDeque<>();
    private final Deque<Memento> redo = new ArrayDeque<>();
    private final ObservableList<Memento> history = FXCollections.observableArrayList();

    public void pushUndo(Memento m) {
        undo.push(m);
        history.add(m);
    }

    public boolean canUndo() { return !undo.isEmpty(); }
    public boolean canRedo() { return !redo.isEmpty(); }

    public Memento popUndo() { return undo.pop(); }
    public void pushRedo(Memento m) { redo.push(m); }
    public Memento popRedo() { return redo.pop(); }
    public void clearRedo() { redo.clear(); }

    public ObservableList<Memento> history() { return history; }

    public void trimHistoryAfter(Memento chosen) {
        int idx = history.indexOf(chosen);
        if (idx >= 0 && idx < history.size()-1) {
            history.remove(idx+1, history.size());
        }
        undo.clear();
        for (int i = history.size()-1; i >= 0; i--) {
            undo.push(history.get(i));
        }
        redo.clear();
    }
}

