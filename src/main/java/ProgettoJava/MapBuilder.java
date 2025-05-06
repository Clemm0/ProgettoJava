package ProgettoJava;

import java.awt.Color;
import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JPanel;

public class MapBuilder extends JPanel {
    private int[][] map;
    private final int TILE_SIZE = 20; 

    public MapBuilder(String mapFilePath) {
        loadMap(mapFilePath);
    }

    private void loadMap(String mapFilePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(mapFilePath))) {
            String line;
            int rows = 0;
            int cols = 0;
            
            while ((line = reader.readLine()) != null) {
                cols = Math.max(cols, line.length());
                rows++;
            }

            map = new int[rows][cols];

            reader.close();
            try (BufferedReader secondReader = new BufferedReader(new FileReader(mapFilePath))) {
                int row = 0;
                while ((line = secondReader.readLine()) != null) {
                    for (int col = 0; col < line.length(); col++) {
                        map[row][col] = Character.getNumericValue(line.charAt(col));
                    }
                    row++;
                }
            }
        } catch (IOException e) {
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (map == null) return;

        for (int row = 0; row < map.length; row++) {
            for (int col = 0; col < map[row].length; col++) {
                switch (map[row][col]) {
                    case 0 -> g.setColor(Color.WHITE); // Walkable space
                    case 1 -> g.setColor(Color.GRAY);  // Wall
                    case 2 -> g.setColor(Color.BLUE);  // Water
                    case 3 -> g.setColor(new Color(139, 69, 19)); // Door
                    case 4 -> g.setColor(Color.YELLOW); // Key
                    case 5 -> g.setColor(Color.RED);    // Player
                }
                g.fillRect(col * TILE_SIZE, row * TILE_SIZE, TILE_SIZE, TILE_SIZE);
            }
        }
    }
}