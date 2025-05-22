package object;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import Main.GamePanel;

public class SuperObject {
    GamePanel sgp = new GamePanel(GamePanel.selectedCharacter);
    public BufferedImage image;
    public String name;
    public boolean collision = true;
    public int worldX, worldY;
    public Rectangle solidArea = new Rectangle(0, 0, sgp.tileSize, sgp.tileSize);
    public int solidAreaDefaultX = 0, solidAreaDefaultY = 0;

    public void draw(java.awt.Graphics2D g2, GamePanel gp, int cameraX, int cameraY) {
        int drawX = worldX - cameraX;
        int drawY = worldY - cameraY;
        g2.drawImage(image, drawX, drawY, gp.tileSize, gp.tileSize, null);
    }
}
