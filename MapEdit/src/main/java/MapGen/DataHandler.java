package MapGen;


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


    public ArrayList<ImageObj> images;

    public ArrayList<Tile> fields;
    public ArrayList<Tile> plants;
    public ArrayList<Tile> buildings;

    public ArrayList<Biome> biomes;

    String PATH = System.getProperty("user.home") + "/2D Game/";


    public DataHandler() {
        images = new ArrayList<ImageObj>();
        fields = new ArrayList<Tile>();
        plants = new ArrayList<Tile>();
        buildings = new ArrayList<Tile>();
        biomes = new ArrayList<Biome>();

        readJSONs();
    }

    public int getImageIdByTile(Tile t) {
        return images.indexOf(t.imgObj);
    }

    public Image getImageByName(String name) {
        for (ImageObj imob : images) {
            if (imob.name.equals(name))
                return imob.img;
        }
        return null;
    }

    public ImageObj getImageObjByName(String name) {
        for (ImageObj imob : images) {
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
        ImageObj imgObj = getImageObjByName(obj.getAsJsonPrimitive("imageName").getAsString());
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

        ImageObj img = new ImageObj();

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
            JOptionPane.showMessageDialog(null, "Could not load ImageObject: " + img.name, "InfoBox: " + "ERROR - ImageObject", JOptionPane.INFORMATION_MESSAGE);
        }

    }

    public void addBiome(JsonObject obj) {

        String name = obj.getAsJsonPrimitive("name").getAsString();

        ArrayList<TileWeight> biomeFields = calcFieldWeights(obj.getAsJsonArray("fields"));
        ArrayList<TileWeight> biomePlants = null;
        if (obj.getAsJsonArray("plants") != null)
            biomePlants = calcPlantWeights(obj.getAsJsonArray("plants"));


        //TODO: Add naturallyGenerated field to reader
        biomes.add(new Biome(name, biomeFields, biomePlants, false));


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
