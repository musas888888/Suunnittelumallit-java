package AbstractFactory.src;

class TextFieldA extends TextField {
    public TextFieldA(String text) {
        super(text);
    }

    @Override
    public void display() {
        String inner = " " + getText() + " ";
        String border = "+" + "-".repeat(inner.length()) + "+";
        System.out.println(border);
        System.out.println("|" + inner + "|");
        System.out.println(border);
    }
}
