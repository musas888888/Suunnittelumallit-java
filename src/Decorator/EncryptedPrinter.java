package Decorator;

// EN: Decorator that encrypts the message with a reversible Caesar cipher, then prints.
// AR: ديكوريتر يشفّر الرسالة بخوارزمية قيصر (قابلة لفك التشفير) ثم يطبع.
public class EncryptedPrinter extends PrinterDecorator {
    private final int shift; // EN/AR: مقدار الإزاحة في قيصر (مفتاح التشفير)

    public EncryptedPrinter(Printer inner) {
        this(inner, 3); // default shift 3 / افتراضي 3
    }

    public EncryptedPrinter(Printer inner, int shift) {
        super(inner);
        // normalize shift to [0..25]  // تطبيع قيمة المفتاح
        int s = shift % 26;
        if (s < 0) s += 26;
        this.shift = s;
    }

    @Override
    public void print(String message) {
        String enc = encryptCaesar(message, shift);
        inner.print(enc);
    }

    // EN: Simple Caesar cipher over A-Z / a-z; other chars stay as-is.
    // AR: تشفير قيصر بسيط للأحرف فقط؛ باقي الرموز تبقى كما هي.
    public static String encryptCaesar(String text, int shift) {
        StringBuilder sb = new StringBuilder(text.length());
        for (char c : text.toCharArray()) {
            if (c >= 'A' && c <= 'Z') {
                sb.append((char) ('A' + (c - 'A' + shift) % 26));
            } else if (c >= 'a' && c <= 'z') {
                sb.append((char) ('a' + (c - 'a' + shift) % 26));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    // EN: Decrypt helper (not required by assignment, but proves reversibility).
    // AR: دالة لفك التشفير (اختيارية لإثبات إمكانية الاسترجاع).
    public static String decryptCaesar(String text, int shift) {
        return encryptCaesar(text, 26 - (shift % 26));
    }
}

