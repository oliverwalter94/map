
import java.util.ArrayList;
import java.util.Random;


	
public class MapGenerator {

		static int counter = 1;
		static Random R = new Random();
	    static int map_xsize = Map.map_xsize;
	    static int map_ysize = Map.map_ysize;
	    boolean walkOnWater = true;
	    static int riverAmount = 5;
	    
		public ArrayList<Biome> biomes;
		public ArrayList<Field> fields;
		
		public MapGenerator(ArrayList<Biome> b, ArrayList<Field> f) {
			biomes = b;
			fields = f;
		}
		
	    public void generateNewMap(){
	        int x_c = 0;
	        int y_c = 0;
	        do{
	            do{
	                int a = R.nextInt(101);
	                int b = R.nextInt(6) + 1;
	                if (x_c == 0 || y_c == 0 || x_c == (map_xsize - 1) || y_c == (map_ysize - 1) || x_c == 1 || y_c == 1 || x_c == (map_xsize - 2) || y_c == (map_ysize - 2))
	                {
	                	Board.MAP[x_c][ y_c] = new Map(0, 0, 0,0);
	                }
	                else if( x_c == 2 && y_c == 2){
	                    Board.MAP[x_c][y_c] = new Map(0,5,0,0);
	                }
	                else{
	                    if (a <= 95){
	                        if (y_c != 2 && x_c != 2){
	                            if (Board.MAP[x_c][y_c - 1].biome == Board.MAP[x_c - 1][y_c].biome){
	                                Board.MAP[x_c][y_c] = new Map(0, Board.MAP[x_c][y_c - 1].biome,0,0);
	                            }
	                            else{
	                                int i = R.nextInt(2);
	                                    if(i == 0)
	                                Board.MAP[x_c][y_c] = new Map(0, Board.MAP[x_c][y_c - 1].biome,0,0);
	                                    else
	                                Board.MAP[x_c][y_c] = new Map(0, Board.MAP[x_c - 1][y_c].biome,0,0);
	                            }
	                        }
	                        else if (x_c == 2){
	                            Board.MAP[x_c][y_c] = new Map(0, Board.MAP[x_c][y_c - 1].biome,0,0);
	                        }
	                        else if (y_c == 2){
	                            Board.MAP[x_c][y_c] = new Map(0, Board.MAP[x_c - 1][y_c].biome,0,0);
	                        }
	                    }
	                    else{
	                    	if (b ==1) do{
	                    		b = R.nextInt(6) + 1;
	                    	} while(b==1);
	                    	Board.MAP[x_c][y_c] = new Map(0,b,0,0);
	                    }
	                }
	                x_c++;
	            } while (x_c < map_xsize);
	            if (x_c == map_xsize)
	            {
	                x_c = 0;
	                y_c++;
	            }
	        } while (y_c < map_ysize);
	        x_c = 0;
	        y_c = 0;
	        int twice = 0;
	        do{
	        do{
	            do{
	                if (x_c >= 2 && y_c >= 2 && x_c <= map_xsize-2 && y_c <= map_ysize-2){
	                	int b = Board.MAP[x_c][y_c].biome;
	                	if(!check_up(x_c, y_c, b, 1, true) && !check_down(x_c, y_c, b, 1, true) && !check_left(x_c, y_c, b, 1, true) && !check_right(x_c, y_c, b, 1, true))
	                		Board.MAP[x_c][y_c] = new Map(0,Board.MAP[x_c-1][y_c].biome,0,0);                
	                	}
	                x_c++;
	            } while (x_c < map_xsize);
	            if (x_c == map_xsize)
	            {
	                x_c = 0;
	                y_c++;
	            }
	        } while (y_c < map_ysize);
	        twice++;
	        }while (twice < 2);
	        x_c = 0;
	        y_c = 0;

	        generateCoast();
	        
	        create_rivers();
	        
	        generateBlocksNew(x_c, y_c);
	    }
	    
	    public void generateNewEmptyMap(){
	        int x_c = 0;
	        int y_c = 0;
	        do{
	            do{
	                Board.MAP[x_c][y_c] = new Map(1,0,0,0);
	                x_c++;
	            } while (x_c < map_xsize);
	            if (x_c == map_xsize){
	                x_c = 0;
	                y_c++;
	            }
	        } while (y_c < map_ysize);
	    }
	    
