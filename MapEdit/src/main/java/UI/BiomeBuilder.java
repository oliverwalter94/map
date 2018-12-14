package UI;

import Data.DataHandler;
import MapGen.Biome;

import java.awt.*;

public class BiomeBuilder extends UIElement {
    public Biome selection;
    boolean saved = false;
    DataHandler data;
    public int spacing = 16;
    public int imageSize = 48;

    public BiomeBuilder(DataHandler dataHandler) {
        super(new Point(0, 0), "biomeBuilder");
        data = dataHandler;
        reset();
        selection = data.biomes.get(0);
    }

    void reset() {

        selection = data.biomes.get(0);
        selection = null;
    }

    void clicked(Point position) {

    }

    @Override
    public void render(Graphics2D g2d) {
        super.render(g2d);
    }

}
