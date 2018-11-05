public class Map {
	public int field;
    public int biome;
    public int plant;
    public int adInf;
    static int map_xsize =128;
    static int map_ysize = 64;
    static boolean mapChange = true;
    static boolean miniMap = false;
    static int mapDrawState = 0;
    static boolean saved = false;

    public Map(int field, int biome, int plant, int adInf) {
        this.field = field;
        this.biome = biome;
        this.plant = plant;
        this.adInf = adInf;
    }
    
    
}
