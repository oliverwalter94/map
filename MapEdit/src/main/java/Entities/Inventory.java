package Entities;

import java.util.ArrayList;

public class Inventory {
    ArrayList<ItemInstance> items;
    int size;
    int money;
    boolean hasHotbar;
    ArrayList<Item> hotBar;
    int hotbarSize;

    private class ItemInstance {
        Item item;
        int amount;
        float durability;

        private ItemInstance(Item item) {
            this.item = item;
            this.amount = 1;
            if (item.hasDurability)
                this.durability = 100f;
        }
    }
}
