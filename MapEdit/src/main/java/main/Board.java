package main;

import Data.DataHandler;
import Entities.Item;
import UI.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class Board extends JPanel implements ActionListener {

    public static ImagePicker imagePicker;
    public static BiomeBuilder biomeBuilder;
    public static StructureEditor structureEditor;

    public enum EditorState {
        EDIT, MOVE, IMAGEPICKER, TILEMENU, TILE_EDITOR
    }

    private static final long serialVersionUID = 1L;
    private static int timestat = 0;
    private static int x1, y1;
    static EditorState editorState = EditorState.EDIT;
    private static int mxa1, mya1;
    static boolean dragging = false;
    static boolean remove = false;
    static Point mouse1, mouse2;

    static Menu menu;
    public static DataHandler dataHandler;
    public static GameRender gameRenderer;
    static MapHandler mapHandler;

    static ArrayList<Message> messages = new ArrayList<>();
    static ArrayList<UIFrame> frames = new ArrayList<>();
    static ArrayList<Item> items;

    Board() {

        dataHandler = new DataHandler();
        imagePicker = new ImagePicker(dataHandler);
        biomeBuilder = new BiomeBuilder(dataHandler);
        structureEditor = new StructureEditor(dataHandler);
        menu = new Menu();
        mapHandler = new MapHandler(this);
        gameRenderer = new GameRender();

        addMouseListener(new CD());
        setFocusable(true);
        Timer time = new Timer(30, this);
        time.start();

//        addFrame();
    }

    public void debug() {
//        if(frames.size()==1) frames.clear();

//        if(frames.size()==0) addFrame();
    }

    public void actionPerformed(ActionEvent e) {
        timestat++;
        if (timestat == 100) timestat = 0;
        mouseMoved();
        repaint();
        updateMessages();
        debug();
    }

    private void updateMessages() {
        for (Message message : messages) {
            message.timeToLive--;
            if (message.timeToLive <= 0) {
                messages.remove(message);
                break;
            }

        }
    }

    private void mouseMoved() {
        if (dragging) {
            switch (editorState) {
                case EDIT:
                    try {
                        mouse2 = new Point(Math.floorDiv((int) this.getMousePosition().getX(), mapHandler.tileSpacing) * mapHandler.tileSpacing, Math.floorDiv((int) this.getMousePosition().getY(), mapHandler.tileSpacing) * mapHandler.tileSpacing);
                        if (mouse1.x < mouse2.x)
                            mouse2.x += mapHandler.tileSpacing;
                        else if (mouse1.x == mouse2.x)
                            mouse2.x += mapHandler.tileSpacing;
                        if (mouse1.y < mouse2.y)
                            mouse2.y += mapHandler.tileSpacing;
                        else if (mouse1.y == mouse2.y)
                            mouse2.y += mapHandler.tileSpacing;
                    } catch (NullPointerException e) {

                    }
                    break;
                case MOVE:
                    try {
                        Point newOrigin = new Point((int) (mapHandler.getOrigin().x + mxa1 - this.getMousePosition().getX()), (int) (mapHandler.getOrigin().y + mya1 - this.getMousePosition().getY()));
                        mapHandler.setOrigin(newOrigin);
                        mapHandler.calcNeededChunks();
                        mxa1 = (int) this.getMousePosition().getX();
                        mya1 = (int) this.getMousePosition().getY();
                    } catch (NullPointerException e1) {
                        //Probably just mousepointer out of Frame...
                    }
                    break;
            }

        }
    }

    static void addTooltip(Message message, Point pos) {
        messages.removeIf(message1 -> message1.type == Message.Type.TOOLTIP);
        message.setPosition(pos);
        messages.add(message);
    }

    static void addInfoMessage(Message message) {
        messages.removeIf(message1 -> message1.type != Message.Type.TOOLTIP);
        messages.add(message);
    }

    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        gameRenderer.drawGame(g2d, this.getSize());
    }

    public void addFrame() {
        UIFrame f = new UIFrame(new Point(200, 200), "");

        f.setBackgroundColor(Color.gray);
        f.setTitle("frame1");
        f.setWidth(200);
        f.setHeight(200);
        f.addChildElement(new UILabel(new Point(20, 20), "label1", "test"));
        f.addChildElement(new UITextbox(new Point(20, 80), "textbox1", 100, 25));
        f.addChildElement(new UITextbox(new Point(20, 120), "textbox2", 100, 25));
        frames.add(f);
    }

    private class CD extends MouseAdapter {

        boolean menu_clicked = false;


        public void mousePressed(MouseEvent e) {
            Rectangle2D Tabs = new Rectangle2D.Double(0, 0, menu.tabsPerRow * menu.tabSize, ((menu.activeTabs / 5) + 1) * menu.tabSize);
            Rectangle2D TabContent = new Rectangle2D.Double(0, ((menu.activeTabs / 5) + 1) * menu.tabSize, 200, 10000);
            Rectangle2D Toolbar = new Rectangle2D.Double(200, 0, 10000, menu.tabSize);

            if ((e.getButton() == MouseEvent.BUTTON1 || e.getButton() == MouseEvent.BUTTON3) && Tabs.contains(e.getPoint())) {
                //Tab
                menu.tabClicked(e.getPoint(), e.getButton() == MouseEvent.BUTTON1);
                menu_clicked = true;
            } else if ((e.getButton() == MouseEvent.BUTTON1 || e.getButton() == MouseEvent.BUTTON3) && TabContent.contains(e.getPoint()) && menu.sidebarVisible) {
                //Tab Content
                menu.tabContentClicked(e.getPoint(), e.getButton() == MouseEvent.BUTTON1);
                menu_clicked = true;
            } else if ((e.getButton() == MouseEvent.BUTTON1 || e.getButton() == MouseEvent.BUTTON3) && Toolbar.contains(e.getPoint())) {
                //Toolbar
                menu.toolbarClicked(e.getPoint(), e.getButton() == MouseEvent.BUTTON1);
                menu_clicked = true;
            } else {
                menu_clicked = false;
                if (editorState == EditorState.EDIT) {
                    if (mapHandler.mapOpen && !mapHandler.miniMap && (e.getButton() == MouseEvent.BUTTON1 || e.getButton() == MouseEvent.BUTTON3)) {
                        x1 = Math.floorDiv(mapHandler.getOrigin().x + e.getX(), mapHandler.tileSpacing);
                        y1 = Math.floorDiv(mapHandler.getOrigin().y + e.getY(), mapHandler.tileSpacing);
                        mouse1 = new Point(Math.floorDiv(e.getX(), mapHandler.tileSpacing) * mapHandler.tileSpacing, Math.floorDiv(e.getY(), mapHandler.tileSpacing) * mapHandler.tileSpacing);
                        mouse2 = mouse1;
                        dragging = true;
                        remove = e.getButton() == MouseEvent.BUTTON3;
                    }
                } else if (editorState == EditorState.MOVE) {
                    if (e.getButton() == MouseEvent.BUTTON1 && mapHandler.mapOpen && !mapHandler.miniMap) {
                        mxa1 = e.getX();
                        mya1 = e.getY();
                        dragging = true;
                    }
                }else if(editorState == EditorState.IMAGEPICKER){
                    imagePicker.onClick(e.getPoint());
                }
            }
        }

        public void mouseReleased(MouseEvent mouse){
            if(editorState == EditorState.EDIT && !menu_clicked && mapHandler.mapOpen){
                int y2;
                int x2;
                if ((mouse.getButton() == MouseEvent.BUTTON1 ||mouse.getButton() == MouseEvent.BUTTON3) && mapHandler.mapOpen && !mapHandler.miniMap && menu.selectedTabIndex == 0) {
                    x2 = Math.floorDiv(mapHandler.getOrigin().x + mouse.getX(), mapHandler.tileSpacing);
                    y2 = Math.floorDiv(mapHandler.getOrigin().y + mouse.getY(), mapHandler.tileSpacing);
                    if (x2 > x1)
                        x2++;
                    else x2--;
                    if (y2 > y1)
                        y2++;
                    else y2--;

                    int x_dif, y_dif;

                    x_dif = x2 - x1;
                    y_dif = y2 - y1;
                    ArrayList<Point> selectedTiles = new ArrayList<>();
                    int x_step = (int)Math.signum(x_dif);
                    int y_step = (int)Math.signum(y_dif);

                    int x = x1;
                    int y = y1;
                    do{
                        do{
                            selectedTiles.add(new Point(x, y));
                            y += (y_step);
                        }while (y != y1 + y_dif);
                        y = y1;
                        x += (x_step);
                    }while (x != x1 + x_dif);

                    if(mouse.getButton() == MouseEvent.BUTTON1) {
                        if (menu.selectedTabIndex == 0) {
                            for (Point tile : selectedTiles) {
                                mapHandler.setGround(dataHandler.fields.get(menu.Tabs[0].selected), tile);
                            }
                        } else if (menu.selectedTabIndex == 1) {
                            for (Point tile : selectedTiles) {
                                mapHandler.setPlant(dataHandler.plants.get(menu.Tabs[1].selected), tile);
                            }
                        }
                    }else {
                        for (Point tile : selectedTiles) {
                            mapHandler.setPlant(null, tile);
                        }
                    }





                dragging = false;
                } else if (editorState == EditorState.MOVE && !menu_clicked) {
                    if (mouse.getButton() == MouseEvent.BUTTON1 && mapHandler.mapOpen && !mapHandler.miniMap) {
                        Point newOrigin = new Point(mapHandler.getOrigin().x + mxa1 - mouse.getX(), mapHandler.getOrigin().y + mya1 - mouse.getY());
                        mapHandler.setOrigin(newOrigin);
                        dragging = false;
                        mya1 = -1;
                        mxa1 = -1;
                        mapHandler.calcNeededChunks();
                }
            }
            }
        }

    }
}