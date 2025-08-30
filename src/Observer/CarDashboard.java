package Observer;

// EN: Concrete observer for car dashboard.
// AR: مراقب لوحة السيارة.
public class CarDashboard implements WeatherObserver {
    @Override
    public void update(double temperature) {
        System.out.printf("Car Dashboard: Temp %.1f°C%n", temperature);
    }
}
