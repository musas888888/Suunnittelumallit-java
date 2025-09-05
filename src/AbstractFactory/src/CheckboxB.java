package AbstractFactory.src;

class CheckboxB extends Checkbox {
    public CheckboxB(String text) {
        super(text);
    }

    @Override
    public void display() {
        String mark = isChecked() ? "X" : " ";
        System.out.println("( " + mark + " ) " + getText());
    }
}
