import java.awt.Image;
import java.io.File;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class Images {

	static File f;
	static ImageIcon[][] IdIcons = new ImageIcon[2][20];
	static Image[][] IdImages = new Image[1][20];
	static ImageIcon[][] BiomeIcons = new ImageIcon[2][10];
	static Image[][] BiomeImages = new Image[1][10];
	static ImageIcon[][] ExtraIcons = new ImageIcon[2][10];
	static Image[][] ExtraImages = new Image[1][10];
	static ImageIcon[][] OtherIcons = new ImageIcon[2][10];
	static Image[][] OtherImages = new Image[1][10];

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
		loadBuildings();
		loadBiomes();
		loadOthers();
		
		//loadBiomeImages();
		//loadExtraImages();
		//loadIDImages();
		//loadOtherImages();
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
	
	//TODO add name-finder for other Tile types
	
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
	
	public void loadBuildings() {
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
	
	
	
	
	
	
	public static void loadIDImages() {

		f = new File(TEX + "seawater32.png");
		if (f.exists())
			IdIcons[0][1] = new ImageIcon(TEX + "seawater32.png");
		else
			IdIcons[0][1] = new ImageIcon(InterfaceNew.class.getResource("/sources/Environment/seawater_1.png"));

		f = new File(TEX + "freshwater32.png");
		if (f.exists())
			IdIcons[0][2] = new ImageIcon(TEX + "freshwater32.png");
		else
			IdIcons[0][2] = new ImageIcon(InterfaceNew.class.getResource("/sources/Environment/freshwater_1.png"));

		f = new File(TEX + "floatingwater32.png");
		if (f.exists())
			IdIcons[0][3] = new ImageIcon(TEX + "floatingwater32.png");
		else
			IdIcons[0][3] = new ImageIcon(InterfaceNew.class.getResource("/sources/Environment/floatingwater_1.png"));

		f = new File(TEX + "grass32.png");
		if (f.exists())
			IdIcons[0][4] = new ImageIcon(TEX + "grass32.png");
		else
			IdIcons[0][4] = new ImageIcon(InterfaceNew.class.getResource("/sources/Environment/grass_01.png"));

		f = new File(TEX + "sand32.png");
		if (f.exists())
			IdIcons[0][5] = new ImageIcon(TEX + "sand32.png");
		else
			IdIcons[0][5] = new ImageIcon(InterfaceNew.class.getResource("/sources/Environment/sand_1.png"));

		f = new File(TEX + "gravel32.png");
		if (f.exists())
			IdIcons[0][6] = new ImageIcon(TEX + "gravel32.png");
		else
			IdIcons[0][6] = new ImageIcon(InterfaceNew.class.getResource("/sources/Environment/gravel_1.png"));

		f = new File(TEX + "farmland32.png");
		if (f.exists())
			IdIcons[0][7] = new ImageIcon(TEX + "farmland32.png");
		else
			IdIcons[0][7] = new ImageIcon(InterfaceNew.class.getResource("/sources/Environment/farmland_01.png"));

		for (int j = 1; j < 8; j++) {
			IdImages[0][j] = IdIcons[0][j].getImage();
		}

	}

	public static void loadBiomeImages() {

		f = new File(TEX + "ocean32.png");
		if (f.exists())
			BiomeIcons[0][1] = new ImageIcon(TEX + "ocean32.png");
		else
			BiomeIcons[0][1] = new ImageIcon(InterfaceNew.class.getResource("/sources/Environment/Ocean.png"));

		f = new File(TEX + "grassland32.png");
		if (f.exists())
			BiomeIcons[0][2] = new ImageIcon(TEX + "grassland32.png");
		else
			BiomeIcons[0][2] = new ImageIcon(InterfaceNew.class.getResource("/sources/Environment/grass_01.png"));

		f = new File(TEX + "desert32.png");
		if (f.exists())
			BiomeIcons[0][3] = new ImageIcon(TEX + "desert32.png");
		else
			BiomeIcons[0][3] = new ImageIcon(InterfaceNew.class.getResource("/sources/Environment/sand_1.png"));

		f = new File(TEX + "beach32.png");
		if (f.exists())
			BiomeIcons[0][4] = new ImageIcon(TEX + "beach32.png");
		else
			BiomeIcons[0][4] = new ImageIcon(InterfaceNew.class.getResource("/sources/Environment/Beach.png"));

		f = new File(TEX + "forest32.png");
		if (f.exists())
			BiomeIcons[0][5] = new ImageIcon(TEX + "forest32.png");
		else
			BiomeIcons[0][5] = new ImageIcon(InterfaceNew.class.getResource("/sources/Environment/Forrest.png"));

		f = new File(TEX + "path32.png");
		if (f.exists())
			BiomeIcons[0][6] = new ImageIcon(TEX + "path32.png");
		else
			BiomeIcons[0][6] = new ImageIcon(InterfaceNew.class.getResource("/sources/Environment/Path.png"));

		f = new File(TEX + "river32.png");
		if (f.exists())
			BiomeIcons[0][7] = new ImageIcon(TEX + "river32.png");
		else
			BiomeIcons[0][7] = new ImageIcon(InterfaceNew.class.getResource("/sources/Environment/River.png"));

		f = new File(TEX + "lake32.png");
		if (f.exists())
			BiomeIcons[0][8] = new ImageIcon(TEX + "lake32.png");
		else
			BiomeIcons[0][8] = new ImageIcon(InterfaceNew.class.getResource("/sources/Environment/Lake.png"));

		for (int j = 1; j < 9; j++) {
			BiomeImages[0][j] = BiomeIcons[0][j].getImage();
		}

	}

	public static void loadExtraImages() {

		f = new File(TEX + "empty32.png");
		if (f.exists())
			ExtraIcons[0][0] = new ImageIcon(TEX + "empty32.png");
		else
			ExtraIcons[0][0] = new ImageIcon(InterfaceNew.class.getResource("/sources/Environment/empty32.png"));

		f = new File(TEX + "tree32.png");
		if (f.exists())
			ExtraIcons[0][1] = new ImageIcon(TEX + "tree32.png");
		else
			ExtraIcons[0][1] = new ImageIcon(InterfaceNew.class.getResource("/sources/Environment/tree_1.png"));

		f = new File(TEX + "fir32.png");
		if (f.exists())
			ExtraIcons[0][2] = new ImageIcon(TEX + "fir32.png");
		else
			ExtraIcons[0][2] = new ImageIcon(InterfaceNew.class.getResource("/sources/Environment/fir.png"));

		f = new File(TEX + "flowers32.png");
		if (f.exists())
			ExtraIcons[0][3] = new ImageIcon(TEX + "flowers32.png");
		else
			ExtraIcons[0][3] = new ImageIcon(InterfaceNew.class.getResource("/sources/Environment/flowers.png"));

		f = new File(TEX + "cactus32.png");
		if (f.exists())
			ExtraIcons[0][4] = new ImageIcon(TEX + "cactus32.png");
		else
			ExtraIcons[0][4] = new ImageIcon(InterfaceNew.class.getResource("/sources/Environment/cactus.png"));

		f = new File(TEX + "bush32.png");
		if (f.exists())
			ExtraIcons[0][5] = new ImageIcon(TEX + "bush32.png");
		else
			ExtraIcons[0][5] = new ImageIcon(InterfaceNew.class.getResource("/sources/Environment/bush.png"));

		for (int j = 0; j < 6; j++) {
			ExtraImages[0][j] = ExtraIcons[0][j].getImage();
		}

		
	}

	public static void loadOtherImages() {
		f = new File(TEX + "reloadTex32.png");
		if (f.exists())
			OtherIcons[0][1] = new ImageIcon(TEX + "reloadTex32.png");
		else
			OtherIcons[0][1] = new ImageIcon(InterfaceNew.class.getResource("/sources/Environment/textures Load.png"));

		f = new File(TEX + "switchView32.png");
		if (f.exists())
			OtherIcons[0][2] = new ImageIcon(TEX + "switchView32.png");
		else
			OtherIcons[0][2] = new ImageIcon(InterfaceNew.class.getResource("/sources/Environment/textures Load.png"));

		f = new File(TEX + "newMap32.png");
		if (f.exists())
			OtherIcons[0][3] = new ImageIcon(TEX + "newMap32.png");
		else
			OtherIcons[0][3] = new ImageIcon(InterfaceNew.class.getResource("/sources/Environment/icon.png"));

		
		for (int j = 1; j < 4; j++) {
			OtherImages[0][j] = OtherIcons[0][j].getImage();
		}

	}
}
