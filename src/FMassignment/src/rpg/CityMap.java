package FMassignment.src.rpg;

public class CityMap extends Map {
    public CityMap(int w, int h) { super(w, h); }

    @Override
    protected Tile createTile() {
        int k = rnd.nextInt(3);           // road / forest / building
        return switch (k) {
            case 0 -> new RoadTile();
            case 1 -> new ForestTile();
            default -> new BuildingTile();
        };
    }
}
