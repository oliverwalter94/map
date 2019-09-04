package Entities;

import Data.ImageObject;

import java.awt.*;

public class Player extends NPC {


    public Player(Point position, String name, ImageObject imageObject) {
        super(position, name, new Point(16, 16), imageObject);
    }

    @Override
    public void render() {
        super.render();
    }
}
