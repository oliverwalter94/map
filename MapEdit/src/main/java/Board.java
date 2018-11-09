import Data.DataHandler;
import MapGen.Chunk;
import MapGen.Map;
import MapGen.MapGenerator;
import UI.Frame;
import UI.Message;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class Board extends JPanel implements ActionListener {

    public enum EditorState {
        EDIT, MOVE
    }

    private static final long serialVersionUID = 1L;
    static boolean MapOpen = false;
    private static int timestat = 0;
    private static Graphics2D g2d;
    //    public static Map[][] MAP = new Map[Map.map_xsize][Map.map_ysize];
    private static int x1, x2, y1, y2;
    static EditorState editorState = EditorState.EDIT;
    //    private static int mx1, my1;
    private static int mxa1, mya1;


    static Menu menu;
    public static DataHandler dataHandler;
    private static MapGenerator mapGen;
    private GameRender gameRenderer;

    static ArrayList<Message> messages = new ArrayList<>();
    static ArrayList<Frame> frames = new ArrayList<>();
    static ArrayList<Chunk> chunks = new ArrayList<>();
    static ArrayList<Point> existingChunks = new ArrayList<>();

    static Point origin = new Point(0, 0);
    static Point neededChunks = new Point(0, 0);

    Board() {

        dataHandler = new DataHandler();
        menu = new Menu();
        mapGen = new MapGenerator(dataHandler);

        gameRenderer = new GameRender();

        addMouseListener(new CD());
        setFocusable(true);
        Timer time = new Timer(30, this);
        time.start();
        calcNeededChunks();

        MapOpen = true;
    }

    private void addChunk(int x, int y) {
        if (!chunkExists(x, y)) {
            Chunk c = new Chunk(x, y);
            mapGen.generateChunk(c);
            chunks.add(c);
            existingChunks.add(new Point(x, y));
        }
    }

    private boolean chunkExists(int x, int y) {
        for (Point p : existingChunks)
            if (p.equals(new Point(x, y)))
                return true;
        return false;
    }

    private boolean chunkVisible(Chunk chunk) {
        return false;
    }

    private void calcNeededChunks() {
        int chunkSize = GameRender.tileSize * 16;
        neededChunks.x = (int) Math.ceil(((chunkSize - origin.x % chunkSize) + (double) this.getWidth()) / chunkSize);
        neededChunks.y = (int) Math.ceil(((chunkSize - origin.y % chunkSize) + (double) this.getHeight()) / chunkSize);

        Point topLeft = new Point(Math.floorDiv(origin.x, chunkSize), Math.floorDiv(origin.y, chunkSize));
//        chunks.clear();
        for (int x = topLeft.x; x < topLeft.x + neededChunks.x; x++) {
            for (int y = topLeft.y; y < topLeft.y + neededChunks.y; y++) {
                addChunk(x, y);
            }
        }
        System.out.println(origin);
        addInfoMessage(new Message(topLeft.toString(), Message.Type.INFO));

    }

    public void actionPerformed(ActionEvent e) {
        timestat++;
        if (timestat == 100) timestat = 0;
        repaint();
        updateMessages();
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

    static void addTooltip(Message message, Point pos) {
        messages.removeIf(message1 -> message1.type == Message.Type.TOOLTIP);
        message.position = pos;
        messages.add(message);
    }

    static void addInfoMessage(Message message) {
        messages.removeIf(message1 -> message1.type != Message.Type.TOOLTIP);
        messages.add(message);
    }

    public void paint(Graphics g) {
        super.paint(g);
        g2d = (Graphics2D) g;
        gameRenderer.drawGame(g2d, this.getSize(), MapOpen);
    }

    private class CD extends MouseAdapter {

        boolean m = false;

//        @Override
//        public void (MouseEvent e) {
//            Rectangle2D Toolbar = new Rectangle2D.Double(200, 0, 10000, menu.tabSize);
//            if(Toolbar.contains(e.getPoint()))
//                addTooltip(new Message("Toolbar", Message.Type.TOOLTIP), e.getPoint());
//        }

        public void mousePressed(MouseEvent e) {
            Rectangle2D Tabs = new Rectangle2D.Double(0, 0, menu.tabsPerRow * menu.tabSize, ((menu.activeTabs / 5) + 1) * menu.tabSize);
            Rectangle2D TabContent = new Rectangle2D.Double(0, ((menu.activeTabs / 5) + 1) * menu.tabSize, 200, 10000);
            Rectangle2D Toolbar = new Rectangle2D.Double(200, 0, 10000, menu.tabSize);

            if ((e.getButton() == MouseEvent.BUTTON1 || e.getButton() == MouseEvent.BUTTON3) && Tabs.contains(e.getPoint())) {
                menu.tabClicked(e.getPoint(), e.getButton() == MouseEvent.BUTTON1);
                m = true;
            } else if ((e.getButton() == MouseEvent.BUTTON1 || e.getButton() == MouseEvent.BUTTON3) && TabContent.contains(e.getPoint()) && menu.sidebarVisible) {
                menu.tabContentClicked(e.getPoint(), e.getButton() == MouseEvent.BUTTON1);
                m = true;
            } else if ((e.getButton() == MouseEvent.BUTTON1 || e.getButton() == MouseEvent.BUTTON3) && Toolbar.contains(e.getPoint())) {
                menu.toolbarClicked(e.getPoint(), e.getButton() == MouseEvent.BUTTON1, mapGen);
                m = true;
            } else {
                m = false;
                if (editorState == EditorState.EDIT) {
                    if (e.getButton() == MouseEvent.BUTTON1 && MapOpen && !Map.miniMap) {
                        x1 = e.getX() / GameRender.tileSize;
                        y1 = e.getY() / GameRender.tileSize;
                    }
                } else if (editorState == EditorState.MOVE) {
                    if (e.getButton() == MouseEvent.BUTTON1 && MapOpen && !Map.miniMap) {
//                        mx1 = e.getX() / GameRender.tileSize;
//                        my1 = e.getY() / GameRender.tileSize;
                        mxa1 = e.getX();
                        mya1 = e.getY();
                    }
                }
            }
        }

        //TODO: Add Movement to Map
        @Override
        public void mouseDragged(MouseEvent e) {
            System.out.println("Teset");
            if (editorState == EditorState.MOVE && e.getButton() == MouseEvent.BUTTON1 && MapOpen && !Map.miniMap) {
                origin.x += mxa1 - e.getX();
                origin.y += mya1 - e.getY();
                System.out.println("Test");
            }
        }

        public void mouseReleased(MouseEvent e) {
            if (editorState == EditorState.EDIT && !m) {
                if (e.getButton() == MouseEvent.BUTTON1 && MapOpen && !Map.miniMap && menu.selectedTabIndex == 0) {
                    x2 = e.getX() / GameRender.tileSize;
                    y2 = e.getY() / GameRender.tileSize;
                    int x_dif, y_dif;

                    if (x2 > x1) x_dif = x2 - x1 + 1;
                    else x_dif = x1 - x2 + 1;
                    if (y2 > y1) y_dif = y2 - y1 + 1;
                    else y_dif = y1 - y2 + 1;
                    int a = 0, b = 0;

                    if (x2 > x1 && y2 > y1) do {
                        do {
//                            MAP[x1 + a + GameRender.screenx][y1 + b + GameRender.screeny].field = menu.Tabs[0].selected;
                            a++;
                        } while (a < x_dif);
                        if (a == x_dif) a = 0;
                        b++;
                    } while (b < y_dif);

                    else if (x2 < x1 && y2 < y1) do {
                        do {
//                            MAP[x1 - a + GameRender.screenx][y1 - b + GameRender.screeny].field = menu.Tabs[0].selected;
                            a++;
                        } while (a < x_dif);
                        if (a == x_dif) a = 0;
                        b++;
                    } while (b < y_dif);

                    else if (x2 > x1 && y2 < y1) do {
                        do {
//                            MAP[x1 + a + GameRender.screenx][y1 - b + GameRender.screeny].field = menu.Tabs[0].selected;
                            a++;
                        } while (a < x_dif);
                        if (a == x_dif) a = 0;
                        b++;
                    } while (b < y_dif);

                    else do {
                            do {
//                                MAP[x1 - a + GameRender.screenx][y1 + b + GameRender.screeny].field = menu.Tabs[0].selected;
                                a++;
                            } while (a < x_dif);
                            if (a == x_dif) a = 0;
                            b++;
                        } while (b < y_dif);
                    Map.mapChange = true;
                    gameRenderer.drawGame(g2d, getSize(), true);
                    Map.saved = false;
                }

                if (e.getButton() == MouseEvent.BUTTON1 && MapOpen && !Map.miniMap && menu.selectedTabIndex == 1) {
                    x2 = e.getX() / GameRender.tileSize;
                    y2 = e.getY() / GameRender.tileSize;
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
//                            MAP[x1 + a + GameRender.screenx][y1 + b + GameRender.screeny].plant = menu.Tabs[1].selected;
                            a++;
                        } while (a < xdif);
                        if (a == xdif) a = 0;
                        b++;
                    } while (b < ydif);

                    else if (x2 < x1 && y2 < y1) do {
                        do {
//                            MAP[x1 - a + GameRender.screenx][y1 - b + GameRender.screeny].plant = menu.Tabs[1].selected;
                            a++;
                        } while (a < xdif);
                        if (a == xdif) a = 0;
                        b++;
                    } while (b < ydif);

                    else if (x2 > x1 && y2 < y1) do {
                        do {
//                            MAP[x1 + a + GameRender.screenx][y1 - b + GameRender.screeny].plant = menu.Tabs[1].selected;
                            a++;
                        } while (a < xdif);
                        if (a == xdif) a = 0;
                        b++;
                    } while (b < ydif);

                    else do {
                            do {
//                                MAP[x1 - a + GameRender.screenx][y1 + b + GameRender.screeny].plant = menu.Tabs[1].selected;
                                a++;
                            } while (a < xdif);
                            if (a == xdif) a = 0;
                            b++;
                        } while (b < ydif);
                    Map.mapChange = true;
                    gameRenderer.drawGame(g2d, getSize(), true);
                    Map.saved = false;
                }
            } else if (editorState == EditorState.MOVE && !m) {
                if (e.getButton() == MouseEvent.BUTTON1 && MapOpen && !Map.miniMap) {
//                    int mx2 = e.getX() / GameRender.tileSize;
//                    int my2 = e.getY() / GameRender.tileSize;
//                    int x = mx1 - mx2;
//                    int y = my1 - my2;
//                    GameRender.screenx += x;
//                    GameRender.screeny += y;
                    origin.x += mxa1 - e.getX();
                    origin.y += mya1 - e.getY();
                    mya1 = -1;
                    mxa1 = -1;
//                    if (GameRender.screenx <= 0) GameRender.screenx = 0;
//                    if (GameRender.screeny <= 0) GameRender.screeny = 0;
//                    if (GameRender.screenx >= Map.map_xsize - GameRender.screenXSize)
//                        GameRender.screenx = Map.map_xsize - GameRender.screenXSize;
//                    if (GameRender.screeny >= Map.map_ysize - GameRender.screenYSize)
//                        GameRender.screeny = Map.map_ysize - GameRender.screenYSize;

//                    Map.mapChange = true;
//                    gameRenderer.drawGame(g2d, getSize(), true);
//                    Map.saved = false;
                    calcNeededChunks();
                }
            }
        }
    }
}