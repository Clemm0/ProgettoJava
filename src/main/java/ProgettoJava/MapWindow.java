package ProgettoJava;

import javax.swing.JFrame;

public class MapWindow extends JFrame {
    public MapWindow(MapBuilder mapBuilder, Player player) {
        super("Map Builder");
        this.add(mapBuilder);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setUndecorated(true);
        this.setVisible(true);
        this.setResizable(false);

        this.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent e) {
                char key = (char) e.getKeyCode();
                player.move(key);
                mapBuilder.updateMapWithPlayer(player);
            }
        });
        // define how to make the player move with arrows

        // define how to update the map

        // define how to update the map with the player

        // define how to move movable objects

        // define how to update the map with movable objects

        // define how to make use of the functions in player class
    }
}
