package ProgettoJava;

import java.io.File;

public class ProgettoJava {
    public static void main(String[] args) {
        Player p = new Player(7, 5);

        String mapFilePath = "src/maps/map1.map";
        File mapFile = new File(mapFilePath);
        if (!mapFile.exists()) {
            System.err.println("Map file not found: " + mapFilePath);
            return;
        }
        MapBuilder mapBuilder = new MapBuilder(mapFilePath);
        @SuppressWarnings("unused")
        MapWindow mapWindow = new MapWindow(mapBuilder, p);
    }
}
