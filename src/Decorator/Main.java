package Decorator;

// EN: Demo usage matching the assignment.
// AR: مثال تشغيل مطابق لمتطلبات المهمة.
public class Main {
    public static void main(String[] args) {
        // --- Basic printer ---
        Printer printer = new BasicPrinter();
        printer.print("Hello World!");

        // --- Decorated: Encrypt then XML then print ---
        // EN: Outer EncryptedPrinter -> encrypts first,
        //     then XMLPrinter wraps it, then BasicPrinter prints.
        // AR: الترتيب مهم: التشفير أولاً ثم التغليف بـ XML ثم الطباعة.
        Printer printer2 = new EncryptedPrinter(new XMLPrinter(new BasicPrinter()));
        printer2.print("Hello World!");

        // Extra (optional): show decrypt works (just to console)
        String encrypted = EncryptedPrinter.encryptCaesar("Hello World!", 3);
        System.out.println("Encrypted only = " + encrypted);
        System.out.println("Decrypted back = " + EncryptedPrinter.decryptCaesar(encrypted, 3));
    }
}

