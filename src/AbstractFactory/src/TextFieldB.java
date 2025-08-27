class TextFieldB extends TextField {
    public TextFieldB(String text) {
        super(text);
    }

    @Override
    public void display() {
        String label = "<< " + getText() + " >>";
        String border = "=".repeat(label.length() + 2);
        System.out.println(border);
        System.out.println(" " + label);
        System.out.println(border);
    }
}
