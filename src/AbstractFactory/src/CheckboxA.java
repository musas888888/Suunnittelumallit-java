class CheckboxA extends Checkbox {
    public CheckboxA(String text) {
        super(text);
    }

    @Override
    public void display() {
        String mark = isChecked() ? "x" : " ";
        System.out.println("[ " + mark + " ] " + getText());
    }
}