	    public static void clearMap(){
	        int x_c = 0;
	        int y_c = 0;
	        do{
	            do{
	                Board.MAP[x_c][y_c] = null;
	                x_c++;
	            } while (x_c < map_xsize);
	            if (x_c == map_xsize){
	                x_c = 0;
	                y_c++;
	            }
	        } while (y_c < map_ysize);
	    }

//OLD:
//	1 seawater
//	2 freshwater
//	3 floatingwater
//	4 grass
//	5 sand
//	6 gravel
//	7 farmland
	    
	    static int sumOfFieldChances(ArrayList<FieldWeight> fields) {
	    	int result = 0;
	    	for (FieldWeight f:fields) {
	    		result += f.weight;
	    	}
	    	return result;
	    }
	    static int sumOfPlantChances(ArrayList<PlantWeight> plants) {
	    	int result = 0;
	    	for (PlantWeight p:plants) {
	    		result += p.weight;
	    	}
	    	return result;
	    }
	    
	    void generateBlocksNew(int x_c, int y_c) {
	    	
	    	int x = 0;
	    	int y = 0;
        	do{
	            do {
	    	    	int b = Board.MAP[x][y].biome;
	    	    		//System.out.println(biomes.get(b).fieldweights.get(0).field);
	            	if(biomes.get(b).fieldweights.size() == 1)
	            		Board.MAP[x][y].id = fields.indexOf(biomes.get(b).fieldweights.get(0).field);
	            	else {
	            		
	            	}
	                x++;
	            } while (x < map_xsize);
	            if (x == map_xsize)
	            {
	                x = 0;
	                y++;
	            }
	        } while (y < map_ysize);
	        x = 0;
	        y = 0;
	    }
	    
	    static void generateBlocks(int x_c, int y_c){
	    	do{
	            do {
//	            	int a = R.nextInt(10);
//	                switch (Board.MAP[x_c][y_c].biome)
//	                {
//	                case 0:{
//	                	Board.MAP[x_c][y_c].id = 1;// ocean
//	                	break;
//	                }
//	                case 1:{
//	                	Board.MAP[x_c][y_c].id = 4; //
//	                	break;
//	                }
//	                case 2:{
//	                	Board.MAP[x_c][y_c].id = 3;
//	                	if (a == 1)Board.MAP[x_c][y_c].subId = 1;
//	                	else if(a == 3) Board.MAP[x_c][y_c].subId = 3;
//	                	else if (a == 5) Board.MAP[x_c][y_c].subId = 6;
//	                	break;
//	                }
//	                case 3:{
//	                	Board.MAP[x_c][y_c].id = 3;
//	                	if (a <= 8)Board.MAP[x_c][y_c].subId = 1;
//	                	break;
//	                }
//	                case 4:{
//	                	Board.MAP[x_c][y_c].id = 3;
//	                	if (a <= 8)Board.MAP[x_c][y_c].subId = 2;
//	                	break;
//	                }
//	                case 5:{
//	                	Board.MAP[x_c][y_c].id = 3;
//	                	if (a <= 4)Board.MAP[x_c][y_c].subId = 1;
//	                	else if (a <= 8)Board.MAP[x_c][y_c].subId = 2;
//	                	break;
//	                }
//	                case 6:{
//	                	Board.MAP[x_c][y_c].id = 4;
//	                	if (a == 4)Board.MAP[x_c][y_c].subId = 4;
//	                	break;
//	                }
//	                case 8:{
//	                	Board.MAP[x_c][y_c].id = 1;
//	                	break;
//	                }
//	                }
//	                x_c++;
	            } while (x_c < map_xsize);
	            if (x_c == map_xsize)
	            {
	                x_c = 0;
	                y_c++;
	            }
	        } while (y_c < map_ysize);
	        x_c = 0;
	        y_c = 0;
	    }
	  
		
	    void generateCoast() {
//	        int xc = 0;
//	        int yc = 0;
//	        int i = 0;
//	        do{
//	        do{
//	            do{
//	                if (Board.MAP[xc][yc].biome != 0) {
//	                    if (check_left(xc,yc, 0, 1,true) == true || check_up(xc,yc, 0, 1,true) == true || check_right(xc,yc, 0, 1,true) == true || check_down(xc,yc, 0, 1,true) == true){
//	                    	if (R.nextInt(4) == 0){
//	                            Board.MAP[xc][yc].biome = 0;
//	                            Board.MAP[xc][yc].id = 1;
//	                        }
//	                    }
//	                }
//	                xc++;
//	            } while (xc < map_xsize);
//	            if (xc == map_xsize){
//	                xc = 0;
//	               yc++;
//	            }
//	        } while (yc < map_ysize);
//	       xc = 0;
//	       yc = 0;
//	       i++;
//	       }while (i < 4);
//	       do
//	       {
//	           do
//	           {
//	               if (Board.MAP[xc][yc].biome != 0)
//	               {
//	                   if (check_left(xc,yc, 0, 1,true) == true || check_up(xc,yc, 0, 1,true) == true || check_right(xc,yc, 0, 1,true) == true || check_down(xc,yc, 0, 1,true) == true){
//	                       Board.MAP[xc][yc].biome = 1;
//	                       Board.MAP[xc][yc].id = 4;
//	                   }
//	                   if (check_left(xc,yc, 1, 1,true) == true || check_up(xc,yc, 1, 1,true) == true || check_right(xc,yc, 1, 1,true) == true || check_down(xc,yc, 1, 1,true) == true){
//	                   	if (R.nextInt(3) == 1){
//	                       Board.MAP[xc][yc].biome = 1;
//	                       Board.MAP[xc][yc].id = 4;
//	                   	}
//	                   }
//	               }
//	               xc++;
//	           } while (xc < map_xsize);
//	           if (xc == map_xsize){
//	               xc = 0;
//	              yc++;
//	           }
//	       } while (yc < map_ysize);
	    }

