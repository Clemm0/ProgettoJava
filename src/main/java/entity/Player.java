package entity;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import Main.GamePanel;
import Main.KeyHandler;
import Main.Setting;
import object.SuperObject;

public class Player extends Entity {
    GamePanel gp;
    KeyHandler keyH;
    public static int level = 1;
    public int standCounter = 0;
    boolean moving = false;
    public String name;
    Image image = null;

    public final int screenX;
    public final int screenY;

    private int targetX, targetY;

    // Animazioni
    public BufferedImage up0, up1, up2, up3, up4;
    public BufferedImage down0, down1, down2, down3, down4;
    public BufferedImage left0, left1, left2, left3, left4;
    public BufferedImage right0, right1, right2, right3, right4;

    public Player(GamePanel gp, KeyHandler keyH, String name) {
        this.name = name;
        this.gp = gp;
        this.keyH = keyH;
        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

        int offset = gp.tileSize / 8;
        solidArea = new Rectangle(offset, offset, gp.tileSize - 2 * offset, gp.tileSize - 2 * offset);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        setDefaultValues();
        getPlayerImage();

        targetX = worldX;
        targetY = worldY;
    }

    public void setDefaultValues() {
        worldX = gp.tileSize * 7;
        worldY = gp.tileSize * 7;
        speed = 4;
        direction = "right";
    }

