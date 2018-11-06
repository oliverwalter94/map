package MapGen;

import java.util.ArrayList;

public class Biome {
	String name;
	
	public ArrayList<TileWeight> fieldweights;
	public ArrayList<TileWeight> plantweights;

	boolean naturallyGenerated;

	Biome(String Name, ArrayList<TileWeight> Fields, ArrayList<TileWeight> Plants, boolean NaturallyGenerated) {
		name = Name;
		fieldweights = Fields;
		plantweights = Plants;
		naturallyGenerated = NaturallyGenerated;
	}
}
