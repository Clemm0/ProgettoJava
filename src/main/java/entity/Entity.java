package entity;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Entity {

    // Posizione dell'entità nel mondo di gioco e velocità di movimento
    public int worldX, worldY;
    public int speed;

    // Sprite per l'animazione nelle quattro direzioni
    public BufferedImage right0, right1, right2,right3, right4;
    public BufferedImage left0, left1, left2,left3, left4;
    public BufferedImage up0, up1;
    public BufferedImage down0,down1;
    public String direction;

    // Gestione dei frame per l'animazione
    public int spriteCounter = 0;
    public int spriteNum = 1;

    // Area usata per rilevare le collisioni
    public Rectangle solidArea;
    public boolean collisionOn = false;

    // Posizione predefinita dell'area solida (usata per il reset)
    public int solidAreaDefaultX, solidAreaDefaultY;
}
