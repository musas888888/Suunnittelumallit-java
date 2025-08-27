package rpg;

import java.util.Random;

public abstract class Map {
    protected final int width, height;
    protected final Tile[][] grid;
    protected final Random rnd = new Random();

    protected Map(int w, int h) {
        this.width = w; this.height = h;
        this.grid = new Tile[h][w];
        fill();
    }

    // Factory Method: subclass decides which concrete Tile to create
    protected abstract Tile createTile();

    private void fill() {
        for (int y = 0; y < height; y++)
            for (int x = 0; x < width; x++)
                grid[y][x] = createTile();
    }

    // completely agnostic to concrete tile types
    public void display() {
        for (int y = 0; y < height; y++) {
            StringBuilder row = new StringBuilder();
            for (int x = 0; x < width; x++) {
                row.append(grid[y][x].getCharacter()).append(' ');
            }
            System.out.println(row);
        }
    }
}
