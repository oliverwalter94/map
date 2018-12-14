package UI;

import Data.DataHandler;
import Data.ImageObject;

import java.awt.*;

public class ImagePicker extends UIElement {

    public ImageObject selection;
    boolean done = false;
    public ImageObject[][] images;
    DataHandler data;
    public int itemsPerRow = 8;
    public Point origin = new Point(0, 200);
    public int spacing = 16;
    public int imageSize = 64;

    public ImagePicker(DataHandler dataHandler) {
        super(new Point(0, 0), "imagePicker");
        data = dataHandler;
        reset();
        selection = images[0][0];
    }

    void reset() {
        int a = (int) Math.ceil((float) data.images.size() / itemsPerRow);
        images = new ImageObject[itemsPerRow][a];

        int x = 0;
        int y = 0;
        for (int i = 0; i < data.images.size(); i++) {
            if (x == itemsPerRow) {
                x = 0;
                y++;
            }
            images[x][y] = data.images.get(i);
            x++;
        }
        done = false;
        selection = null;
    }

    void clicked(Point position) {

    }

    @Override
    public void render(Graphics2D g2d) {

    }
}