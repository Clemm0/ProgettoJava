package object;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import Main.GamePanel;

// Classe base per tutti gli oggetti del gioco
public class SuperObject {
    GamePanel sgp = new GamePanel(GamePanel.selectedCharacter);
    public BufferedImage image;
    protected BufferedImage image1, image2;
    public String name;
    public boolean collision = true;
    public int worldX, worldY;
    public Rectangle solidArea = new Rectangle(0, 0, sgp.tileSize, sgp.tileSize);
    public int solidAreaDefaultX = 0, solidAreaDefaultY = 0;
    private int frameCounter = 0;
    private final int switchFrames = 30;

    // Metodo per disegnare l'oggetto sullo schermo
    public void draw(java.awt.Graphics2D g2, GamePanel gp, int cameraX, int cameraY) {
        int drawX = worldX - cameraX;
        int drawY = worldY - cameraY;
        g2.drawImage(image, drawX, drawY, gp.tileSize, gp.tileSize, null);
    }

    // Metodo di aggiornamento per oggetti animati
    public void update() {
        if (image1 != null && image2 != null) {
            frameCounter++;
            if (frameCounter >= switchFrames) {
                image = (image == image1) ? image2 : image1;
                frameCounter = 0;
            }
        }
    }
}
