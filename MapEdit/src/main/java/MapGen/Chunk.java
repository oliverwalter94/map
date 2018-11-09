package MapGen;

public class Chunk {
    //TODO: Add Implementation of chunks
    public MapTile[][] chunkTiles;
    public int x;
    public int y;
    Boolean updated;
    public Boolean saved;
    public Boolean rendering = false;

    public Chunk(int x, int y) {
        chunkTiles = new MapTile[16][16];
        this.x = x;
        this.y = y;

    }
}
