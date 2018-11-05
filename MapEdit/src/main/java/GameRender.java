import MapGen.DataHandler;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class GameRender {

    static int screenXSize = 70;
    static int screenYSize = 48;
    static int screenx = 0;
    static int screeny = 0;
	static int counter = 1;
    static int factor = 5;
    static int rs = 16; // rendersize
    static Dimension boardSize;
    DataHandler data;
    Menu menu;

    public GameRender() {
        this.data = Board.dataHandler;
        this.menu = Board.menu;
    }

    static Screen_Array[][] field = new Screen_Array[screenXSize][screenYSize];
	
	protected void drawGame(Graphics2D g2d, Dimension size, boolean mapOpen){
		boardSize = size;
		if(mapOpen) {
	    	if (!Map.miniMap){
	    		if(Map.mapDrawState == 0)drawMap(g2d);
	    		else drawBiomesMap(g2d);
	    	}
	    	else drawMiniMap(g2d);
		}
    	renderMenu(g2d);
    }
	
	private void renderMenu(Graphics2D g2d) {

		
		//HARDCODED VARS FOR MENU
		final int MBW = 200; // menu bar width
		final int LINE = 1;
		
		int renderHeightMenu = 0;
		int renderWidthToolbar = MBW;
		g2d.setColor(menu.background);
        menu.size = new Dimension(200, boardSize.height);
		//Draw Toolbar
		for(Menu.MenuItem item:menu.toolbarItems) {
			if(item.active)
				g2d.setColor(menu.backgroundSelected);
			else
				g2d.setColor(menu.background);
            g2d.fill(new Rectangle2D.Double(renderWidthToolbar, 0, menu.tabSize, menu.tabSize));
	    	if(item.img != null) {
	    		g2d.drawImage(item.img,renderWidthToolbar + 4,4, null );
	    	}
            renderWidthToolbar += menu.tabSize;
		}

        g2d.fill(new Rectangle2D.Double(renderWidthToolbar, 0, boardSize.width - renderWidthToolbar, menu.tabSize));
		
		//Draw Tabs
		if(menu.calcRender) {
            menu.tabsHeight = (((menu.activeTabs - menu.activeTabs % menu.tabsPerRow) / menu.tabsPerRow) + 1) * menu.tabSize;
            menu.calcRender = false;
		}
		int i = 0;
		while(i < menu.activeTabs) {
			if(i == menu.selectedTabIndex)
				g2d.setColor(menu.backgroundSelected);
			else
				g2d.setColor(menu.background);


            g2d.fill(new Rectangle2D.Double((i % menu.tabsPerRow) * menu.tabSize, (i - i % menu.tabsPerRow) / menu.tabsPerRow * menu.tabSize, menu.tabSize, menu.tabSize));
            g2d.drawImage(menu.Tabs[i].img, (i % menu.tabsPerRow) * menu.tabSize + 4, (i - i % menu.tabsPerRow) / menu.tabsPerRow * menu.tabSize + 4, null);
			i++;
		}
        renderHeightMenu = (i - i % menu.tabsPerRow) / menu.tabsPerRow * menu.tabSize;
		
		// Fill row of tabs with gray area
        if (menu.activeTabs % menu.tabsPerRow > 0) {
			g2d.setColor(menu.background);

            g2d.fill(new Rectangle2D.Double((i % menu.tabsPerRow) * menu.tabSize, renderHeightMenu, menu.tabSize * (menu.tabsPerRow - menu.activeTabs % menu.tabsPerRow), menu.tabSize));
            renderHeightMenu += menu.tabSize;
		}
		//Draw separator Line
		g2d.setColor(menu.line);
        g2d.fill(new Rectangle2D.Double(0, renderHeightMenu, MBW, LINE));
	    renderHeightMenu += LINE;
		
		//Draw Rest

    	
		
	    
		g2d.setColor(menu.background);
        g2d.fill(new Rectangle2D.Double(0, renderHeightMenu, MBW, boardSize.height - renderHeightMenu));
	    
	    
	}
	
	private void toScreenArray() {
        int x_c = 0;
        int y_c = 0;
        int x_val = 0;
        int y_val = 0;
        int x_len = rs;
        int y_len = rs;
        do{
            do{
                field[x_c][y_c] = new Screen_Array(Board.MAP[screenx + x_c][screeny + y_c].field, new Rectangle(x_val, y_val, x_len, y_len), Board.MAP[screenx + x_c][screeny + y_c].biome, Board.MAP[screenx + x_c][screeny + y_c].plant);
                x_val += rs;
                x_c++;
            } while (x_c < screenXSize);
            if (x_c == screenXSize) {
                x_c = 0;
                x_val = 0;
                y_c++;
                y_val += rs;
            }
        } while (y_c < screenYSize);
    }
    
    private void drawMiniMap(Graphics2D g2d){
    	if (Map.mapChange)toScreenArray();
    	Map.mapChange = false;
        int xc = 0;
        int yc = 0;
        int xpos = 0,ypos = 0;
        counter = 1;
        do{
            do{
            	int c = Board.MAP[xc][yc].field;
                switch(c){
                case 1:
                	g2d.setColor(Color.blue);
                	break;
                case 2:
                	g2d.setColor(Color.blue);
                	break;
                case 3:
                	g2d.setColor(Color.green);
                	break;
                case 4:
                	g2d.setColor(Color.yellow);
                	break;
                case 5:
                	g2d.setColor(Color.gray);
                	break;
                case 6:
                	g2d.setColor(Color.orange);
                	break;
                default:
                    g2d.setColor(null);
                	break;
                }
                g2d.drawRect(xpos, ypos,factor,factor);
                counter++;
                xc++;
                xpos += factor + 1;
            } while (xc < Map.map_xsize);
            if (xc == Map.map_xsize){
                xpos = 0;
                xc = 0;
               yc++;
               ypos += factor + 1;
            }
        } while (yc < Map.map_ysize);
        g2d.setColor(null);
    }
    
    private void drawBiomesMap(Graphics2D g2d){
//    	if (Map.mapChange)toScreenArray();
//    	Map.mapChange = false;
//        int xc = 0;
//        int yc = 0;
//        counter = 1;
//        do{
//            do{
//            	int a = field[xc][yc].abc.x;
//            	int b = field[xc][yc].abc.y;
//            	Biome c = field[xc][yc].biome;
//            	switch (c){
//	            	case 0:{
//	            		g2d.drawImage(Images.BiomeImages[rs/32 -1][1],a,b,null);
//		            	break;
//	            	}
//	            	case 1:{
//	            		g2d.drawImage(Images.BiomeImages[rs/32 -1][4],a,b,null);
//	            		break;
//	            	}
//	            	case 2:{
//	            		g2d.drawImage(Images.BiomeImages[rs/32 -1][2],a,b,null);
//	            		break;
//	            	}
//	            	case 3:{
//	            		g2d.drawImage(Images.BiomeImages[rs/32 -1][1],a,b,null);
//	            		break;
//	            	}
//	            	case 4:{
//	            		g2d.drawImage(Images.BiomeImages[rs/32 -1][1],a,b,null);
//	            		break;
//	            	}
//            	}
//                counter++;
//                xc++;
//            } while (xc < screenXSize);
//            if (xc == screenXSize)
//            {
//                xc = 0;
//               yc++;
//            }
//        } while (yc < screenYSize);
    }
    
    private void drawMap(Graphics2D g2d) {
        if (Map.mapChange)toScreenArray();
        Map.mapChange = false;
        int xc = 0;
        int yc = 0;
        counter = 1;
        do{
            do{
            	int a = field[xc][yc].abc.x;
            	int b = field[xc][yc].abc.y;
            	int c = field[xc][yc].id;
            	int d = field[xc][yc].subId;
                g2d.drawImage(data.fields.get(c).imgObj.img, a, b, rs, rs, null);
                if (d != 0) g2d.drawImage(data.plants.get(d).imgObj.img, a, b, rs, rs, null);
                counter++;
                xc++;
            } while (xc < screenXSize);
            if (xc == screenXSize)
            {
                xc = 0;
               yc++;
            }
        } while (yc < screenYSize);
    }
}
