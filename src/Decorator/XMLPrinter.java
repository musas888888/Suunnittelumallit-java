package Decorator;

// EN: Decorator that wraps message in <message>...</message> then prints.
// AR: ديكوريتر يلفّ الرسالة داخل وسم XML ثم يطبعها.
public class XMLPrinter extends PrinterDecorator {

    public XMLPrinter(Printer inner) {
        super(inner);
    }

    @Override
    public void print(String message) {
        String xml = "<message>" + message + "</message>";
        inner.print(xml); // pass to the next printer (may be another decorator or BasicPrinter)
    }
}

