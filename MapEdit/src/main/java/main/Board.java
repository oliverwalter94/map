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
        EDIT, MOVE, IMAGEPICKER
    }

    private static final long serialVersionUID = 1L;
    private static int timestat = 0;
    private static int x1, y1;
    static EditorState editorState = EditorState.EDIT;
    private static int mxa1, mya1;
    static boolean dragging = false;
    static boolean remove = false;
    static Point mouse1, mouse2;
    public static Rectangle2D selectedTiles;

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

        boolean m = false;


        public void mousePressed(MouseEvent e) {
            Rectangle2D Tabs = new Rectangle2D.Double(0, 0, menu.tabsPerRow * menu.tabSize, ((menu.activeTabs / 5) + 1) * menu.tabSize);
            Rectangle2D TabContent = new Rectangle2D.Double(0, ((menu.activeTabs / 5) + 1) * menu.tabSize, 200, 10000);
            Rectangle2D Toolbar = new Rectangle2D.Double(200, 0, 10000, menu.tabSize);

            if ((e.getButton() == MouseEvent.BUTTON1 || e.getButton() == MouseEvent.BUTTON3) && Tabs.contains(e.getPoint())) {
                //Tab
                menu.tabClicked(e.getPoint(), e.getButton() == MouseEvent.BUTTON1);
                m = true;
            } else if ((e.getButton() == MouseEvent.BUTTON1 || e.getButton() == MouseEvent.BUTTON3) && TabContent.contains(e.getPoint()) && menu.sidebarVisible) {
                //Tab Content
                menu.tabContentClicked(e.getPoint(), e.getButton() == MouseEvent.BUTTON1);
                m = true;
            } else if ((e.getButton() == MouseEvent.BUTTON1 || e.getButton() == MouseEvent.BUTTON3) && Toolbar.contains(e.getPoint())) {
                //Toolbar
                menu.toolbarClicked(e.getPoint(), e.getButton() == MouseEvent.BUTTON1);
                m = true;
            } else {
                m = false;
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

        public void mouseReleased(MouseEvent e) {
            if (editorState == EditorState.EDIT && !m) {
                int y2;
                int x2;
                if (e.getButton() == MouseEvent.BUTTON1 && mapHandler.mapOpen && !mapHandler.miniMap && menu.selectedTabIndex == 0) {
                    x2 = Math.floorDiv(mapHandler.getOrigin().x + e.getX(), mapHandler.tileSpacing);
                    y2 = Math.floorDiv(mapHandler.getOrigin().y + e.getY(), mapHandler.tileSpacing);
                    int x_dif, y_dif;

                    if (x2 > x1) x_dif = x2 - x1 + 1;
                    else x_dif = x1 - x2 + 1;
                    if (y2 > y1) y_dif = y2 - y1 + 1;
                    else y_dif = y1 - y2 + 1;
                    int a = 0, b = 0;

                    if (x2 > x1 && y2 > y1) do {
                        do {
                            mapHandler.setGround(dataHandler.fields.get(menu.Tabs[0].selected), new Point(x1 + a, y1 + b));
                            a++;
                        } while (a < x_dif);
                        if (a == x_dif) a = 0;
                        b++;
                    } while (b < y_dif);

                    else if (x2 < x1 && y2 < y1) do {
                        do {
                            mapHandler.setGround(dataHandler.fields.get(menu.Tabs[0].selected), new Point(x1 - a, y1 - b));
                            a++;
                        } while (a < x_dif);
                        if (a == x_dif) a = 0;
                        b++;
                    } while (b < y_dif);

                    else if (x2 > x1 && y2 < y1) do {
                        do {
                            mapHandler.setGround(dataHandler.fields.get(menu.Tabs[0].selected), new Point(x1 + a, y1 - b));
                            a++;
                        } while (a < x_dif);
                        if (a == x_dif) a = 0;
                        b++;
                    } while (b < y_dif);

                    else do {
                            do {
                                mapHandler.setGround(dataHandler.fields.get(menu.Tabs[0].selected), new Point(x1 - a, y1 + b));
                                a++;
                            } while (a < x_dif);
                            if (a == x_dif) a = 0;
                            b++;
                        } while (b < y_dif);
                }

                if ((e.getButton() == MouseEvent.BUTTON1 || e.getButton() == MouseEvent.BUTTON3) && mapHandler.mapOpen && !mapHandler.miniMap && menu.selectedTabIndex == 1) {
                    x2 = e.getX() / mapHandler.tileSize;
                    y2 = e.getY() / mapHandler.tileSize;
                    int xdif, ydif;

                    if (x2 > x1)
                        xdif = x2 - x1 + 1;
                    else
                        xdif = x1 - x2 + 1;
                    if (y2 > y1)
                        ydif = y2 - y1 + 1;
                    else
                        ydif = y1 - y2 + 1;
                    int a = 0, b = 0;

                    if (x2 > x1 && y2 > y1) do {
                        do {
                            if(e.getButton() == MouseEvent.BUTTON1)
                                mapHandler.setPlant(dataHandler.plants.get(menu.Tabs[1].selected), new Point(x1 + a, y1 + b));
                            else
                                mapHandler.setPlant(null, new Point(x1 + a, y1 + b));
                            a++;
                        } while (a < xdif);
                        if (a == xdif) a = 0;
                        b++;
                    } while (b < ydif);

                    else if (x2 < x1 && y2 < y1) do {
                        do {
                            if(e.getButton() == MouseEvent.BUTTON1)
                                mapHandler.setPlant(dataHandler.plants.get(menu.Tabs[1].selected), new Point(x1 - a, y1 - b));
                            else
                                mapHandler.setPlant(null, new Point(x1 - a, y1 - b));
                            a++;
                        } while (a < xdif);
                        if (a == xdif) a = 0;
                        b++;
                    } while (b < ydif);

                    else if (x2 > x1 && y2 < y1) do {
                        do {
                            if(e.getButton() == MouseEvent.BUTTON1)
                                mapHandler.setPlant(dataHandler.plants.get(menu.Tabs[1].selected), new Point(x1 + a, y1 - b));
                            else
                                mapHandler.setPlant(null, new Point(x1 + a, y1 - b));
                            a++;
                        } while (a < xdif);
                        if (a == xdif) a = 0;
                        b++;
                    } while (b < ydif);

                    else do {
                            do {
                                if(e.getButton() == MouseEvent.BUTTON1)
                                    mapHandler.setPlant(dataHandler.plants.get(menu.Tabs[1].selected), new Point(x1 - a, y1 + b));
                                else
                                    mapHandler.setPlant(null, new Point(x1 - a, y1 + b));
                                a++;
                            } while (a < xdif);
                            if (a == xdif) a = 0;
                            b++;
                        } while (b < ydif);
                }

                dragging = false;
            } else if (editorState == EditorState.MOVE && !m) {
                if (e.getButton() == MouseEvent.BUTTON1 && mapHandler.mapOpen && !mapHandler.miniMap) {
                    Point newOrigin = new Point(mapHandler.getOrigin().x + mxa1 - e.getX(), mapHandler.getOrigin().y + mya1 - e.getY());
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