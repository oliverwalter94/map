package main;

import Data.DataHandler;
import MapGen.Biome;
import MapGen.Tile;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Menu {
    public int lineThickness;
    Color backgroundColor;
    Color backgroundSelected;
    Color fontColor;
    Color line;
    Color text;
    Font font;

    Dimension size;

    Tab Tabs[];
    int activeTabs;
    int selectedTabIndex;

    ArrayList<MenuItem> toolbarItems;
    boolean sidebarVisible;
    boolean infobarVisible;

    //Render Variables
    boolean calcRender;
    int tabsHeight;
    int tabSize;
    int tabsPerRow;
    int sideBarWidth;
    int infoBarWidth;

    //InfoBar Stuff

    Font infoBarFont;

    int distinctTiles;
    HashMap<Tile, Integer> ground;
    HashMap<Tile, Integer> plants;
    HashMap<Biome, Integer> biomes;

    DataHandler data;


    Menu() {
        this.data = Board.dataHandler;

        fontColor = Color.lightGray;
        backgroundColor = new Color(21, 21, 21, 200);
        backgroundSelected = new Color(89, 89, 89, 190);
        line = new Color(147, 147, 147, 200);
        text = new Color(255, 255, 255, 190);
        font = new Font("Arial", Font.PLAIN, 15);
        infoBarFont = new Font("Arial", Font.PLAIN, 12);

        Tabs = new Tab[10];
        toolbarItems = new ArrayList<>();

        //Tabs
        selectedTabIndex = 0;
        activeTabs = 0;

        // Render Vars
        calcRender = true;
        tabSize = 40;
        tabsPerRow = 5;
        sideBarWidth = tabSize * tabsPerRow;
        infoBarWidth = 200;
        lineThickness = 1;

        sidebarVisible = true;
        infobarVisible = true;

        //InfoBar
        distinctTiles = 3;
        ground = new HashMap<>();
        plants = new HashMap<>();
        biomes = new HashMap<>();

        fillToolbar();
        addTabs();
        fillTabs();

    }

    private void fillToolbar() {
        MenuItem item = new MenuItem("Edit Mode", data.getImageByName("Edit Mode"));

        toolbarItems.add(new MenuItem("Edit Mode", data.getImageByName("Edit Mode"), true));
        toolbarItems.add(new MenuItem("Move Mode", data.getImageByName("Move Mode")));
        toolbarItems.add(new MenuItem("New Map", data.getImageByName("New Map")));
        toolbarItems.add(new MenuItem("Save Map", data.getImageByName("Save Map")));
        toolbarItems.add(new MenuItem("Load Map", data.getImageByName("Load Map")));
        toolbarItems.add(new MenuItem("Close Map", data.getImageByName("Close Map")));
        toolbarItems.add(new MenuItem("Show Grid", data.getImageByName("Show Grid")));
        toolbarItems.add(new MenuItem("Reload Textures", data.getImageByName("Reload Textures")));
        toolbarItems.add(new MenuItem("Minimap", data.getImageByName("Minimap")));
        toolbarItems.add(new MenuItem("ZoomIn", data.getImageByName("ZoomIn")));
        toolbarItems.add(new MenuItem("ZoomOut", data.getImageByName("ZoomOut")));
        toolbarItems.add(new MenuItem("Imagepicker", data.getImageByName("Imagepicker")));
        toolbarItems.add(new MenuItem("InfoBar", data.getImageByName("Sidebar")));
    }

    private void addTabs() {
        this.addTab("Fields", data.getImageByName("Fields"), 50);
        this.addTab("Plants", data.getImageByName("Plants"), 50);
        this.addTab("Buildings", data.getImageByName("Buildings"), 50);
        this.addTab("Technical", data.getImageByName("Technical"), 50);
        //his.addTab("NPC", data.getImageByName("NPC"), 50);
        //this.addTab("Items", data.getImageByName("Items"), 50);
    }

    private void fillTabs() {

        for (Tile t : data.fields) {
            this.Tabs[0].menuItems.add(new MenuItem(t.name, t.imageObject.img));
        }
        for (Tile t : data.plants) {
            this.Tabs[1].menuItems.add(new MenuItem(t.name, t.imageObject.img));
        }
    }

    void tabClicked(Point pos, boolean lmb) {

        int tabIndex;
        tabIndex = (pos.x / tabSize) + tabsPerRow * (pos.y / tabSize);
        if (tabIndex < activeTabs) {
            selectedTabIndex = tabIndex;
        }


    }

    void tabContentClicked(Point pos, boolean lmb) {
        Tab tab = Tabs[selectedTabIndex];
        pos.y -= (((int) Math.ceil(activeTabs / (double) tabsPerRow)) * tabSize);
        int i = pos.y / tab.itemHeight;
        if (i < tab.menuItems.size()) {
            for (MenuItem item : tab.menuItems)
                item.active = false;
            tab.menuItems.get(i).active = true;
            tab.selected = i;
        }


    }

    void toolbarClicked(Point pos, boolean lmb) {

        if (pos.x <= 200 + toolbarItems.size() * tabSize) {
            int index = ((pos.x - 200) - (pos.x - 200) % tabSize) / tabSize;
            switch (toolbarItems.get(index).name) {
                case "Edit Mode": {
                    Board.editorState = Board.EditorState.EDIT;
                    toolbarItems.get(0).active = true;
                    toolbarItems.get(1).active = false;
                    toolbarItems.get(12).active = false;
                    sidebarVisible = true;
                    Board.addInfoMessage("Mode: Edit");
                    break;
                }
                case "Move Mode": {
                    Board.editorState = Board.EditorState.MOVE;
                    toolbarItems.get(0).active = false;
                    toolbarItems.get(1).active = true;
                    toolbarItems.get(12).active = false;
                    sidebarVisible = false;
                    Board.addInfoMessage("Mode: Move");
                    break;
                }
                case "New Map": {
                    Board.mapHandler.newMap();
                    Board.addInfoMessage("Generated new Map");
                    Board.gameRenderer.renderMode = GameRender.RenderMode.MAPEDIT;
                    break;
                }
                case "Save Map": {
                    // TODO implement new saving mechanism
                    break;
                }
                case "Load Map": {
                    // TODO implement new loading mechanism
                    Board.addInfoMessage("LOADING MAPS NOT IMPLEMENTED YET");
                    break;
                }
                case "ZoomIn": {
                    if (Board.mapHandler.tileSize < 32) {
                        Board.mapHandler.tileSpacing += Board.mapHandler.tileSize;
                        Board.mapHandler.tileSize *= 2;
                        Board.mapHandler.calcNeededChunks();
                    }
                    break;
                }
                case "ZoomOut": {
                    if (Board.mapHandler.tileSize > 4) {
                        Board.mapHandler.tileSize /= 2;
                        Board.mapHandler.tileSpacing -= Board.mapHandler.tileSize;
                        Board.mapHandler.calcNeededChunks();
                    }
                    break;
                }
                case "Close Map": {
                    Board.mapHandler.clearMap();
                    Board.addInfoMessage("Map closed");
                    break;
                }
                case "Reload Textures": {
                    // TODO implement reload textures mechanism
                    Board.addInfoMessage("RELOADING TEXTURES NOT IMPLEMENTED YET");
                    break;
                }
                case "Show Grid": {
                    toolbarItems.get(6).active ^= true;
                    if (Board.mapHandler.tileSpacing == Board.mapHandler.tileSize) {
                        Board.mapHandler.tileSpacing += 1;
                        Board.mapHandler.showGrid = true;
                    } else {
                        Board.mapHandler.tileSpacing -= 1;
                        Board.mapHandler.showGrid = false;
                    }
                    Board.mapHandler.calcNeededChunks();
                    Board.addInfoMessage("Grid " + Board.mapHandler.showGrid);
                    break;
                }
                case "Minimap": {
                    if (Board.mapHandler.mapOpen) {
//                        if (Map.miniMap) {
//                            Map.miniMap = false;
//                            main.Board.addInfoMessage(new Message("Minimap off", Message.Type.INFO));
//                        } else {
//                            Map.miniMap = true;
//                            main.Board.addInfoMessage(new Message("Minimap on", Message.Type.INFO));
//                        }
//                        Map.mapChange = true;
                        toolbarItems.get(8).active = !toolbarItems.get(8).active;
                    }
                    break;
                }
                case "Imagepicker": {
                    if (Board.gameRenderer.renderMode != GameRender.RenderMode.IMAGEPICKER) {
                        Board.gameRenderer.renderMode = GameRender.RenderMode.IMAGEPICKER;
                        sidebarVisible = false;
                        Board.editorState = Board.EditorState.IMAGEPICKER;
                    } else if (Board.mapHandler.mapOpen) {
                        Board.gameRenderer.renderMode = GameRender.RenderMode.MAPEDIT;
                        if (toolbarItems.get(0).active) {
                            Board.editorState = Board.EditorState.EDIT;
                            sidebarVisible = true;
                        } else
                            Board.editorState = Board.EditorState.MOVE;
                    }
                    break;
                }
                case "InfoBar": {
                    infobarVisible = !infobarVisible;

                    if (infobarVisible) {
                        toolbarItems.get(12).active = true;
                        toolbarItems.get(0).active = false;
                        toolbarItems.get(1).active = false;
                        Board.editorState = Board.EditorState.INFOBAR;
                        Board.addInfoMessage("Mode: Info");
                    } else {
                        toolbarItems.get(12).active = false;
                        toolbarItems.get(0).active = true;
                        Board.addInfoMessage("Mode: Edit");
                        Board.editorState = Board.EditorState.EDIT;
                        sidebarVisible = true;
                    }
                    break;
                }
            }
        }

    }


    private void addTab(String name, Image img, int tabHeight) {
        Tabs[activeTabs] = new Tab(name, img, tabHeight);
        activeTabs++;
    }

    ///Class Definition of Tabs --> Used for Categories

    public class Tab {
        String name;
        Image img;
        int itemHeight;
        ArrayList<MenuItem> menuItems;
        int selected = 0;

        Tab(String Name, Image Img, int ItemHeight) {
            name = Name;
            img = Img;
            itemHeight = ItemHeight;
            menuItems = new ArrayList<>();
        }

    }

    ///Class Definition of MenuItems --> Used within Tabs

    class MenuItem {
        String name;
        Image img;
        boolean active;

        Runnable onClick() {
            return null;
        }


        MenuItem(String Name, Image Img) {
            name = Name;
            img = Img;
            active = false;
        }

        public MenuItem(String Name, Image Img, boolean Active) {
            name = Name;
            img = Img;
            active = Active;
        }


        public MenuItem(String Name, Image Img, boolean Active, Runnable onClick) {
            name = Name;
            img = Img;
            active = Active;
        }

    }

}
