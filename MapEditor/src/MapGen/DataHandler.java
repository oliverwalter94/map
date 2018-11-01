package MapGen;

import java.awt.Image;
import java.util.ArrayList;

public class DataHandler {
	
	public ArrayList<ImageObj> images;
	public ArrayList<Tile> tiles;
	public ArrayList<Biome> biomes;
	
	public DataHandler() {
		images = new ArrayList<ImageObj>();
		tiles = new ArrayList<Tile>();
		biomes = new ArrayList<Biome>();
	}
	
	public int getImageIdByTile(Tile t) {
		return images.lastIndexOf(t.img);
	}
	
	public Image getImageByName(String name) {
		for (ImageObj imob:images) {
			if (imob.name == name) {
				return imob.img;
			}
		}
		return null;
	}
	
	public int getTileByName (String name) {
		int result = -1;
			
		return result;
	}

}
