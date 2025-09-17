package Memento.App;

import javafx.scene.paint.Color;
import java.time.Instant;

public record Memento(Color c1, Color c2, Color c3, boolean checked,
                      Instant timestamp, String summary) implements Metadata {

    public Memento(Color c1, Color c2, Color c3, boolean checked) {
        this(c1, c2, c3, checked, Instant.now(),
                String.format("c1=%s, c2=%s, c3=%s, checked=%s",
                        toHex(c1), toHex(c2), toHex(c3), checked));
    }

    private static String toHex(Color c) {
        int r = (int)Math.round(c.getRed()*255);
        int g = (int)Math.round(c.getGreen()*255);
        int b = (int)Math.round(c.getBlue()*255);
        return String.format("#%02X%02X%02X", r,g,b);
    }

    @Override public String summary() { return summary; }
    @Override public Instant timestamp() { return timestamp; }

    @Override public String toString() {
        return timestamp.toString() + " — " + summary;
    }
}

