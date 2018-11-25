package main;

import MapGen.Chunk;
import MapGen.MapTile;
import UI.Frame;
import UI.Message;
import main.Menu.MenuItem;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

class GameRender {

    private static Dimension boardSize;
    private Menu menu;

    GameRender() {
        this.menu = Board.menu;
    }

    void drawGame(Graphics2D g2d, Dimension size, boolean mapOpen) {
        boardSize = size;
        if (mapOpen) {
            if (!Board.mapHandler.miniMap) {
                renderMapNew(g2d);
            } else drawMiniMap(g2d);
        }
        renderMenu(g2d);
        if (Board.editorState == Board.EditorState.EDIT && Board.dragging)
            renderSelection(g2d);
        renderMessages(g2d);
        renderFrames(g2d);

    }

    private void renderSelection(Graphics2D g2d) {

        Rectangle r = new Rectangle(Board.mouse1);
        r.add(Board.mouse2);
        if (!Board.remove) {
            g2d.setColor(new Color(47, 205, 0, 120));
            g2d.fill(r);
            g2d.setColor(new Color(13, 205, 36, 255));
//            g2d.draw(r);
        } else {
            g2d.setColor(new Color(255, 39, 7, 120));
            g2d.fill(r);
            g2d.setColor(new Color(255, 0, 25, 255));
//            g2d.draw(r);

        }
    }

    private void renderMenu(Graphics2D g2d) {

        int renderHeightMenu = 0;
        g2d.setFont(menu.font);
        int renderWidthToolbar = menu.sideBarWidth;
        g2d.setColor(menu.backgroundColor);
        menu.size = new Dimension(200, boardSize.height);
        //Draw Toolbar
        for (MenuItem item : menu.toolbarItems) {
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

            for (MenuItem item : menu.Tabs[menu.selectedTabIndex].menuItems) {
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

    private void drawMiniMap(Graphics2D g2d){
//        if (Map.mapChange) toScreenArray();
//        Map.mapChange = false;
//        int xc = 0;
//        int yc = 0;
//        int xpos = 0,ypos = 0;
//        do{
//            int factor = 5;
//            do{
//                int c = main.Board.MAP[xc][yc].field;
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

    public void renderMapNew(Graphics2D g2d) {
        for (Chunk chunk : Board.mapHandler.chunks) {
            chunk.rendering = true;
            int tileSize = Board.mapHandler.tileSize;
            int tileSpacing = Board.mapHandler.tileSpacing;
            for (int x = 0; x < 16; x++) {
                for (int y = 0; y < 16; y++) {
                    int x_draw = (chunk.pos.x * 16 + x) * tileSpacing - Board.mapHandler.getOrigin().x;
                    int y_draw = (chunk.pos.y * 16 + y) * tileSpacing - Board.mapHandler.getOrigin().y;
                    MapTile tile = chunk.chunkTiles[x][y];
                    g2d.drawImage(tile.ground.imageObject.img, x_draw, y_draw, tileSize, tileSize, null);
                    if (tile.plant != null)
                        g2d.drawImage(tile.plant.imageObject.img, x_draw, y_draw, tileSize, tileSize, null);
                }
            }
            chunk.rendering = false;
        }
    }

    public void renderFrames(Graphics2D g2d) {
        for (Frame frame : Board.frames)
            frame.render(g2d);

    }
}
