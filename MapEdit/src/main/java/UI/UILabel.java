package UI;

import java.awt.*;

public class UILabel extends UIElement {

    public UILabel(Point position, String title, String text) {
        super(position, title);
        this.text = text;
        this.background = false;
    }


}
