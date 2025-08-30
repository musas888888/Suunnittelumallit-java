package Observer;

// EN: Main: start station thread, add observers, remove one, keep running.
// AR: Main: تشغيل المحطة، إضافة مراقبين، إزالة واحد، والاستمرار.
public class Main {
    public static void main(String[] args) throws InterruptedException {
        // 1) create station with min/max
        WeatherStation station = new WeatherStation(-10.0, 40.0);

        // 2) run station in its own thread
        Thread stationThread = new Thread(station, "Weather-Station");
        stationThread.start();

        // 3) create observers and register them
        WeatherObserver obs1 = new PhoneDisplay("Musa");
        WeatherObserver obs2 = new CarDashboard();
        WeatherObserver obs3 = new WebPanel();

        station.addObserver(obs1);
        station.addObserver(obs2);
        station.addObserver(obs3);

        System.out.println("== Started with 3 observers ==\n");

        // 4) let it run ~12s
        Thread.sleep(12_000);

        // 5) remove one observer and keep running
        System.out.println("\n== Removing WebPanel observer ==\n");
        station.removeObserver(obs3);

        // 6) run more ~10s
        Thread.sleep(10_000);

        // 7) stop and exit
        System.out.println("\n== Stopping simulation ==\n");
        station.stop();
        stationThread.interrupt();
        stationThread.join();
    }
}
