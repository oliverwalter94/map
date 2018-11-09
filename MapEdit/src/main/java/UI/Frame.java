package UI;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class Frame {
    //Render Variables
    Color backgroundColor;
    Color foregroundColor;
    Color fontColor;
    Font font;

    ArrayList<Label> labels;
    ArrayList<TextBox> textBoxes;

    Rectangle2D hitBox;

    private class Element {
        Point position;
        int height;
        int width;
        Color frameColor;
        Color backGroundColor;
        Color activeColor;
        String text;
        Boolean visible;

        Element() {

        }


        public void render() {
            if (visible) {

            }
        }

    }

    private class TextBox extends Element {

        TextBox() {
            super();
        }
    }

    private class Label {
        Label() {
            super();
        }
    }

    public void RenderFrame(Graphics2D g2d) {

    }

    public void onClick(Point position) {

    }
}
