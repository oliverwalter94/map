package MapGen;

public class Map {
    public int field;
    public int biome;
    public int plant;
    public int adInf;
    public static int map_xsize = 256;
    public static int map_ysize = 128;
    public static boolean mapChange = true;
    public static boolean miniMap = false;
    public static int mapDrawState = 0;
    public static boolean saved = false;

    Map(int field, int biome, int plant, int adInf) {
        this.field = field;
        this.biome = biome;
        this.plant = plant;
        this.adInf = adInf;
    }


}
