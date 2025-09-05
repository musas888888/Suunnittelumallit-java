package State;

public class EncryptedPrinter extends PrinterDecorator {
    public EncryptedPrinter(Printer printer) {
        super(printer);
    }

    @Override
    public void print(String message) {
        String encrypted = encrypt(message);
        wrappedPrinter.print(encrypted);
    }

    private String encrypt(String text) {
        StringBuilder sb = new StringBuilder();
        for (char c : text.toCharArray()) {
            sb.append((char) (c + 3)); // shift 3
        }
        return sb.toString();
    }
}

