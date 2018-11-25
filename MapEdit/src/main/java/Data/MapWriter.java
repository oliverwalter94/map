package Data;

import main.MapHandler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;


public class MapWriter {

	public static String FileName;
	//	static BufferedImage a = new BufferedImage(Map.map_xsize * 3, Map.map_ysize, BufferedImage.TYPE_INT_RGB);
//	static BufferedImage b = new BufferedImage(Map.map_xsize * 2 + 2, Map.map_ysize, BufferedImage.TYPE_INT_RGB);
	static int encrypt = 1;
	static String MAPDIR;
    private String saveName;
    private MapHandler mapHandler;


    public MapWriter(String saveName, MapHandler mapHandler) {
        this.saveName = saveName;
        this.mapHandler = mapHandler;
    }



	private static void writer_v1() {
//		int x=0,y=0,ix=0,iy=0;
//		do {
//			do {
//				a.setRGB(ix, iy, main.Board.MAP[x][y].field*encrypt);
//				y++;
//				iy++;
//			}while (y < Map.map_ysize);
//			y=0;
//			iy = 0;
//			x++;
//			ix++;
//		}while (x < Map.map_xsize);
//		x = 0;
//		y = 0;
//		iy = 0;
//		do {
//			do {
////		    	a.setRGB(ix,iy,main.Board.MAP[x][y].biome*encrypt);
//				y++;
//				iy++;
//			}while (y < Map.map_ysize);
//			y=0;
//			iy=0;
//			x++;
//			ix++;
//		}while (x < Map.map_xsize);
//
//		x = 0;
//		y = 0;
//		iy = 0;
//		do {
//			do {
//				a.setRGB(ix, iy, main.Board.MAP[x][y].plant * encrypt);
//				y++;
//				iy++;
//			}while (y < Map.map_ysize);
//			y=0;
//			iy=0;
//			x++;
//			ix++;
//		}while (x < Map.map_xsize);
//		File f = new File(FileName + ".map");
//		try {
//			ImageIO.write(a, "PNG", f);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}

	private static void writer_v0() {
//		//setColors();
//		int x = 0;
//		int y = 0;
//		int ix = 0;
//		int iy = 0;
//		do {
//			do {
//				switch(main.Board.MAP[x][y].field){
//					case 1:{
//						b.setRGB(ix, iy, Color.blue.getRGB());
//						break;
//					}
//					case 2:{
//						b.setRGB(ix, iy, Color .blue.getRGB());
//						break;
//					}
//					case 3:{
//						b.setRGB(ix, iy, Color.green.getRGB());
//						break;
//					}
//					case 4:{
//						b.setRGB(ix, iy, Color.yellow.getRGB());
//						break;
//					}
//					case 5:{
//						b.setRGB(ix, iy, Color.gray.getRGB());
//						break;
//					}
//					case 6:{
//						b.setRGB(ix, iy, Color.orange.getRGB());
//						break;
//					}
//					default:{
//						b.setRGB(ix,iy,Color.black.getRGB());
//						break;
//					}
//				}
//				y++;
//				iy++;
//			}while (y < Map.map_ysize);
//			y=0;
//			iy = 0;
//			x++;
//			ix++;
//		}while (x < Map.map_xsize);
//
//		ix+=2;
//		x = 0;
//		y = 0;
//		iy = 0;
//
//		do {
//			do {
////		    	switch(main.Board.MAP[x][y].biome){
////		    	case 0:{
////		    		b.setRGB(ix, iy, Color.BLUE.getRGB());
////		    		break;
////		    	}
////		    	case 1:{
////		    		b.setRGB(ix, iy, Color.ORANGE.getRGB());
////		    		break;
////		    	}
////		    	case 2:{
////		    		b.setRGB(ix, iy, Color.green.getRGB());
////		    		break;
////		    	}
////		    	case 3:{
////		    		b.setRGB(ix, iy, Color.LIGHT_GRAY.getRGB());
////		    		break;
////		    	}
////		    	case 4:{
////		    		b.setRGB(ix, iy, Color.DARK_GRAY.getRGB());
////		    		break;
////		    	}
////		    	case 5:{
////		    		b.setRGB(ix, iy, Color.GRAY.getRGB());
////		    		break;
////		    	}
////		    	case 6:{
////		    		b.setRGB(ix, iy, Color.YELLOW.getRGB());
////		    		break;
////		    	}
////		    	default:{
////		    		b.setRGB(ix,iy,Color.black.getRGB());
////		    		break;
////		    	}
////		    	}
//				y++;
//				iy++;
//			}while (y < Map.map_ysize);
//			y=0;
//			iy=0;
//			x++;
//			ix++;
//		}while (x < Map.map_xsize);
//		File f = new File(FileName + ".png");
//		try {
//			ImageIO.write(b, "PNG", f);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}

    private void createProperties() throws FileNotFoundException {

        File file = new File(MAPDIR + "/properties.prop");
        if (file.exists()) {
            PrintWriter pw = new PrintWriter(file);
            pw.println("MAPNAME: " + mapHandler.saveName);
            pw.println("EDITORVERSION: " + mapHandler.editorVersion);
            pw.println("LOADED IMAGES: " + mapHandler.board.dataHandler.images.size());
            pw.println("LOADED FIELDS: " + mapHandler.board.dataHandler.fields.size());
        }

    }

    private void createSaveDir(String saveName) {
        Installer.runInstaller();
        MAPDIR = System.getProperty("user.home") + "/2D Game/Saves/" + saveName;
        File MapDIR = new File(MAPDIR);
        if (!MapDIR.exists()) {
            boolean result = MapDIR.mkdir();
            if (result) System.out.println("Save Dir created");
        }
        FileName = MAPDIR + "/" + "map.mp";
//    	if(type == 0) {
//    		writer_v0();
//    		writer_v1();
//    	}
//    	else if (type == 1)writer_v0();
//    	else if (type == 2)writer_v1();
//        try {
//            propWriter();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
    }

}
