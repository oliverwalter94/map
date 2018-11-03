package MapGen;

public class Tile {
	public enum TileType{
		FIELD, PLANT, BUILDING
	}
	public String name;
	public ImageObj imgObj;
	
	
	public Tile(String Name, ImageObj ImgObj) {
		name = Name;
		imgObj = ImgObj;
	}
}
