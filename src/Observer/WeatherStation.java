package Observer;

import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

// EN: Subject/Observable. Runs in its own thread and notifies observers.
// AR: الكائن المُراقَب. يعمل في ثريد مستقل ويُبلغ المراقبين.
public class WeatherStation implements Runnable {

    private final List<WeatherObserver> observers = new CopyOnWriteArrayList<>();
    private final Random random = new Random();

    private final double MIN_TEMP;   // EN: lower limit  | AR: الحد الأدنى
    private final double MAX_TEMP;   // EN: upper limit  | AR: الحد الأعلى
    private volatile double temperature; // EN/AR: الحرارة الحالية
    private volatile boolean running = true; // EN/AR: لتوقيف الحلقة بأمان

    // EN: constructor sets initial random temperature within limits.
    // AR: المُنشئ يحدد حرارة ابتدائية عشوائية ضمن الحدود.
    public WeatherStation(double minTemp, double maxTemp) {
        this.MIN_TEMP = minTemp;
        this.MAX_TEMP = maxTemp;
        this.temperature = minTemp + (maxTemp - minTemp) * random.nextDouble();
    }

    // EN: register/remove observer | AR: إضافة/إزالة مراقب
    public void addObserver(WeatherObserver observer) { observers.add(observer); }
    public void removeObserver(WeatherObserver observer) { observers.remove(observer); }

    // EN: notify all observers | AR: إشعار جميع المراقبين
    private void notifyObservers() {
        for (WeatherObserver o : observers) o.update(temperature);
    }

    public void stop() { running = false; } // EN/AR: إيقاف المحطة

    public double getTemperature() { return temperature; }

    @Override
    public void run() {
        while (running) {
            try {
                // EN: sleep 1–5 seconds | AR: نوم من 1 إلى 5 ثوانٍ
                Thread.sleep(1000 + random.nextInt(4000));

                // EN: change temp by ±1 degree within bounds
                // AR: تعديل الحرارة بمقدار ±1 درجة مع احترام الحدود
                double delta = random.nextBoolean() ? +1.0 : -1.0;
                double next = temperature + delta;
                if (next < MIN_TEMP) next = MIN_TEMP;
                if (next > MAX_TEMP) next = MAX_TEMP;
                temperature = next;

                // EN/AR: notify observers after each update
                notifyObservers();

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}
