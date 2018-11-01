import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;

public class MapRender {

    static int screenXSize = 32;
    static int screenYSize = 24;
    static int screenx = 0;
    static int screeny = 0;
	static int counter = 1;
    static int factor = 5;
    static int rs = 32; // rendersize
    static Dimension boardSize;
    
    
    static Screen_Array[][] field = new Screen_Array[screenXSize][screenYSize];
	
	public static void drawMap(Graphics2D g2d, Dimension size, Images img, boolean mapOpen){
		boardSize = size;
		if(mapOpen) {
	    	if (!Map.miniMap){
	    		if(Map.mapDrawState == 0)drawMap(g2d, img);
	    		else drawBiomesMap(g2d);
	    	}
	    	else drawMiniMap(g2d);
		}
    	renderMenu(g2d);
    }
	
	private static void renderMenu(Graphics2D g2d) {

		Menu m = Board.menu;
		
		//HARDCODED VARS FOR MENU
		final int MBW = 200; // menu bar width
		final int TAB = m.tabSize; //Tab Size
		final int TPR = m.tabsPerRow; //Tabs per Row
		final int LINE = 1;
		
		int renderHeightMenu = 0;
		int renderWidthToolbar = MBW;
		g2d.setColor(m.background);
		m.size = new Dimension(200, boardSize.height);
		//Draw Toolbar
		for(Menu.MenuItem item:m.toolbarItems) {
			if(item.active)
				g2d.setColor(m.backgroundSelected);
			else
				g2d.setColor(m.background);
	    	g2d.fill((Shape) new Rectangle2D.Double(renderWidthToolbar, 0, TAB, TAB));
	    	if(item.img != null) {
	    		g2d.drawImage(item.img,renderWidthToolbar + 4,4, null );
	    	}
	    	//TODO: draw Icon
			renderWidthToolbar += TAB;
		}
		
			//Fill rest of toolbar
			g2d.setColor(new Color(255,0,0,180));
	    	g2d.fill((Shape) new Rectangle2D.Double(renderWidthToolbar, 0, boardSize.width - renderWidthToolbar, TAB));
		
		//Draw Tabs
		if(m.calcRender) {
			m.tabsHeight = (((m.activeTabs-m.activeTabs%TPR) / TPR)+1) * TAB;
			m.calcRender = false;
		}
		int i = 0;
		while(i < m.activeTabs) {
			if(i == m.selectedTabIndex)
				g2d.setColor(m.backgroundSelected);
			else
				g2d.setColor(m.background);
			
			
		    g2d.fill((Shape) new Rectangle2D.Double((i%TPR)*TAB, (i-i%TPR)/TPR * TAB, TAB, TAB));
    		g2d.drawImage(m.Tabs[i].img,(i%TPR)*TAB + 4,(i-i%TPR)/TPR * TAB + 4, null );
			i++;
		}
		renderHeightMenu = (i-i%TPR)/TPR * TAB;
		
		// Fill row of tabs with gray area
		if(m.activeTabs%TPR > 0) { 
			g2d.setColor(m.background);
			g2d.setColor(new Color(0,255,0,180));//DEBUG
			
		    g2d.fill((Shape) new Rectangle2D.Double((i%TPR)*TAB, renderHeightMenu, TAB*(TPR-m.activeTabs%TPR), TAB ));
		    renderHeightMenu += TAB;
		}
		//Draw separator Line
		g2d.setColor(m.line);
	    g2d.fill((Shape) new Rectangle2D.Double(0, renderHeightMenu, MBW, LINE));
	    renderHeightMenu += LINE;
		
		//Draw Rest

    	
		
	    
		g2d.setColor(m.background);
	    g2d.fill((Shape) new Rectangle2D.Double(0, renderHeightMenu, MBW, boardSize.height - renderHeightMenu));
	    
	    
	}
	
	static void toScreenArray() {
        int x_c = 0;
        int y_c = 0;
        int x_val = 0;
        int y_val = 0;
        int x_len = rs;
        int y_len = rs;
        do{
            do{
                field[x_c][y_c] = new Screen_Array(Board.MAP[screenx + x_c][screeny + y_c].id, new Rectangle(x_val, y_val, x_len, y_len), Board.MAP[screenx + x_c][ screeny + y_c].biome, Board.MAP[screenx + x_c][ screeny + y_c].subId);
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
    
    private static void drawMiniMap(Graphics2D g2d){
    	if (Map.mapChange)toScreenArray();
    	Map.mapChange = false;
        int xc = 0;
        int yc = 0;
        int xpos = 0,ypos = 0;
        counter = 1;
        do{
            do{
            	int c = Board.MAP[xc][yc].id;
                switch(c){
                case 1:{
                	g2d.setColor(Color.blue);
                	break;
                }
                case 2:{
                	g2d.setColor(Color.blue);
                	break;
                }
                case 3:{
                	g2d.setColor(Color.green);
                	break;
                }
                case 4:{
                	g2d.setColor(Color.yellow);
                	break;
                }
                case 5:{
                	g2d.setColor(Color.gray);
                	break;
                }
                case 6:{
                	g2d.setColor(Color.orange);
                	break;
                }
                default:{
                    g2d.setColor(null);
                	break;
                }
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
    
    private static void drawBiomesMap(Graphics2D g2d){
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
    
    private static void drawMap(Graphics2D g2d, Images img) {
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
            	g2d.drawImage(img.Fields.get(c).img,a,b,null);
                if(d!=0)g2d.drawImage(Images.ExtraImages[rs/32 -1][d], a,b,null);
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
