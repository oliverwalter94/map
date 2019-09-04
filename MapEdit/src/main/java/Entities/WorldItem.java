package Entities;

import java.awt.*;

public class WorldItem extends Entity {
    Item item;

    public WorldItem(Item item, Point position) {
        super(position, item.name, new Point(8, 8), item.imageObject);
        this.item = item;
    }
}
