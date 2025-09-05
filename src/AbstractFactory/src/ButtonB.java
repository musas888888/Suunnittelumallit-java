package AbstractFactory.src;

class ButtonB extends Button {
    public ButtonB(String text) {
        super(text);
    }

    @Override
    public void display() {
        String label = "< " + getText() + " >";
        String top = "/" + "-".repeat(label.length()) + "\\";
        String bottom = "\\" + "-".repeat(label.length()) + "/";
        System.out.println(top);
        System.out.println("|" + label + "|");
        System.out.println(bottom);
    }
}
