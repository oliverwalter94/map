import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

public class Images {

	static File f;


	ArrayList<Tile> Fields = new ArrayList<Tile>();
	ArrayList<Tile> Plants = new ArrayList<Tile>();
	ArrayList<Tile> Buildings = new ArrayList<Tile>();
	ArrayList<Tile> Biomes = new ArrayList<Tile>();
	ArrayList<Tile> Other = new ArrayList<Tile>();
	
	static String TEX = System.getProperty("user.home") + "/2D Game/Textures/";
	
	enum ImageCategory  {
		FIELD, PLANT, BUILDING, BIOME, OTHER
	}

	public class Tile{
		Image img;
		String name;
		public Tile(Image Img, String Name) {
			img = Img;
			name = Name;
		}
	}
	
	public void loadAllImages() {
		loadFields();
		loadPlants();
		loadBiomes();
		loadOthers();
		
	}
	
	public int findFieldImageByName(String name) {
		int result = -1;
		for(Tile t:this.Fields) {
			if(t.name == name) {
				result = Fields.indexOf(t);
				break;
			}
		}
		return result;
	}
	
	
	public void loadImage(String name, String replaceName, ImageCategory imgcat) {
		f = new File(TEX + replaceName);
		ImageIcon icon;
		if(f.exists())
			icon = new ImageIcon(f.getPath());
		else
			icon = new ImageIcon(InterfaceNew.class.getResource("sources/Environment/" + replaceName));
		
		Tile t = new Tile(icon.getImage(), name);
		
		switch (imgcat) {
		
		case FIELD:
			Fields.add(new Tile(icon.getImage(), name));
			break;
			
		case PLANT:
			Plants.add(t);
			break;
			
		case BUILDING:
			Buildings.add(t);
			break;
			
		case BIOME:
			Biomes.add(t);
			break;
			
		case OTHER:
			Other.add(t);
			break;
			
		
		}
	
	}
	
	public void loadFields() {
		loadImage("Sea Water", "seawater32.png", ImageCategory.FIELD);
		loadImage("Fresh Water", "freshwater32.png", ImageCategory.FIELD);
		loadImage("Floating Water", "floatingwater32.png", ImageCategory.FIELD);
		loadImage("Grass", "grass32.png", ImageCategory.FIELD);
		loadImage("Sand", "sand32.png", ImageCategory.FIELD);
		loadImage("Gravel", "gravel32.png", ImageCategory.FIELD);
		loadImage("Farmland", "farmland32.png", ImageCategory.FIELD);
	}

	public void loadPlants() {
		loadImage("Nothing", "empty32.png", ImageCategory.PLANT);
		loadImage("Tree", "tree32.png", ImageCategory.PLANT);
		loadImage("Fir", "fir32.png", ImageCategory.PLANT);
		loadImage("Flowers", "flowers32.png", ImageCategory.PLANT);
		loadImage("Cactus", "cactus32.png", ImageCategory.PLANT);
		loadImage("Bush", "bush32.png", ImageCategory.PLANT);
	}


	public void loadBiomes() {
		loadImage("Ocean", "ocean32.png", ImageCategory.BIOME);
		loadImage("Grassland", "grassland32.png", ImageCategory.BIOME);
		loadImage("Desert", "desert32.png", ImageCategory.BIOME);
		loadImage("Beach", "beach32.png", ImageCategory.BIOME);
		loadImage("Forest", "forest32.png", ImageCategory.BIOME);
		loadImage("Path", "path32.png", ImageCategory.BIOME);
		loadImage("River", "river32.png", ImageCategory.BIOME);
		loadImage("Lake", "lake32.png", ImageCategory.BIOME);
	}
	
	public void loadOthers() {
		loadImage("New Map", "newMap32.png", ImageCategory.OTHER); 			// 0
		loadImage("Save Map", "saveMap32.png", ImageCategory.OTHER); 		// 1
		loadImage("Load Map", "loadMap32.png", ImageCategory.OTHER); 		// 2
		loadImage("Reload Textures", "reloadTextures32.png", ImageCategory.OTHER); 	// 3
		loadImage("Edit Mode", "edit32.png", ImageCategory.OTHER); 			// 4
		loadImage("Move Mode", "move32.png", ImageCategory.OTHER); 			// 5
		loadImage("Minimap", "minimap32.png", ImageCategory.OTHER);	// 6
		
		loadImage("Fields", "fields32.png", ImageCategory.OTHER); 			// 7
		loadImage("Plants", "plants32.png", ImageCategory.OTHER);		// 8
		loadImage("Buildings", "buildings32.png", ImageCategory.OTHER);	// 9
		loadImage("Biomes", "biomes32.png", ImageCategory.OTHER);		// 12
		loadImage("Technical", "technicals32.png", ImageCategory.OTHER);	// 11
	}
	
	
}
