import java.util.ArrayList;

public class Biome {

	enum BiomeType {
		DRY, HUMID, DAMP, FRESHWATER, SALTWATER
	}
	
	String name;
	ArrayList<FieldWeight> fieldweights;
	ArrayList<PlantWeight> plantweights;
	
	public Biome(String Name, ArrayList<FieldWeight> Fields, ArrayList<PlantWeight> Plants) {
		name = Name;
		fieldweights = Fields;
		plantweights = Plants;
	}
	
	
	
	
}
