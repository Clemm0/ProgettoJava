package Way2Logic.Logic; // Nice pun (no pun intended)

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import Main.GamePanel;

public class Block {
    public static boolean collision, you, push, sink, stop, lock, ulock, win;
    public BufferedImage image;
    protected BufferedImage image1, image2;
    public String name;
    public boolean isMovable = true;
    public boolean isSolid = true;
    public int worldX, worldY;
    public int tileSize = GamePanel.Size;
    public Rectangle solidArea = new Rectangle(0, 0, tileSize, tileSize);
    public int solidAreaDefaultX = 0, solidAreaDefaultY = 0;
    private int frameCounter = 0;
    private final int switchFrames = 30;

    public Block(String name) {
        this.name = name;
        collision = true;
        push = true;
        you = false;
        sink = false;
        stop = false;
        lock = false;
        ulock = false;
        win = false;
    }

    public void draw(java.awt.Graphics2D g2, int cameraX, int cameraY) {
        int drawX = worldX - cameraX;
        int drawY = worldY - cameraY;
        g2.drawImage(image, drawX, drawY, tileSize, tileSize, null);
    }

    public void update() {
        if (image1 != null && image2 != null) {
            frameCounter++;
            if (frameCounter >= switchFrames) {
                image = (image == image1) ? image2 : image1;
                frameCounter = 0;
            }
        }
    }

    public String getName() {
        return name;
    }

    public BufferedImage setImage(String path){
        try {
            image = ImageIO.read(getClass().getResourceAsStream("src/main/java/Way2Logic/LogicTiles/" + name + ".png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return image;
    }
}
