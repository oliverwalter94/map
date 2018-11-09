import Data.DataHandler;
import MapGen.Chunk;
import MapGen.Map;
import MapGen.MapTile;
import UI.Message;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

class GameRender {

    //    static int screenXSize = 128;
//    static int screenYSize = 64;
//    static int screenx = 0;
//    static int screeny = 0;
    static int tileSize = 16; // rendersize
    private static Dimension boardSize;
    private DataHandler data;
    private Menu menu;

    GameRender() {
        this.data = Board.dataHandler;
        this.menu = Board.menu;
    }
//
//    private static Screen_Array[][] screenArray = new Screen_Array[screenXSize][screenYSize];

    void drawGame(Graphics2D g2d, Dimension size, boolean mapOpen) {
        boardSize = size;
        if (mapOpen) {
            if (!Map.miniMap) {
                if (Map.mapDrawState == 0) {
                    renderMapNew(g2d);
//                    drawMap(g2d);
                }
                else drawBiomesMap(g2d);
            } else drawMiniMap(g2d);
        }
        renderMenu(g2d);
        renderMessages(g2d);
    }

    private void renderMenu(Graphics2D g2d) {


        int renderHeightMenu = 0;
        g2d.setFont(menu.font);
        int renderWidthToolbar = menu.sideBarWidth;
        g2d.setColor(menu.backgroundColor);
        menu.size = new Dimension(200, boardSize.height);
        //Draw Toolbar
        for (Menu.MenuItem item : menu.toolbarItems) {
            if (item.active)
                g2d.setColor(menu.backgroundSelected);
            else
                g2d.setColor(menu.backgroundColor);
            g2d.fill(new Rectangle2D.Double(renderWidthToolbar, 0, menu.tabSize, menu.tabSize));
            if (item.img != null) {
                g2d.drawImage(item.img, renderWidthToolbar + 4, 4, null);
            }
            renderWidthToolbar += menu.tabSize;
        }
        g2d.setColor(menu.backgroundColor);
        g2d.fill(new Rectangle2D.Double(renderWidthToolbar, 0, boardSize.width - renderWidthToolbar, menu.tabSize));

        //Draw Tabs
        if (menu.calcRender) {
            menu.tabsHeight = (((menu.activeTabs - menu.activeTabs % menu.tabsPerRow) / menu.tabsPerRow) + 1) * menu.tabSize;
            menu.calcRender = false;
        }
        int i = 0;
        while (i < menu.activeTabs) {
            if (i == menu.selectedTabIndex)
                g2d.setColor(menu.backgroundSelected);
            else
                g2d.setColor(menu.backgroundColor);


            g2d.fill(new Rectangle2D.Double((i % menu.tabsPerRow) * menu.tabSize, ((i - (i % menu.tabsPerRow)) / menu.tabsPerRow) * menu.tabSize, menu.tabSize, menu.tabSize));
            g2d.drawImage(menu.Tabs[i].img, (i % menu.tabsPerRow) * menu.tabSize + 4, (i - i % menu.tabsPerRow) / menu.tabsPerRow * menu.tabSize + 4, null);
            i++;
        }
        renderHeightMenu = (i - i % menu.tabsPerRow) / menu.tabsPerRow * menu.tabSize;

        // Fill row of tabs with gray area
        if (menu.activeTabs % menu.tabsPerRow > 0) {
            g2d.setColor(menu.backgroundColor);

            g2d.fill(new Rectangle2D.Double((i % menu.tabsPerRow) * menu.tabSize, renderHeightMenu, menu.tabSize * (menu.tabsPerRow - menu.activeTabs % menu.tabsPerRow), menu.tabSize));
            renderHeightMenu += menu.tabSize;
        }

        if (menu.sidebarVisible) {
            //Draw separator Line
            g2d.setColor(menu.line);
            g2d.fill(new Rectangle2D.Double(0, renderHeightMenu, menu.sideBarWidth, menu.lineThickness));
            renderHeightMenu += menu.lineThickness;

            //Draw Rest

            for (Menu.MenuItem item : menu.Tabs[menu.selectedTabIndex].menuItems) {
                if (item.active)
                    g2d.setColor(menu.backgroundSelected);
                else
                    g2d.setColor(menu.backgroundColor);

                g2d.fill(new Rectangle2D.Double(0, renderHeightMenu, menu.sideBarWidth, menu.Tabs[menu.selectedTabIndex].itemHeight));
                g2d.drawImage(item.img, 16, renderHeightMenu + (menu.Tabs[menu.selectedTabIndex].itemHeight - item.img.getHeight(null)) / 2, null);
                g2d.setColor(menu.text);
                g2d.drawString(item.name, 64, renderHeightMenu + 32);
                renderHeightMenu += menu.Tabs[menu.selectedTabIndex].itemHeight;
                g2d.setColor(menu.line);
                g2d.fill(new Rectangle2D.Double(0, renderHeightMenu, menu.sideBarWidth, menu.lineThickness));
                renderHeightMenu += menu.lineThickness;
            }


            g2d.setColor(menu.backgroundColor);
            g2d.fill(new Rectangle2D.Double(0, renderHeightMenu, menu.sideBarWidth, boardSize.height - renderHeightMenu));
        }

    }

