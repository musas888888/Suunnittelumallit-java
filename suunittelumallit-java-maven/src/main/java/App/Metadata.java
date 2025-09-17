package App;

import java.time.Instant;

public interface Metadata {
    Instant timestamp();
    String summary();
}

