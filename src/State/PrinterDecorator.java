package State;

public abstract class PrinterDecorator implements Printer {
    protected Printer wrappedPrinter;

    public PrinterDecorator(Printer printer) {
        this.wrappedPrinter = printer;
    }
}

