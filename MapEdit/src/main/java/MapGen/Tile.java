package MapGen;

public class Tile {
	public enum TileType{
		FIELD, PLANT, BUILDING
	}
	public String name;
	public ImageObj img;
	
	
	public Tile(String Name, ImageObj Img) {
		name = Name;
		img = Img;
	}
}
