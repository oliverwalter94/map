package UI;

import java.awt.*;
import java.awt.event.MouseEvent;

public class UIButton extends UIElement {


    public UIButton(Point position, String title) {
        super(position, title);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
        System.out.println("Clicked " + this.title);
    }

}
