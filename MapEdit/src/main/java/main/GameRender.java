package main;

import MapGen.Biome;
import MapGen.Chunk;
import MapGen.MapTile;
import MapGen.Tile;
import UI.BiomeBuilder;
import UI.ImagePicker;
import UI.Message;
import UI.UIFrame;
import main.Menu.MenuItem;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import static java.util.stream.Collectors.toMap;


class GameRender {
    private final ImagePicker imagePicker;
    private final BiomeBuilder biomeBuilder;
    private final StructureEditor structureEditor;

    enum RenderMode {
        NONE, MAPEDIT, MINIMAP, IMAGEPICKER, BIOMEBUILDER, STRUCTEDITOR, SIMULATION
    }

    private static Dimension boardSize;
    private Menu menu;
    RenderMode renderMode = RenderMode.NONE;

    GameRender() {
        this.menu = Board.menu;
        this.imagePicker = Board.imagePicker;
        this.biomeBuilder = Board.biomeBuilder;
        this.structureEditor = Board.structureEditor;
    }

    void drawGame(Graphics2D g2d, Dimension size) {
        boardSize = size;
        switch (renderMode) {
            case MAPEDIT:
                renderMap(g2d);
                if (Board.editorState == Board.EditorState.EDIT && Board.dragging)
                    renderSelection(g2d);
                break;
            case STRUCTEDITOR:
                structureEditor.render(g2d);
                break;
            case IMAGEPICKER:
                imagePicker.render(g2d, boardSize);
                break;
            case BIOMEBUILDER:
                biomeBuilder.render(g2d);
                break;
        }

        renderMenu(g2d);
        renderFrames(g2d);
        renderMessages(g2d);

    }


    private void renderSelection(Graphics2D g2d) {
        Point mouse1actual = new Point(Board.mouse1.x, Board.mouse1.y);
        if (Board.mouse1.x > Board.mouse2.x)
            mouse1actual.x += Board.mapHandler.tileSpacing;
        if (Board.mouse1.y > Board.mouse2.y)
            mouse1actual.y += Board.mapHandler.tileSpacing;

        Rectangle r = new Rectangle(mouse1actual);
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

        int renderHeightMenu;
        g2d.setFont(menu.font);
        int renderWidthToolbar = menu.sideBarWidth;
        g2d.setColor(menu.backgroundColor);
        menu.size = new Dimension(200, boardSize.height);
// TOOLBAR
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

//TABS
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
//SIDEBAR LEFT
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
        if (menu.infobarVisible) {
// INFOBAR RIGHT
            //Draw separator Line
            int infoBarTop = menu.tabSize;
            int infoBarBottom = boardSize.height;
            int infoBarLeft = boardSize.width - menu.infoBarWidth;
            g2d.setColor(menu.line);
            g2d.fill(new Rectangle2D.Double(infoBarLeft, infoBarTop, menu.infoBarWidth, menu.lineThickness));
            infoBarTop += menu.lineThickness;


            if (Board.selectedTiles.size() > 0) {
                if (Board.selectionChanged)
                    updateInfoBar();

                //RENDER TILE INFO
                g2d.setColor(menu.backgroundColor);
                g2d.fill(new Rectangle2D.Double(infoBarLeft, infoBarTop, menu.infoBarWidth, 50));
                g2d.setColor(menu.text);
                g2d.drawString(Board.selectedTiles.size() + " Tiles selected", infoBarLeft + 32, infoBarTop + 32);
                infoBarTop += 50;

                infoBarTop = renderInfoBarLine(g2d, infoBarLeft, infoBarTop);

                g2d.setFont(menu.infoBarFont);

                //Count different tile types
                for (Map.Entry<Tile, Integer> m : menu.ground.entrySet()) {

                    g2d.setColor(menu.backgroundColor);
                    g2d.fill(new Rectangle2D.Double(infoBarLeft, infoBarTop, menu.infoBarWidth, 24));
                    g2d.setColor(menu.text);
                    g2d.drawImage(m.getKey().imageObject.img, infoBarLeft + 8, infoBarTop + 4, 16, 16, null);
                    g2d.drawString(m.getKey().name + ": " + m.getValue(), infoBarLeft + 32, infoBarTop + 16);
                    infoBarTop += 24;
                }

                infoBarTop = renderInfoBarLine(g2d, infoBarLeft, infoBarTop);

                //RENDER BIOME INFO
                for (Map.Entry<Biome, Integer> m : menu.biomes.entrySet()) {

                    g2d.setColor(menu.backgroundColor);
                    g2d.fill(new Rectangle2D.Double(infoBarLeft, infoBarTop, menu.infoBarWidth, 24));
                    g2d.setColor(menu.text);
                    //g2d.drawImage(m.getKey().imageObject.img, infoBarLeft + 8, infoBarTop + 4 , 16, 16, null);
                    g2d.drawString(m.getKey().name + ": " + m.getValue(), infoBarLeft + 32, infoBarTop + 16);
                    infoBarTop += 24;
                }

                infoBarTop = renderInfoBarLine(g2d, infoBarLeft, infoBarTop);

                //RENDER PLANT / OBJECT INFO
                for (Map.Entry<Tile, Integer> m : menu.plants.entrySet()) {

                    g2d.setColor(menu.backgroundColor);
                    g2d.fill(new Rectangle2D.Double(infoBarLeft, infoBarTop, menu.infoBarWidth, 24));
                    g2d.setColor(menu.text);
                    g2d.drawImage(m.getKey().imageObject.img, infoBarLeft + 8, infoBarTop + 4, 16, 16, null);
                    g2d.drawString(m.getKey().name + ": " + m.getValue(), infoBarLeft + 32, infoBarTop + 16);
                    infoBarTop += 24;
                }

                infoBarTop = renderInfoBarLine(g2d, infoBarLeft, infoBarTop);


                //TODO: RENDER TECHNICAL INFO

                //TODO: RENDER ITEM INFO

                //TODO: RENDER ADDITIONAL INFO
            }


//RENDER FILLER
            g2d.setColor(menu.backgroundColor);
            g2d.fillRect(infoBarLeft, infoBarTop, menu.infoBarWidth, infoBarBottom - infoBarTop);


        }


    }

