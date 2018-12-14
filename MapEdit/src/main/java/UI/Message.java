package UI;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

public class Message extends UIElement {

    public enum Type {
        ERROR, TOOLTIP, INFO
    }

    public Type type;
    public int timeToLive;
    public Color backgroundColor;
    public Color fontColor;

    public Message(String text, Type type) {
        //TODO: implement UI Parent stuff
        super(new Point(0, 0), "Message");
        this.text = text;
        this.type = type;
        this.timeToLive = 100;
        this.font = new Font("Arial", Font.PLAIN, 15);
        this.position = new Point(500, 500);
        this.fontColor = Color.lightGray;
        this.backgroundColor = new Color(21, 21, 21, 182);
    }


    public Message(String text, Type type, int timeToLive, Font font, Point position, Color backgroundColor, Color fontColor) {
        super(new Point(0, 0), "Message");
        this.text = text;
        this.type = type;
        this.timeToLive = timeToLive;
        this.font = font;
        this.position = position;
        this.backgroundColor = backgroundColor;
        this.fontColor = fontColor;
    }

    public void render(Graphics2D g2d, Dimension boardSize) {

        g2d.setColor(backgroundColor);
        Rectangle2D bounds = g2d.getFontMetrics(font).getStringBounds(text, null);
        g2d.setFont(font);
        if (type == Type.INFO || type == Type.ERROR) {
            g2d.fill(new RoundRectangle2D.Double((boardSize.getWidth() - bounds.getWidth()) / 2 - 8, 80, bounds.getWidth() + 16, bounds.getHeight() + 8, 7, 7));
            if (type == Type.INFO) g2d.setColor(fontColor);
            else g2d.setColor(Color.red);
            g2d.drawString(text, (int) (boardSize.getWidth() - bounds.getWidth()) / 2, 80 + (int) bounds.getHeight() + 1);
        } else {
            g2d.fill(new RoundRectangle2D.Double(position.x, position.y, bounds.getWidth() + 16, bounds.getHeight() + 8, 7, 7));
            g2d.setColor(fontColor);
            g2d.drawString(text, position.x + 8, position.y + (int) bounds.getHeight());
        }
    }
}
