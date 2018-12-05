package main;

import Data.DataHandler;
import Data.ImageObject;

public class ImagePicker {

    ImageObject selection;
    boolean done = false;
    ImageObject[][] images;
    DataHandler data;
    int itemsPerRow = 8;

    public ImagePicker(DataHandler dataHandler) {
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

    void clicked(int x, int y) {

    }
}
