package MapGen;

import java.awt.*;

public class Chunk {
    //TODO: Add Implementation of chunks
    public MapTile[][] chunkTiles;
    public Point pos;
    Boolean updated;
    public Boolean saved;
    public Boolean rendering = false;

    public Chunk(int x, int y) {
        chunkTiles = new MapTile[16][16];
        pos = new Point(x, y);

    }
}
