package State;

public class XMLPrinter extends PrinterDecorator {
    public XMLPrinter(Printer printer) {
        super(printer);
    }

    @Override
    public void print(String message) {
        String xmlMessage = "<message>" + message + "</message>";
        wrappedPrinter.print(xmlMessage);
    }
}

