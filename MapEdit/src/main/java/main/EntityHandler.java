package main;

import Data.ImageObject;
import Entities.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class EntityHandler {

    private static Random R = new Random();
    int UUID_counter = 0;

    private ArrayList<Player> players;
    private ArrayList<WorldItem> worldItems;
    private ArrayList<Item> items;
    ArrayList<ItemClass> itemClasses;
    ArrayList<NPC> npcs;

    Point spawnpoint = new Point(10, 10);

    int spawnradius = 3;


    public EntityHandler() {

        players = new ArrayList<>();
        items = new ArrayList<>();
        itemClasses = new ArrayList<>();
        npcs = new ArrayList<>();
        worldItems = new ArrayList<>();
    }

    private Point getSpawnPoint(Point spawn, int radius) {
        spawn.x = spawn.x + (R.nextInt(radius * 2 + 1) - radius);
        spawn.y = spawn.y + (R.nextInt(radius * 2 + 1) - radius);
        return spawn;
    }


    public void addNewPlayer(String name, Point position) {
        if (position == null)
            position = getSpawnPoint(spawnpoint, spawnradius);

        // TODO: Add Playerimage
        ImageObject playerImage = null;

        Player p = new Player(position, name, playerImage);
        p.renderTitle = true;

    }


}
