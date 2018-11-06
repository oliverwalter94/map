import java.awt.*;

public class Message {

    enum Type {
        ERROR, TOOLTIP, INFO
    }

    String text;
    Type type;
    int timeToLive;
    Font font;
    Point position;
    Color background;
    Color fontColor;

    public Message(String text, Type type) {
        this.text = text;
        this.type = type;
        this.timeToLive = 100;
        this.font = Board.menu.font;
        this.position = new Point(500, 500);
        this.background = Board.menu.background;
        this.fontColor = Board.menu.text;
    }


    public Message(String text, Type type, int timeToLive, Font font, Point position, Color background, Color fontColor) {
        this.text = text;
        this.type = type;
        this.timeToLive = timeToLive;
        this.font = font;
        this.position = position;
        this.background = background;
        this.fontColor = fontColor;
    }
}
