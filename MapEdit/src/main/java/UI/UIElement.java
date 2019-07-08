package UI;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class UIElement {
    Point position;
    Point parentPosition;
    int width = 50;
    int height = 50;

    Image image;

    String title;
    String text;

    boolean visible = true;
    boolean border = false;
    boolean background = false;
    boolean active = true;

    Color borderColor;
    Color backgroundColor;

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean hasBorder() {
        return border;
    }

    public void setBorder(boolean border) {
        this.border = border;
    }

    public boolean hasBackground() {
        return background;
    }

    public void setBackground(boolean background) {
        this.background = background;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Color getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
        this.background = true;
    }

    public Color getActiveColor() {
        return activeColor;
    }

    public void setActiveColor(Color activeColor) {
        this.activeColor = activeColor;
    }

    public Color getForegroundColor() {
        return foregroundColor;
    }

    public void setForegroundColor(Color foregroundColor) {
        this.foregroundColor = foregroundColor;
    }

    public Font getFont() {
        return font;
    }

    public void setFont(Font font) {
        this.font = font;
    }

    Color activeColor;
    Color foregroundColor;

    Font font;

    private ArrayList<UIElement> childElements;


    public UIElement(Point position, String title) {
        this.position = position;

        this.title = title;

        this.parentPosition = new Point(0, 0);
    }

    public void onClick(Point position) {
        System.out.println("Element clicked");
    }

    public void render(Graphics2D g2d) {
        if (visible) {
            if (background) {
                if (active)
                    g2d.setColor(activeColor);
                else
                    g2d.setColor(backgroundColor);
                g2d.fill(new Rectangle2D.Double(position.x + parentPosition.x, position.y + parentPosition.y, width, height));
            }
            if (border) {
                g2d.setColor(borderColor);
                g2d.draw(new Rectangle2D.Double(position.x + parentPosition.x, position.y + parentPosition.y, width, height));
            }

            g2d.setColor(foregroundColor);
            if (text != null)
                g2d.drawString(text, position.x + parentPosition.x + 2, position.y + parentPosition.y + 2);
            if (childElements != null) childElements.forEach(element -> element.render(g2d));
        }
    }

    public void addChildElement(UIElement element) {
        if (childElements == null)
            childElements = new ArrayList<>();
        element.parentPosition = this.position;
        childElements.add(element);


    }
}
