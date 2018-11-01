import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.util.ArrayList;

import javax.swing.ImageIcon;

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
	
	ArrayList<MenuItem> toolbarItems;
	
	//Render Variables
	boolean calcRender;
	int tabsHeight;
	int toolbarFilledWidth;
	int tabSize;
	int tabsPerRow;
	
	Images images;
	
	
	public Menu(Images img) {
		images = img;
		
		foreground = Color.lightGray;
		background = new Color(21,21,21, 160);
		backgroundSelected = new Color(81,81,81, 200);
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
	
	public void tabClicked(Point pos, boolean lmb) {
		
		int tabIndex = 0;
		tabIndex = ((int)pos.x/tabSize)+tabsPerRow*((int)pos.y/tabSize);
		if(tabIndex < activeTabs) {
			selectedTabIndex = tabIndex;
		}
			
	}
	
	public void tabContentClicked(Point pos, boolean lmb) {

		System.out.println("clicked tab content" + pos.x + ":" + pos.y + " " + lmb);
	}
	
	public void toolbarClicked(Point pos, boolean lmb) {
		
		if(pos.x <= 200 + toolbarItems.size()*tabSize) {
			int index = ((pos.x - 200)-(pos.x - 200)%tabSize) / tabSize;
			switch(index) {
			case 0:{
				Board.editorstate = "EDIT";
				toolbarItems.get(0).active = true;
				toolbarItems.get(1).active = false;
				break;
				}
			case 1:{
				Board.editorstate = "MOVE";
				toolbarItems.get(0).active = false;
				toolbarItems.get(1).active = true;
				break;
				}
			case 2:{
				Board.mapGen.generateNewMap();
				MapRender.screenx = 0;
				MapRender.screeny = 0;
				Board.MapOpen = true;
				Map.mapChange = true;
				break;
				}
			case 3:{
				// TODO implement new saving mechanism
				}
			case 4:{
				// TODO implement new loading mechanism
				}
			case 5:{
				// TODO implement reload textures mechanism
				}
			case 6:{
				// TODO implement switching minimap mode mechanism
				if (Board.MapOpen){
					if (Map.miniMap)
							Map.miniMap = false;
					else 
						Map.miniMap = true;
					Map.mapChange = true;
					toolbarItems.get(6).active = !toolbarItems.get(6).active;
					}
				}
			}
		}
		
	}
	
	private void fillToolbar() {
		toolbarItems.add(new MenuItem("Edit Mode", images.Other.get(4).img, false));
		toolbarItems.add(new MenuItem("Move Mode", images.Other.get(5).img, false));
		toolbarItems.add(new MenuItem("New Map", images.Other.get(0).img, false));
		toolbarItems.add(new MenuItem("Save Map", images.Other.get(1).img, false));
		toolbarItems.add(new MenuItem("Load Map", images.Other.get(2).img, false));
		toolbarItems.add(new MenuItem("Reload Textures", images.Other.get(3).img, false));
		toolbarItems.add(new MenuItem("Minimap", images.Other.get(6).img, false));
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
