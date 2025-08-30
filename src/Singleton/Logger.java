package Singleton;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

/**
 * Singleton Logger
 * EN: One shared instance that writes log lines to a file.
 * AR: كائن واحد فقط يكتب رسائل السجل إلى ملف.
 */
public class Logger {

    // --- Singleton state / حالة السينجلتون ---
    private static Logger instance;           // EN: the single instance | AR: النسخة الوحيدة
    private BufferedWriter writer;            // EN: current file writer | AR: كاتب الملف الحالي
    private String fileName;                  // EN/AR: اسم ملف السجل الحالي

    // EN: Private constructor: initialize with default file name.
    // AR: مُنشئ خاص: يبدأ باسم ملف افتراضي.
    private Logger() {
        setFileNameInternal("app.log");
        openWriterSafely();
    }

    // EN: Global access point.
    // AR: نقطة الوصول العامة للنسخة الوحيدة.
    public static synchronized Logger getInstance() {
        if (instance == null) instance = new Logger();
        return instance;
    }

    // EN: Write one line to the log file (each call = new line).
    // AR: يكتب سطرًا واحدًا في الملف (كل استدعاء = سطر جديد).
    public synchronized void write(String message) {
        try {
            ensureWriterOpen();
            writer.write(message);
            writer.newLine();
            writer.flush(); // EN: make sure it lands on disk | AR: للتأكد من الحفظ
        } catch (IOException e) {
            System.err.println("Logger write failed: " + e.getMessage());
        }
    }

    // EN: Change file name at runtime (close current file then open new one).
    // AR: تغيير اسم الملف أثناء التشغيل (إغلاق الحالي ثم فتح الجديد).
    public synchronized void setFileName(String newFileName) {
        if (newFileName == null || newFileName.isBlank()) return;
        if (newFileName.equals(this.fileName)) return;
        closeInternal();
        setFileNameInternal(newFileName);
        openWriterSafely();
    }

    // EN: Close resources (call when the app ends).
    // AR: إغلاق الموارد (يُستحسن عند انتهاء البرنامج).
    public synchronized void close() {
        closeInternal();
    }

    // ===== Helpers / دوال مساعدة =====
    private void setFileNameInternal(String name) { this.fileName = name; }

    private void ensureWriterOpen() throws IOException {
        if (writer == null) openWriter();
    }

    private void openWriterSafely() {
        try { openWriter(); }
        catch (IOException e) {
            System.err.println("Logger open failed: " + e.getMessage());
        }
    }

    private void openWriter() throws IOException {
        Path path = Path.of(fileName);
        writer = Files.newBufferedWriter(
                path,
                StandardCharsets.UTF_8,
                StandardOpenOption.CREATE,   // create if not exists / أنشئ إذا غير موجود
                StandardOpenOption.APPEND    // append / إضافة في نهاية الملف
        );
    }

    private void closeInternal() {
        if (writer != null) {
            try { writer.flush(); writer.close(); }
            catch (IOException e) {
                System.err.println("Logger close failed: " + e.getMessage());
            } finally {
                writer = null;
            }
        }
    }
}