    public void getPlayerImage() {
        try {
            // UP
            up0 = ImageIO.read(getClass().getResourceAsStream("/res/player/" + name + "/Up0.png"));
            up1 = ImageIO.read(getClass().getResourceAsStream("/res/player/" + name + "/Up1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/res/player/" + name + "/Up2.png"));
            up3 = ImageIO.read(getClass().getResourceAsStream("/res/player/" + name + "/Up3.png"));
            up4 = ImageIO.read(getClass().getResourceAsStream("/res/player/" + name + "/Up4.png"));

            // DOWN
            down0 = ImageIO.read(getClass().getResourceAsStream("/res/player/" + name + "/Down0.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/res/player/" + name + "/Down1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/res/player/" + name + "/Down2.png"));
            down3 = ImageIO.read(getClass().getResourceAsStream("/res/player/" + name + "/Down3.png"));
            down4 = ImageIO.read(getClass().getResourceAsStream("/res/player/" + name + "/Down4.png"));

            // LEFT
            left0 = ImageIO.read(getClass().getResourceAsStream("/res/player/" + name + "/Left0.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/res/player/" + name + "/Left1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/res/player/" + name + "/Left2.png"));
            left3 = ImageIO.read(getClass().getResourceAsStream("/res/player/" + name + "/Left3.png"));
            left4 = ImageIO.read(getClass().getResourceAsStream("/res/player/" + name + "/Left4.png"));

            // RIGHT
            right0 = ImageIO.read(getClass().getResourceAsStream("/res/player/" + name + "/Right0.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/res/player/" + name + "/Right1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/res/player/" + name + "/Right2.png"));
            right3 = ImageIO.read(getClass().getResourceAsStream("/res/player/" + name + "/Right3.png"));
            right4 = ImageIO.read(getClass().getResourceAsStream("/res/player/" + name + "/Right4.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update() {
        if (keyH.escapePressed) {
            keyH.escapePressed = false;
            showPauseMenu();
            return;
        }

        if (!moving) {
            int nextX = worldX;
            int nextY = worldY;

            if (keyH.upPressed) {
                direction = "up";
                image = up0;
                nextY -= gp.tileSize;
                gp.playSE(3);
            } else if (keyH.downPressed) {
                direction = "down";
                image = down0;
                nextY += gp.tileSize;
                gp.playSE(3);
            } else if (keyH.leftPressed) {
                direction = "left";
                image = left0;
                nextX -= gp.tileSize;
                gp.playSE(3);
            } else if (keyH.rightPressed) {
                direction = "right";
                image = right0;
                nextX += gp.tileSize;
                gp.playSE(3);
            }

            if (nextX != worldX || nextY != worldY) {
                collisionOn = false;
                int oldX = worldX, oldY = worldY;
                worldX = nextX;
                worldY = nextY;
                gp.cChecker.checkTile(this);
                worldX = oldX;
                worldY = oldY;

                int objIndex = -1;
                for (int i = 0; i < gp.obj.length; i++) {
                    SuperObject obj = gp.obj[i];
                    if (obj != null && obj.worldX == nextX && obj.worldY == nextY) {
                        objIndex = i;
                        break;
                    }
                }

                boolean canMovePlayer = false;

                if (!collisionOn && canMoveTo(nextX, nextY) && (objIndex == -1 || gp.obj[objIndex] == null)) {
                    canMovePlayer = true;
                } else if (objIndex != -1 && gp.obj[objIndex] != null) {
                    boolean pushed = tryPushObject(objIndex);
                    if (pushed) {
                        canMovePlayer = true;
                    }
                }

                if (canMovePlayer) {
                    targetX = nextX;
                    targetY = nextY;
                    moving = true;
                }

                keyH.upPressed = keyH.downPressed = keyH.leftPressed = keyH.rightPressed = false;
            } else {
                standCounter++;
                if (standCounter == 20) {
                    spriteNum = 1;
                    standCounter = 0;
                }
            }
        }

        if (moving) {
            if (worldX < targetX) worldX += speed;
            if (worldX > targetX) worldX -= speed;
            if (worldY < targetY) worldY += speed;
            if (worldY > targetY) worldY -= speed;

            if (worldX == targetX && worldY == targetY) {
                moving = false;
                spriteNum = (spriteNum + 1) % 5;
            }
        }
    }

    private boolean canMoveTo(int nextX, int nextY) {
        return !(nextX < 0 || nextY < 0 ||
                nextX > gp.screenWidth - gp.tileSize ||
                nextY > gp.screenHeight - gp.tileSize);
    }

    private boolean tryPushObject(int i) {
        if (gp.obj[i] == null) return false;
        String objectName = gp.obj[i].name;

        switch (objectName) {
            case "Key" -> {
                int keyX = gp.obj[i].worldX;
                int keyY = gp.obj[i].worldY;
                switch (direction) {
                    case "up" -> keyY -= gp.tileSize;
                    case "down" -> keyY += gp.tileSize;
                    case "left" -> keyX -= gp.tileSize;
                    case "right" -> keyX += gp.tileSize;
                }

                int tileCol = keyX / gp.tileSize;
                int tileRow = keyY / gp.tileSize;

                if (tileCol < 0 || tileRow < 0 ||
                    tileCol >= gp.tileM.mapTileNum.length ||
                    tileRow >= gp.tileM.mapTileNum[0].length) {
                    return false;
                }

                boolean canMove = !gp.tileM.tile[gp.tileM.mapTileNum[tileCol][tileRow]].collision;

                boolean occupied = false;
                for (int j = 0; j < gp.obj.length; j++) {
                    SuperObject obj = gp.obj[j];
                    if (obj != null && j != i && obj.worldX == keyX && obj.worldY == keyY && !"Door".equals(obj.name)) {
                        occupied = true;
                        break;
                    }
                }

                if (canMove && !occupied) {
                    gp.obj[i].worldX = keyX;
                    gp.obj[i].worldY = keyY;
                    for (int j = 0; j < gp.obj.length; j++) {
                        SuperObject obj = gp.obj[j];
                        if (obj != null && "Door".equals(obj.name) && obj.worldX == keyX && obj.worldY == keyY) {
                            gp.playSE(2);
                            gp.obj[j] = null;
                            gp.obj[i] = null;
                            break;
                        }
                    }
                    return true;
                }
                return false;
            }
            case "Flag" -> {
                gp.playSE(1);
                level++;
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                System.exit(0);
                return false;
            }
            default -> {
                return false;
            }
        }
    }

    private void showPauseMenu() {
        new Setting();
    }

    public void draw(Graphics2D g2) {
        switch (direction) {
            case "up" -> {
                switch (spriteNum) {
                    case 0 -> image = up0;
                    case 1 -> image = up1;
                    case 2 -> image = up2;
                    case 3 -> image = up3;
                    case 4 -> image = up4;
                }
            }
            case "down" -> {
                switch (spriteNum) {
                    case 0 -> image = down0;
                    case 1 -> image = down1;
                    case 2 -> image = down2;
                    case 3 -> image = down3;
                    case 4 -> image = down4;
                }
            }
            case "left" -> {
                switch (spriteNum) {
                    case 0 -> image = left0;
                    case 1 -> image = left1;
                    case 2 -> image = left2;
                    case 3 -> image = left3;
                    case 4 -> image = left4;
                }
            }
            case "right" -> {
                switch (spriteNum) {
                    case 0 -> image = right0;
                    case 1 -> image = right1;
                    case 2 -> image = right2;
                    case 3 -> image = right3;
                    case 4 -> image = right4;
                }
            }
        }

        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
    }
}
