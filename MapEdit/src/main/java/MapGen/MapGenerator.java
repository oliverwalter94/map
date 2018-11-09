package MapGen;

import Data.DataHandler;

import java.util.Random;


public class MapGenerator {

    private static Random R = new Random();
    //    private static int map_xsize = Map.map_xsize;
//    private static int map_ysize = Map.map_ysize;
    private OpenSimplexNoise noise;

    private DataHandler data;
//    private Map[][] map;

    public MapGenerator(DataHandler dataHandler) {
        data = dataHandler;
//        map = MAP;
        noise = new OpenSimplexNoise(R.nextInt(10000));
    }

    public void generateNewPerlinMap() {
//        noise = new OpenSimplexNoise(R.nextInt(10000));
//
//        int x = 0;
//        int y = 0;
//        do {
//            do {
//                int biome = calcNoise(x, y, data.biomes.size(), 0.1f, 0.3f, 0.05f);
//                map[x][y] = new Map(0, biome, -1, 0);
//                x++;
//            } while (x < map_xsize);
//            x = 0;
//            y++;
//        } while (y < map_ysize);
//
//        generateBlocksNew();
    }

    public void generateChunk(Chunk chunk) {
        MapTile[][] tiles = chunk.chunkTiles;

        for (int x = 0; x < 16; x++) {
            for (int y = 0; y < 16; y++) {
                //Generate biome
                Biome biome = data.biomes.get(calcNoise(chunk.x * 16 + x, chunk.y * 16 + y, data.biomes.size(), 0.1f, 0.3f, 0.05f));
                tiles[x][y] = new MapTile(biome);

                //Generate ground
                if (biome.groundweights.size() == 1)
                    tiles[x][y].ground = biome.groundweights.get(0).tile;
                else {
                    float chance = R.nextFloat();
                    float soFar = 0;
                    for (TileWeight tileWeight : biome.groundweights) {
                        soFar += tileWeight.weight;
                        if (chance <= soFar) {
                            tiles[x][y].ground = tileWeight.tile;
                            break;
                        }
                    }
                }
                //Generate plant
                if (biome.plantweights != null) {
                    float chance = R.nextFloat();
                    float soFar = 0;
                    for (TileWeight tileWeight : biome.plantweights) {
                        soFar += tileWeight.weight;
                        if (chance <= soFar) {
                            tiles[x][y].plant = tileWeight.tile;
                            break;
                        }
                    }
                }
            }
        }
    }

    private int calcNoise(int x, int y, int upper, float gran1, float gran2, float gran3) {
        double calc, calc1, calc2, calc3;
        calc1 = (noise.eval(x * gran1, y * gran1) + 1) / 2;
        calc2 = (noise.eval(x * gran2, y * gran2) + 1) / 2;
        calc3 = (noise.eval(x * gran3, y * gran3) + 1) / 2;

        if (gran3 == 0)
            calc = (Math.sqrt(calc1 * calc2) * upper);
        else
            calc = (Math.cbrt(calc1 * calc2 * calc3) * upper);

        return (int) calc;
    }

    private void generateBlocksNew() {
//
//        int x = 0;
//        int y = 0;
//        do {
//            do {
//                int b = map[x][y].biome;
//                //generate Field
//                if (data.biomes.get(b).groundweights.size() == 1)
//                    map[x][y].field = data.fields.indexOf(data.biomes.get(b).groundweights.get(0).tile);
//                else {
//                    float chance = R.nextFloat();
//                    float soFar = 0;
//                    for (TileWeight tileWeight : data.biomes.get(b).groundweights) {
//                        soFar += tileWeight.weight;
//                        if (chance <= soFar) {
//                            map[x][y].field = data.fields.indexOf(tileWeight.tile);
//                            break;
//                        }
//                    }
//                }
//                //Generate plants
//                if (data.biomes.get(b).plantweights != null) {
//                    float chance = R.nextFloat();
//                    float soFar = 0;
//                    System.out.println("Test");
//                    for (TileWeight tileWeight : data.biomes.get(b).plantweights) {
//                        soFar += tileWeight.weight;
//                        if (chance <= soFar) {
//                            map[x][y].plant = data.plants.indexOf(tileWeight.tile);
//                            break;
//                        }
//                    }
//                }
//                x++;
//            } while (x < map_xsize);
//            if (x == map_xsize) {
//                x = 0;
//                y++;
//            }
//        } while (y < map_ysize);
    }