	    boolean check_right(int x, int y, int id, int distance,boolean biome){
	        if (x + distance <= map_xsize){
	            if (biome == false){
	                if (Board.MAP[x + distance][y].id == id) return true;
	                else return false;
	            }
	            else{
	                if (Board.MAP[x + distance][y].biome == id) return true;
	                else return false;
	            }
	        }
	        else return false;
	    }

	    static boolean check_left(int x, int y, int id, int distance, boolean biome){
	        if (x - distance >= 0){
	            if (biome == false) {
	                if (Board.MAP[x - distance][y].id == id) return true;
	                else return false;
	            }
	            else{
	                if (Board.MAP[x - distance][y].biome == id) return true;
	                else return false;
	            }
	        }
	        else return false;
	    }

	    static boolean check_up(int x, int y, int id, int distance, boolean biome){
	        if (y - distance >= 0){
	            if (biome == false){
	                if (Board.MAP[x][y - distance].id == id) return true;
	                else return false;
	            }
	            else{
	                if (Board.MAP[x][y - distance].biome == id)  return true;
	                else return false;
	            }
	        }
	        else return false;
	    }

	    static boolean check_down(int x, int y, int id, int distance, boolean biome){
	        if (y  + distance <= map_xsize){
	            if (biome == false){
	                if (Board.MAP[x][ y + distance].id == id) return true;
	                else  return false;
	            }
	            else {
	                if (Board.MAP[x][y + distance].biome == id)  return true;
	                else return false;
	            }
	        }
	        else return false;
	    }

	   

	    public int ReturnBiome(int x,int y){
	    	return Board.MAP[((x-(x%32))/32)][((y-(y%32))/32)].biome;
	    }
	    
