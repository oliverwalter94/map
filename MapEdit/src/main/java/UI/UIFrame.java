package UI;

import java.awt.*;

public class UIFrame extends UIElement {


    public UIFrame(Point position, String name) {
        super(position, name);
        this.backgroundColor = Color.gray;
        this.foregroundColor = Color.white;
        this.borderColor = Color.lightGray;
        this.background = true;
    }


    public void render(Graphics2D g2d) {
        super.render(g2d);
        if (title != null)
            g2d.drawString(title, position.x + width / 2, position.y + 30);


    }

}
