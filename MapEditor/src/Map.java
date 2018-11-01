public class Map {
	public int id;
    public int biome;
    public int subId;
    public int adInf;
    static int map_xsize =128;
    static int map_ysize = 64;
    static boolean mapChange = true;
    static boolean miniMap = false;
    static int mapDrawState = 0;
    static boolean saved = false;
    
    public Map(int ID, int b, int SubId, int AdInf){
        id = ID;
        biome = b;
        subId = SubId;
        adInf = AdInf;
    }
    
    
}
