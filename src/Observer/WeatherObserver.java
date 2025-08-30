package Observer;

// EN: Observer interface. Any listener must implement update().
// AR: واجهة المراقب. أي مستمع يجب أن يطبق الدالة update().
public interface WeatherObserver {
    void update(double temperature);
}
