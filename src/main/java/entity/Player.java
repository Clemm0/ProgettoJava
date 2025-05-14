package entity;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import Main.GamePanel;
import Main.KeyHandler;

public class Player extends Entity {
    GamePanel gp;
    KeyHandler keyH;

    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;
        solidArea = new Rectangle(gp.tileSize / 8, gp.tileSize / 8, gp.tileSize - gp.tileSize / 4,
                gp.tileSize - gp.tileSize / 4);
        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {
        x = gp.tileSize * 7;
        y = gp.tileSize * 7;
        speed = 4; // come si fa a far muovere secondo una griglia fissa boh da scoprire
        direction = "right";
    }

    public void getPlayerImage() {
        try {
            leftStill = ImageIO.read(getClass().getResourceAsStream("/res/player/cat/CatStill.png"));
            /*
             * upStill =
             * ImageIO.read(getClass().getResourceAsStream("/res/player/cat/UpStill.png"));
             * downStill =
             * ImageIO.read(getClass().getResourceAsStream("/res/player/cat/DownStill.png"))
             * ;
             * rightStill =
             * ImageIO.read(getClass().getResourceAsStream("/res/player/cat/RightStill.png")
             * );
             * up1 =
             * ImageIO.read(getClass().getResourceAsStream("/res/player/cat/Up1.png"));
             * up2 =
             * ImageIO.read(getClass().getResourceAsStream("/res/player/cat/Up2.png"));
             * up3 =
             * ImageIO.read(getClass().getResourceAsStream("/res/player/cat/Up3.png"));
             * up4 =
             * ImageIO.read(getClass().getResourceAsStream("/res/player/cat/Up4.png"));
             * down1 =
             * ImageIO.read(getClass().getResourceAsStream("/res/player/cat/Down1.png"));
             * down2 =
             * ImageIO.read(getClass().getResourceAsStream("/res/player/cat/Down2.png"));
             * down3 =
             * ImageIO.read(getClass().getResourceAsStream("/res/player/cat/Down3.png"));
             * down4 =
             * ImageIO.read(getClass().getResourceAsStream("/res/player/cat/Down4.png"));
             * left1 =
             * ImageIO.read(getClass().getResourceAsStream("/res/player/cat/Left1.png"));
             * left2 =
             * ImageIO.read(getClass().getResourceAsStream("/res/player/cat/Left2.png"));
             * left3 =
             * ImageIO.read(getClass().getResourceAsStream("/res/player/cat/Left3.png"));
             * left4 =
             * ImageIO.read(getClass().getResourceAsStream("/res/player/cat/Left4.png"));
             * right1 =
             * ImageIO.read(getClass().getResourceAsStream("/res/player/cat/Right1.png"));
             * right2 =
             * ImageIO.read(getClass().getResourceAsStream("/res/player/cat/Right2.png"));
             * right3 =
             * ImageIO.read(getClass().getResourceAsStream("/res/player/cat/Right3.png"));
             * right4 =
             * ImageIO.read(getClass().getResourceAsStream("/res/player/cat/Right4.png"));
             */
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update() {
        if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {
            if (keyH.upPressed)
                direction = "up";
            else if (keyH.downPressed)
                direction = "down";
            else if (keyH.leftPressed)
                direction = "left";
            else if (keyH.rightPressed)
                direction = "right";
            collisionOn = false;
            gp.cChecker.checkTile(this);
            if (collisionOn == false) {
                switch (direction) {
                    case "up" -> {
                        y -= speed;
                        if (y < 0)
                            y = 0;
                    }
                    case "down" -> {
                        y += speed;
                        if (y > gp.screenHeight - gp.tileSize)
                            y = gp.screenHeight - gp.tileSize;
                        break;
                    }
                    case "left" -> {
                        x -= speed;
                        if (x < 0)
                            x = 0;
                        break;
                    }
                    case "right" -> {
                        x += speed;
                        if (x > gp.screenWidth - gp.tileSize)
                            x = gp.screenWidth - gp.tileSize;
                        break;
                    }
                }
            }
            spriteCounter++;
            if (spriteCounter > 20) {
                switch (spriteNum) {
                    case 1 -> spriteNum = 2;
                    case 2 -> spriteNum = 3;
                    case 3 -> spriteNum = 4;
                    case 4 -> spriteNum = 1;
                    default -> {
                    }
                }
                spriteCounter = 0;
            }
        } else {
            direction = "still";
        }
    }

    public void draw(java.awt.Graphics2D g2) {
        BufferedImage image = null;
        /*
         * if (direction.equals("up")) {
         * if (spriteNum == 1) {
         * image = up1;
         * } else if (spriteNum == 2) {
         * image = up2;
         * } else if (spriteNum == 3) {
         * image = up3;
         * } else if (spriteNum == 4) {
         * image = up4;
         * }
         * }
         * if (direction.equals("down")) {
         * if (spriteNum == 1) {
         * image = down1;
         * } else if (spriteNum == 2) {
         * image = down2;
         * } else if (spriteNum == 3) {
         * image = down3;
         * } else if (spriteNum == 4) {
         * image = down4;
         * }
         * }
         * if (direction.equals("left")) {
         * if (spriteNum == 1) {
         * image = left1;
         * } else if (spriteNum == 2) {
         * image = left2;
         * } else if (spriteNum == 3) {
         * image = left3;
         * } else if (spriteNum == 4) {
         * image = left4;
         * }
         * }
         * if (direction.equals("right")) {
         * if (spriteNum == 1) {
         * image = right1;
         * } else if (spriteNum == 2) {
         * image = right2;
         * } else if (spriteNum == 3) {
         * image = right3;
         * } else if (spriteNum == 4) {
         * image = right4;
         * }
         * }
         */
        image = leftStill;
        g2.drawImage(image, x, y, gp.tileSize, gp.tileSize, null);
    }
}
