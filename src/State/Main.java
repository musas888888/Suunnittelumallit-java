package State;

public class Main {
    public static void main(String[] args) {
        // Normal printer
        Printer printer1 = new BasicPrinter();
        printer1.print("Hello World!");

        // Decorated: XML + Encrypted
        Printer printer2 = new EncryptedPrinter(new XMLPrinter(new BasicPrinter()));
        printer2.print("Hello World!");
    }
}

