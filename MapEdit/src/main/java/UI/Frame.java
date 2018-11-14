package UI;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class Frame {
    //Render Variables
    public Color backgroundColor = Color.gray;
    public Color foregroundColor = Color.white;
    public Color borderColor = Color.lightGray;
    public Font font;

    ArrayList<Label> labels;
    ArrayList<TextBox> textBoxes;

    Rectangle2D hitBox;

    Point position;
    public int width;
    public int height;
    public String title;

    public Frame() {

    }


    public void render(Graphics2D g2d) {
        g2d.setColor(backgroundColor);
        if (position == null)
            position = new Point(400, 400);

        g2d.fill(new Rectangle2D.Double(position.x, position.y, width, height));
        if (title != null)
            g2d.drawString(title, position.x + width / 2, position.y + 30);

        if (labels != null)
            for (Label label : labels)
                label.render(g2d);


        if (textBoxes != null)
            for (TextBox textBox : textBoxes)
                textBox.render(g2d);
    }

    public void onClick(Point position) {

    }

    public void addLabel(String text, Point position) {
        if (labels == null)
            labels = new ArrayList<>();
        Label label = new Label(text, position);
        labels.add(label);
    }

    public void addTextbox(Point position, int w, int h) {
        boolean firstItem = false;
        if (textBoxes == null) {
            textBoxes = new ArrayList<>();
            firstItem = true;
        }
        TextBox textBox = new TextBox(position, w, h);
        if (firstItem)
            textBox.e_active = true;
        textBoxes.add(textBox);
    }

    private class Element {
        Point e_position;
        int e_height;
        int e_width;
        Color e_borderColor;
        Color e_backGroundColor;
        Color e_activeColor;
        String e_text;
        Boolean e_visible;
        Boolean e_active;
        Boolean e_backGround;
        Boolean e_border;

        Element(String text, Point position) {
            e_text = text;
            e_position = position;
            e_backGroundColor = backgroundColor;
            e_activeColor = Color.lightGray;
            e_borderColor = borderColor;
            e_active = false;
            e_visible = true;
            e_backGround = true;
            e_border = false;
            e_height = 40;
            e_width = 50;

        }


        public void render(Graphics2D g2d) {
            if (e_visible) {
                if (e_backGround) {
                    if (e_active)
                        g2d.setColor(e_activeColor);
                    else
                        g2d.setColor(e_backGroundColor);
                    g2d.fill(new Rectangle2D.Double(position.x + this.e_position.x, position.y + this.e_position.y, e_width, e_height));
                }
                if (e_border) {
                    g2d.setColor(e_borderColor);
                    g2d.draw(new Rectangle2D.Double(position.x + this.e_position.x, position.y + this.e_position.y, e_width, e_height));
                }

                g2d.setColor(foregroundColor);
                g2d.drawString(e_text, position.x + this.e_position.x + 2, position.y + this.e_position.y + 2);
            }
        }

    }

    private class TextBox extends Element {

        TextBox(Point position, int width, int height) {
            super("", position);
            e_width = width;
            e_height = height;
        }
    }

    private class Label extends Element {
        public Label(String text, Point position) {
            super(text, position);
            e_backGround = false;
        }

    }

}
