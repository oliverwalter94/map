package UI;

import java.awt.*;

public class UIButton extends UIElement {


    public UIButton(Point position, String title) {
        super(position, title);
    }

    @Override
    public void onClick() {
        System.out.println("Clicked Button");
        super.onClick();
    }
}
