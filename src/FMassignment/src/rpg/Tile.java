package rpg;

public abstract class Tile {
    public abstract char getCharacter();     // 'S','W','R','F','B'
    public abstract String getType();        // "swamp", "water", ...
    public void action() { /* not used in this assignment */ }
}

