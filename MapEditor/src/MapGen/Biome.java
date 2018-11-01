package MapGen;

import java.util.ArrayList;

public class Biome {
	String name;
	
	public ArrayList<TileWeight> fieldweights;
	public ArrayList<TileWeight> plantweights;
	
	public Biome(String Name, ArrayList<TileWeight> Fields, ArrayList<TileWeight> Plants) {
		name = Name;
		fieldweights = Fields;
		plantweights = Plants;
	}
}
