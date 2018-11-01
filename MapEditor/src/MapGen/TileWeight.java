package MapGen;
import java.util.ArrayList;

public class PlantWeight {
		int id;
		int weight;
		ArrayList<Integer> requiredField;
		public PlantWeight(int Id, int Weight, ArrayList<Integer> Required) {
			id = Id;
			weight = Weight;
			requiredField = Required;
		}
	}