
import java.awt.*;
import MapGen.*;
import javax.swing.*;

import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class Board extends JPanel implements ActionListener{

	private static final long serialVersionUID = 1L;
	static boolean MapOpen = false;
	static int timestat = 0;
	public static Graphics2D g2d;
	static int plusHor = 0;
	static int plusVer = 0;
	Timer time;
	public static MapGenerator mapGen;
	public static Map[][] MAP = new Map[Map.map_xsize][Map.map_ysize];
	static boolean IDsel = false, Extrasel = false;
	static int selID,selEXTRA;
	static int x1,x2,y1,y2;
	static String editorstate = "EDIT";
	static String Mapstate = "ID";
	static int mx1,mx2,my1,my2;
	
	public static Menu menu;
	public Images images;
	public ArrayList<Biome> biomes;
	public ArrayList<Field> fields;
	public DataHandler data;
	
	public Board(){

		data = new DataHandler();
		
		images = new Images();
		images.loadAllImages();
		
		
		menu = new Menu(images);
		fields = new ArrayList<Field>();
		biomes = new ArrayList<Biome>();
		loadFields();
		loadBiomes();
		
		mapGen = new MapGenerator(biomes, fields);
		
		addMouseListener(new CD());
		setFocusable(true);
		time = new Timer(30,this);
		time.start();
		fillMenu();
	}
	
	private void loadFields() {
		fields.add(new Field("Sea Water",images.findFieldImageByName("Sea Water")));
		fields.add(new Field("Fresh Water",images.findFieldImageByName("Fresh Water")));
		fields.add(new Field("Floating Water",images.findFieldImageByName("Floating Water")));
		fields.add(new Field("Grass",images.findFieldImageByName("Grass")));
		fields.add(new Field("Sand",images.findFieldImageByName("Sand")));
		fields.add(new Field("Gravel",images.findFieldImageByName("Gravel")));
		fields.add(new Field("Farmland",images.findFieldImageByName("Farmland")));
	}
	
	private void loadBiomes() {
		ArrayList<FieldWeight> fieldWeights = new ArrayList<FieldWeight>();
		ArrayList<PlantWeight> plantWeights = new ArrayList<PlantWeight>();
		// OCEAN 0
		fieldWeights = new ArrayList<FieldWeight>();
		plantWeights = new ArrayList<PlantWeight>();
		fieldWeights.add(new FieldWeight(fields.get(0),1)) ;
		System.out.println(fieldWeights.get(0));
		biomes.add(new Biome("Ocean",fieldWeights, plantWeights));
		// LAKE 1
		fieldWeights = new ArrayList<FieldWeight>();
		plantWeights = new ArrayList<PlantWeight>();
		fieldWeights.add(new FieldWeight(fields.get(1),1)) ;
		System.out.println(fieldWeights.get(0));
		biomes.add(new Biome("Lake",fieldWeights, plantWeights));
		// RIVER 2
		fieldWeights = new ArrayList<FieldWeight>();
		plantWeights = new ArrayList<PlantWeight>();
		fieldWeights.add(new FieldWeight(fields.get(2),1)) ;
		biomes.add(new Biome("River",fieldWeights, plantWeights));
		// GRASSLAND 3
		fieldWeights = new ArrayList<FieldWeight>();
		plantWeights = new ArrayList<PlantWeight>();
		fieldWeights.add(new FieldWeight(fields.get(3),1)) ;
		biomes.add(new Biome("Grassland",fieldWeights, plantWeights));
		// DESERT 4
		fieldWeights = new ArrayList<FieldWeight>();
		plantWeights = new ArrayList<PlantWeight>();
		fieldWeights.add(new FieldWeight(fields.get(4),1)) ;
		biomes.add(new Biome("Desert",fieldWeights, plantWeights));
		// BEACH 5
		fieldWeights = new ArrayList<FieldWeight>();
		plantWeights = new ArrayList<PlantWeight>();
		fieldWeights.add(new FieldWeight(fields.get(4),1)) ;
		biomes.add(new Biome("Beach",fieldWeights, plantWeights));
		// FOREST 6
		fieldWeights = new ArrayList<FieldWeight>();
		plantWeights = new ArrayList<PlantWeight>();
		fieldWeights.add(new FieldWeight(fields.get(3),1)) ;
		biomes.add(new Biome("Grassland",fieldWeights, plantWeights));
		// PATH 7
		fieldWeights = new ArrayList<FieldWeight>();
		plantWeights = new ArrayList<PlantWeight>();
		fieldWeights.add(new FieldWeight(fields.get(5),1)) ;
		biomes.add(new Biome("Grassland",fieldWeights, plantWeights));
		for (Biome bi:biomes) {
    		System.out.println(bi.fieldweights.get(0));
    	}
	}
	
	public void fillMenu() {
		

		menu.addTab("Fields", images.Other.get(7).img, 50);
		menu.addTab("Plants", images.Other.get(8).img, 50);
		menu.addTab("Buildings", images.Other.get(9).img, 50);
		
	}
	
	public void actionPerformed(ActionEvent e){
		timestat ++;
		if (timestat == 100) timestat = 0;
		repaint();
	}
	
	public void paint(Graphics g){
		super.paint(g);
		g2d = (Graphics2D) g;
		MapRender.drawMap(g2d, this.getSize(),images, MapOpen);
	}
	
	
	private class CD extends MouseAdapter{

		boolean m = false;
		
		public void mousePressed(MouseEvent e){
			Rectangle2D Tabs = new Rectangle2D.Double(0,0,menu.tabsPerRow*menu.tabSize, ((int)(menu.activeTabs/5)+1)*menu.tabSize);
			Rectangle2D TabContent = new Rectangle2D.Double(0,((int)(menu.activeTabs/5)+1)*menu.tabSize,200, 10000);
			Rectangle2D Toolbar = new Rectangle2D.Double(200,0,10000, menu.tabSize);
			
			if((e.getButton() == MouseEvent.BUTTON1 || e.getButton() == MouseEvent.BUTTON3) && Tabs.contains(e.getPoint())) {
				menu.tabClicked(e.getPoint(), e.getButton()==MouseEvent.BUTTON1);
				m = true;
			}
			else if((e.getButton() == MouseEvent.BUTTON1 || e.getButton() == MouseEvent.BUTTON3) && TabContent.contains(e.getPoint())) {
				menu.tabContentClicked(e.getPoint(), e.getButton()==MouseEvent.BUTTON1);
				m = true;
			}
			else if((e.getButton() == MouseEvent.BUTTON1 || e.getButton() == MouseEvent.BUTTON3) && Toolbar.contains(e.getPoint())) {
				menu.toolbarClicked(e.getPoint(), e.getButton()==MouseEvent.BUTTON1);
				m = true;
			}
			else {
				m = false;
				if (editorstate == "EDIT"){
					if (e.getButton() == MouseEvent.BUTTON1 && MapOpen && !Map.miniMap && (IDsel || Extrasel)){
					x1 = e.getX()/MapRender.rs;
					y1 = e.getY()/MapRender.rs;
					}
				}
				else if (editorstate == "MOVE"){
					if (e.getButton() == MouseEvent.BUTTON1 && MapOpen && !Map.miniMap){
						mx1 = e.getX()/MapRender.rs;
						my1 = e.getY()/MapRender.rs;
						}
				}
			}
		}
		public void mouseReleased(MouseEvent e){
			if (editorstate == "EDIT" && !m){
				if (e.getButton() == MouseEvent.BUTTON1 && MapOpen && !Map.miniMap && IDsel && Mapstate == "ID"){
					x2 = e.getX()/MapRender.rs;
					y2 = e.getY()/MapRender.rs;
					int xdif,ydif;
					
					if (x2>x1)xdif = x2-x1+1;
					else xdif = x1-x2+1;
					if (y2>y1)ydif = y2 - y1+1;
					else ydif = y1-y2+1;
					int a = 0,b = 0;
					
					if(x2>x1 && y2>y1)do{
						do{
							MAP[x1+a+ MapRender.screenx][y1+b+ MapRender.screeny].id = selID;
							a++;
						}while(a<xdif);
						if (a == xdif)a=0;
						b++;
					}while(b<ydif);
					
					else if(x2<x1 && y2<y1)do{
						do{
							MAP[x1-a + MapRender.screenx][y1-b+ MapRender.screeny].id = selID;
							a++;
						}while(a<xdif);
						if (a == xdif)a=0;
						b++;
					}while(b<ydif);
					
					else if(x2>x1 && y2<y1)do{
						do{
							MAP[x1+a + MapRender.screenx][y1-b+ MapRender.screeny].id = selID;
							a++;
						}while(a<xdif);
						if (a == xdif)a=0;
						b++;
					}while(b<ydif);
					
					else do{
						do{
							MAP[x1-a + MapRender.screenx][y1+b+ MapRender.screeny].id = selID;
							a++;
						}while(a<xdif);
						if (a == xdif)a=0;
						b++;
					}while(b<ydif);
					Map.mapChange = true;
					MapRender.drawMap(g2d, getSize(), images,true);
					Map.saved = false;
				}
				
				if (e.getButton() == MouseEvent.BUTTON1 && MapOpen && !Map.miniMap && Extrasel && Mapstate == "EXTRA"){
					x2 = e.getX()/MapRender.rs;
					y2 = e.getY()/MapRender.rs;
					int xdif,ydif;
					
					if (x2>x1)xdif = x2-x1+1;
					else xdif = x1-x2+1;
					if (y2>y1)ydif = y2 - y1+1;
					else ydif = y1-y2+1;
					int a = 0,b = 0;
					
					if(x2>x1 && y2>y1)do{
						do{
							MAP[x1+a+ MapRender.screenx][y1+b+ MapRender.screeny].subId = selEXTRA;
							a++;
						}while(a<xdif);
						if (a == xdif)a=0;
						b++;
					}while(b<ydif);
					
					else if(x2<x1 && y2<y1)do{
						do{
							MAP[x1-a + MapRender.screenx][y1-b+ MapRender.screeny].subId = selEXTRA;
							a++;
						}while(a<xdif);
						if (a == xdif)a=0;
						b++;
					}while(b<ydif);
					
					else if(x2>x1 && y2<y1)do{
						do{
							MAP[x1+a + MapRender.screenx][y1-b+ MapRender.screeny].subId = selEXTRA;
							a++;
						}while(a<xdif);
						if (a == xdif)a=0;
						b++;
					}while(b<ydif);
					
					else do{
						do{
							MAP[x1-a + MapRender.screenx][y1+b+ MapRender.screeny].subId = selEXTRA;
							a++;
						}while(a<xdif);
						if (a == xdif)a=0;
						b++;
					}while(b<ydif);
					Map.mapChange = true;
					MapRender.drawMap(g2d, getSize(),images, true);
					Map.saved = false;
				}
			}
			else if (editorstate == "MOVE" && !m){
				if (e.getButton() == MouseEvent.BUTTON1 && MapOpen && !Map.miniMap){
					mx2 = e.getX()/MapRender.rs;
					my2 = e.getY()/MapRender.rs;
					int x = mx1- mx2;
					int y = my1-my2;
					MapRender.screenx += x;
					MapRender.screeny += y;
					if (MapRender.screenx <= 0)MapRender.screenx =0;
					if (MapRender.screeny <= 0)MapRender.screeny =0;
					if (MapRender.screenx >= Map.map_xsize - MapRender.screenXSize)MapRender.screenx = Map.map_xsize - MapRender.screenXSize;
					if (MapRender.screeny >= Map.map_ysize - MapRender.screenYSize)MapRender.screeny = Map.map_ysize - MapRender.screenYSize;
					
					Map.mapChange = true;
					MapRender.drawMap(g2d, getSize(),images, true);
					Map.saved = false;
					System.out.println(MapRender.screenx +" : " + MapRender.screeny);
			}
		}
	}
}
}