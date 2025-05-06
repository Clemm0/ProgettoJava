package ProgettoJava;

import java.awt.Image;

import javax.swing.ImageIcon;

public abstract class Placed {
    Image image;
    int x,y;
    boolean isMovable,isWalkable;
    public Placed(int x, int y) {
        this.x = x;
        this.y = y;
        this.isMovable = false;
        this.isWalkable = false;
        this.image = new ImageIcon("src/main/resources/empty.png").getImage(); //black square to know where smt doens't work
        loadImage("src/main/resources/empty.png");
    }

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    void loadImage(String im){
        image = new ImageIcon(im).getImage();
    }
}