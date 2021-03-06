package Data;

import Entities.Item;
import Entities.NPC;
import MapGen.Biome;
import MapGen.Structure;
import MapGen.Tile;
import MapGen.TileWeight;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class DataHandler {


    public ArrayList<ImageObject> images;

    public ArrayList<Tile> fields;
    public ArrayList<Tile> plants;
    public ArrayList<Tile> buildings;
    public ArrayList<Structure> structures;
    public ArrayList<Biome> biomes;

    public ArrayList<Item> items;
    public ArrayList<NPC> npcs;

    String PATH = System.getProperty("user.home") + "/2D Game/";


    public DataHandler() {
        images = new ArrayList<>();
        fields = new ArrayList<>();
        plants = new ArrayList<>();
        buildings = new ArrayList<>();
        biomes = new ArrayList<>();
        structures = new ArrayList<>();
        items = new ArrayList<>();
        npcs = new ArrayList<>();

        readJSONs();
    }

    public int getImageIdByTile(Tile t) {
        return images.indexOf(t.imageObject);
    }

    public Image getImageByName(String name) {
        for (ImageObject imob : images) {
            if (imob.name.equals(name))
                return imob.img;
        }
        System.out.println("No image is loaded for: " + name);
        //TODO: Add live message
        return null;
    }

    public ImageObject getImageObjByName(String name) {
        for (ImageObject imob : images) {
            if (imob.name.equals(name))
                return imob;
        }
        return null;
    }

    public int getBiomeIdByName(String name) {
        for (Biome b : biomes)
            if (b.name.equals(name)) return biomes.indexOf(b);
        return -1;
    }

    public int getTileIdByName(String name) {
        for (Tile t : fields) {
            if (t.name.equals(name))
                return fields.indexOf(t);
        }
        for (Tile t : plants) {
            if (t.name.equals(name))
                return plants.indexOf(t);
        }
        return -1;
    }

    public void addTile(JsonObject obj) {
        String name = obj.getAsJsonPrimitive("name").getAsString();
        ImageObject imgObj = getImageObjByName(obj.getAsJsonPrimitive("imageName").getAsString());
        String type = obj.getAsJsonPrimitive("type").getAsString();
        Tile tile = new Tile(name, imgObj);
        switch (type) {
            case "FIELD":
                fields.add(tile);
                break;
            case "PLANT":
                plants.add(tile);
                break;
            case "BUILDING":
                buildings.add(tile);
                break;

        }
    }

    public void addImage(JsonObject obj) {

        ImageObject img = new ImageObject();

        ImageIcon icon = null;
        try {
            File f = new File(PATH + "Textures/" + obj.getAsJsonPrimitive("fileName").getAsString());
            if (f.exists())
                icon = new ImageIcon(f.getPath());

            img.name = obj.get("name").getAsString();
            img.img = icon.getImage();
            img.transparent = obj.getAsJsonPrimitive("transparent").getAsBoolean();
            img.resolution = obj.getAsJsonPrimitive("resolution").getAsInt();

            images.add(img);
        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(null, "Could not load Data.ImageObject: " + img.name, "InfoBox: " + "ERROR - Data.ImageObject", JOptionPane.INFORMATION_MESSAGE);
        }

    }

    public void addBiome(JsonObject obj) {

        String name = obj.getAsJsonPrimitive("name").getAsString();
        Boolean natGen = obj.getAsJsonPrimitive("naturallyGenerated").getAsBoolean();

        ArrayList<TileWeight> biomeFields = calcFieldWeights(obj.getAsJsonArray("fields"));
        ArrayList<TileWeight> biomePlants = null;
        if (obj.getAsJsonArray("plants") != null)
            biomePlants = calcPlantWeights(obj.getAsJsonArray("plants"));


        biomes.add(new Biome(name, biomeFields, biomePlants, natGen));


    }

    private ArrayList<TileWeight> calcFieldWeights(JsonArray jsonArray) {
        ArrayList<TileWeight> tileWeights = new ArrayList<TileWeight>();

        for (JsonElement jE : jsonArray) {
            JsonObject jO = jE.getAsJsonObject();

            //FOR DEBUGING --> to find referenced Tiles which do not exist... :/
//            String n = jO.getAsJsonPrimitive("fieldName").getAsString();
//            int id = getTileIdByName(n);
//            System.out.println(id + " " + n);

            Tile field = fields.get(getTileIdByName(jO.getAsJsonPrimitive("fieldName").getAsString()));
            float weight = jO.getAsJsonPrimitive("weight").getAsFloat();

            tileWeights.add(new TileWeight(field, weight));
        }
        return tileWeights;
    }

    private ArrayList<TileWeight> calcPlantWeights(JsonArray jsonArray) {
        ArrayList<TileWeight> tileWeights = new ArrayList<>();

        for (JsonElement jE : jsonArray) {
            JsonObject jO = jE.getAsJsonObject();

            Tile plant = plants.get(getTileIdByName(jO.getAsJsonPrimitive("plantName").getAsString()));
            float weight = jO.getAsJsonPrimitive("weight").getAsFloat();
            TileWeight plantWeight = new TileWeight(plant, weight);
            ArrayList<Tile> allowedFields = new ArrayList<>();

            JsonArray allowed = jO.getAsJsonArray("allowedFields");
            for (JsonElement element : allowed) {
                allowedFields.add(fields.get(getTileIdByName(element.getAsString())));
            }

            plantWeight.requiredTile = allowedFields;

            tileWeights.add(plantWeight);
        }
        return tileWeights;
    }


    public boolean readJSONs() {

        JsonParser parser = new JsonParser();

        //Images
        try {
            String contents = new String(Files.readAllBytes(Paths.get(PATH + "Data/" + "images.json")));
            JsonElement jsonTree = parser.parse(contents);

            if (jsonTree.isJsonArray()) {
                JsonArray jsonObject = jsonTree.getAsJsonArray();
                for (JsonElement je : jsonObject)
                    addImage(je.getAsJsonObject());

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Tiles
        try {
            String contents = new String(Files.readAllBytes(Paths.get(PATH + "Data/" + "tiles.json")));

            JsonElement jsonTree = parser.parse(contents);

            if (jsonTree.isJsonArray()) {
                JsonArray jsonObject = jsonTree.getAsJsonArray();
                for (JsonElement je : jsonObject)
                    addTile(je.getAsJsonObject());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


        // Biomes
        try {
            String contents = new String(Files.readAllBytes(Paths.get(PATH + "Data/" + "biomes.json")));

            JsonElement jsonTree = parser.parse(contents);

            if (jsonTree.isJsonArray()) {
                JsonArray jsonArray = jsonTree.getAsJsonArray();
                for (JsonElement je : jsonArray)
                    addBiome(je.getAsJsonObject());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }

}
