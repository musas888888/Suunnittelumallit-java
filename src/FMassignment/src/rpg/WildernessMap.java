package rpg;

public class WildernessMap extends Map {
    public WildernessMap(int w, int h) { super(w, h); }

    @Override
    protected Tile createTile() {
        int k = rnd.nextInt(3);           // swamp / water / forest
        return switch (k) {
            case 0 -> new SwampTile();
            case 1 -> new WaterTile();
            default -> new ForestTile();
        };
    }
}
