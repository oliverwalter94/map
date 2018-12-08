package UI;

import java.awt.*;

public class UITextbox extends UIElement {

    public UITextbox(Point position, String title) {
        super(position, title);
    }

    public UITextbox(Point position, String title, int width, int height) {
        super(position, title);
        this.width = width;
        this.height = height;
        this.border = true;
        this.borderColor = new Color(68, 68, 68, 255);
    }


}
