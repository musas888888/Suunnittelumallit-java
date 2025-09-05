package FMassignment.src.rpg;

public class Game {
    public enum MapType {
        CITY, WILDERNESS
    }

    // factory method in Game for Map objects
    public static Map createMap(MapType type, int w, int h) {
        return (type == MapType.CITY) ? new CityMap(w, h) : new WildernessMap(w, h);
    }

    // usage: java rpg.Game [city|wild] [width] [height]
    public static void main(String[] args) {
        MapType type = (args.length > 0 && args[0].toLowerCase().startsWith("w"))
                ? MapType.WILDERNESS : MapType.CITY;
        int w = (args.length > 1) ? Integer.parseInt(args[1]) : 10;
        int h = (args.length > 2) ? Integer.parseInt(args[2]) : 6;

        Map map = createMap(type, w, h);
        map.display();
    }
}
