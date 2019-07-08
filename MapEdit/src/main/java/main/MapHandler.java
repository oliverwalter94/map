package main;

import MapGen.Chunk;
import MapGen.MapGenerator;
import MapGen.Tile;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Iterator;

public class MapHandler {
    boolean mapOpen;
    boolean miniMap;
    boolean showGrid = false;

    private MapGenerator mapGenerator;
    public Board board;
    private Point neededChunks;
    public String saveName = "unnamed";
    public String editorVersion = "0.5.0";


    ArrayList<Chunk> chunks = new ArrayList<>();
    private ArrayList<Chunk> offloadedChunks = new ArrayList<>();
    private Point origin = new Point(0, 0);
    int tileSize = 16;
    int tileSpacing = 16;

    Point getOrigin() {
        return origin;
    }

    void setOrigin(Point origin) {
        this.origin = origin;
    }

    MapHandler(Board board) {
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

    void clearMap() {
        mapOpen = false;
        chunks.clear();
        origin = new Point(0, 0);
    }

    void calcNeededChunks() {
        int chunkSize = tileSpacing * 16;
        neededChunks.x = (int) Math.ceil(((chunkSize - origin.x % chunkSize) + (double) board.getWidth()) / chunkSize);
        neededChunks.y = (int) Math.ceil(((chunkSize - origin.y % chunkSize) + (double) board.getHeight()) / chunkSize);

        Point topLeft = new Point(Math.floorDiv(origin.x, chunkSize), Math.floorDiv(origin.y, chunkSize));


        for (Iterator<Chunk> iterator = chunks.iterator(); iterator.hasNext(); ) {
            Chunk current = iterator.next();
            if (!chunkVisible(current)) {
                iterator.remove();
                offloadedChunks.add(current);
            }
        }

        //add new Chunks if needed
        for (int x = topLeft.x; x < topLeft.x + neededChunks.x; x++) {
            for (int y = topLeft.y; y < topLeft.y + neededChunks.y; y++) {
                loadChunk(x, y);
            }
        }
        Board.addInfoMessage(topLeft.toString());
        mapOpen = true;
    }

    private void loadChunk(int x, int y) {
        if (!chunkLoaded(x, y)) {
            if (!chunkOffloaded(x, y)) {
                Chunk c = new Chunk(x, y);
                mapGenerator.generateChunk(c);
                chunks.add(c);
//                System.out.println("made new : " + x + ":" + y);
            } else chunks.add(reloadChunk(x, y));
        }
    }

    private boolean chunkOffloaded(int x, int y) {
        for (Chunk chunk : offloadedChunks)
            if (chunk.pos.x == x && chunk.pos.y == y) {
                return true;
            }

        return false;
    }

    private boolean chunkLoaded(int x, int y) {
        for (Chunk chunk : chunks)
            if (chunk.pos.equals(new Point(x, y)))
                return true;

        return false;
    }

    private Chunk reloadChunk(int x, int y) {
        for (Chunk c : offloadedChunks)
            if (c.pos.equals(new Point(x, y))) {
                offloadedChunks.remove(c);
                return c;
            }
        return null;
    }

    private boolean chunkVisible(Chunk chunk) {
        Rectangle2D window = new Rectangle2D.Double(origin.x, origin.y, board.getWidth(), board.getHeight());
        Rectangle2D chunkDim = new Rectangle2D.Double(chunk.pos.x * tileSpacing * 16, chunk.pos.y * tileSpacing * 16, tileSpacing * 16, tileSpacing * 16);
        return window.intersects(chunkDim);
    }

    private Chunk getActiveChunkByCoords(Point position) {
        for (Chunk chunk : chunks)
            if (chunk.pos.equals(position))
                return chunk;
        return null;
    }

    void setGround(Tile ground, Point position) {
        Chunk chunk = getActiveChunkByCoords(new Point(position.x / 16, position.y / 16));
        if (chunk != null) {
            chunk.chunkTiles[position.x % 16][position.y % 16].ground = ground;
        } else
            System.out.println("Chunk is not loaded!!!");
    }

    void setPlant(Tile plant, Point position) {
        Chunk chunk = getActiveChunkByCoords(new Point(position.x / 16, position.y / 16));
        if (chunk != null) {
            chunk.chunkTiles[position.x % 16][position.y % 16].plant = plant;
        } else
            System.out.println("Chunk is not loaded!!!");
    }

}
