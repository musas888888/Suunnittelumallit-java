package Decorator;

// EN: Basic printer that just prints to console.
// AR: طابعة أساسية تطبع الرسالة إلى الشاشة.
public class BasicPrinter implements Printer {
    @Override
    public void print(String message) {
        System.out.println(message);
    }
}

