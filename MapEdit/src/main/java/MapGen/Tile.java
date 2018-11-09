package MapGen;


import Data.ImageObject;

public class Tile {
	public enum TileType{
		FIELD, PLANT, BUILDING
	}
	public String name;
    public ImageObject imageObject;
    ;

    public Tile(String Name, ImageObject ImgObj) {
		name = Name;
        imageObject = ImgObj;
	}
}
