package Memento.App;

import javafx.scene.paint.Color;

public class Model {
    private Color c1 = Color.CRIMSON;
    private Color c2 = Color.DODGERBLUE;
    private Color c3 = Color.MEDIUMSEAGREEN;
    private boolean checked = false;

    public Color getC1() { return c1; }
    public Color getC2() { return c2; }
    public Color getC3() { return c3; }
    public boolean isChecked() { return checked; }

    public void setC1(Color c) { this.c1 = c; }
    public void setC2(Color c) { this.c2 = c; }
    public void setC3(Color c) { this.c3 = c; }
    public void setChecked(boolean v) { this.checked = v; }

    public Memento saveToMemento() {
        return new Memento(c1, c2, c3, checked);
    }

    public void restoreFromMemento(Memento m) {
        this.c1 = m.c1();
        this.c2 = m.c2();
        this.c3 = m.c3();
        this.checked = m.checked();
    }
}
