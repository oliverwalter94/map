package MapGen;

import java.util.ArrayList;

public class Biome {
	public String name;

	public ArrayList<TileWeight> groundweights;
	public ArrayList<TileWeight> plantweights;

	boolean naturallyGenerated;

	public Biome(String Name, ArrayList<TileWeight> Grounds, ArrayList<TileWeight> Plants, boolean NaturallyGenerated) {
		name = Name;
		groundweights = Grounds;
		plantweights = Plants;
		naturallyGenerated = NaturallyGenerated;
	}
}
