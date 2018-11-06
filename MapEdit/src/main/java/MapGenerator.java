import MapGen.DataHandler;
import MapGen.OpenSimplexNoise;
import MapGen.TileWeight;

import java.util.Random;


public class MapGenerator {

    private static Random R = new Random();
    private static int map_xsize = Map.map_xsize;
    private static int map_ysize = Map.map_ysize;
    OpenSimplexNoise noise;

    private DataHandler data;

    MapGenerator() {
        data = Board.dataHandler;
    }

    void generateNewPerlinMap() {
        noise = new OpenSimplexNoise(R.nextInt(10000));

        int x = 0;
        int y = 0;
        do {
            do {
                int biome = calcNoise(x, y, data.biomes.size(), 0.1f, 0.3f, 0.05f);
                Board.MAP[x][y] = new Map(0, biome, -1, 0);
                x++;
            } while (x < map_xsize);
            x = 0;
            y++;
        } while (y < map_ysize);

        generateBlocksNew();
    }

    int calcNoise(int x, int y, int upper, float gran1, float gran2, float gran3) {
        int result = -1;
        double calc, calc1, calc2, calc3;
        calc1 = (noise.eval(x * gran1, y * gran1) + 1) / 2;
        calc2 = (noise.eval(x * gran2, y * gran2) + 1) / 2;
        calc3 = (noise.eval(x * gran3, y * gran3) + 1) / 2;

        if (gran3 == 0)
            calc = (Math.sqrt(calc1 * calc2) * upper);
        else
            calc = (Math.cbrt(calc1 * calc2 * calc3) * upper);

        result = (int) calc;
        return result;
    }

    public void generateNewEmptyMap() {
        int x_c = 0;
        int y_c = 0;
        do {
            do {
                Board.MAP[x_c][y_c] = new Map(1, 0, -1, 0);
                x_c++;
            } while (x_c < map_xsize);
            if (x_c == map_xsize) {
                x_c = 0;
                y_c++;
            }
        } while (y_c < map_ysize);
    }

    private void generateBlocksNew() {

        int x = 0;
        int y = 0;
        do {
            do {
                int b = Board.MAP[x][y].biome;
                //generate Field
                if (data.biomes.get(b).fieldweights.size() == 1)
                    Board.MAP[x][y].field = data.fields.indexOf(data.biomes.get(b).fieldweights.get(0).tile);
                else {
                    float chance = R.nextFloat();
                    float soFar = 0;
                    for (TileWeight tileWeight : data.biomes.get(b).fieldweights) {
                        soFar += tileWeight.weight;
                        if (chance <= soFar) {
                            Board.MAP[x][y].field = data.fields.indexOf(tileWeight.tile);
                            break;
                        }
                    }
                }
                //Generate plants
                if (data.biomes.get(b).plantweights != null) {
                    float chance = R.nextFloat();
                    float soFar = 0;
                    for (TileWeight tileWeight : data.biomes.get(b).plantweights) {
                        soFar += tileWeight.weight;
                        if (chance <= soFar) {
                            Board.MAP[x][y].plant = data.plants.indexOf(tileWeight.tile);
                            break;
                        }
                    }
                }
                x++;
            } while (x < map_xsize);
            if (x == map_xsize) {
                x = 0;
                y++;
            }
        } while (y < map_ysize);
    }

    public static void create_rivers() {
        int rivercounter = 0;
        int prevx = 0;
        int prevy = 0;
        int riverAmount = 5;
        do {
            int x = R.nextInt((map_xsize - 10)) + 5;
            int y = R.nextInt((map_ysize - 10)) + 5;
            int dir = R.nextInt(4);
            int c = 0;
            boolean a = false;
            boolean b = false;
            switch (dir) {
                case 0: {
                    do {
                        if (c == 0) Board.MAP[x][y].adInf = 0;
                        Board.MAP[x][y].biome = 2;
                        int r = R.nextInt(5);
                        if (r == 1 && !b) {

                            x++;
                            Board.MAP[x][y].biome = 2;
                            if (c != 0) Board.MAP[prevx][prevy].adInf = 1;
                            a = true;
                        } else if (r == 2 && !a) {
                            x--;
                            Board.MAP[x][y].biome = 2;
                            if (c != 0) Board.MAP[prevx][prevy].adInf = 3;
                            b = true;
                        } else {
                            Board.MAP[prevx][prevy].adInf = 0;
                            b = false;
                            a = false;
                        }
                        y--;
                        prevx = x;
                        prevy = y;
                        c++;
                    } while (Board.MAP[x][y].biome != 0 && Board.MAP[x][y].biome != 2);
                    Board.MAP[x][y].adInf = 0;
                    break;
                }
                case 1: {
                    do {
                        if (c == 0) Board.MAP[x][y].adInf = 1;
                        Board.MAP[x][y].biome = 2;
                        int r = R.nextInt(5);
                        if (r == 1 && !b) {
                            y--;
                            Board.MAP[x][y].biome = 2;
                            if (c != 0) Board.MAP[prevx][prevy].adInf = 0;
                            a = true;
                        } else if (r == 2 && !a) {
                            y++;
                            Board.MAP[x][y].biome = 2;
                            if (c != 0) Board.MAP[prevx][prevy].adInf = 2;
                            b = true;
                        } else {
                            Board.MAP[prevx][prevy].adInf = 1;
                            a = false;
                            b = false;
                        }
                        x++;
                        prevx = x;
                        prevy = y;
                        c++;
                    } while (Board.MAP[x][y].biome != 0 && Board.MAP[x][y].biome != 2);
                    Board.MAP[x][y].adInf = 1;
                    break;
                }
                case 2: {
                    do {
                        if (c == 0) Board.MAP[x][y].adInf = 2;
                        Board.MAP[x][y].biome = 2;
                        int r = R.nextInt(5);
                        if (r == 1 && !b) {
                            x--;
                            Board.MAP[x][y].biome = 2;
                            if (c != 0) Board.MAP[prevx][prevy].adInf = 3;
                            a = true;
                        } else if (r == 2 && !a) {
                            x++;
                            Board.MAP[x][y].biome = 2;
                            if (c != 0) Board.MAP[prevx][prevy].adInf = 1;
                            b = true;
                        } else {
                            Board.MAP[prevx][prevy].adInf = 2;
                            a = false;
                            b = false;
                        }
                        y++;
                        prevx = x;
                        prevy = y;
                        c++;
                    } while (Board.MAP[x][y].biome != 0 && Board.MAP[x][y].biome != 8);
                    Board.MAP[x][y].adInf = 2;
                    break;
                }
                case 3: {
                    do {
                        if (c == 0) Board.MAP[x][y].adInf = 3;
                        Board.MAP[x][y].biome = 2;
                        int r = R.nextInt(5);
                        if (r == 1 && !b) {
                            y--;
                            Board.MAP[x][y].biome = 2;
                            if (c != 0) Board.MAP[prevx][prevy].adInf = 0;
                            a = true;
                        } else if (r == 2 && !a) {
                            y++;
                            Board.MAP[x][y].biome = 2;
                            if (c != 0) Board.MAP[prevx][prevy].adInf = 2;
                            b = true;
                        } else {
                            a = false;
                            b = false;
                            Board.MAP[prevx][prevy].adInf = 3;
                        }
                        x--;
                        prevx = x;
                        prevy = y;
                        c++;
                    } while (Board.MAP[x][y].biome != 0 && Board.MAP[x][y].biome != 2);
                    Board.MAP[x][y].adInf = 3;
                    break;
                }
            }
            rivercounter++;
        } while (rivercounter < riverAmount);
    }



}
