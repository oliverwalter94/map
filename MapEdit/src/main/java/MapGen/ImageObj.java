package MapGen;

import java.awt.*;

public class ImageObj {
	public String name;
	public Image img;
	public boolean transparent;
	public int resolution;

	public ImageObj(){
	}

	public ImageObj(String Name, Image Img) {
		name = Name;
		img = Img;
	}

}
