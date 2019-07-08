package UI;

import Data.DataHandler;
import Data.ImageObject;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class ImagePicker extends UIElement {

    public PickerItem selection;
    boolean done = false;
    public ImageObject[][] images;
    DataHandler data;
    public int itemsPerRow = 8;
    public Point origin = new Point(0, 200);
    public int spacing = 16;
    public int imageSize = 64;
    public ArrayList<PickerItem> pickerItemList;
    public UIButton chooseSelected;

    class PickerItem {
        ImageObject image;
        Point position;
        boolean active;

        PickerItem(ImageObject img, Point pos, boolean act) {
            image = img;
            position = pos;
            active = act;
        }

    }

    public ImagePicker(DataHandler dataHandler) {
        super(new Point(0, 0), "imagePicker");
        data = dataHandler;
        pickerItemList = new ArrayList<>();
        reset();
        chooseSelected = new UIButton(new Point(800, 800), "Save");
        chooseSelected.setSize(80, 30);
        chooseSelected.setBackgroundColor(Color.gray);
        this.addChildElement(chooseSelected);
        selection = pickerItemList.get(0);

        this.font = new Font("Calibri", Font.PLAIN, 40);
        this.foregroundColor = Color.lightGray;
        this.backgroundColor = new Color(42, 42, 42);
    }

    void reset() {
        int a = (int) Math.ceil((float) data.images.size() / itemsPerRow);
        images = new ImageObject[itemsPerRow][a];

        int size = imageSize;
        int spacing = this.spacing;

        int x = 0;
        int y = 0;
        for (int i = 0; i < data.images.size(); i++) {
            if (x == itemsPerRow) {
                x = 0;
                y++;
            }
            //images[x][y] = data.images.get(i);
            pickerItemList.add(new PickerItem(data.images.get(i), new Point(x * (size + spacing), y * (size + spacing)), false));

            x++;
        }
        done = false;
        selection = null;
    }

    public void onClick(Point position) {
        Point newPosition = new Point(position.x - origin.x, position.y - origin.y);
        for (PickerItem item : pickerItemList) {
            if (new Rectangle2D.Double(item.position.x, item.position.y, imageSize, imageSize).contains(newPosition)) {
                selection = item;
                return;
            }
        }


    }


    public void render(Graphics2D g2d, Dimension boardSize) {

        //Render Background
        g2d.setColor(backgroundColor);
        g2d.fill(new Rectangle2D.Double(0, 0, boardSize.width, boardSize.height));
        //Render Title Bar
        g2d.setColor(foregroundColor);
        g2d.setFont(font);
        g2d.drawString(selection.image.name, boardSize.width / 2 - (int) g2d.getFontMetrics(font).getStringBounds(selection.image.name, null).getWidth() / 2, 150);
        //Render Images

        origin.x = (boardSize.width / 2) - (itemsPerRow * imageSize + spacing * (itemsPerRow - 1)) / 2;
        for (PickerItem item : pickerItemList) {
            Point pos = new Point(item.position.x + origin.x, item.position.y + origin.y);

            //Set Images background color
            g2d.setColor(Color.gray);
            if (item == selection)
                g2d.setColor(new Color(85, 185, 62));

            g2d.fill(new Rectangle2D.Double(pos.x - 2, pos.y - 2, imageSize + 4, imageSize + 4));
            g2d.drawImage(item.image.img, pos.x, pos.y, imageSize, imageSize, null);
        }

        chooseSelected.position = new Point(boardSize.width - 100, boardSize.height - 50);


        super.render(g2d);
    }
}