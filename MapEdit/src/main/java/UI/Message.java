package UI;

import java.awt.*;

public class Message {

    public enum Type {
        ERROR, TOOLTIP, INFO
    }

    public String text;
    public Type type;
    public int timeToLive;
    public Font font;
    public Point position;
    public Color backgroundColor;
    public Color fontColor;

    public Message(String text, Type type) {
        this.text = text;
        this.type = type;
        this.timeToLive = 100;
        this.font = new Font("Arial", Font.PLAIN, 15);
        this.position = new Point(500, 500);
        this.fontColor = Color.lightGray;
        this.backgroundColor = new Color(21, 21, 21, 182);
    }


    public Message(String text, Type type, int timeToLive, Font font, Point position, Color backgroundColor, Color fontColor) {
        this.text = text;
        this.type = type;
        this.timeToLive = timeToLive;
        this.font = font;
        this.position = position;
        this.backgroundColor = backgroundColor;
        this.fontColor = fontColor;
    }
}
