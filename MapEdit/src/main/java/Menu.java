import MapGen.DataHandler;
import MapGen.Tile;

import java.awt.*;
import java.util.ArrayList;

public class Menu {
    Color background;
    Color backgroundSelected;
    Color foreground;
    Color line;
    Color text;
    Font font;

    Dimension size;

    Tab Tabs[];
    int activeTabs;
    int selectedTabIndex;

    ArrayList<MenuItem> toolbarItems;
    boolean sidebarVisible;

    //Render Variables
    boolean calcRender;
    int tabsHeight;
    int tabSize;
    int tabsPerRow;
    int sideBarWidth;

    DataHandler data;


    Menu() {
        this.data = Board.dataHandler;

        foreground = Color.lightGray;
        background = new Color(21, 21, 21, 182);
        backgroundSelected = new Color(89, 89, 89, 190);
        line = Color.gray;
        text = new Color(255, 255, 255, 190);
        font = new Font("Arial", Font.PLAIN, 15);

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

        sidebarVisible = true;

        fillToolbar();
        addTabs();
        fillTabs();

    }

    private void fillToolbar() {
        toolbarItems.add(new MenuItem("Edit Mode", data.getImageByName("Edit Mode"), true));
        toolbarItems.add(new MenuItem("Move Mode", data.getImageByName("Move Mode")));
        toolbarItems.add(new MenuItem("New Map", data.getImageByName("New Map")));
        toolbarItems.add(new MenuItem("Save Map", data.getImageByName("Save Map")));
        toolbarItems.add(new MenuItem("Load Map", data.getImageByName("Load Map")));
        toolbarItems.add(new MenuItem("Reload Textures", data.getImageByName("Reload Textures")));
        toolbarItems.add(new MenuItem("Minimap", data.getImageByName("Minimap")));
    }

    private void addTabs() {
        this.addTab("Fields", data.getImageByName("Fields"), 50);
        this.addTab("Plants", data.getImageByName("Plants"), 50);
        this.addTab("Buildings", data.getImageByName("Buildings"), 50);
    }

    private void fillTabs() {

        for (Tile t : data.fields) {
            this.Tabs[0].menuItems.add(new MenuItem(t.name, t.imgObj.img));
        }
        for (Tile t : data.plants) {
            this.Tabs[1].menuItems.add(new MenuItem(t.name, t.imgObj.img));
        }
    }

    void tabClicked(Point pos, boolean lmb) {

        int tabIndex = 0;
        tabIndex = (pos.x / tabSize) + tabsPerRow * (pos.y / tabSize);
        if (tabIndex < activeTabs) {
            selectedTabIndex = tabIndex;
        }

    }

    void tabContentClicked(Point pos, boolean lmb) {
        Tab tab = Tabs[selectedTabIndex];
        pos.y -= (((int) Math.ceil(activeTabs / (double) tabsPerRow)) * tabSize);
        for (MenuItem item : tab.menuItems)
            item.active = false;
        int i = pos.y / tab.itemHeight;
        tab.menuItems.get(i).active = true;
        tab.selected = i;
    }

    void toolbarClicked(Point pos, boolean lmb, MapGenerator mapGen) {

        if (pos.x <= 200 + toolbarItems.size() * tabSize) {
            int index = ((pos.x - 200) - (pos.x - 200) % tabSize) / tabSize;
            switch (index) {
                case 0: {
                    Board.editorState = Board.EditorState.EDIT;
                    toolbarItems.get(0).active = true;
                    toolbarItems.get(1).active = false;
                    sidebarVisible = true;
                    Board.addInfoMessage(new Message("Edit - Mode", Message.Type.INFO));
                    break;
                }
                case 1: {
                    Board.editorState = Board.EditorState.MOVE;
                    toolbarItems.get(0).active = false;
                    toolbarItems.get(1).active = true;
                    sidebarVisible = false;
                    Board.addInfoMessage(new Message("Move - Mode", Message.Type.INFO));
                    break;
                }
                case 2: {
                    mapGen.generateNewPerlinMap();
                    GameRender.screenx = 0;
                    GameRender.screeny = 0;
                    Board.MapOpen = true;
                    Map.mapChange = true;
                    Board.addInfoMessage(new Message("Generated new Map", Message.Type.INFO));
                    break;
                }
                case 3: {
                    // TODO implement new saving mechanism
                    Board.addInfoMessage(new Message("NOT IMPLEMENTED YET", Message.Type.ERROR));
                    break;
                }
                case 4: {
                    // TODO implement new loading mechanism
                    Board.addInfoMessage(new Message("NOT IMPLEMENTED YET", Message.Type.ERROR));
                    break;
                }
                case 5: {
                    // TODO implement reload textures mechanism
                    Board.addInfoMessage(new Message("NOT IMPLEMENTED YET", Message.Type.ERROR));
                    break;
                }
                case 6: {
                    if (Board.MapOpen) {
                        if (Map.miniMap) {
                            Map.miniMap = false;
                            Board.addInfoMessage(new Message("Minimap off", Message.Type.INFO));
                        } else {
                            Map.miniMap = true;
                            Board.addInfoMessage(new Message("Minimap on", Message.Type.INFO));
                        }
                        Map.mapChange = true;
                        toolbarItems.get(6).active = !toolbarItems.get(6).active;
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
    }


}
