package Data;

import java.io.File;

public class Installer {

    public static void runInstaller(){
        String home = System.getProperty("user.home") + "/2D Game";
        File gameDir = new File(home);
        if (!gameDir.exists()){
            boolean result = gameDir.mkdir();
            if(result)System.out.println("Home Directory created");
        }
        File texturesDir = new File(home + "/Textures");
        if (!texturesDir.exists()){
            boolean result = texturesDir.mkdir();
            if(result)System.out.println("DIR created");
        }
        File mapsDir = new File(home + "/Saves");
        if (!mapsDir.exists()){
            boolean result = mapsDir.mkdir();
            if(result)System.out.println("DIR created");
        }
        File mobsDir = new File(home + "/Technical/Mobs");
        if (!mobsDir.exists()){
            boolean result = mobsDir.mkdir();
            if(result)System.out.println("DIR created");
        }
        File spawnDir = new File(home + "/Technical/Spawner");
        if (!spawnDir.exists()){
            boolean result = spawnDir.mkdir();
            if(result)System.out.println("DIR created");
        }
    }
}
