package MapGen;


import com.google.gson.*;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class DataHandler {



	public ArrayList<ImageObj> images;

	public ArrayList<Tile> fields;
	public ArrayList<Tile> plants;
	public ArrayList<Tile> buildings;

	public ArrayList<Biome> biomes;

    String PATH = System.getProperty("user.home") + "/2D Game/Textures/";

	
	public DataHandler() {
		images = new ArrayList<ImageObj>();
		fields = new ArrayList<Tile>();
		plants = new ArrayList<Tile>();
		buildings = new ArrayList<Tile>();
		biomes = new ArrayList<Biome>();

		readJSON();
	}
	
	public int getImageIdByTile(Tile t) {
		return images.indexOf(t.imgObj);
	}
	
	public Image getImageByName(String name) {
		for (ImageObj imob:images) {
			if (imob.name.equals(name)) {
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
	
	public void addImage(JsonObject obj) {

        ImageObj img = new ImageObj();

        ImageIcon icon = null;
        File f = new File(PATH + obj.getAsJsonPrimitive("fileName").getAsString());
        if(f.exists())
            icon = new ImageIcon(f.getPath());

		img.name = obj.get("name").getAsString();
        img.img = icon.getImage();
        img.transparent = obj.getAsJsonPrimitive("transparent").getAsBoolean();
        img.resolution = obj.getAsJsonPrimitive("resolution").getAsInt();

        images.add(img);
	}

	public void readBiomes() {
		
	}
	
	public void readTiles() {
		
	}

	public boolean readJSON(){
		String s = "[\n" +
                "\t{\n" +
                "\t\t\"name\" : \"Sea Water\",\n" +
                "\t\t\"fileName\" : \"seawater32.png\",\n" +
                "\t\t\"transparent\" : false,\n" +
                "\t\t\"resolution\" : 32\n" +
                "\t},\n" +
                "\t{\n" +
                "\t\t\"name\" : \"Fresh Water\",\n" +
                "\t\t\"fileName\" : \"freshwater32.png\",\n" +
                "\t\t\"transparent\" : false,\n" +
                "\t\t\"resolution\" : 32\n" +
                "\t},\n" +
                "\t{\n" +
                "\t\t\"name\" : \"Flowing Water\",\n" +
                "\t\t\"fileName\" : \"flowingwater32.png\",\n" +
                "\t\t\"transparent\" : false,\n" +
                "\t\t\"resolution\" : 32\n" +
                "\t},\n" +
                "\t{\n" +
                "\t\t\"name\" : \"Grass\",\n" +
                "\t\t\"fileName\" : \"grass32.png\",\n" +
                "\t\t\"transparent\" : false,\n" +
                "\t\t\"resolution\" : 32\n" +
                "\t},\n" +
                "\t{\n" +
                "\t\t\"name\" : \"Sand\",\n" +
                "\t\t\"fileName\" : \"sand32.png\",\n" +
                "\t\t\"transparent\" : false,\n" +
                "\t\t\"resolution\" : 32\n" +
                "\t},\n" +
                "\t{\n" +
                "\t\t\"name\" : \"Gravel\",\n" +
                "\t\t\"fileName\" : \"gravel32.png\",\n" +
                "\t\t\"transparent\" : false,\n" +
                "\t\t\"resolution\" : 32\n" +
                "\t},\n" +
                "\t{\n" +
                "\t\t\"name\" : \"Farmland\",\n" +
                "\t\t\"fileName\" : \"farmland32.png\",\n" +
                "\t\t\"transparent\" : false,\n" +
                "\t\t\"resolution\" : 32\n" +
                "\t},\n" +
                "\t{\n" +
                "\t\t\"name\" : \"Tree\",\n" +
                "\t\t\"fileName\" : \"tree32.png\",\n" +
                "\t\t\"transparent\" : true,\n" +
                "\t\t\"resolution\" : 32\n" +
                "\t},\n" +
                "\t{\n" +
                "\t\t\"name\" : \"Fir\",\n" +
                "\t\t\"fileName\" : \"fir32.png\",\n" +
                "\t\t\"transparent\" : true,\n" +
                "\t\t\"resolution\" : 32\n" +
                "\t},\n" +
                "\t{\n" +
                "\t\t\"name\" : \"Flowers\",\n" +
                "\t\t\"fileName\" : \"flowers32.png\",\n" +
                "\t\t\"transparent\" : true,\n" +
                "\t\t\"resolution\" : 32\n" +
                "\t},\n" +
                "\t{\n" +
                "\t\t\"name\" : \"Cactus\",\n" +
                "\t\t\"fileName\" : \"cactus32.png\",\n" +
                "\t\t\"transparent\" : true,\n" +
                "\t\t\"resolution\" : 32\n" +
                "\t},\n" +
                "\t{\n" +
                "\t\t\"name\" : \"Bush\",\n" +
                "\t\t\"fileName\" : \"bush32.png\",\n" +
                "\t\t\"transparent\" : true,\n" +
                "\t\t\"resolution\" : 32\n" +
                "\t},\n" +
                "\t{\n" +
                "\t\t\"name\" : \"Ocean\",\n" +
                "\t\t\"fileName\" : \"ocean32.png\",\n" +
                "\t\t\"transparent\" : false,\n" +
                "\t\t\"resolution\" : 32\n" +
                "\t},\n" +
                "\t{\n" +
                "\t\t\"name\" : \"Grassland\",\n" +
                "\t\t\"fileName\" : \"grassland32.png\",\n" +
                "\t\t\"transparent\" : false,\n" +
                "\t\t\"resolution\" : 32\n" +
                "\t},\n" +
                "\t{\n" +
                "\t\t\"name\" : \"Desert\",\n" +
                "\t\t\"fileName\" : \"desert32.png\",\n" +
                "\t\t\"transparent\" : false,\n" +
                "\t\t\"resolution\" : 32\n" +
                "\t},\n" +
                "\t{\n" +
                "\t\t\"name\" : \"Beach\",\n" +
                "\t\t\"fileName\" : \"beach32.png\",\n" +
                "\t\t\"transparent\" : false,\n" +
                "\t\t\"resolution\" : 32\n" +
                "\t},\n" +
                "\t{\n" +
                "\t\t\"name\" : \"Forest\",\n" +
                "\t\t\"fileName\" : \"forest32.png\",\n" +
                "\t\t\"transparent\" : false,\n" +
                "\t\t\"resolution\" : 32\n" +
                "\t},\n" +
                "\t{\n" +
                "\t\t\"name\" : \"Path\",\n" +
                "\t\t\"fileName\" : \"path32.png\",\n" +
                "\t\t\"transparent\" : false,\n" +
                "\t\t\"resolution\" : 32\n" +
                "\t},\n" +
                "\t{\n" +
                "\t\t\"name\" : \"River\",\n" +
                "\t\t\"fileName\" : \"river32.png\",\n" +
                "\t\t\"transparent\" : false,\n" +
                "\t\t\"resolution\" : 32\n" +
                "\t},\n" +
                "\t{\n" +
                "\t\t\"name\" : \"Lake\",\n" +
                "\t\t\"fileName\" : \"lake32.png\",\n" +
                "\t\t\"transparent\" : false,\n" +
                "\t\t\"resolution\" : 32\n" +
                "\t},\n" +
                "\t{\n" +
                "\t\t\"name\" : \"New Map\",\n" +
                "\t\t\"fileName\" : \"newMap32.png\",\n" +
                "\t\t\"transparent\" : false,\n" +
                "\t\t\"resolution\" : 32\n" +
                "\t},\n" +
                "\t{\n" +
                "\t\t\"name\" : \"Save Map\",\n" +
                "\t\t\"fileName\" : \"saveMap32.png\",\n" +
                "\t\t\"transparent\" : false,\n" +
                "\t\t\"resolution\" : 32\n" +
                "\t},\n" +
                "\t{\n" +
                "\t\t\"name\" : \"Load Map\",\n" +
                "\t\t\"fileName\" : \"loadMap32.png\",\n" +
                "\t\t\"transparent\" : false,\n" +
                "\t\t\"resolution\" : 32\n" +
                "\t},\n" +
                "\t{\n" +
                "\t\t\"name\" : \"Reload Textures\",\n" +
                "\t\t\"fileName\" : \"reloadTextures32.png\",\n" +
                "\t\t\"transparent\" : false,\n" +
                "\t\t\"resolution\" : 32\n" +
                "\t},\n" +
                "\t{\n" +
                "\t\t\"name\" : \"Edit Mode\",\n" +
                "\t\t\"fileName\" : \"edit32.png\",\n" +
                "\t\t\"transparent\" : false,\n" +
                "\t\t\"resolution\" : 32\n" +
                "\t},\n" +
                "\t{\n" +
                "\t\t\"name\" : \"Move Mode\",\n" +
                "\t\t\"fileName\" : \"move32.png\",\n" +
                "\t\t\"transparent\" : false,\n" +
                "\t\t\"resolution\" : 32\n" +
                "\t},\n" +
                "\t{\n" +
                "\t\t\"name\" : \"Minimap\",\n" +
                "\t\t\"fileName\" : \"minimap32.png\",\n" +
                "\t\t\"transparent\" : false,\n" +
                "\t\t\"resolution\" : 32\n" +
                "\t},\n" +
                "\t{\n" +
                "\t\t\"name\" : \"Fields\",\n" +
                "\t\t\"fileName\" : \"fields32.png\",\n" +
                "\t\t\"transparent\" : false,\n" +
                "\t\t\"resolution\" : 32\n" +
                "\t},\n" +
                "\t{\n" +
                "\t\t\"name\" : \"Plants\",\n" +
                "\t\t\"fileName\" : \"plants32.png\",\n" +
                "\t\t\"transparent\" : false,\n" +
                "\t\t\"resolution\" : 32\n" +
                "\t},\n" +
                "\t{\n" +
                "\t\t\"name\" : \"Buildings\",\n" +
                "\t\t\"fileName\" : \"buildings32.png\",\n" +
                "\t\t\"transparent\" : false,\n" +
                "\t\t\"resolution\" : 32\n" +
                "\t},\n" +
                "\t{\n" +
                "\t\t\"name\" : \"Biomes\",\n" +
                "\t\t\"fileName\" : \"biomes32.png\",\n" +
                "\t\t\"transparent\" : false,\n" +
                "\t\t\"resolution\" : 32\n" +
                "\t},\n" +
                "\t{\n" +
                "\t\t\"name\" : \"Technical\",\n" +
                "\t\t\"fileName\" : \"technicals32.png\",\n" +
                "\t\t\"transparent\" : false,\n" +
                "\t\t\"resolution\" : 32\n" +
                "\t}\n" +
                "]";




        JsonParser parser = new JsonParser();

        JsonElement jsonTree = parser.parse(s);

        if (jsonTree.isJsonArray()){

            JsonArray jsonObject = jsonTree.getAsJsonArray();

            for (JsonElement je : jsonObject){
                addImage(je.getAsJsonObject());
            }
        }



		return true;
	}

	public class ImageObjCreator implements InstanceCreator<ImageObj>{
        public ImageObj createInstance(Type type){
            ImageObj imob = new ImageObj(null, null);
            imob.name = "Test";
            return null;
        }
    }

}
