package MapGen;

import Data.ImageObject;

public class Structure {
    int size;
    String name;
    boolean hasGround;
    MapTile[][] data;
    ImageObject icon;

    public Structure(String name) {
        data = new MapTile[16][16];
        this.name = name;

    }


}
