package Observer;

// EN: Concrete observer showing phone message.
// AR: مراقب حقيقي يعرض رسالة هاتف.
public class PhoneDisplay implements WeatherObserver {
    private final String owner;
    public PhoneDisplay(String owner) {
        this.owner = owner;
    }

    @Override
    public void update(double temperature) {
        System.out.printf("Phone of %s: %.1f°C%n", owner, temperature);
    }
}
