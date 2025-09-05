package AbstractFactory.src;

public class Main {
    public static void main(String[] args) {
        // Choose style: default A, or pass B as arg
        String style = (args.length > 0) ? args[0].trim().toUpperCase() : "A";

        UIFactory factory = style.equals("B") ? new BFactory() : new AFactory();
        System.out.println("Using STYLE " + (style.equals("B") ? "B" : "A") + "\n");

        Button btn = factory.createButton("Submit");
        TextField tf = factory.createTextField("Username");
        Checkbox cb = factory.createCheckbox("Remember me");

        // show
        btn.display();
        tf.display();
        cb.display();

        System.out.println("\n-- setText() dynamic change --\n");

        // change text content dynamically (shared in UIElement)
        btn.setText("OK");
        tf.setText("Enter name...");
        cb.setText("I agree to terms");
        cb.setChecked(true); // show check state

        // show again
        btn.display();
        tf.display();
        cb.display();
    }
}