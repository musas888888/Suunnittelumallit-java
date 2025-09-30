package Bridge;



interface Device {            // "Implementaatio": mitä laite osaa
    boolean isOn();
    void powerOn();
    void powerOff();
    int getVolume();
    void setVolume(int v);
    String getName();
}

class TV implements Device {   // Peruslaite
    private boolean on = false;
    private int volume = 20;

    public boolean isOn() { return on; }
    public void powerOn()  { on = true;  System.out.println(getName()+": ON"); }
    public void powerOff() { on = false; System.out.println(getName()+": OFF"); }
    public int getVolume() { return volume; }
    public void setVolume(int v) {
        volume = Math.max(0, Math.min(100, v));
        System.out.println(getName()+": volume=" + volume);
    }
    public String getName() { return "TV"; }
}

class SmartTV extends TV {     // Uusi laite: SmartTV – pieni lisäominaisuus
    public void browseInternet(String url) {
        if (!isOn()) {
            System.out.println("SmartTV: laite on OFF. Laita päälle ensin.");
            return;
        }
        System.out.println("SmartTV: selaa " + url);
    }
    @Override public String getName() { return "SmartTV"; }
}

class Remote {                 // "Abstraktio": kaukosäädin joka ohjaa mitä tahansa Devicea
    protected final Device device;
    public Remote(Device d) { this.device = d; }

    public void togglePower() {
        if (device.isOn()) device.powerOff(); else device.powerOn();
    }
    public void volumeUp()   { device.setVolume(device.getVolume() + 10); }
    public void volumeDown() { device.setVolume(device.getVolume() - 10); }
}

class SmartRemote extends Remote { // Uusi kaukosäädin: yksinkertainen voiceControl
    public SmartRemote(Device d) { super(d); }

    // Komennot: "power on", "power off", "vol 50", "browse https://..."
    public void voiceControl(String cmd) {
        String c = cmd.trim().toLowerCase();
        if (c.startsWith("power on"))  { if (!device.isOn()) device.powerOn();  return; }
        if (c.startsWith("power off")) { if (device.isOn())  device.powerOff(); return; }
        if (c.startsWith("vol ")) {
            try {
                int v = Integer.parseInt(c.substring(4));
                device.setVolume(v);
            } catch (Exception ignored) {}
            return;
        }
        if (c.startsWith("browse ")) {
            if (device instanceof SmartTV s) {
                String url = cmd.substring(7).trim();
                if (url.isEmpty()) url = "https://example.com";
                s.browseInternet(url);
            } else {
                System.out.println("Tämä laite ei tue selausta.");
            }
            return;
        }
        System.out.println("Tuntematon komento: " + cmd);
    }
}

public class Main {
    public static void main(String[] args) {
        // 1) Peruslaite + peruskaukosäädin
        Device tv = new TV();
        Remote tvRemote = new Remote(tv);
        tvRemote.togglePower();  // ON
        tvRemote.volumeUp();     // 30
        tvRemote.volumeDown();   // 20
        tvRemote.togglePower();  // OFF

        System.out.println("---");

        // 2) SmartTV + SmartRemote (ääni-komennot)
        Device smart = new SmartTV();
        SmartRemote r = new SmartRemote(smart);
        r.voiceControl("power on");
        r.voiceControl("vol 55");
        r.voiceControl("browse https://refactoring.guru");
        r.voiceControl("power off");
    }
}

