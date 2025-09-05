package AbstractFactory.src;

abstract class Checkbox extends UIElement {
    private boolean checked = false;

    public Checkbox(String text) {
        super(text);
    }

    public void setChecked(boolean value) {
        this.checked = value;
    }
    public boolean isChecked() {
        return checked;
    }
}