	    public static void create_rivers(){
	        int rivercounter = 0;
	        int prevx = 0;
	        int prevy = 0;
	        do{
	            int x = R.nextInt((map_xsize - 10)) + 5;
	            int y = R.nextInt((map_ysize - 10)) + 5;
	            int dir = R.nextInt(4);
	            int c = 0;
	            boolean a = false;
	            boolean b = false;
	            switch (dir){
	                case 0: {
	                        do{
	                        	if (c == 0)Board.MAP[x][y].adInf = 0;
	                            Board.MAP[x][y].biome = 2;
	                            int r = R.nextInt(5);
	                            if (r == 1 && !b){
	                            	
	                                x++;
	                                Board.MAP[x][y].biome = 2;
	                                if(c!=0)Board.MAP[prevx][prevy].adInf = 1;
	                                a = true;
	                            }
	                            else if (r == 2 && !a){
	                                x--;
	                                Board.MAP[x][y].biome = 2;
	                                if(c!=0)Board.MAP[prevx][prevy].adInf = 3;
	                                b = true;
	                            }
	                            else{
	                            	Board.MAP[prevx][prevy].adInf = 0;
	                            	b = false;
	                            	a = false;
	                            }
	                            y--;
	                            prevx = x; prevy = y;
	                            c++;
	                        }while (Board.MAP[x][y].biome != 0 && Board.MAP[x][y].biome != 2);
	                        Board.MAP[x][y].adInf = 0;
	                        break;
	                    }
	                case 1:{
	                        do{
	                        	if (c == 0)Board.MAP[x][y].adInf = 1;
	                            Board.MAP[x][y].biome = 2;
	                            int r = R.nextInt(5);
	                            if (r == 1 && !b){
	                                y--;
	                                Board.MAP[x][y].biome = 2;
	                                if(c!=0)Board.MAP[prevx][prevy].adInf = 0;
	                                a = true;
	                            }
	                            else if (r == 2 && !a){
	                                y++;
	                                Board.MAP[x][y].biome = 2;
	                                if(c!=0)Board.MAP[prevx][prevy].adInf = 2;
	                                b = true;
	                            }
	                            else{
	                            	Board.MAP[prevx][prevy].adInf = 1;
	                            	a = false;
	                            	b = false;
	                            }
	                            x++;
	                            prevx = x; prevy = y;
	                            c++;
	                        }while (Board.MAP[x][y].biome != 0 && Board.MAP[x][y].biome != 2);
	                        Board.MAP[x][y].adInf = 1;
	                        break;
	                    }
	                case 2:{
	                        do{
	                        	if (c == 0)Board.MAP[x][y].adInf = 2;
	                            Board.MAP[x][y].biome = 2;
	                            int r = R.nextInt(5);
	                            if (r == 1 && !b){
	                                x--;
	                                Board.MAP[x][y].biome = 2;
	                                if(c!=0)Board.MAP[prevx][prevy].adInf = 3;
	                                a = true;
	                            }
	                            else if (r == 2 && !a){
	                                x++;
	                                Board.MAP[x][y].biome = 2;
	                                if(c!=0)Board.MAP[prevx][prevy].adInf = 1;
	                                b = true;
	                            }
	                            else{
	                            	Board.MAP[prevx][prevy].adInf = 2;
	                            	a = false;
	                            	b = false;
	                            }
	                            y++;
	                            prevx = x; prevy = y;
	                            c++;
	                        }while (Board.MAP[x][y].biome != 0 && Board.MAP[x][y].biome != 8);
	                        Board.MAP[x][y].adInf = 2;
	                        break;
	                    }
	                case 3:{
	                        do{
	                        	if (c == 0)Board.MAP[x][y].adInf = 3;
	                            Board.MAP[x][y].biome = 2;
	                            int r = R.nextInt(5);
	                            if (r == 1 && !b){
	                                y--;
	                                Board.MAP[x][y].biome = 2;
	                                if(c!=0)Board.MAP[prevx][prevy].adInf = 0;
	                                a = true;
	                            }
	                            else if (r == 2 && !a){
	                                y++;
	                                Board.MAP[x][y].biome = 2;
	                                if(c!=0)Board.MAP[prevx][prevy].adInf = 2;
	                                b = true;
	                            }
	                            else{
	                            	a = false;
	                            	b = false;
	                            	Board.MAP[prevx][prevy].adInf = 3;
	                            }
	                            x--;
	                            prevx = x; prevy = y;
	                            c++;
	                        }while (Board.MAP[x][y].biome != 0 && Board.MAP[x][y].biome != 2);
	                        Board.MAP[x][y].adInf =3;
	                        break;
	                    }
	            }
	            rivercounter++;
	        } while (rivercounter < riverAmount);
	    }

}
