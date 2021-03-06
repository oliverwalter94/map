
import java.awt.*;
import MapGen.*;
import javax.swing.*;

import java.awt.event.*;
import java.awt.geom.Rectangle2D;

public class Board extends JPanel implements ActionListener{

    public enum EditorState{
        EDIT, MOVE
    }

	private static final long serialVersionUID = 1L;
	static boolean MapOpen = false;
	private static int timestat = 0;
	public static Graphics2D g2d;
	static int plusHor = 0;
	static int plusVer = 0;
	Timer time;
	public MapGenerator mapGen;
	public static Map[][] MAP = new Map[Map.map_xsize][Map.map_ysize];
	static boolean IDsel = false, Extrasel = false;
	static int selID,selEXTRA;
	static int x1,x2,y1,y2;
	static EditorState editorState = EditorState.EDIT;
	private static String Mapstate = "ID";
	static int mx1,mx2,my1,my2;
	
	private Menu menu;
	private DataHandler data;

	private GameRender gameRender;
	
	public Board(){

		data = new DataHandler();

		menu = new Menu(data);
		
		mapGen = new MapGenerator(data);

		gameRender = new GameRender(data, menu);
		
		addMouseListener(new CD());
		setFocusable(true);
		time = new Timer(30,this);
		time.start();
		fillMenu();
	}
	
	public void fillMenu() {
		

		menu.addTab("Fields", data.getImageByName("Fields"), 50);
		menu.addTab("Plants", data.getImageByName("Plants"), 50);
		menu.addTab("Buildings", data.getImageByName("Buildings"), 50);
		
	}
	
	public void actionPerformed(ActionEvent e){
		timestat ++;
		if (timestat == 100) timestat = 0;
		repaint();
	}
	
	public void paint(Graphics g){
		super.paint(g);
		g2d = (Graphics2D) g;
		gameRender.drawGame(g2d, this.getSize(), MapOpen);
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
				menu.toolbarClicked(e.getPoint(), e.getButton()==MouseEvent.BUTTON1, mapGen);
				m = true;
			}
			else {
				m = false;
				if (editorState == EditorState.EDIT){
					if (e.getButton() == MouseEvent.BUTTON1 && MapOpen && !Map.miniMap && (IDsel || Extrasel)){
					x1 = e.getX()/ GameRender.rs;
					y1 = e.getY()/ GameRender.rs;
					}
				}
				else if (editorState == EditorState.MOVE){
					if (e.getButton() == MouseEvent.BUTTON1 && MapOpen && !Map.miniMap){
						mx1 = e.getX()/ GameRender.rs;
						my1 = e.getY()/ GameRender.rs;
						}
				}
			}
		}
		public void mouseReleased(MouseEvent e){
			if (editorState == EditorState.EDIT && !m){
				if (e.getButton() == MouseEvent.BUTTON1 && MapOpen && !Map.miniMap && IDsel && Mapstate.equals("ID")){
					x2 = e.getX()/ GameRender.rs;
					y2 = e.getY()/ GameRender.rs;
					int xdif,ydif;
					
					if (x2>x1)xdif = x2-x1+1;
					else xdif = x1-x2+1;
					if (y2>y1)ydif = y2 - y1+1;
					else ydif = y1-y2+1;
					int a = 0,b = 0;
					
					if(x2>x1 && y2>y1)do{
						do{
							MAP[x1+a+ GameRender.screenx][y1+b+ GameRender.screeny].field = selID;
							a++;
						}while(a<xdif);
						if (a == xdif)a=0;
						b++;
					}while(b<ydif);
					
					else if(x2<x1 && y2<y1)do{
						do{
							MAP[x1-a + GameRender.screenx][y1-b+ GameRender.screeny].field = selID;
							a++;
						}while(a<xdif);
						if (a == xdif)a=0;
						b++;
					}while(b<ydif);
					
					else if(x2>x1 && y2<y1)do{
						do{
							MAP[x1+a + GameRender.screenx][y1-b+ GameRender.screeny].field = selID;
							a++;
						}while(a<xdif);
						if (a == xdif)a=0;
						b++;
					}while(b<ydif);
					
					else do{
						do{
							MAP[x1-a + GameRender.screenx][y1+b+ GameRender.screeny].field = selID;
							a++;
						}while(a<xdif);
						if (a == xdif)a=0;
						b++;
					}while(b<ydif);
					Map.mapChange = true;
					gameRender.drawGame(g2d, getSize(),true);
					Map.saved = false;
				}
				
				if (e.getButton() == MouseEvent.BUTTON1 && MapOpen && !Map.miniMap && Extrasel && Mapstate.equals("EXTRA")){
					x2 = e.getX()/ GameRender.rs;
					y2 = e.getY()/ GameRender.rs;
					int xdif,ydif;
					
					if (x2>x1)xdif = x2-x1+1;
					else xdif = x1-x2+1;
					if (y2>y1)ydif = y2 - y1+1;
					else ydif = y1-y2+1;
					int a = 0,b = 0;
					
					if(x2>x1 && y2>y1)do{
						do{
							MAP[x1+a+ GameRender.screenx][y1+b+ GameRender.screeny].subId = selEXTRA;
							a++;
						}while(a<xdif);
						if (a == xdif)a=0;
						b++;
					}while(b<ydif);
					
					else if(x2<x1 && y2<y1)do{
						do{
							MAP[x1-a + GameRender.screenx][y1-b+ GameRender.screeny].subId = selEXTRA;
							a++;
						}while(a<xdif);
						if (a == xdif)a=0;
						b++;
					}while(b<ydif);
					
					else if(x2>x1 && y2<y1)do{
						do{
							MAP[x1+a + GameRender.screenx][y1-b+ GameRender.screeny].subId = selEXTRA;
							a++;
						}while(a<xdif);
						if (a == xdif)a=0;
						b++;
					}while(b<ydif);
					
					else do{
						do{
							MAP[x1-a + GameRender.screenx][y1+b+ GameRender.screeny].subId = selEXTRA;
							a++;
						}while(a<xdif);
						if (a == xdif)a=0;
						b++;
					}while(b<ydif);
					Map.mapChange = true;
					gameRender.drawGame(g2d, getSize(), true);
					Map.saved = false;
				}
			}
			else if (editorState == EditorState.MOVE && !m){
				if (e.getButton() == MouseEvent.BUTTON1 && MapOpen && !Map.miniMap){
					mx2 = e.getX()/ GameRender.rs;
					my2 = e.getY()/ GameRender.rs;
					int x = mx1- mx2;
					int y = my1-my2;
					GameRender.screenx += x;
					GameRender.screeny += y;
					if (GameRender.screenx <= 0) GameRender.screenx =0;
					if (GameRender.screeny <= 0) GameRender.screeny =0;
					if (GameRender.screenx >= Map.map_xsize - GameRender.screenXSize)
						GameRender.screenx = Map.map_xsize - GameRender.screenXSize;
					if (GameRender.screeny >= Map.map_ysize - GameRender.screenYSize)
						GameRender.screeny = Map.map_ysize - GameRender.screenYSize;
					
					Map.mapChange = true;
					gameRender.drawGame(g2d, getSize(), true);
					Map.saved = false;
					System.out.println(GameRender.screenx +" : " + GameRender.screeny);
			}
		}
	}
}
}