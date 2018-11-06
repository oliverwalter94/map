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

    void generateNewMap() {
        int x_c = 0;
        int y_c = 0;
        do {
            do {
                int a = R.nextInt(101);
                int b = R.nextInt(6) + 1;
                if (x_c == 0 || y_c == 0 || x_c == (map_xsize - 1) || y_c == (map_ysize - 1) || x_c == 1 || y_c == 1 || x_c == (map_xsize - 2) || y_c == (map_ysize - 2)) {
                    Board.MAP[x_c][y_c] = new Map(0, 0, 0, 0);
                } else if (x_c == 2 && y_c == 2) {
                    Board.MAP[x_c][y_c] = new Map(0, 5, 0, 0);
                } else {
                    if (a <= 95) {
                        if (y_c != 2 && x_c != 2) {
                            if (Board.MAP[x_c][y_c - 1].biome == Board.MAP[x_c - 1][y_c].biome) {
                                Board.MAP[x_c][y_c] = new Map(0, Board.MAP[x_c][y_c - 1].biome, 0, 0);
                            } else {
                                int i = R.nextInt(2);
                                if (i == 0)
                                    Board.MAP[x_c][y_c] = new Map(0, Board.MAP[x_c][y_c - 1].biome, 0, 0);
                                else
                                    Board.MAP[x_c][y_c] = new Map(0, Board.MAP[x_c - 1][y_c].biome, 0, 0);
                            }
                        } else if (x_c == 2) {
                            Board.MAP[x_c][y_c] = new Map(0, Board.MAP[x_c][y_c - 1].biome, 0, 0);
                        } else if (y_c == 2) {
                            Board.MAP[x_c][y_c] = new Map(0, Board.MAP[x_c - 1][y_c].biome, 0, 0);
                        }
                    } else {
                        if (b == 1) do {
                            b = R.nextInt(6) + 1;
                        } while (b == 1);
                        Board.MAP[x_c][y_c] = new Map(0, b, 0, 0);
                    }
                }
                x_c++;
            } while (x_c < map_xsize);
            if (x_c == map_xsize) {
                x_c = 0;
                y_c++;
            }
        } while (y_c < map_ysize);
        x_c = 0;
        y_c = 0;
        int twice = 0;
        do {
            do {
                do {
                    if (x_c >= 2 && y_c >= 2 && x_c <= map_xsize - 2 && y_c <= map_ysize - 2) {
                        int b = Board.MAP[x_c][y_c].biome;
                        if (!check_up(x_c, y_c, b, 1, true) && !check_down(x_c, y_c, b, 1, true) && !check_left(x_c, y_c, b, 1, true) && !check_right(x_c, y_c, b, 1, true))
                            Board.MAP[x_c][y_c] = new Map(0, Board.MAP[x_c - 1][y_c].biome, 0, 0);
                    }
                    x_c++;
                } while (x_c < map_xsize);
                if (x_c == map_xsize) {
                    x_c = 0;
                    y_c++;
                }
            } while (y_c < map_ysize);
            twice++;
        } while (twice < 2);

        generateCoast();

//	        create_rivers();

        generateBlocksNew();
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

    public static void clearMap() {
        int x_c = 0;
        int y_c = 0;
        do {
            do {
                Board.MAP[x_c][y_c] = null;
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


    private void generateCoast() {
        int beach = data.getBiomeIdByName("Beach");
        int ocean = data.getBiomeIdByName("Ocean");

        int xc = 0;
        int yc = 0;
        int i = 0;
        do {
            do {
                do {
                    if (Board.MAP[xc][yc].biome != 0) {
                        if (check_left(xc, yc, ocean, 1, true) || check_up(xc, yc, ocean, 1, true) || check_right(xc, yc, ocean, 1, true) || check_down(xc, yc, ocean, 1, true)) {
                            if (R.nextInt(4) == 0)
                                Board.MAP[xc][yc].biome = ocean;
                        }
                    }
                    xc++;
                } while (xc < map_xsize);
                if (xc == map_xsize) {
                    xc = 0;
                    yc++;
                }
            } while (yc < map_ysize);
            xc = 0;
            yc = 0;
            i++;
        } while (i < 4);
        do {
            do {
                if (Board.MAP[xc][yc].biome != 0) {
                    if (check_left(xc, yc, 0, 1, true) || check_up(xc, yc, 0, 1, true) || check_right(xc, yc, 0, 1, true) || check_down(xc, yc, 0, 1, true)) {
                        Board.MAP[xc][yc].biome = beach;
                    }
                    if (check_left(xc, yc, 1, 1, true) || check_up(xc, yc, 1, 1, true) || check_right(xc, yc, 1, 1, true) || check_down(xc, yc, 1, 1, true)) {
                        if (R.nextInt(3) == 1) {
                            Board.MAP[xc][yc].biome = beach;
                        }
                    }
                }
                xc++;
            } while (xc < map_xsize);
            if (xc == map_xsize) {
                xc = 0;
                yc++;
            }
        } while (yc < map_ysize);
    }

    private boolean check_right(int x, int y, int field_id, int distance, boolean biome) {
        if (x + distance <= map_xsize) {
            if (!biome) {
                return Board.MAP[x + distance][y].field == field_id;
            } else {
                return Board.MAP[x + distance][y].biome == field_id;
            }
        } else return false;
    }

    private static boolean check_left(int x, int y, int field_id, int distance, boolean biome) {
        if (x - distance >= 0) {
            if (!biome) {
                return Board.MAP[x - distance][y].field == field_id;
            } else {
                return Board.MAP[x - distance][y].biome == field_id;
            }
        } else return false;
    }

    private static boolean check_up(int x, int y, int field_id, int distance, boolean biome) {
        if (y - distance >= 0) {
            if (!biome) {
                return Board.MAP[x][y - distance].field == field_id;
            } else {
                return Board.MAP[x][y - distance].biome == field_id;
            }
        } else return false;
    }

    private static boolean check_down(int x, int y, int field_id, int distance, boolean biome) {
        if (y + distance <= map_xsize) {
            if (!biome) {
                return Board.MAP[x][y + distance].field == field_id;
            } else {
                return Board.MAP[x][y + distance].biome == field_id;
            }
        } else return false;
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