    private void renderMessages(Graphics2D g2d) {
        for (Message message : Board.messages) {
            g2d.setColor(message.backgroundColor);
            Rectangle2D bounds = g2d.getFontMetrics(message.font).getStringBounds(message.text, null);
            g2d.setFont(message.font);
            if (message.type == Message.Type.INFO || message.type == Message.Type.ERROR) {
                g2d.fill(new RoundRectangle2D.Double((boardSize.getWidth() - bounds.getWidth()) / 2 - 8, 80, bounds.getWidth() + 16, bounds.getHeight() + 8, 7, 7));
                if (message.type == Message.Type.INFO) g2d.setColor(message.fontColor);
                else g2d.setColor(Color.red);
                g2d.drawString(message.text, (int) (boardSize.getWidth() - bounds.getWidth()) / 2, 80 + (int) bounds.getHeight() + 1);
            } else {
                g2d.fill(new RoundRectangle2D.Double(message.position.x, message.position.y, bounds.getWidth() + 16, bounds.getHeight() + 8, 7, 7));
                g2d.setColor(message.fontColor);
                g2d.drawString(message.text, message.position.x + 8, message.position.y + (int) bounds.getHeight());
            }
        }
    }

    private void toScreenArray() {
//        int x_c = 0;
//        int y_c = 0;
//        int x_val = 0;
//        int y_val = 0;
//        int x_len = tileSize;
//        int y_len = tileSize;
//        do{
//            do{
//                screenArray[x_c][y_c] = new Screen_Array(Board.MAP[screenx + x_c][screeny + y_c].field, new Rectangle(x_val, y_val, x_len, y_len), Board.MAP[screenx + x_c][screeny + y_c].biome, Board.MAP[screenx + x_c][screeny + y_c].plant);
//                x_val += tileSize;
//                x_c++;
//            } while (x_c < screenXSize);
//            if (x_c == screenXSize) {
//                x_c = 0;
//                x_val = 0;
//                y_c++;
//                y_val += tileSize;
//            }
//        } while (y_c < screenYSize);
    }

    private void drawMiniMap(Graphics2D g2d){
//        if (Map.mapChange) toScreenArray();
//        Map.mapChange = false;
//        int xc = 0;
//        int yc = 0;
//        int xpos = 0,ypos = 0;
//        do{
//            int factor = 5;
//            do{
//                int c = Board.MAP[xc][yc].field;
//                switch(c){
//                    case 0:
//                    case 1:
//                    case 2:
//                        g2d.setColor(Color.blue);
//                        break;
//                    case 3:
//                        g2d.setColor(Color.green);
//                        break;
//                    case 4:
//                        g2d.setColor(Color.yellow);
//                        break;
//                    case 5:
//                        g2d.setColor(Color.gray);
//                        break;
//                    case 6:
//                        g2d.setColor(Color.orange);
//                        break;
//                    default:
//                        g2d.setColor(null);
//                        break;
//                }
//                g2d.fill(new Rectangle2D.Double(xpos, ypos, factor, factor));
//                xc++;
//                xpos += factor + 1;
//            } while (xc < Map.map_xsize);
//            if (xc == Map.map_xsize){
//                xpos = 0;
//                xc = 0;
//                yc++;
//                ypos += factor + 1;
//            }
//        } while (yc < Map.map_ysize);
//        g2d.setColor(null);
    }

    private void drawBiomesMap(Graphics2D g2d){
//    	if (MapGen.Map.mapChange)toScreenArray();
//    	MapGen.Map.mapChange = false;
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
//	            		g2d.drawImage(Images.BiomeImages[tileSize/32 -1][1],a,b,null);
//		            	break;
//	            	}
//	            	case 1:{
//	            		g2d.drawImage(Images.BiomeImages[tileSize/32 -1][4],a,b,null);
//	            		break;
//	            	}
//	            	case 2:{
//	            		g2d.drawImage(Images.BiomeImages[tileSize/32 -1][2],a,b,null);
//	            		break;
//	            	}
//	            	case 3:{
//	            		g2d.drawImage(Images.BiomeImages[tileSize/32 -1][1],a,b,null);
//	            		break;
//	            	}
//	            	case 4:{
//	            		g2d.drawImage(Images.BiomeImages[tileSize/32 -1][1],a,b,null);
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
//        if (Map.mapChange)toScreenArray();
//        Map.mapChange = false;
//        int x_screen = 0;
//        int y_screen = 0;
//        do{
//            do{
//                int x = screenArray[x_screen][y_screen].abc.x;
//                int y = screenArray[x_screen][y_screen].abc.y;
//                int field = screenArray[x_screen][y_screen].id;
//                int d = screenArray[x_screen][y_screen].subId;
//                g2d.drawImage(data.fields.get(field).imageObject.img, x, y, tileSize, tileSize, null);
//                if (d >= 0) g2d.drawImage(data.plants.get(d).imageObject.img, x, y, tileSize, tileSize, null);
//                x_screen++;
//            } while (x_screen < screenXSize);
//            if (x_screen == screenXSize)
//            {
//                x_screen = 0;
//                y_screen++;
//            }
//        } while (y_screen < screenYSize);
    }

    public void renderMapNew(Graphics2D g2d) {
        for (Chunk chunk : Board.chunks) {
            chunk.rendering = true;
            for (int x = 0; x < 16; x++) {
                for (int y = 0; y < 16; y++) {
                    int x_draw = (chunk.x * 16 + x) * tileSize - Board.origin.x;
                    int y_draw = (chunk.y * 16 + y) * tileSize - Board.origin.y;
                    MapTile tile = chunk.chunkTiles[x][y];
                    g2d.drawImage(tile.ground.imageObject.img, x_draw, y_draw, tileSize, tileSize, null);
                    if (tile.plant != null)
                        g2d.drawImage(tile.plant.imageObject.img, x_draw, y_draw, tileSize, tileSize, null);
                }
            }
            chunk.rendering = false;
        }
    }
}
