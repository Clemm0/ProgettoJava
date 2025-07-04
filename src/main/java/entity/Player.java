package entity;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;

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
            // LEFT (5 frames)
            left0 = ImageIO.read(getClass().getResourceAsStream("/res/player/" + name + "/Left0.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/res/player/" + name + "/Left1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/res/player/" + name + "/Left2.png"));
            left3 = ImageIO.read(getClass().getResourceAsStream("/res/player/" + name + "/Left3.png"));
            left4 = ImageIO.read(getClass().getResourceAsStream("/res/player/" + name + "/Left4.png"));

            // RIGHT (5 frames)
            right0 = ImageIO.read(getClass().getResourceAsStream("/res/player/" + name + "/Right0.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/res/player/" + name + "/Right1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/res/player/" + name + "/Right2.png"));
            right3 = ImageIO.read(getClass().getResourceAsStream("/res/player/" + name + "/Right3.png"));
            right4 = ImageIO.read(getClass().getResourceAsStream("/res/player/" + name + "/Right4.png"));

            // UP (2 frames)
            up0 = ImageIO.read(getClass().getResourceAsStream("/res/player/" + name + "/Up0.png"));
            up1 = ImageIO.read(getClass().getResourceAsStream("/res/player/" + name + "/Up1.png"));

            // DOWN (2 frames)
            down0 = ImageIO.read(getClass().getResourceAsStream("/res/player/" + name + "/Down0.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/res/player/" + name + "/Down1.png"));

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
                nextY -= gp.tileSize;
                gp.playSE(3);
            } else if (keyH.downPressed) {
                direction = "down";
                nextY += gp.tileSize;
                gp.playSE(3);
            } else if (keyH.leftPressed) {
                direction = "left";
                nextX -= gp.tileSize;
                gp.playSE(3);
            } else if (keyH.rightPressed) {
                direction = "right";
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
            spriteCounter++;
            if (spriteCounter > 10) {
                spriteNum++;
                if (direction.equals("left") || direction.equals("right")) {
                    if (spriteNum > 4) spriteNum = 0;
                } else { // up or down hanno 2 frame
                    if (spriteNum > 1) spriteNum = 0;
                }
                spriteCounter = 0;
            }

            if (worldX < targetX) {
                worldX += speed;
                if (worldX > targetX) worldX = targetX;
            }
            if (worldX > targetX) {
                worldX -= speed;
                if (worldX < targetX) worldX = targetX;
            }
            if (worldY < targetY) {
                worldY += speed;
                if (worldY > targetY) worldY = targetY;
            }
            if (worldY > targetY) {
                worldY -= speed;
                if (worldY < targetY) worldY = targetY;
            }
            if (worldX == targetX && worldY == targetY) {
                moving = false;
            }
        }
    }

    private boolean canMoveTo(int nextX, int nextY) {
        if (nextX < 0 || nextY < 0 ||
                nextX > gp.maxWorldSize - gp.tileSize ||
                nextY > gp.maxWorldHeight - gp.tileSize) {
            return false;
        }
        return true;
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

        public void draw(Graphics2D g2, int cameraX, int cameraY) 
        {
        int screenX = this.worldX - cameraX;
        int screenY = this.worldY - cameraY;

        switch (direction) {
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
            case "up" -> {
                switch (spriteNum) {
                    case 0 -> image = up0;
                    case 1 -> image = up1;
                }
            }
            case "down" -> {
                switch (spriteNum) {
                    case 0 -> image = down0;
                    case 1 -> image = down1;
                }
            }
        }

        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
    }

}
