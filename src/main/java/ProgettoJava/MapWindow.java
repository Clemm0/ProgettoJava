package ProgettoJava;

import javax.swing.JFrame;

public class MapWindow extends JFrame {
    public MapWindow(MapBuilder mapBuilder, Player p) {
        super("Map Builder");
        this.add(mapBuilder);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setUndecorated(true);
        this.setVisible(true);
        this.setResizable(false);

        //define how to make the player move with wasd

        //define how to make the player move with arrows

        //define how to update the map
        
        //define how to update the map with the player

        //define how to move movable objects

        //define how to update the map with movable objects

        //define how to make use of the functions in player class
    }
}
