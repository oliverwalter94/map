package Data;

import java.awt.*;
import java.awt.image.BufferedImage;

public class MapReader{

	public static void readMap(String dir){
//		try{
//			File file = new File(dir);
//			Image image = ImageIO.read(file);
//			BufferedImage map=bufferImage(image,BufferedImage.TYPE_INT_RGB);
//			int w = Map.map_xsize;
//			int h = Map.map_ysize;
//			int x=0,y=0;
//			do{
//				do{
//					int c = 16777216 + map.getRGB(x+Map.map_xsize*2,y);
//					int b = 16777216 + map.getRGB(x+Map.map_xsize,y);
//					int a = 16777216 + map.getRGB(x, y);
////					main.Board.MAP[x][y] = new MapGen.Map(a,b,c,0);
//					x++;
//				}while (x < w);
//				if (x == w){
//					x = 0;
//					y++;
//				}
//			} while (y < h);
////			main.Board.MapOpen = true;
//			//GameRender.drawMap(main.Board.g2d, new Dimension(500,500),main.Board.images);
//		} catch(Exception e){
//			System.out.println("Your MapGen.Map could not be loaded. Either your map does not match the right map format or it was not a MapGen.Map.");
//		}
	}

	public static BufferedImage bufferImage(Image image, int type) {
		BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), type);
		Graphics2D g = bufferedImage.createGraphics();
		g.drawImage(image, null, null);
		return bufferedImage;
	}
}

