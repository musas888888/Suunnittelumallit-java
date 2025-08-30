package Decorator;

// EN: Base decorator: holds another Printer and forwards calls.
// AR: ديكوريتر أساسي: يحتفظ بطابعة أخرى ويمرر الاستدعاءات.
public abstract class PrinterDecorator implements Printer {
    protected final Printer inner;

    public PrinterDecorator(Printer inner) {
        this.inner = inner;
    }
}

