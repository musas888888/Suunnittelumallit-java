package Singleton;

/**
 * Example usage of Logger (Singleton)
 * مثال استخدام اللوجر (سينجلتون)
 */
public class Main {
    public static void main(String[] args) {
        Logger logger = Logger.getInstance();   // EN/AR: احصل على النسخة الوحيدة

        logger.write("Simulation started");
        logger.write("Processing data...");

        logger.setFileName("new_log.txt");      // EN: switch file | AR: تغيير ملف السجل
        logger.write("Switched to new_log.txt");
        logger.write("Simulation finished");

        logger.close(); // EN: good practice to close | AR: من الجيد إغلاقه في النهاية
    }
}

