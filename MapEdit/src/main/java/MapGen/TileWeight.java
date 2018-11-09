package MapGen;
import java.util.ArrayList;

public class TileWeight {
	public Tile tile;
	public float weight;
	public ArrayList<Tile> requiredTile;
		
		public TileWeight(Tile t, float Weight) {
			tile = t;
			weight = Weight;
		}
		
		public TileWeight(Tile t, float Weight, ArrayList<Tile> Required) {
			tile = t;
			weight = Weight;
			requiredTile = Required;
		}
	}

