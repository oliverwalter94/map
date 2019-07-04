package UI;

import Data.DataHandler;
import Data.ImageObject;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

public class ImagePicker extends UIElement {

    public ImageObject selection;
    boolean done = false;
    public ImageObject[][] images;
    DataHandler data;
    public int itemsPerRow = 8;
    public Point origin = new Point(0, 200);
    public int spacing = 16;
    public int imageSize = 64;
    UILabel title;

    public ImagePicker(DataHandler dataHandler) {

        super(new Point(0, 0), "imagePicker");
        data = dataHandler;
        reset();
        selection = images[0][0];

        title = new UILabel(new Point(0, 0), "TitleBar", "Image Picker");
        title.setBackground(new Color(42, 42, 42));

        title.setForeground(foregroundColor);
        title.setFont(font);
//        g2d.drawString(selection.name, boardSize.width / 2 - (int) g2d.getFontMetrics(font).getStringBounds(selection.name, null).getWidth() / 2, 150);



        this.font = new Font("Calibri", Font.PLAIN, 40);
        this.foregroundColor = Color.lightGray;
        this.backgroundColor = new Color(42, 42, 42);

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


    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("Clicked " + this.title);
    }

    public void render(Graphics2D g2d, Dimension boardSize) {
//        super.render(g2d);

        //Render Background
        g2d.setColor(backgroundColor);
        g2d.fill(new Rectangle2D.Double(0, 0, boardSize.width, boardSize.height));
        //Render Title Bar
        g2d.setColor(foregroundColor);
        g2d.setFont(font);
        g2d.drawString(selection.name, boardSize.width / 2 - (int) g2d.getFontMetrics(font).getStringBounds(selection.name, null).getWidth() / 2, 150);
        //Render Images
        //Set Images background color
        g2d.setColor(Color.gray);

        int size = imageSize;
        int spacing = this.spacing;
        for (int x = 0; x < itemsPerRow; x++) {
            for (int y = 0; y < images[0].length; y++) {
                if (images[x][y] != null) {
                    if (images[x][y].transparent)
                        g2d.fill(new Rectangle2D.Double(origin.x + x * (size + spacing), origin.y + y * (size + spacing), size, size));
                    g2d.drawImage(images[x][y].img, origin.x + x * (size + spacing), origin.y + y * (size + spacing), size, size, null);
                }
            }
        }
    }
}