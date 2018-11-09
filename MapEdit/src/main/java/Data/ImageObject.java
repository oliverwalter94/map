package Data;

import java.awt.*;

public class ImageObject {
	public String name;
	public Image img;
	public boolean transparent;
	public int resolution;

    public ImageObject() {
    }

    public ImageObject(String Name, Image Img) {
		name = Name;
		img = Img;
	}

}