    public void create_rivers() {
//        int rivercounter = 0;
//        int prevx = 0;
//        int prevy = 0;
//        int riverAmount = 5;
//        do {
//            int x = R.nextInt((map_xsize - 10)) + 5;
//            int y = R.nextInt((map_ysize - 10)) + 5;
//            int dir = R.nextInt(4);
//            int c = 0;
//            boolean a = false;
//            boolean b = false;
//            switch (dir) {
//                case 0: {
//                    do {
//                        if (c == 0) map[x][y].adInf = 0;
//                        map[x][y].biome = 2;
//                        int r = R.nextInt(5);
//                        if (r == 1 && !b) {
//
//                            x++;
//                            map[x][y].biome = 2;
//                            if (c != 0) map[prevx][prevy].adInf = 1;
//                            a = true;
//                        } else if (r == 2 && !a) {
//                            x--;
//                            map[x][y].biome = 2;
//                            if (c != 0) map[prevx][prevy].adInf = 3;
//                            b = true;
//                        } else {
//                            map[prevx][prevy].adInf = 0;
//                            b = false;
//                            a = false;
//                        }
//                        y--;
//                        prevx = x;
//                        prevy = y;
//                        c++;
//                    } while (map[x][y].biome != 0 && map[x][y].biome != 2);
//                    map[x][y].adInf = 0;
//                    break;
//                }
//                case 1: {
//                    do {
//                        if (c == 0) map[x][y].adInf = 1;
//                        map[x][y].biome = 2;
//                        int r = R.nextInt(5);
//                        if (r == 1 && !b) {
//                            y--;
//                            map[x][y].biome = 2;
//                            if (c != 0) map[prevx][prevy].adInf = 0;
//                            a = true;
//                        } else if (r == 2 && !a) {
//                            y++;
//                            map[x][y].biome = 2;
//                            if (c != 0) map[prevx][prevy].adInf = 2;
//                            b = true;
//                        } else {
//                            map[prevx][prevy].adInf = 1;
//                            a = false;
//                            b = false;
//                        }
//                        x++;
//                        prevx = x;
//                        prevy = y;
//                        c++;
//                    } while (map[x][y].biome != 0 && map[x][y].biome != 2);
//                    map[x][y].adInf = 1;
//                    break;
//                }
//                case 2: {
//                    do {
//                        if (c == 0) map[x][y].adInf = 2;
//                        map[x][y].biome = 2;
//                        int r = R.nextInt(5);
//                        if (r == 1 && !b) {
//                            x--;
//                            map[x][y].biome = 2;
//                            if (c != 0) map[prevx][prevy].adInf = 3;
//                            a = true;
//                        } else if (r == 2 && !a) {
//                            x++;
//                            map[x][y].biome = 2;
//                            if (c != 0) map[prevx][prevy].adInf = 1;
//                            b = true;
//                        } else {
//                            map[prevx][prevy].adInf = 2;
//                            a = false;
//                            b = false;
//                        }
//                        y++;
//                        prevx = x;
//                        prevy = y;
//                        c++;
//                    } while (map[x][y].biome != 0 && map[x][y].biome != 8);
//                    map[x][y].adInf = 2;
//                    break;
//                }
//                case 3: {
//                    do {
//                        if (c == 0) map[x][y].adInf = 3;
//                        map[x][y].biome = 2;
//                        int r = R.nextInt(5);
//                        if (r == 1 && !b) {
//                            y--;
//                            map[x][y].biome = 2;
//                            if (c != 0) map[prevx][prevy].adInf = 0;
//                            a = true;
//                        } else if (r == 2 && !a) {
//                            y++;
//                            map[x][y].biome = 2;
//                            if (c != 0) map[prevx][prevy].adInf = 2;
//                            b = true;
//                        } else {
//                            a = false;
//                            b = false;
//                            map[prevx][prevy].adInf = 3;
//                        }
//                        x--;
//                        prevx = x;
//                        prevy = y;
//                        c++;
//                    } while (map[x][y].biome != 0 && map[x][y].biome != 2);
//                    map[x][y].adInf = 3;
//                    break;
//                }
//            }
//            rivercounter++;
//        } while (rivercounter < riverAmount);
    }



}
