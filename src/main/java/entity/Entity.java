package entity;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Entity {
    public int worldX, worldY;
    public int speed;

    public BufferedImage right0, right1, right2,right3, right4;
    public BufferedImage left0, left1, left2,left3, left4;
    public BufferedImage up0, up1;
    public BufferedImage down0,down1;
    public String direction;

    public int spriteCounter = 0;
    public int spriteNum = 1;

    public Rectangle solidArea;
    public boolean collisionOn = false;

    public int solidAreaDefaultX, solidAreaDefaultY;
}
