package rpg;

public class BuildingTile extends Tile {
    @Override
    public char getCharacter() {
        return 'B';
    }
    @Override
    public String getType() {
        return "building";
    }
}
