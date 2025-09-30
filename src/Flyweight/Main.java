package Flyweight;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.*;

// --- 1) Ruututyypit (intrinsic = jaettava tieto: nimi + väri) ---
enum TileType {
    GRASS("G", Color.LIGHTGREEN),
    WATER("W", Color.CORNFLOWERBLUE),
    MOUNTAIN("M", Color.DARKGRAY),
    SAND("S", Color.KHAKI);

    final String label;  // lyhyt merkki
    final Color color;
    TileType(String label, Color color) { this.label = label; this.color = color; }
}

// --- 2) Tile (extrinsic = yksilöllinen: x, y, tyyppi) ---
final class Tile {
    final int x, y;
    final TileType type;
    Tile(int x, int y, TileType type) { this.x = x; this.y = y; this.type = type; }
}

// --- 3) Yksinkertainen generaattori (Factory Method -henkinen) ---
final class SimpleMap {
    final int width, height;
    final List<Tile> tiles;

    SimpleMap(int w, int h) {
        this.width = w; this.height = h;
        this.tiles = new ArrayList<>(w * h);
        Random r = new Random();
        // hyvin yksinkertainen jakauma
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                double p = r.nextDouble();
                TileType t = (p < 0.15) ? TileType.WATER
                        : (p < 0.35) ? TileType.SAND
                        : (p < 0.85) ? TileType.GRASS
                        : TileType.MOUNTAIN;
                tiles.add(new Tile(x, y, t));
            }
        }
    }
}

// --- 4) Flyweight: tehdään YKSI kuva per TileType ja kierrätetään ---
final class TileGraphicFactory {
    private final Map<TileType, Image> cache = new EnumMap<>(TileType.class);
    private final int size;
    TileGraphicFactory(int tileSize) { this.size = tileSize; }

    Image get(TileType t) {
        return cache.computeIfAbsent(t, this::makeImage);
    }

    private Image makeImage(TileType t) {
        // Piirretään pikku kuva Canvasille ja snapshot -> Image
        Canvas c = new Canvas(size, size);
        GraphicsContext g = c.getGraphicsContext2D();

        // tausta
        g.setFill(t.color);
        g.fillRect(0, 0, size, size);

        // kevyt reunaviiva
        g.setStroke(Color.color(0,0,0,0.25));
        g.strokeRect(0.5, 0.5, size-1, size-1);

        // kulmaan pikku kirjain (G/W/M/S)
        g.setFill(Color.color(0,0,0,0.45));
        g.fillText(t.label, 3, size - 3);

        return c.snapshot(null, null);
    }
}

// --- 5) Renderer: piirtää kartan flyweight-kuvilla Canvasille ---
final class Renderer {
    private final int tileSize;
    private final TileGraphicFactory gfx;

    Renderer(int tileSize) {
        this.tileSize = tileSize;
        this.gfx = new TileGraphicFactory(tileSize);
    }

    void render(SimpleMap map, GraphicsContext g) {
        for (Tile t : map.tiles) {
            Image img = gfx.get(t.type); // sama kuva jaettu kaikille saman tyypin ruuduille
            g.drawImage(img, t.x * tileSize, t.y * tileSize);
        }
    }
}

// --- 6) JavaFX App: luo kartta ja näyttää sen ---
public class Main extends Application {
    private static final int TILE = 24;
    private static final int W = 34;  // ruutuja vaakaan
    private static final int H = 22;  // ruutuja pystyyn

    @Override
    public void start(Stage stage) {
        SimpleMap map = new SimpleMap(W, H);
        Canvas canvas = new Canvas(W * TILE, H * TILE);
        new Renderer(TILE).render(map, canvas.getGraphicsContext2D());

        stage.setTitle("RPG Map - Flyweight (super-helppo)");
        stage.setScene(new Scene(new StackPane(canvas)));
        stage.show();
    }

    public static void main(String[] args) { launch(args); }
}

