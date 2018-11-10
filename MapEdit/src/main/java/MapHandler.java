import MapGen.Chunk;
import MapGen.MapGenerator;
import UI.Message;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class MapHandler {
    boolean mapOpen;
    boolean miniMap;

    private MapGenerator mapGenerator;
    private Board board;
    private Point neededChunks;

    ArrayList<Chunk> chunks = new ArrayList<>();

    private Point origin = new Point(0, 0);
    int tileSize = 16;

    public Point getOrigin() {
        return origin;
    }

    public void setOrigin(Point origin) {
        this.origin = origin;
    }

    public MapHandler(Board board) {
        miniMap = false;
        mapGenerator = new MapGenerator(Board.dataHandler);
        neededChunks = new Point(0, 0);
        this.board = board;
        mapOpen = false;

    }

    void newMap() {
        clearMap();
        mapGenerator = new MapGenerator(Board.dataHandler);
        calcNeededChunks();
    }

    private void addChunk(int x, int y) {
        if (!chunkExists(x, y)) {
            Chunk c = new Chunk(x, y);
            mapGenerator.generateChunk(c);
            chunks.add(c);
        }
    }

    private boolean chunkExists(int x, int y) {
        for (Chunk chunk : chunks)
            if (chunk.pos.equals(new Point(x, y)))
                return true;
        return false;
    }

    private boolean chunkVisible(Chunk chunk) {
        Rectangle2D window = new Rectangle2D.Double(origin.x, origin.y, board.getWidth(), board.getHeight());
        Rectangle2D chunkDim = new Rectangle2D.Double(chunk.pos.x * tileSize * 16, chunk.pos.y * tileSize * 16, tileSize * 16, tileSize * 16);
        if (window.intersects(chunkDim))
            return true;
        return false;
    }

    public void clearMap() {
        mapOpen = false;
        chunks.clear();
        origin = new Point(0, 0);
    }

    void calcNeededChunks() {
        int chunkSize = tileSize * 16;
        neededChunks.x = (int) Math.ceil(((chunkSize - origin.x % chunkSize) + (double) board.getWidth()) / chunkSize);
        neededChunks.y = (int) Math.ceil(((chunkSize - origin.y % chunkSize) + (double) board.getHeight()) / chunkSize);

        Point topLeft = new Point(Math.floorDiv(origin.x, chunkSize), Math.floorDiv(origin.y, chunkSize));


        chunks.removeIf(chunk1 -> !chunkVisible(chunk1));

        //add new Chunks if needed
        for (int x = topLeft.x; x < topLeft.x + neededChunks.x; x++) {
            for (int y = topLeft.y; y < topLeft.y + neededChunks.y; y++) {
                addChunk(x, y);
            }
        }
        System.out.println(origin);
        Board.addInfoMessage(new Message(topLeft.toString(), Message.Type.INFO));
        mapOpen = true;
    }

}
