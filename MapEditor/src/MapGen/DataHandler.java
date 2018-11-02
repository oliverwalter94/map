package MapGen;

import java.awt.Image;
import java.util.ArrayList;

public class DataHandler {
	
	public ArrayList<ImageObj> images;
	public ArrayList<Tile> fields;
	public ArrayList<Tile> plants;
	public ArrayList<Tile> buildings;
	public ArrayList<Biome> biomes;
	
	public DataHandler() {
		images = new ArrayList<ImageObj>();
		fields = new ArrayList<Tile>();
		plants = new ArrayList<Tile>();
		buildings = new ArrayList<Tile>();
		biomes = new ArrayList<Biome>();
	}
	
	public int getImageIdByTile(Tile t) {
		return images.indexOf(t.img);
	}
	
	public Image getImageByName(String name) {
		for (ImageObj imob:images) {
			if (imob.name == name) {
				return imob.img;
			}
		}
		return null;
	}

	public int getBiomeIdByName(String name){
		for(Biome b:biomes){
			if(b.name == name)
				return biomes.indexOf(b);
		}
		return -1;
	}

	public int getTileIdByName (String name) {
		for (Tile t:fields) {
			if(t.name == name)
				return fields.indexOf(t);
		}
		for (Tile t:plants) {
			if(t.name == name)
				return plants.indexOf(t);
		}
		return -1;
	}
	
	public void addTile() {
		
	}
	
	public void addImage() {
		
	}
	
	public void readImages() {
		
	}
	
	public void readBiomes() {
		
	}
	
	public void readTiles() {
		
	}

}
