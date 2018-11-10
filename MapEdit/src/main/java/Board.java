import Data.DataHandler;
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
    private static int timestat = 0;
    private static Graphics2D g2d;
    private static int x1, x2, y1, y2;
    static EditorState editorState = EditorState.EDIT;
    private static int mxa1, mya1;


    static Menu menu;
    public static DataHandler dataHandler;
    private static GameRender gameRenderer;
    static MapHandler mapHandler;

    static ArrayList<Message> messages = new ArrayList<>();
    static ArrayList<Frame> frames = new ArrayList<>();

    Board() {

        dataHandler = new DataHandler();
        menu = new Menu();
        mapHandler = new MapHandler(this);
        gameRenderer = new GameRender();

        addMouseListener(new CD());
        setFocusable(true);
        Timer time = new Timer(30, this);
        time.start();

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
        gameRenderer.drawGame(g2d, this.getSize(), mapHandler.mapOpen);
    }

    private class CD extends MouseAdapter {

        boolean m = false;


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
                menu.toolbarClicked(e.getPoint(), e.getButton() == MouseEvent.BUTTON1);
                m = true;
            } else {
                m = false;
                if (editorState == EditorState.EDIT) {
                    if (e.getButton() == MouseEvent.BUTTON1 && mapHandler.mapOpen && !mapHandler.miniMap) {
                        x1 = e.getX() / mapHandler.tileSize;
                        y1 = e.getY() / mapHandler.tileSize;
                    }
                } else if (editorState == EditorState.MOVE) {
                    if (e.getButton() == MouseEvent.BUTTON1 && mapHandler.mapOpen && !mapHandler.miniMap) {
                        mxa1 = e.getX();
                        mya1 = e.getY();
                    }
                }
            }
        }

        //TODO: Add Movement to Map
//        @Override
//        public void mouseDragged(MouseEvent e) {
//            System.out.println("Teset");
//            if (editorState == EditorState.MOVE && e.getButton() == MouseEvent.BUTTON1 && mapHandler.mapOpen && !mapHandler.miniMap) {
//                Point newOrigin = new Point(mapHandler.getOrigin().x + mxa1 - e.getX(),mapHandler.getOrigin().y + mya1 - e.getY());
//                mapHandler.setOrigin(newOrigin);
//            }
//        }

        public void mouseReleased(MouseEvent e) {
            if (editorState == EditorState.EDIT && !m) {
                if (e.getButton() == MouseEvent.BUTTON1 && mapHandler.mapOpen && !mapHandler.miniMap && menu.selectedTabIndex == 0) {
                    x2 = e.getX() / mapHandler.tileSize;
                    y2 = e.getY() / mapHandler.tileSize;
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
//                    Map.mapChange = true;
//                    Map.saved = false;
                }

                if (e.getButton() == MouseEvent.BUTTON1 && mapHandler.mapOpen && !mapHandler.miniMap && menu.selectedTabIndex == 1) {
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
//                    Map.mapChange = true;
//                    Map.saved = false;
                }
            } else if (editorState == EditorState.MOVE && !m) {
                if (e.getButton() == MouseEvent.BUTTON1 && mapHandler.mapOpen && !mapHandler.miniMap) {
                    Point newOrigin = new Point(mapHandler.getOrigin().x + mxa1 - e.getX(), mapHandler.getOrigin().y + mya1 - e.getY());
                    mapHandler.setOrigin(newOrigin);
                    mya1 = -1;
                    mxa1 = -1;
                    mapHandler.calcNeededChunks();
                }
            }
        }
    }
}