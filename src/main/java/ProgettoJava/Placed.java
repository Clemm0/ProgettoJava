package ProgettoJava;

import java.awt.Image;

import javax.swing.ImageIcon;

public abstract class Placed {
    protected Image image;
    protected int x, y;
    protected boolean isMovable, isWalkable;

    public Placed(int x, int y, boolean isMovable) {
        this.x = x;
        this.y = y;
        this.isMovable = isMovable;
        this.isWalkable = false;
        this.image = new ImageIcon("src/main/resources/empty.png").getImage();
        loadImage("src/main/resources/empty.png");
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    final void loadImage(String im) {
        image = new ImageIcon(im).getImage();
    }

    public boolean isMovable() {
        return isMovable;
    }

    public boolean isWalkable() {
        return isWalkable;
    }

    public void setImage(String imagePath) {
        this.image = new ImageIcon(imagePath).getImage();
        loadImage(imagePath);
    }
}