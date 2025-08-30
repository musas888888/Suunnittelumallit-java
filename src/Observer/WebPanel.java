package Observer;

// EN: Concrete observer for a web UI panel.
// AR: مراقب لواجهة ويب.
public class WebPanel implements WeatherObserver {
    @Override
    public void update(double temperature) {
        System.out.printf("Web Panel: Current = %.1f°C%n", temperature);
    }
}
