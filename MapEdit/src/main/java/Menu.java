import MapGen.DataHandler;
import MapGen.Tile;

import java.awt.*;
import java.util.ArrayList;

public class Menu {
	Color background;
	Color backgroundSelected;
	Color foreground;
	Color line;
	int tabHeight;
	
	Dimension size;
	
	public Tab Tabs[];
	int activeTabs;
	int selectedTabIndex;
	
	public ArrayList<MenuItem> toolbarItems;
	
	//Render Variables
	boolean calcRender;
	int tabsHeight;
	int toolbarFilledWidth;
	int tabSize;
	int tabsPerRow;
	
	DataHandler data;


	public Menu() {
		this.data = Board.dataHandler;
		
		foreground = Color.lightGray;
		background = new Color(21,21,21, 166);
		backgroundSelected = new Color(89, 89, 89, 190);
		line = Color.gray;
		
		Tabs = new Tab[10];
		toolbarItems = new ArrayList<MenuItem>();
		
		//Tabs
		selectedTabIndex = 0;
		activeTabs = 0;
		
		// Render Vars
		calcRender = true;
		tabsHeight = 0;
		tabSize = 40;
		tabsPerRow = 5;
		
		fillToolbar();
		
	}
	
	private void fillToolbar() {
		toolbarItems.add(new MenuItem("Edit Mode", data.getImageByName("Edit Mode"), false));
		toolbarItems.add(new MenuItem("Move Mode", data.getImageByName("Move Mode"), false));
		toolbarItems.add(new MenuItem("New Map", data.getImageByName("New Map"), false));
		toolbarItems.add(new MenuItem("Save Map", data.getImageByName("Save Map"), false));
		toolbarItems.add(new MenuItem("Load Map", data.getImageByName("Load Map"), false));
		toolbarItems.add(new MenuItem("Reload Textures", data.getImageByName("Reload Textures"), false));
		toolbarItems.add(new MenuItem("Minimap", data.getImageByName("Minimap"), false));
	}

	private void fillTabs() {

		for (Tile t:data.fields) {
			this.Tabs[0].menuItems.add(new MenuItem(t.name, t.imgObj.img, false));
		}
	}

	public void tabClicked(Point pos, boolean lmb) {
		
		int tabIndex = 0;
		tabIndex = ((int)pos.x/tabSize)+tabsPerRow*(int)(pos.y/tabSize);
		if(tabIndex < activeTabs) {
			selectedTabIndex = tabIndex;
		}
			
	}
	
	public void tabContentClicked(Point pos, boolean lmb) {

		System.out.println("clicked tab content" + pos.x + ":" + pos.y + " " + lmb);
	}
	
	public void toolbarClicked(Point pos, boolean lmb, MapGenerator mapGen) {
		
		if(pos.x <= 200 + toolbarItems.size()*tabSize) {
			int index = ((pos.x - 200)-(pos.x - 200)%tabSize) / tabSize;
			switch(index) {
			case 0:{
				Board.editorState = Board.EditorState.EDIT;
				toolbarItems.get(0).active = true;
				toolbarItems.get(1).active = false;
				break;
				}
			case 1:{
				Board.editorState = Board.EditorState.MOVE;
				toolbarItems.get(0).active = false;
				toolbarItems.get(1).active = true;
				break;
				}
			case 2:{
				mapGen.generateNewMap();
				GameRender.screenx = 0;
				GameRender.screeny = 0;
				Board.MapOpen = true;
				Map.mapChange = true;
				break;
				}
			case 3:{
				// TODO implement new saving mechanism
				break;
				}
			case 4:{
				// TODO implement new loading mechanism
				break;
				}
			case 5:{
				// TODO implement reload textures mechanism
				break;
				}
			case 6:{
				if (Board.MapOpen){
					if (Map.miniMap)
							Map.miniMap = false;
					else 
						Map.miniMap = true;
					Map.mapChange = true;
					toolbarItems.get(6).active = !toolbarItems.get(6).active;
					}
				break;
				}
			}
		}
		
	}
	
	
	
	public void addTab(String name, Image img, int tabHeight) {
		Tabs[activeTabs] = new Tab(name, img, tabHeight);
		activeTabs++;
	}
	
	///Class Definition of Tabs --> Used for Categories
	
	public class Tab {
		String name;
		Image img;
		int itemHeight;
		ArrayList<MenuItem> menuItems;
		
		public Tab(String Name, Image Img, int ItemHeight) {
			name = Name;
			img = Img;
			itemHeight = ItemHeight;
			menuItems = new ArrayList<MenuItem>();  
		}
		
	}
	
	///Class Definition of MenuItems --> Used within Tabs
	
	 public class MenuItem  {
		String name;
		Image img;
		boolean active;
		
		public MenuItem(String Name, Image Img) {
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
