package Entities;

import Data.ImageObject;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Entity {
    int UUID;
    Point position;
    Point velocity;
    boolean show;
    Rectangle2D hitbox;
    Inventory inventory;
    public String title;
    public boolean renderTitle;
    public ImageObject imageObject;
    Point size;

    Entity(Point position, String title, Point size, ImageObject imageObject) {
        this.position = position;
        this.title = title;
        this.show = true;
        this.size = size;
        this.hitbox = new Rectangle2D.Float(position.x, position.y, size.x, size.y);
        this.imageObject = imageObject;
    }

    public void render() {

    }

}