    private void updateInfoBar() {
        Board.selectionChanged = false;
        menu.ground.clear();
        menu.plants.clear();
        int i;
        for (Point point : Board.selectedTiles) {
            MapTile mapTile = Board.mapHandler.getMapTile(point);

            i = menu.ground.get(mapTile.ground) != null ? menu.ground.get(mapTile.ground) : 0;

            menu.ground.put(Board.mapHandler.getMapTile(point).ground, i + 1);

            menu.ground = menu.ground
                    .entrySet()
                    .stream()
                    .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                    .collect(
                            toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
                                    LinkedHashMap::new));

            i = menu.plants.get(mapTile.plant) != null ? menu.plants.get(mapTile.plant) : 0;

            if (Board.mapHandler.getMapTile(point).plant != null) {
                menu.plants.put(Board.mapHandler.getMapTile(point).plant, i + 1);

                menu.plants = menu.plants
                        .entrySet()
                        .stream()
                        .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                        .collect(
                                toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
                                        LinkedHashMap::new));


            }

            i = menu.biomes.get(mapTile.biome) != null ? menu.biomes.get(mapTile.biome) : 0;

            menu.biomes.put(Board.mapHandler.getMapTile(point).biome, i + 1);

            menu.biomes = menu.biomes
                    .entrySet()
                    .stream()
                    .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                    .collect(
                            toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
                                    LinkedHashMap::new));

        }


    }

    private int renderInfoBarLine(Graphics2D g2d, int infoBarLeft, int infoBarTop) {
        g2d.setColor(menu.line);
        g2d.fill(new Rectangle2D.Double(infoBarLeft, infoBarTop, menu.infoBarWidth, menu.lineThickness));
        return infoBarTop + menu.lineThickness;
    }

    private void renderMessages(Graphics2D g2d) {
        for (Message message : Board.messages) {
            message.render(g2d, boardSize);


        }
    }

    private void renderMap(Graphics2D g2d) {
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
                    for (Point point : Board.selectedTiles) {
                        Point p = new Point(x_draw / tileSpacing, y_draw / tileSpacing);
                        if (point.equals(p)) {
                            g2d.setColor(new Color(47, 205, 0, 120));
                            g2d.fillRect(x_draw, y_draw, tileSpacing, tileSpacing);
                        }

                    }
                }
            }
            chunk.rendering = false;
        }
    }

    private void renderFrames(Graphics2D g2d) {
        for (UIFrame frame : Board.frames)
            frame.render(g2d);

    }

}
